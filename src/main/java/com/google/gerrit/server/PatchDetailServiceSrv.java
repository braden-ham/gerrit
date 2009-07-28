// Copyright (C) 2008 The Android Open Source Project
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

package com.google.gerrit.server;

import com.google.gerrit.server.patch.PatchDetailServiceImpl;
import com.google.inject.Singleton;

/** Publishes {@link PatchDetailServiceImpl} over JSON. */
@SuppressWarnings("serial")
@Singleton
public class PatchDetailServiceSrv extends GerritJsonServlet {
  @Override
  protected Object createServiceHandle() throws Exception {
    return new PatchDetailServiceImpl(GerritServer.getInstance());
  }
}
