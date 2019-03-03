package TSP.geneticAlgorithm_abandoned;

import TSP.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chromosome {
    private List<City> genes;

    public Chromosome(int size){
        this.genes = new ArrayList<City>(size);
        for (int i=0; i<size; i++){
            this.genes.add(null);
        }
    }

    public Chromosome(List<City> cities, int index){
        this.genes = cities;
        //if it is the first i can use the ordinated list of cities
        if (index != 0)
            Collections.shuffle(cities);
    }

    public List<City> getGenes() {
        return genes;
    }

    public void insertGenes(City gene, int index){
        this.genes.set(index, gene);
    }
}
