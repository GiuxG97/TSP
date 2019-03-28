package parser;

import TSP.City;
import TSP.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    //coordinates containes all the cities' coordinates, where the name is the index + 1
    private List<City> cities;
    private List<String> fileLines;
    private int index;
    private String fileName;
    private String fileType;
    private String fileComment;
    private String edgeWeightType;
    private int bestKnown;
    private int dimension;

    public Parser(List<String> lines){
        this.index = 0;
        this.cities = new ArrayList<>();
        this.fileLines = lines;
    }

    public void readCities(){
        int linesSize = fileLines.size();
        //index is now the first city
        index++;
        int id = 0;
        for (int i=index; i<linesSize; i++){
            obtainCities(fileLines.get(i), id);
            id++;
        }
    }

    public void readHeader(){
        fileName = fileLines.get(0);
        fileComment = fileLines.get(1).split(": ")[1];
        fileType = fileLines.get(2).split(": ")[1];
        dimension = Integer.parseInt(fileLines.get(3).split(": ")[1]);
        edgeWeightType = fileLines.get(4).split(": ")[1];
        bestKnown = Integer.parseInt(fileLines.get(5).split(": ")[1]);
        index = 6;
//
//        while(!(line = fileLines.get(index)).equals("NODE_COORD_SECTION")){
//            header.add(line);
//            index++;
//        }
    }

    public List<City> getCities() {
        return cities;
    }

    public void setFileLines(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    private void obtainCities(String line, int id){
        String[] splitted = line.split(" ");
        double x, y;
        if (splitted[0].equals("")){
            x = Double.parseDouble(splitted[2]);
            y = Double.parseDouble(splitted[3]);
        }
        else {
            x = Double.parseDouble(splitted[1]);
            y = Double.parseDouble(splitted[2]);
        }
        cities.add(new City(id, new Coordinate(x, y)));
    }

    public void printCities(){
        int count = 0;
        for (City c: cities){
            System.out.println(++count + " --> " + c.getCoordinate().getX() + " " + c.getCoordinate().getY());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileComment() {
        return fileComment;
    }

    public String getEdgeWeightType() {
        return edgeWeightType;
    }

    public int getBestKnown() {
        return bestKnown;
    }

    public int getDimension() {
        return dimension;
    }
}
