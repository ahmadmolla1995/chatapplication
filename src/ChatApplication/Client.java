package ChatApplication;


import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
    private static final int ServerPort = 1234;

    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);

        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, ServerPort);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread(() -> {
            while (true) {
                String msg = scn.next();
                try {
                    dos.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readMessage = new Thread(() -> {
            while (true) {
                try {
                    String msg = dis.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        sendMessage.start();
        readMessage.start();
    }
}

