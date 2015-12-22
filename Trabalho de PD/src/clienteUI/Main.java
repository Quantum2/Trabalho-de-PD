/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteUI;

import trabalho.de.pd.*;
/**
 *
 * @author LittleBeast7
 */
public class Main {
    
    static Janela janela;
    
    public static void main (String args[]) {
        if (args.length != 3) {
            System.out.println("Argumentos Inválidos");
        } else {
            CascaCliente cliente = new CascaCliente(new Cliente(args[0],args[1],Integer.parseInt(args[2])));
            janela = new Janela("Programação Distribuida - Cliente", 300, 150, 640, 480, cliente);
        }
    }
    
}
