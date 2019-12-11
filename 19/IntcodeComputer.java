import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class IntcodeComputer {
    private long index;
    private long relative;
    private HashMap<Long, Long> memory;
    private Queue<Long> inputs;
    private Queue<Long> outputs;
    private Scanner reader;

    public IntcodeComputer(long[] instructions) {
        this.index = 0;
        this.relative = 0;
        this.inputs = new LinkedList<>();
        this.outputs = new LinkedList<>();
        this.reader = new Scanner(System.in);
        this.memory = new HashMap<>(instructions.length + 50);
        insertValues(instructions);
    }
    private void insertValues(long[] instructions){
        for (int i = 0; i < instructions.length; i++){
            this.memory.put((long) i, instructions[i]);
        }
    }
    public HashMap<Long, Long> getMemory(){
        return this.memory;
    }
    public void addInput(long input){
        this.inputs.add(input);
    }
    public Long getOutput(){
        return this.outputs.poll();
    }
    public int run(){
        long data;
        while (true){
//            printStatus();
            data = this.memory.get(this.index++);
            int opcode = (int) (data % 100);
            int paramMode1 = (int) (data / 100 % 10);
            int paramMode2 = (int) (data / 1000 % 10);
            int paramMode3 = (int) (data / 10000 % 10);

            switch (opcode){
                case 99:
                    return 99;
                case 1:
                    data = getValue(paramMode1) + getValue(paramMode2);
                    setValue(paramMode3, data);
                    break;
                case 2:
                    data = getValue(paramMode1) * getValue(paramMode2);
                    setValue(paramMode3, data);
                    break;
                case 3:
                    long input;
                    if (this.inputs.isEmpty()){
                        this.index--;
                        return 3;
                    } else {
                        input = this.inputs.poll();
                    }
                    setValue(paramMode1, input);
                    return 3;
                case 4:
                    this.outputs.add(getValue(paramMode1));
                    break;
                case 5:
                    if (getValue(paramMode1) != 0){
                        this.index = getValue(paramMode2);
                    }else {
                        this.index++;
                    }
                    break;
                case 6:
                    if (getValue(paramMode1) == 0) {
                        this.index = getValue(paramMode2);
                    }else {
                        this.index++;
                    }
                    break;
                case 7:
                    if (getValue(paramMode1) < getValue(paramMode2)){
                        setValue(paramMode3, 1);
                    } else {
                        setValue(paramMode3, 0);
                    }
                    break;
                case 8:
                    if (getValue(paramMode1) == getValue(paramMode2)){
                        setValue(paramMode3, 1);
                    } else {
                        setValue(paramMode3, 0);
                    }
                    break;
                case 9:
                    this.relative += getValue(paramMode1);
                    break;
            }
        }
    }
    public long getExitCode(){
        return this.memory.get(0L);
    }
    private void setValue(int mode, long value){
        if (mode == 0 || mode == 1){
            this.memory.put(getValue(1), value);
        } else {
            this.memory.put(getValue(3), value);
        }
    }

    private long getValue(int mode){
        long value = 0;
        try {
            switch (mode){
                case 0:
                    value = this.memory.get(this.memory.get(this.index++));
                    break;
                case 1:
                    value = this.memory.get(this.index++);
                    break;
                case 2:
                    value = this.memory.get(this.memory.get(this.index++) + this.relative);
                    break;
                case 3:
                    value = this.memory.get(this.index++) + this.relative;
            }
        } catch (Exception e) {
            value = 0;
        }
        return value;
    }
    private void printStatus(){
        System.out.println("Index: " + this.index);
        System.out.println("Relative: " + this.relative);
        long value = this.memory.get(this.index);
        System.out.println("Value: " + this.memory.get(this.index));
        System.out.println("Value at index: " + this.memory.get(value));
//        System.out.println("Memory: " + this.memory);
    }
}
