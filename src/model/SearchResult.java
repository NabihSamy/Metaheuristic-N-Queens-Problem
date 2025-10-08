package src.model;

public class SearchResult {
    private Board solution;
    private int nodesExplored;
    private long timeMillis;
    private boolean success;
    
    public SearchResult(Board solution, int nodesExplored, long timeMillis, boolean success) {
        this.solution = solution;
        this.nodesExplored = nodesExplored;
        this.timeMillis = timeMillis;
        this.success = success;
    }
    
    public Board getSolution() {
        return solution;
    }
    
    public int getNodesExplored() {
        return nodesExplored;
    }
    
    public long getTimeMillis() {
        return timeMillis;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    @Override
    public String toString() {
        return String.format("Solution trouvée: %s\nNœuds explorés: %d\nTemps: %d ms", 
            success ? "Oui" : "Non", nodesExplored, timeMillis);
    }
}
