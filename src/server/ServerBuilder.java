package server;

import java.net.ServerSocket;

public interface ServerBuilder {

    server.Server buildServer();

    ServerBuilder setMessageQueue(ServerMessageQueueInterface msgQueue);
    ServerBuilder setServerSocket(ServerSocket serverSocket);
    ServerBuilder startServer();

}
