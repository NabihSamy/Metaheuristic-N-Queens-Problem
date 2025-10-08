package src.control;
import src.algorithm.*;
import src.algorithm.heuristic.*;
import src.model.SearchResult;

public class SolverController {
    private SearchAlgorithm algorithm;
    private SearchResult lastResult;
    
    public void solve(int boardSize, AlgorithmType type) {
        // Factory pattern
        algorithm = createAlgorithm(type);
        
        long startTime = System.currentTimeMillis();
        lastResult = algorithm.solve(boardSize);
        lastResult.setElapsedTime(System.currentTimeMillis() - startTime);
    }
    
    private SearchAlgorithm createAlgorithm(AlgorithmType type) {
        switch(type) {
            case DFS: return new DepthFirstSearch();
            case BFS: return new BreadthFirstSearch();
            case ASTAR_CONFLICTS: return new AStarSearch(new ConflictHeuristic());
            case ASTAR_DISTANCE: return new AStarSearch(new DistanceHeuristic());
            default: throw new IllegalArgumentException();
        }
    }
}