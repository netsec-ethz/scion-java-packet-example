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
import java.net.InetAddress;
import java.nio.ByteBuffer;
import org.scion.jpan.*;

public class ScionPathExample {
  public static void main(String[] args) throws IOException {
    String message = args.length == 0 ? "Hello there!" : args[0];
    // send to https://scionpacketinspector.netsec.ethz.ch/
    long dstIsdAs = ScionUtil.parseIA("64-2:0:9");
    InetAddress dstIP = InetAddress.getByName("129.132.175.104");
    Path path = Scion.defaultService().getPaths(dstIsdAs, dstIP, 30041).get(0);

    try (ScionDatagramChannel channel = ScionDatagramChannel.open()) {
      channel.connect(path);
      ByteBuffer sendBuf = ByteBuffer.wrap(message.getBytes());
      channel.write(sendBuf);
      PathMetadata meta = channel.getConnectionPath().getMetadata();
      System.out.println("Sent via path: " + ScionUtil.toStringPath(meta));
    }
  }
}
