/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.de.pd;

import trabalho.de.pd.servidor.ClienteInfo;
import java.awt.Desktop;
import static java.awt.Desktop.getDesktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import trabalho.de.pd.servidor.Ficheiro;
import trabalho.de.pd.servidor.HeartBeat;
import trabalho.de.pd.servidor.ListaFicheiros;
import trabalho.de.pd.servidor.Pedido;
import trabalho.de.pd.servidor.Servidor;

/**
 *
 * @author Carlos Oliveira
 */
public class Cliente {
    static String HOSTNAME_DIRETORIA = "localhost";
    static int PORT_DIRETORIA = 7001;
    final static int MAX_SIZE = 4000;
    final static int TIMEOUT = 5;
    
    //socket principal
    Socket socketServidor = null;
    OutputStream principalOut = null;
    PrintStream principalPrintStream = null;
    ObjectOutputStream principalOOS = null;
    //socket a correr na thread AtualizaInformacao para atualizar ficheiros disponiveis no servidor
    Socket socketAtualizaInformacao = null;
    ClienteInfo clienteInfo = null;
    ListaFicheiros listaFicheirosServidor = null;
    ListaFicheiros listaFicheirosCliente = null;
    Thread atualizaFicheirosServidor = null;
    HeartBeat servidorInfo = null;
    File localDirectory = null;
    String localDirectoryPath = null;
    
    public Cliente(String diretoria, String ip, int port) {
        //Path currentRelativePath = Paths.get("");
        //localDirectoryPath = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + localDirectoryPath);
        localDirectoryPath = diretoria;
        HOSTNAME_DIRETORIA = ip;
        PORT_DIRETORIA = port;
        localDirectory=new File(localDirectoryPath);
        for(File file: localDirectory.listFiles()) file.delete();
        atualizaListaFicheirosCliente();
    }
        
    public void inicializa(String username,String password) {
        autentica(username,password);
        ligacaoServidor();
        //AtualizaInformacaoServidor runnableAtualizador = new AtualizaInformacaoServidor(this);
        //atualizaFicheirosServidor = new Thread(runnableAtualizador);
        //atualizaFicheirosServidor.start();
    }
    
    public void autentica(String username,String password) {
        //Scanner sc = new Scanner(System.in);
        System.out.println("Username: " + username);
        //String username = sc.next();
        System.out.println("Password: " + password);
        //String password = sc.next();
        clienteInfo = new ClienteInfo(username,password);
        try {
            System.out.println("Binding to a local port");
            // CREATE A DATAGRAM SOCKET, BOUND TO ANY AVAILABLE LOCAL PORT
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            System.out.println("Bound to local port " + socket.getLocalPort());
            
            // CREATE A DATAGRAM PACKET, CONTAINING OUR BYTE ARRAY
            ByteArrayOutputStream bOut = new ByteArrayOutputStream(1000);
            ObjectOutputStream out = new ObjectOutputStream(bOut);
            out.writeObject(clienteInfo);
            out.flush();
            DatagramPacket packet = new DatagramPacket(bOut.toByteArray(), bOut.size());
            System.out.println("Looking up hostname " + HOSTNAME_DIRETORIA);
            
            // LOOKUP THE SPECIFIED HOSTNAME, AND GET AN INETADDRESS
            InetAddress addr = InetAddress.getByName(HOSTNAME_DIRETORIA);
            System.out.println("Hostname resolved as " + addr.getHostAddress());
            // ADDRESS PACKET TO SENDER
            packet.setAddress(addr);
            // SET PORT NUMBER TO 7000
            packet.setPort(PORT_DIRETORIA);
            // SEND THE PACKET - REMEMBER NO GUARANTEE OF DELIVERY
            socket.send(packet);
            System.out.println("Packet sent!");
            
            packet=new DatagramPacket(new byte[1000],1000);
            socket.receive(packet);
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            servidorInfo = (HeartBeat)in.readObject();
        } catch (UnknownHostException e) {
            System.err.println("Can't find host " + HOSTNAME_DIRETORIA);
        } catch (IOException e) {
            System.err.println("Error - " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("Error - " + e);
        }
    }
    
    public void ligacaoServidor() {
        
        // GET THE HOSTNAME OF SERVER
        String hostServidor = servidorInfo.getEndereço().getHostAddress();
        int portServidor = servidorInfo.getTcpPort();
        
        try {
            socketServidor = new Socket(hostServidor, portServidor);
            System.out.println("Connection established");
            // SET THE SOCKET OPTION JUST IN CASE SERVER STALLS
            socketServidor.setSoTimeout(TIMEOUT*1000); // 5 * 1000 = 5000ms pq e preciso tempo para o servidor responder
            principalOut = socketServidor.getOutputStream();
            principalPrintStream = new PrintStream(principalOut);
            principalOOS = new ObjectOutputStream(principalOut);
            recebeListaFicheiros();
            // READ FROM THE SERVER
            //BufferedReader reader = new BufferedReader(new InputStreamReader(servidorPrincipal.getInputStream()));
            //System.out.println("Results : " + reader.readLine());
            // CLOSE THE CONNECTION
            //servidorPrincipal.close();
            //socketAtualizaInformacao = new Socket(hostServidor, portServidor);
            //socketAtualizaInformacao.setSoTimeout(TIMEOUT*400); // 5 * 400 = 2000ms
            //recebeListaFicheiros();
        } catch (IOException e) { //catches also InterruptedIOException
            System.err.println("Error " + e);
        }
    }
    
    public void recebeListaFicheiros() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socketServidor.getInputStream());
            listaFicheirosServidor = (ListaFicheiros)ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviaPedido(String nomeFicheiro, int tipoPedido) {
        Pedido novoPedido = new Pedido(nomeFicheiro,tipoPedido);
        try {
            principalOOS.writeObject(novoPedido);
            principalOOS.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void downloadFicheiro(String fileToGet) {
        
        String fileName, localFilePath = null;
        FileOutputStream localFileOutputStream = null;
        Socket socketToServer = null;
        PrintWriter pout;
        InputStream in;
        byte []fileChunck = new byte[MAX_SIZE];
        int nbytes;
        /*
        if(args.length != 4){
            System.out.println("Sintaxe: java GetFileTcpClient serverAddress serverTcpPort fileToGet localDirectory");
            return;
        }        
        */
        fileName = fileToGet.trim();
        localDirectory = new File(localDirectoryPath);
        
        if(!localDirectory.exists()){
            System.out.println("A directoria " + localDirectory + " nao existe!");
            return;
        }
        
        if(!localDirectory.isDirectory()){
            System.out.println("O caminho " + localDirectory + " nao se refere a uma directoria!");
            return;
        }
        
        if(!localDirectory.canWrite()){
            System.out.println("Sem permissoes de escrita na directoria " + localDirectory);
            return;
        }
        
        if (new File(fileToGet).exists()) {
            //Avisar algures que ficheiro já existe no lado do cliente
            System.out.println("Ficheiro já existe, não irá ser feito o seu download outra vez.");
            return;
        }
        
        enviaPedido(fileToGet,Pedido.DOWNLOAD);
        
            try{
                /*
                serverPort = Integer.parseInt(args[1]);
                
                socketToServer = new Socket(args[0], serverPort);
                
                socketToServer.setSoTimeout(TIMEOUT*1000);
                */
                
                /*pout = new PrintWriter(socketServidor.getOutputStream(), true);                
                pout.println(fileName);
                pout.flush();*/
                
                localFilePath = localDirectory.getCanonicalPath()+File.separator+fileName;
                localFileOutputStream = new FileOutputStream(localFilePath);
                System.out.println("Ficheiro " + localFilePath + " criado.");
                in = socketServidor.getInputStream();
                while((nbytes = in.read(fileChunck)) > 0){                    
                    //System.out.println("Recebido o bloco n. " + ++contador + " com " + nbytes + " bytes.");
                    localFileOutputStream.write(fileChunck, 0, nbytes);
                    //System.out.println("Acrescentados " + nbytes + " bytes ao ficheiro " + localFilePath+ ".");                    
                }                    
                
                System.out.println("Transferencia concluida.");

            }catch(UnknownHostException e){
                 System.out.println("Destino desconhecido:\n\t"+e);
            }catch(NumberFormatException e){
                System.out.println("O porto do servidor deve ser um inteiro positivo:\n\t"+e);
            }catch(SocketTimeoutException e){
                System.out.println("Não foi recebida qualquer bloco adicional, podendo a transferencia estar incompleta:\n\t"+e);
            }catch(SocketException e){
                System.out.println("Ocorreu um erro ao nível do socket TCP:\n\t"+e);
            }catch(IOException e){
                System.out.println("Ocorreu um erro no acesso ao socket ou ao ficheiro local " + localFilePath +":\n\t"+e);
            }finally {
                atualizaListaFicheirosCliente();
            }     
   }
    
    public void uploadFicheiro(String fileToGet) {
        enviaPedido(fileToGet,Pedido.UPLOAD);
        
        try {
            ObjectInputStream ois = new ObjectInputStream(socketServidor.getInputStream());
            Pedido pedido = (Pedido)ois.readObject();
            if (pedido.isAceite()) {
                System.out.println("[CLIENTE] Pedido Upload aceite!");
                FileInputStream fileIn = new FileInputStream(localDirectoryPath + "\\" + pedido.getNomeFicheiro());
                int nbytes;
                byte[] filechunck = new byte[Servidor.MAX_SIZE];
                OutputStream outputFicheiro;
                if (pedido.getSocketPrimario()!=null) {
                    outputFicheiro = pedido.getSocketPrimario().getOutputStream();
                } else {
                    outputFicheiro = socketServidor.getOutputStream();
                }
                while ((nbytes = fileIn.read(filechunck)) > 0) {
                    outputFicheiro.write(filechunck, 0, nbytes);
                    outputFicheiro.flush();
                }
            }
        } catch (IOException ex) {
            System.out.println("[CLIENTE - uploadFicheiro] Error - " + ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
                atualizaListaFicheirosCliente();
            try {
                sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
                atualizaListaFicheirosServidor();
            } 
    }
    
    public void visualizarFicheiro(String fileToGet) {
        //Path currentRelativePath = Paths.get("");
        //localDirectoryPath = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + localDirectoryPath);
        String fileName = fileToGet.trim();
        File teste = new File (localDirectoryPath+"\\"+fileName);
        if (teste.exists()) {
            Desktop dt = getDesktop();
            try {
                dt.open(teste);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setListaFicheirosServidor(ListaFicheiros listaFicheirosServidor) {
        this.listaFicheirosServidor=listaFicheirosServidor;
    }
    
    public void atualizaListaFicheirosServidor(){
        enviaPedido("",Pedido.ACTUALIZACAO);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(socketServidor.getInputStream());
            listaFicheirosServidor = (ListaFicheiros)ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void atualizaListaFicheirosCliente() {
        localDirectory = new File(localDirectoryPath);
        File[] listaFicheiros = localDirectory.listFiles();
         
        listaFicheirosCliente = new ListaFicheiros();
        
        for (File ficheiro : listaFicheiros) {
            Ficheiro ficheiroTemp = new Ficheiro(ficheiro.getName(),ficheiro.length());
            listaFicheirosCliente.addFicheiro(ficheiroTemp);
        }
    }
    
    public String getLocalDirectoryPath() {
        return localDirectoryPath;
    }
    
    public Socket getSocketAtualizaInformacao() {
        return socketAtualizaInformacao;
    }
    
    public ListaFicheiros getListaFicheirosCliente() {
        return listaFicheirosCliente;
    }
    
    public ListaFicheiros getListaFicheirosServidor() {
        return listaFicheirosServidor;
    }
    
    public void removeActualizacao(){
        try {
            sleep(5000);
            socketServidor.setSoTimeout(20000);
            ObjectInputStream ois=new ObjectInputStream(socketServidor.getInputStream());
            listaFicheirosServidor=(ListaFicheiros)ois.readObject();
            socketServidor.setSoTimeout(TIMEOUT*1000);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
}
