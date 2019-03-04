//package TSP.simulatedAnnealing;
//
//import TSP.Tour;
//
//public class TwoOpt {
//
//    private Tour tour;
//
//    public void computeAlgorithm() {
//        // Get tour size
//        int size = _tour.TourSize();
//
//        //CHECK THIS!!
//        for (int i=0;i<size;i++)
//        {
//            newTour.SetCity(i, _tour.GetCity(i));
//        }
//
//        // repeat until no improvement is made
//        int improve = 0;
//        int iteration = 0;
//
//        while ( improve < 800 )
//        {
//            double best_distance = _tour.TourDistance();
//
//            for ( int i = 1; i < size - 1; i++ )
//            {
//                for ( int k = i + 1; k < size; k++)
//                {
//                    TwoOptSwap( i, k );
//                    iteration++;
//                    double new_distance = _newTour.TourDistance();
//
//                    if ( new_distance < best_distance )
//                    {
//                        // Improvement found so reset
//                        improve = 0;
//
//                        for (int j=0;j<size;j++)
//                        {
//                            _tour.SetCity(j, _newTour.GetCity(j));
//                        }
//
//                        best_distance = new_distance;
//
//                        // Update the display
//                        NotifyTourUpdate(_tour, Double.toString(best_distance), Integer.toString(iteration));
//                    }
//                }
//            }
//
//            improve ++;
//        }
//    }
//
//    void TwoOptSwap( int i, int k )
//    {
//        int size = _tour.TourSize();
//
//        // 1. take route[0] to route[i-1] and add them in order to new_route
//        for ( int c = 0; c <= i - 1; ++c )
//        {
//            _newTour.SetCity( c, _tour.GetCity( c ) );
//        }
//
//        // 2. take route[i] to route[k] and add them in reverse order to new_route
//        int dec = 0;
//        for ( int c = i; c <= k; ++c )
//        {
//            _newTour.SetCity( c, _tour.GetCity( k - dec ) );
//            dec++;
//        }
//
//        // 3. take route[k+1] to end and add them in order to new_route
//        for ( int c = k + 1; c < size; ++c )
//        {
//            _newTour.SetCity( c, _tour.GetCity( c ) );
//        }
//    }
//}
