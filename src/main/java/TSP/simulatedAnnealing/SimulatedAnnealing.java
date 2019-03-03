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

    public Tour computeAlgorithm(Tour currentTour){
        //bestTour point to the initialTour object
        Tour bestTour = new Tour(currentTour.getIndexCities());
        Random random = new Random(randomSeed);
        int numberCities = TourManager.numberOfCities();
        //this method only return the distance set after the nearest neighbour algorithm (not computed every time i call the method)
        int totalDistance = bestTour.getTotalDistance();
        int count = 0;
        while (temperature > 0.5){
            int randomIndex1 = 0, randomIndex2 = 0;
            //and also neighbourTour point to the initialTour object
//            Tour neighbourTour = currentTour;
            Tour neighbourTour = new Tour(currentTour.getIndexCities());
            //create 2 different random index
            while(randomIndex1 == randomIndex2){
                randomIndex1 = random.nextInt(numberCities);
                randomIndex2 = random.nextInt(numberCities);
            }
            //get the indexes of the 2 random cities
            int indexCity1 = neighbourTour.get(randomIndex1);
            int indexCity2 = neighbourTour.get(randomIndex2);

//            int currentTourDistance = computePartialDistance(randomIndex1, randomIndex2, currentTour);

            //swap these 2 cities to get the neighbour tour
            neighbourTour.setIndexCities(randomIndex1, indexCity2);
            neighbourTour.setIndexCities(randomIndex2, indexCity1);

//            int neighbourTourDistance = computePartialDistance(randomIndex1, randomIndex2, neighbourTour);

            int currentTourDistance = currentTour.computeTotalDistance();
            int neighbourTourDistance = neighbourTour.computeTotalDistance();
            //check if the neighbour tour should be accepted
            double P = acceptNeighbour(currentTourDistance, neighbourTourDistance);
            double randomRate = randomCompare();
            if (acceptNeighbour(currentTourDistance, neighbourTourDistance) >= randomCompare())
//                currentTour = neighbourTour;
                currentTour = new Tour(neighbourTour.getIndexCities());

            int currentDistance = currentTour.computeTotalDistance();
            int bestDistance = bestTour.computeTotalDistance();
            if (currentTour.computeTotalDistance() < bestTour.computeTotalDistance())
                bestTour = new Tour(currentTour.getIndexCities());

            temperature *= 1 - coolingRate;
            count++;
        }
        System.out.println("Conta: " + count);
        bestTour.setTotalDistance(bestTour.computeTotalDistance());
        return bestTour;
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
        return Math.exp((currentDistance - neighbourDistance) / temperature);
    }

    private double randomCompare(){
        Random random = new Random(randomSeed);
        //return random double between 0.0 and 1.0
        return random.nextInt(1000) / 1000.0;
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

}
