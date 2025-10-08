package src.model;
public class Solution{
	public long elapsedTime;
	public ChessTable Solution;
	public long parcouru;
	public long cree;
	
	public Solution(ChessTable sol,long time,long parcouru,long cree) {
		this.Solution = sol;
		this.elapsedTime = time;
		this.parcouru=parcouru;
		this.cree=cree;
	}
}