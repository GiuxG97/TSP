package TSP.simulatedAnnealing;

import TSP.City;
import TSP.Tour;
import TSP.TourManager;

import java.util.List;
import java.util.Random;

public class NearestNeighbour {

    private long randomSeed;

    public NearestNeighbour(List<City> cities) {
        //if user doesn't specify a seed i assign it manually
        this.randomSeed = 20;
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public Tour computeAlgorithm() {
        Tour tour = new Tour();
        int totalDistance = 0;
        Random random = new Random(randomSeed);
        int index = random.nextInt(TourManager.numberOfCities());
//        int index = 0;
        int startIndex = index;
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        //array use to check is a city is alredy visited
        int[] citiesVisited = new int[distanceMatrix.length];
        citiesVisited[startIndex] = -1;

        tour.addIndexCities(startIndex);
        int temp, indexTemp = 0;
        for (int i = 0; i < distanceMatrix.length-1; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < distanceMatrix.length; j++) {
                temp = distanceMatrix[index][j];
                if (temp < min && temp != 0 && citiesVisited[j] != -1) {
                    min = temp;
                    indexTemp = j;
                }
            }
            index = indexTemp;
            citiesVisited[index] = -1;
            tour.addIndexCities(index);
            totalDistance += min;
        }
        //link the last city with the first city
        tour.addIndexCities(startIndex);
        totalDistance += distanceMatrix[index][startIndex];
        tour.setTotalDistance(totalDistance);
        return tour;
    }


}
