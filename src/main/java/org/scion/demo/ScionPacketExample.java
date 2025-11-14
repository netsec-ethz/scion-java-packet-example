// Copyright 2024 ETH Zurich
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

package org.scion.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.scion.jpan.*;

public class ScionPacketExample {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("You can add a custom message as argument to the executable.");
    }
    String message = args.length == 0 ? "Hello there!" : args[0];
    // send to https://scionpacketinspector.netsec.ethz.ch/
    // This requires the URL to have a "scion" TXT entry, e.g.
    // $ dig +short TXT scionpacketinspector.netsec.ethz.ch
    //   netsec-ac3914.inf.ethz.ch.
    //   "scion=64-2:0:9,129.132.175.104"
    InetSocketAddress addr = new InetSocketAddress("scionpacketinspector.netsec.ethz.ch", 30041);
    try (ScionDatagramChannel channel = ScionDatagramChannel.open()) {
      channel.connect(addr);
      channel.write(ByteBuffer.wrap(message.getBytes()));
      PathMetadata meta = channel.getConnectionPath().getMetadata();
      System.out.println("The packet was sent via path: " + ScionUtil.toStringPath(meta));
    }
    System.out.println("The packet was sent to: https://scionpacketinspector.netsec.ethz.ch/");
  }
}
