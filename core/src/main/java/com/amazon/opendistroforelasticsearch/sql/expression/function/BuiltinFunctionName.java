package com.amazon.opendistroforelasticsearch.sql.expression.function;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Builtin Function Name.
 */
@Getter
@RequiredArgsConstructor
public enum BuiltinFunctionName {
  /**
   * Mathematical Functions.
   */
  ABS(FunctionName.of("abs")),
  CEIL(FunctionName.of("ceil")),
  CEILING(FunctionName.of("ceiling")),
  CONV(FunctionName.of("conv")),
  CRC32(FunctionName.of("crc32")),
  E(FunctionName.of("e")),
  EXP(FunctionName.of("exp")),
  FLOOR(FunctionName.of("floor")),
  LN(FunctionName.of("ln")),
  LOG(FunctionName.of("log")),
  LOG10(FunctionName.of("log10")),
  LOG2(FunctionName.of("log2")),
  MOD(FunctionName.of("mod")),
  PI(FunctionName.of("pi")),
  POW(FunctionName.of("pow")),
  POWER(FunctionName.of("power")),
  RAND(FunctionName.of("rand")),
  ROUND(FunctionName.of("round")),
  SIGN(FunctionName.of("sign")),
  SQRT(FunctionName.of("sqrt")),
  TRUNCATE(FunctionName.of("truncate")),

  ACOS(FunctionName.of("acos")),
  ASIN(FunctionName.of("asin")),
  ATAN(FunctionName.of("atan")),
  ATAN2(FunctionName.of("atan2")),
  COS(FunctionName.of("cos")),
  COT(FunctionName.of("cot")),
  DEGREES(FunctionName.of("degrees")),
  RADIANS(FunctionName.of("radians")),
  SIN(FunctionName.of("sin")),
  TAN(FunctionName.of("tan")),

  /**
   * Date and Time Functions.
   */
  ADDDATE(FunctionName.of("adddate")),
  ADDTIME(FunctionName.of("addtime")),
  CONVERT_TZ(FunctionName.of("convert_tz")),
  CURDATE(FunctionName.of("curdate")),
  CURRENT_DATE(FunctionName.of("current_date")),
  CURRENT_TIME(FunctionName.of("current_time")),
  CURRENT_TIMESTAMP(FunctionName.of("current_timestamp")),
  CURTIME(FunctionName.of("curtime")),
  DATE(FunctionName.of("date")),
  DATE_ADD(FunctionName.of("date_add")),
  DATE_FORMAT(FunctionName.of("date_format")),
  DATE_SUB(FunctionName.of("date_sub")),
  DATEDIFF(FunctionName.of("datediff")),
  DAY(FunctionName.of("day")),
  DAYNAME(FunctionName.of("dayname")),
  DAYOFMONTH(FunctionName.of("dayofmonth")),
  DAYOFWEEK(FunctionName.of("dayoffweek")),
  DAYOFYEAR(FunctionName.of("dayofyear")),
  FROM_DAYS(FunctionName.of("from_days")),
  FROM_UNIXTIME(FunctionName.of("from_unixtime")),
  GET_FORMAT(FunctionName.of("get_format")),
  HOUR(FunctionName.of("hour")),
  LAST_DAY(FunctionName.of("last_day")),
  LOCALTIME(FunctionName.of("localtime")),
  LOCALTIMESTAMP(FunctionName.of("localtimestamp")),
  MAKEDATE(FunctionName.of("makedate")),
  MAKETIME(FunctionName.of("maketime")),
  MICROSECOND(FunctionName.of("microsecond")),
  MINUTE(FunctionName.of("minute")),
  MONTH(FunctionName.of("month")),
  MONTHNAME(FunctionName.of("monthname")),
  NOW(FunctionName.of("now")),
  PERIOD_ADD(FunctionName.of("period_add")),
  PERIOD_DIFF(FunctionName.of("period_diff")),
  QUARTER(FunctionName.of("quarter")),
  SECOND(FunctionName.of("second")),
  SEC_TO_TIME(FunctionName.of("sec_to_time")),
  STR_TO_DATE(FunctionName.of("str_to_date")),
  SUBDATE(FunctionName.of("sub_date")),
  SUBTIME(FunctionName.of("sub_time")),
  SYSDATE(FunctionName.of("sysdate")),
  TIME(FunctionName.of("time")),
  TIMEDIFF(FunctionName.of("timediff")),
  TIMESTAMP(FunctionName.of("timestamp")),
  TIMESTAMPADD(FunctionName.of("timestampadd")),
  TIMESTAMPDIFF(FunctionName.of("timestampdiff")),
  TIME_FORMAT(FunctionName.of("time_format")),
  TIME_TO_SEC(FunctionName.of("time_to_sec")),
  TO_DAYS(FunctionName.of("to_days")),
  TO_SECONDS(FunctionName.of("to_seconds")),
  UNIX_TIMESTAMP(FunctionName.of("unix_timestamp")),
  UTC_DATE(FunctionName.of("utc_date")),
  UTC_TIME(FunctionName.of("utc_time")),
  UTC_TIMESTAMP(FunctionName.of("utc_timestamp")),
  WEEK(FunctionName.of("week")),
  WEEKDAY(FunctionName.of("weekday")),
  WEEKOFYEAR(FunctionName.of("weekofyear")),
  YEAR(FunctionName.of("year")),
  YEARWEEK(FunctionName.of("yearweek")),


  /**
   * Text Functions.
   */
  TOSTRING(FunctionName.of("tostring")),

  /**
   * Arithmetic Operators.
   */
  ADD(FunctionName.of("+")),
  SUBTRACT(FunctionName.of("-")),
  MULTIPLY(FunctionName.of("*")),
  DIVIDE(FunctionName.of("/")),
  MODULES(FunctionName.of("%")),

  /**
   * Boolean Operators.
   */
  AND(FunctionName.of("and")),
  OR(FunctionName.of("or")),
  XOR(FunctionName.of("xor")),
  NOT(FunctionName.of("not")),
  EQUAL(FunctionName.of("=")),
  NOTEQUAL(FunctionName.of("!=")),
  LESS(FunctionName.of("<")),
  LTE(FunctionName.of("<=")),
  GREATER(FunctionName.of(">")),
  GTE(FunctionName.of(">=")),
  LIKE(FunctionName.of("like")),

  /**
   * Aggregation Function.
   */
  AVG(FunctionName.of("avg")),
  SUM(FunctionName.of("sum")),
  COUNT(FunctionName.of("count"));

  private final FunctionName name;

  private static final Map<FunctionName, BuiltinFunctionName> ALL_NATIVE_FUNCTIONS;

  static {
    ImmutableMap.Builder<FunctionName, BuiltinFunctionName> builder = new ImmutableMap.Builder<>();
    for (BuiltinFunctionName func : BuiltinFunctionName.values()) {
      builder.put(func.getName(), func);
    }
    ALL_NATIVE_FUNCTIONS = builder.build();
  }

  public static Optional<BuiltinFunctionName> of(String str) {
    return Optional.ofNullable(ALL_NATIVE_FUNCTIONS.getOrDefault(FunctionName.of(str), null));
  }
}
