package network;

import protocol.client2server.JoinGame;
import protocol.server2client.PlayerJoined;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import client.ServerProxy;
import io.SocketBuilder;
import logger.ConsoleLogger;
import logger.Logger;

public class ClientMain {

    public static void main(String[] args) throws InterruptedException {

        Logger.getInstance().addObserver(new ConsoleLogger());
        ServerProxy client = new SocketBuilder.IOClientBuilder(message -> System.out.println("dsfasdf")).buildClient();

        System.out.println("Good");

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.send(new JoinGame("asdf"));
        }

    }

}
