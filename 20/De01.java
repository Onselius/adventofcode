import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class De01 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<Integer> lines = ReadFile.convertToInteger(ReadFile.getTextFromFile("20/input01.txt"));

        System.out.println(lines.toString());
        int[] factors = get2FactorsFromList(lines);
        System.out.println("Result is: " + (factors[0] * factors[1]));
        factors = get3FactorsFromList(lines);
        System.out.println("Result is: " + (factors[0] * factors[1] * factors[2]));
        timer.stopTime();
    }
    private static int[] get2FactorsFromList(List<Integer> lines){
        int[] factors = new int[2];
        int listSize = lines.size();

        for (int i = 0; i < listSize - 1; i++ ){
            for (int j = i + 1; j < listSize; j++){
                if (lines.get(i) + lines.get(j) == 2020) {
                    int x = lines.get(i);
                    int y = lines.get(j);
                    System.out.println("Found " + x);
                    System.out.println("Found " + y);
                    factors[0] = x;
                    factors[1] = y;
                    return factors;
                }
            }
        }
        return factors;
    }
    private static int[] get3FactorsFromList(List<Integer> lines){
        int[] factors = new int[3];
        int listSize = lines.size();

        for (int i = 0; i < listSize - 2; i++ ){
            for (int j = i + 1; j < listSize - 1; j++){
                for (int k = j + 1; k < listSize; k++){
                    if (lines.get(i) + lines.get(j) + lines.get(k) == 2020) {
                        int x = lines.get(i);
                        int y = lines.get(j);
                        int z = lines.get(k);
                        System.out.println("Found " + x);
                        System.out.println("Found " + y);
                        System.out.println("Found " + z);
                        factors[0] = x;
                        factors[1] = y;
                        factors[2] = z;
                        return factors;
                    }
                }
            }
        }
        return factors;
    }
}
