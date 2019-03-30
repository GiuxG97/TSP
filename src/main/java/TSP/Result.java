package TSP;

public class Result {
    private long time;
    private double error;
    private long seed;

    public Result(double error){
        this.error = error;
    }

    public Result(double error, long time, long seed){
        this.error = error;
        this.time = time;
        this.seed = seed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
