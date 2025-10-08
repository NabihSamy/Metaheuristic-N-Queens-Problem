package src.model;
import src.model.Board;


public class SearchNode implements Comparable<SearchNode> {
    private final Board board;
    private final int gCost;  // Coût depuis le début (depth)
    private final int hCost;  // Heuristique
    
    public int getFCost() { return gCost + hCost; }
    
    @Override
    public int compareTo(SearchNode other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }
}