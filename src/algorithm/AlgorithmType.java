package src.algorithm;

public enum AlgorithmType {
    DFS("Depth First Search"),
    BFS("Breadth First Search"),
    ASTAR_DISTANCE("A* (Distance)"),
    ASTAR_CONFLICT("A* (Conflits)");
    
    private String displayName;
    
    AlgorithmType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
