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
import java.nio.ByteBuffer;
import org.scion.jpan.Constants;
import org.scion.jpan.PathMetadata;
import org.scion.jpan.ScionDatagramChannel;
import org.scion.jpan.ScionUtil;

/**
 * Minimal ping pong client/server demo. The client sends a text message to a server and the server
 * responds with an extended message. Client and server are in different ASes connected by a mock
 * border router.
 *
 * <p>To better see what is happening you can set the logging level to INFO in
 * src/test/resources/simplelogger.properties
 */
public class PingPongChannelClient {

  public static boolean PRINT = true;

  public static void main(String[] args) throws IOException {
    // The following setting attempts connection to the daemon of the AS "1-ff00:0:131"
    // in the scionproto ¨default" topology.
    System.setProperty(Constants.DEFAULT_DAEMON, "127.0.0.77:30255");
    println("Connecting to daemon: " + System.getProperty(Constants.DEFAULT_DAEMON));

    try (ScionDatagramChannel channel = ScionDatagramChannel.open()) {
      channel.configureBlocking(true);
      channel.connect(PingPongChannelServer.SERVER_ADDRESS);
      String msg = "Hello there!";
      ByteBuffer sendBuf = ByteBuffer.wrap(msg.getBytes());
      channel.write(sendBuf);
      PathMetadata meta = channel.getConnectionPath().getMetadata();
      println("Sent via " + ScionUtil.toStringPath(meta) + ": " + msg);

      println("Receiving ... (" + channel.getLocalAddress() + ")");
      ByteBuffer buffer = ByteBuffer.allocate(512);
      channel.read(buffer);
      buffer.flip();
      byte[] bytes = new byte[buffer.remaining()];
      buffer.get(bytes);
      println("Received: " + new String(bytes));
    }
  }

  private static void println(String msg) {
    if (PRINT) {
      System.out.println(msg);
    }
  }
}
