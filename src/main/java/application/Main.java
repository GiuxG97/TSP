package application;

import TSP.City;
import TSP.Timer;
import TSP.Tour;
import TSP.TourManager;
import TSP.simulatedAnnealing.NearestNeighbour;
import TSP.simulatedAnnealing.SimulatedAnnealing;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import parser.FileReader;
import parser.Parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

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
            line = new Line(cities.get(indexCities.get(i)).getCoordinate().getX(), cities.get(indexCities.get(i)).getCoordinate().getY(),
                    cities.get(indexCities.get(i + 1)).getCoordinate().getX(), cities.get(indexCities.get(i + 1)).getCoordinate().getY());
            root.getChildren().add(line);
        }

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
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(10000, 0.001);
        simulatedAnnealing.setRandomSeed(20);
        Tour tourSimulatedAnnealing = simulatedAnnealing.computeAlgorithm(tourNearest);
        timer.stopTimer();
        tourNearest.print();
        tourSimulatedAnnealing.print();
        System.out.println("Best distance: " + parser.getHeader().get(5));
        timer.printTimer();

        cities = parser.getCities();
//        tour = tourNearest;
        tour = tourSimulatedAnnealing;
        launch(args);

    }

    private static InputStream chooseResource(String name) {
        return Main.class.getClassLoader().getResourceAsStream(name);
    }
}
