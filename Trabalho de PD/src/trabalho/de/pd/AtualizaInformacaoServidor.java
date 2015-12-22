/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalho.de.pd.servidor.ListaFicheiros;

/**
 *
 * @author Carlos Oliveira
 */
public class AtualizaInformacaoServidor extends Thread {

    Cliente cliente = null;
    
    public AtualizaInformacaoServidor(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public void run() {
        ObjectInputStream  in;
        while (true) {
            //try {
                cliente.atualizaListaFicheirosCliente();
                System.out.println(" actualiza: "+cliente.getListaFicheirosCliente().getSize());
                /*in = new ObjectInputStream(cliente.getSocketAtualizaInformacao().getInputStream());
                ListaFicheiros listaFicheirosServidor = (ListaFicheiros) in.readObject();
                cliente.setListaFicheirosServidor(listaFicheirosServidor
            } catch (IOException ex) {
                Logger.getLogger(AtualizaInformacaoServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AtualizaInformacaoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }
    
}
