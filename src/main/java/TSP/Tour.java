package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tour {

    private List<Integer> indexCities;
    private int totalDistance = 0;

    public Tour() {
        this.indexCities = new ArrayList<>();
    }

    public Tour(Tour other) {
        this(other.getIndexCities());
        this.totalDistance = other.totalDistance;
    }

    public Tour(List<Integer> indexCities) {
        this.indexCities = new ArrayList<>(indexCities);
    }

    public void addIndexCities(int index) {
        this.indexCities.add(index);
    }

    public int size() {
        return this.indexCities.size();
    }

    public int get(int index) {
        return this.indexCities.get(index);
    }

    public List<Integer> getIndexCities() {
        return this.indexCities;
    }

    public void setIndexCities(int position, int indexCity) {
        this.indexCities.set(position, indexCity);
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int computeTotalDistance() {
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        totalDistance = 0;
        for (int i = 0; i < distanceMatrix.length; i++) {
            totalDistance += distanceMatrix[indexCities.get(i)][indexCities.get(i + 1)];
        }
        return totalDistance;
    }

    public void print() {
        int size = indexCities.size();
//        System.out.println();
        System.out.print("TOUR: ");
        for (int i = 0; i < size; i++) {
            if (i < size - 1)
                System.out.print(indexCities.get(i) + 1 + " -> ");
            else
                System.out.print(indexCities.get(i) + 1);
        }
        System.out.println();
        System.out.println("Total distance: " + totalDistance);
    }

    public void shuffle() {
        Collections.shuffle(indexCities);
    }

}
