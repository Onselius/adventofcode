import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class De15 {
    public static void main(String[] args) {
        Timer timer = new Timer();

        String testInput = "0,3,6";
        String realInput = "9,6,0,10,18,2,1";
        List<Integer> input = Stream.of(realInput.split(",")).map(Integer::parseInt).collect(Collectors.toList());

        int numberOfIterations = 30000000;
        int lastSpoken;
        int turn = 1;
        HashMap<Integer, Integer> turnWhenSpoken = new HashMap<>();

        for (; turn < input.size(); turn++){
            lastSpoken = input.get(turn - 1);
            turnWhenSpoken.put(lastSpoken, turn);
        }
        lastSpoken = input.get(turn - 1);
        turn++;

        int lastTurnSpoken;

        while (turn <= numberOfIterations){
            if (turnWhenSpoken.containsKey(lastSpoken)){
                lastTurnSpoken = turnWhenSpoken.get(lastSpoken);
                turnWhenSpoken.put(lastSpoken, turn -1);
                lastSpoken = turn - 1 - lastTurnSpoken;
            } else {
                turnWhenSpoken.put(lastSpoken,  turn - 1);
                lastSpoken = 0;
            }
            turn++;
        }
        System.out.println("Last word spoken: " + lastSpoken);

        timer.stopTime();
        timer = new Timer();

        turn = 1;
        int[] numbers = new int[realInput.length()];

        for (; turn < input.size(); turn++){
            lastSpoken = input.get(turn - 1);
            if (lastSpoken > numbers.length){
                int[] newNumbers = new int[numbers.length * 2];
                System.arraycopy(numbers, 0, newNumbers,0, numbers.length);
                numbers = newNumbers;
            }
            numbers[lastSpoken] = turn;
        }
        lastSpoken = input.get(turn - 1);
        turn++;

        while (turn <= numberOfIterations){
            if (lastSpoken >= numbers.length){
                int[] newNumbers = new int[numbers.length * 2];
                System.arraycopy(numbers, 0, newNumbers,0, numbers.length);
                numbers = newNumbers;
            }
            lastTurnSpoken = numbers[lastSpoken];
            numbers[lastSpoken] = turn - 1;
            if (lastTurnSpoken > 0){
                lastSpoken = turn - 1 - lastTurnSpoken;
            } else {
                lastSpoken = 0;
            }
            turn++;
        }
        System.out.println("Lastspoken: " + lastSpoken);

        timer.stopTime();
    }
}
