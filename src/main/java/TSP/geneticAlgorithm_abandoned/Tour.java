package TSP.geneticAlgorithm_abandoned;

import TSP.City;
import TSP.TourManager;

import java.util.List;
import java.util.Random;

public class Tour {
    private TourManager tourManager;
    private Generation firstGeneration;
    private int [][] distanceMatrix;
    private Random random;
    private int genesSize;

    public Tour(TourManager tourManager){
        this.tourManager = tourManager;
        this.firstGeneration = new Generation(tourManager.getCities(), 10);
        this.distanceMatrix = tourManager.getDistanceMatrix();
        this.random = new Random();
        this.genesSize = tourManager.getCities().size();
    }

    public double computeFitness(int indexChromosomes){
        Chromosome chromosome = firstGeneration.getChromosomes().get(indexChromosomes);
        List<City> genes = chromosome.getGenes();
        int totalDistance = 0;
        for (int i=0; i<genesSize-1; i++){
            totalDistance += distanceMatrix[genes.get(i).getId()][genes.get(i+1).getId()];
        }
        return (double) (1 / totalDistance);
    }

    public void setRandomSeed(long seed){
        this.random.setSeed(seed);
    }

    public Chromosome crossover(int lenghtSegment){
        //choose the two parent
        int indexFirst = random.nextInt(genesSize);
        int indexSecond = random.nextInt(genesSize);
        Chromosome parent1 = firstGeneration.getChromosomes().get(indexFirst);
        Chromosome parent2 = firstGeneration.getChromosomes().get(indexSecond);
        Chromosome chromosomeChild = new Chromosome(genesSize);
        //index of the position inside the genes
        int posSegment = random.nextInt(genesSize) - (lenghtSegment - 1);
        int[] indicesSegment = new int[lenghtSegment];
        for (int i=0; i<lenghtSegment; i++){
            City c = parent1.getGenes().get(posSegment+i);
            //add the genes of the first parent into the same position of the future child
            chromosomeChild.insertGenes(c, (posSegment+i));
            indicesSegment[i] = c.getId();
        }
        //get the index of the second parents to start merging the genes
        int indexParent2, indexChild;
        if ((posSegment + lenghtSegment) == genesSize) {
            indexChild = 0;
            indexParent2 = 0;
        }
        else {
            indexParent2 = posSegment + lenghtSegment;
            indexChild = indexParent2;
        }
        boolean isPresent;
        for (int j=0; j<genesSize; j++){
            isPresent = false;
            City city = parent2.getGenes().get(indexParent2);
            //check if the city is alredy in the child chromosome
            for (int i=0; i<lenghtSegment; i++){
                if (city.getId() == indicesSegment[i]) {
                    indexParent2 = preventOutOfBoundParent(indexParent2);
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent){
                chromosomeChild.insertGenes(city, indexChild);
                indexParent2 = preventOutOfBoundParent(indexParent2);
                indexChild = preventOutOfBoundChild(indexChild, posSegment, lenghtSegment);
            }
        }
        return chromosomeChild;
    }


//    public void nextGeneration

    private int preventOutOfBoundParent(int index){
        if (index < genesSize - 1)
            index++;
        else
            index = 0;
        return index;
    }

    private int preventOutOfBoundChild(int index, int posSegment, int lenghtSegment){
        if (index < genesSize - 1)
            if (index < posSegment - 1 || index > posSegment + lenghtSegment)
                index++;
        else
            index = 0;
        return index;
    }


}
