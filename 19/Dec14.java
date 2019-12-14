import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dec14 {
    public static void main(String[] args) {
        File file = new File("19/input14.txt");
        String input;
        Map<Chemical, Integer> chemicals = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String name;
            String amount;
            String[] splitString;
            while (!(input = reader.readLine()).equals("")){
                splitString = input.split(" => ");
                name = splitString[1].split(" ")[1];
                amount = splitString[1].split(" ")[0];
                Chemical chemical;
                Chemical component;

                if ((chemical = getChemicalByName(chemicals.keySet(), name)) == null){
                    chemical = new Chemical(name);
                    chemical.setAmount(Integer.parseInt(amount));
                }
                for (String string: splitString[0].split(", ")){
                    amount = string.split(" ")[0];
                    name = string.split(" ")[1];
                    if ((component = getChemicalByName(chemicals.keySet(), name)) == null){
                       component = new Chemical(name);
                    }
                    chemical.addComponent(component, Integer.parseInt(amount));
                }
            chemicals.put(chemical,chemical.getAmount());
            }
            System.out.println(chemicals);
            for (Chemical chemical: chemicals.keySet()){
                System.out.println(chemical.longString());
            }
            getOres(chemicals, "FUEL");
            System.out.println(chemicals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getOres(Map<Chemical, Integer> chemicals, String name){
        Chemical startChem = getChemicalByName(chemicals.keySet(), name);
        for (Chemical chem: startChem.getComponents().keySet()){
            int orgAmount = chemicals.get(chem);
            int compAmount = startChem.getComponentAmount(chem);
            chemicals.put(chem, orgAmount + compAmount);
        }
    }
    public static Chemical getChemicalByName(Set<Chemical> chemicals, String name){
        for (Chemical chemical : chemicals) {
            if (chemical.getName().equals(name)) {
                return chemical;
            }
        }
        return null;
    }
}

class Chemical{
    private HashMap<Chemical, Integer> components;
    private String name;
    private int amount;

    public Chemical(String name) {
        this.name = name;
        this.components = new HashMap<>();
        this.amount = 0;
    }

    public int getComponentAmount(Chemical chemical){
        return this.components.get(chemical);
    }

    public String longString() {
        return amount + " of " + name + " is made from " + shortComponents();
    }

    @Override
    public String toString() {
        return name;
    }

    public String shortComponents(){
        StringBuilder builder = new StringBuilder();
        for (Chemical chemical: components.keySet()){
            builder.append(components.get(chemical));
            builder.append(" ");
            builder.append(chemical.getName());
            builder.append(", ");
        }
        return builder.toString();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addComponent(Chemical chemical, int amount){
        this.components.put(chemical, amount);
    }

    public HashMap<Chemical, Integer> getComponents() {
        return components;
    }

    public String getName() {
        return name;
    }
}
