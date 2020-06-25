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

package com.amazon.opendistroforelasticsearch.sql.ppl;

import static com.amazon.opendistroforelasticsearch.sql.legacy.TestsConstants.TEST_INDEX_BANK;
import static com.amazon.opendistroforelasticsearch.sql.legacy.TestsConstants.TEST_INDEX_BANK_WITH_NULL_VALUES;
import static com.amazon.opendistroforelasticsearch.sql.util.MatcherUtils.rows;
import static com.amazon.opendistroforelasticsearch.sql.util.MatcherUtils.schema;
import static com.amazon.opendistroforelasticsearch.sql.util.MatcherUtils.verifyDataRows;
import static com.amazon.opendistroforelasticsearch.sql.util.MatcherUtils.verifySchema;


import java.io.IOException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class MathematicalFunctionIT extends PPLIntegTestCase {

  @Override
  public void init() throws IOException {
    loadIndex(Index.BANK);
    loadIndex(Index.BANK_WITH_NULL_VALUES);
  }

  @Test
  public void testAbsFunction() throws IOException {
    JSONObject result =
        executeQuery(
            String.format(
                "source=%s | eval f=abs(age) | fields f",
                TEST_INDEX_BANK));
    verifySchema(result, schema("f", null, "integer"));
    verifyDataRows(result, rows(28), rows(32), rows(33), rows(34), rows(36), rows(36), rows(39));
  }

  @Test
  public void testAbsNull() throws IOException {
    JSONObject result =
        executeQuery(
            String.format(
                "source=%s | eval f=abs(age) | fields f",
                TEST_INDEX_BANK_WITH_NULL_VALUES));
    verifySchema(result, schema("f", null, "integer"));
    verifyDataRows(result, rows(28), rows(32), rows(33), rows(34), rows(36), rows(36), rows(null));
  }
}
