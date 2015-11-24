/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Carlos Oliveira
 */
public class Cliente {
    final static String hostnameDiretoria = "225.15.15.15";
    final static int portDiretoria = 7000;
    
    Socket servidorPrincipal = null;
    ClienteInfo cinfo = null;
    ListaFicheiros listaFicheirosServidor = null;
    
    public void autentica() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        String username = sc.next();
        System.out.println("Password: ");
        String password = sc.next();
        cinfo = new ClienteInfo(username,password);
        try {
            System.out.println("Binding to a local port");
            // CREATE A DATAGRAM SOCKET, BOUND TO ANY AVAILABLE LOCAL PORT
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Bound to local port " + socket.getLocalPort());
            
            // CREATE A DATAGRAM PACKET, CONTAINING OUR BYTE ARRAY
            ByteArrayOutputStream bOut = new ByteArrayOutputStream(1000);
            ObjectOutputStream out = new ObjectOutputStream(bOut);
            out.writeObject(cinfo);
            DatagramPacket packet = new DatagramPacket(bOut.toByteArray(), bOut.size());
            System.out.println("Looking up hostname " + hostnameDiretoria);
            

            // LOOKUP THE SPECIFIED HOSTNAME, AND GET AN INETADDRESS
            InetAddress addr = InetAddress.getByName(hostnameDiretoria);
            System.out.println("Hostname resolved as " + addr.getHostAddress());
            // ADDRESS PACKET TO SENDER
            packet.setAddress(addr);
            // SET PORT NUMBER TO 7000
            packet.setPort(portDiretoria);
            // SEND THE PACKET - REMEMBER NO GUARANTEE OF DELIVERY
            socket.send(packet);
            System.out.println("Packet sent!");
            
            socket.receive(packet);
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            in.readObject();
        } catch (UnknownHostException e) {
            System.err.println("Can't find host " + hostnameDiretoria);
        } catch (IOException e) {
            System.err.println("Error - " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("Error - " + e);
        }
    }
    
    public void ligacaoServidor(/*Informacoes servidor*/) {
        
        // GET THE HOSTNAME OF SERVER
        String hostServidor = "placeholder";
        int portServidor = 1231;
        
        try {
            servidorPrincipal = new Socket(hostServidor, portServidor);
            System.out.println("Connection established");
            // SET THE SOCKET OPTION JUST IN CASE SERVER STALLS
            servidorPrincipal.setSoTimeout(2000); //ms
            // READ FROM THE SERVER
            //BufferedReader reader = new BufferedReader(new InputStreamReader(servidorPrincipal.getInputStream()));
            //System.out.println("Results : " + reader.readLine());
            // CLOSE THE CONNECTION
            //servidorPrincipal.close();
        } catch (IOException e) { //catches also InterruptedIOException
            System.err.println("Error " + e);
        }
    }
    
    public void setListaFicheirosServidor(ListaFicheiros listaFicheirosServidor) {
        this.listaFicheirosServidor=listaFicheirosServidor;
    }
}
