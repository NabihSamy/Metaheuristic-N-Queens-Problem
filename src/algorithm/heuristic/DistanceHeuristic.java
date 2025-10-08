package src.algorithm.heuristic;

import src.model.Board;

public class DistanceHeuristic implements Heuristic {
    @Override
    public int evaluate(Board board) {
        // Votre logique de distance
        return board.getLastQueenDistance();
    }
}
