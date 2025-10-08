package src.algorithm;

import src.model.Board;
import src.model.SearchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements SearchAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 10000;
    private static final double MUTATION_RATE = 0.1;
    private static final double CROSSOVER_RATE = 0.8;
    private static final int TOURNAMENT_SIZE = 5;
    private static final int ELITE_SIZE = 2;
    
    private Random random;
    private int nodesExplored;
    
    public GeneticAlgorithm() {
        this.random = new Random();
    }
    
    @Override
    public SearchResult solve(int boardSize) {
        long startTime = System.currentTimeMillis();
        nodesExplored = 0;
        
        // Initialiser la population
        List<Individual> population = initializePopulation(boardSize);
        
        Individual bestSolution = null;
        int generation = 0;
        
        while (generation < MAX_GENERATIONS) {
            generation++;
            nodesExplored += population.size();
            
            // Évaluer la population
            evaluatePopulation(population);
            
            // Trier par fitness (meilleur = fitness la plus basse)
            population.sort((a, b) -> Integer.compare(a.fitness, b.fitness));
            
            // Vérifier si on a trouvé une solution
            if (population.get(0).fitness == 0) {
                bestSolution = population.get(0);
                break;
            }
            
            // Créer nouvelle génération
            List<Individual> newPopulation = new ArrayList<>();
            
            // Élitisme : garder les meilleurs
            for (int i = 0; i < ELITE_SIZE && i < population.size(); i++) {
                newPopulation.add(new Individual(population.get(i)));
            }
            
            // Remplir le reste avec crossover et mutation
            while (newPopulation.size() < POPULATION_SIZE) {
                Individual parent1 = tournamentSelection(population);
                Individual parent2 = tournamentSelection(population);
                
                Individual child;
                if (random.nextDouble() < CROSSOVER_RATE) {
                    child = crossover(parent1, parent2);
                } else {
                    child = new Individual(parent1);
                }
                
                if (random.nextDouble() < MUTATION_RATE) {
                    mutate(child);
                }
                
                newPopulation.add(child);
            }
            
            population = newPopulation;
        }
        
        long endTime = System.currentTimeMillis();
        
        if (bestSolution != null && bestSolution.fitness == 0) {
            Board solutionBoard = createBoard(bestSolution, boardSize);
            return new SearchResult(solutionBoard, nodesExplored, endTime - startTime, true);
        }
        
        return new SearchResult(null, nodesExplored, endTime - startTime, false);
    }
    
    private List<Individual> initializePopulation(int boardSize) {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Individual(boardSize));
        }
        return population;
    }
    
    private void evaluatePopulation(List<Individual> population) {
        for (Individual individual : population) {
            individual.fitness = calculateFitness(individual);
        }
    }
    
    private int calculateFitness(Individual individual) {
        // Fitness = nombre de conflits (0 = solution parfaite)
        int conflicts = 0;
        int size = individual.genes.length;
        
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                // Conflit de colonne
                if (individual.genes[i] == individual.genes[j]) {
                    conflicts++;
                }
                // Conflit de diagonale
                if (Math.abs(individual.genes[i] - individual.genes[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }
    
    private Individual tournamentSelection(List<Individual> population) {
        Individual best = population.get(random.nextInt(population.size()));
        
        for (int i = 1; i < TOURNAMENT_SIZE; i++) {
            Individual candidate = population.get(random.nextInt(population.size()));
            if (candidate.fitness < best.fitness) {
                best = candidate;
            }
        }
        
        return best;
    }
    
    private Individual crossover(Individual parent1, Individual parent2) {
        int size = parent1.genes.length;
        Individual child = new Individual(size);
        
        // Crossover à un point
        int crossoverPoint = random.nextInt(size);
        
        for (int i = 0; i < size; i++) {
            if (i < crossoverPoint) {
                child.genes[i] = parent1.genes[i];
            } else {
                child.genes[i] = parent2.genes[i];
            }
        }
        
        return child;
    }
    
    private void mutate(Individual individual) {
        int size = individual.genes.length;
        
        // Mutation: changer la position d'une reine aléatoire
        int row = random.nextInt(size);
        individual.genes[row] = random.nextInt(size);
    }
    
    private Board createBoard(Individual individual, int boardSize) {
        Board board = new Board(boardSize);
        for (int row = 0; row < boardSize; row++) {
            board.placeQueen(row, individual.genes[row]);
        }
        return board;
    }
    
    // Classe interne pour représenter un individu
    private class Individual {
        int[] genes;  // genes[i] = colonne de la reine à la ligne i
        int fitness;
        
        Individual(int size) {
            this.genes = new int[size];
            // Initialisation aléatoire
            for (int i = 0; i < size; i++) {
                genes[i] = random.nextInt(size);
            }
            this.fitness = Integer.MAX_VALUE;
        }
        
        Individual(Individual other) {
            this.genes = other.genes.clone();
            this.fitness = other.fitness;
        }
    }
}