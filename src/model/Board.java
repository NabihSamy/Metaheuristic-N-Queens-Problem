package src.model;

import java.util.Arrays;

public class Board {
    private int[] queens;
    private int size;
    
    public Board(int size) {
        this.size = size;
        this.queens = new int[size];
        Arrays.fill(queens, -1);
    }
    
    public Board(Board other) {
        this.size = other.size;
        this.queens = Arrays.copyOf(other.queens, other.size);
    }
    
    public void placeQueen(int row, int col) {
        queens[row] = col;
    }
    
    public void removeQueen(int row) {
        queens[row] = -1;
    }
    
    public int getQueenColumn(int row) {
        return queens[row];
    }
    
    public int getSize() {
        return size;
    }
    
    public int[] getQueens() {
        return Arrays.copyOf(queens, size);
    }
    
    public boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            int queenCol = queens[i];
            if (queenCol == -1) continue;
            
            if (queenCol == col) return false;
            if (Math.abs(queenCol - col) == Math.abs(i - row)) return false;
        }
        return true;
    }
    
    public boolean isComplete() {
        for (int i = 0; i < size; i++) {
            if (queens[i] == -1) return false;
        }
        return countConflicts() == 0;
    }
    
    public int countConflicts() {
        int conflicts = 0;
        for (int i = 0; i < size; i++) {
            if (queens[i] == -1) continue;
            for (int j = i + 1; j < size; j++) {
                if (queens[j] == -1) continue;
                
                if (queens[i] == queens[j]) conflicts++;
                if (Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) conflicts++;
            }
        }
        return conflicts;
    }
    
    public int getLastQueenDistance() {
        int distance = 0;
        for (int i = 0; i < size; i++) {
            if (queens[i] == -1) {
                distance += size;
            }
        }
        return distance;
    }
    
    public int getDepth() {
        int depth = 0;
        for (int i = 0; i < size; i++) {
            if (queens[i] != -1) depth++;
        }
        return depth;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(queens[i] == j ? "Q " : ". ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) return false;
        Board other = (Board) obj;
        return Arrays.equals(this.queens, other.queens);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(queens);
    }
}
