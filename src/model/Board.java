package src.model;

/**
 * Represents an immutable N-Queens board state.
 * Each queen is placed in a separate row, and the array stores the column position for each row.
 */
public class Board {
    private final int[] queenPositions;
    private final int size;
    private final int placedQueens;
    
    // Cache for conflict calculation (computed once, then reused)
    private Integer cachedConflicts = null;
    
    /**
     * Creates a new empty board of the given size.
     * 
     * @param size the size of the board (N x N)
     */
    public Board(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Board size must be at least 1");
        }
        this.size = size;
        this.queenPositions = new int[size];
        for (int i = 0; i < size; i++) {
            queenPositions[i] = -1;
        }
        this.placedQueens = 0;
    }
    
    /**
     * Copy constructor - creates a copy of the given board.
     * 
     * @param other the board to copy
     */
    private Board(Board other) {
        this.size = other.size;
        this.queenPositions = new int[other.size];
        System.arraycopy(other.queenPositions, 0, this.queenPositions, 0, other.size);
        this.placedQueens = other.placedQueens;
        // Don't copy cache - will be recomputed if needed
    }
    
    /**
     * Places a queen at the specified column in the next available row.
     * Returns a new Board instance (this method does not modify the current board).
     * 
     * @param column the column where to place the queen (0-indexed)
     * @return a new Board with the queen placed
     * @throws IllegalStateException if the board is already complete
     * @throws IllegalArgumentException if column is out of bounds
     */
    public Board placeQueen(int column) {
        if (isComplete()) {
            throw new IllegalStateException("Board is already complete - cannot place more queens");
        }
        if (column < 0 || column >= size) {
            throw new IllegalArgumentException("Column must be between 0 and " + (size - 1));
        }
        
        Board newBoard = new Board(this);
        newBoard.queenPositions[newBoard.placedQueens] = column;
        // Use reflection to modify the final field (hacky but necessary for immutability pattern)
        try {
            java.lang.reflect.Field field = Board.class.getDeclaredField("placedQueens");
            field.setAccessible(true);
            field.setInt(newBoard, this.placedQueens + 1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update placedQueens", e);
        }
        
        return newBoard;
    }
    
    /**
     * Checks if all queens have been placed on the board.
     * 
     * @return true if the board is complete (N queens placed)
     */
    public boolean isComplete() {
        return placedQueens == size;
    }
    
    /**
     * Checks if the current board configuration is valid (no conflicts).
     * 
     * @return true if no queens are attacking each other
     */
    public boolean isValid() {
        return countConflicts() == 0;
    }
    
    /**
     * Counts the total number of queen conflicts on the board.
     * Each pair of attacking queens is counted once.
     * Uses caching to avoid redundant calculations.
     * 
     * @return the number of conflicts (0 means valid solution)
     */
    public int countConflicts() {
        if (cachedConflicts != null) {
            return cachedConflicts;
        }
        
        int conflicts = 0;
        
        // Check each pair of placed queens
        for (int i = 0; i < placedQueens; i++) {
            if (queenPositions[i] == -1) continue;
            
            for (int j = i + 1; j < placedQueens; j++) {
                if (queenPositions[j] == -1) continue;
                
                // Check column conflict (same column)
                if (queenPositions[i] == queenPositions[j]) {
                    conflicts++;
                }
                
                // Check diagonal conflict
                int rowDiff = Math.abs(i - j);
                int colDiff = Math.abs(queenPositions[i] - queenPositions[j]);
                if (rowDiff == colDiff) {
                    conflicts++;
                }
            }
        }
        
        cachedConflicts = conflicts;
        return conflicts;
    }
    
    /**
     * Counts conflicts with weights (for heuristic purposes).
     * This method counts all attacking relationships.
     * 
     * @return weighted conflict count
     */
    public int countConflictsWithWeight() {
        int conflicts = 0;
        
        for (int i = 0; i < placedQueens; i++) {
            if (queenPositions[i] == -1) continue;
            
            // Check column conflicts
            for (int j = 0; j < placedQueens; j++) {
                if (i != j && queenPositions[i] == queenPositions[j] && queenPositions[i] != -1) {
                    conflicts++;
                }
            }
            
            // Check diagonal conflicts
            conflicts += countDiagonalConflicts(i, queenPositions[i]);
        }
        
        return conflicts;
    }
    
    /**
     * Counts diagonal conflicts for a queen at the given position.
     * 
     * @param row the row of the queen
     * @param col the column of the queen
     * @return number of diagonal conflicts
     */
    private int countDiagonalConflicts(int row, int col) {
        int conflicts = 0;
        
        // Check all four diagonal directions
        int[][] directions = {{-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            
            while (r >= 0 && r < placedQueens && c >= 0 && c < size) {
                if (queenPositions[r] == c && c != col && r != row && c != -1) {
                    conflicts++;
                }
                r += dir[0];
                c += dir[1];
            }
        }
        
        return conflicts;
    }
    
    /**
     * Calculates the distance between the last two placed queens.
     * Used as a heuristic in some algorithms.
     * 
     * @return the column distance between the last two queens, or 0 if less than 2 queens
     */
    public int getLastQueenDistance() {
        if (placedQueens < 2) {
            return 0;
        }
        return Math.abs(queenPositions[placedQueens - 1] - queenPositions[placedQueens - 2]);
    }
    
    /**
     * Gets the number of queens currently placed on the board.
     * 
     * @return the number of placed queens
     */
    public int getPlacedQueens() {
        return placedQueens;
    }
    
    /**
     * Gets the size of the board.
     * 
     * @return the board size (N)
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets a copy of the queen positions array.
     * Each index represents a row, and the value represents the column (0-indexed).
     * A value of -1 means no queen is placed in that row yet.
     * 
     * @return a copy of the queen positions array
     */
    public int[] getQueenPositions() {
        int[] copy = new int[size];
        System.arraycopy(queenPositions, 0, copy, 0, size);
        return copy;
    }
    
    /**
     * Gets the column position of the queen in the specified row.
     * 
     * @param row the row to check
     * @return the column position, or -1 if no queen in that row
     * @throws IllegalArgumentException if row is out of bounds
     */
    public int getQueenColumn(int row) {
        if (row < 0 || row >= size) {
            throw new IllegalArgumentException("Row must be between 0 and " + (size - 1));
        }
        return queenPositions[row];
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board[").append(size).append("x").append(size).append("] - ");
        sb.append(placedQueens).append(" queens placed");
        
        if (isComplete()) {
            sb.append(", conflicts: ").append(countConflicts());
        }
        
        sb.append("\nPositions: ");
        for (int i = 0; i < placedQueens; i++) {
            sb.append("[").append(i).append(",").append(queenPositions[i]).append("] ");
        }
        
        return sb.toString();
    }
    
    /**
     * Creates a visual representation of the board.
     * 
     * @return a string showing the board with Q for queens and . for empty squares
     */
    public String toVisualString() {
        StringBuilder sb = new StringBuilder();
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (queenPositions[row] == col) {
                    sb.append("Q ");
                } else {
                    sb.append(". ");
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}