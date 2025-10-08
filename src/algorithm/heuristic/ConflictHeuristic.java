package src.algorithm.heuristic;

import src.model.Board;

public class ConflictHeuristic implements Heuristic {
    @Override
    public int evaluate(Board board) {
        // Compte les conflits (attention au double comptage!)
        return board.countConflicts() / 2;
    }
}
