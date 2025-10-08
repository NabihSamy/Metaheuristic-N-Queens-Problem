package src.algorithm;

import src.model.Board;
import src.model.SearchResult;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch implements SearchAlgorithm {
    
    @Override
    public SearchResult solve(int boardSize) {
        long startTime = System.currentTimeMillis();
        int nodesExplored = 0;
        
        Queue<Board> queue = new LinkedList<>();
        Board initialBoard = new Board(boardSize);
        queue.offer(initialBoard);
        
        while (!queue.isEmpty()) {
            Board current = queue.poll();
            nodesExplored++;
            
            if (current.isComplete()) {
                long endTime = System.currentTimeMillis();
                return new SearchResult(current, nodesExplored, endTime - startTime, true);
            }
            
            int row = current.getDepth();
            if (row < boardSize) {
                for (int col = 0; col < boardSize; col++) {
                    if (current.isSafe(row, col)) {
                        Board newBoard = new Board(current);
                        newBoard.placeQueen(row, col);
                        queue.offer(newBoard);
                    }
                }
            }
        }
        
        long endTime = System.currentTimeMillis();
        return new SearchResult(null, nodesExplored, endTime - startTime, false);
    }
}
