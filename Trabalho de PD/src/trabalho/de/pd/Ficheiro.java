/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

/**
 *
 * @author LittleBeast7
 */
public class Ficheiro {
    String nome;
    long bytes;
    String extensao;
    
    Ficheiro(String nome,long bytes,String extensao) {
        this.nome=nome;
        this.bytes=bytes;
        this.extensao=extensao;
    }
    
    public String getNome() {
        return nome;
    }
    
    public long getBytes() {
        return bytes;
    }
    
    public String getExtensao() {
        return extensao;
    }
}
