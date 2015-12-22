/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.net.Socket;
import java.util.Observable;
import trabalho.de.pd.servidor.ListaFicheiros;

/**
 *
 * @author Carlos Oliveira
 */
public class CascaCliente extends Observable {
    
    Cliente c;
    
    public CascaCliente(Cliente c) {
        
        this.c = c;
        setChanged();
        notifyObservers();
    }
    
    public void inicializa(String username,String password) {
        this.c.inicializa(username, password);
        setChanged();
        notifyObservers();
    }
    
    public void autentica(String username,String password) {
        this.c.autentica(username,password);
        setChanged();
        notifyObservers();
    }
    
    public void ligacaoServidor() {
        this.c.ligacaoServidor();
        setChanged();
        notifyObservers();
    }
    
    public void recebeListaFicheiros() {
        this.c.recebeListaFicheiros();
        setChanged();
        notifyObservers();
    }
    
    public void enviaPedido(String nomeFicheiro, int tipoPedido) {
        this.c.enviaPedido(nomeFicheiro, tipoPedido);
        setChanged();
        notifyObservers();
    }
    
    public void downloadFicheiro(String fileToGet) {
        this.c.downloadFicheiro(fileToGet);
        setChanged();
        notifyObservers();
    }
    
    public void uploadFicheiro(String fileToGet) {
        this.c.uploadFicheiro(fileToGet);
        setChanged();
        notifyObservers();
    }
    
    public void visualizarFicheiro(String fileToGet) {
        this.c.visualizarFicheiro(fileToGet);
        setChanged();
        notifyObservers();
    }
    
    public void setListaFicheirosServidor(ListaFicheiros listaFicheirosServidor) {
        this.c.setListaFicheirosServidor(listaFicheirosServidor);
        setChanged();
        notifyObservers();
    }
    
    public void atualizaListaFicheirosCliente() {
        this.c.atualizaListaFicheirosCliente();
        setChanged();
        notifyObservers();
    }
    
    public String getLocalDirectoryPath() {
        String s = this.c.getLocalDirectoryPath();
        setChanged();
        notifyObservers();
        return s;
    }
    
    public Socket getSocketAtualizaInformacao() {
        Socket s = this.c.getSocketAtualizaInformacao();
        setChanged();
        notifyObservers();
        return s;
    }
    
    public ListaFicheiros getListaFicheirosCliente() {
        ListaFicheiros lf = this.c.getListaFicheirosCliente();
        //setChanged();
        //notifyObservers();
        return lf;
    }
    
    public ListaFicheiros getListaFicheirosServidor() {
        ListaFicheiros lf = this.c.getListaFicheirosServidor();
        //setChanged();
        //notifyObservers();
        return lf;
    }
}
