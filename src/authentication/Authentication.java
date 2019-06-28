/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;

/**
 *
 * @author ricky.tucker_snhu
 */
public class Authentication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        //define variables for future use
        Credentials credentialSystem = new Credentials();
        int loginAttempts = 0;
        Scanner scnr = new Scanner(System.in); // set up input scanner
        String username;
        String userPassword;
        String userRoleName;
        String userRole;
        
        //initilized credential list
        credentialSystem.LoadCredentials();
        
        //loop 3 times or until valide data is matched
        for (loginAttempts = 0; loginAttempts < 3 ; loginAttempts++) {
            
            //prompt for username
            System.out.print("Please enter User Name or 'q' to quit: ");
            username = scnr.nextLine();
            
            //check for q for quit
            if ( username.equals("q") ) {
                System.out.println("Goodbye");
                return;
            }
            else {
                
                //prompt for password
                System.out.print("\nPlease enter your password: ");
                userPassword = scnr.nextLine();
                
                //hash the password provided
                userPassword = Credentials.HashMD5(userPassword);
                
               //check for matching username and password
               if ( credentialSystem.MatchCredentials(username,userPassword).equals("Match")) {
                   
                    //we have a match lets get the userRoleName
                   userRoleName = credentialSystem.GetuserRole(username,userPassword);
                   
                   //load user role file into our string and print it out
                   userRole = LoadUserRoleFile(userRoleName);
                   System.out.println(userRole);
                   
                   //prompt user to press any key to exit and once key press logout
                   System.out.print("Press any key to exit/logout ");
                   String keypress = scnr.next();
                   System.out.println("\n");
                   System.out.println("Good Bye!");
                   return;
               }
               else {
                   
                   System.out.println("\n***Invalid user Credentials***\n");
               }
               
                
                
            }
            
            if (loginAttempts == 2) {
                
                //let the user know they hit max login attempts and exit
                System.out.println("Max failed login attempts,\nGood Bye.");
                return;
            }
            
        }
    }
    
    /**
     * Takes the provided userRoleName and loads its corresponding file.
     * @param userRoleName - The name of the role file to load
     * @return the contents of the role file
     */
    public static String LoadUserRoleFile(String userRoleName) throws IOException {
        //setup our variables 
        FileInputStream fileByteStream = null;
        Scanner inFS = null;
        String output = "";
        
        //open file stream and set up scanner to this stream
        fileByteStream = new FileInputStream(userRoleName + ".txt");
        inFS = new Scanner(fileByteStream);
        
        //loop thru and read the lines
        while(inFS.hasNextLine()) {
            output += "\n" + inFS.nextLine();
            
        }
        
        return output;
    }
    
}
