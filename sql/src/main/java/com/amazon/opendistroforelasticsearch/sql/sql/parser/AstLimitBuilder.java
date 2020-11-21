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

package com.amazon.opendistroforelasticsearch.sql.sql.parser;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import com.amazon.opendistroforelasticsearch.sql.ast.expression.Argument;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.DataType;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Field;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Literal;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.RareTopN;
import com.amazon.opendistroforelasticsearch.sql.ast.tree.UnresolvedPlan;
import com.amazon.opendistroforelasticsearch.sql.sql.antlr.parser.OpenDistroSQLParser;
import com.amazon.opendistroforelasticsearch.sql.sql.antlr.parser.OpenDistroSQLParserBaseVisitor;
import com.amazon.opendistroforelasticsearch.sql.sql.parser.context.QuerySpecification;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AstLimitBuilder extends OpenDistroSQLParserBaseVisitor<UnresolvedPlan> {
  private final QuerySpecification querySpec;

  /**
   * {@link RareTopN} node is used as Limit node here since they have similar properties.
   * Here the user-specified limit in query is stored as "noOfResults" argument of RareTopN.
   */
  @Override
  public UnresolvedPlan visitLimitClause(OpenDistroSQLParser.LimitClauseContext ctx) {
    ImmutableList.Builder<Field> builder = new ImmutableList.Builder<>();
    querySpec.getSelectItems().forEach(v -> builder.add(new Field(v, emptyList())));
    Argument size = new Argument("noOfResults",
        new Literal(Integer.parseInt(ctx.limit.getText()), DataType.INTEGER));
    return new RareTopN(RareTopN.CommandType.TOP, singletonList(size), builder.build(), emptyList())
        .setOffset(ctx.offset == null ? 0 : integerValue(ctx.offset))
        .markAsLimitPlan();
  }

  private Integer integerValue(OpenDistroSQLParser.DecimalLiteralContext ctx) {
    return Integer.parseInt(ctx.getText());
  }
}
