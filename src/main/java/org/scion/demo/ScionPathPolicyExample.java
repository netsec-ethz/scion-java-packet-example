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
import java.util.List;
import java.util.stream.Collectors;

import org.scion.jpan.*;

/**
 * This example shows how to implement a path policy that requires all traversed hops to be in ISD
 * 64. Predefined policies (fewest hops, etc) can be found in the {@link PathPolicy} class.
 */
public class ScionPathPolicyExample {

  private static final int ISD_SWITZERLAND = 64;

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("You can add a custom message as argument to the executable.");
    }
    String message = args.length == 0 ? "Hello there!" : args[0];
    long dstIsdAs = ScionUtil.parseIA("64-2:0:9");
    InetAddress ip = InetAddress.getByName("129.132.175.104");
    Path path = Scion.defaultService().getPaths(dstIsdAs, ip, 30041).get(0);

    try (ScionDatagramChannel channel = ScionDatagramChannel.open()) {
      channel.setPathPolicy(new OnlySwitzerland());
      ByteBuffer sendBuf = ByteBuffer.wrap(message.getBytes());
      channel.send(sendBuf, path);
      System.out.println("The packet was sent via path: " + ScionUtil.toStringPath(path.getMetadata()));
    }
  }

  /**
   * A path policy that requires all traversed hops to be in ISD 64. Note: an empty path will always
   * be accepted.
   */
  private static class OnlySwitzerland implements PathPolicy {
    @Override
    public List<Path> filter(List<Path> paths) {
      return paths.stream().filter(this::isPathOkay).collect(Collectors.toList());
    }

    private boolean isPathOkay(Path path) {
      for (PathMetadata.PathInterface pif : path.getMetadata().getInterfacesList()) {
        int isd = ScionUtil.extractIsd(pif.getIsdAs());
        // Reject any path that goes outside Switzerland's ISD
        if (isd != ISD_SWITZERLAND) {
          return false;
        }
      }
      return true;
    }
  }
}
