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
import java.util.Arrays;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Argument extends Expression {
    private final String argName;
    private final Expression value;
//    private final DataType valueType;
    @Override
    public List<Expression> getChild() {
        return Arrays.asList(value);
    }

    @Override
    public <R, C> R accept(AbstractNodeVisitor<R, C> nodeVisitor, C context) {
        return nodeVisitor.visitArgument(this, context);
    }
}