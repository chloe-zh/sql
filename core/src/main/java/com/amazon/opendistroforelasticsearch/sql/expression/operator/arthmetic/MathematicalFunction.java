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

import static com.amazon.opendistroforelasticsearch.sql.expression.operator.OperatorUtils.doubleArgFunc;
import static com.amazon.opendistroforelasticsearch.sql.expression.operator.OperatorUtils.unaryOperator;

import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValueUtils;
import com.amazon.opendistroforelasticsearch.sql.data.type.ExprCoreType;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionName;
import com.amazon.opendistroforelasticsearch.sql.expression.function.BuiltinFunctionRepository;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionBuilder;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionName;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionResolver;
import com.amazon.opendistroforelasticsearch.sql.expression.function.FunctionSignature;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathematicalFunction {
  /**
   * Register Mathematical Functions.
   *
   * @param repository {@link BuiltinFunctionRepository}.
   */
  public static void register(BuiltinFunctionRepository repository) {
    repository.register(abs());
    repository.register(ceil());
    repository.register(exp());
    repository.register(floor());
    repository.register(ln());
    repository.register(log());
  }

  /**
   * Definition of abs() function.
   * The supported signature of abs() function are
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
   * Definition of ceil(x) function.
   * Calculate the next highest integer that x rounds up to
   * The supported signature of ceil/ceiling function is
   * INTEGER -> INTEGER
   * LONG -> LONG
   * FLOAT -> FLOAT
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver ceil() {
    FunctionName functionName = BuiltinFunctionName.CEIL.getName();
    return new FunctionResolver(
        functionName,
        singleArgumentFunction(
            functionName,
            v -> (int) Math.ceil(v),
            v -> (long) Math.ceil(v),
            v -> (float) Math.ceil(v),
            Math::ceil
        ));
  }

  /**
   * Definition of exp(x) function.
   * Calculate exponent function e to the x
   * The supported signature of exp function is
   * INTEGER/LONG/FLOAT/DOUBLE -> DOUBLE
   */
  private static FunctionResolver exp() {
    return new FunctionResolver(BuiltinFunctionName.EXP.getName(),
        singleArgumentFunction(BuiltinFunctionName.EXP.getName(), Math::exp));
  }

  /**
   * Definition of floor(x) function.
   * Calculate the next nearest whole integer that x rounds down to
   * The supported signature of floor function is
   * INTEGER -> INTEGER
   * LONG -> LONG
   * FLOAT -> FLOAT
   * DOUBLE -> DOUBLE
   */
  private static FunctionResolver floor() {
    FunctionName functionName = BuiltinFunctionName.FLOOR.getName();
    return new FunctionResolver(
        functionName,
        singleArgumentFunction(
            functionName,
            v -> (int) Math.floor(v),
            v -> (long) Math.floor(v),
            v -> (float) Math.floor(v),
            Math::floor
        ));
  }

  /**
   * Definition of ln(x) function.
   * Calculate the natural logarithm of x
   * The supported signature of ln function is
   * INTEGER/LONG/FLOAT/DOUBLE -> DOUBLE
   */
  private static FunctionResolver ln() {
    return new FunctionResolver(BuiltinFunctionName.LN.getName(),
        singleArgumentFunction(BuiltinFunctionName.LN.getName(), Math::log));
  }

  /**
   * Definition of log(x, y) function.
   * Calculate the logarithm of x using y as the base
   * The supported signature of log function is
   * (x: INTEGER/LONG/FLOAT/DOUBLE, y: INTEGER/LONG/FLOAT/DOUBLE) -> DOUBLE
   */
  private static FunctionResolver log() {
    return new FunctionResolver(BuiltinFunctionName.LOG.getName(),
        doubleArgumentsFunction(
            BuiltinFunctionName.LOG.getName(), (v1, v2) -> Math.log(v2) / Math.log(v1)));
  }

  /**
   * Util method to generate single argument function bundles. Applicable for
   * INTEGER -> INTEGER
   * LONG -> LONG
   * FLOAT -> FLOAT
   * DOUBLE -> DOUBLE
   */
  private static Map<FunctionSignature, FunctionBuilder> singleArgumentFunction(
      FunctionName functionName,
      Function<Integer, Integer> integerFunc,
      Function<Long, Long> longFunc,
      Function<Float, Float> floatFunc,
      Function<Double, Double> doubleFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    builder.put(
        new FunctionSignature(functionName, Arrays.asList(ExprCoreType.INTEGER)),
        unaryOperator(
            functionName, integerFunc, ExprValueUtils::getIntegerValue, ExprCoreType.INTEGER));
    builder.put(
        new FunctionSignature(functionName, Arrays.asList(ExprCoreType.LONG)),
        unaryOperator(functionName, longFunc, ExprValueUtils::getLongValue, ExprCoreType.LONG));
    builder.put(
        new FunctionSignature(functionName, Arrays.asList(ExprCoreType.FLOAT)),
        unaryOperator(functionName, floatFunc, ExprValueUtils::getFloatValue, ExprCoreType.FLOAT));
    builder.put(
        new FunctionSignature(functionName, Arrays.asList(ExprCoreType.DOUBLE)),
        unaryOperator(
            functionName, doubleFunc, ExprValueUtils::getDoubleValue, ExprCoreType.DOUBLE));
    return builder.build();
  }

  /**
   * Util method to generate single argument function bundles. Applicable for
   * DOUBLE (INTEGER/LONG/FLOAT) -> DOUBLE
   */
  private static Map<FunctionSignature, FunctionBuilder> singleArgumentFunction(
      FunctionName functionName,
      Function<Double, Double> doubleFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    return builder
        .put(
            new FunctionSignature(functionName, Arrays.asList(ExprCoreType.DOUBLE)),
            unaryOperator(
                functionName, doubleFunc, ExprValueUtils::getDoubleValue, ExprCoreType.DOUBLE))
        .build();
  }

  /**
   * Util method to generate single argument function bundles. Applicable for
   * (DOUBLE, DOUBLE) -> DOUBLE
   */
  private static Map<FunctionSignature, FunctionBuilder> doubleArgumentsFunction(
      FunctionName functionName,
      BiFunction<Double, Double, Double> doubleFunc) {
    ImmutableMap.Builder<FunctionSignature, FunctionBuilder> builder = new ImmutableMap.Builder<>();
    return builder
        .put(
            new FunctionSignature(
                functionName, Arrays.asList(ExprCoreType.DOUBLE, ExprCoreType.DOUBLE)),
            doubleArgFunc(functionName, doubleFunc, ExprValueUtils::getDoubleValue,
                ExprValueUtils::getDoubleValue, ExprCoreType.DOUBLE))
        .build();
  }
}