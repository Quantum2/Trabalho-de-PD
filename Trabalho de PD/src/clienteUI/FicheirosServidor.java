/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import trabalho.de.pd.*;

/**
 *
 * @author LittleBeast7
 */
public class FicheirosServidor extends JPanel implements Observer {

    Janela janela=null;
    Cliente cliente=null;
    JList list;
    DefaultListModel model;
    
    public FicheirosServidor(Janela janela,Cliente cliente) {
        this.janela=janela;
        this.cliente=cliente;
        model=new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);
        for (int i = 0; i < 15; i++)
            model.addElement("Element " + i);
        setLayout(new BorderLayout());
        add(pane);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        janela.repaint();
    }
    
    public void paintComponent(Graphics g) {
        
        
    }
    
}
