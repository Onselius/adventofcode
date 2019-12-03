import java.io.*;
import java.util.*;

public class dec03 {
    public static void main(String[] args) {
        try {
            File input = new File("19/input03.txt");
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String[] directions = reader.readLine().split(",");
            HashSet<List<Integer>> firstWire = calculatePositions(directions);
            directions = reader.readLine().split(",");
            HashSet<List<Integer>> secondWire = calculatePositions(directions);

//            System.out.println("First size: " + firstWire.size());
//            System.out.println(Arrays.deepToString(firstWire.toArray()));
//            System.out.println("Second size: " + secondWire.size());
//            System.out.println(Arrays.deepToString(secondWire.toArray()));


            Set<List<Integer>> intersection = new HashSet<>(firstWire);
            intersection.retainAll(secondWire);
            intersection.remove(new ArrayList<>(List.of(0,0)));
            System.out.println(Arrays.deepToString(intersection.toArray()));
            System.out.println(calculateSum(intersection));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static HashSet<List<Integer>> calculatePositions(String[] input){
        HashSet<List<Integer>> wire = new HashSet<>();
        int[] currentPos = {0,0};
        for (String command : input){
//        for (int j = 0; j < 3; j++){
//            String command = input[j];
            String direction = command.substring(0,1);
            int length = Integer.parseInt(command.substring(1));
            int x = currentPos[0];
            int y = currentPos[1];
            switch (direction){
                case "R":
                    currentPos[0] += length;
                    for (int i = x;i <= currentPos[0];i++){
                        wire.add(new ArrayList<>(List.of(i,y)));
                    }
                    break;
                case "L":
                    currentPos[0] -= length;
                    for (int i = x;i >= currentPos[0];i--){
                        wire.add(new ArrayList<>(List.of(i,y)));
                    }
                    break;
                case "U":
                    currentPos[1] += length;
                    for (int i = y;i <= currentPos[1];i++){
                        wire.add(new ArrayList<>(List.of(x,i)));
                    }
                    break;
                case "D":
                    currentPos[1] -= length;
                    for (int i = y;i >= currentPos[1];i--){
                        wire.add(new ArrayList<>(List.of(x,i)));
                    }
                    break;
            }
        }
        return wire;
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
