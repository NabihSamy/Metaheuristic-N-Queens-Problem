package src.algorithm.heuristic;
import src.model.Board;

public interface Heuristic {
    int evaluate(Board board);
}