import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class dec06 {
    private HashMap<String, Integer> orbits;
    HashSet<String[]> input;
    HashSet<String[]> originalInput;
    HashSet<String> myWayToSanta;
    public static void main(String[] args) {
        dec06 calculator = new dec06();
        calculator.run();
    }
    public void run(){
        File file = new File("19/input06.txt");
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (!(line = reader.readLine()).equals("")){
                this.input.add(line.split("\\)"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.originalInput = (HashSet<String[]>) this.input.clone();
        HashSet<String> foundObject = new HashSet<>();
        foundObject.add("COM");
        this.orbits.put("COM", 0);
        updateOrbitValues(foundObject);
        int sum = 0;
        for (int value : this.orbits.values()){
            sum += value;
        }
        System.out.println("Total orbits are: " + sum);
        myWayToCOM("YOU");
        System.out.println("Total orbits to COM from YOU: " + this.myWayToSanta.size());
        String commonObject = searchForSanta("SAN");
        System.out.println("Found common object: " + commonObject);
        int orbitsForMe = this.orbits.get("YOU") - this.orbits.get(commonObject) - 1;
        int orbitsForSanta = this.orbits.get("SAN") - this.orbits.get(commonObject) - 1;
        System.out.println("Common object: " + commonObject);
        System.out.println("Steps for me to object: " + orbitsForMe);
        System.out.println("Steps for Santa to object: " + orbitsForSanta);
        System.out.println("Steps for me to Santa: " + (orbitsForMe + orbitsForSanta));
    }

    public dec06() {
        this.orbits = new HashMap<>();
        this.input = new HashSet<>();
        this.myWayToSanta = new HashSet<>();
    }
    public void myWayToCOM(String searchString){
        String searchFor = "";
        for (String[] object: this.originalInput){
            if (searchString.equals(object[1])){
                searchFor = object[0];
                this.myWayToSanta.add(searchFor);
                break;
            }
        }
        if (!searchFor.equals("COM")) {
            myWayToCOM(searchFor);
        }
    }
    public String searchForSanta(String searchString){
        String searchFor = "";
        String foundObject = "";
        for (String[] object : this.originalInput){
            if (searchString.equals(object[1])){
                searchFor = object[0];
                break;
            }
        }
        foundObject = searchFor;
        if (!this.myWayToSanta.contains(searchFor)){
            foundObject = searchForSanta(searchFor);
        }
        return foundObject;
    }

    public void updateOrbitValues(HashSet<String> foundObjects){
        HashSet<String[]> setToRemove = new HashSet<>();
        HashSet<String> setToSearch = new HashSet<>();
        for (String[] object: this.input){
            if (foundObjects.contains(object[0])){
                int indirectValue = this.orbits.get(object[0]);
                this.orbits.put(object[1], indirectValue+1);
                setToRemove.add(object);
                setToSearch.add(object[1]);
            }
        }
        this.input.removeAll(setToRemove);
        if (this.input.size() > 0){
            updateOrbitValues(setToSearch);
        }
    }
}
