package src.model;

/**
 * Represents a node in the search tree for solving the N-Queens problem.
 * Used primarily in informed search algorithms like A*.
 * Implements Comparable to allow priority queue ordering based on f-cost.
 */
public class SearchNode implements Comparable<SearchNode> {
    private final Board board;
    private final int gCost;  // Cost from start (depth/path cost)
    private final int hCost;  // Heuristic cost to goal
    private final SearchNode parent;  // Parent node for path reconstruction
    
    /**
     * Creates a search node with the given board state and costs.
     * 
     * @param board the board state at this node
     * @param gCost the cost from the start node (typically depth)
     * @param hCost the heuristic estimated cost to goal
     */
    public SearchNode(Board board, int gCost, int hCost) {
        this(board, gCost, hCost, null);
    }
    
    /**
     * Creates a search node with the given board state, costs, and parent.
     * 
     * @param board the board state at this node
     * @param gCost the cost from the start node (typically depth)
     * @param hCost the heuristic estimated cost to goal
     * @param parent the parent node in the search tree
     */
    public SearchNode(Board board, int gCost, int hCost, SearchNode parent) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        this.board = board;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }
    
    /**
     * Gets the total estimated cost f(n) = g(n) + h(n).
     * This is the evaluation function used in A* search.
     * 
     * @return the sum of path cost and heuristic cost
     */
    public int getFCost() {
        return gCost + hCost;
    }
    
    /**
     * Gets the cost from the start node to this node.
     * In N-Queens, this is typically the depth (number of queens placed).
     * 
     * @return the g-cost
     */
    public int getGCost() {
        return gCost;
    }
    
    /**
     * Gets the heuristic estimated cost from this node to the goal.
     * 
     * @return the h-cost
     */
    public int getHCost() {
        return hCost;
    }
    
    /**
     * Gets the board state at this node.
     * 
     * @return the board
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Gets the parent node in the search tree.
     * 
     * @return the parent node, or null if this is the root
     */
    public SearchNode getParent() {
        return parent;
    }
    
    /**
     * Checks if this node represents a goal state.
     * A goal state is a complete board with no conflicts.
     * 
     * @return true if this is a goal state
     */
    public boolean isGoal() {
        return board.isComplete() && board.isValid();
    }
    
    /**
     * Gets the depth of this node in the search tree.
     * This is the number of queens placed on the board.
     * 
     * @return the depth (number of placed queens)
     */
    public int getDepth() {
        return board.getPlacedQueens();
    }
    
    /**
     * Compares this node with another based on f-cost.
     * Used by priority queue to order nodes.
     * If f-costs are equal, prefers nodes with more queens placed (deeper nodes).
     * 
     * @param other the other node to compare to
     * @return negative if this node has lower priority, positive if higher, 0 if equal
     */
    @Override
    public int compareTo(SearchNode other) {
        // Primary comparison: f-cost (lower is better)
        int fComparison = Integer.compare(this.getFCost(), other.getFCost());
        if (fComparison != 0) {
            return fComparison;
        }
        
        // Tie-breaker: prefer deeper nodes (more queens placed)
        // This helps A* explore more complete solutions first
        return Integer.compare(other.getDepth(), this.getDepth());
    }
    
    /**
     * Creates a string representation of this search node.
     * 
     * @return a string showing the node's costs and board state
     */
    @Override
    public String toString() {
        return String.format("SearchNode[f=%d, g=%d, h=%d, depth=%d, complete=%b, valid=%b]",
                getFCost(), gCost, hCost, getDepth(), 
                board.isComplete(), board.isValid());
    }
    
    /**
     * Creates a detailed string representation including board visualization.
     * 
     * @return a detailed string with costs and board layout
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\n");
        sb.append("Board State:\n");
        sb.append(board.toVisualString());
        return sb.toString();
    }
    
    /**
     * Reconstructs the path from the start node to this node.
     * 
     * @return an array of boards representing the solution path
     */
    public Board[] reconstructPath() {
        // Count the depth to create the array
        int depth = getDepth() + 1;  // +1 for initial empty board
        Board[] path = new Board[depth];
        
        // Traverse back to root
        SearchNode current = this;
        for (int i = depth - 1; i >= 0 && current != null; i--) {
            path[i] = current.board;
            current = current.parent;
        }
        
        return path;
    }
    
    /**
     * Calculates the branching factor at this node.
     * For N-Queens, this is typically N (one branch per column).
     * 
     * @return the number of possible successor nodes
     */
    public int getBranchingFactor() {
        return board.isComplete() ? 0 : board.getSize();
    }
    
    /**
     * Checks if this node is a leaf node (no more successors).
     * 
     * @return true if the board is complete
     */
    public boolean isLeaf() {
        return board.isComplete();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        SearchNode other = (SearchNode) obj;
        
        // Two nodes are equal if they have the same board state
        // (we compare queen positions)
        int[] thisPositions = this.board.getQueenPositions();
        int[] otherPositions = other.board.getQueenPositions();
        
        if (thisPositions.length != otherPositions.length) return false;
        
        for (int i = 0; i < thisPositions.length; i++) {
            if (thisPositions[i] != otherPositions[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        // Hash based on board configuration
        int hash = 17;
        int[] positions = board.getQueenPositions();
        for (int pos : positions) {
            hash = hash * 31 + pos;
        }
        return hash;
    }
}