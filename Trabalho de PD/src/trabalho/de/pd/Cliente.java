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
    final static String HOSTNAME_DIRETORIA = "225.15.15.15";
    final static int PORT_DIRETORIA = 7000;
    
    Socket servidorPrincipal = null;
    ClienteInfo clienteInfo = null;
    ListaFicheiros listaFicheirosServidor = null;
    Thread atualizaFicheirosServidor = null;
    ServidorInfo servidorInfo = null;
    
    public void inicializa(String username,String password) {
        autentica(username,password);
        ligacaoServidor();
        AtualizaInformacaoServidor runnableAtualizador = new AtualizaInformacaoServidor(this);
        atualizaFicheirosServidor = new Thread(runnableAtualizador);
    }
    
    void autentica(String username,String password) {
        //Scanner sc = new Scanner(System.in);
        System.out.println("Username: ");
        //String username = sc.next();
        System.out.println("Password: ");
        //String password = sc.next();
        clienteInfo = new ClienteInfo(username,password);
        try {
            System.out.println("Binding to a local port");
            // CREATE A DATAGRAM SOCKET, BOUND TO ANY AVAILABLE LOCAL PORT
            DatagramSocket socket = new DatagramSocket();
            System.out.println("Bound to local port " + socket.getLocalPort());
            
            // CREATE A DATAGRAM PACKET, CONTAINING OUR BYTE ARRAY
            ByteArrayOutputStream bOut = new ByteArrayOutputStream(1000);
            ObjectOutputStream out = new ObjectOutputStream(bOut);
            out.writeObject(clienteInfo);
            out.flush();
            DatagramPacket packet = new DatagramPacket(bOut.toByteArray(), bOut.size());
            System.out.println("Looking up hostname " + HOSTNAME_DIRETORIA);
            
            // LOOKUP THE SPECIFIED HOSTNAME, AND GET AN INETADDRESS
            InetAddress addr = InetAddress.getByName(HOSTNAME_DIRETORIA);
            System.out.println("Hostname resolved as " + addr.getHostAddress());
            // ADDRESS PACKET TO SENDER
            packet.setAddress(addr);
            // SET PORT NUMBER TO 7000
            packet.setPort(PORT_DIRETORIA);
            // SEND THE PACKET - REMEMBER NO GUARANTEE OF DELIVERY
            socket.send(packet);
            System.out.println("Packet sent!");
            
            socket.receive(packet);
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            servidorInfo = (ServidorInfo)in.readObject();
        } catch (UnknownHostException e) {
            System.err.println("Can't find host " + HOSTNAME_DIRETORIA);
        } catch (IOException e) {
            System.err.println("Error - " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("Error - " + e);
        }
    }
    
    public void pedeFicheiro() {
        
    }
    
    void ligacaoServidor() {
        
        // GET THE HOSTNAME OF SERVER
        String hostServidor = servidorInfo.getIP();
        int portServidor = servidorInfo.getPort();
        
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
