/*
 *   Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package com.amazon.opendistroforelasticsearch.sql.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OperatorUtils {
  /**
   * Wildcard pattern matcher util.
   * Percent (%) character for wildcard,
   * Underscore (_) character for a single character match.
   * @param pattern string pattern to match.
   * @return if text matches pattern returns true; else return false.
   */
  public static boolean matches(String pattern, String text) {
    return Pattern.compile(patternToRegex(pattern)).matcher(text).matches();
  }

  /**
   * Util method to convert a period meaning number to {@link Period}.
   * @param period a period in format yyMM or yyyyMM
   * @return a Period type value equivalent with period
   */
  public static Period toPeriod(int period) {
    int month = period % 100;
    int year = period - month > 99 ? period - month : period - month < 70 ? period - month + 2000 :
        period - month + 1900;
    return Period.of(year, month, 0);
  }

  /**
   * Util method to convert a period to a number representing this period in yyyyMM format.
   * @param period a period with year and month values
   * @return a number in yyyyMM format to represent a period
   */
  public static int fromPeriod(Period period) {
    int month = period.getMonths();
    int year = period.getYears();
    return year * 100 + month;
  }

  /**
   * Util method to get the quarter number of a given date.
   * @param date the date to get quarter number from
   * @return quarter of the given date
   */
  public static int getQuarter(LocalDate date) {
    int month = date.getMonthValue();
    return (month - 1) / 4 + 1;
  }

  /**
   * Util method to convert a number of yyMMdd, yyMMddhhmmss, yyyyMMdd, or yyyyMMddhhmmss format
   * to a {@link ZonedDateTime} instance.
   * @param time a number representing time or datetime
   * @return converted ZonedDateTime instance
   */
  public static ZonedDateTime toUnixTime(long time) {
    int year = 0;
    int month = 0;
    int day = 0;
    int hour = 0;
    int minute = 0;
    int second = 0;
    if (time < 10e7) {
      year = time / 10e4 < 70 ? (int) (time / 10e4 + 2000) : (int) (time / 10e4 + 1900);
      month = (int) (time % 10e4 / 10e2);
      day = (int) (time % 10e2);
    } else if (time < 10e9) {
      year = (int) (time / 10e4);
      month = (int) (time % 10e4 / 10e2);
      day = (int) (time % 10e2);
    } else if (time < 10e13) {
      year = time / 10e10 < 70 ? (int) (time / 10e10 + 2000) : (int) (time / 10e10 + 1900);
      month = (int) (time % 10e10 / 10e8);
      day = (int) (time % 10e8 / 10e6);
      hour = (int) (time % 10e6 / 10e4);
      minute = (int) (time % 10e4 / 10e2);
      second = (int) (time % 10e2);
    } else {
      year = (int) (time / 10e12);
      month = (int) (time % 10e10 / 10e8);
      day = (int) (time % 10e8 / 10e6);
      hour = (int) (time % 10e6 / 10e4);
      minute = (int) (time % 10e4 / 10e2);
      second = (int) (time % 10e2);
    }
    return ZonedDateTime.of(year, month, day, hour, minute, second, 0, ZoneId.systemDefault());
  }

  public static ZonedDateTime toUnixTime(Double time) {
    ZonedDateTime dateTime = toUnixTime(time.longValue());
    return dateTime.withNano((int) ((time - time.longValue()) * 10e9));
  }

  private static final char DEFAULT_ESCAPE = '\\';

  private static String patternToRegex(String patternString) {
    StringBuilder regex = new StringBuilder(patternString.length() * 2);
    regex.append('^');
    boolean escaped = false;
    for (char currentChar : patternString.toCharArray()) {
      if (!escaped && currentChar == DEFAULT_ESCAPE) {
        escaped = true;
      } else {
        switch (currentChar) {
          case '%':
            if (escaped) {
              regex.append("%");
            } else {
              regex.append(".*");
            }
            escaped = false;
            break;
          case '_':
            if (escaped) {
              regex.append("_");
            } else {
              regex.append('.');
            }
            escaped = false;
            break;
          default:
            switch (currentChar) {
              case '\\':
              case '^':
              case '$':
              case '.':
              case '*':
              case '[':
              case ']':
              case '(':
              case ')':
              case '|':
              case '+':
                regex.append('\\');
                break;
              default:
            }

            regex.append(currentChar);
            escaped = false;
        }
      }
    }
    regex.append('$');
    return regex.toString();
  }
}
