# SCION Java send packet example

This is a simple example project that uses [JPAN](https://github.com/scionproto-contrib/jpan) to send a packet to the [SCION packet analyzer](https://echoscion.ddns.net/).

The easiest way to execute the example is to [download the stand-alone jar file](https://github.com/netsec-ethz/scion-java-packet-example/releases/download/v0.1.0/scion-packet-example-0.1.0-shaded.jar) and
execute it from command line:
```
java -jar scion-packet-example-0.1.0-shaded.jar
```

# Troubleshooting

## No DNS search domain found. Please check your /etc/resolv.conf or similar.
This happens, for example, on Windows when using a VPN. One solution is to execute the jar with the following property (the example works only for `ethz.ch`):

```
java -Dorg.scion.dnsSearchDomains=ethz.ch. -jar scion-packet-example-0.1.0-shaded.jar
```
