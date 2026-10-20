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
import java.util.List;
import org.scion.jpan.*;

public class ScionPathExample {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("You can add a custom message as argument to the executable.");
    }
    String message = args.length == 0 ? "Hello there!" : args[0];
    long dstIsdAs = ScionUtil.parseIA("64-2:0:9");
    InetSocketAddress dst = new InetSocketAddress("scionpacketinspector.netsec.ethz.ch", 30041);
    List<Path> paths = Scion.defaultService().getPaths(dstIsdAs, dst);
    if (paths.isEmpty()) {
      System.out.println("No paths found to: https://scionpacketinspector.netsec.ethz.ch/");
      System.out.println("  (using address: 64-2:0:9,129.132.175.104)");
      return;
    }

    try (ScionDatagramChannel channel = ScionDatagramChannel.open()) {
      ByteBuffer sendBuf = ByteBuffer.wrap(message.getBytes());
      // It is recommended to select paths indirectly via a PathPolicy or possibly a PathSelector.
      // However, if you want to use a specific paths without these, it can be done as follows,
      // but there are some disadvantages:
      // - If the path expires, it is not automatically renewed
      // - If the path encounters a problem, it is not automatically replaced with a new path
      Path myPath = paths.get(0); // Choose any specific path.
      channel.send(sendBuf, myPath);
      PathMetadata meta = myPath.getMetadata();
      System.out.println("The packet was sent via path: " + ScionUtil.toStringPath(meta));
    }
    System.out.println("The packet was sent to: https://scionpacketinspector.netsec.ethz.ch/");
  }
}
