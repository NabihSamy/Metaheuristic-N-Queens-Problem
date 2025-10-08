package src.algorithm;

import src.algorithm.heuristic.Heuristic;
import src.model.Board;
import src.model.SearchNode;
import src.model.SearchResult;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearch implements SearchAlgorithm {
    private final Heuristic heuristic;
    
    public AStarSearch(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
    
    @Override
    public SearchResult solve(int boardSize) {
        long startTime = System.currentTimeMillis();
        int nodesExplored = 0;
        
        PriorityQueue<SearchNode> openSet = new PriorityQueue<>();
        Set<Board> closedSet = new HashSet<>();
        
        Board initialBoard = new Board(boardSize);
        SearchNode startNode = new SearchNode(initialBoard, 0, heuristic.evaluate(initialBoard), null);
        openSet.offer(startNode);
        
        while (!openSet.isEmpty()) {
            SearchNode current = openSet.poll();
            nodesExplored++;
            
            Board currentBoard = current.getBoard();
            
            if (currentBoard.isComplete()) {
                long endTime = System.currentTimeMillis();
                return new SearchResult(currentBoard, nodesExplored, endTime - startTime, true);
            }
            
            closedSet.add(currentBoard);
            
            int row = currentBoard.getDepth();
            if (row < boardSize) {
                for (int col = 0; col < boardSize; col++) {
                    if (currentBoard.isSafe(row, col)) {
                        Board newBoard = new Board(currentBoard);
                        newBoard.placeQueen(row, col);
                        
                        if (!closedSet.contains(newBoard)) {
                            int gCost = current.getGCost() + 1;
                            int hCost = heuristic.evaluate(newBoard);
                            SearchNode neighbor = new SearchNode(newBoard, gCost, hCost, current);
                            openSet.offer(neighbor);
                        }
                    }
                }
            }
        }
        
        long endTime = System.currentTimeMillis();
        return new SearchResult(null, nodesExplored, endTime - startTime, false);
    }
}
