# SCION Java examples

## Send packet example

This is a simple example project that uses [JPAN](https://github.com/scionproto-contrib/jpan) to
send a packet to the [SCION packet inspector](https://scionpacketinspector.netsec.ethz.ch).

To try out the example please download the stand-alone jar file through one of these options:
- [download link to release section](https://github.com/netsec-ethz/scion-java-packet-example/releases/download/v0.5.0/packet-example-0.5.0.jar)
- `wget https://github.com/netsec-ethz/scion-java-packet-example/releases/download/v0.5.0/packet-example-0.5.0.jar`


Then join the IETF's WLAN: **ietf-dual-stack**.

Then execute the example (requires Java 1.8 or later):

```
java -Dorg.scion.bootstrap.host=31.130.239.184 -jar jpan-cli.jar traceroute 64-2:0:9,0.0.0.0 "A better message"
```

## Other examples

The project contains other examples, but they do not come with an executable jar file:

* [Use explicit path example](src/main/java/org/scion/demo/ScionPathExample.java)
* [Custom path policy example](src/main/java/org/scion/demo/ScionPathPolicyExample.java)
* [Ping-pong examples](src/main/java/org/scion/demo/pingpong/)
  * [DatagramChannel client](src/main/java/org/scion/demo/pingpong/PingPongChannelClient.java)
  * [DatagramChannel server](src/main/java/org/scion/demo/pingpong/PingPongChannelServer.java)
  * [DatagramSocket client](src/main/java/org/scion/demo/pingpong/PingPongSocketClient.java)
  * [DatagramSocket server](src/main/java/org/scion/demo/pingpong/PingPongSocketServer.java)

# Building your own executable jar

You can build your own executable jar file with

```
mvn clean package -Pcreate-executable-example
```

This creates a file `packet-example-0.5.1-SNAPSHOT.jar` in `target/`.
(Note, the `scion-...` jar file is *not* executable)

# Troubleshooting

## No DNS search domain found. Please check your /etc/resolv.conf or similar.

This happens, for example, on Windows when using a VPN.
There are several solutions to this (aside from reconfiguring your system).

### Solution #1: Provide search domain

This is useful if you have access to a search domain with a NAPTR record of the discovery server.
You can execute the jar with the following property (on example of a search domain is `ethz.ch.` but
it obviously works only when you are in that very domain):

```
java -Dorg.scion.dnsSearchDomains=<search domain> -jar packet-example-<version>.jar
```

### Solution #2: Provide a discovery server

You can directly set the IP:port of the discovery server:

```
java -Dorg.scion.bootstrap.host=<IP of bootstrap server:8041> -jar packet-example-<version>.jar
```

### Solution #3: Provide a topology file

If you have a topology file, you can do the following:

```
java -Dorg.scion.bootstrap.topoFile=<yourTopologyFile.json> -jar packet-example-<version>.jar
```
