import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class IntcodeComputerv2 {
    private long index;
    private long relative;
    private HashMap<Long, Long> memory;
    private Queue<Long> inputs;
    private Queue<Long> outputs;
    private Scanner reader;

    public IntcodeComputerv2(long[] instructions) {
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
    private void addInput(long input){
        this.inputs.add(input);
    }
    public long getOutput(){
        if (this.outputs.size() > 0) {
            return this.outputs.poll();
        }
        return -666;
    }
    public int run(){
        long data;
        while (true){
            data = this.memory.get(this.index++);
            int opcode = (int) (data % 100);
            int paramMode1 = (int) (data / 100 % 10);
            int paramMode2 = (int) (data / 1000 % 10);

            switch (opcode){
                case 99:
                    return 99;
                case 1:
                    data = getValue(paramMode1) + getValue(paramMode2);
                    this.memory.put(getValue(1), data);
                    break;
                case 2:
                    data = getValue(paramMode1) * getValue(paramMode2);
                    this.memory.put(getValue(1), data);
                    break;
                case 3:
                    long input;
                    if (this.inputs.isEmpty()){
                        System.out.print("Enter input: ");
                        input = reader.nextLong();
                    } else {
                        input = this.inputs.poll();
                    }
                    this.memory.put(getValue(1), input);
                    break;
                case 4:
                    this.outputs.add(getValue(paramMode1));
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
            }
        }
    }
    public long getExitCode(){
        return this.memory.get(0L);
    }

    private long getValue(int mode){
        long value = 0;
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
        }
        return value;
    }
}
