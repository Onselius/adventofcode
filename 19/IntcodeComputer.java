import java.util.ArrayList;
import java.util.Scanner;

public class IntcodeComputer {
    private ArrayList<Long> input;
    private ArrayList<Long> output;
    private long[] instructions;
    private int index;
    private int relativeIndex;
    private Scanner reader;

    public IntcodeComputer(long[] instructions) {
        this.instructions = instructions;
        this.index = 0;
        this.relativeIndex = 0;
        this.reader = new Scanner(System.in);
        this.input = new ArrayList<>(2);
        this.output = new ArrayList<>(5);
    }

    public void setInput(long input) {
        this.input.add(input);
    }

    public long getOutput() {
        if (this.output.size() > 0) {
            return this.output.get(0);
        }
        return -666L;
    }

    public int run() {
        String command;
        while (true) {
            command = String.valueOf(this.instructions[this.index]);
            if (command.endsWith("99")) {
                System.out.println("Exited because of input 99");
                return 99;
            } else if (command.endsWith("1")) {
                instruction1(command);
            } else if (command.endsWith("2")) {
                instruction2(command);
            } else if (command.endsWith("3")) {
                if (!instruction3(command)) {
                    break;
                }
            } else if (command.endsWith("4")) {
                instruction4(command);
                break;
            } else if (command.endsWith("5")) {
                instruction5(command);
            } else if (command.endsWith("6")) {
                instruction6(command);
            } else if (command.endsWith("7")) {
                instruction7(command);
            } else if (command.endsWith("8")) {
                instruction8(command);
            } else {
                System.out.println("Invalid input, terminating");
                System.out.println("Index is " + this.index);
                System.out.println("Value is " + command);
                break;
            }
        }
        return -1;
    }

    private ArrayList<String> populateParameters(String command) {
        ArrayList<String> parameters = new ArrayList<>();
        int length;
        for (int i = command.length() - 3; i >= 0; i--) {
            parameters.add(String.valueOf(command.charAt(i)));
        }
        length = parameters.size();
        for (int i = 2; i > length; i--) {
            parameters.add("0");
        }
        this.index++; // Index is now not on a value
        return parameters;
    }

    private ArrayList<Long> populateValues(ArrayList<String> parameters) {
        ArrayList<Long> values = new ArrayList<>(parameters.size());
        for (String parameter : parameters) {
            if (parameter.equals("0")) {
                values.add(this.instructions[Math.toIntExact(this.instructions[this.index])]);
            } else if (parameter.equals("1")) {
                values.add(this.instructions[this.index]);
            }
            this.index++;
        }
        return values;
    }

    private void instruction1(String command) {
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Long> values = populateValues(parameters);
        long pos = this.instructions[this.index];
        Long sum = 0L;
        for (Long number : values) {
            sum += number;
        }
        this.instructions[Math.toIntExact(pos)] = sum;
        //        System.out.println("Writing " + sum + " to position " + pos);
        this.index++; //Index is now on next instruction
    }

    private void instruction2(String command) {
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Long> values = populateValues(parameters);
        long pos = this.instructions[this.index];
        Long sum = 1L;
        for (Long number : values) {
            sum *= number;
        }
        this.instructions[Math.toIntExact(pos)] = sum;
        //        System.out.println("Writing " + sum + " to position " + pos);
        this.index++;
    }

    private boolean instruction3(String command) {
        Long input;
        if (this.input.isEmpty()) {
            System.out.print("Enter input: ");
            String userInput = this.reader.nextLine();
            if (userInput.equals("")) {
                return false;
            }
            input = Long.parseLong(userInput);
        } else {
            input = this.input.get(0);
            this.input.remove(0);
        }
        //        System.out.println("index for input is: " + this.index);
        this.index++;
        long pos = this.instructions[this.index];
        this.instructions[Math.toIntExact(pos)] = input;
        //        System.out.println("Writing " + input + " to position " + pos);
        //        System.out.println("Size of inputlist is " + this.inputList.size());
        this.index++;
        return true;
    }

    private void instruction4(String command) {
        ArrayList<String> parameters = populateParameters(command);
        long output;
        if (parameters.get(0).equals("0")) {
            output = this.instructions[Math.toIntExact(this.instructions[this.index])];
        } else {
            output = this.instructions[this.index];
        }
        //        System.out.println("index is " + this.index);
        //        System.out.println("Output is: " + output);
        this.output.add(0, output);
        this.index++;
    }

    private void instruction5(String command) {
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Long> values = populateValues(parameters);
        if (values.get(0) != 0) {
            this.index = Math.toIntExact(values.get(1));
        }
    }

    private void instruction6(String command) {
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Long> values = populateValues(parameters);
        if (values.get(0) == 0) {
            this.index = Math.toIntExact(values.get(1));
        }
    }

    private void instruction7(String command) {
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Long> values = populateValues(parameters);
        int pos = Math.toIntExact(this.instructions[this.index]);
        long value = 0L;
        if (values.get(0) < values.get(1)) {
            value = 1L;
        }
        this.instructions[pos] = value;
        //        System.out.println("Writing " + value + " to position " + pos);
        this.index++;
    }

    private void instruction8(String command) {
        ArrayList<String> parameters = populateParameters(command);
        ArrayList<Long> values = populateValues(parameters);
        int pos = Math.toIntExact(this.instructions[this.index]);
        long value = 0L;
        if (values.get(0).equals(values.get(1))) {
            value = 1L;
        }
        this.instructions[pos] = value;
        //        System.out.println("Writing " + value + " to position " + pos);
        this.index++;
    }

    public long getExitCode() {
        return this.instructions[0];
    }

    public void printInstructions() {
        for (int i = 0; i < this.index; i++) {
            System.out.println(this.instructions[i]);
        }
    }
}
