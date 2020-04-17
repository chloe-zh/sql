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

package com.amazon.opendistroforelasticsearch.sql.ppl.plans.dsl;

import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.AggregateFunction;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.And;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Array;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.DataType;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.EqualTo;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Expression;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Function;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.In;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Literal;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Map;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Nest;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Not;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.Or;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression.UnresolvedAttribute;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.logical.Aggregation;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.logical.Filter;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.logical.LogicalPlan;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.logical.Project;
import com.amazon.opendistroforelasticsearch.sql.ppl.plans.logical.Relation;
import java.util.Arrays;
import java.util.List;

public class DSL {

    public static LogicalPlan filter(LogicalPlan input, Expression expression) {
        return new Filter(expression).withInput(input);
    }

    public static LogicalPlan relation(String tableName) {
        return new Relation(tableName);
    }

    public static LogicalPlan project(LogicalPlan input, Expression... projectList) {
        return new Project(Arrays.asList(projectList)).withInput(input);
    }

    public static LogicalPlan agg(LogicalPlan input, List<Expression> aggList, List<Expression> sortList,
                                  List<Expression> groupList) {
        return new Aggregation(aggList, sortList, groupList).withInput(input);
    }

    public static Expression equalTo(Expression left, Expression right) {
        return new EqualTo(left, right);
    }

    public static Expression unresolvedAttr(String attr) {
        return new UnresolvedAttribute(attr);
    }

    private static Expression literal(Object value, DataType type) {
        return new Literal(value, type);
    }

    public static Expression intLiteral(Integer value) {
        return literal(value, DataType.INTEGER);
    }

    public static Expression doubleLiteral(Double value) {
        return literal(value, DataType.DOUBLE);
    }

    public static Expression stringLiteral(String value) {
        return literal(value, DataType.STRING);
    }

    public static Expression booleanLiteral(Boolean value) {
        return literal(value, DataType.BOOLEAN);
    }

    public static Expression map(String origin, String target) {
        return new Map(new UnresolvedAttribute(origin), new UnresolvedAttribute(target));
    }

    public static Expression aggregate(Expression func, Expression field) {
        return new AggregateFunction(func, field);
    }

    public static Expression function(Expression func, Expression... functionArgs) {
        return new Function(func, Arrays.asList(functionArgs));
    }

    public static Expression not(Expression expression) {
        return new Not(expression);
    }

    public static Expression or(Expression left, Expression right) {
        return new Or(left, right);
    }

    public static Expression and(Expression left, Expression right) {
        return new And(left, right);
    }

    public static Expression in(Expression field, Expression... valueList) {
        return new In(field, Arrays.asList(valueList));
    }

    public static Expression nest(Expression current, Expression next) {
        return new Nest(current, next);
    }

    public static Expression array(String name, Integer index) {
        return new Array(new UnresolvedAttribute(name), intLiteral(index));
    }

}