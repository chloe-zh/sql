/*
 *   Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package com.amazon.opendistroforelasticsearch.sql.expression.operator.arthmetic;

import static com.amazon.opendistroforelasticsearch.sql.config.TestConfig.DOUBLE_TYPE_MISSING_VALUE_FIELD;
import static com.amazon.opendistroforelasticsearch.sql.config.TestConfig.DOUBLE_TYPE_NULL_VALUE_FIELD;
import static com.amazon.opendistroforelasticsearch.sql.config.TestConfig.INT_TYPE_MISSING_VALUE_FIELD;
import static com.amazon.opendistroforelasticsearch.sql.config.TestConfig.INT_TYPE_NULL_VALUE_FIELD;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getDoubleValue;
import static com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils.getLongValue;
import static com.amazon.opendistroforelasticsearch.sql.utils.MatcherUtils.hasType;
import static com.amazon.opendistroforelasticsearch.sql.utils.MatcherUtils.hasValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.amazon.opendistroforelasticsearch.sql.data.model.ExprType;
import com.amazon.opendistroforelasticsearch.sql.expression.DSL;
import com.amazon.opendistroforelasticsearch.sql.expression.ExpressionTestBase;
import com.amazon.opendistroforelasticsearch.sql.expression.FunctionExpression;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

public class MathematicalFunctionTest extends ExpressionTestBase {
  private static Stream<Arguments> testLogArguments() {
    List<List<Double>> arguments = Arrays.asList(
        Arrays.asList(2D, 2D), Arrays.asList(3D, 9D)
    );
    Stream.Builder<Arguments> builder = Stream.builder();
    for (List<Double> argPair : arguments) {
      builder.add(Arguments.of(argPair.get(0), argPair.get(1)));
    }
    return builder.build();
  }

  private static Stream<Arguments> testPowArguments() {
    List<List<Double>> arguments = Arrays.asList(
        Arrays.asList(2D, 2D), Arrays.asList(-2D, 2D)
    );
    Stream.Builder<Arguments> builder = Stream.builder();
    for (List<Double> argPair : arguments) {
      builder.add(Arguments.of(argPair.get(0), argPair.get(1)));
    }
    return builder.build();
  }

  /**
   * Test abs with integer value.
   */
  @ParameterizedTest(name = "abs({0})")
  @ValueSource(ints = {-2, 2})
  public void abs_int_value(Integer value) {
    FunctionExpression abs = dsl.abs(typeEnv, DSL.literal(value));
    assertThat(
        abs.valueOf(valueEnv()),
        allOf(hasType(ExprType.INTEGER), hasValue(Math.abs(value))));
    assertEquals(String.format("abs(%s)", value.toString()), abs.toString());
  }

  /**
   * Test abs with long value.
   */
  @ParameterizedTest(name = "abs({0})")
  @ValueSource(longs = {-2L, 2L})
  public void abs_long_value(Long value) {
    FunctionExpression abs = dsl.abs(typeEnv, DSL.literal(value));
    assertThat(
        abs.valueOf(valueEnv()),
        allOf(hasType(ExprType.LONG), hasValue(Math.abs(value))));
    assertEquals(String.format("abs(%s)", value.toString()), abs.toString());
  }

  /**
   * Test abs with float value.
   */
  @ParameterizedTest(name = "abs({0})")
  @ValueSource(floats = {-2f, 2f})
  public void abs_float_value(Float value) {
    FunctionExpression abs = dsl.abs(typeEnv, DSL.literal(value));
    assertThat(
        abs.valueOf(valueEnv()),
        allOf(hasType(ExprType.FLOAT), hasValue(Math.abs(value))));
    assertEquals(String.format("abs(%s)", value.toString()), abs.toString());
  }

  /**
   * Test abs with double value.
   */
  @ParameterizedTest(name = "abs({0})")
  @ValueSource(doubles = {-2L, 2L})
  public void abs_double_value(Double value) {
    FunctionExpression abs = dsl.abs(typeEnv, DSL.literal(value));
    assertThat(
        abs.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.abs(value))));
    assertEquals(String.format("abs(%s)", value.toString()), abs.toString());
  }

  @Test
  public void abs_null_value() {
    assertTrue(dsl.abs(typeEnv, DSL.ref(INT_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void abs_missing_value() {
    assertTrue(
        dsl.abs(typeEnv, DSL.ref(INT_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test ceil/ceiling with double value.
   */
  @ParameterizedTest(name = "ceil({0})")
  @ValueSource(doubles = {-2L, 2L})
  public void ceil_double_value(Double value) {
    FunctionExpression ceil = dsl.ceil(typeEnv, DSL.literal(value));
    assertThat(
        ceil.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.ceil(value))));
    assertEquals(String.format("ceil(%s)", value.toString()), ceil.toString());

    FunctionExpression ceiling = dsl.ceiling(typeEnv, DSL.literal(value));
    assertThat(
        ceiling.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.ceil(value))));
    assertEquals(String.format("ceiling(%s)", value.toString()), ceiling.toString());
  }

  @Test
  public void ceil_null_value() {
    assertTrue(
        dsl.ceil(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
    assertTrue(
        dsl.ceiling(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void ceil_missing_value() {
    assertTrue(dsl.ceil(
        typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
    assertTrue(dsl.ceiling(
        typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test exp with double value.
   */
  @ParameterizedTest(name = "exp({0})")
  @ValueSource(doubles = {-2L, 2L})
  public void exp_double_value(Double value) {
    FunctionExpression exp = dsl.exp(typeEnv, DSL.literal(value));
    assertThat(
        exp.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.exp(value))));
    assertEquals(String.format("exp(%s)", value.toString()), exp.toString());
  }

  @Test
  public void exp_null_value() {
    assertTrue(
        dsl.exp(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void exp_missing_value() {
    assertTrue(
        dsl.exp(typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test floor with double value.
   */
  @ParameterizedTest(name = "floor({0})")
  @ValueSource(doubles = {-2D, 2D})
  public void floor_double_value(Double value) {
    FunctionExpression floor = dsl.floor(typeEnv, DSL.literal(value));
    assertThat(
        floor.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.floor(value))));
    assertEquals(String.format("floor(%s)", value.toString()), floor.toString());
  }

  @Test
  public void floor_null_value() {
    assertTrue(
        dsl.floor(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void floor_missing_value() {
    assertTrue(dsl.floor(
        typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test ln with double value.
   */
  @ParameterizedTest(name = "ln({0})")
  @ValueSource(doubles = {2D})
  public void ln_double_value(Double value) {
    FunctionExpression ln = dsl.ln(typeEnv, DSL.literal(value));
    assertThat(
        ln.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.log(value))));
    assertEquals(String.format("ln(%s)", value.toString()), ln.toString());
  }

  @Test
  public void ln_null_value() {
    assertTrue(dsl.ln(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void ln_missing_value() {
    assertTrue(
        dsl.ln(typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test log with double value.
   */
  @ParameterizedTest(name = "log({0}, {1})")
  @MethodSource("testLogArguments")
  public void log_double_value(Double v1, Double v2) {
    FunctionExpression log = dsl.log(typeEnv, DSL.literal(v1), DSL.literal(v2));
    assertThat(
        log.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.log(v2) / Math.log(v1))));
    assertEquals(String.format("log(%s, %s)", v1.toString(), v2.toString()), log.toString());
  }

  @Test
  public void log_null_value() {
    assertTrue(dsl.log(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD),
        DSL.literal(2D)).valueOf(valueEnv()).isNull());
    assertTrue(dsl.log(typeEnv,
        DSL.literal(2D),
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
    assertTrue(dsl.log(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void log_missing_value() {
    assertTrue(dsl.log(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD),
        DSL.literal(2D)).valueOf(valueEnv()).isMissing());
    assertTrue(dsl.log(typeEnv,
        DSL.literal(2D),
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
    assertTrue(dsl.log(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  @Test
  public void log_null_missing() {
    assertTrue(dsl.log(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
    assertTrue(dsl.log(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test ln with double value.
   */
  @Test
  public void pi_double_value() {
    FunctionExpression pi = dsl.pi(typeEnv);
    assertThat(
        pi.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE),
            hasValue(new BigDecimal(Math.PI).setScale(10, RoundingMode.HALF_UP).doubleValue())));
    assertEquals("pi()", pi.toString());
  }

  /**
   * Test pow/power with double value.
   */
  @ParameterizedTest(name = "log({0}, {1})")
  @MethodSource("testPowArguments")
  public void pow_double_value(Double v1, Double v2) {
    FunctionExpression pow = dsl.pow(typeEnv, DSL.literal(v1), DSL.literal(v2));
    assertThat(
        pow.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.pow(v1, v2))));
    assertEquals(String.format("pow(%s, %s)", v1.toString(), v2.toString()), pow.toString());

    FunctionExpression power = dsl.power(typeEnv, DSL.literal(v1), DSL.literal(v2));
    assertThat(
        power.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.pow(v1, v2))));
    assertEquals(String.format("power(%s, %s)", v1.toString(), v2.toString()), power.toString());
  }

  @Test
  public void pow_null_value() {
    assertTrue(dsl.pow(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD),
        DSL.literal(2D)).valueOf(valueEnv()).isNull());

    assertTrue(dsl.pow(typeEnv,
        DSL.literal(2D),
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());

    assertTrue(dsl.pow(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void pow_missing_value() {
    assertTrue(dsl.pow(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD),
        DSL.literal(2D)).valueOf(valueEnv()).isMissing());

    assertTrue(dsl.pow(typeEnv,
        DSL.literal(2D),
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());

    assertTrue(dsl.pow(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  @Test
  public void pow_null_missing() {
    assertTrue(dsl.pow(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());

    assertTrue(dsl.pow(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD),
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test round with {float} and {float, integer} values.
   */
  @ParameterizedTest(name = "round({0})")
  @ValueSource(floats = {2F, 2.1F})
  public void round_float_value(Float value) {
    FunctionExpression round = dsl.round(typeEnv, DSL.literal(value));
    assertEquals(ExprType.INTEGER, round.type(typeEnv()));
    assertEquals(Math.round(value), getLongValue(round.valueOf(valueEnv())));
    assertEquals(String.format("round(%s)", value.toString()), round.toString());

    round = dsl.round(typeEnv, DSL.literal(value), DSL.literal(1));
    assertEquals(ExprType.FLOAT, round.type(typeEnv()));
    assertEquals(new BigDecimal(value).setScale(1, RoundingMode.HALF_UP).floatValue(),
        getDoubleValue(round.valueOf(valueEnv())));
  }

  /**
   * Test round with {double} and {double, integer} values.
   */
  @ParameterizedTest(name = "round({0})")
  @ValueSource(doubles = {2D, 2.1D})
  public void round_double_value(Double value) {
    FunctionExpression round = dsl.round(typeEnv, DSL.literal(value));
    assertEquals(ExprType.LONG, round.type(typeEnv()));
    assertEquals(Math.round(value), getLongValue(round.valueOf(valueEnv())));
    assertEquals(String.format("round(%s)", value.toString()), round.toString());

    round = dsl.round(typeEnv, DSL.literal(value), DSL.literal(1));
    assertEquals(ExprType.DOUBLE, round.type(typeEnv()));
    assertEquals(new BigDecimal(value).setScale(1, RoundingMode.HALF_UP).doubleValue(),
        getDoubleValue(round.valueOf(valueEnv())));
  }

  @Test
  public void round_null_value() {
    assertTrue(
        dsl.round(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());

    assertTrue(dsl.round(typeEnv,
        DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD), DSL.literal(1)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void round_missing_value() {
    assertTrue(dsl.round(
        typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());

    assertTrue(dsl.round(typeEnv,
        DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD), DSL.literal(1)).valueOf(valueEnv()).isMissing());
  }

  /**
   * Test sqrt with double value.
   */
  @ParameterizedTest(name = "ln({0})")
  @ValueSource(doubles = {2D})
  public void sqrt_double_value(Double value) {
    FunctionExpression sqrt = dsl.sqrt(typeEnv, DSL.literal(value));
    assertThat(
        sqrt.valueOf(valueEnv()),
        allOf(hasType(ExprType.DOUBLE), hasValue(Math.sqrt(value))));
    assertEquals(String.format("sqrt(%s)", value.toString()), sqrt.toString());
  }

  @Test
  public void sqrt_null_value() {
    assertTrue(
        dsl.sqrt(typeEnv, DSL.ref(DOUBLE_TYPE_NULL_VALUE_FIELD)).valueOf(valueEnv()).isNull());
  }

  @Test
  public void sqrt_missing_value() {
    assertTrue(dsl.sqrt(
        typeEnv, DSL.ref(DOUBLE_TYPE_MISSING_VALUE_FIELD)).valueOf(valueEnv()).isMissing());
  }
}
