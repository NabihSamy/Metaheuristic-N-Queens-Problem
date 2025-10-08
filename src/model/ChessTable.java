package src.model;
public class ChessTable {
	
	public int Queens[];
	public int max;
	public int index;
	public boolean finished = false;
	
	public ChessTable(int NbQueens){
		this.Queens = new int[NbQueens];
			for(int i = 0 ; i < NbQueens;i++) this.Queens[i] = -1;
		this.max = NbQueens;
		this.index=0;
	}
	public ChessTable(ChessTable T){
		this.Queens = new int[T.max];
		for(int i = 0 ; i < T.index;i++) this.Queens[i] = T.Queens[i];
		for(int i = T.index;i < T.max;i++) this.Queens[i] = -1;
		this.max = T.max;
		this.finished = T.finished;
		this.index=T.index;
	}
	
	public void addElement(int x) {
		if(!finished) {
			this.Queens[index] = x;
			index++;
			if(index == max) finished = true;
		}
	}
	
	public  int verifyQueens() {

		for(int i = 0;i < this.index;i++) {
			
			//on vérifie les colonne vu que les lignes sont surement juste
			for(int j= 0; j<this.index;j++) {
				if(Queens[i] == Queens[j] && i != j && Queens[i] != -1) return 1;
			}
			
			//vérification des diagonale
			if(verifyDiag(i,this.Queens[i]) == 1) return 1;
		}
		return 0;
	}
	
	private int verifyDiag(int i, int j) {

		//on vérifie les quatre diagonale de l'element situé a [i,j]
		//Nord ouest
		int tempI = i,tempJ = j;
		while(tempI >= 0 && tempJ >= 0) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) return 1;
			tempI--;
			tempJ--;
		}
		//Sud est
		tempI = i;
		tempJ = j;
		while(tempI < this.index && tempJ < this.index) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) return 1;
			tempI++;
			tempJ++;
		}
		// nord est
		tempI = i;
		tempJ = j;
		while(tempI >= 0 && tempJ < this.index) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) return 1;
			tempI--;
			tempJ++;
		}
		//sud ouest
		tempI = i;
		tempJ = j;
		while(tempI < this.index && tempJ >= 0) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) return 1;
			tempI++;
			tempJ--;
		}
		return 0;
	}


	public  int verifyQueensWithWeight() {
		int inDanger = 0;
		for(int i = 0;i < this.index;i++) {
			
			//on vérifie les colonne vu que les lignes sont surement juste
			for(int j= 0; j<this.index;j++) {
				if(Queens[i] == Queens[j] && i != j && Queens[i] != -1) inDanger++;
			}
			
			//vérification des diagonale
			inDanger = inDanger + verifyDiagWithWeight(i,Queens[i]);
		}
		return inDanger;
	}
	
	private int verifyDiagWithWeight(int i, int j) {
		int Danger = 0;
		//on vérifie les quatre diagonale de l'element situé a [i,j]
		//Nord ouest
		int tempI = i,tempJ = j;
		while(tempI >= 0 && tempJ >= 0) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) Danger++;
			tempI--;
			tempJ--;
		}
		//Sud est
		tempI = i;
		tempJ = j;
		while(tempI < this.index && tempJ < this.index) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) Danger++;
			tempI++;
			tempJ++;
		}
		// nord est
		tempI = i;
		tempJ = j;
		while(tempI >= 0 && tempJ < this.index) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) Danger++;
			tempI--;
			tempJ++;
		}
		//sud ouest
		tempI = i;
		tempJ = j;
		while(tempI < this.index && tempJ >= 0) {
			if(this.Queens[tempI] == tempJ && tempJ  !=j && tempI != i && tempJ != -1) Danger++;
			tempI++;
			tempJ--;
		}
		return Danger;
	}
	public int distance() {
		if(index < 2) return 0;
		return Math.abs(this.Queens[index-1] - this.Queens[index-2]);
	}
	}
