package TSP;

public class Timer {
    public static final long ENDTIME = 180000;
    private long start, stop;

    public void startTimer(){
        this.start = System.currentTimeMillis();
    }

    public void stopTimer(){
        this.stop = System.currentTimeMillis();
    }

    public long getElapsedTime(){
        return this.stop - this.start;
    }

    public void printTimer(){
        double sec = (double) (this.stop - this.start) / 1000.0;
        double min = sec / 60.0;
        double rest = min - Math.floor(min);
        System.out.println("Time elapsed: " + (int)Math.floor(min) + ":" + (int)Math.floor(rest*60));
    }

}
