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
        
        if (args.length != 3) {
            System.out.println("Argumentos Errados");
        } else {
            Cliente cliente = new Cliente(args[0], args[1], Integer.parseInt(args[2]));
            
            cliente.ligacaoServidor();
            AtualizaInformacaoServidor runnableAtualizador = new AtualizaInformacaoServidor(cliente);
            Thread atualizaFicheirosServidor = new Thread(runnableAtualizador);

            atualizaFicheirosServidor.join();
        }
    }
}