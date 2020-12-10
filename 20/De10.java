import java.util.*;

public class De10 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<Long> inputLines = ReadFile.convertToInteger(ReadFile.getTextFromFile("20/input10.txt"));
        inputLines.add(0L);

        int numberOf1 = 0;
        int numberOf3 = 0;

        Collections.sort(inputLines);

        for (int i = 0; i < inputLines.size() - 1; i++){
            if (inputLines.get(i + 1) - inputLines.get(i) == 1){
                numberOf1++;
            } else if (inputLines.get(i + 1) - inputLines.get(i) == 3){
                numberOf3++;
            }
        }
        numberOf1++;
        numberOf3++;

        System.out.println("1: " + numberOf1);
        System.out.println("3: " + numberOf3);

        System.out.println("Part1: " + (numberOf1 * numberOf3));
        System.out.println("Part2: " + part2(inputLines));

        timer.stopTime();
    }

    static Long part2(List<Long> input){
        Map<Long, Long> adapters = new HashMap<>();
        long max = Collections.max(input);
        adapters.put(max, (long) 1);

        long permutations = 1;
        Long value;
        int count;
        Long oldValue;

        for (int i = input.size() - 1; i > 0; i--){
            value = input.get(i);
            permutations = adapters.get(value);
            //System.out.println("value: " + value);
            try {
                if (input.contains(value - 1)){
                    adapters.put(value - 1, adapters.getOrDefault(value - 1, 0L) + permutations);
                }
                if (input.contains(value - 2)){
                    adapters.put(value - 2, adapters.getOrDefault(value - 2, 0L) + permutations);
                }
                if (input.contains(value - 3)){
                    adapters.put(value - 3, adapters.getOrDefault(value - 3, 0L) + permutations);
                }
            } catch (IndexOutOfBoundsException ignored){

            }

            //System.out.println("perm: " + permutations);
        }

        return adapters.get(0L);
    }
}
