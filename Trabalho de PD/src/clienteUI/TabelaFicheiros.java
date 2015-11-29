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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import trabalho.de.pd.*;

/**
 *
 * @author LittleBeast7
 */
public class TabelaFicheiros extends JPanel implements Observer,ListSelectionListener {

    Janela janela=null;
    Cliente cliente=null;
    JList list = null;
    DefaultListModel model = null;
    DefaultTableModel tableModel = null;
    JTable table = null;
    boolean souServidor;
    
    public TabelaFicheiros(Janela janela,Cliente cliente,boolean souServidor) {
        this.janela=janela;
        this.cliente=cliente;
        this.souServidor=souServidor;
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(this);
        tableModel.addColumn("Nome");
        tableModel.addColumn("Tamanho");
        tableModel.addRow(new Object[]{"asdasdasd.CENAS","14526 bytes"});
        tableModel.addRow(new Object[]{"csasdds.CENAS","54526 bytes"});
        JScrollPane pane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(pane);
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
    }
    
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent");
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tableModel.addColumn("Nome");
        tableModel.addColumn("Tamanho");
        File localDirectory = new File(cliente.getLocalDirectoryPath());
        File[] listaFicheiros = localDirectory.listFiles();
        //tableModel.setRowCount(listaFicheiros.length);
        Object[] o = {"Cenas","1"};
        tableModel.addRow(new Object[]{"v1","v2"});
        tableModel.addRow(o);
        /*
        for (File ficheiro : listaFicheiros) {
            Object[] p = {ficheiro.getName(),ficheiro.length()};
            tableModel.addRow(p);
        }
        */
    }
    public String getSelected() {
        return (String) table.getValueAt(table.getSelectedRow(),table.getSelectedColumn());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
            janela.deselectTable(souServidor);
    }
    
    public void deselectTable() {
        table.clearSelection();
    }
}
