package application;

import TSP.*;
import TSP.simulatedAnnealing.NearestNeighbour;
import TSP.simulatedAnnealing.SimulatedAnnealing;
import TSP.simulatedAnnealing.TwoOpt;
import parser.FileReader;
import parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainNuovaGui {
    private static List<City> cities;
    private static Tour tour;


    public static String getPlotCommand(String path) {
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
        InputStream inputFile;
        String[] filesName = {"eil76.tsp", "kroA100.tsp", "ch130.tsp", "d198.tsp", "lin318.tsp", "pr439.tsp", "pcb442.tsp", "rat783.tsp", "u1060.tsp", "fl1577.tsp"};
        long seed;
        String path = "/home/test/Dropbox/TSP/TSP_FinalResults/results1/";
        Result result;
        int count = 1;
        while (true) {
            for (int i = 0; i < filesName.length; i++) {
                inputFile = chooseResource(filesName[i]);
                seed = System.currentTimeMillis();
                try {
                    if (!alreadyBest(filesName[i])) {
                        result = computeAlgorithms(seed, inputFile);
                        if (checkResults((path+filesName[i]), result)) {
                            printFile(result, (path+filesName[i]));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("File " + filesName[i] + " analised " + count + " times");
            }
            count++;
        }

    }

    private static boolean alreadyBest(String fileName) throws FileNotFoundException {
        FileReader fileReader = new FileReader((fileName + ".txt"));
        Result oldResult = fileReader.readFileResult();
        if (oldResult.getError() == 0.0)
            return true;
        return false;
    }

    private static void printFile(Result result, String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new FileOutputStream(fileName + ".txt", false));
        writer.println(result.getError());
        writer.println(result.getSeed());
        writer.println(result.getTime());
        writer.close();
    }

    private static boolean checkResults(String fileName, Result result) throws FileNotFoundException {
        FileReader fileReader = new FileReader((fileName+".txt"));
        Result oldResult = fileReader.readFileResult();
        if (result.getError() < oldResult.getError()){
            System.err.println("File " + fileName + " improved from " + oldResult.getError() + " to " + result.getError() + " !");
            return true;
        }
        return false;

    }

    private static Result computeAlgorithms(long seed, InputStream inputFile) {
        Timer.startTimer();
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

        NearestNeighbour nearestNeighbour = new NearestNeighbour(seed);
        Tour tourNearest = nearestNeighbour.computeAlgorithm();

        TwoOpt twoOpt = new TwoOpt();
        Tour tourTwoOpt = twoOpt.computeAlgorithm(tourNearest);

//        //1000 e 0.99 vanno bene per fl1577, ma sforo di qualche secondo
//        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(1000, 0.99, seed);
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(seed, 0.97);
        Tour tourSimulatedAnnealing = simulatedAnnealing.computeAlgorithm(tourTwoOpt);
        tour = tourSimulatedAnnealing;

//        System.out.println("Nearest neighbour");
//        tourNearest.print();
//        System.out.println("Two-Opt after nearest neighbour");
//        tourTwoOpt.print();
//        System.out.println("Simulated annealing:");
//        tourSimulatedAnnealing.print();
//        System.out.println("Best distance: " + parser.getBestKnown());

        double error = (double) (tour.getTotalDistance() - parser.getBestKnown()) / (double) parser.getBestKnown();
//        System.out.println("Error: " + error * 100 + "%");

        return new Result(error, Timer.getElapsedTime(), seed);

    }

    private static void print() {
        FileWriter w;
        try {
            w = new FileWriter("nodes");


            BufferedWriter b = new BufferedWriter(w);

            List<City> tourCities = new ArrayList<>();
            for (int i = 0; i < tour.size(); i++)
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
