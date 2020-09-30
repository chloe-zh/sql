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

import com.amazon.opendistroforelasticsearch.sql.data.model.ExprValue;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RelationOperator extends PhysicalPlan {
  private PhysicalPlan input;

  @Override
  public <R, C> R accept(PhysicalPlanNodeVisitor<R, C> visitor, C context) {
    return visitor.visitRelation(this, context);
  }

  @Override
  public List<PhysicalPlan> getChild() {
    return ImmutableList.of(this.input);
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public ExprValue next() {
    return null;
  }
}
