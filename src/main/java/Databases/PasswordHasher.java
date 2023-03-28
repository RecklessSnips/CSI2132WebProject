package Databases;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordHasher {
    public static String hash (String userpassword, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            byte[] result = md.digest((userpassword + salt).getBytes());
            return bytesToHex(result);
        }
        catch (NoSuchAlgorithmException e){
            return "error";
        }
    }

    public static boolean checkHash (String hashedpassword, String userpassword, String salt) {
        return hashedpassword == hash(userpassword, salt);
    }

    public static String generateSalt () {
        Random random = new Random(); //Is random safe enough to use?

        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
