/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fcnproject2fall;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.DigestInputStream;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Hitesh Mohite
 * 
 */

public class client_fcn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, NoSuchAlgorithmException {
        // TODO code application logic here
        
        String[] params = new String[6];
        /*if(args[0].contains("c"))
        {
            String[] params = new String[6];
            boolean timer = false, quiet_status = false;
            int length = args.length;
         
            //stores the server port number
            params[5] = args[length - 1];
            //stores the client port number
            params[4] = args[length - 1];
            //stores the server address
            params[3] = args[length - 2];
            //stores file or file path
            params[0] = args[1];
                
            for(int i = 0; i < length; i++)
            {
                if(args[i].contains("t") || args[i].contains("timeout"))
                {
                    timer = true;
                    //sets the time out of a program
                    params[1] = args[i+1];
                }
                if(args[i].contains("q") || args[i].contains("quiet"))
                {
                    quiet_status = true;
                    //sets the diagnostics display
                    params[2] = "q";
                }
            }
            
            if(!timer)
            {
                params[1] = "1000";
            }
            if(!quiet_status)
            {
                params[2] = "nq";
            }
            new client_does(params);
        }
        else
        {
            System.out.println("Invalid commands");
        }*/
        new client_does(params);
    }
}
class client_does
{
    String[] args;
    
    int client_port = 0, server_port = 0, store_numerics = 0;
    int packet_count = 0;
    String exit_string = "exit", store_data = "", temp = "", quiet_status = "";
    boolean value = true;
    byte[] data_to_send, data_to_receive, store_acks;
    DatagramSocket socket;
    InetAddress server_terminal;
    DatagramPacket packet_to_send, packet_to_receive;
    BufferedReader read_input;
    
    public client_does(String[] params) throws SocketException, UnknownHostException, IOException, NoSuchAlgorithmException
    {
        args = params;
        //client_port = Integer.parseInt(params[4]);
        //
        server_port = Integer.parseInt("5000");
        //socket = new DatagramSocket(4000);
        //quiet_status = params[2];
        //server_terminal = InetAddress.getByName(params[3]);
        server_terminal = InetAddress.getByName("localhost");
        start_client();
    }
    
    public void start_client() throws SocketException, IOException, NoSuchAlgorithmException
    {
        //socket = new DatagramSocket(client_port);
        socket = new DatagramSocket(4000);
        
        
        //sending the file to server
        
        byte[] data_to_send = new byte[1024];
        FileInputStream f_in = new FileInputStream("C:\\Users\\Hitesh Mohite\\Documents\\NetBeansProjects\\fcnproject2fall\\src\\fcnproject2fall\\new_file.txt");
        int read = 0, total_bytes = 0;
        String recognizer = "XXXACK";
        while((read = (f_in.read(data_to_send))) != -1)
        {
            packet_count += 1; 
            //System.out.println(new String(data_to_send));
            total_bytes = total_bytes + read;
            //data_to_send = "connected??".getBytes();
            String packet_head = recognizer + String.valueOf(packet_count);
            String data_read = new String(data_to_send) + packet_head;
            byte[] modified_data = data_read.getBytes();
            System.out.println("ACK"+packet_count);
            packet_to_send = new DatagramPacket(modified_data, modified_data.length, server_terminal, server_port);
            socket.send(packet_to_send);
        }
        System.out.println("Total packets sent : "+ packet_count);
        data_to_send = "hitesh".getBytes();
        packet_to_send = new DatagramPacket(data_to_send, data_to_send.length, server_terminal, server_port);
        socket.send(packet_to_send);
        //System.out.println("total bytes are "+total_bytes);
        
        //generating hash code for a file
        
        
        /*data_to_receive = new byte[1024];
        packet_to_receive = new DatagramPacket(data_to_receive, data_to_receive.length);
        socket.receive(packet_to_receive);
        store_data = new String(packet_to_receive.getData());
        System.out.println(store_data);*/
        
        MessageDigest digesting = MessageDigest.getInstance("MD5");
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
      
        //Read file data and update in message digest
        while ((bytesCount = f_in.read(byteArray)) != -1) 
        {
            digesting.update(byteArray, 0, bytesCount);
        };
     
        //close the stream; We don't need it now.
        f_in.close();
     
        //Get the hash's bytes
        byte[] bytes = digesting.digest();
     
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb.toString());
    }
}