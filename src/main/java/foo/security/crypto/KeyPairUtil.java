package foo.security.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyPairUtil {

   public static void main(String[] args) throws Exception {
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");     
      keyPairGen.initialize(512);
      
      KeyPair pair = keyPairGen.generateKeyPair();
      
      PrivateKey privKey = pair.getPrivate();
      PublicKey publicKey = pair.getPublic(); 

      System.out.println("Keys Generated!");
   }
}