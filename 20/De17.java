import java.util.*;
import java.util.stream.Collectors;

public class De17 {

    static Set<List<Integer>> newPositions;
    static Set<List<Integer>> positions = new HashSet<>();
    static List<List<Integer>> differences;
    static List<List<Integer>> borders = new ArrayList<>();
    static Set<List<Integer>> alreadySeen;

    public static void main(String[] args) {
        Timer timer = new Timer();
        String testFile = "20/test/test17.txt";
        String realFile = "20/input/input17.txt";
        int iterations = 6;

        List<String> input = ReadFile.getTextFromFile(realFile);

        for (int y = 0; y < input.size(); y++){
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++){
                if (line.charAt(x) == '#'){
                    positions.add(List.of(y,x,0,0));
                }
            }
        }
        Set<List<Integer>> positions2 = new HashSet<>();
        positions2.addAll(positions);
        generatePart1Diff();

        for (int i = 0; i < iterations; i++){
            newPositions = new HashSet<>();
            alreadySeen = new HashSet<>();
            for (List<Integer> position: positions){
                borders = new ArrayList<>();
                int count = getNumberOfNeighbors(position, true);
                addActivePosition(position, count);
                for (List<Integer> border: borders){
                    count = getNumberOfNeighbors(border, false);
                    addActivePosition(border, count);
                }
            }
            positions = newPositions;
        }
        int totalActive = positions.size();
        System.out.println("Total: " + totalActive);

        generatePart2Diff();
        positions = positions2;
        for (int i = 0; i < iterations; i++){
            newPositions = new HashSet<>();
            alreadySeen = new HashSet<>();
            for (List<Integer> position: positions){
                borders = new ArrayList<>();
                int count = getNumberOfNeighbors(position, true);
                addActivePosition(position, count);
//                borders = borders.stream().distinct().filter(alreadySeen::contains).collect(Collectors.toList());
//                System.out.println(alreadySeen);
                for (List<Integer> border: borders){
                    count = getNumberOfNeighbors(border, false);
                    addActivePosition(border, count);
                }
            }
            positions = newPositions;
        }
        totalActive = positions.size();
        System.out.println("Total: " + totalActive);
        timer.stopTime();
    }

    private static void generatePart1Diff(){
        differences = new ArrayList<>();
        for (int y = -1; y < 2; y++){
            for (int x = -1; x < 2; x++){
                for (int z = -1; z < 2; z++){
                    differences.add(List.of(y,x,z,0));
                }
            }
        }
    }

    private static void generatePart2Diff(){
        differences = new ArrayList<>();
        for (int y = -1; y < 2; y++){
            for (int x = -1; x < 2; x++){
                for (int z = -1; z < 2; z++){
                    for (int w = -1; w < 2; w++){
                        differences.add(List.of(y,x,z,w));
                    }
                }
            }
        }
    }

    private static int getNumberOfNeighbors(List<Integer> position, boolean updateBorders) {
        int count = 0;
        for (List<Integer> diff: differences){
            List<Integer> testPosition = new ArrayList<>();
            for (int i = 0; i < diff.size(); i++){
                testPosition.add(position.get(i) + diff.get(i));
            }
            if (positions.contains(testPosition) && !position.equals(testPosition)){
                count++;
            } else {
                if (updateBorders){
                    borders.add(testPosition);
                }
            }
//            alreadySeen.add(testPosition);
        }
        return count;
    }

    private static void addActivePosition(List<Integer> position, int count){
//        System.out.println("count " + count);
//        System.out.println(positions.contains(position));
        if (count == 3 && !positions.contains(position)){
//            System.out.println("changed to active");
//            System.out.println("Position: " + position + " count: " + count);
            newPositions.add(position);
        } else if ((count == 2 || count == 3) && positions.contains(position)){
//            System.out.println("Stayed active");
//            System.out.println("Position: " + position + " count: " + count);
            newPositions.add(position);
        }
    }
}
