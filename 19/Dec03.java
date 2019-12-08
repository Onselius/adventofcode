import java.io.*;
import java.util.*;

public class Dec03 {
    public static void main(String[] args) {
        try {
            File input = new File("19/input03.txt");
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String[] firstLineOfDirections = reader.readLine().split(",");
            String[] secondLineOfDirections = reader.readLine().split(",");
            HashMap<List<Integer>, Integer> steps = new HashMap<>();
            HashSet<List<Integer>> intersection = new HashSet<>();

            HashSet<List<Integer>> firstWire = calculatePositions(firstLineOfDirections, intersection, steps);
            HashSet<List<Integer>> secondWire = calculatePositions(secondLineOfDirections, intersection, steps);

            intersection = firstWire;
            intersection.retainAll(secondWire);
            intersection.remove(new ArrayList<>(List.of(0,0)));
            System.out.println("Closest distance to instersection: " + calculateSum(intersection));
            calculatePositions(firstLineOfDirections, intersection, steps);
            calculatePositions(secondLineOfDirections, intersection, steps);
            System.out.println("Min amounts of steps: " + Collections.min(steps.values()));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static HashSet<List<Integer>> calculatePositions(String[] input, Set<List<Integer>> intersection, HashMap<List<Integer>, Integer> steps){
        HashSet<List<Integer>> wire = new HashSet<>();
        Integer[] currentPos = {0,0};
        int numberOfSteps = 0;
        for (String command : input){
            String direction = command.substring(0,1);
            int length = Integer.parseInt(command.substring(1));
            int x = currentPos[0];
            int y = currentPos[1];
            switch (direction){
                case "R":
                    currentPos[0] += length;
                    for (int i = ++x;i <= currentPos[0];i++){
                        ArrayList<Integer> position = new ArrayList<>(List.of(i,y));
                        numberOfSteps++;
                        if (checkIfIntersection(intersection, position) && !wire.contains(position)){
                            int originalValue = 0;
                            if (steps.containsKey(position)){
                                originalValue = steps.get(position);
                            }
                            steps.put(position, numberOfSteps + originalValue);
                        }
                        wire.add(position);
                    }
                    break;
                case "L":
                    currentPos[0] -= length;
                    for (int i = --x;i >= currentPos[0];i--){
                        ArrayList<Integer> position = new ArrayList<>(List.of(i,y));
                        numberOfSteps++;
                        if (checkIfIntersection(intersection, position) && !wire.contains(position)){
                            int originalValue = 0;
                            if (steps.containsKey(position)){
                                originalValue = steps.get(position);
                            }
                            steps.put(position, numberOfSteps + originalValue);
                        }
                        wire.add(position);
                    }
                    break;
                case "U":
                    currentPos[1] += length;
                    for (int i = ++y;i <= currentPos[1];i++){
                        ArrayList<Integer> position = new ArrayList<>(List.of(x,i));
                        numberOfSteps++;
                        if (checkIfIntersection(intersection, position) && !wire.contains(position)){
                            int originalValue = 0;
                            if (steps.containsKey(position)){
                                originalValue = steps.get(position);
                            }
                            steps.put(position, numberOfSteps + originalValue);
                        }
                        wire.add(position);
                    }
                    break;
                case "D":
                    currentPos[1] -= length;
                    for (int i = --y;i >= currentPos[1];i--){
                        ArrayList<Integer> position = new ArrayList<>(List.of(x,i));
                        numberOfSteps++;
                        if (checkIfIntersection(intersection, position) && !wire.contains(position)){
                            int originalValue = 0;
                            if (steps.containsKey(position)){
                                originalValue = steps.get(position);
                            }
                            steps.put(position, numberOfSteps + originalValue);
                        }
                        wire.add(position);
                    }
                    break;
            }
        }
        return wire;
    }

    public static boolean checkIfIntersection(Set<List<Integer>> intersection, ArrayList<Integer> position){
        return intersection.contains(position);
    }

    public static int calculateSum(Set<List<Integer>> arrayList){
        int minValue = 555555555;
        for (List<Integer> list : arrayList){
            int sum = Math.abs(list.get(0))+ Math.abs(list.get(1));
            if (sum < minValue){
                minValue = sum;
            }
        }
        return minValue;
    }
}
