import java.io.*;
import java.util.*;

public class Dec20 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        File file = new File("19/input20.txt");
        String baseString;
        Map<List<Integer>, Character> grid = new HashMap<>();
        Map<List<Integer>, Integer> steps = new LinkedHashMap<>();
        Map<List<Integer>, List<Integer>> portals = new HashMap<>();
        List<List<Integer>> paths = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int y = 0;
            int x;
            char c;
            while (!(baseString = reader.readLine()).equals("")){
                for (x = 0; x < baseString.length(); x++){
                    if ((c = baseString.charAt(x)) > 32) {
                        grid.put(List.of(y,x),c);
                    }
                }
                y++;
            }
            printGrid(grid);
            insertPortals(grid, portals);
            System.out.println(portals);


        } catch (IOException e) {
            e.printStackTrace();
        }
        timer.stopTime();

    }
    public static List<Integer> getNextPosition(){

        return null;
    }
    public static void insertPortals(Map<List<Integer>, Character> grid, Map<List<Integer>, List<Integer>> portals){
        /*
        foreach gridPosition
            if value > 64
                if getValidpositions > 64
                    if portals.get(portalname) != null
                        portalA.put(onePosition, otherPosition)
                        portalB.put(otherPosition, onePosition)
                        portals.remove(portalname)
                    else
                        portals.put(portalname, onePosition)

         */
        Set<Character> tempPortalName;
        List<List<Integer>> move;
        HashMap<Set<Character>, List<Integer>> temporaryPortals = new HashMap<>();
        for (Map.Entry<List<Integer>, Character> entry: grid.entrySet()){
            if ( (entry.getValue() > 64) && ((move = getValidMove(grid, entry.getKey())).size() == 1) ){
                tempPortalName = new HashSet<>(2);
                tempPortalName.add(grid.get(move.get(0)));
                tempPortalName.add(grid.get(entry.getKey()));
                if (temporaryPortals.containsKey(tempPortalName)){
                    portals.putIfAbsent(move.get(0), temporaryPortals.get(tempPortalName));
                    portals.putIfAbsent(temporaryPortals.get(tempPortalName), move.get(0));
                    System.out.println("Existing portal found, name: " + tempPortalName + " position: " + move.get(0));
                } else {
                    temporaryPortals.put(Set.copyOf(tempPortalName), move.get(0));
                    System.out.println("New portal found, name: " + tempPortalName + " position: " + move.get(0));
                }
            }
        }
    }
    public static List<List<Integer>> getValidMove(Map<List<Integer>, Character> grid, List<Integer> position){
        List<List<Integer>> moves = new ArrayList<>(4);
        List<Integer> validMove;
        if (grid.containsKey((validMove = List.of(position.get(0)+1, position.get(1))))){
            moves.add(List.copyOf(validMove));
        }
        if (grid.containsKey((validMove = List.of(position.get(0)-1, position.get(1))))){
            moves.add(List.copyOf(validMove));
        }
        if (grid.containsKey((validMove = List.of(position.get(0), position.get(1)+1)))){
            moves.add(List.copyOf(validMove));
        }
        if (grid.containsKey((validMove = List.of(position.get(0), position.get(1)-1)))){
            moves.add(List.copyOf(validMove));
        }
        return moves;
    }
    public static void printGrid(Map<List<Integer>, Character> grid){
        List<Integer> position;
        for (int y = 0; y < 20; y++){
            for (int x = 0; x < 50; x++){
                position = List.of(y,x);
                if (grid.containsKey(position)){
                    System.out.print(grid.get(position));
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }
}
