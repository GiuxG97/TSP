package application;

import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;
import TSP.simulatedAnnealing.NearestNeighbour;
import TSP.simulatedAnnealing.SimulatedAnnealing;
import parser.FileReader;
import parser.Parser;

import java.io.InputStream;
import java.util.List;

public class Main {

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

        TourManager tourManager = TourManager.getInstance(parser.getCities());
        tourManager.retriveDistance();
//        tourManager.printDistanceMatrix();

        NearestNeighbour nearestNeighbour = new NearestNeighbour(parser.getCities());
        Tour tourNearest = nearestNeighbour.computeAlgorithm();
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(Double.MAX_VALUE, 0.001);
        simulatedAnnealing.setRandomSeed(20);
        Tour tourSimulatedAnnealing = simulatedAnnealing.computeAlgorithm(tourNearest);
        timer.stopTimer();
        tourNearest.print();
        tourSimulatedAnnealing.print();
        System.out.println("Best distance: " + parser.getHeader().get(5));
        timer.printTimer();

    }

    private static InputStream chooseResource(String name){
        return Main.class.getClassLoader().getResourceAsStream(name);
    }
}
