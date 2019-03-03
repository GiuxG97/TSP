package parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    private List<String> lines;
    private InputStream inputFile;

    public FileReader(){
        this.lines = new ArrayList<String>();
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
}
