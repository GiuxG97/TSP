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
        System.out.println("Time elapsed: " + (this.stop - this.start) + "ms");
    }

}
