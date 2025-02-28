# Ping Pong Demo

This package contains simple ping pong demos for SCION.
There is a demo for `DatagramSocket` and one for `DatagramChannel`.
Both demos have a server and a client. 

The demos require a locally running scionproto topology, specifically the 
"default" topology.

To install the topology:
1. Download the [scionproto source](https://github.com/scionproto/scion)
2. Compile it with `make`, see also the [official instructions](https://docs.scion.org/en/latest/dev/build.html).
3. Run `./scion.sh topology -c topology/default.topo` to generate the topology. 
   The resulting files can be found in the `gen` folder.
4. Start the topology with `./scion.sh start`.
5. Start the server.
6. Start the client.

The client should then report sending a message and receiving a message.


### Troubleshooting

The `./scion.sh topology -c topology/default.topo` command generates TRC files
with a limited validity of a few days only. Please re-run the command regularly. 
