package parser;

import TSP.City;
import TSP.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    //coordinates containes all the cities' coordinates, where the name is the index + 1
    private List<City> cities;
    private List<String> fileLines;
    private List<String> header;
    private int index;
    /*private String fileName;
    private String fileType;
    private String fileComment;
    private String edgeWeightType;
    private int bestKnown;
    private int dimension;*/

    public Parser(List<String> lines){
        this.index = 0;
        this.cities = new ArrayList<City>();
        this.header = new ArrayList<String>();
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
        String line;
        while(!(line = fileLines.get(index)).equals("NODE_COORD_SECTION")){
            header.add(line);
            index++;
        }
    }

    public List<City> getCities() {
        return cities;
    }

    public void setFileLines(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    public List<String> getHeader() {
        return header;
    }

    private void obtainCities(String line, int id){
        String[] splitted = line.split(" ");
        double x = Double.parseDouble(splitted[1]);
        double y = Double.parseDouble(splitted[2]);
        cities.add(new City(id, new Coordinate(x, y)));
    }
}
