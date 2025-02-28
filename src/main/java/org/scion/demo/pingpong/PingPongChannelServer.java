// Copyright 2025 ETH Zurich
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.scion.demo.pingpong;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.scion.jpan.*;

public class PingPongChannelServer {

  public static boolean PRINT = true;

  public static final InetSocketAddress SERVER_ADDRESS =
      new InetSocketAddress(InetAddress.getLoopbackAddress(), 44444);
  public static String TOPO_FILE = "topologies/default/ASff00_0_221/topology.json";

  public static void main(String[] args) throws IOException {
    // We set the topology file to bootstrap the server in AS 2-ff00:0:221
    System.setProperty(Constants.PROPERTY_BOOTSTRAP_TOPO_FILE, TOPO_FILE);

    try (ScionDatagramChannel channel = ScionDatagramChannel.open()) {
      channel.bind(SERVER_ADDRESS);
      ByteBuffer buffer = ByteBuffer.allocate(100);
      println("Waiting for packet ... ");
      ScionSocketAddress responseAddress = channel.receive(buffer);
      Path path = responseAddress.getPath();
      String msg = extractMessage(buffer);
      String remoteAddress = path.getRemoteAddress() + ":" + path.getRemotePort();
      String borderRouterInterfaces = ScionUtil.toStringPath(path.getRawPath());
      println("Received (from " + remoteAddress + ") via " + borderRouterInterfaces + "): " + msg);

      String msgAnswer = "Re: " + msg;
      channel.send(ByteBuffer.wrap(msgAnswer.getBytes()), path);
      println("Sent answer: " + msgAnswer);
    }
  }

  private static String extractMessage(ByteBuffer buffer) {
    buffer.flip();
    byte[] bytes = new byte[buffer.remaining()];
    buffer.get(bytes);
    return new String(bytes);
  }

  private static void println(String msg) {
    if (PRINT) {
      System.out.println(msg);
    }
  }
}
