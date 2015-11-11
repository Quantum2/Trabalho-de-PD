/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Rafael
 */
public class TrabalhoDePD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String hostname = "225.15.15.15";
        try {
            System.out.println("Binding to a local port");
            // CREATE A DATAGRAM SOCKET, BOUND TO ANY AVAILABLE LOCAL PORT
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Bound to local port " + socket.getLocalPort());
            
            //Abordagem mais directa: 
            byte[] barray = "Greetings!".getBytes();
            
            // CREATE A DATAGRAM PACKET, CONTAINING OUR BYTE ARRAY
            DatagramPacket packet = new DatagramPacket(barray, barray.length);
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
