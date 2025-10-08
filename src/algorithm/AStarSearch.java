package src.algorithm;

import src.algorithm.heuristic.Heuristic;
import src.model.SearchResult;

public class AStarSearch implements SearchAlgorithm {
    private final Heuristic heuristic;
    
    public AStarSearch(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
    
    @Override
    public SearchResult solve(int boardSize) {
        // VRAI A* avec f(n) = g(n) + h(n)
        return null; // Implémentation de A*
    }
}
