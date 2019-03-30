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

import java.io.*;
import java.net.URL;
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
        double error;
        double minError;
        int minSeed;
        List<Double> minErrors = new ArrayList<>();
        List<Integer> minSeeds = new ArrayList<>();
        InputStream inputFile;
        String[] filesName = {"eil76.tsp", "kroA100.tsp", "ch130.tsp", "d198.tsp", "lin318.tsp", "pr439.tsp", "pcb442.tsp", "rat783.tsp", "u1060.tsp", "fl1577.tsp"};
        for (String name : filesName) {
            minError = Double.MAX_VALUE;
            minSeed = 0;
//            System.err.println(f.getName());
            for (int i = 0; i < 1; i++) {
                inputFile = chooseResource(name);
                error = computeAlgorithms(System.currentTimeMillis(), inputFile);
                if (error < minError) {
                    minError = error;
                    minSeed = i;
                }
            }
            minErrors.add(minError);
            minSeeds.add(minSeed);
        }

        print();


    }


    private static double computeAlgorithms(long seed, InputStream inputFile){
        Timer timer = new Timer();
        timer.startTimer();
        FileReader fileReader = new FileReader();
        fileReader.setInputFile(inputFile);
        //read and save the lines in a list
        List<String> lines = fileReader.readFile();
        Parser parser = new Parser(lines);
        parser.readHeader();
        parser.readCities();
        cities = parser.getCities();

        TourManager tourManager = TourManager.getInstance(cities);
        tourManager.retriveDistance();

        NearestNeighbour nearestNeighbour = new NearestNeighbour();
        nearestNeighbour.setRandomSeed(seed);
        Tour tourNearest = nearestNeighbour.computeAlgorithm();

        TwoOpt twoOpt = new TwoOpt();
        Tour tourTwoOpt = twoOpt.computeAlgorithm(tourNearest);

//        //1000 e 0.99 vanno bene per fl1577, ma sforo di qualche secondo
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(1000, 0.99);
        simulatedAnnealing.setRandomSeed(seed);
        Tour tourSimulatedAnnealing = simulatedAnnealing.computeAlgorithm(tourTwoOpt);
        tour = tourSimulatedAnnealing;

        timer.stopTimer();
        System.out.println("Nearest neighbour");
        tourNearest.print();
        System.out.println("Two-Opt after nearest neighbour");
        tourTwoOpt.print();
        System.out.println("Simulated annealing:");
        tourSimulatedAnnealing.print();
        System.out.println("Best distance: " + parser.getBestKnown());

        double error = (double) (tour.getTotalDistance() - parser.getBestKnown()) / (double)parser.getBestKnown();
        System.out.println("Error: " + error*100 + "%");

        return error;

    }

    private static void print() {
        FileWriter w;
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
        return MainNuovaGui.class.getClassLoader().getResourceAsStream(name);
    }
}
