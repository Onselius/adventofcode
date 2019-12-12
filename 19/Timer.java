import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Timer {
    private long startTime;
    private long stopTime;

    public Timer() {
        this.startTime = System.nanoTime();
    }
    public void stopTime(){
        this.stopTime = System.nanoTime();
        System.out.println(((float) stopTime - startTime)/1000000000 + " seconds");
    }
}
