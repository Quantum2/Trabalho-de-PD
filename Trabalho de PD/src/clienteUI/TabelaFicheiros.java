/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import trabalho.de.pd.*;

/**
 *
 * @author LittleBeast7
 */
public class TabelaFicheiros extends JPanel implements Observer {

    Janela janela=null;
    Cliente cliente=null;
    JList list;
    DefaultListModel model;
    DefaultTableModel tableModel;
    JTable table;
    
    public TabelaFicheiros(Janela janela,Cliente cliente) {
        this.janela=janela;
        this.cliente=cliente;
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Nome");
        tableModel.addColumn("Tamanho");
        repaint();
        /*
        model=new DefaultListModel();
        list = new JList(model);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(10);
        JScrollPane pane = new JScrollPane(list);
        for (int i = 0; i < 15; i++)
            model.addElement("Element " + i);
        setLayout(new BorderLayout());
        add(pane);
        */
        JScrollPane pane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(pane);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        janela.repaint();
    }
    
    public void paintComponent(Graphics g) {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Tamanho");
        File localDirectory = new File(cliente.getLocalDirectoryPath());
        File[] listaFicheiros = localDirectory.listFiles();
        tableModel.setRowCount(listaFicheiros.length);
        Object[] o = {"Cenas","1"};
        tableModel.addRow(o);
        for (File ficheiro : listaFicheiros) {
            Object[] p = {ficheiro.getName(),ficheiro.length()};
            tableModel.addRow(p);
        }
        
    }
    
}
