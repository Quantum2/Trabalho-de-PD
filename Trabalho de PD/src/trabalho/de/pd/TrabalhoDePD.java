/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 *
 * @author Rafael
 */
public class TrabalhoDePD {
    /**
     * @param args the command line arguments
     */
    public void send() throws IOException
    {
        ByteArrayOutputStream byteout=new ByteArrayOutputStream(MAX_SIZE);
        ObjectOutputStream send=new ObjectOutputStream(byteout);
        send.writeObject(sendMSG);
        packet.setData(byteout.toByteArray());
        packet.setLength(byteout.size());
        socket.send(packet);
    }
    public static void main(String[] args) {
        String hostname = "225.15.15.15";
        int port = 7000;
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.next();
        System.out.println("Password: ");
        String password = sc.next();
        ClienteInfo cinfo = new ClienteInfo(username,password);
        try {
            System.out.println("Binding to a local port");
            // CREATE A DATAGRAM SOCKET, BOUND TO ANY AVAILABLE LOCAL PORT
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Bound to local port " + socket.getLocalPort());
            
            // CREATE A DATAGRAM PACKET, CONTAINING OUR BYTE ARRAY
            ByteArrayOutputStream bOut = new ByteArrayOutputStream(1000);
            ObjectOutputStream out = new ObjectOutputStream(bOut);
            out.writeObject(cinfo);
            DatagramPacket packet = new DatagramPacket();
            System.out.println("Looking up hostname " + hostname);
            

            // LOOKUP THE SPECIFIED HOSTNAME, AND GET AN INETADDRESS
            InetAddress addr = InetAddress.getByName(hostname);
            System.out.println("Hostname resolved as " + addr.getHostAddress());
            // ADDRESS PACKET TO SENDER
            packet.setAddress(addr);
            // SET PORT NUMBER TO 7000
            packet.setPort(7000);
            // SEND THE PACKET - REMEMBER NO GUARANTEE OF DELIVERY
            socket.send(packet);
            System.out.println("Packet sent!");
        } catch (UnknownHostException e) {
            System.err.println("Can't find host " + hostname);
        } catch (IOException e) {
            System.err.println("Error - " + e);
        }
    }
}
