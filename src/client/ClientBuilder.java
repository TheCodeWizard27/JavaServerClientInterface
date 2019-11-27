package client;

import java.net.InetSocketAddress;
import java.net.Socket;

import io.ClientMessageQueue;

public interface ClientBuilder {

    ServerProxy buildClient();

    ClientBuilder setSocket(Socket socket);
    ClientBuilder setAddress(InetSocketAddress address);
    ClientBuilder setQueue(ClientMessageQueue queue);
    ClientBuilder connect();

}
