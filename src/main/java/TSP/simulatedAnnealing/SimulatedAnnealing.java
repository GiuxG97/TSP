package TSP.simulatedAnnealing;

import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;

import java.util.Random;

public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private Random random;

    public SimulatedAnnealing(double temperature, double coolingRate, long seed) {
        this.temperature = temperature;
        this.coolingRate = coolingRate;
        this.random = new Random(seed);
    }

    public SimulatedAnnealing(long seed) {
        this.random = new Random(seed);
        this.temperature = Timer.ENDTIME;
    }

    public Tour computeAlgorithm(Tour tour) {
        Tour currentTour = new Tour(tour);
        Tour bestTour = new Tour(currentTour);
        Tour candidateTour;
        TwoOpt twoOpt = new TwoOpt();

        while (Timer.getElapsedTime() < Timer.ENDTIME) {
            Tour neighborTour = computeNeighbor(currentTour, random);

            candidateTour = twoOpt.computeAlgorithm(neighborTour);
            if (candidateTour.getTotalDistance() < currentTour.getTotalDistance()) {
                currentTour = new Tour(candidateTour);
                if (currentTour.getTotalDistance() < bestTour.getTotalDistance()) {
                    bestTour = new Tour(currentTour);
                }
            } else if (random.nextDouble() < acceptNeighbour(currentTour.getTotalDistance(), candidateTour.getTotalDistance())) {
                currentTour = new Tour(candidateTour);
            }
            temperature = Timer.ENDTIME - Timer.getElapsedTime();
        }
        Timer.stopTimer();
        return bestTour;
    }

    //compute neighbor
    private Tour computeNeighbor(Tour current, Random random) {
        int randomIndex1, randomIndex2, randomIndex3, randomIndex4;
        Tour neighbour = new Tour();
        int bound = TourManager.numberOfCities() / 4;
        //create 4 different random index that are sorted from the smallest to the bigger
        randomIndex1 = random.nextInt(bound);
        randomIndex2 = random.nextInt(bound) + bound;
        randomIndex3 = random.nextInt(bound) + bound*2;
        randomIndex4 = random.nextInt(bound) + bound*3;

        for (int i = 0; i <= randomIndex1; i++)
            neighbour.addIndexCities(current.get(i));
        for (int i = randomIndex3 + 1; i <= randomIndex4; i++)
            neighbour.addIndexCities(current.get(i));
        for (int i = randomIndex2 + 1; i <= randomIndex3; i++)
            neighbour.addIndexCities(current.get(i));
        for (int i = randomIndex1 + 1; i <= randomIndex2; i++)
            neighbour.addIndexCities(current.get(i));
        for (int i = randomIndex4 + 1; i <= TourManager.numberOfCities(); i++)
            neighbour.addIndexCities(current.get(i));

        return neighbour;
    }

    private double acceptNeighbour(int currentDistance, int neighbourDistance) {
        if (neighbourDistance < currentDistance)
            return 1.0;
        return Math.exp((currentDistance - neighbourDistance) / temperature);
    }

}
