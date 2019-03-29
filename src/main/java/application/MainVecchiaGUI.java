package application;

import TSP.City;
import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;
import TSP.simulatedAnnealing.NearestNeighbour;
import TSP.simulatedAnnealing.SimulatedAnnealing;
import TSP.simulatedAnnealing.TwoOpt;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import parser.FileReader;
import parser.Parser;

import java.io.InputStream;
import java.util.List;

public class MainVecchiaGUI extends Application {

    private static List<City> cities;
    private static Tour tour;

    public void start(Stage stage) {
        //Creating a line object
        Line line = new Line();

        //Creating a Group
        Group root = new Group(line);

        //Setting the properties to a line
        List<Integer> indexCities = tour.getIndexCities();
        int size = indexCities.size();
        for (int i = 0; i < size - 1; i++) {
            if(i==0) {
                root.getChildren().add(new Circle(cities.get(indexCities.get(i)).getCoordinate().getX(), cities.get(indexCities.get(i)).getCoordinate().getY(), 5));
                System.out.println("\n\n"+indexCities.get(i));
            }
                root.getChildren().add(new Circle(cities.get(indexCities.get(i)).getCoordinate().getX(), cities.get(indexCities.get(i)).getCoordinate().getY(), 3));
            line = new Line(cities.get(indexCities.get(i)).getCoordinate().getX(), cities.get(indexCities.get(i)).getCoordinate().getY(),
                    cities.get(indexCities.get(i + 1)).getCoordinate().getX(), cities.get(indexCities.get(i + 1)).getCoordinate().getY());
            root.getChildren().add(line);
        }

        line = new Line(cities.get(indexCities.get(0)).getCoordinate().getX(), cities.get(indexCities.get(0)).getCoordinate().getY(),
                cities.get(indexCities.get(cities.size()-1)).getCoordinate().getX(), cities.get(indexCities.get(cities.size()-1)).getCoordinate().getY());
        root.getChildren().add(line);

        //Creating a Scene
        Scene scene = new Scene(new ScrollPane(root), 1000, 600);

        //Setting title to the scene
        stage.setTitle("Graph");

        //Adding the scene to the stage
        stage.setScene(scene);

        //Displaying the contents of a scene
        stage.show();
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.startTimer();
        FileReader fileReader = new FileReader();
        InputStream inputFile = chooseResource("kroA100.tsp");
        fileReader.setInputFile(inputFile);
        //read and save the lines in a list
        List<String> lines = fileReader.readFile();
        Parser parser = new Parser(lines);
        parser.readHeader();
        parser.readCities();
//        parser.printCities();
        cities = parser.getCities();


        TourManager tourManager = TourManager.getInstance(cities);
        tourManager.retriveDistance();
//        tourManager.printDistanceMatrix();

        NearestNeighbour nearestNeighbour = new NearestNeighbour();
        Tour tourNearest = nearestNeighbour.computeAlgorithm();

        TwoOpt twoOpt = new TwoOpt();
        Tour tourTwoOpt = twoOpt.computeAlgorithm(tourNearest);


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

        launch(args);

    }

    private static InputStream chooseResource(String name) {
        return MainVecchiaGUI.class.getClassLoader().getResourceAsStream(name);
    }
}
