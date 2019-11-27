package network;

import protocol.server2client.PlayerJoined;

import java.io.IOException;

import io.SocketBuilder;
import logger.ConsoleLogger;
import logger.Logger;

public class Main{
    public static void main(String[] args) throws IOException, InterruptedException {

        Logger.getInstance().addObserver(new ConsoleLogger());
        server.Server server = new SocketBuilder.IOServerBuilder((message, connectionId) -> System.out.println(connectionId + " : sent message")).buildServer();

        System.out.println("Good");

        while(true){
            Thread.sleep(1000);
            server.broadcast(new PlayerJoined("asdf", 0,0));
        }

    }
}
