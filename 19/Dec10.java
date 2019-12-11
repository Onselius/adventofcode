import java.io.*;
import java.util.*;

public class Dec10 {
    public static void main(String[] args) {
        File file = new File("19/input10.txt");
        String input;
        HashSet<List<Integer>> asteroids = new HashSet<>(441);
        HashMap<List<Integer>, Integer> visible = new HashMap<>();
        int y = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (!(input = reader.readLine()).equals("")){
                for (int x = 0; x < input.length(); x++){
                    if (input.charAt(x) == '#') {
                        asteroids.add(new ArrayList<>(List.of(x,y)));
                    }
                }
                y++;
            }
            for (List<Integer> asteroid: asteroids){
                int count = countVisible(asteroids, asteroid);
                visible.put(asteroid, count);
            }
            printMax(visible);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void printMax(HashMap<List<Integer>, Integer> visible){
        List<Integer> spaceStation = null;
        int highest = 0;
        for (List<Integer> asteroid: visible.keySet()){
            if (visible.get(asteroid) > highest){
                highest = visible.get(asteroid);
                spaceStation = asteroid;
            }
        }
        if (spaceStation != null){
            System.out.println("Location should be at " + Arrays.toString(spaceStation.toArray()) + " with " + highest);
        } else {
            System.out.println("Could not find any asteroids :(");
        }
    }
    private static int countVisible(HashSet<List<Integer>> asteroids, List<Integer> current){
        HashSet<Double> visible = new HashSet<>();
        for (List<Integer> asteroid : asteroids){
            double x = (double) (current.get(0) - asteroid.get(0));
            double y = (double) (current.get(1) - asteroid.get(1));
            double theta = Math.atan2(y, x);
            visible.add(theta);
        }
        return visible.size();
    }
}
