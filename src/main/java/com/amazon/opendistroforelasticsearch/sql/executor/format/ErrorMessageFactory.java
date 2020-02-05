/*
 *   Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package com.amazon.opendistroforelasticsearch.sql.executor.format;

import org.elasticsearch.ElasticsearchException;

public class ErrorMessageFactory {

    public static ErrorMessage createErrorMessage(Exception e, int status) {
        if (e instanceof ElasticsearchException) {
            return new ElasticsearchErrorMessage((ElasticsearchException) e,
                    ((ElasticsearchException) e).status().getStatus());
        } else if (unwrapCause(e) instanceof ElasticsearchException) {
            ElasticsearchException exception = (ElasticsearchException) unwrapCause(e);
            return new ElasticsearchErrorMessage(exception, exception.status().getStatus());
        }
        return new ErrorMessage(e, status);
    }


    private static Throwable unwrapCause(Throwable t) {
        Throwable result = t;
        int layer = 0;
        // limit the unwrapping up to 5 layers
        while (result != null && layer < 5) {
            if (result instanceof ElasticsearchException) {
                return result;
            }
            if (result.getCause() == null) {
                return result;
            }
            result = result.getCause();
            layer++;
        }
        return result;
    }
}
