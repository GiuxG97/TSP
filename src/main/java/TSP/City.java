package TSP;

public class City {
    private int id;
    private Coordinate coordinate;

    public City(int id, Coordinate coordinate){
        this.id = id;
        this.coordinate = coordinate;
    }

    public int getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
