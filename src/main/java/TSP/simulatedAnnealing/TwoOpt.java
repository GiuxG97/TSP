package TSP.simulatedAnnealing;

import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;

public class TwoOpt {

    private Tour tour;

    public TwoOpt() {
    }

    public Tour computeAlgorithm(Tour initialTour) {
        tour = new Tour(initialTour);
        int size = tour.size();
        int bestGain = -1;
        int gain;
        int bestI = 0, bestJ = 0;
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        boolean isImproved = true;
        while (isImproved && Timer.getElapsedTime() < Timer.ENDTIME){
            isImproved = false;
            for (int i=1; i < size; i++){
                for (int j=i+1; j < size-1; j++){
                    if (checkDistance(i, j, distanceMatrix)){
                        tour = swapArcs(i, j, size);
                        isImproved = true;

                    }
                }
            }
        }

//
//        while (bestGain < 0 && Timer.getElapsedTime() < Timer.ENDTIME) {
//            bestGain = Integer.MAX_VALUE;
//            for (int i = 0; i < size; i++) {
//                for (int j = i+1; j < size; j++) {
//                    gain = computeDistance(i, j, distanceMatrix);
//                    if (gain < bestGain) {
//                        bestGain = gain;
//                        bestI = i;
//                        bestJ = j;
//                    }
//                }
//            }
//            if (bestGain < 0)
//                tour = swapArcs(bestI, bestJ, size);
//
//        }
        tour.setTotalDistance(tour.computeTotalDistance());
        return tour;
    }

    private boolean checkDistance(int index1, int index2, int [][] distanceMatrix){
        return ((distanceMatrix[tour.get(index1-1)][tour.get(index1)] + distanceMatrix[tour.get(index2)][tour.get(index2+1)]) >
                (distanceMatrix[tour.get(index1-1)][tour.get(index2)] + distanceMatrix[tour.get(index1)][tour.get(index2+1)]));
    }

    private Tour swapArcs(int index1, int index2, int size){
        Tour newTour = new Tour();
        for (int i=0; i<index1; i++)
            newTour.addIndexCities(tour.get(i));
        int var = 0;
        for (int i=index1; i<=index2; i++){
            newTour.addIndexCities(tour.get(index2-var));
            var++;
        }
        for (int i=index2+1; i<size; i++)
            newTour.addIndexCities(tour.get(i));

        return newTour;

    }

//    private int computeDistance(int index1, int index2, int[][] distanceMatrix) {
//        int nextI1, nextI2;
//        if (index1 == distanceMatrix.length)
//            nextI1 = 0;
//        else
//            nextI1 = index1+1;
//        if (index2 == distanceMatrix.length)
//            nextI2 = 0;
//        else
//            nextI2 = index2+1;
//
//        return (distanceMatrix[tour.get(index1)][tour.get(index2)] + distanceMatrix[tour.get(nextI1)][tour.get(nextI2)]) -
//                (distanceMatrix[tour.get(index1)][tour.get(nextI1)] + distanceMatrix[tour.get(index2)][tour.get(nextI2)]);
//    }
//
//    private Tour swapArcs(int index1, int index2, int size) {
//        Tour newTour = new Tour();
//        int distance = 0;
//        for (int i = 0; i <= index1; i++)
//            newTour.addIndexCities(tour.get(i));
//
//        for (int i = index2; i > index1; i--)
//            newTour.addIndexCities(tour.get(i));
//
//        for (int i = index2 + 1; i < size; i++)
//            newTour.addIndexCities(tour.get(i));
//
//        newTour.setTotalDistance(distance);
//        return newTour;
//    }

}
