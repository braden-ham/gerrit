// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.reviewdb.client;

import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.reviewdb.client.RefNames.parseShardedRefPart;

import org.junit.Test;

public class RefNamesTest {
  private final Account.Id accountId = new Account.Id(1011123);
  private final Change.Id changeId = new Change.Id(67473);
  private final PatchSet.Id psId = new PatchSet.Id(changeId, 42);

  @Test
  public void fullName() throws Exception {
    assertThat(RefNames.fullName("refs/meta/config")).isEqualTo("refs/meta/config");
    assertThat(RefNames.fullName("refs/heads/master")).isEqualTo("refs/heads/master");
    assertThat(RefNames.fullName("master")).isEqualTo("refs/heads/master");
    assertThat(RefNames.fullName("refs/tags/v1.0")).isEqualTo("refs/tags/v1.0");
  }

  @Test
  public void refsUsers() throws Exception {
    assertThat(RefNames.refsUsers(accountId)).isEqualTo("refs/users/23/1011123");
  }

  @Test
  public void refsDraftComments() throws Exception {
    assertThat(RefNames.refsDraftComments(accountId, changeId))
      .isEqualTo("refs/draft-comments/23/1011123/67473");
  }

  @Test
  public void refsDraftCommentsPrefix() throws Exception {
    assertThat(RefNames.refsDraftCommentsPrefix(accountId))
      .isEqualTo("refs/draft-comments/23/1011123/");
  }

  @Test
  public void refsStarredChanges() throws Exception {
    assertThat(RefNames.refsStarredChanges(changeId, accountId))
      .isEqualTo("refs/starred-changes/73/67473/1011123");
  }

  @Test
  public void refsStarredChangesPrefix() throws Exception {
    assertThat(RefNames.refsStarredChangesPrefix(changeId))
      .isEqualTo("refs/starred-changes/73/67473/");
  }

  @Test
  public void refsEdit() throws Exception {
    assertThat(RefNames.refsEdit(accountId, changeId, psId))
      .isEqualTo("refs/users/23/1011123/edit-67473/42");
  }

  @Test
  public void testParseShardedRefsPart() throws Exception {
    assertThat(parseShardedRefPart("01/1")).isEqualTo(1);
    assertThat(parseShardedRefPart("01/1-drafts")).isEqualTo(1);
    assertThat(parseShardedRefPart("01/1-drafts/2")).isEqualTo(1);

    assertThat(parseShardedRefPart(null)).isNull();
    assertThat(parseShardedRefPart("")).isNull();

    // Prefix not stripped.
    assertThat(parseShardedRefPart("refs/users/01/1")).isNull();

    // Invalid characters.
    assertThat(parseShardedRefPart("01a/1")).isNull();
    assertThat(parseShardedRefPart("01/a1")).isNull();

    // Mismatched shard.
    assertThat(parseShardedRefPart("01/23")).isNull();

    // Shard too short.
    assertThat(parseShardedRefPart("1/1")).isNull();
  }
}
