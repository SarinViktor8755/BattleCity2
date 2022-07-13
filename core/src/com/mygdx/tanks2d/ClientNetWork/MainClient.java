package com.mygdx.tanks2d.ClientNetWork;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.tanks2d.ClientNetWork.VoiceChat.VoiceChatClient;
import com.mygdx.tanks2d.MainGame;
import com.mygdx.tanks2d.Units.Tanks.OpponentsTanks;
import com.mygdx.tanks2d.Utils.VectorUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.TreeMap;


public class MainClient {
    public float aplphaConnect;
    private Client client;   //клиент
    private boolean onLine;
    private MainGame mg;
    private int myIdConnect;
    private RouterSM routerSM;


    private NetworkPacketStock networkPacketStock;
    public TreeMap<Integer, Network.PleyerPositionNom> otherPlayer;
    public HashMap<Integer, Boolean> frameUpdates; //Обновления кадра для играков
//    public ArrayDeque<PacketModel> inDequePacket; // входящие пакеты для обработки;

    public MainClient(MainGame mg) {
        this.mg = mg;
        routerSM = new RouterSM(mg);


        client = new Client();
//        client.start();


        new ClientThread(client);


        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client);

        frameUpdates = new HashMap<Integer, Boolean>();
        try {
            client.connect(5000, Network.host, Network.tcpPort, Network.udpPort);
            // Server communication after connection can go here, or in Listener#connected().
        } catch (IOException ex) {
            //  ex.printStackTrace();

        } catch (RuntimeException e3) {
            System.out.println("An error occurred please try again!");
        }


        otherPlayer = new TreeMap<>();
        onLine = true;


        this.networkPacketStock = new NetworkPacketStock(client);
        // this.startClient();

        client.addListener(new Listener() {
            public void connected(Connection connection) {
                setMyIdConnect(connection.getID());
            }

            public void received(Connection connection, Object object) {
                router(object);
            }

            public void disconnected(Connection connection) {
            }
        });
        //   Sytem.out.printlsn(client.isConnected() + "!!!!!!!!!!!!!!!1");
    }

    private void startClient() {
        //    System.out.println(client.isConnected());
        this.client = new Client();
        Network.register(client);
        this.client.start();
    }


    public int getMyIdConnect() {
        return myIdConnect;
    }

    public void setMyIdConnect(int myIdConnect) {
        this.myIdConnect = myIdConnect;
    }

    public Client getClient() {
        return client;
    }

    public void router(Object object) {
        if (!onLine) return;
        if (object instanceof Network.PleyerPositionNom) { // полученеи позиции играков
            Network.PleyerPositionNom pp = (Network.PleyerPositionNom) object;
            frameUpdates.put(pp.nom, true);
            //  System.out.println(pp.nom);
            if (pp.nom == client.getID()) return;
            // System.out.println("PleyerPositionNom");
            try {
                try {
                    OpponentsTanks t = mg.getGamePlayScreen().getTanksOther().getTankForID(pp.nom);
                    mg.getGamePlayScreen().getTanksOther().setTankPosition(pp, mg.getMainClient().frameUpdates.get(pp.nom));
                } catch (NullPointerException e) {

                }

                //mg.getMainClient().frameUpdates.put(pp.nom, false); /// закрывает флаг о рендере __
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return;
        }

        if (object instanceof Network.StockMessOut) {
            //    System.out.println((Network.StockMessOut) object);
            try {
                routerSM.routeSM((Network.StockMessOut) object);
            } catch (NullPointerException e) {
               // e.printStackTrace();
            }

        }


    }


    public boolean isOnLine() {
        return true;
    }

    public void upDateClient() {

    }

    public NetworkPacketStock getNetworkPacketStock() {
        return this.networkPacketStock;
    }

    public boolean checkConnect() {
        boolean result = true;
        //     System.out.println(NetworkPacketStock.required_to_send_tooken);
        getNetworkPacketStock().toSendMyNikAndTokken(); // отправка ника и токкена
        //   if (!getClient().isConnected()) NetworkPacketStock.required_to_send_tooken = true;

        if (!getClient().isConnected()) {
            result = false;
            if (MathUtils.randomBoolean(.05f)) {
                try {
                    System.out.println("reconect");
                    getClient().reconnect(5000);
                    NetworkPacketStock.required_to_send_tooken = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


}
