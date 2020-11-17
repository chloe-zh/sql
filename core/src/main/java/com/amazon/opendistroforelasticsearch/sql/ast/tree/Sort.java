/*
 *   Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

package com.amazon.opendistroforelasticsearch.sql.ast.tree;

import static com.amazon.opendistroforelasticsearch.sql.ast.tree.Sort.NullOrder.NULL_FIRST;
import static com.amazon.opendistroforelasticsearch.sql.ast.tree.Sort.NullOrder.NULL_LAST;
import static com.amazon.opendistroforelasticsearch.sql.ast.tree.Sort.SortOrder.ASC;
import static com.amazon.opendistroforelasticsearch.sql.ast.tree.Sort.SortOrder.DESC;

import com.amazon.opendistroforelasticsearch.sql.ast.AbstractNodeVisitor;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Argument;
import com.amazon.opendistroforelasticsearch.sql.ast.expression.Field;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * AST node for Sort {@link Sort#sortList} represent a list of sort expression and sort options.
 * Options (arguments):
 * First argument with default value: {"count": -1}
 * Second argument with default value: {"offset": 0}
 * 1. count:
 * The argument "count" is to limit the result size, it should be type of non-negative integer.
 * PPL rule:
 * If no count is specified, the default limit of 10000 is used.
 * If 0 is specified, all of the results are returned.
 * SQL rule (where key word is LIMIT instead):
 * If no limit is specified, all results are returned.
 * Else return the results with the limit size,
 * Or all of the results if the original results have a smaller size than the specified limit.
 * To distinguish the argument meanings of PPL and SQL,
 * PPL count is set to 1000 by default, and is to -1 when the input count is 0.
 * SQL limit is set to -1 by default.
 * Where -1 is translated to all fields in
 * {@link com.amazon.opendistroforelasticsearch.sql.planner.physical.SortOperator}.
 * 2. offset:
 * This argument is to set the offset rows to skip from the results, specifically in SQL.
 * The offset rows are skipped before starting to count the limit.
 * Offset is set to 0 by default.
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Sort extends UnresolvedPlan {
  private UnresolvedPlan child;
  private final List<Argument> options;
  private final List<Field> sortList;

  @Override
  public Sort attach(UnresolvedPlan child) {
    this.child = child;
    return this;
  }

  @Override
  public List<UnresolvedPlan> getChild() {
    return ImmutableList.of(child);
  }

  @Override
  public <T, C> T accept(AbstractNodeVisitor<T, C> nodeVisitor, C context) {
    return nodeVisitor.visitSort(this, context);
  }

  /**
   * Sort Options.
   */
  @Data
  public static class SortOption {

    /**
     * Default ascending sort option, null first.
     */
    public static SortOption DEFAULT_ASC = new SortOption(ASC, NULL_FIRST);
    /**
     * Default descending sort option, null last.
     */
    public static SortOption DEFAULT_DESC = new SortOption(DESC, NULL_LAST);

    private final SortOrder sortOrder;
    private final NullOrder nullOrder;
  }

  public enum SortOrder {
    ASC,
    DESC
  }

  public enum NullOrder {
    NULL_FIRST,
    NULL_LAST
  }
}
