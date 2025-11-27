//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//import javax.crypto.SecretKey;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.util.Base64;
//
//public class JwtKeyGenerator {
//    public static void main(String[] args) throws Exception {   // <-- FIXED
//        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
//        keyGen.initialize(2048);
//        KeyPair pair = keyGen.generateKeyPair();
//
//        String publicKeyBase64 = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
//        String privateKeyBase64 = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
//
//        System.out.println("PUBLIC KEY:");
//        System.out.println(publicKeyBase64);
//
//        System.out.println("PRIVATE KEY:");
//        System.out.println(privateKeyBase64);
//    }
//}
