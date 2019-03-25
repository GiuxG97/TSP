package TSP.simulatedAnnealing;

import TSP.Tour;
import TSP.TourManager;

import java.util.Random;

public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private long randomSeed;

    public SimulatedAnnealing(double temperature, double coolingRate){
        this.temperature = temperature;
        this.coolingRate = coolingRate;
        this.randomSeed = 20;
    }

    public Tour computeAlgorithm(Tour tour){
        Tour currentTour = new Tour(tour);
        //bestTour point to the initialTour object
        currentTour.shuffle();
        currentTour.setTotalDistance(currentTour.computeTotalDistance());

        Tour bestTour = new Tour(currentTour);
//        currentTour.setTotalDistance(currentTour.computeTotalDistance());
        System.out.println("TOUR SHUFFLE!");
        currentTour.print();
        Random random = new Random(randomSeed);
        //this method only return the distance set after the nearest neighbour algorithm (not computed every time i call the method)
        int totalDistance = bestTour.getTotalDistance();
        int count = 0;
        while (temperature > 0.5){
            //and also neighbourTour point to the initialTour object
//            Tour neighbourTour = currentTour;
            Tour neighbourTour = computeNeighbour(currentTour, random);
//            int neighbourTourDistance = computePartialDistance(randomIndex1, randomIndex2, neighbourTour);

            int currentTourDistance = currentTour.getTotalDistance();
            int neighbourTourDistance = neighbourTour.getTotalDistance();
            //check if the neighbour tour should be accepted
            double P = acceptNeighbour(currentTourDistance, neighbourTourDistance);
            double randomRate = randomCompare(random);
            if (neighbourTourDistance < currentTourDistance){
                currentTour = new Tour(neighbourTour);
                if (neighbourTourDistance < bestTour.getTotalDistance()){
                    bestTour = new Tour(currentTour);
                    System.err.println("Best: " + neighbourTourDistance);
                }

            }
            else if (randomRate < P) {
//                currentTour = neighbourTour;
                currentTour = new Tour(neighbourTour);
            }

//            int currentDistance = currentTour.computeTotalDistance();
//            int bestDistance = bestTour.computeTotalDistance();

            temperature *= 1 - coolingRate;
            count++;
        }
        System.out.println("Conta: " + count);
        return bestTour;
    }

    private Tour computeNeighbour(Tour current, Random random){
        int randomIndex1 = 0, randomIndex2 = 0;
        //create 2 different random index
        while(randomIndex1 == randomIndex2){
            randomIndex1 = random.nextInt(TourManager.numberOfCities());
            randomIndex2 = random.nextInt(TourManager.numberOfCities());
        }

        Tour neighbourTour = new Tour(current);
        //get the indexes of the 2 random cities
        int indexCity1 = neighbourTour.get(randomIndex1);
        int indexCity2 = neighbourTour.get(randomIndex2);

        //swap these 2 cities to get the neighbour tour
        neighbourTour.setIndexCities(randomIndex1, indexCity2);
        neighbourTour.setIndexCities(randomIndex2, indexCity1);

        int [][] distanceMatrix = TourManager.getDistanceMatrix();
        int totalDistance = 0;
        for (int i=0; i<distanceMatrix.length; i++){
            totalDistance += distanceMatrix[neighbourTour.get(i)][neighbourTour.get(i+1)];
        }
        neighbourTour.setTotalDistance(totalDistance);
        return neighbourTour;
    }

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

    private int computePartialDistance(int indexCity1, int indexCity2, Tour tour){
        int distance;
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        if (indexCity1 == 0 && indexCity2 != distanceMatrix.length-1) {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1+1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2+1)];
        }
        else if (indexCity2 == 0 && indexCity1 != distanceMatrix.length-1){
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2+1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1+1)];
        }
        else if (indexCity1 == distanceMatrix.length-1 && indexCity2 != 0){
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2+1)];
        }
        else if (indexCity2 == distanceMatrix.length-1 && indexCity1 != 0){
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1-1)]
                    +distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1+1)];
        }
        else if (indexCity1 == 0 && indexCity2 == distanceMatrix.length-1){
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1+1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2-1)];
        }
        else if (indexCity2 == 0 && indexCity1 == distanceMatrix.length-1){
            distance = distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2+1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1-1)];
        }
        else {
            distance = distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1-1)]
                    + distanceMatrix[tour.get(indexCity1)][tour.get(indexCity1+1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2-1)]
                    + distanceMatrix[tour.get(indexCity2)][tour.get(indexCity2+1)];
        }
        return distance;
    }

    private double acceptNeighbour(int currentDistance, int neighbourDistance){
        if (neighbourDistance < currentDistance)
            return 1.0;
        double esp = (double)(currentDistance - neighbourDistance) / temperature;
        return Math.exp(esp);
    }

    private double randomCompare(Random random){
        //return random double between 0.0 and 1.0
//        int n = random.nextInt(1000);
//        double d = n / 1000.0;
        return random.nextDouble();
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

}
