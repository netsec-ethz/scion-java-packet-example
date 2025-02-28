// Copyright 2023 ETH Zurich
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
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import org.scion.jpan.Constants;
import org.scion.jpan.ScionDatagramSocket;

/**
 * Minimal ping pong client/server demo. The client sends a text message to a server and the server
 * responds with an extended message. Client and server are in different ASes connected by a mock
 * border router.
 *
 * <p>To better see what is happening you can set the logging level to INFO in
 * src/test/resources/simplelogger.properties
 */
public class PingPongSocketClient {

  public static boolean PRINT = true;

  public static void main(String[] args) throws IOException {
    // The following setting attempts connection to the daemon of the AS "1-ff00:0:131"
    // in the scionproto ¨default" topology.
    System.setProperty(Constants.DEFAULT_DAEMON, "127.0.0.77:30255");
    println("Connecting to daemon: " + System.getProperty(Constants.DEFAULT_DAEMON));

    InetSocketAddress serverAddress = PingPongSocketServer.SERVER_ADDRESS;
    try (ScionDatagramSocket socket = new ScionDatagramSocket(null)) {
      String msg = "Hello there!";
      byte[] sendBuf = msg.getBytes();
      DatagramPacket request = new DatagramPacket(sendBuf, sendBuf.length, serverAddress);
      socket.send(request);
      println("Sent: " + msg);

      println("Receiving ... (" + socket.getLocalSocketAddress() + ")");
      byte[] buffer = new byte[512];
      DatagramPacket response = new DatagramPacket(buffer, buffer.length);
      socket.receive(response);

      String pong = new String(buffer, 0, response.getLength());
      println("Received: " + pong);
    }
  }

  private static void println(String msg) {
    if (PRINT) {
      System.out.println(msg);
    }
  }
}
