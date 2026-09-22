package mashup.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import mashup.thrift.generated.MovieService;

public class MovieServiceServer {

    public static final int DEFAULT_PORT = 9090;

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        start(port);
    }

    public static void start(int port) throws Exception {
        MovieService.Processor<MovieService.Iface> processor = new MovieService.Processor<>(new MovieServiceThriftImpl());
        TServerTransport transport = new TServerSocket(port);
        TServer server = new TSimpleServer(new TServer.Args(transport).processor(processor));
        server.serve();
    }
}
