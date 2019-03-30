package TSP;

public class Timer {
    public static final long ENDTIME = 179900;
    private static long start, stop;

    public static void startTimer(){
        start = System.currentTimeMillis();
    }

    public static void stopTimer(){
        stop = System.currentTimeMillis();
    }

    public static long getElapsedTime(){
        return System.currentTimeMillis() - start;
    }

    public void printTimer(){
        double sec = (double) (stop - start) / 1000.0;
        double min = sec / 60.0;
        double rest = min - Math.floor(min);
        System.out.println("Time elapsed: " + (int)Math.floor(min) + ":" + (int)Math.floor(rest*60));
    }

}
