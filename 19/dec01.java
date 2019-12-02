import java.io.*;

public class dec01 {

    public static void main(String[] args) {
        try {
            File input = new File("input01.txt");
            BufferedReader reader = new BufferedReader(new FileReader(input));
            int sum = 0;
            int totalsum = 0;
            String line;

            while ((line = reader.readLine()) != null){
                int number = Integer.parseInt(line);
                sum += calculateFuel(number);
                while (number > 0){
                    number = calculateFuel(number);
                    totalsum += number;
                }
            }
            System.out.println("Answer question 1: " + sum);
            System.out.println("Answer question 2: " + totalsum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int calculateFuel(int startFuel){
        int fuel = startFuel / 3 - 2;
        return Math.max(fuel, 0);
    }
}
