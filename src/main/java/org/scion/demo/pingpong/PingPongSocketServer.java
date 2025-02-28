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
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.scion.jpan.Constants;
import org.scion.jpan.ScionDatagramSocket;

public class PingPongSocketServer {

  public static boolean PRINT = true;

  public static final InetSocketAddress SERVER_ADDRESS =
      new InetSocketAddress(InetAddress.getLoopbackAddress(), 44444);
  public static String TOPO_FILE = "topologies/default/ASff00_0_221/topology.json";

  public static void main(String[] args) throws IOException {
    System.setProperty(Constants.PROPERTY_BOOTSTRAP_TOPO_FILE, TOPO_FILE);

    try (ScionDatagramSocket socket = new ScionDatagramSocket(SERVER_ADDRESS)) {
      DatagramPacket packet = new DatagramPacket(new byte[100], 100);
      println("Waiting for packet ... ");
      socket.receive(packet);
      String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
      println("Received (from " + packet.getSocketAddress() + "): " + msg);

      String msgAnswer = "Re: " + msg;
      packet.setData(msgAnswer.getBytes());

      socket.send(packet);
      println("Sent answer: " + msgAnswer);
    }
  }

  private static void println(String msg) {
    if (PRINT) {
      System.out.println(msg);
    }
  }
}
