/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

/**
 *
 * @author Carlos Oliveira
 */
public class ServidorInfo {
    String ip;
    int port;
    
    public ServidorInfo(String ip,int port) {
        this.ip=ip;
        this.port=port;
    }
    
    public String getIP() {
        return ip;
    }
    
    public int getPort() {
        return port;
    }
}
