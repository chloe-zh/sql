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
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getDoubleValue;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getIntegerValue;
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
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAY;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAYNAME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAYOFMONTH;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAYOFWEEK;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.DAYOFYEAR;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.FROM_DAYS;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.FROM_UNIXTIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.GET_FORMAT;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.HOUR;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.LAST_DAY;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.LOCALTIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.LOCALTIMESTAMP;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.MAKEDATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.MAKETIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.MICROSECOND;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.MINUTE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.MONTH;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.MONTHNAME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.NOW;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.PERIOD_ADD;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.PERIOD_DIFF;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.QUARTER;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.SECOND;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.SEC_TO_TIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.STR_TO_DATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.SUBTIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.SYSDATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.TIMEDIFF;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.TIME_FORMAT;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.TIME_TO_SEC;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.TO_DAYS;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.TO_SECONDS;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.UNIX_TIMESTAMP;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.UTC_DATE;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.UTC_TIME;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName.UTC_TIMESTAMP;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.define;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.impl;
import static com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionDSL.nullMissingHandling;
import static com.amazon.opendistroforelasticsearch.sql.utils.OperatorUtils.fromPeriod;
import static com.amazon.opendistroforelasticsearch.sql.utils.OperatorUtils.getQuarter;
import static com.amazon.opendistroforelasticsearch.sql.utils.OperatorUtils.toPeriod;
import static com.amazon.opendistroforelasticsearch.sql.utils.OperatorUtils.toUnixTime;

import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDateValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDatetimeValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprDoubleValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprIntegerValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprLongValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprNullValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprStringValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimeValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTimestampValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue;
import com.amazon.opendistroforelasticsearch.sql.data.type.ExprType;
import com.amazon.opendistroforelasticsearch.sql.exception.ExpressionEvaluationException;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionRepository;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver;
import com.amazon.opendistroforelasticsearch.sql.expression.function.SerializableBiFunction;
import com.amazon.opendistroforelasticsearch.sql.expression.function.SerializableFunction;
import com.google.common.collect.ImmutableTable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import lombok.experimental.UtilityClass;
import org.joda.time.DateTime;
import org.joda.time.Days;

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
    repository.register(day());
    repository.register(dayName());
    repository.register(dayOfMonth());
    repository.register(dayOfWeek());
    repository.register(dayOfYear());
    repository.register(fromDays());
    repository.register(fromUnixTime());
    repository.register(getFormat());
    repository.register(hour());
    repository.register(lastDay());
    repository.register(localtime());
    repository.register(localtimestamp());
    repository.register(makeDate());
    repository.register(microsecond());
    repository.register(minute());
    repository.register(month());
    repository.register(monthName());
    repository.register(now());
    repository.register(periodAdd());
    repository.register(periodDiff());
    repository.register(quarter());
    repository.register(second());
    repository.register(secToTime());
    repository.register(strToDate());
    repository.register(subTime());
    repository.register(sysDate());
    repository.register(time());
    repository.register(timeDiff());
    repository.register(timestamp());
    repository.register(timeFormat());
    repository.register(timeToSec());
    repository.register(toDays());
    repository.register(toSeconds());
    repository.register(unixTimestamp());
    repository.register(utcDate());
    repository.register(utcTime());
    repository.register(utcTimestamp());
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
   * DAYOFMONTH(DATE). return the day of the month (1-31).
   */
  private FunctionResolver dayOfMonth() {
    return define(DAYOFMONTH.getName(), impl(nullMissingHandling(DateTimeFunction::exprDayOfMonth),
        INTEGER, DATE)
    );
  }

  private FunctionResolver dayName() {
    return define(DAYNAME.getName(), impl(nullMissingHandling(DateTimeFunction::exprDayName),
        STRING, DATE)
    );
  }

  private FunctionResolver day() {
    return define(DAY.getName(), impl(nullMissingHandling(DateTimeFunction::exprDayOfMonth),
        INTEGER, DATE)
    );
  }

  private FunctionResolver dayOfWeek() {
    return define(DAYOFWEEK.getName(), impl(nullMissingHandling(DateTimeFunction::exprDayOfWeek),
        STRING, DATE)
    );
  }

  private FunctionResolver dayOfYear() {
    return define(DAYOFYEAR.getName(), impl(nullMissingHandling(DateTimeFunction::exprDayOfYear),
        STRING, DATE)
    );
  }

  private FunctionResolver fromDays() {
    return define(FROM_DAYS.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprFromDays), DATE, INTEGER),
        impl(nullMissingHandling(DateTimeFunction::exprFromDays), DATE, LONG)
    );
  }

  private FunctionResolver fromUnixTime() {
    return define(
        FROM_UNIXTIME.getName(),
        impl(nullMissingHandling(
            (SerializableFunction<ExprValue, ExprValue>)
                DateTimeFunction::exprFromUnixTime), STRING, LONG),
        impl(nullMissingHandling(
            (SerializableBiFunction<ExprValue, ExprValue, ExprValue>)
                DateTimeFunction::exprFromUnixTime), STRING, LONG, STRING));
  }

  private FunctionResolver getFormat() {
    return define(GET_FORMAT.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprGetFormat), STRING, DATE, STRING),
        impl(nullMissingHandling(DateTimeFunction::exprGetFormat), STRING, TIME, STRING),
        impl(nullMissingHandling(DateTimeFunction::exprGetFormat), STRING, DATETIME, STRING),
        impl(nullMissingHandling(DateTimeFunction::exprGetFormat), STRING, TIMESTAMP, STRING)
    );
  }

  private FunctionResolver hour() {
    return define(
        HOUR.getName(), impl(nullMissingHandling(DateTimeFunction::exprHour), INTEGER, TIME));
  }

  private FunctionResolver lastDay() {
    return define(
        LAST_DAY.getName(), impl(nullMissingHandling(DateTimeFunction::exprLastDay), DATE, DATE));
  }

  private FunctionResolver localtime() {
    return define(LOCALTIME.getName(), impl(DateTimeFunction::exprNow, TIMESTAMP));
  }

  private FunctionResolver localtimestamp() {
    return define(LOCALTIMESTAMP.getName(), impl(DateTimeFunction::exprNow, TIMESTAMP));
  }

  private FunctionResolver makeDate() {
    return define(MAKEDATE.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprMakeDate), DATE, INTEGER, INTEGER));
  }

  private FunctionResolver makeTime() {
    return define(MAKETIME.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprMakeTime), DATE, INTEGER, INTEGER, INTEGER));
  }

  private FunctionResolver microsecond() {
    return define(MICROSECOND.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprMicroSecond), INTEGER, TIME),
        impl(nullMissingHandling(DateTimeFunction::exprMicroSecond), INTEGER, DATETIME)
    );
  }

  private FunctionResolver minute() {
    return define(MINUTE.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprMinute), INTEGER, TIME),
        impl(nullMissingHandling(DateTimeFunction::exprMinute), INTEGER, DATETIME)
    );
  }

  private FunctionResolver month() {
    return define(
        MONTH.getName(), impl(nullMissingHandling(DateTimeFunction::exprMonth), INTEGER, DATE));
  }

  private FunctionResolver monthName() {
    return define(MONTHNAME.getName(), impl(nullMissingHandling(DateTimeFunction::exprMonthName),
        STRING, DATE));
  }

  private FunctionResolver periodAdd() {
    return define(PERIOD_ADD.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprPeriodAdd), INTEGER, LONG, LONG));
  }

  private FunctionResolver periodDiff() {
    return define(PERIOD_DIFF.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprPeriodDiff), INTEGER, LONG, LONG));
  }

  private FunctionResolver quarter() {
    return define(
        QUARTER.getName(), impl(nullMissingHandling(DateTimeFunction::exprQuarter), INTEGER, DATE));
  }

  private FunctionResolver second() {
    return define(
        SECOND.getName(), impl(nullMissingHandling(DateTimeFunction::exprSecond), INTEGER, TIME));
  }

  private FunctionResolver secToTime() {
    return define(SEC_TO_TIME.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprSecToTime), TIME, LONG));
  }

  private FunctionResolver strToDate() {
    return define(STR_TO_DATE.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprStrToDate), DATE, STRING, STRING));
  }

  private FunctionResolver subTime() {
    return define(SUBTIME.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprSubTime), TIME, TIME, TIME),
        impl(nullMissingHandling(DateTimeFunction::exprSubTime), DATETIME, TIME, DATETIME));
  }

  private FunctionResolver sysDate() {
    return define(SYSDATE.getName(), impl(DateTimeFunction::exprSysDate, DATETIME));
  }

  private FunctionResolver time() {
    return define(BuiltinFunctionName.TIME.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprTime), TIME, TIME),
        impl(nullMissingHandling(DateTimeFunction::exprTime), TIME, DATETIME));
  }

  private FunctionResolver timeDiff() {
    return define(TIMEDIFF.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprTimeDiff), TIME, TIME, TIME),
        impl(nullMissingHandling(DateTimeFunction::exprTimeDiff), TIME, DATETIME, DATETIME));
  }

  private FunctionResolver timestamp() {
    return define(BuiltinFunctionName.TIMESTAMP.getName(),
        impl(nullMissingHandling(
            (SerializableFunction<ExprValue, ExprValue>) DateTimeFunction::exprTimestamp),
            DATETIME, DATE),
        impl(nullMissingHandling(
            (SerializableFunction<ExprValue, ExprValue>) DateTimeFunction::exprTimestamp),
            DATETIME, DATETIME),
        impl(nullMissingHandling((SerializableBiFunction<ExprValue, ExprValue, ExprValue>)
                DateTimeFunction::exprTimestamp), DATETIME, DATE, TIME),
        impl(nullMissingHandling((SerializableBiFunction<ExprValue, ExprValue, ExprValue>)
            DateTimeFunction::exprTimestamp), DATETIME, DATETIME, TIME));
  }

  private FunctionResolver timeFormat() {
    return define(TIME_FORMAT.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprTimeFormat), STRING, TIME, STRING));
  }

  private FunctionResolver timeToSec() {
    return define(TIME_TO_SEC.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprTimeToSec), INTEGER, TIME));
  }

  private FunctionResolver toDays() {
    return define(
        TO_DAYS.getName(), impl(nullMissingHandling(DateTimeFunction::exprToDays), LONG, DATE));
  }

  private FunctionResolver toSeconds() {
    return define(TO_SECONDS.getName(),
        impl(nullMissingHandling(DateTimeFunction::exprToSeconds), LONG, DATETIME),
        impl(nullMissingHandling(DateTimeFunction::exprToSeconds), LONG, DATE));
  }

  private FunctionResolver unixTimestamp() {
    return define(UNIX_TIMESTAMP.getName(), impl(DateTimeFunction::exprUnixTimestamp, TIMESTAMP),
        impl(nullMissingHandling((SerializableFunction<ExprValue, ExprValue>)
            DateTimeFunction::exprUnixTimestamp), TIMESTAMP, TIME));
  }

  private FunctionResolver utcDate() {
    return define(UTC_DATE.getName(), impl(DateTimeFunction::exprUtcDate, LONG));
  }

  private FunctionResolver utcTime() {
    return define(UTC_TIME.getName(), impl(DateTimeFunction::exprUtcTime, LONG));
  }

  private FunctionResolver utcTimestamp() {
    return define(UTC_TIMESTAMP.getName(), impl(DateTimeFunction::exprUtcTimestamp, LONG));
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
    return new ExprDatetimeValue(getDatetimeValue(dateTime).withZoneSameInstant(from)
        .withZoneSameInstant(to).toLocalDateTime());
  }

  private ExprValue exprCurdate() {
    return new ExprDateValue(ZonedDateTime.now().toLocalDate());
  }

  private ExprValue exprCurtime() {
    return new ExprTimeValue(ZonedDateTime.now().toLocalTime());
  }

  private ExprValue exprNow() {
    return new ExprTimestampValue(Instant.now());
  }

  private ExprValue exprDate(ExprValue exprValue) {
    return new ExprDateValue(getDateValue(exprValue).toLocalDate());
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

  private ExprValue exprDayName(ExprValue date) {
    return new ExprStringValue(getDateValue(date).getMonth().name());
  }

  private ExprValue exprDayOfWeek(ExprValue date) {
    return new ExprStringValue(getDateValue(date).getDayOfWeek().name());
  }

  private ExprValue exprDayOfYear(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).getDayOfYear());
  }

  private ExprValue exprFromDays(ExprValue days) {
    final int DAYS_PER_CYCLE = 146097;
    final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);
    return new ExprDateValue(
        LocalDate.ofEpochDay(getLongValue(days) - DAYS_0000_TO_1970).toString());
  }

  private ExprValue exprFromUnixTime(ExprValue unixTimestamp) {
    return new ExprStringValue(Instant.ofEpochSecond(getLongValue(unixTimestamp)).toString());
  }

  private ExprValue exprFromUnixTime(ExprValue unixTimestamp, ExprValue format) {
    Instant timestamp = Instant.ofEpochSecond(getLongValue(unixTimestamp));
    return new ExprStringValue(
        DateTimeFormatter.ofPattern(getStringValue(format)).format(timestamp));
  }

  private ImmutableTable<ExprType, String, String> formatTable =
      new ImmutableTable.Builder<ExprType, String, String>()
          .put(DATE, "USA", "%m.%d.%Y")
          .put(DATE, "JIS", "%Y-%m-%d")
          .put(DATE, "ISO", "%Y-%m-%d")
          .put(DATE, "EUR", "%d.%m.%Y")
          .put(DATE, "INTERNAL", "%Y%m%d")
          .put(DATETIME, "USA", "%Y-%m-%d %H.%i.%s")
          .put(DATETIME, "JIS", "%Y-%m-%d %H:%i:%s")
          .put(DATETIME, "ISO", "%Y-%m-%d %H:%i:%s")
          .put(DATETIME, "EUR", "%Y-%m-%d %H.%i.%s")
          .put(DATETIME, "INTERNAL", "%Y%m%d%H%i%s")
          .put(TIMESTAMP, "USA", "%Y-%m-%d %H.%i.%s")
          .put(TIMESTAMP, "JIS", "%Y-%m-%d %H:%i:%s")
          .put(TIMESTAMP, "ISO", "%Y-%m-%d %H:%i:%s")
          .put(TIMESTAMP, "EUR", "%Y-%m-%d %H.%i.%s")
          .put(TIMESTAMP, "INTERNAL", "%Y%m%d%H%i%s")
          .put(TIME, "USA", "%H.%i.%s")
          .put(TIME, "JIS", "%H:%i:%s")
          .put(TIME, "ISO", "%H:%i:%s")
          .put(TIME, "EUR", "%H.%i.%s")
          .put(TIME, "INTERNAL", "%H%i%s")
          .build();

  private ExprValue exprGetFormat(ExprValue datetime, ExprValue standard) {
    String pattern = formatTable.get(datetime.type(), getStringValue(standard).toUpperCase());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    if (datetime instanceof ExprDateValue) {
      return new ExprStringValue(formatter.format(getDateValue(datetime)));
    } else if (datetime instanceof ExprTimeValue) {
      return new ExprStringValue(formatter.format(getTimeValue(datetime)));
    } else if (datetime instanceof ExprDatetimeValue || datetime instanceof ExprTimestampValue) {
      return new ExprStringValue(formatter.format(getDatetimeValue(datetime)));
    } else {
      throw new ExpressionEvaluationException(
          String.format("Invalid call of get_format function on %s type", datetime.type()));
    }
  }

  private ExprValue exprHour(ExprValue time) {
    return new ExprIntegerValue(getTimeValue(time).getHour());
  }

  private ExprValue exprLastDay(ExprValue date) {
    LocalDate localDate = getDateValue(date).toLocalDate();
    return new ExprDateValue(
        localDate.withDayOfMonth(localDate.getMonth().length(localDate.isLeapYear())));
  }

  private ExprValue exprMakeDate(ExprValue year, ExprValue dayOfYear) {
    return new ExprDateValue(
        LocalDate.ofYearDay(getIntegerValue(year), getIntegerValue(dayOfYear)));
  }

  private ExprValue exprMakeTime(ExprValue hour, ExprValue minute, ExprValue second) {
    int sec = getDoubleValue(second).intValue();
    int nanoSec = (int) ((getDoubleValue(second) - sec) * 10e9);
    return new ExprTimeValue(
        LocalTime.of(getIntegerValue(hour), getIntegerValue(minute), sec, nanoSec));
  }

  private ExprValue exprMicroSecond(ExprValue time) {
    return new ExprIntegerValue(getTimeValue(time).getNano() * 10e-3);
  }

  private ExprValue exprMinute(ExprValue time) {
    return new ExprIntegerValue(getTimeValue(time).getMinute());
  }

  private ExprValue exprMonth(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).getMonthValue());
  }

  private ExprValue exprMonthName(ExprValue date) {
    return new ExprStringValue(getDateValue(date).getMonth().name());
  }

  private ExprValue exprPeriodAdd(ExprValue period, ExprValue months) {
    Period addedPeriod = toPeriod(getIntegerValue(period)).plusMonths(getIntegerValue(months));
    return new ExprIntegerValue(fromPeriod(addedPeriod));
  }

  private ExprValue exprPeriodDiff(ExprValue period1, ExprValue period2) {
    Period p1 = toPeriod(getIntegerValue(period1));
    Period p2 = toPeriod(getIntegerValue(period2));
    return new ExprIntegerValue(
        (p1.getYears() - p2.getYears()) * 12 + (p1.getMonths() - p2.getMonths()));
  }

  private ExprValue exprQuarter(ExprValue date) {
    return new ExprIntegerValue(getQuarter(getDateValue(date).toLocalDate()));
  }

  private ExprValue exprSecond(ExprValue time) {
    return new ExprIntegerValue(getTimeValue(time).getSecond());
  }

  private ExprValue exprSecToTime(ExprValue seconds) {
    return new ExprTimeValue(LocalTime.ofSecondOfDay(getIntegerValue(seconds)));
  }

  private ExprValue exprStrToDate(ExprValue str, ExprValue format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getStringValue(format));
    return new ExprDateValue(LocalDate.parse(getStringValue(str), formatter));
  }

  private ExprValue exprSubTime(ExprValue time1, ExprValue time2) {
    LocalTime t2 = getTimeValue(time2);
    LocalDateTime subTime = getDatetimeValue(time1)
        .minusSeconds(t2.getSecond()).minusMinutes(t2.getSecond()).minusHours(t2.getHour())
        .toLocalDateTime();
    if (time1 instanceof ExprTimeValue) {
      return new ExprTimeValue(subTime.toLocalTime());
    }
    return new ExprDatetimeValue(subTime);
  }

  private ExprValue exprSysDate() {
    return new ExprDatetimeValue(LocalDateTime.now());
  }

  private ExprValue exprTime(ExprValue value) {
    return new ExprTimeValue(getTimeValue(value));
  }

  private ExprValue exprTimeDiff(ExprValue time1, ExprValue time2) {
    if (time1 instanceof ExprTimeValue) {
      return new ExprTimeValue(
          Duration.between(getTimeValue(time1), getTimeValue(time2)).toString());
    } else {
      return new ExprTimeValue(
          Duration.between(getDatetimeValue(time1), getDatetimeValue(time2)).toString());
    }
  }

  private ExprValue exprTimestamp(ExprValue value) {
    LocalDateTime dateTime;
    if (value instanceof ExprDateValue) {
      dateTime = getDateValue(value).toLocalDate().atStartOfDay();
    } else {
      dateTime = getDatetimeValue(value).toLocalDateTime();
    }
    return new ExprDatetimeValue(dateTime);
  }

  private ExprValue exprTimestamp(ExprValue v1, ExprValue v2) {
    LocalDateTime dateTime;
    LocalTime timeToAdd = getTimeValue(v2);
    if (v1 instanceof ExprDateValue) {
      dateTime = getDateValue(v1).toLocalDate().atStartOfDay();
    } else {
      dateTime = getDatetimeValue(v1).toLocalDateTime();
    }
    return new ExprDatetimeValue(dateTime.plusSeconds(timeToAdd.getSecond())
        .plusMinutes(timeToAdd.getMinute()).plusHours(timeToAdd.getHour()));
  }

  private ExprValue exprTimeFormat(ExprValue time, ExprValue format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getStringValue(format));
    return new ExprStringValue(formatter.format(getTimeValue(time)));
  }

  private ExprValue exprTimeToSec(ExprValue time) {
    return new ExprIntegerValue(getTimeValue(time).toSecondOfDay());
  }

  private ExprValue exprToDays(ExprValue date) {
    return new ExprLongValue(getDateValue(date).toLocalDate().toEpochDay());
  }

  private ExprValue exprToSeconds(ExprValue dateTime) {
    if (dateTime instanceof ExprDatetimeValue) {
      return new ExprLongValue(getDatetimeValue(dateTime).toEpochSecond());
    } else if (dateTime instanceof ExprDateValue) {
      return new ExprLongValue(getDateValue(dateTime).toEpochSecond());
    }
    return ExprNullValue.of();
  }

  private ExprValue exprUnixTimestamp() {
    return new ExprLongValue(Instant.now().getEpochSecond());
  }

  private ExprValue exprUnixTimestamp(ExprValue time) {
    if (time instanceof ExprDateValue) {
      return new ExprLongValue(getDateValue(time).toEpochSecond());
    } else if (time instanceof ExprDatetimeValue || time instanceof ExprTimestampValue) {
      return new ExprLongValue(getDatetimeValue(time).toEpochSecond());
    } else if (time instanceof ExprIntegerValue) {
      return new ExprLongValue(toUnixTime(getIntegerValue(time)).toEpochSecond());
    } else if (time instanceof ExprDoubleValue) {
      return new ExprDoubleValue(toUnixTime(getDoubleValue(time)).toEpochSecond());
    } else {
      throw new ExpressionEvaluationException(
          String.format("invalid call function unix_timestamp on [%s] type", time.type()));
    }
  }

  private ExprValue exprUtcDate() {
    return new ExprDateValue(LocalDate.now());
  }

  private ExprValue exprUtcTime() {
    return new ExprTimeValue(LocalTime.now());
  }

  private ExprValue exprUtcTimestamp() {
    return new ExprTimestampValue(Instant.now());
  }

  private ExprValue exprWeek(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
  }

  private ExprValue exprWeekDay(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).getDayOfWeek().getValue());
  }

  private ExprValue exprWeekOfYear(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
  }

  private ExprValue exprYear(ExprValue date) {
    return new ExprIntegerValue(getDateValue(date).getYear());
  }
}
