import java.io.*;
import java.util.*;

public class Dec20 {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        File file = new File("19/input20.txt");
        String baseString;
        Map<List<Integer>, Character> grid = new HashMap<>();
        Map<List<Integer>, Integer> steps = new LinkedHashMap<>();
        Map<List<Integer>, List<Integer>> portals;
        List<Integer> position;
        List<Integer> endPosition;
        Queue<List<Integer>> nextStep = new LinkedList<>();
        Map<Set<Character>, List<Integer>> innerPortals;
        Map<Set<Character>, List<Integer>> outerPortals;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int y = 0;
            int x;
            char c;
            while (!(baseString = reader.readLine()).equals("")){
                for (x = 0; x < baseString.length(); x++){
                    if ((c = baseString.charAt(x)) > 35) {
                        grid.put(List.of(y,x, 0),c);
                    }
                }
                y++;
            }
            position = getPortalPosition(grid, 'A');
            endPosition = getPortalPosition(grid, 'Z');
            System.out.println("Start position: " + position);
            nextStep.add(position);
            steps.put(position, 0);
            portals = getPortals(grid);
            while (!nextStep.isEmpty() && !steps.containsKey(endPosition)){
                for (int i = 0, q = nextStep.size(); i < q; i++){
                    position = nextStep.poll();
                    for (List<Integer> move: getValidMoves(grid, position)){
                        move = checkForPortal(grid, portals, move);
                        if (steps.getOrDefault(move, 0) < (steps.get(position) + 1)){
                            steps.put(move, steps.get(position) + 1);
                            nextStep.add(move);
                        }
                    }
                }
            }
            printGrid(grid, steps);
            System.out.println(grid);
            System.out.println("valid: " + getValidMoves(grid, endPosition));
            System.out.println("End position: " + endPosition);
            System.out.println("Number of steps: " + steps.get(endPosition));

        } catch (IOException e) {
            e.printStackTrace();
        }
        timer.stopTime();
    }
    public static List<Integer> getPortalPosition(Map<List<Integer>, Character> grid, char portalName){
        List<List<Integer>> moves;
        for (List<Integer> position: grid.keySet()){
            if (grid.get(position).equals(portalName) && (moves = getValidMoves(grid, position)).size() == 1){
                if (grid.get(moves.get(0)).equals(portalName)){
                    for (List<Integer> move: getValidMoves(grid, moves.get(0))){
                        if (grid.get(move).equals('.')){
                            return move;
                        }
                    }

                }
            }
        }
        return new ArrayList<>();

    }
    public static List<Integer> checkForPortal(Map<List<Integer>, Character> grid,
                                               Map<List<Integer>, List<Integer>> portals,
                                               List<Integer> position){
        if (portals.containsKey(position)){
            List<List<Integer>> moves = getValidMoves(grid, portals.get(position));
            for (List<Integer> move: moves){
                if (grid.get(move).equals('.')){
                    return move;
                }
            }
        }
        return position;
    }
    public static Map<List<Integer>, List<Integer>> getPortals(Map<List<Integer>, Character> grid){
        /*
        If y or x == 0, then add to inner/outer respectively
        During moves, check if nextposition is a letter, att to set and get position from portal, keep track of levels.
         */
        Map<List<Integer>, List<Integer>> portals = new HashMap<>();
        Set<Character> tempPortalName;
        List<List<Integer>> move;
        HashMap<Set<Character>, List<Integer>> temporaryPortals = new HashMap<>();
        for (Map.Entry<List<Integer>, Character> entry: grid.entrySet()){
            if ( (entry.getValue() > 64) && ((move = getValidMoves(grid, entry.getKey())).size() == 1) ){
                tempPortalName = new HashSet<>(2);
                tempPortalName.add(grid.get(move.get(0)));
                tempPortalName.add(grid.get(entry.getKey()));
                if (temporaryPortals.containsKey(tempPortalName)){
                    portals.putIfAbsent(move.get(0), temporaryPortals.get(tempPortalName));
                    portals.putIfAbsent(temporaryPortals.get(tempPortalName), move.get(0));
//                    System.out.println("Existing portal found, name: " + tempPortalName + " position: " + move.get(0));
                } else {
                    temporaryPortals.put(Set.copyOf(tempPortalName), move.get(0));
//                    System.out.println("New portal found, name: " + tempPortalName + " position: " + move.get(0));
                }
            }
        }
        return portals;
    }
    public static List<List<Integer>> getValidMoves(Map<List<Integer>, Character> grid, List<Integer> position){
        List<List<Integer>> moves = new ArrayList<>(4);
        List<Integer> validMove;
        try {
            if (grid.containsKey((validMove = List.of(position.get(0)+1, position.get(1), position.get(2))))){
                moves.add(validMove);
            }
            if (grid.containsKey((validMove = List.of(position.get(0)-1, position.get(1), position.get(2))))){
                moves.add(validMove);
            }
            if (grid.containsKey((validMove = List.of(position.get(0), position.get(1)+1, position.get(2))))){
                moves.add(validMove);
            }
            if (grid.containsKey((validMove = List.of(position.get(0), position.get(1)-1, position.get(2))))){
                moves.add(validMove);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return moves;
    }
    public static void printGrid(Map<List<Integer>, Character> grid, Map<List<Integer>, Integer> steps){
        List<Integer> position;
        for (int y = 0; y < 150; y++){
            for (int x = 0; x < 150; x++){
                position = List.of(y,x, 0);
                if (steps.containsKey(position)){
                    System.out.print(",");
                } else if (grid.containsKey(position)){
                    System.out.print(grid.get(position));
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }

    }
}
