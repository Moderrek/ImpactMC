package com.impact.lib;

import com.impact.lib.api.server.FakeServer;

public abstract class ServerTest {

  protected final ImpactLibPlugin impact;
  protected final FakeServer server;

  public ServerTest() {
    server = FakeServer.getInstance();
    impact = new ImpactLibPlugin(server);
    impact.onEnableTest(server);
  }

}
