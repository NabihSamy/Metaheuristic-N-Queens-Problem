package src.algorithm.heuristic;

import src.model.Board;

public class ConflictHeuristic implements Heuristic {
    
    @Override
    public int evaluate(Board board) {
        int conflicts = board.countConflicts();
        int remainingQueens = board.getSize() - board.getDepth();
        return conflicts + remainingQueens;
    }
}
