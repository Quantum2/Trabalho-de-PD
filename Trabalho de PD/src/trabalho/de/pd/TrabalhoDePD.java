/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Rafael
 */
public class TrabalhoDePD {
    /**
     * @param args the command line arguments
     */  
    public static void main(String[] args) {
        
        Cliente cliente = new Cliente();
        //autentica deve devolver as informacoes do servidor
        cliente.autentica();
        cliente.ligacaoServidor(/*Informacoes do servidor*/);
        
    }
}