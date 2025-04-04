# SCION Java examples

## Send packet example

This is a simple example project that uses [JPAN](https://github.com/scionproto-contrib/jpan) to
send a packet to the [SCION packet analyzer](https://echoscion.ddns.net/).

The easiest way to execute the example is
to [download the stand-alone jar file](https://github.com/netsec-ethz/scion-java-packet-example/releases/download/v0.1.3/scion-packet-example-0.1.3-executable.jar)
and execute it from command line:

```
java -jar scion-packet-example-0.1.3-executable.jar
```

You can also send a custom message string with

```
java -jar scion-packet-example-0.1.3-executable.jar "A better message"
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

This creates a file `scion-packet-example-0.1.4-SNAPSHOT-executable.jar` (note the `-executable`) in
`target/`.

# Troubleshooting

## No DNS search domain found. Please check your /etc/resolv.conf or similar.

This happens, for example, on Windows when using a VPN.
There are several solutions to this (aside from reconfiguring your system).

### Solution #1: Provide search domain

This is useful if you have access to a search domain with a NAPTR record of the discovery server.
You can execute the jar with the following property (on example of a search domain is `ethz.ch.` but
it obviously works only when you are in that very domain):

```
java -Dorg.scion.dnsSearchDomains=<search domain> -jar scion-packet-example-<version>-executable.jar
```

### Solution #2: Provide a discovery server

You can directly set the IP:port of the discovery server:

```
java -Dorg.scion.bootstrap.host=<IP of bootstrap server:8041> -jar scion-packet-example-<version>-executable.jar
```

### Solution #3: Provide a topology file

If you have a topology file, you can do the following:

```
java -Dorg.scion.bootstrap.topoFile=<yourTopologyFile.json> -jar scion-packet-example-<version>-executable.jar
```
