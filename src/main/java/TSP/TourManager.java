package TSP;

import java.util.List;

public class TourManager {
    private static List<City> cities;
    private static int[][] distanceMatrix;
    private static TourManager tourManager;

    public static TourManager getInstance(List<City> cities){
        if (tourManager == null){
            tourManager = new TourManager(cities);
        }
        return tourManager;
    }

    private TourManager(List<City> cities){
        TourManager.cities = cities;
        distanceMatrix = new int[cities.size()][cities.size()];
    }

    public void retriveDistance(){
        int distance = 0;
        int citiesSize = cities.size();
        Coordinate city, distCity;
        for (int i=0; i<citiesSize; i++){
            for (int j=i; j<citiesSize; j++){
                if (i != j){
                    city = cities.get(i).getCoordinate();
                    distCity = cities.get(j).getCoordinate();
                    distance = (int) Math.sqrt(Math.pow(city.getX() - distCity.getX(), 2) +
                            Math.pow(city.getY() - distCity.getY(), 2));
                    //the matrix is specular and the diagonal is the simmetrical axis
                    distanceMatrix[i][j] = distance;
                    distanceMatrix[j][i] = distance;
                }
                else {
                    distanceMatrix[i][j] = 0;
                }
            }
        }
    }

    public void printDistanceMatrix(){
        int size = distanceMatrix.length;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                System.out.print(distanceMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<City> getCities() {
        return cities;
    }

    public static int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public static int numberOfCities(){
        return cities.size();
    }
}
