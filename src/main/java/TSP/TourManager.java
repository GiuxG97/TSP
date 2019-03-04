package TSP;

import java.util.List;

public class TourManager {
    private static List<City> cities;
    private static int[][] distanceMatrix;
    private static TourManager tourManager;
    private static int numberCities;

    public static TourManager getInstance(List<City> cities){
        if (tourManager == null){
            tourManager = new TourManager(cities);
        }
        return tourManager;
    }

    private TourManager(List<City> cities){
        TourManager.cities = cities;
        numberCities = cities.size();
        distanceMatrix = new int[numberCities][numberCities];
    }

    public void retriveDistance(){
        int distance = 0;
        Coordinate city, distCity;
        for (int i=0; i<numberCities; i++){
            for (int j=i; j<numberCities; j++){
                if (i != j){
                    city = cities.get(i).getCoordinate();
                    distCity = cities.get(j).getCoordinate();
                    distance = (int) (Math.sqrt(Math.pow(city.getX() - distCity.getX(), 2) +
                            Math.pow(city.getY() - distCity.getY(), 2))+0.5);
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
                System.out.print(distanceMatrix[i][j] + "\t");
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
        return numberCities;
    }
}
