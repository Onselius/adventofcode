import java.io.*;
import java.lang.reflect.Array;
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
            List<Integer> spaceStations = getSpaceStation(visible);
            TreeMap<Double, List<Integer>> angles = getAngles(asteroids, spaceStations);
            int toBeDestroyed = 1;
            while (toBeDestroyed < 200){
                if (angles.isEmpty()){
                    angles = getAngles(asteroids, spaceStations);
                }
                List<Integer> asteroid = angles.pollFirstEntry().getValue();
                asteroids.remove(asteroid);
                toBeDestroyed++;
            }
            List<Integer> asteroid = angles.pollFirstEntry().getValue();
            System.out.println("number 200 to be destroyed is " + asteroid);
            System.out.println("X * 100 + Y = " + (asteroid.get(0) * 100 + asteroid.get(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<Integer> getSpaceStation(HashMap<List<Integer>, Integer> visible){
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
        return spaceStation;
    }
    private static int countVisible(HashSet<List<Integer>> asteroids, List<Integer> current){
        return getAngles(asteroids, current).size();
    }

    public static TreeMap<Double, List<Integer>> getAngles(HashSet<List<Integer>> asteroids, List<Integer> current) {
        TreeMap<Double, List<Integer>> angles = new TreeMap<>();
        for (List<Integer> asteroid : asteroids){
            if (current.equals(asteroid)){
                continue;
            }
            double x = asteroid.get(0) - current.get(0);
            double y = asteroid.get(1) - current.get(1);
            double theta = Math.atan2(x, y);
            angles.put(0-theta, asteroid);
        }
        return angles;
    }
}
