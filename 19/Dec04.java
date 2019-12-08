import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Dec04 {
    public static void main(String[] args) {
        ArrayList<String> passwords = new ArrayList<>();
        int numberOfPasswords = 0;

        for (int i = 387638; i <= 919123; i++){
            if (checkValid(String.valueOf(i))){
                passwords.add(Integer.toString(i));
            }
        }
        System.out.println("Number of passwords: " + passwords.size());
        for (String line : passwords){
            if (checkForMoreThenTwice(line)){
                numberOfPasswords++;
            }
        }
        System.out.println("Number of passwords: " + numberOfPasswords);
    }
    public static boolean checkForMoreThenTwice(String password){
        HashMap<Character, Integer> charCount = new HashMap<>();
        for (char c : password.toCharArray()){
            if (charCount.containsKey(c)){
                charCount.put(c, charCount.get(c) + 1);
            }else {
                charCount.put(c, 1);
            }
        }
        return charCount.containsValue(2);
    }

    public static boolean checkValid(String number){
        boolean isDouble = false;
        boolean isIncreasing = true;
        for (int i = 0; i < number.length()-1; i++){
            char first = number.charAt(i);
            char second = number.charAt(i+1);
            if (first == second) {
                isDouble = true;
            }
            if (first > second){
                isIncreasing = false;
            }
        }
        return isDouble && isIncreasing;
    }
}
