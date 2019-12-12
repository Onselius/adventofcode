import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class D04 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        String key = "bgvyzdsv";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int i = 0;
        String hash = encode(md, key + i);
        while (!hash.startsWith("000000")){
            i++;
            hash = encode(md, key + i);
        }
        System.out.println(hash);
        System.out.println(i);
        timer.stopTime();

    }
    public static String encode(MessageDigest md, String input) {
        StringBuilder stringBuilder = new StringBuilder();
        md.update(input.getBytes());
        byte[] digest = md.digest();
        for (byte b: digest){
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }

}
