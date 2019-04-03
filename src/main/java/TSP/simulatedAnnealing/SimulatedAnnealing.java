package TSP.simulatedAnnealing;

import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private Random random;
    private final int NUM_ITERATION = 100;

    public SimulatedAnnealing(double temperature, double coolingRate, long seed) {
        this.temperature = temperature;
        this.coolingRate = coolingRate;
        this.random = new Random(seed);
    }

    public SimulatedAnnealing(long seed, double coolingRate) {
        this.random = new Random(seed);
        this.temperature = 100;
        this.coolingRate = coolingRate;
    }

    public Tour computeAlgorithm(Tour tour) {
        Tour currentTour = new Tour(tour);
        Tour bestTour = new Tour(currentTour);
        Tour candidateTour;
        TwoOpt twoOpt = new TwoOpt();

        while (temperature > 0 && Timer.getElapsedTime() < Timer.ENDTIME) {
            for (int i=0; i<NUM_ITERATION; i++) {
                Tour neighborTour = computeNeighbor(currentTour);
                candidateTour = twoOpt.computeAlgorithm(neighborTour);
                if (candidateTour.getTotalDistance() < currentTour.getTotalDistance()) {
                    currentTour = new Tour(candidateTour);
                    if (currentTour.getTotalDistance() < bestTour.getTotalDistance()) {
                        bestTour = new Tour(currentTour);
                    }
                } else if (random.nextDouble() < acceptNeighbour(currentTour.getTotalDistance(), candidateTour.getTotalDistance())) {
                    currentTour = new Tour(candidateTour);
                }
            }

            temperature *= coolingRate;
        }
        Timer.stopTimer();
        return bestTour;
    }


    private Tour computeNeighbor(Tour current){
        List<Integer> randomIndexes = new ArrayList<>();
        Tour neighbour = new Tour();
        //create 4 different random index that are sorted from the smallest to the bigger
        int rIndex;
        int size = TourManager.numberOfCities();
        for (int i=0; i<5; i++){
            do {
                rIndex = random.nextInt(size+1);
            }while (randomIndexes.contains(rIndex));
            randomIndexes.add(rIndex);
        }
        Collections.sort(randomIndexes);

//        start + a
        for (int i=0; i<randomIndexes.get(1); i++)
            neighbour.addIndexCities(current.get(i));
//        d
        for (int i=randomIndexes.get(3); i<randomIndexes.get(4); i++)
            neighbour.addIndexCities(current.get(i));
//        c
        for (int i=randomIndexes.get(2); i<randomIndexes.get(3); i++)
            neighbour.addIndexCities(current.get(i));
//        b
        for (int i=randomIndexes.get(1); i<randomIndexes.get(2); i++)
            neighbour.addIndexCities(current.get(i));
//        stop
        for (int i=randomIndexes.get(4); i<=size; i++)
            neighbour.addIndexCities(current.get(i));

        return neighbour;
    }

    //compute neighbor
//    private Tour computeNeighbor(Tour current) {
//        int randomIndex1, randomIndex2, randomIndex3, randomIndex4;
//        Tour neighbour = new Tour();
//        int bound = TourManager.numberOfCities() / 4;
//        int min = bound;
//        //create 4 different random index that are sorted from the smallest to the bigger
//        randomIndex1 = random.nextInt(bound) + 1;
//        randomIndex2 = random.nextInt(bound * 2 - min) + min;
//        min = bound * 2;
//        randomIndex3 = random.nextInt(bound * 3 - min) + min;
//        min = bound * 3;
//        randomIndex4 = random.nextInt(bound * 4 - min) + min - 1;
//
//        for (int i = 0; i <= randomIndex1; i++)
//            neighbour.addIndexCities(current.get(i));
//        for (int i = randomIndex3 + 1; i <= randomIndex4; i++)
//            neighbour.addIndexCities(current.get(i));
//        for (int i = randomIndex2 + 1; i <= randomIndex3; i++)
//            neighbour.addIndexCities(current.get(i));
//        for (int i = randomIndex1 + 1; i <= randomIndex2; i++)
//            neighbour.addIndexCities(current.get(i));
//        for (int i = randomIndex4 + 1; i <= TourManager.numberOfCities(); i++)
//            neighbour.addIndexCities(current.get(i));
//
//        return neighbour;
//    }

    private double acceptNeighbour(int currentDistance, int neighbourDistance) {
        if (neighbourDistance < currentDistance)
            return 1.0;
        return Math.exp((currentDistance - neighbourDistance) / temperature);
    }

}
