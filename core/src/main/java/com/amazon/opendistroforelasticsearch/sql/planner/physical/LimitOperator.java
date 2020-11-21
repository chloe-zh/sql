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

package com.amazon.opendistroforelasticsearch.sql.planner.physical;

import com.amazon.opendistroforelasticsearch.sql.ast.tree.RareTopN;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprTupleValue;
import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue;
import com.amazon.opendistroforelasticsearch.sql.expression.Expression;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LimitOperator extends RareTopNOperator {
  @Getter
  private final PhysicalPlan input;
  @Getter
  private final Integer limit;
  @Getter
  private final Integer offset;

  /**
   * Constructor of LimitOperator.
   */
  public LimitOperator(
      PhysicalPlan input,
      RareTopN.CommandType commandType,
      Integer limit,
      Integer offset,
      List<Expression> fields) {
    super(input, commandType, offset, fields, fields);
    this.input = input;
    this.limit = limit;
    this.offset = offset;
    this.group = new LimitGroup();
  }

  @Override
  public void open() {
    super.open();
    iterator = Iterators.limit(offset(iterator, offset), limit);
  }

  @Override
  public <R, C> R accept(PhysicalPlanNodeVisitor<R, C> visitor, C context) {
    return visitor.visitLimit(this, context);
  }

  @Override
  public List<PhysicalPlan> getChild() {
    return Collections.singletonList(input);
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public ExprValue next() {
    return iterator.next();
  }

  /**
   * Util method to skip first rows of an {offset} amount from the starting of the result set.
   */
  private Iterator<ExprValue> offset(Iterator<ExprValue> iterator, Integer offset) {
    if (offset > 0 && iterator.hasNext()) {
      iterator.next();
      return offset(iterator, offset - 1);
    }
    return iterator;
  }

  @VisibleForTesting
  @RequiredArgsConstructor
  public class LimitGroup extends Group {

    private final List<Key> list = new LinkedList<>();

    /**
     * To respect the original order in result set,
     * Rewrite methods {@link Group#push(ExprValue) and #result()}
     * To ignore the result frequency of a field
     * But put all field expression into result list directly.
     */
    @Override
    public void push(ExprValue inputValue) {
      Key fieldKey = new Key(inputValue, fieldExprList);
      list.add(fieldKey);
    }

    @Override
    public List<ExprValue> result() {
      ImmutableList.Builder<ExprValue> resultBuilder = new ImmutableList.Builder<>();
      list.forEach(field -> {
        Map<String, ExprValue> map = new LinkedHashMap<>(field.keyMap(fieldExprList));
        resultBuilder.add(ExprTupleValue.fromExprValueMap(map));
      });

      return resultBuilder.build();
    }
  }

}
