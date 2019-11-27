package io;

import server.IOServer;
import server.Server;
import server.ServerApplicationInterface;
import server.ServerBuilder;
import server.ServerMessageQueueInterface;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import client.ClientApplicationInterface;
import client.ClientBuilder;
import client.IOClient;
import client.ServerProxy;

public class SocketBuilder {
    public static String IP = "127.0.0.1";
    public static int PORT = 3000;

    private SocketBuilder(){

    }

    public static class IOClientBuilder implements ClientBuilder {
        private IOClient client;

        public IOClientBuilder(ClientApplicationInterface msgHandler){
            this.client = new IOClient(msgHandler);
        }

        @Override
        public ServerProxy buildClient() {
            setSocket(new Socket())
            .setAddress(new InetSocketAddress(PORT))
            .setQueue(new ClientMessageQueue())
            .connect();

            return client;
        }

        @Override
        public ClientBuilder setSocket(Socket socket) {
            this.client.setSocket(socket);
            return this;
        }

        @Override
        public ClientBuilder setAddress(InetSocketAddress address) {
            this.client.setAddress(address);
            return this;
        }

        @Override
        public ClientBuilder setQueue(ClientMessageQueue queue) {
            this.client.setQueue(queue);
            return this;
        }

        @Override
        public ClientBuilder connect() {
            try{
                this.client.connect();
            }catch(Exception ex){
                System.out.println("hewo hewo hewo... Owo | Couldn't connect to server.");
            }
            return this;
        }
    }

    public static class IOServerBuilder implements ServerBuilder {
        private IOServer server;

        public IOServerBuilder(ServerApplicationInterface msgHandler){
            this.server = new IOServer(msgHandler);
        }

        @Override
        public Server buildServer() {
            setMessageQueue(new ServerMessageQueue());

            try{
                setServerSocket(new ServerSocket(PORT));
                startServer();
            }catch(Exception ex){
                System.out.println("*Tinkering* uwu *sowy* | Server couldn't be created.");
            }

            return server;
        }

        @Override
        public ServerBuilder setMessageQueue(ServerMessageQueueInterface msgQueue) {
            this.server.setMessageQueue(msgQueue);
            return this;
        }

        @Override
        public ServerBuilder setServerSocket(ServerSocket serverSocket) {
            this.server.setServerSocket(serverSocket);
            return this;
        }

        @Override
        public ServerBuilder startServer() {
            try{
                this.server.startListening();
            }catch(Exception ex){
                System.out.println("*Suuu* *BREAKS* uwu *sowy* | Couldn't start listening thread for server.");
            }

            return this;
        }
    }
}
