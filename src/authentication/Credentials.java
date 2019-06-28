/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.security.MessageDigest;

/**
 *
 * @author ricky.tucker_snhu
 */
public class Credentials {
    
    private List<String[]> credentialsList = new ArrayList<String[]>();
    
    /**
     * Loads the credentials from file
     */
    public void LoadCredentials() throws IOException {
    
        //create a empy credentials list and input file
        credentialsList = new ArrayList<String[]>();
        FileInputStream fileInputStream = null;
        Scanner scnr = null;
        String inputLine;
        
        
        //open up the file and setup scanner
        fileInputStream = new FileInputStream("credentials.txt");
        scnr = new Scanner(fileInputStream);
        
        //loop through while we have content
        while (scnr.hasNextLine()) {
            inputLine = scnr.nextLine();
            credentialsList.add(inputLine.split("\t"));
           
        }    
        
    }
    /**
     * Finds the corresponding username and password combination and outputs the roleName
     * @param userName - the username to find
     * @param userPassword - the password to corresponding user
     * @return No userRole or the userRoleName
     */
    public String GetuserRole( String username,String userPassword) {
        String userRoleName;
        
        for(int i =0; i < credentialsList.size(); i++) {
            if(credentialsList.get(i)[0].equals(username) && credentialsList.get(i)[1].equals(userPassword)) {
                
                return credentialsList.get(i)[3];
            }
        }
        
        return "No userRole";
    }
    
    
    /**
     * A MD5 hash function modified from code provided by instructor
     * @param toHash the original string to MD5 hash
     * @return the string once it has been MD5 hashed
     */
    public static String HashMD5(String toHash) throws Exception{
        
        //begin provided MD5Digest code
        MessageDigest md = MessageDigest.getInstance("MD5");
	md.update(toHash.getBytes());
	byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
	for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        //end provided MD5Digest code
        
        //return the modified string
        return sb.toString();
    }
    
    /**
     * looks through the credentialsList and finds a match or not
     * @param username - the username to search for
     * @param userPassword - the password to check
     * @return "Match" for match or "NoMatch" for no match
     */
    public String MatchCredentials( String username, String userPassword) {
        
        //uses a member of the collection interface to find if any match exist
        
        return (credentialsList.stream().anyMatch(item -> item[0].equals(username) && item[1].equals(userPassword) ) ?  "Match" : "NoMatch");
    }
}
