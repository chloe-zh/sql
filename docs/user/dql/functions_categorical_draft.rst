
=============
SQL Functions
=============

.. rubric:: Table of contents

.. contents::
   :local:
   :depth: 2

Introduction
============

hello


Mathematical Functions
======================

hello


Date and Time Functions
=======================

ADDDATE
-------

Description
>>>>>>>>>>>

Usage: adddate(date, INTERVAL expr unit) adds the time interval of second argument to date; adddate(date, days) adds the second argument as integer number of days to date.

Argument type: DATE/DATETIME/TIMESTAMP, INTERVAL/INTEGER

Return type map:

(DATE, DATE INTERVAL) -> DATE
(DATE, TIME INTERVAL) -> DATETIME
(DATETIME/TIMESTAMP, INTERVAL) -> DATETIME

Synonyms: `DATE_ADD`_


ADDTIME
-------

Description
>>>>>>>>>>>

Usage: addtime(expr1, expr2) adds expr2 amount of time to time expr1

Argument type: TIME/DATETIME, TIME

Return type: TIME


CONVERT_TZ
----------

Desciption
>>>>>>>>>>

Usage: convert_tz(dt, from_tz, to_tz) converts the datetime dt from timezone from_tz to another timezone to_tz. Returns NULL if arguments are invalid.

Argument type: DATETIME, STRING, STRING

Return type: DATETIME


CURDATE
-------

Description
>>>>>>>>>>>

Usage: Returns the current date as a value in 'YYYY-MM-DD' or YYYYMMDD format, depending on whether the function is used in string or numeric context.

Return type: DATE

Synonyms: `CURRENT_DATE`_


CURRENT_DATE
------------

Description
>>>>>>>>>>>

Usage: Returns the current date as a value in 'YYYY-MM-DD' or YYYYMMDD format, depending on whether the function is used in string or numeric context.

Return type: DATE

Synonyms: `CURDATE`_


CURRENT_TIME
------------

Description
>>>>>>>>>>>

Usage: current_time() returns the current time as a value in 'hh:mm:ss' or hhmmss format, depending on whether the function is used in string or numeric context. The value is expressed in the session time zone. Given an integer argument fsp, current_time(fsp) specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: TIME

Synonyms: `CURTIME`_


CURRENT_TIMESTAMP
-----------------

Description
>>>>>>>>>>>

Usage: current_timestamp() returns the current date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. The value is expressed in the session time zone. Given an integer argument fsp, current_timestamp(fsp) specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: TIMESTAMP

Synonyms: `NOW`_


CURTIME
-------

Description
>>>>>>>>>>>

Usage: curtime() returns the current time as a value in 'hh:mm:ss' or hhmmss format, depending on whether the function is used in string or numeric context. The value is expressed in the session time zone. Given an integer argument fsp, curtime(fsp) specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: TIME

Synonyms: `CURRENT_TIME`_


DATE
----

Description
>>>>>>>>>>>

Usage: date(expr) extracts the date part of the expression expr.

Argument type: DATE/DATETIME

Return type: DATE


DATEDIFF
--------

Description
>>>>>>>>>>>

Usage: datediff(expr1, expr2) returns expr1 − expr2 expressed as a value in days from one date to the other. Only the date parts of the values are used in the calculation.

Argument type: DATE/DATETIME

Return type: DATE


DATE_ADD
--------

Description
>>>>>>>>>>>

Usage: date_add(date, INTERVAL expr unit) adds the time interval expr to date

Argument type: DATE/DATETIME/TIMESTAMP, INTERVAL

Return type map:

(DATE, DATE INTERVAL) -> DATE
(DATE, TIME INTERVAL) -> DATETIME
(DATETIME/TIMESTAMP, INTERVAL) -> DATETIME

Synonyms: `ADDDATE`_


DATE_FORMAT
-----------

Description
>>>>>>>>>>>

Usage: date_format(date, format) formats the date value according to the format string. Format specifier reference: `Appendix I: Date and Time Format Specifier Characters Table`_

Argument type: DATE, STRING

Return type: STRING


DATE_SUB
--------

Description
>>>>>>>>>>>

Usage: date_sub(date, INTERVAL expr unit) subtracts the time interval expr from date

Argument type: DATE/DATETIME/TIMESTAMP, INTERVAL

Return type map:

(DATE, DATE INTERVAL) -> DATE
(DATE, TIME INTERVAL) -> DATETIME
(DATETIME/TIMESTAMP, INTERVAL) -> DATETIME

Synonyms: `SUBDATE`_


DAY
---

Description
>>>>>>>>>>>

Usage: day(date) extracts the day of the month for date, in the range 1 to 31, or 0 for dates such as '0000-00-00' or '2008-00-00' that have a zero day part.

Argument type: DATE

Return type: INTEGER

Synonyms: `DAYOFMONTH`_


DAYNAME
-------

Description
>>>>>>>>>>>

Usage: dayname(date) returns the name of the weekday for date, including Monday, Tuesday, Wednesday, Thursday, Friday, Saturday and Sunday.

Argument type: DATE

Return type: INTEGER


DAYOFMONTH
----------

Description
>>>>>>>>>>>

Usage: dayofmonth(date) extracts the day of the month for date, in the range 1 to 31, or 0 for dates such as '0000-00-00' or '2008-00-00' that have a zero day part.

Argument type: DATE

Return type: INTEGER

Synonyms: `DAY`_


DAYOFWEEK
---------

Description
>>>>>>>>>>>

Usage: dayofweek(date) returns the weekday index for date (1 = Sunday, 2 = Monday, …, 7 = Saturday).

Argument type: DATE

Return type: INTEGER


DAYOFYEAR
---------

Description
>>>>>>>>>>>

Usage: dayofyear(date) returns the day of the year for date, in the range 1 to 366.

Argument type: DATE

Return type: INTEGER


EXTRACT
-------

Description
>>>>>>>>>>>

Usage: extract(unit FROM date) extracts parts with respect to the unit from the date.

Argument type: UNIT, DATE

Return type: INTEGER


FROM_DAYS
---------

Description
>>>>>>>>>>>

Usage: from_days(N) returns the date value given the day number N.

Argument type: INTEGER/LONG

Return type: DATE


FROM_UNIXTIME
-------------

Description
>>>>>>>>>>>

Usage: from_unixtime(unix_timestamp[, format]) returns a representation of the unix_timestamp argument as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in a string or numeric context. If second argument is specified, returns the time in the given format.

Argument type: LONG[, STRING]

Return type: STRING


GET_FORMAT
----------

Description
>>>>>>>>>>>

Usage: get_format(expr, standard) gets the datetime in the specified standard format. The standard formats include 'EUR'/'USA'/'JIS'/'ISO''INTERNAL'. The detailed formats of these standards refer to `Appendix II: FORMAT STANDARDS TABLE`_

Argument type: DATE/TIME/DATETIME/TIMESTAMP, STRING

Return type: STRING


HOUR
----

Description
>>>>>>>>>>>

Usage: hour(time) extracts the hour value for time. Different from the time of day value, the time value has a large range and can be greater than 23, so the return value of hour(time) can be also greater than 23.

Argument type: TIME

Return type: INTEGER


LAST_DAY
--------

Description
>>>>>>>>>>>

Usage: last_day(date) takes a date or datetime value and returns the corresponding value for the last day of the month. Returns NULL if the argument is invalid.

Argument type: DATE/DATETIME

Return type: DATE


LOCAL_TIME
----------

Description
>>>>>>>>>>>

Usage: local_time() returns the current date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. The value is expressed in the session time zone. Given an integer argument fsp, local_time(fsp) specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: DATETIME

Synonyms: `NOW`_


LOCAL_TIMESTAMP
---------------

Description
>>>>>>>>>>>

Usage: local_timestamp() returns the current date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. The value is expressed in the session time zone. Given an integer argument fsp, local_timestamp(fsp) specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: TIMESTAMP

Synonyms: `NOW`_


MAKEDATE
--------

Description
>>>>>>>>>>>

Usage: makedate(year, dayofyear) returns a date, given year and day-of-year values. dayofyear must be greater than 0 or the result is NULL.

Argument type: INTEGER, INTEGER

Return type: DATE


MAKETIME
--------

Description
>>>>>>>>>>>

Usage: maketime(hour, minute, second) returns a time value calculated from the hour, minute, and second arguments. Second can have a fractional part.

Argument type: INTEGER, INTEGER, INTEGER/DOUBLE

Return type: TIME


MICROSECOND
-----------

Description
>>>>>>>>>>>

Usage: microsecond(expr) returns the microseconds from the time or datetime expression expr as a number in the range from 0 to 999999.

Argument type: TIME/DATETIME/TIMESTAMP

Return type: TIME


MINUTE
------

Description
>>>>>>>>>>>

Usage: minute(time) returns the minute for time, in the range 0 to 59.

Argument type: TIME/DATETIME/TIMESTAMP

Return type: TIME


MONTH
-----

Description
>>>>>>>>>>>

Usage: month(date) returns the month for date, in the range 1 to 12 for January to December, or 0 for dates such as '0000-00-00' or '2008-00-00' that have a zero month part.

Argument type: DATE

Return type: INTEGER


MONTHNAME
---------

Description
>>>>>>>>>>>

Usage: monthname(date) returns the full name of the month for date.

Argument type: DATE

Return type: STRING


NOW
---

Description
>>>>>>>>>>>

Usage: now() returns the current date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. The value is expressed in the session time zone. Given an integer argument fsp, now(fsp) specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: DATETIME

Difference from SYSDATE: See `SYSDATE`_


PERIOD_ADD
----------

Description
>>>>>>>>>>>

Usage: period_add(P, N) adds N months to period P (in the format YYMM or YYYYMM). Returns a value in the format YYYYMM.

Argument type: LONG, INTEGER

Return type: LONG


PERIOD_DIFF
-----------

Description
>>>>>>>>>>>

Usage: period_diff(P1, P2) returns the number of months between periods P1 and P2. P1 and P2 should be in the format YYMM or YYYYMM. Note that the period arguments P1 and P2 are not date values.

Argument type: LONG, LONG

Return type: INTEGER


QUARTER
-------

Description
>>>>>>>>>>>

Usage: quarter(date) returns the quarter of the year for date, in the range 1 to 4.

Argument type: DATE

Return type: INTEGER


SECOND
------

Description
>>>>>>>>>>>

Usage: second(time) returns the second for time, in the range 0 to 59.

Argument type: TIME

Return type: INTEGER


SEC_TO_TIME
-----------

Description
>>>>>>>>>>>

Usage: sec_to_time(seconds) returns the seconds argument, converted to hours, minutes, and seconds, as a TIME value. The range of the result is constrained to that of the TIME data type. A warning occurs if the argument corresponds to a value outside that range.

Argument type: INTEGER/LONG

Return type: STRING/LONG


STR_TO_DATE
-----------

Description
>>>>>>>>>>>

Usage: str_to_date(str, format) is the inverse of the DATE_FORMAT() function. It returns the date value of the str from given format.

Argument type: STRING, STRING

Return type: DATE/DATETIME/TIME


SUBDATE
-------

Description
>>>>>>>>>>>

Usage: subdate(date,INTERVAL expr unit) subtracts time interval from date. subdate(expr, days) subtracts interval in day unit from the temporal expression expr.

Argument type: DATE/DATETIME/TIMESTAMP, INTERVAL

Return type map:

(DATE, DATE INTERVAL) -> DATE
(DATE, TIME INTERVAL) -> DATETIME
(DATETIME/TIMESTAMP, INTERVAL) -> DATETIME

Synonyms: `DATE_SUB`_


SUBTIME
-------

Description
>>>>>>>>>>>

Usage: subtime(expr1, expr2) returns expr1 − expr2 expressed as a value in the same format as expr1. expr1 is a time or datetime expression, and expr2 is a time expression.

Argument type: DATE/DATETIME/TIMESTAMP, TIME

Return type: TIME


SYSDATE
-------

Description
>>>>>>>>>>>

Usage: sysdate() Returns the current date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. Given an integer argument fsp, it specifies a fractional seconds precision from 0 to 6, the return value then includes a fractional seconds part of that many digits.

Return type: DATETIME

Note: Difference between SYSDATE and NOW

NOW and SYSDATE functions may return different values even within the same statement. Specifically, the NOW function is to catch the system datetime at the moment the SQL statement is placed; and the SYSDATE function is to get the datetime when the query is executed. Alternatively, you can use the --sysdate-is-now option to cause SYSDATE() to be an alias for NOW(). This works if the option is used on both the replication source server and the replica. The nondeterministic nature of SYSDATE() also means that indexes cannot be used for evaluating expressions that refer to it.


TIME
----

Description
>>>>>>>>>>>

Usage: time(expr) extracts the time value from the temporal expression expr

Argument type: DATETIME/TIME/TIMESTAMP

Return type: TIME


TIMEDIFF
--------

Description
>>>>>>>>>>>

Usage: time(expr1, expr2) returns expr1 − expr2 expressed as a time value. expr1 and expr2 are time or date-and-time expressions, but both must be of the same type.

Argument type: DATETIME/TIME/TIMESTAMP

Return type map:

(DATETIME, DATETIME) -> TIME
(TIME, TIME) -> TIME
(TIMESTAMP, TIMESTAMP) -> TIME


TIMESTAMP
---------

Description
>>>>>>>>>>>

Usage: timestamp(expr) returns the date or datetime expression expr as a datetime value. timestamps(expr1, expr2) adds the time expression expr2 to the date or datetime expression expr1 and returns the result as a datetime value.

Argument type: DATETIME/TIME/TIMESTAMP[, TIME]

Return type map:

DATE/DATETIME/TIMESTAMP -> DATETIME
(DATE/DATETIME/TIMESTAMP, TIME) -> DATETIME


TIMESTAMPADD
------------

Description
>>>>>>>>>>>

Usage: timestamp(unit, interval, datetime_expr) Adds the integer expression interval to the date or datetime expression datetime_expr. The unit for interval is given by the unit argument, which should be one of the following values: MICROSECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, QUARTER, or YEAR.

Argument type: UNIT, INTERVAL, DATE/DATETIME/TIMESTAMP

Return type: DATETIME/DATE??


TIMESTAMPDIFF
-------------

Description
>>>>>>>>>>>

Usage: timestampdiff(unit,datetime_expr1,datetime_expr2) returns datetime_expr2 − datetime_expr1 in the given unit. Date type is converted to datetime by attaching time of 00:00:00 if necessary.

Argument type: UNIT, DATE/DATETIME/TIMESTAMP, DATE/DATETIME/TIMESTAMP

Return type: LONG


TIME_FORMAT
-----------

Description
>>>>>>>>>>>

Usage: time_format(time, format) is used like the `DATE_FORMAT`_function, but the format string may contain format specifiers only for hours, minutes, seconds, and microseconds. Other specifiers produce a NULL value or 0.

Argument type: TIME, STRING

Return type: STRING


TIME_TO_SEC
-----------

Description
>>>>>>>>>>>

Usage: time_to_sec(time) returns the time argument, converted to seconds.

Argument type: TIME

Return type: LONG


TO_DAYS
-------

Description
>>>>>>>>>>>

Usage: to_days(date) returns the day number (the number of days since year 0) of the given date. Returns NULL if date is invalid.

Argument type: DATE

Return type: LONG


TO_SECONDS
----------

Description
>>>>>>>>>>>

Usage: to_seconds(expr) returns the seconds number of the given date since year 0. Returns NULL if date is invalid.

Argument type: DATE/DATETIME/TIMESTAMP

Return type: LONG


UNIX_TIMESTAMP
--------------

Description
>>>>>>>>>>>

Usage: unix_timestamp() returns the current Unix timestamp in the session timezone representing seconds since '1970-01-01 00:00:00' UTC. With a date argument, unix_timestamp(date) returns the value of the argument as seconds since '1970-01-01 00:00:00' UTC.

Argument type: DATE/DATETIME/TIMESTAMP

Return type: LONG


UTC_DATE
--------

Description
>>>>>>>>>>>

Usage: Returns the current UTC date as a value in 'YYYY-MM-DD' or YYYYMMDD format, depending on whether the function is used in string or numeric context.

Return type: STRING/LONG


UTC_TIME
--------

Description
>>>>>>>>>>>

Usage: Returns the current UTC date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. If the fsp argument is given to specify a fractional seconds precision from 0 to 6, the return value includes a fractional seconds part of that many digits.

Return type: STRING/LONG/DOUBLE

UTC_TIMESTAMP
-------------

Description
>>>>>>>>>>>

Usage: Returns the current UTC date and time as a value in 'YYYY-MM-DD hh:mm:ss' or YYYYMMDDhhmmss format, depending on whether the function is used in string or numeric context. If the fsp argument is given to specify a fractional seconds precision from 0 to 6, the return value includes a fractional seconds part of that many digits.

Return type: STRING/LONG/DOUBLE


WEEK
----

Description
>>>>>>>>>>>

Usage: [TODO]


WEEKDAY
-------

Description
>>>>>>>>>>>

Usage: weekday(date) returns the weekday index for date (0 = Monday, 1 = Tuesday, … 6 = Sunday).

Argument type: DATE/DATETIME/TIMESTAMP

Return type: INTEGER


WEEKOFYEAR
----------

Description
>>>>>>>>>>>

Usage: weekday(date) returns the calendar week of the date as a number in the range from 1 to 53. WEEKOFYEAR is a compatibility function that is equivalent to week(date,3).

Argument type: DATE

Return type: INTEGER


YEAR
----

Description
>>>>>>>>>>>

Usage: year(date) returns the year for date, in the range 1000 to 9999, or 0 for the “zero” date.

Argument type: DATE

Return type: INTEGER


YEARWEEK
--------

Description
>>>>>>>>>>>

Usage: yearweek(date) returns year and week for a date. The year in the result may be different from the year in the date argument for the first and the last week of the year. year(date, mode) works exactly like the mode argument to WEEK(). For the single-argument syntax, a mode value of 0 is used. Unlike `WEEK`, the value of default_week_format does not influence YEARWEEK.

Argument type: DATE[, INTEGER]

Return type: LONG













Appendix I: Date and Time Format Specifier Characters Table
-----------------------------------------------------------

=========  ================================================================================================
Specifier  Description
=========  ================================================================================================
%a         Abbreviated weekday name (Sun..Sat)
---------  ------------------------------------------------------------------------------------------------
%b         Abbreviated month name (Jan..Dec)
---------  ------------------------------------------------------------------------------------------------
%c         Month, numeric (0..12)
---------  ------------------------------------------------------------------------------------------------
%D         Day of the month with English suffix (0th, 1st, 2nd, 3rd, …)
---------  ------------------------------------------------------------------------------------------------
%d         Day of the month, numeric (00..31)
---------  ------------------------------------------------------------------------------------------------
%e         Day of the month, numeric (0..31)
---------  ------------------------------------------------------------------------------------------------
%f         Microseconds (000000..999999)
---------  ------------------------------------------------------------------------------------------------
%H         Hour (00..23)
---------  ------------------------------------------------------------------------------------------------
%h         Hour (01..12)
---------  ------------------------------------------------------------------------------------------------
%I         Hour (01..12)
---------  ------------------------------------------------------------------------------------------------
%i         Minutes, numeric (00..59)
---------  ------------------------------------------------------------------------------------------------
%j         Day of year (001..366)
---------  ------------------------------------------------------------------------------------------------
%k         Hour (0..23)
---------  ------------------------------------------------------------------------------------------------
%l         Hour (1..12)
---------  ------------------------------------------------------------------------------------------------
%M         Month name (January..December)
---------  ------------------------------------------------------------------------------------------------
%m         Month, numeric (00..12)
---------  ------------------------------------------------------------------------------------------------
%p         AM or PM
---------  ------------------------------------------------------------------------------------------------
%r         Time, 12-hour (hh:mm:ss followed by AM or PM)
---------  ------------------------------------------------------------------------------------------------
%S         Seconds (00..59)
---------  ------------------------------------------------------------------------------------------------
%s         Seconds (00..59)
---------  ------------------------------------------------------------------------------------------------
%T         Time, 24-hour (hh:mm:ss)
---------  ------------------------------------------------------------------------------------------------
%U         Week (00..53), where Sunday is the first day of the week; WEEK() mode 0
---------  ------------------------------------------------------------------------------------------------
%u         Week (00..53), where Monday is the first day of the week; WEEK() mode 1
---------  ------------------------------------------------------------------------------------------------
%V         Week (01..53), where Sunday is the first day of the week; WEEK() mode 2; used with %X
---------  ------------------------------------------------------------------------------------------------
%v         Week (01..53), where Monday is the first day of the week; WEEK() mode 3; used with %x
---------  ------------------------------------------------------------------------------------------------
%W         Weekday name (Sunday..Saturday)
---------  ------------------------------------------------------------------------------------------------
%w         Day of the week (0=Sunday..6=Saturday)
---------  ------------------------------------------------------------------------------------------------
%X         Year for the week where Sunday is the first day of the week, numeric, four digits; used with %V
---------  ------------------------------------------------------------------------------------------------
%x         Year for the week, where Monday is the first day of the week, numeric, four digits; used with %v
---------  ------------------------------------------------------------------------------------------------
%Y         Year, numeric, four digits
---------  ------------------------------------------------------------------------------------------------
%y         Year, numeric (two digits)
---------  ------------------------------------------------------------------------------------------------
%%         A literal % character
---------  ------------------------------------------------------------------------------------------------
%x         x, for any “x” not listed above
=========  ================================================================================================


Appendix II: FORMAT STANDARDS TABLE
-----------------------------------

=========  ==========  ===================
Data Type  Standard    Result
=========  ==========  ===================
DATE       'USA'       '%m.%d.%Y'
---------  ----------  -------------------
DATE       'JIS'       '%Y-%m-%d'
---------  ----------  -------------------
DATE       'ISO'       '%Y-%m-%d'
---------  ----------  -------------------
DATE       'EUR'       '%d.%m.%Y'
---------  ----------  -------------------
DATE       'INTERNAL'  '%Y%m%d'
---------  ----------  -------------------
DATETIME   'USA'       '%Y-%m-%d %H.%i.%s'
---------  ----------  -------------------
DATETIME   'JIS'       '%Y-%m-%d %H:%i:%s'
---------  ----------  -------------------
DATETIME   'ISO'       '%Y-%m-%d %H:%i:%s'
---------  ----------  -------------------
DATETIME   'EUR'       '%Y-%m-%d %H.%i.%s'
---------  ----------  -------------------
DATETIME   'INTERNAL'  '%Y%m%d%H%i%s'
---------  ----------  -------------------
TIME       'USA'       '%h:%i:%s %p'
---------  ----------  -------------------
TIME       'JIS'       '%H:%i:%s'
---------  ----------  -------------------
TIME       'ISO'       '%H:%i:%s'
---------  ----------  -------------------
TIME       'EUR'       '%H.%i.%s'
---------  ----------  -------------------
TIME       'INTERNAL'  '%H%i%s'
=========  ==========  ===================


