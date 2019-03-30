package parser;

import TSP.Result;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    private List<String> lines;
    private InputStream inputFile;
    private String fileName;

    public FileReader(){
        this.lines = new ArrayList<String>();
    }

    public FileReader(String fileName){
        this.fileName = fileName;
    }

    public List<String> readFile(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile));
            String line;
            while (!(line = bufferedReader.readLine()).equals("EOF")){
                lines.add(line);
//                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void setInputFile(InputStream inputFile) {
        this.inputFile = inputFile;
    }

    public List<String> getLines() {
        return lines;
    }

    public Result readFileResult() throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        Result result = new Result();

        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine())
            lines.add(sc.nextLine());

        result.setError(Double.parseDouble(lines.get(0)));
        result.setSeed(Long.parseLong(lines.get(1)));
        result.setTime(Long.parseLong(lines.get(2)));
        return result;
    }
}
