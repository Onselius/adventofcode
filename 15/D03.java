import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class D03 {
    public static void main(String[] args) {
        File file = new File("15/input03.txt");
        Integer[] position = new Integer[]{0,0};
        HashSet<List<Integer>> houses = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            char[] input = reader.readLine().toCharArray();
            System.out.println(input.length);
            for (char c : input){
                switch (c){
                    case '^':
                        position[1]++;
                        break;
                    case 'v':
                        position[1]--;
                        break;
                    case '<':
                        position[0]--;
                        break;
                    case '>':
                        position[0]++;
                        break;
                }
                houses.add(new ArrayList<>(List.of(position)));
            }
            System.out.println("Number of houses: " + houses.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/*    private static List<Integer> changePosition(){

    }

 */
}
