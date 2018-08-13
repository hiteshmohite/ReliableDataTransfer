/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fcnproject2fall;

import java.io.IOException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Hitesh Mohite
 */
public class fcntcp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SocketException, NoSuchAlgorithmException {
        // TODO code application logic here
        
        String[] params;
        if(args[0].contains("s"))
        {
            params = new String[2];
            if(args.length == 3)
            {
                params[0] = "q";
                params[1] = args[2];
            }
            else if(args.length == 2)
            {
                //quiet or not quiet
                params[0] = "nq";
                //port number for server
                params[1] = args[1];
            }
            else
            {
                System.out.println("Invalid commands");
            }
            new server().fcn_server(params);
        }
        else if(args[0].contains("c"))
        {
            params = new String[6];
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
            new client().fcn_client(params);
        }
        else
        {
            System.out.println("Invalid commands");
        }
    }
        
}
    

