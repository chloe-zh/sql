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

package com.amazon.opendistroforelasticsearch.sql.ppl.plans.expression;

import com.amazon.opendistroforelasticsearch.sql.ppl.node.AbstractNodeVisitor;
import com.amazon.opendistroforelasticsearch.sql.ppl.node.NodeVisitor;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Expression node of struct array
 * Params include array name and index (0, 1, 2..)
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Array extends Expression {
    private final Expression arrayName;
    private final Expression index;

    @Override
    public List<Expression> getChild() {
        return ImmutableList.of();
    }

    @Override
    public <R> R accept(NodeVisitor<R> nodeVisitor) {
        if (nodeVisitor instanceof AbstractNodeVisitor) {
            return ((AbstractNodeVisitor<R>) nodeVisitor).visitArray(this);
        } else {
            return nodeVisitor.visitChildren(this);
        }
    }
}
