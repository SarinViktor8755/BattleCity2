package com.mygdx.tanks2d.ClientNetWork;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.HashMap;

public class ClientThread extends Thread{
    Client client;

    public ClientThread(Client client) {
        this.start();
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("Hello, client!");
        client.run();
        Network.register(client);


        try {
            client.connect(5000, Network.host, Network.tcpPort, Network.udpPort);
            // Server communication after connection can go here, or in Listener#connected().
            System.out.println("1111");
        } catch (IOException ex) {
            //  ex.printStackTrace();
        }
    }
}
