/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteUI;

import trabalho.de.pd.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author LittleBeast7
 */
public class Janela extends JFrame {
    
    Cliente cliente;
    
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem iniciaClienteItem;
    JMenuItem sairItem;
    
    Janela thisJanela=this;
    
    JPanel painelPrincipal;
    JPanel painelListas;
    JPanel painelBotoes;
    
    JButton botaoDownload;
    JButton botaoUpload;
    JButton botaoVisualizar;
    JButton botaoEliminar;
    
    TabelaFicheiros listaServidor;
    TabelaFicheiros listaCliente;
    
    Janela(String nome, int x, int y, int h, int w, Cliente cliente) {
        super(nome);
        setSize(h, w);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));
        this.cliente = cliente;
        inicializaJanela();
    }
    
    public void deselectTable(boolean servidor) {
        if (servidor) {
            listaServidor.deselectTable();
        } else
            listaCliente.deselectTable();
    }

    void inicializaJanela() {
        
        Container cp = getContentPane();
        
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        cp.add(painelPrincipal);
        
        painelListas = new JPanel(new GridLayout(1,2));
        painelPrincipal.add(painelListas,BorderLayout.CENTER);
        
        listaServidor = new TabelaFicheiros(this,cliente,true);
        painelListas.add(listaServidor);
        listaCliente = new TabelaFicheiros(this,cliente,false);
        painelListas.add(listaCliente);
        
        painelBotoes = new JPanel(new GridLayout(2,2));
        botaoDownload = new JButton("Download");
        botaoUpload = new JButton ("Upload");
        botaoVisualizar = new JButton ("Visualizar");
        botaoEliminar = new JButton ("Eliminar");
        painelBotoes.add(botaoUpload);
        painelBotoes.add(botaoDownload);
        painelBotoes.add(botaoVisualizar);
        painelBotoes.add(botaoEliminar);
        painelPrincipal.add(painelBotoes,BorderLayout.SOUTH);
        
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        iniciaClienteItem = new JMenuItem("Autenticar");
        sairItem = new JMenuItem("Sair");

        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(iniciaClienteItem);
        menu.add(sairItem);

        iniciaClienteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField username = new JTextField();
                JPasswordField password = new JPasswordField();
                final JComponent[] inputs = new JComponent[]{
                    new JLabel("Utilizador"),
                    username,
                    new JLabel("Password"),
                    password
                };
                int n = JOptionPane.showConfirmDialog(null, inputs, "Autenticação", JOptionPane.OK_CANCEL_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    cliente.inicializa(username.getText(),String.copyValueOf(password.getPassword()));
                } else if (n == JOptionPane.NO_OPTION) {

                }                
            }
        });

        sairItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        botaoDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.downloadFicheiro(listaServidor.getSelected());
            }
        });
        
        botaoUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.uploadFicheiro(listaCliente.getSelected());
            }
        });
        
        botaoEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.enviaPedido(listaCliente.getSelected(), Pedido.ELIMINAR);
            }
        });
        
        botaoVisualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.visualizarFicheiro();
            }
        });
        setVisible(true);
    }
}
