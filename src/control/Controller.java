package src.control;
import src.model.Solution;
import src.model.Solver;

/**
 * Contrôleur léger côté "modèle" pour piloter les algorithmes
 * et exposer les résultats à l'interface Swing.
 */
public class Controller {

    // Constantes de sélection d'algorithme
    public static final int DFS = 0;
    public static final int BFS = 1;
    public static final int ASTAR_H1 = 2;
    public static final int ASTAR_H2 = 3;

    private Solver solver;
    private Solution solution;

    /**
     * Lance la résolution pour N reines avec l'algorithme choisi.
     * @param nQueens nombre de reines (taille du plateau)
     * @param algo    0=DFS, 1=BFS, 2=A* H1, 3=A* H2
     */
    public void solve(int nQueens, int algo) {
        solver = new Solver(nQueens, algo);
        solution = solver.Sol; // Sol est déjà construit dans Solver
    }

    /**
     * Indique si une solution/évaluation a déjà été calculée.
     */
    public boolean hasResult() {
        return solution != null;
    }

    /**
     * Temps d'exécution en millisecondes.
     */
    public long getElapsedTimeMs() {
        return (solution != null) ? solution.elapsedTime : 0L;
    }

    /**
     * Temps d'exécution formaté en secondes avec 3 décimales (ex: "0.034 s").
     */
    public String getElapsedTimePretty() {
        double sec = getElapsedTimeMs() / 1000.0;
        return String.format("%.3f s", sec);
    }

    /**
     * Nombre de noeuds parcourus.
     */
    public long getParcouru() {
        return (solution != null) ? solution.parcouru : 0L;
    }

    /**
     * Nombre de noeuds créés.
     */
    public long getCree() {
        return (solution != null) ? solution.cree : 0L;
    }

    /**
     * Renvoie une copie des colonnes des reines par ligne (taille N),
     * ou un tableau vide si aucun résultat.
     */
    public int[] getQueens() {
        if (solution == null || solution.Solution == null || solution.Solution.Queens == null) {
            return new int[0];
        }
        int[] src = solution.Solution.Queens;
        int[] copy = new int[src.length];
        System.arraycopy(src, 0, copy, 0, src.length);
        return copy;
    }

    /**
     * Taille du plateau (N).
     */
    public int getBoardSize() {
        if (solution != null && solution.Solution != null && solution.Solution.Queens != null) {
            return solution.Solution.Queens.length;
        }
        return 0;
    }
}
