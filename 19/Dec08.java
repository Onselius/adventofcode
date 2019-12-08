import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dec08 {
    public static void main(String[] args) {
        File file = new File("19/input08.txt");
        String inputString;
        HashMap<String, Integer> numberOfZeroes = new HashMap<>();
        ArrayList<int[][]> layers = new ArrayList<>();
        int[][] decode = new int[6][25];
        for (int[] ints : decode) {
            Arrays.fill(ints, 2);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            inputString = reader.readLine();
            for (int i = 0, end = 150; i < inputString.length(); i = i+150, end = end+150){
                String row = inputString.substring(i, end);
                int[][] layer = new int[6][25];
                for (int start = 0, stop = 25, index = 0; start < row.length(); start += 25, stop += 25, index++){
                    String substring = row.substring(start, stop);
                    for (int c = 0; c < substring.length(); c++){
                        layer[index][c] = Integer.parseInt(String.valueOf(substring.charAt(c)));
                    }
                }
                layers.add(layer);
                numberOfZeroes.put(row, countCharacters(row, "0"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String rowWithFewestZeroes = calculateMin(numberOfZeroes);
        System.out.println("row with fewest zeroes: " + rowWithFewestZeroes);
        int numberOfOnes = countCharacters(rowWithFewestZeroes, "1");
        int numberOfTwos = countCharacters(rowWithFewestZeroes, "2");
        System.out.println("Ones: " + numberOfOnes + " twos: " + numberOfTwos);
        System.out.println("number of 1 times no. of 2: " + (numberOfOnes * numberOfTwos));


        for (int[][] layer : layers){
            for (int i = 0; i < layer.length; i++){
                for (int j = 0 ;j < layer[i].length; j++){
                    if ((decode[i][j] == 2) && (layer[i][j] != 2)){
                        decode[i][j] = layer[i][j];
                    }
                }
            }
        }
        for (int[] ints : decode) {
            System.out.println(Arrays.toString(ints));
        }
    }
    public static String calculateMin(HashMap<String, Integer> hashMap){
        String row = "";
        int lowest = 555555;
        for (String seq : hashMap.keySet()){
            if (hashMap.get(seq) < lowest){
                row = seq;
                lowest = hashMap.get(seq);
            }
        }
        return row;
    }
    public static int countCharacters(String fullString, String searchForCharacter){
        HashMap<String, Integer> hashMap = new HashMap<>(3);
        for (int i = 0; i < fullString.length(); i++){
            String c = String.valueOf(fullString.charAt(i));
            if (hashMap.containsKey(c)){
                hashMap.put(c, hashMap.get(c) + 1);
            }else{
                hashMap.put(c, 1);
            }
        }
        return hashMap.get(searchForCharacter);
    }
}
