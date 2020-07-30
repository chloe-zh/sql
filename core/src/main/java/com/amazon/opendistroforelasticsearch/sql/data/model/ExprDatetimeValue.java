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

package com.amazon.opendistroforelasticsearch.sql.data.model;

import com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType;
import com.amazon.opendistroforelasticsearch.sql.data.type.ExprType;
import com.amazon.opendistroforelasticsearch.sql.exception.SemanticCheckException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExprDatetimeValue extends AbstractExprValue {
  /**
   * todo. only support UTC now.
   */
  private static final ZoneId ZONE = ZoneId.of("UTC");
  private LocalDateTime dateTime;

  /**
   * Constructor of ExprDateValue.
   */
  public ExprDatetimeValue(String dateTime) {
    try {
      LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
      this.dateTime = localDateTime.atZone(ZONE).toLocalDateTime();
    } catch (DateTimeParseException e) {
      throw new SemanticCheckException(String.format("date:%s in unsupported format, please use "
          + "yyyy-MM-dd HH:mm:ss", dateTime));
    }
  }

  @Override
  public ZonedDateTime datetimeValue() {
    return dateTime.atZone(ZONE);
  }

  @Override
  public LocalTime timeValue() {
    return dateTime.toLocalTime();
  }

  @Override
  public ZonedDateTime dateValue() {
    return dateTime.toLocalDate().atStartOfDay(ZONE);
  }

  @Override
  public int compare(ExprValue other) {
    return dateTime.atZone(ZONE).compareTo(other.datetimeValue());
  }

  @Override
  public boolean equal(ExprValue other) {
    return dateTime.atZone(ZONE).equals(other.datetimeValue());
  }

  @Override
  public Object value() {
    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZONE).format(dateTime);
  }

  @Override
  public ExprType type() {
    return ExprCoreType.DATETIME;
  }
}
