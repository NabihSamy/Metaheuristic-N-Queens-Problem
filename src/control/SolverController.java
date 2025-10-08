package src.control;
import src.algorithm.*;
import src.algorithm.heuristic.*;
import src.model.SearchResult;

public class SolverController {
    public static final AlgorithmType DFS = null;
    public static final AlgorithmType BFS = null;
    public static final AlgorithmType ASTAR_H1 = null;
    public static final AlgorithmType ASTAR_H2 = null;
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

    public String getElapsedTimePretty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getElapsedTimePretty'");
    }

    public boolean hasResult() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasResult'");
    }

    public char[] getParcouru() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getParcouru'");
    }

    public char[] getCree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCree'");
    }

	public int[] getQueens() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getQueens'");
	}
}