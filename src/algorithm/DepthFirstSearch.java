package src.algorithm;

import src.model.Board;
import src.model.SearchResult;

public class DepthFirstSearch implements SearchAlgorithm {
    private int nodesExplored;
    
    @Override
    public SearchResult solve(int boardSize) {
        long startTime = System.currentTimeMillis();
        nodesExplored = 0;
        
        Board board = new Board(boardSize);
        boolean success = dfs(board, 0);
        
        long endTime = System.currentTimeMillis();
        
        return new SearchResult(
            success ? board : null,
            nodesExplored,
            endTime - startTime,
            success
        );
    }
    
    private boolean dfs(Board board, int row) {
        nodesExplored++;
        
        if (row == board.getSize()) {
            return board.isComplete();
        }
        
        for (int col = 0; col < board.getSize(); col++) {
            if (board.isSafe(row, col)) {
                board.placeQueen(row, col);
                
                if (dfs(board, row + 1)) {
                    return true;
                }
                
                board.removeQueen(row);
            }
        }
        
        return false;
    }
}
