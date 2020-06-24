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

import static com.amazon.opendistroforelasticsearch.sql.expression.operator.OperatorUtils.doubleArgFunctionBuilder;
import static com.amazon.opendistroforelasticsearch.sql.expression.operator.OperatorUtils.doubleArgFunctionBuilder;
import static com.amazon.opendistroforelasticsearch.sql.expression.operator.OperatorUtils.noArgFunctionBuilder;
import static com.amazon.opendistroforelasticsearch.sql.expression.operator.OperatorUtils.singleArgFunctionBuilder;

import com.amazon.opendistroforelasticsearch.sql.data.model.ExprType;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils;
import com.amazon.opendistroforelasticsearch.sql.expression.Expression;
import com.amazon.opendistroforelasticsearch.sql.expression.FunctionExpression;
import com.amazon.opendistroforelasticsearch.sql.expression.env.Environment;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionRepository;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionBuilder;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionName;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionSignature;
import com.google.common.collect.ImmutableMap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathematicalFunction {
  /**
   * Register Mathematical Functions.
   * @param repository {@link BuiltinFunctionRepository}.
   */
  public static void register(BuiltinFunctionRepository repository) {
    repository.register(abs());
    repository.register(ceil());
    repository.register(ceiling());
    repository.register(exp());
    repository.register(floor());
    repository.register(ln());
    repository.register(log());
    repository.register(pi());
    repository.register(pow());
    repository.register(power());
    repository.register(round());
    repository.register(sqrt());
  }

  /**
   * Definition of abs(x) function.
   * Calculate absolute value of x
   * The supported signature of abs function are
   * INT -> INT
   * LONG -> LONG
   * FLOAT -> FLOAT
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver abs() {
    return new FunctionResolver(
        BuiltinFunctionName.ABS.getName(),
        singleArgumentFunction(
            BuiltinFunctionName.ABS.getName(), Math::abs, Math::abs, Math::abs, Math::abs));
  }

  /**
   * Definition of ceil(x)/ceiling(x) function.
   * Calculate the next highest integer that x rounds up to
   * The supported signature of ceil/ceiling function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver ceil() {
    return new FunctionResolver(BuiltinFunctionName.CEIL.getName(),
        singleArgumentFunction(BuiltinFunctionName.CEIL.getName(), Math::ceil));
  }

  private static FunctionResolver ceiling() {
    return new FunctionResolver(BuiltinFunctionName.CEILING.getName(),
        singleArgumentFunction(BuiltinFunctionName.CEILING.getName(), Math::ceil));
  }

  /**
   * Definition of exp(x) function.
   * Calculate exponent function e to the x
   * The supported signature of exp function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver exp() {
    return new FunctionResolver(BuiltinFunctionName.EXP.getName(),
        singleArgumentFunction(BuiltinFunctionName.EXP.getName(), Math::exp));
  }

  /**
   * Definition of floor(x) function.
   * Calculate the next nearest whole integer that x rounds down to
   * The supported signature of floor function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver floor() {
    return new FunctionResolver(BuiltinFunctionName.FLOOR.getName(),
        singleArgumentFunction(BuiltinFunctionName.FLOOR.getName(), Math::floor));
  }

  /**
   * Definition of ln(x) function.
   * Calculate the natural logarithm of x
   * The supported signature of ln function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver ln() {
    return new FunctionResolver(BuiltinFunctionName.LN.getName(),
        singleArgumentFunction(BuiltinFunctionName.LN.getName(), Math::log));
  }

  /**
   * Definition of log(x, y) function.
   * Calculate the logarithm of x using y as the base
   * The supported signature of log function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver log() {
    return new FunctionResolver(BuiltinFunctionName.LOG.getName(),
        doubleArgumentsFunction(
            BuiltinFunctionName.LOG.getName(), (v1, v2) -> Math.log(v2) / Math.log(v1)));
  }

  /**
   * Definition of pi() function.
   * Return the constant pi to 11 digits of precision
   * The supported signature of floor function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver pi() {
    FunctionName functionName = BuiltinFunctionName.PI.getName();
    return FunctionResolver.builder()
        .functionName(functionName)
        .functionBundle(new FunctionSignature(functionName, Collections.emptyList()),
            noArgFunctionBuilder(functionName,
                () -> new BigDecimal(Math.PI).setScale(10, RoundingMode.HALF_UP).doubleValue(),
                ExprType.DOUBLE))
        .build();
  }

  /**
   * Definition of pow(x, y)/power(x, y) function.
   * Calculate x to the power of x
   * The supported signature of pow/power function is
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver pow() {
    FunctionName functionName = BuiltinFunctionName.POW.getName();
    return new FunctionResolver(functionName,
        doubleArgumentsFunction(functionName, Math::pow));
  }

  private static FunctionResolver power() {
    FunctionName functionName = BuiltinFunctionName.POWER.getName();
    return new FunctionResolver(functionName,
        doubleArgumentsFunction(functionName, Math::pow));
  }

  /**
   * Definition of round(x [, y]) function.
   * Return x rounded to the amount of decimal places specified by y
   * Default round to the nearest integer
   * The supported signature of floor function is
   * FLOAT -> FLOAT
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver round() {
    return new FunctionResolver(
        BuiltinFunctionName.ROUND.getName(),
        roundFunction(BuiltinFunctionName.ROUND.getName(),
            Math::round,
            Math::round,
            (v1, v2) -> new BigDecimal(v1).setScale(v2, RoundingMode.HALF_UP).floatValue(),
            (v1, v2) -> new BigDecimal(v1).setScale(v2, RoundingMode.HALF_UP).doubleValue()
        )
    );
  }

  /**
   * Definition of sqrt(x) function.
   * Return the square root of x
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver sqrt() {
    return new FunctionResolver(BuiltinFunctionName.SQRT.getName(),
        singleArgumentFunction(BuiltinFunctionName.SQRT.getName(), Math::sqrt));
  }

  private static Map<FunctionSignature, FunctionBuilder> singleArgumentFunction(
      FunctionName functionName,
      Function<Integer, Integer> integerFunc,
      Function<Long, Long> longFunc,
      Function<Float, Float> floatFunc,
      Function<Double, Double> doubleFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    return builder
        .put(
            new FunctionSignature(
                functionName, Arrays.asList(ExprType.INTEGER)), singleArgFunctionBuilder(
                    functionName, integerFunc, ExprValueUtils::getIntegerValue, ExprType.INTEGER))
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.LONG)),
            singleArgFunctionBuilder(functionName, longFunc, ExprValueUtils::getLongValue,
                ExprType.LONG))
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.FLOAT)),
            singleArgFunctionBuilder(functionName, floatFunc, ExprValueUtils::getFloatValue,
                ExprType.FLOAT))
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.DOUBLE)),
            singleArgFunctionBuilder(
                functionName, doubleFunc, ExprValueUtils::getDoubleValue, ExprType.DOUBLE))
        .build();
  }

  private static Map<FunctionSignature, FunctionBuilder> singleArgumentFunction(
      FunctionName functionName,
      Function<Double, Double> doubleFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    return builder
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.DOUBLE)),
            singleArgFunctionBuilder(
                functionName, doubleFunc, ExprValueUtils::getDoubleValue, ExprType.DOUBLE))
        .build();
  }

  private static Map<FunctionSignature, FunctionBuilder> doubleArgumentsFunction(
      FunctionName functionName,
      BiFunction<Double, Double, Double> doubleFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    return builder
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.DOUBLE, ExprType.DOUBLE)),
            doubleArgFunctionBuilder(functionName, doubleFunc, ExprValueUtils::getDoubleValue,
                ExprValueUtils::getDoubleValue, ExprType.DOUBLE))
        .build();
  }

  private static Map<FunctionSignature, FunctionBuilder> roundFunction(
      FunctionName functionName,
      Function<Float, Integer> floatFunc,
      Function<Double, Long> doubleFunc,
      BiFunction<Float, Integer, Float> floatBiFunc,
      BiFunction<Double, Integer, Double> doubleBiFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    return builder
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.FLOAT)),
            roundFunctionBuilder(
                functionName, floatFunc, ExprValueUtils::getFloatValue, ExprType.INTEGER))
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.DOUBLE)),
            roundFunctionBuilder(
                functionName, doubleFunc, ExprValueUtils::getDoubleValue, ExprType.LONG))
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.FLOAT, ExprType.INTEGER)),
            roundFunctionBuilder(functionName,
                floatBiFunc, ExprValueUtils::getFloatValue, ExprValueUtils::getIntegerValue,
                ExprType.FLOAT))
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprType.DOUBLE, ExprType.INTEGER)),
            roundFunctionBuilder(functionName,
                doubleBiFunc, ExprValueUtils::getDoubleValue, ExprValueUtils::getIntegerValue,
                ExprType.DOUBLE))
        .build();
  }

  private static <T, R> FunctionBuilder roundFunctionBuilder(FunctionName functionName,
                                                             BiFunction<T, R, T> function,
                                                             Function<ExprValue, T> observer1,
                                                             Function<ExprValue, R> observer2,
                                                             ExprType returnType) {
    return arguments ->
        new FunctionExpression(functionName, arguments) {
          @Override
          public ExprValue valueOf(Environment<Expression, ExprValue> env) {
            ExprValue arg1 = arguments.get(0).valueOf(env);
            ExprValue arg2 = arguments.get(1).valueOf(env);
            if (arg1.isMissing()) {
              return ExprValueUtils.missingValue();
            } else if (arg1.isNull()) {
              return ExprValueUtils.nullValue();
            } else {
              return ExprValueUtils.fromObjectValue(
                  function.apply(observer1.apply(arg1), observer2.apply(arg2)));
            }
          }

          @Override
          public ExprType type(Environment<Expression, ExprType> env) {
            return returnType;
          }

          @Override
          public String toString() {
            return String.format("%s(%s)", functionName,
                arguments.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
          }
        };
  }

  private static <T, R> FunctionBuilder roundFunctionBuilder(FunctionName functionName,
                                                          Function<T, R> function,
                                                          Function<ExprValue, T> observer,
                                                          ExprType returnType) {
    return arguments ->
        new FunctionExpression(functionName, arguments) {
          @Override
          public ExprValue valueOf(Environment<Expression, ExprValue> env) {
            ExprValue arg1 = arguments.get(0).valueOf(env);
            if (arg1.isMissing()) {
              return ExprValueUtils.missingValue();
            } else if (arg1.isNull()) {
              return ExprValueUtils.nullValue();
            } else {
              return ExprValueUtils.fromObjectValue(
                  function.apply(observer.apply(arg1)));
            }
          }

          @Override
          public ExprType type(Environment<Expression, ExprType> env) {
            return returnType;
          }

          @Override
          public String toString() {
            return String.format("%s(%s)", functionName,
                arguments.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")));
          }
        };
  }

  private static <T, U, R> FunctionBuilder doubleArgFunctionBuilder(
      FunctionName functionName,
      BiFunction<T, U, R> function,
      Function<ExprValue, T> observer1,
      Function<ExprValue, U> observer2,
      ExprType returnType) {
    return arguments ->
        new FunctionExpression(functionName, arguments) {
          @Override
          public ExprValue valueOf(Environment<Expression, ExprValue> env) {
            ExprValue arg1 = arguments.get(0).valueOf(env);
            ExprValue arg2 = arguments.get(1).valueOf(env);
            if (arg1.isMissing() || arg2.isMissing()) {
              return ExprValueUtils.missingValue();
            } else if (arg1.isNull() || arg2.isNull()) {
              return ExprValueUtils.nullValue();
            } else {
              return ExprValueUtils.fromObjectValue(
                  function.apply(observer1.apply(arg1), observer2.apply(arg2)));
            }
          }

          @Override
          public ExprType type(Environment<Expression, ExprType> env) {
            return returnType;
          }

          @Override
          public String toString() {
            return String.format(
                "%s(%s, %s)",
                functionName, arguments.get(0).toString(), arguments.get(1).toString());
          }
        };
  }
}
