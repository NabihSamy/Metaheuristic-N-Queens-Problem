package src.algorithm.heuristic;

import src.model.Board;

public class DistanceHeuristic implements Heuristic {
    
    @Override
    public int evaluate(Board board) {
        return board.getSize() - board.getDepth() + board.countConflicts();
    }
}
