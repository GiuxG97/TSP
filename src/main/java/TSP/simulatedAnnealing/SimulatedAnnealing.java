package TSP.simulatedAnnealing;

import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private long randomSeed;
    private Timer timer;

    public SimulatedAnnealing(double temperature, double coolingRate) {
        this.temperature = temperature;
        this.coolingRate = coolingRate;
    }

    public SimulatedAnnealing(Timer timer){
        this.timer = timer;
    }

    public Tour computeAlgorithm(Tour tour) {
        Tour currentTour = new Tour(tour);
//        currentTour.shuffle();
//        currentTour.setTotalDistance(currentTour.computeTotalDistance());

        Tour bestTour = new Tour(currentTour);
//        System.out.println("TOUR SHUFFLE!");
//        currentTour.print();
        Random random = new Random(randomSeed);
//        int count = 0;
        Tour candidateTour;
        TwoOpt twoOpt = new TwoOpt();

        while (temperature > 0.5) {
            Tour neighborTour = computeNeighbor(currentTour, random);
//            int neighbourTourDistance = computePartialDistance(randomIndex1, randomIndex2, neighbourTour);

            candidateTour = twoOpt.computeAlgorithm(neighborTour);

//            int currentTourDistance = currentTour.getTotalDistance();
//            int neighbourTourDistance = neighbourTour.getTotalDistance();
//            //check if the neighbour tour should be accepted
//            if (neighbourTourDistance < currentTourDistance){
//                currentTour = new Tour(neighbourTour);
//                if (neighbourTourDistance < bestTour.getTotalDistance()){
//                    bestTour = new Tour(currentTour);
//                    System.err.println("Best: " + neighbourTourDistance);
//                }
//            }

            if (candidateTour.getTotalDistance() < currentTour.getTotalDistance()) {
                currentTour = new Tour(candidateTour);
                if (currentTour.getTotalDistance() < bestTour.getTotalDistance()) {
                    bestTour = new Tour(currentTour);
//                    System.err.println("Best: " + bestTour.getTotalDistance());
                }
            } else if (random.nextDouble() < acceptNeighbour(currentTour.getTotalDistance(), candidateTour.getTotalDistance())) {
                currentTour = new Tour(candidateTour);
            }

            temperature *= coolingRate;
//            count++;
        }
//        System.out.println("Conta: " + count);
        bestTour = twoOpt.computeAlgorithm(bestTour);
        return bestTour;
    }

    //compute neighbor
    private Tour computeNeighbor(Tour current, Random random) {
        int randomIndex1, randomIndex2, randomIndex3, randomIndex4;
        Tour neighbour = new Tour();
        int bound = TourManager.numberOfCities() / 4;
        int min = bound;
        //create 4 different random index that are sorted from the smallest to the bigger
        randomIndex1 = random.nextInt(bound)+1;
        randomIndex2 = random.nextInt(bound * 2 - min) + min;
        min = bound * 2;
        randomIndex3 = random.nextInt(bound * 3 - min) + min;
        min = bound * 3;
        randomIndex4 = random.nextInt(bound * 4 - min) + min-1;

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

//    private Tour computeNeighbor(Tour current, Random random){
//        int randomIndex1 = 0, randomIndex2 = 0;
//        //create 2 different random index
//        while(randomIndex1 == randomIndex2){
//            randomIndex1 = random.nextInt(TourManager.numberOfCities());
//            randomIndex2 = random.nextInt(TourManager.numberOfCities());
//        }
//
//        Tour neighbourTour = new Tour(current);
//        //get the indexes of the 2 random cities
//        int indexCity1 = neighbourTour.get(randomIndex1);
//        int indexCity2 = neighbourTour.get(randomIndex2);
//
//        //swap these 2 cities to get the neighbour tour
//        neighbourTour.setIndexCities(randomIndex1, indexCity2);
//        neighbourTour.setIndexCities(randomIndex2, indexCity1);
//
//        int [][] distanceMatrix = TourManager.getDistanceMatrix();
//        int totalDistance = 0;
//        for (int i=0; i<distanceMatrix.length; i++){
//            totalDistance += distanceMatrix[neighbourTour.get(i)][neighbourTour.get(i+1)];
//        }
//        neighbourTour.setTotalDistance(totalDistance);
//        return neighbourTour;
//    }

    //too specific for the distance of the swap tour
    /*private int computePartialDistance(int indexCity1, int indexCity2, Tour tour){
        int distance;
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        if (indexCity1 == 0 && indexCity2 != distanceMatrix.length-1) {
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1+1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2+1)];
        }
        else if (indexCity2 == 0 && indexCity1 != distanceMatrix.length-1){
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2+1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1+1)];
        }
        else if (indexCity1 == distanceMatrix.length-1 && indexCity2 != 0){
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2+1)];
        }
        else if (indexCity2 == distanceMatrix.length-1 && indexCity1 != 0){
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1-1)]
                    +distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1+1)];
        }
        else if (indexCity1 == 0 && indexCity2 == distanceMatrix.length){
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1+1)];
        }
        else if (indexCity2 == 0 && indexCity1 == distanceMatrix.length){
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2+1)];
        }
        else {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity2+1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity1+1)];
        }
        return distance;
    }*/

    private int computePartialDistance(int indexCity1, int indexCity2, Tour tour) {
        int distance;
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        if (indexCity1 == 0 && indexCity2 != distanceMatrix.length - 1) {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 + 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 - 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 + 1)];
        } else if (indexCity2 == 0 && indexCity1 != distanceMatrix.length - 1) {
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 + 1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 - 1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 + 1)];
        } else if (indexCity1 == distanceMatrix.length - 1 && indexCity2 != 0) {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 - 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 - 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 + 1)];
        } else if (indexCity2 == distanceMatrix.length - 1 && indexCity1 != 0) {
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 - 1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 - 1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 + 1)];
        } else if (indexCity1 == 0 && indexCity2 == distanceMatrix.length - 1) {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 + 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 - 1)];
        } else if (indexCity2 == 0 && indexCity1 == distanceMatrix.length - 1) {
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 + 1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 - 1)];
        } else {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 - 1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1 + 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 - 1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2 + 1)];
        }
        return distance;
    }

    private double acceptNeighbour(int currentDistance, int neighbourDistance) {
        if (neighbourDistance < currentDistance)
            return 1.0;
        return Math.exp((currentDistance - neighbourDistance) / temperature);
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

}
