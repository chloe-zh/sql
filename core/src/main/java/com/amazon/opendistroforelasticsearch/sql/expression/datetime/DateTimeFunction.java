/*
 *
 *    Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License").
 *    You may not use this file except in compliance with the License.
 *    A copy of the License is located at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    or in the "license" file accompanying this file. This file is distributed
 *    on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *    express or implied. See the License for the specific language governing
 *    permissions and limitations under the License.
 *
 */

package com.amazon.opendistroforelasticsearch.sql.expression.datetime;

import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getDateValue;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getDatetimeValue;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getLongValue;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getStringValue;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getTimeValue;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.DATE;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.DATETIME;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.INTEGER;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.LONG;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.STRING;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.TIME;
import static com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType.TIMESTAMP;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.ADDDATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.ADDTIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.CONVERT_TZ;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.CURDATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.CURRENT_DATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.CURRENT_TIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.CURRENT_TIMESTAMP;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.CURTIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DATEDIFF;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DATE_FORMAT;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAYOFMONTH;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.NOW;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.define;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.impl;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.nullMissingHandling;

import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDateValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDatetimeValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprIntegerValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprStringValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimeValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimestampValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionRepository;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

/**
 * The definition of date and time functions.
 * 1) have the clear interface for function define.
 * 2) the implementation should rely on ExprValue.
 */
@UtilityClass
public class DateTimeFunction {
  /**
   * Register DateTime Functions.
   * @param repository {@link BuiltinFunctionRepository}.
   */
  public void register(BuiltinFunctionRepository repository) {
    repository.register(dayOfMonth());
    repository.register(addDate());
    repository.register(addTime());
    repository.register(convertTimeZone());
    repository.register(curdate());
    repository.register(currentDate());
    repository.register(curtime());
    repository.register(currentTime());
    repository.register(currentTimestamp());
    repository.register(date());
    repository.register(dateFormat());
    repository.register(dateDiff());
    repository.register(now());
  }

  /**
   * DAYOFMONTH(DATE). return the day of the month (1-31).
   */
  private FunctionResolver dayOfMonth() {
    return define(DAYOFMONTH.getName(), impl(nullMissingHandling(DateTimeFunction::exprDayOfMonth),
            INTEGER, DATE)
    );
  }

  private FunctionResolver addDate() {
    return define(ADDDATE.getName(), impl(nullMissingHandling(DateTimeFunction::exprAddDate),
            DATE, DATE, LONG));
  }

  private FunctionResolver addTime() {
    return define(ADDTIME.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprAddTime), TIME, TIME, TIME));
  }

  private FunctionResolver convertTimeZone() {
    return define(
        CONVERT_TZ.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprConvertTimeZone),
            DATETIME, DATETIME, STRING, STRING));
  }

  private FunctionResolver curdate() {
    return define(CURDATE.getName(), impl(DateTimeFunction::exprCurdate, DATE));
  }

  private FunctionResolver currentDate() {
    return define(CURRENT_DATE.getName(), impl(DateTimeFunction::exprCurdate, DATE));
  }

  private FunctionResolver curtime() {
    return define(CURTIME.getName(), impl(DateTimeFunction::exprCurtime, TIME));
  }

  private FunctionResolver currentTime() {
    return define(CURRENT_TIME.getName(), impl(DateTimeFunction::exprCurtime, TIME));
  }

  private FunctionResolver now() {
    return define(NOW.getName(), impl(DateTimeFunction::exprNow, TIMESTAMP));
  }

  private FunctionResolver currentTimestamp() {
    return define(CURRENT_TIMESTAMP.getName(), impl(DateTimeFunction::exprNow, TIMESTAMP));
  }

  private FunctionResolver date() {
    return define(BuiltinFunctionName.DATE.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprDate), DATE, DATETIME),
        impl(DateTimeFunction::exprDate, DATE, TIMESTAMP));
  }

  private FunctionResolver dateFormat() {
    return define(DATE_FORMAT.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprDateFormat), STRING, DATE, STRING));
  }

  private FunctionResolver dateDiff() {
    return define(DATEDIFF.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprDateDiff), INTEGER, DATETIME, DATETIME));
  }

  /**
   * Day of Month implementation for ExprValue.
   * @param date ExprValue of Date type.
   * @return ExprValue.
   */
  private ExprValue exprDayOfMonth(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).getMonthValue());
  }

  private ExprValue exprAddDate(ExprValue date, ExprValue days) {
    return new ExprDateValue(getDateValue(date).plusDays(getLongValue(days)).toString());
  }

  private ExprValue exprAddTime(ExprValue originalTime, ExprValue addedTime) {
    LocalTime time = getTimeValue(addedTime);
    return new ExprTimeValue(getTimeValue(originalTime).plusHours(time.getHour())
        .plusMinutes(time.getMinute()).plusSeconds(time.getSecond()));
  }

  private ExprValue exprConvertTimeZone(
      ExprValue dateTime, ExprValue fromTimeZone, ExprValue toTimeZone) {
    ZoneId from = ZoneId.of(getStringValue(fromTimeZone));
    ZoneId to = ZoneId.of(getStringValue(toTimeZone));
    return new ExprDatetimeValue(
        getDatetimeValue(dateTime).withZoneSameInstant(from).withZoneSameInstant(to).toString());
  }

  private ExprValue exprCurdate() {
    return new ExprDateValue(ZonedDateTime.now().toLocalDate().toString());
  }

  private ExprValue exprCurtime() {
    return new ExprTimeValue(ZonedDateTime.now().toLocalTime());
  }

  private ExprValue exprNow() {
    return new ExprTimestampValue(Instant.now());
  }

  private ExprValue exprDate(ExprValue exprValue) {
    return new ExprDateValue(getDateValue(exprValue).toString());
  }

  private ExprValue exprDateFormat(ExprValue date, ExprValue format) {
    return new ExprStringValue(
        getDateValue(date).format(DateTimeFormatter.ofPattern(getStringValue(format))));
  }

  private ExprValue exprDateDiff(ExprValue date1, ExprValue date2) {
    Days diff = Days.daysBetween(
        new DateTime(getDateValue(date1)), new DateTime(getDateValue(date2)));
    return new ExprIntegerValue(diff.getDays());
  }
}
