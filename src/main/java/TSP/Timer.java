package TSP;

public class Timer {
    private long start, stop;

    public void startTimer(){
        this.start = System.currentTimeMillis();
    }

    public void stopTimer(){
        this.stop = System.currentTimeMillis();
    }

    public void printTimer(){
        double sec = (double) (this.stop - this.start) / 1000.0;
        double min = sec / 60.0;
        System.out.println("Time elapsed: " + min + " min");
    }

}
