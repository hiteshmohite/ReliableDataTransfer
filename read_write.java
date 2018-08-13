/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fcnproject2fall;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Hitesh Mohite
 */
public class read_write {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        // TODO code application logic here
        File new_file = new File("C:\\Users\\Hitesh Mohite\\Documents\\NetBeansProjects\\fcnproject2fall\\src\\fcnproject2fall\\new_file.txt");
        
        if(new_file.createNewFile())
        {
            System.out.println("New File is Created");
        }
        else
        {
            System.out.println("File is not created");
        }
        
        byte[] to_read = new byte[1024];
        FileInputStream f_in = new FileInputStream("C:\\Users\\Hitesh Mohite\\Documents\\NetBeansProjects\\fcnproject2fall\\src\\fcnproject2fall\\new_file.txt");
        int read = 0, total_bytes = 0;
        
        while((read = (f_in.read(to_read))) != -1)
        {
            //System.out.println(new String(to_read));
            total_bytes = total_bytes + read;
        }
        //System.out.println("total bytes are "+total_bytes);
        byte[] digest_byte = new byte[1024];
        MessageDigest md = MessageDigest.getInstance("MD5");
        //FileInputStream get_md5 = new FileInputStream("C:\\Users\\Hitesh Mohite\\Documents\\NetBeansProjects\\fcnproject2fall\\src\\fcnproject2fall\\filetosave.txt");
        
        DigestInputStream dis = new DigestInputStream(f_in, md);
        
        while(dis.read(digest_byte) != -1)
        {
            continue;
        }
        
        byte[] digest = md.digest();
        System.out.println(new BigInteger(1,digest).toString(16));
    }
}
