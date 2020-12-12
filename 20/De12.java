import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class De12 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> input = ReadFile.getTextFromFile("20/input/input12.txt");

        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Integer> mapPart2 = new HashMap<>();
        ArrayList<Character> directions = new ArrayList<>(List.of('N','E','S','W'));
        int[] wayPoint = new int[] {1, 10, 0, 0};
        int currentIndex = 1;
        char newDirection;
        int value;
        int index;

        for (String line: input){
            newDirection = line.charAt(0);
            value = Integer.parseInt(line.substring(1));

            if (directions.contains(newDirection)){
                map.put(newDirection, map.getOrDefault(newDirection, 0) + value);
                index = directions.indexOf(newDirection);
                wayPoint[index] += value;
            } else if (newDirection == 'F'){
                map.put(directions.get(currentIndex), map.getOrDefault(directions.get(currentIndex), 0) + value);
                for (int i = 0; i < value; i++){
                    for (int j = 0; j < directions.size(); j++){
                        mapPart2.put(directions.get(j), mapPart2.getOrDefault(directions.get(j), 0) + wayPoint[j]);
                    }
                }
            } else {
                value = value / 90;
                if (newDirection == 'R'){
                    currentIndex = (currentIndex + value) % directions.size();
                    rotateRight(value, wayPoint);
                } else {
                    currentIndex = (currentIndex - value + directions.size()) % directions.size();
                    rotateLeft(value, wayPoint);
                }
            }
        }

        int eastWest = map.getOrDefault('E', 0) - map.getOrDefault('W', 0);
        int northSouth = map.getOrDefault('N', 0) - map.getOrDefault('S', 0);
        System.out.println("Manhattan distance: " + (Math.abs(eastWest) + Math.abs(northSouth)));

        eastWest = mapPart2.getOrDefault('E', 0) - mapPart2.getOrDefault('W', 0);
        northSouth = mapPart2.getOrDefault('N', 0) - mapPart2.getOrDefault('S', 0);
        System.out.println("Manhattan distance: " + (Math.abs(eastWest) + Math.abs(northSouth)));

        timer.stopTime();
    }

    private static void rotateRight(int rotations, int[] array){
        int last;
        for (int i = 0; i < rotations; i++){
            last = array[array.length - 1];
            System.arraycopy(array, 0, array, 1, array.length - 1);
            array[0] = last;
        }
    }

    private static void rotateLeft(int rotations, int[] array){
        int first;
        for (int i = 0; i < rotations; i++){
            first = array[0];
            System.arraycopy(array, 1, array, 0, array.length - 1);
            array[array.length - 1] = first;
        }
    }

}
