# SCION Java send packet example

This is a simple example project that uses [JPAN](https://github.com/scionproto-contrib/jpan) to send a packet to the [SCION packet analyzer](https://echoscion.ddns.net/).

# Troubleshooting

## No DNS search domain found. Please check your /etc/resolv.conf or similar.
This happens, for example, on Windows when using a VPN. One solution is to execute the jar with the following property (the example works only for `ethz.ch`):

```
java -Dorg.scion.dnsSearchDomains=ethz.ch. -jar target/scion-multiping-0.0.1-ALPHA-SNAPSHOT-shaded.jar
```
