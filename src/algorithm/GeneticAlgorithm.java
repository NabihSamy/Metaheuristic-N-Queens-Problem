package src.algorithm;

import src.model.Board;
import src.model.SearchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class GeneticAlgorithm implements SearchAlgorithm {
    private static final int POPULATION_SIZE = 200;
    private static final int MAX_GENERATIONS = 50000;
    private static final double MUTATION_RATE = 0.15;
    private static final double CROSSOVER_RATE = 0.85;
    private static final int TOURNAMENT_SIZE = 5;
    private static final int ELITE_SIZE = 5;
    
    private Random random;
    private int nodesExplored;
    
    public GeneticAlgorithm() {
        this.random = new Random();
    }
    
    @Override
    public SearchResult solve(int boardSize) {
        long startTime = System.currentTimeMillis();
        nodesExplored = 0;
        
        // Initialiser la population avec permutations
        List<Individual> population = initializePopulation(boardSize);
        
        Individual bestSolution = null;
        int generation = 0;
        int stagnationCounter = 0;
        int bestFitness = Integer.MAX_VALUE;
        
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
            
            // Détecter la stagnation
            if (population.get(0).fitness < bestFitness) {
                bestFitness = population.get(0).fitness;
                stagnationCounter = 0;
            } else {
                stagnationCounter++;
            }
            
            // Réinitialisation partielle si stagnation
            if (stagnationCounter > 1000) {
                for (int i = ELITE_SIZE; i < population.size() / 2; i++) {
                    population.set(i, new Individual(boardSize));
                }
                stagnationCounter = 0;
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
                    child = pmxCrossover(parent1, parent2);
                } else {
                    child = new Individual(parent1);
                }
                
                if (random.nextDouble() < MUTATION_RATE) {
                    swapMutation(child);
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
        
        // Si pas de solution parfaite, retourner la meilleure trouvée
        if (bestSolution == null && !population.isEmpty()) {
            bestSolution = population.get(0);
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
        // Fitness = nombre de paires de reines en conflit
        int conflicts = 0;
        int size = individual.genes.length;
        
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                // Conflit de diagonale (pas de conflit de colonne avec permutation)
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
    
    // PMX Crossover (Partially Mapped Crossover) - maintient la permutation
    private Individual pmxCrossover(Individual parent1, Individual parent2) {
        int size = parent1.genes.length;
        Individual child = new Individual(size);
        
        // Sélectionner deux points de crossover
        int point1 = random.nextInt(size);
        int point2 = random.nextInt(size);
        
        if (point1 > point2) {
            int temp = point1;
            point1 = point2;
            point2 = temp;
        }
        
        // Initialiser avec -1
        for (int i = 0; i < size; i++) {
            child.genes[i] = -1;
        }
        
        // Copier le segment du parent1
        for (int i = point1; i <= point2; i++) {
            child.genes[i] = parent1.genes[i];
        }
        
        // Remplir le reste avec parent2 en évitant les doublons
        for (int i = 0; i < size; i++) {
            if (child.genes[i] == -1) {
                int value = parent2.genes[i];
                
                // Chercher une position valide
                while (contains(child.genes, value)) {
                    int idx = indexOf(parent2.genes, value);
                    value = parent1.genes[idx];
                }
                
                child.genes[i] = value;
            }
        }
        
        return child;
    }
    
    // Swap Mutation - échange deux positions
    private void swapMutation(Individual individual) {
        int size = individual.genes.length;
        int pos1 = random.nextInt(size);
        int pos2 = random.nextInt(size);
        
        int temp = individual.genes[pos1];
        individual.genes[pos1] = individual.genes[pos2];
        individual.genes[pos2] = temp;
    }
    
    private boolean contains(int[] array, int value) {
        for (int v : array) {
            if (v == value) return true;
        }
        return false;
    }
    
    private int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        return -1;
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
        int[] genes;  // Permutation: genes[i] = colonne de la reine à la ligne i
        int fitness;
        
        Individual(int size) {
            this.genes = new int[size];
            
            // Initialisation avec une permutation aléatoire
            List<Integer> columns = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                columns.add(i);
            }
            Collections.shuffle(columns, random);
            
            for (int i = 0; i < size; i++) {
                genes[i] = columns.get(i);
            }
            
            this.fitness = Integer.MAX_VALUE;
        }
        
        Individual(Individual other) {
            this.genes = other.genes.clone();
            this.fitness = other.fitness;
        }
    }
}