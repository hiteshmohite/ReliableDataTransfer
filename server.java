/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fcnproject2fall;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Hitesh Mohite
 */

/*
* references: http://howtodoinjava.com/2015/01/21/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
*
*/

public class server {

    /**
     * @param args the command line arguments
     */
    public static void fcn_server(String[] args) throws SocketException, IOException, NoSuchAlgorithmException {
        // TODO code application logic here
        main_server serve_client = new main_server(args);
    }
}
class main_server
{
    String[] args;
    
    int client_port, store_length = 0, packet_count = 0;
    String store_data = "", temp = "", quiet_status = "";
    String[] params;
    int port = 0;
    boolean value = true;
    byte[] data_to_send, data_to_receive;
    DatagramSocket socket;
    DatagramPacket packet_to_send, packet_to_receive;
    PrintWriter tofile;
    
    public main_server(String[] params) throws SocketException, IOException, NoSuchAlgorithmException 
    {
        args = params;
        port = Integer.parseInt(params[1]);
        quiet_status = params[0];
        socket = new DatagramSocket(port);
        start_server();
    }
    
    public void start_server() throws SocketException, IOException, NoSuchAlgorithmException
    {
        boolean val = true;
        FileWriter f_out = new FileWriter("C:\\Users\\Hitesh Mohite\\Desktop\\new_file.txt");
       
        while(val)
        {
            data_to_receive = new byte[1050];
            packet_to_receive = new DatagramPacket(data_to_receive, data_to_receive.length);
            socket.receive(packet_to_receive);
            store_data = new String(packet_to_receive.getData());
            byte[] file_content = store_data.getBytes();
            store_length = store_data.length();
            if(store_data.contains("hitesh"))
            {
                System.out.println(store_data);
                val = false;
                break;
            }
            //System.out.println(store_data);
            //System.out.println(store_data.substring(0, 1024));
            f_out.write(store_data);
            packet_count += 1;
            //byte[] has_ack = Arrays.copyOfRange(data_to_receive, 1025, port);
            //System.out.println("Ack info: "+has_ack.toString());
            temp = store_data.substring(1024, store_length);
            if(temp.contains("XXXACK"))
            {
                System.out.println("\n"+temp);
            }
            else
            {
                System.out.println(temp);
            }
        }
        
        System.out.println("Total packets received : "+packet_count);
        //generate the hash code for the file.
        
        FileInputStream get_md5 = new FileInputStream("C:\\Users\\Hitesh Mohite\\Desktop\\new_file.txt");
        byte[] digest_byte = new byte[1024];
        MessageDigest md = MessageDigest.getInstance("MD5");
        DigestInputStream dis = new DigestInputStream(get_md5, md);
        
        while(dis.read(digest_byte) != -1)
        {
            continue;
        }
        
        byte[] digest = md.digest();
        //System.out.println(new BigInteger(1,digest).toString(16));*/
        
        
        //System.out.println(store_data);
        MessageDigest digesting = MessageDigest.getInstance("MD5");
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
      
        //Read file data and update in message digest
        while ((bytesCount = get_md5.read(byteArray)) != -1) 
        {
            digesting.update(byteArray, 0, bytesCount);
        };
     
        //close the stream; We don't need it now.
        get_md5.close();
     
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
     
        /*data_to_send = new byte[1024];
        data_to_send = "yes..connected".getBytes();
        packet_to_send = new DatagramPacket(data_to_send, data_to_send.length, packet_to_receive.getAddress(), packet_to_receive.getPort());
        socket.send(packet_to_send);*/
    }
}