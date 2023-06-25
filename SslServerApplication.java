package com.snhu.sslserver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";

//REST object that will be the server that holds the data information
@RestController
class SslServerController{
	
	/*
	  * Converts a byte array to a hexadecimal string
	  * 
	  * @param bytes - byte array that has the computed message in bytes
	  * @return hashedDataString - hashed data in string format
	  */
	 public String bytesToHex(byte[] bytes) {
	     StringBuilder hashedData = new StringBuilder();
	     String hashedDataString;
	    
	     // loop through byte array to hash data into hashedData variable
	     for (int i = 0 ;i<bytes.length;i++) {
	     	hashedData.append(Integer.toHexString(0xFF & bytes[i]));
	     }
	     
	     hashedDataString = hashedData.toString();
	     
	     return hashedDataString;
	 }
	
	// method that will hash the message given
	public String myHash(String filename){
	
		MessageDigest filenameDigest = null; // used for message ciphers 
		byte[] computeFilenameBytes; // message bytes that will be hashed
		String hashedFilename; // hashed message
		
		// needed due to exception error
		try {
			// SHA-512 = the algorithm to be used based on Oracle site 
			// (https://docs.oracle.com/javase/9/docs/specs/security/standard-names.html#messagedigest-algorithms)
			filenameDigest = MessageDigest.getInstance("SHA-512");  
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();									
		}
		
		
		filenameDigest.update(filename.getBytes()); // pass data to the messageDigest
		computeFilenameBytes = filenameDigest.digest(); // compute the message digest into bytes
		
		hashedFilename = bytesToHex(computeFilenameBytes); // hex string that will hold the data to be hashed
		
		 return hashedFilename;
	 }
	 
	 // method that will map to localhost and print the following message
	 @RequestMapping("/hash")
	 public String printHash() {
		 String hashedFilename;
		 String webOutput;
		 
		 String filename = "This is the file to be hashed";
		 
		 hashedFilename = myHash(filename);
		 
		 webOutput = "<p>Developer: Allen Jhane Dela Cruz"
					+ "<br>File: " + filename 
			 		+ "<br>Name of the algorithm cipher used: SHA-512" 
			 		+ "<br>Checksum hash value: " + hashedFilename + "</p>";
		 
		 return webOutput;
		 
	 }
	 
}