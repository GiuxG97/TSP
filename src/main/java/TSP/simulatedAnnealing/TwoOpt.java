package TSP.simulatedAnnealing;

import TSP.Tour;
import TSP.TourManager;

public class TwoOpt {

    private Tour tour;

    public TwoOpt(Tour initialTour) {
        this.tour = initialTour;
    }

    public void setInitialTour(Tour initialTour) {
        this.tour = initialTour;
    }

    public Tour computeAlgorithm() {
        int size = tour.size();
        int bestGain = -1;
        int gain;
        int bestI = 0, bestJ = 0;
        int[][] distanceMatrix = TourManager.getDistanceMatrix();
        while (bestGain < 0) {
            bestGain = Integer.MAX_VALUE;
            for (int i = 0; i < size-1; i++) {
                for (int j = 0; j < size-1; j++) {
                    gain = computeDistance(tour, i, j, distanceMatrix);
                    if (gain < bestGain && i != j) {
                        bestGain = gain;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
            if (bestGain < 0)
                tour = swapArcs(bestI, bestJ, size);
//            System.out.println(bestGain);

        }

//
//        // repeat until no improvement is made
//        boolean isImproved = true;
//        while (isImproved) {
//            isImproved = false;
//            for (int i = 1; i < size - 1; i++) {
//                for (int k = i + 1; k < size - 1; k++) {
//                    if (checkDistance(tour, i, k)) {
//                        isImproved = true;
//                        swapSegment(i, k, size);
////                        for (int j=0; i<size; i++)
////                            tour.addIndexCities(newTour.get(i));
//                        tour = new Tour(newTour.getIndexCities());
//                    }
//                }
//            }
//        }
        tour.setTotalDistance(tour.computeTotalDistance());
        return tour;
    }

    private int computeDistance(Tour tour, int index1, int index2, int[][] distanceMatrix) {
        return (distanceMatrix[tour.get(index1)][tour.get(index2)] + distanceMatrix[tour.get(index1 + 1)][tour.get(index2 + 1)]) -
                (distanceMatrix[tour.get(index1)][tour.get(index1 + 1)] + distanceMatrix[tour.get(index2)][tour.get(index2 + 1)]);
    }

    private Tour swapArcs(int index1, int index2, int size) {
        Tour newTour = new Tour();
        for (int i=0; i<=index1; i++)
            newTour.addIndexCities(tour.get(i));

        for (int i=index2; i>index1; i--)
            newTour.addIndexCities(tour.get(i));

        for (int i=index2+1; i<size; i++)
            newTour.addIndexCities(tour.get(i));

        return newTour;
    }


//    private boolean checkDistance(Tour tour, int index1, int index2) {
//        int[][] distanceMatrix = TourManager.getDistanceMatrix();
//        return (distanceMatrix[tour.get(index1 - 1)][tour.get(index1)] + distanceMatrix[tour.get(index2)][tour.get(index2 + 1)]) >
//                (distanceMatrix[tour.get(index1 - 1)][tour.get(index2)] + distanceMatrix[tour.get(index1)][tour.get(index2 + 1)]);
//    }
//
//    private void swapSegment(int i, int k, int size) {
//
//        // 1. take route[0] to route[i-1] and add them in order to new_route
//        for (int c = 0; c <= i - 1; c++) {
//            newTour.setIndexCities(c, tour.get(c));
//        }
//
//        // 2. take route[i] to route[k] and add them in reverse order to new_route
//        int dec = 1;
//        for (int c = i; c <= k; c++) {
//            newTour.setIndexCities(c, tour.get(k - dec));
//            dec++;
//        }
//
//        // 3. take route[k+1] to end and add them in order to new_route
//        for (int c = k + 1; c < size; c++) {
//            newTour.setIndexCities(c, tour.get(c));
//        }
//
//    }
}
