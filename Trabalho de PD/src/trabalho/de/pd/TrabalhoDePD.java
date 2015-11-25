/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

/**
 *
 * @author Rafael
 */
public class TrabalhoDePD {
    /**
     * @param args the command line arguments
     */  
    public static void main(String[] args) throws InterruptedException {
        
        Cliente cliente = new Cliente();
        //autentica deve devolver as informacoes do servidor
        //cliente.autentica();
        cliente.ligacaoServidor(/*Informacoes do servidor*/);
        AtualizaInformacaoServidor runnableAtualizador = new AtualizaInformacaoServidor(cliente);
        Thread atualizaFicheirosServidor= new Thread(runnableAtualizador);
        
        atualizaFicheirosServidor.join();
    }
}