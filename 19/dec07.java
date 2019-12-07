import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class dec07 {
    public static void main(String[] args) {
        File file = new File("19/input07.txt");
        int input = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String baseString = reader.readLine();
            ArrayList<String> permutations = new ArrayList<>();
            int[] instructions = Arrays.stream(baseString.split(",")).mapToInt(Integer::parseInt).toArray();

            permutations("", "01234", permutations);
            System.out.println(permutations.get(0));
            System.out.println(permutations.get(1));
            System.out.println(permutations.size());
            System.exit(1);
            IntcodeComputer computer = new IntcodeComputer(instructions.clone());
            computer.setInput(input);
            computer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void permutations(String prefix, String s, ArrayList<String> list) {
        int n = s.length();
        if (n == 1) {
            list.add(prefix + s);
        } else {
            for (int i = 0; i < n; i++)
                permutations(prefix + s.charAt(i), s.substring(0, i) + s.substring(i+1, n), list);
        }
    }
}
