package application;

import TSP.City;
import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;
import TSP.simulatedAnnealing.NearestNeighbour;
import TSP.simulatedAnnealing.SimulatedAnnealing;
import TSP.simulatedAnnealing.TwoOpt;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import parser.FileReader;
import parser.Parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainNuovaGui {
    private static List<City> cities;
    private static Tour tour;


    public static String getPlotCommand(String path){
        return String.format("plot \"%1$s\" using 1:2 " +
                "with points pointtype 5, " +
                "\"%1$s\" using 1:2 with l t \"S1\"", path);
    }

    public static void plot(String path) throws IOException {
        Process p = Runtime.getRuntime().exec(
                new String[]{
                        "gnuplot",
                        "-p",
                        "-e",
                        getPlotCommand(path)
                }
        );
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.startTimer();
        FileReader fileReader = new FileReader();
        InputStream inputFile = chooseResource("fl1577.tsp");
        fileReader.setInputFile(inputFile);
        //read and save the lines in a list
        List<String> lines = fileReader.readFile();
        Parser parser = new Parser(lines);
        parser.readHeader();
        parser.readCities();
        cities = parser.getCities();


        TourManager tourManager = TourManager.getInstance(cities);
        tourManager.retriveDistance();

        NearestNeighbour nearestNeighbour = new NearestNeighbour(cities);
        Tour tourNearest = nearestNeighbour.computeAlgorithm();

        TwoOpt twoOpt = new TwoOpt(tourNearest);
        Tour tourTwoOpt = twoOpt.computeAlgorithm();


        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(1000, 0.01);
        simulatedAnnealing.setRandomSeed(20);
        Tour tourSimulatedAnnealing = simulatedAnnealing.computeAlgorithm(tourTwoOpt);
        timer.stopTimer();
        System.out.println("Nearest neighbour");
        tourNearest.print();
        System.out.println("Two-Opt after nearest neighbour");
        tourTwoOpt.print();
        System.out.println("Simulated annealing:");
        tourSimulatedAnnealing.print();
        System.out.println("Best distance: " + parser.getBestKnown());
        timer.printTimer();

//        tour = tourNearest;
//        tour = tourTwoOpt;
        tour = tourSimulatedAnnealing;

        double error = (double) (tour.getTotalDistance() - parser.getBestKnown()) / (double)parser.getBestKnown();
        System.out.println("Error: " + error*100 + "%");

        print();

    }

    private static void print() {
        FileWriter w = null;
        try {
            w = new FileWriter("nodes");


            BufferedWriter b = new BufferedWriter(w);

            List<City> tourCities = new ArrayList<>();
            for (int i=0; i<tour.size(); i++)
                tourCities.add(cities.get(tour.get(i)));

            for (City city : tourCities)
                b.write(city.getCoordinate().getX() + "\t" + city.getCoordinate().getY() + "\n");

            b.write(tourCities.get(0).getCoordinate().getX() + "\t" + tourCities.get(0).getCoordinate().getY() + "\n");

            b.flush();

            plot("nodes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InputStream chooseResource(String name) {
        return MainVecchiaGUI.class.getClassLoader().getResourceAsStream(name);
    }
}
