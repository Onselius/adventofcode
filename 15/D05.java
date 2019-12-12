import java.io.*;
import java.util.Collections;
import java.util.HashSet;

public class D05 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        File file = new File("15/input05.txt");
        int part1 = 0;
        int part2 = 0;
        String input;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (!(input = reader.readLine()).equals("")){
                if (checkIfNice(input)){
                    part1++;
                }
                if (newCheck(input)){
                    part2++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Part1 number of nice strings: " + part1);
        System.out.println("Part2 number of nice strings: " + part2);
        timer.stopTime();

    }
    public static boolean newCheck(String string){
        boolean pair = false;
        boolean repeat = false;
        outerloop:
        for (int i = 0; i < string.length()-3; i++) {
            String substring = string.charAt(i) + "" + string.charAt(i+1);
            for (int j = i+2; j < string.length()-1; j++){
                String compareString = string.charAt(j) + "" + string.charAt(j+1);
                if (substring.equals(compareString)){
                    pair = true;
                    break outerloop;
                }
            }
        }
        for (int i = 0; i < string.length()-2; i++) {
            if (string.charAt(i) == string.charAt(i+2)){
                repeat = true;
                break;
            }
        }
        return repeat && pair;
    }
    public static boolean checkIfNice(String string){
        HashSet<Character> vowels = new HashSet<>();
        HashSet<String> forbidden = new HashSet<>();
        forbidden.add("ab");
        forbidden.add("cd");
        forbidden.add("pq");
        forbidden.add("xy");
        for (char c: "aeiou".toCharArray()){
            vowels.add(c);
        }
        int countVowels = 0;
        for (char c: string.toCharArray()) {
            if (vowels.contains(c)) {
                countVowels++;
            }
        }
        boolean doubleCondition = false;
        for (int i = 0; i < string.length()-1; i++){
            char c1 = string.charAt(i);
            char c2 = string.charAt(i+1);
            if (forbidden.contains(c1 + "" + c2)){
                return false;
            } else if (c1 == c2){
                doubleCondition = true;
            }

        }
        return countVowels >= 3 && doubleCondition;
    }
}
