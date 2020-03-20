#!/bin/bash
set -e

# Files created by Elasticsearch should always be group writable too
umask 0002

run_as_other_user_if_needed() {
    if [[ "$(id -u)" == "0" ]]; then
        # If running as root, drop to specified UID and run command
        exec chroot --userspec=1000 / "${@}"
    else
        # Either we are running in Openshift with random uid and are a member of the root group
        # or with a custom --user
        exec "${@}"
    fi
}

# Allow user specify custom CMD, maybe bin/elasticsearch itself
# for example to directly specify `-E` style parameters for elasticsearch on k8s
# or simply to run /bin/bash to check the image
if [[ "$1" != "eswrapper" ]]; then
    if [[ "$(id -u)" == "0" && $(basename "$1") == "elasticsearch" ]]; then
        set -- "elasticsearch" "${@:2}"
        # Use chroot to switch to UID 1000
        exec chroot --userspec=1000 / "$@"
    else
        # User probably wants to run something else, like /bin/bash, with another uid forced (Openshift?)
        exec "$@"
    fi
fi


declare -a es_opts

while IFS='=' read -r envvar_key envvar_value
do
    # Elasticsearch settings need to have at least two dot separated lowercase
    # words, e.g. `cluster.name`, except for `processors` which we handle
    # specially
    if [[ "$envvar_key" =~ ^[a-z0-9_]+\.[a-z0-9_]+ || "$envvar_key" == "processors" ]]; then
        if [[ ! -z $envvar_value ]]; then
          es_opt="-E${envvar_key}=${envvar_value}"
          es_opts+=("${es_opt}")
        fi
    fi
done < <(env)

# The virtual file /proc/self/cgroup should list the current cgroup
# membership. For each hierarchy, you can follow the cgroup path from
# this file to the cgroup filesystem (usually /sys/fs/cgroup/) and
# introspect the statistics for the cgroup for the given
# hierarchy. Alas, Docker breaks this by mounting the container
# statistics at the root while leaving the cgroup paths as the actual
# paths. Therefore, Elasticsearch provides a mechanism to override
# reading the cgroup path from /proc/self/cgroup and instead uses the
# cgroup path defined the JVM system property
# es.cgroups.hierarchy.override. Therefore, we set this value here so
# that cgroup statistics are available for the container this process
# will run in.
export ES_JAVA_OPTS="-Des.cgroups.hierarchy.override=/ $ES_JAVA_OPTS"

if [[ "$(id -u)" == "0" ]]; then
    # If requested and running as root, mutate the ownership of bind-mounts
    if [[ -n "$TAKE_FILE_OWNERSHIP" ]]; then
        chown -R 1000:0 /usr/share/elasticsearch/{data,logs}
    fi
fi

CLK_TCK=`/usr/bin/getconf CLK_TCK`

run_as_other_user_if_needed /usr/share/elasticsearch/bin/elasticsearch "${es_opts[@]}"
