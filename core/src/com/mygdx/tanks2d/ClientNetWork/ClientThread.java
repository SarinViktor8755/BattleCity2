package com.mygdx.tanks2d.ClientNetWork;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.tanks2d.ClientNetWork.VoiceChat.VoiceChatClient;

import java.io.IOException;
import java.util.HashMap;

public class ClientThread extends Thread {
    Client client;
    VoiceChatClient voiceChatClient;

    public ClientThread(Client client) {
        this.start();
        this.client = client;

    }

    public ClientThread(Client client, VoiceChatClient voiceChatClient) {
        this.start();
        this.client = client;
        this.voiceChatClient = voiceChatClient;

    }

    @Override
    public void run() {
        System.out.println("Hello, client!");
        client.run();
        Network.register(client);

        Kryo kryo = client.getKryo();
        voiceChatClient = new VoiceChatClient(kryo);

        // Finally, allow the client to play audio recieved from the server.
        voiceChatClient.addReceiver(client);
        try {
            client.connect(5000, Network.host, Network.tcpPort, Network.udpPort);
            voiceChatClient = new VoiceChatClient(client.getKryo());
            voiceChatClient.addReceiver(client);
            Network.register(client);
            // Server communication after connection can go here, or in Listener#connected().
            //    System.out.println("1111");
        } catch (IOException ex) {
            //  ex.printStackTrace();
        }
    }
}
