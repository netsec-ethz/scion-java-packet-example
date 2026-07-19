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
[JPAN-CLI](https://github.com/netsec-ethz/jpan-cli) is a tool that allows echo, traceroute, showpaths, ... and other functionality, similar to the SCION CLI tool. JPAN-CLI is stand-alone based on JPAN and does not require any locally installed SCION software. Here are some examples you can run, but first, to download the jar file:

```
wget https://github.com/netsec-ethz/jpan-cli/releases/download/v0.3.0/jpan-cli.jar
```
List the paths to ETH Zurich:
```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar jpan-cli.jar showpaths 64-2:0:9
```
Only 10 paths??? Well, let's pass a parameter to list the first 100 paths:
```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar jpan-cli.jar showpaths 64-2:0:9 -m 100
```

Or do a traceroute:
```
java -Dorg.scion.bootstrap.host=scion-ietf126.ddns.net -jar jpan-cli.jar traceroute 64-2:0:9,0.0.0.0
```

Other destinations to try:
- ETH `64-2:0:9`
- Cambridge `71-20965`
- Cloudscale `64-2:0:9c`
- CSCS `64-2:0:c`
- Korea University `71-2:0:4d`
- University of Virginia `71-225`
- OvGU `71-2:0:4a`

A list of reachable ASes can be found at the end of this document.

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


# List Of Reachable ASes

- 	71-225	University of Virginia
- 	64-559	SWITCH
- 	71-559	SWITCH
- 	73-1125	SURF
- 	75-1125	SURF
- 	71-1140	SIDN Labs
- 	71-1888	Stichting Centrum voor Wiskunde en Informatica
- 	64-3303	Swisscom
- 	64-6730	Sunrise UPC GmbH
- 	64-8300	Swisscom (Suisse) SA
- 	64-12387	TON Total Optical Networks AG
- 	64-13030	Init7
- 	64-13267	Zürcher Kantonalbank
- 	64-15532	Raiffeisen Schweiz Genossenschaft
- 	64-15623	Cyberlink AG
- 	71-20965	GEANT
- 	64-24951	EveryWare
- 	75-30870	Varity (Trans-iX B.V.)
- 	67-33164	Iristel
- 	64-33965	Litecom AG
- 	64-41632	Glarner Kantonalbank
- 	64-59414	Cyberlink AG
- 	64-60284	Axpo WZ-Systems AG
- 	64-64580	Frankfurter Bankgesellschaft (Schweiz) AG
- 	64-196722	Schweizerische Nationalbank
- 	64-214873	Hint AG
- 	73-215088	SIDN
- 	64-2:0:0	Anapaya Systems AG
- 	66-2:0:1	Schweizerische Nationalbank
- 	64-2:0:2	SIX Group Services AG
- 	64-2:0:3	Zürcher Kantonalbank
- 	64-2:0:4	Swisscom (Suisse) SA
- 	64-2:0:9	ETH Zurich
- 	64-2:0:c	Swiss National Supercomputing Centre
- 	65-2:0:f	Anapaya Systems AG
- 	64-2:0:11	Anapaya Systems AG
- 	64-2:0:12	Anapaya Systems AG
- 	64-2:0:13	Anapaya Systems AG
- 	64-2:0:17	Axpo WZ-Systems AG
- 	64-2:0:21	Sunrise UPC GmbH
- 	64-2:0:22	Sunrise UPC GmbH
- 	64-2:0:23	InterCloud SAS
- 	65-2:0:24	InterCloud SAS
- 	64-2:0:27	Cyberlink AG
- 	64-2:0:28	Cyberlink AG
- 	64-2:0:29	VBS
- 	64-2:0:2b	Armasuisse
- 	64-2:0:2c	Armasuisse
- 	64-2:0:2d	Armasuisse
- 	64-2:0:36	RUAG AG
- 	64-2:0:37	UBS
- 	71-2:0:3b	KREONET
- 	71-2:0:3c	KREONET
- 	71-2:0:3d	KREONET
- 	71-2:0:3e	KREONET
- 	71-2:0:3f	KREONET
- 	71-2:0:40	KREONET
- 	64-2:0:41	RUAG AG
- 	64-2:0:46	Swisscom (Suisse) SA
- 	71-2:0:4a	Otto-von-Guericke-Universität Magdeburg
- 	64-2:0:4c	AWS PoC Anapaya
- 	71-2:0:4d	Korea University
- 	64-2:0:4f	Infoguard
- 	65-2:0:51	InterCloud SAS
- 	64-2:0:53	Everyware
- 	64-2:0:54	Everyware
- 	64-2:0:64	Swisscom (Suisse) SA
- 	64-2:0:66	Kanton Solothurn
- 	65-2:0:67	Colt Netherlands
- 	65-2:0:68	Colt United Kingdom
- 	65-2:0:69	Colt Germany
- 	65-2:0:6a	Colt United Kingdom Test AS
- 	65-2:0:6b	Colt Germany Test AS
- 	64-2:0:70	Anapaya Azure Test AS
- 	65-2:0:71	InterCloud SAS
- 	75-2:0:72	Anapaya Systems AG
- 	75-2:0:73	Anapaya Systems AG
- 	64-2:0:74	Anapaya Systems AG
- 	75-2:0:74	Anapaya Systems AG
- 	64-2:0:75	Anapaya Systems AG
- 	75-2:0:75	Anapaya Systems AG
- 	65-2:0:76	Axpo WZ-Systems AG
- 	64-2:0:82	Anapaya Systems AG
- 	75-2:0:82	Anapaya Systems AG
- 	64-2:0:92	PCB
- 	64-2:0:9b	Schweizerische Nationalbank
- 	64-2:0:9c	ETH Zurich
- 	64-2:0:9e	eSANITA
- 	64-2:0:9f	Anapaya Lab
- 	64-2:0:ab	Litecom AG
- 	75-2:0:b1	Varity Whitesky
- 	64-2:0:13a	GMG GmbH
- 	64-2:0:13b	GMG AG
- 	64-2:0:13c	GMG GmbH
- 	64-2:0:13d	GMG AG
- 	64-2:0:140	Schweizerische Nationalbank
- 	64-2:0:141	Schweizerische Nationalbank
- 	65-2:0:144	Proximus Global Marseille (BICS)
- 	66-2:0:145	Proximus Global Singapore (BICS)
- 	75-2:0:149	SURF
- 	75-2:0:14a	SURF
- 	64-2:0:14c	BABS
- 	71-2:0:152	UFES (Federal University of Espírito Santo)
- 	65-2:0:154	Proximus Global Brussels (BICS)
- 	67-2:0:155	Proximus Global New York (BICS)
- 	65-2:0:156	Cyberlink Dusseldorf
- 	65-2:0:157	Cyberlink Vienna
- 	64-2:0:15a	Landesspital Liechtenstein
- 	64-2:0:15b	Anapaya Systems AG
- 	64-2:0:15c	OriginStamp AG
- 	64-2:0:160	Landesspital Liechtenstein

