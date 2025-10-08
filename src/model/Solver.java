package src.model;
import java.util.*;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solver {
	public Solution Sol;
	long parcouru=0,cree=0;
	public Solver(int NombreReines,int algo) {
		//DFS CALCULATION
			ChessTable  sol = null;
			long start = System. currentTimeMillis();
			switch(algo) {
				case  0:
					 sol = dfs(NombreReines);
					break;
				case  1:
					sol = bfs(NombreReines);
					break;
				case  2:
					sol = A1(NombreReines);
					break;
				case  3:
					sol = A2(NombreReines);
					break;
			}
			long end = System. currentTimeMillis();
		this.Sol = new Solution(sol,end-start,parcouru,cree);
		parcouru = 0;cree=0;
	}
	
	public ChessTable dfs(int NombreReines) {
	    	int min = Integer.MAX_VALUE;
	    	Stack<ChessTable> StateStack = new Stack<ChessTable>();
	    	ChessTable sol = new ChessTable(NombreReines);
	    	ChessTable current;
	    	ChessTable  next;
	    	StateStack.push(sol);
	    	while(!StateStack.empty()) {
	    		current = StateStack.pop();
	    		parcouru++;
	    		if(!current.finished) {
	        		for(int i = 0; i < NombreReines;i++) {
	        			next = new  ChessTable(current);
	        			next.addElement(i);
	        			StateStack.push(next);
	        			cree++;
	        		}
	    		}else {
	    			int CurrentDanger = current.verifyQueens();
	    			if(CurrentDanger < min) {
	    				sol  = current;
	    				min  = CurrentDanger;
	    			}
	    			if(CurrentDanger == 0) {sol  = new ChessTable(current);StateStack.clear();}
	    		}
	    	}
	    	return sol;
	    }

	public ChessTable bfs(int NombreReines) {
	    	int min = Integer.MAX_VALUE;
	    	LinkedList<ChessTable> StateList = new LinkedList<ChessTable>();
	    	ChessTable sol = new ChessTable(NombreReines);
	    	ChessTable current;
	    	ChessTable  next;

	    	StateList.add(sol);
	    	while(!StateList.isEmpty()) {
	    		current = StateList.poll();
	    		parcouru++;
	    		if(!current.finished) {
	        		for(int i = 0; i < NombreReines;i++) {
	        			next = new  ChessTable(current);
	        			next.addElement(i);
	        			StateList.add(next);
	        			cree++;
	        		}
	    		}else {
	    			int CurrentDanger = current.verifyQueens();
	    			if(CurrentDanger < min) {
	    				sol  = current;
	    				min  = CurrentDanger;
	    			}
	    			if(CurrentDanger == 0) {sol  = new ChessTable(current);StateList.clear();}
	    		}
	    	}
	    	return sol;
	    }

	
	public ChessTable A1(int NombreReines) {
  
    	PriorityQueue<ChessTable> StateList = new PriorityQueue<ChessTable>(new Comparator<ChessTable>() {

			@Override
			public int compare(ChessTable o1, ChessTable o2) {
				int x = o1.verifyQueensWithWeight();
				int y = o2.verifyQueensWithWeight();
				if(x > y)  return 1;
				if(x < y)  return -1;
				if( o1.index > o2.index ) return -1;
				if (o1.index < o2.index ) return 1;
				return 1;
			}});
    	
    	
    	ChessTable sol = new ChessTable(NombreReines);
    	ChessTable current;
    	ChessTable  next;

    	StateList.add(sol);
    	while(!StateList.isEmpty()) {
    		current = StateList.poll();
    		parcouru++;
    		if(!current.finished) {
        		for(int i = 0; i < NombreReines;i++) {
        			next = new  ChessTable(current);
        			next.addElement(i);
        			StateList.add(next);
        			cree++;
        		}
    		}else {
    			if(current.verifyQueens() == 0) {sol  = new ChessTable(current);StateList.clear();}
    		}
    	}
    	return sol;
	}
	public ChessTable A2(int NombreReines) {
		
		// modifier pour que la case choisie soit celle ou la distance est de N+1/2
		
		PriorityQueue<ChessTable> StateList = new PriorityQueue<ChessTable>(new Comparator<ChessTable>() {

			@Override
			public int compare(ChessTable o1, ChessTable o2) {
				int dist1 = o1.distance();
				
				int dist2 = o2.distance();
				
				if(dist1 > dist2)  return -1;
				if(dist1 < dist2)  return 1;
				if( o1.index > o2.index ) return -1;
				if (o1.index < o2.index ) return 1;
				return 1;
			}});
    	
    	ChessTable sol = new ChessTable(NombreReines);
    	ChessTable current;
    	ChessTable  next;

    	StateList.add(sol);
    	while(!StateList.isEmpty()) {
    		current = StateList.poll();
    		parcouru++;
    		if(!current.finished) {
        		for(int i = 0; i < NombreReines;i++) {
        			next = new  ChessTable(current);
        			next.addElement(i);
        			StateList.add(next);
        			cree++;
        		}
    		}else {
    			if(current.verifyQueens() == 0) {sol  = new ChessTable(current);StateList.clear();}
    		}
    	}
    	return sol;
	}
}

