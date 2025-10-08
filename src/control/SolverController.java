package src.control;
import src.algorithm.*;
import src.algorithm.heuristic.*;
import src.model.SearchResult;

public class SolverController {
    public static final int DFS = 0;
    public static final int BFS = 0;
    public static final int ASTAR_H1 = 0;
    public static final int ASTAR_H2 = 0;
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