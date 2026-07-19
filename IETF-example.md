# SCION Java examples

## Send packet example

This is a simple example project that uses [JPAN](https://github.com/scionproto-contrib/jpan) to
send a packet to the [SCION packet inspector](https://scionpacketinspector.netsec.ethz.ch).

To try out the example please download the stand-alone jar file through one of these options:
- [download link to release section](https://github.com/netsec-ethz/scion-java-packet-example/releases/download/v0.5.0/packet-example-0.5.0.jar), or
- `wget https://github.com/netsec-ethz/scion-java-packet-example/releases/download/v0.5.0/packet-example-0.5.0.jar`


Then join on of the IETF's WLANs: ietf, ietf-hotel, ietf-dual-stack, or eduroam.

Then execute the example (requires Java 1.8 or later):

```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar packet-example-0.5.0.jar "Your unique message"
```
Note: the bootstrap argument is only required because this is an experimental set-up.


### JPAN CLI tool

```
wget https://github.com/netsec-ethz/jpan-cli/releases/download/v0.3.0/jpan-cli.jar
```
List the paths to ETH Zurich:
```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar jpan-cli.jar showpaths 64-2:0:9
```
Only 10 paths??? Well, let's pass a parameter to list the first 100 paths (or fewer if not that many available)
```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar jpan-cli.jar showpaths 64-2:0:9 -m 100
```

Or do a traceroute:
```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar jpan-cli.jar traceroute 64-2:0:9,0.0.0.0
```

Other destinations to try:
- ETH 64-2:0:9
- Cambridge 71-20965
- Cloudscale 64-2:0:9c
- CSCS 64-2:0:c
- Korea University 71-2:0:4d


## Other things to try

* JPAN [CLI tool](https://github.com/netsec-ethz/jpan-cli) with `ping`, `traceroute`, `showpaths`, ..
* [Jingle Ping](https://ping.scionapps.com/)
* [SCION detector and apps](https://apps.scion.org/)
* List of [libraries and projects](https://github.com/scionproto/awesome-scion)

## Additional examples

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
