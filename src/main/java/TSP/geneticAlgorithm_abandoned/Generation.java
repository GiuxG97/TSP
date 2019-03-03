package TSP.geneticAlgorithm_abandoned;

import TSP.City;

import java.util.ArrayList;
import java.util.List;

public class Generation {
    private List<Chromosome> chromosomes;

    public Generation(List<City> cities, int numberChromosomes){
        this.chromosomes = new ArrayList<Chromosome>();
        for (int i=0; i<numberChromosomes; i++){
            chromosomes.add(new Chromosome(cities, i));
        }
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }
}
