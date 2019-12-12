import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class D03 {
    public static void main(String[] args) {
        File file = new File("15/input03.txt");
        Integer[] santa = new Integer[]{0,0};
        Integer[] robot = new Integer[]{0,0};
        HashSet<List<Integer>> houses = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            char[] input = reader.readLine().toCharArray();
            System.out.println(input.length);
            for (char c: input){
                changePosition(santa, c);
                houses.add(new ArrayList<>(List.of(santa)));
            }
            System.out.println("Part 1:");
            System.out.println("Number of houses: " + houses.size());
            System.out.println();
            houses.clear();
            Arrays.fill(santa, 0);

            for (int i = 0, j = 1; j < input.length; i += 2, j += 2){
                changePosition(santa, input[i]);
                changePosition(robot, input[j]);
                houses.add(new ArrayList<>(List.of(santa)));
                houses.add(new ArrayList<>(List.of(robot)));
            }
            System.out.println("Part 2:");
            System.out.println("Number of houses: " + houses.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void changePosition(Integer[] position, char direction){
        switch (direction){
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
    }
}
