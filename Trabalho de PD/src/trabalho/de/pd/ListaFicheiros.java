/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import java.util.ArrayList;

/**
 *
 * @author LittleBeast7
 */
public class ListaFicheiros {
    ArrayList<Ficheiro> listaFicheiros = null;
    
    public void addFicheiro(Ficheiro ficheiro) {
        listaFicheiros.add(ficheiro);
    }
    
    public Ficheiro getFicheiro(int n) {
        return listaFicheiros.get(n);
    }
    
    public Ficheiro removeFicheiro(int n) {
        return listaFicheiros.remove(n);
    }
}