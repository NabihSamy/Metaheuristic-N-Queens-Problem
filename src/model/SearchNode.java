package src.model;

public class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private int gCost;
    private int hCost;
    private SearchNode parent;
    
    public SearchNode(Board board, int gCost, int hCost, SearchNode parent) {
        this.board = board;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public int getGCost() {
        return gCost;
    }
    
    public int getHCost() {
        return hCost;
    }
    
    public int getFCost() {
        return gCost + hCost;
    }
    
    public SearchNode getParent() {
        return parent;
    }
    
    @Override
    public int compareTo(SearchNode other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }
}
