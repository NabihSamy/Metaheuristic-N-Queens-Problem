package src.model;

public class Board {
    private final int[] queenPositions;  // private + final
    private final int size;
    private final int placedQueens;      // renommé de 'index'
    
    // Constructeur immutable
    public Board(int size) { ... }
    
    // Méthode retournant un NOUVEAU board
    public Board placeQueen(int column) {
        Board newBoard = new Board(this);
        // ...
        return newBoard;
    }
    
    // Méthodes avec noms clairs
    public boolean isComplete() { return placedQueens == size; }
    public int countConflicts() { return 0; /* Votre logique ici */ }
    public boolean isValid() { return countConflicts() == 0; }

	public int getLastDistance() {
		// TODO Auto-generated method stub
		return 0;
	}
}