import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class dec05 {
    public static void main(String[] args) {
        File input = new File("19/input05.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String baseString = reader.readLine();
            int[] instructions = Arrays.stream(baseString.split(",")).mapToInt(Integer::parseInt).toArray();

            IntcodeComputer computer = new IntcodeComputer(instructions);
            computer.run();
            System.out.println(computer.getExitCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class IntcodeComputer{
    private int[] instructions;
    private int index;
    private Scanner reader;

    public IntcodeComputer(int[] instructions) {
        this.instructions = instructions;
        this.index = 0;
        this.reader = new Scanner(System.in);
    }
    public void run(){
        String command;
        while(true){
            command = String.valueOf(this.instructions[this.index]);
            if (command.endsWith("99")){
                System.out.println("Exited because of input 99");
                break;
            } else if (command.endsWith("1")){
                instruction1(command);
            } else if (command.endsWith("2")){
                instruction2(command);
            } else if (command.endsWith("3")){
                instruction3(command);
            } else if (command.endsWith("4")){
                instruction4(command);
            } else {
                System.out.println("Invalid input, terminating");
                System.out.println("Index is " + this.index);
                System.out.println("Value is " + command);
                break;
            }
        }
    }
    public ArrayList<String> populateParameters(String command){
//        System.out.println("populating parameters");
        ArrayList<String> parameters = new ArrayList<>();
        int length;
        for (int i = command.length() - 3; i >= 0; i--){
            parameters.add(String.valueOf(command.charAt(i)));
        }
        length = parameters.size();
        for (int i = 2; i > length; i--){
            parameters.add("0");
        }
        this.index++; // Index is now not on a value
        return parameters;
    }

    public ArrayList<Integer> populateValues(ArrayList<String> parameters){
//        System.out.println("populating values");
        ArrayList<Integer> values = new ArrayList<>(parameters.size());
//        System.out.println(parameters);
        for (String parameter : parameters) {
            if (parameter.equals("0")) {
                values.add(this.instructions[this.instructions[this.index]]);
            } else if (parameter.equals("1")) {
                values.add(this.instructions[this.index]);
            }
            this.index++;
        }
        return values;
    }

    public void instruction1(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        int pos = this.instructions[this.index];
        int sum = 0;
        for (int number : values){
            sum += number;
        }
        this.instructions[pos] = sum;
        System.out.println("Writing " + sum + " to position " + pos);
        this.index++; //Index is now on next instruction
    }
    public void instruction2(String command){
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Integer> values = populateValues(parameters);
        int pos = this.instructions[this.index];
        int sum = 1;
        for (int number : values){
            sum *= number;
        }
        this.instructions[pos] = sum;
        System.out.println("Writing " + sum + " to position " + pos);
        this.index++;
    }
    public void instruction3(String command){
        System.out.print("Enter input: ");
        String input = this.reader.nextLine();
        this.index++;
        int pos = this.instructions[this.index];
        this.instructions[pos] = Integer.parseInt(input);
        System.out.println("Writing " + input + " to position " + pos);
        this.index++;
    }
    public void instruction4(String command){
        ArrayList<String> parameters = populateParameters(command);
        int output;
        if (parameters.get(0).equals("0")){
            output = this.instructions[this.instructions[this.index]];
        } else {
            output = this.instructions[this.index];
        }
        System.out.println("index is " + this.index);
        System.out.println("Output is: " + output);
        this.index++;
    }

    public int getExitCode(){
        return this.instructions[0];
    }
    public void printInstructions(){
        for (int i = 0; i < this.index; i++){
            System.out.println(this.instructions[i]);
        }
    }
}
