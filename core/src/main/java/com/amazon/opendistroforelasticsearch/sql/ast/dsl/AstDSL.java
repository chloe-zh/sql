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

package com.amazon.opendistroforelasticsearch.sql.ast.dsl;

import com.amazon.opendistroforelasticsearch.sql.ast.expression.AggregateFunction;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.And;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Argument;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Compare;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.DataType;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.EqualTo;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.UnresolvedExpression;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Field;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Function;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.In;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Literal;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Map;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Not;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Or;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.QualifiedName;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.UnresolvedAttribute;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.Aggregation;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.Filter;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.UnresolvedPlan;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.Project;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.Relation;
import java.util.Arrays;
import java.util.List;

/**
 * Class of static methods to create specific node instances
 */
public class AstDSL {

    public static UnresolvedPlan filter(UnresolvedPlan input, UnresolvedExpression expression) {
        return new Filter(expression).attach(input);
    }

    public static UnresolvedPlan relation(String tableName) {
        return new Relation(qualifiedName(tableName));
    }

    public static UnresolvedPlan project(UnresolvedPlan input, UnresolvedExpression... projectList) {
        return new Project(Arrays.asList(projectList)).attach(input);
    }

    public static UnresolvedPlan projectWithArg(UnresolvedPlan input, List<UnresolvedExpression> argList, UnresolvedExpression... projectList) {
        return new Project(Arrays.asList(projectList), argList).attach(input);
    }

    public static UnresolvedPlan agg(UnresolvedPlan input, List<UnresolvedExpression> aggList, List<UnresolvedExpression> sortList,
                                     List<UnresolvedExpression> groupList, List<UnresolvedExpression> argList) {
        return new Aggregation(aggList, sortList, groupList, argList).attach(input);
    }

    public static UnresolvedExpression qualifiedName(String... parts) {
        return new QualifiedName(Arrays.asList(parts));
    }

    public static UnresolvedExpression equalTo(UnresolvedExpression left, UnresolvedExpression right) {
        return new EqualTo(left, right);
    }

    public static UnresolvedExpression unresolvedAttr(String attr) {
        return new UnresolvedAttribute(attr);
    }

    private static UnresolvedExpression literal(Object value, DataType type) {
        return new Literal(value, type);
    }

    public static UnresolvedExpression intLiteral(Integer value) {
        return literal(value, DataType.INTEGER);
    }

    public static UnresolvedExpression doubleLiteral(Double value) {
        return literal(value, DataType.DOUBLE);
    }

    public static UnresolvedExpression stringLiteral(String value) {
        return literal(value, DataType.STRING);
    }

    public static UnresolvedExpression booleanLiteral(Boolean value) {
        return literal(value, DataType.BOOLEAN);
    }

    public static UnresolvedExpression nullLiteral() {
        return literal(null, DataType.NULL);
    }

    public static UnresolvedExpression map(String origin, String target) {
        return new Map(new Field(origin), new Field(target));
    }

    public static UnresolvedExpression map(UnresolvedExpression origin, UnresolvedExpression target) {
        return new Map(origin, target);
    }

    public static UnresolvedExpression aggregate(String func, UnresolvedExpression field) {
        return new AggregateFunction(func, field);
    }

    public static UnresolvedExpression aggregate(String func, UnresolvedExpression field, UnresolvedExpression... args) {
        return new AggregateFunction(func, field, Arrays.asList(args));
    }

    public static UnresolvedExpression function(String funcName, UnresolvedExpression... funcArgs) {
        return new Function(funcName, Arrays.asList(funcArgs));
    }

    public static UnresolvedExpression not(UnresolvedExpression expression) {
        return new Not(expression);
    }

    public static UnresolvedExpression or(UnresolvedExpression left, UnresolvedExpression right) {
        return new Or(left, right);
    }

    public static UnresolvedExpression and(UnresolvedExpression left, UnresolvedExpression right) {
        return new And(left, right);
    }

    public static UnresolvedExpression in(UnresolvedExpression field, UnresolvedExpression... valueList) {
        return new In(field, Arrays.asList(valueList));
    }

    public static UnresolvedExpression compare(String operator, UnresolvedExpression left, UnresolvedExpression right) {
        return new Compare(operator, left, right);
    }

    public static UnresolvedExpression argument(String argName, UnresolvedExpression argValue) {
        return new Argument(argName, argValue);
    }

    public static UnresolvedExpression field(UnresolvedExpression field) {
        return new Field((QualifiedName) field);
    }

    public static UnresolvedExpression field(String field) {
        return new Field(field);
    }

    public static UnresolvedExpression field(UnresolvedExpression field, UnresolvedExpression... fieldArgs) {
        return new Field((QualifiedName) field, Arrays.asList(fieldArgs));
    }

    public static UnresolvedExpression field(String field, UnresolvedExpression... fieldArgs) {
        return new Field(field, Arrays.asList(fieldArgs));
    }

    public static UnresolvedExpression field(UnresolvedExpression field, List<UnresolvedExpression> fieldArgs) {
        return new Field((QualifiedName) field, fieldArgs);
    }

    public static UnresolvedExpression field(String field, List<UnresolvedExpression> fieldArgs) {
        return new Field(field, fieldArgs);
    }

    public static List<UnresolvedExpression> exprList(UnresolvedExpression... exprList) {
        return Arrays.asList(exprList);
    }

    public static List<UnresolvedExpression> defaultFieldsArgs() {
        return exprList(
                argument("exclude", booleanLiteral(false))
        );
    }

    public static List<UnresolvedExpression> defaultStatsArgs() {
        return exprList(
                argument("partitions", intLiteral(1)),
                argument("allnum", booleanLiteral(false)),
                argument("delim", stringLiteral(" ")),
                argument("dedupsplit", booleanLiteral(false))
        );
    }

    public static List<UnresolvedExpression> defaultDedupArgs() {
        return exprList(
                argument("number", intLiteral(1)),
                argument("keepevents", booleanLiteral(false)),
                argument("keepempty", booleanLiteral(false)),
                argument("consecutive", booleanLiteral(false))
        );
    }

    public static List<UnresolvedExpression> defaultSortArgs() {
        return exprList(
                argument("count", intLiteral(1000)),
                argument("desc", booleanLiteral(false))
        );
    }

    public static List<UnresolvedExpression> defaultSortFieldArgs() {
        return exprList(
                argument("exclude", booleanLiteral(false)),
                argument("type", nullLiteral())
        );
    }

}