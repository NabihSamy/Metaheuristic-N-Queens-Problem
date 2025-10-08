package src.control;

import src.algorithm.*;
import src.algorithm.heuristic.*;
import src.model.SearchResult;
import src.view.QueensGUI;
import javax.swing.SwingWorker;

public class SolverController {
    private QueensGUI view;
    
    public SolverController(QueensGUI view) {
        this.view = view;
    }
    
    public void solveDFS(int boardSize) {
        solve(new DepthFirstSearch(), boardSize, "DFS");
    }
    
    public void solveBFS(int boardSize) {
        solve(new BreadthFirstSearch(), boardSize, "BFS");
    }
    
    public void solveAStarConflict(int boardSize) {
        solve(new AStarSearch(new ConflictHeuristic()), boardSize, "A* (Conflits)");
    }
    
    public void solveAStarDistance(int boardSize) {
        solve(new AStarSearch(new DistanceHeuristic()), boardSize, "A* (Distance)");
    }
    
    private void solve(SearchAlgorithm algorithm, int boardSize, String algorithmName) {
        SwingWorker<SearchResult, Void> worker = new SwingWorker<>() {
            @Override
            protected SearchResult doInBackground() {
                view.setStatus("Recherche en cours avec " + algorithmName + "...");
                return algorithm.solve(boardSize);
            }
            
            @Override
            protected void done() {
                try {
                    SearchResult result = get();
                    if (result.isSuccess()) {
                        view.displaySolution(result);
                        view.setStatus(String.format(
                            "%s - Solution trouvée! Nœuds: %d, Temps: %d ms",
                            algorithmName,
                            result.getNodesExplored(),
                            result.getTimeMillis()
                        ));
                    } else {
                        view.setStatus(algorithmName + " - Aucune solution trouvée");
                    }
                } catch (Exception e) {
                    view.setStatus("Erreur: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        
        worker.execute();
    }
}
