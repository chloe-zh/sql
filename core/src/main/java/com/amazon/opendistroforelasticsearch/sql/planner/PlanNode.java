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

package com.amazon.opendistroforelasticsearch.sql.planner;

import java.util.List;

/**
 * The definition of Plan Node
 */
public interface PlanNode<T extends PlanNode> {

    /**
     * Return the child nodes.
     *
     * @return child nodes.
     */
    List<T> getChild();

    /**
     * Accept the visitor.
     *
     * @param visitor visitor.
     * @param context visitor context.
     * @param <R>     returned object type.
     * @param <C>     context type.
     * @return returned object.
     */
    <R, C> R accept(AbstractPlanNodeVisitor<R, C> visitor, C context);
}