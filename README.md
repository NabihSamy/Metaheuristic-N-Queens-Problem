# N-Queens Solver 👑

Résolveur du problème des N-Reines utilisant différents algorithmes de recherche avec interface graphique Swing.

![Interface](docs/screenshot.png)

## 📋 Description

Ce projet implémente une solution au problème des N-Reines en utilisant:
- **DFS** (Depth-First Search)
- **BFS** (Breadth-First Search)
- **A*** avec deux heuristiques différentes:
  - H1: Heuristique basée sur les conflits
  - H2: Heuristique basée sur la distance

## 🔧 Prérequis

- **Java**: Version 11 ou supérieur
- **Maven**: 3.6+ (optionnel, pour la gestion automatique)

### Versions testées et compatibles

| Version Java | Statut | Date de test |
|--------------|--------|--------------|
| Java 25      | ✅ Compatible | Octobre 2025 |

## 🚀 Installation et Exécution

### Méthode 1: Sans Maven (compilation manuelle)

Cloner le repository
git clone https://github.com/VOTRE_USERNAME/nqueens-solver.git
cd nqueens-solver

Créer le dossier de compilation
mkdir -p bin

Compiler tous les fichiers
javac -d bin -encoding UTF-8 src/model/.java src/algorithm/.java src/algorithm/heuristic/.java src/control/.java src/view/*.java src/Main.java

Copier les ressources
cp -r src/ressources bin/

Exécuter
java -cp bin src.Main

### Méthode 2: Avec Maven (recommandé)


Cloner le repository
git clone https://github.com/VOTRE_USERNAME/nqueens-solver.git
cd nqueens-solver

Compiler
mvn clean compile

Exécuter
mvn exec:java

Ou créer un JAR exécutable
mvn package
java -jar target/nqueens-solver-1.0.0.jar



## 📁 Structure du Projet


nqueens-solver/
├── src/ \n
│ ├── algorithm/ \n
│ │ ├── SearchAlgorithm.java # Interface commune \n
│ │ ├── DepthFirstSearch.java # Algorithme DFS
│ │ ├── BreadthFirstSearch.java # Algorithme BFS
│ │ ├── AStarSearch.java # Algorithme A*
│ │ └── heuristic/
│ │ ├── Heuristic.java # Interface heuristique
│ │ ├── ConflictHeuristic.java # H1: Conflits
│ │ └── DistanceHeuristic.java # H2: Distance
│ ├── model/
│ │ ├── Board.java # État du plateau
│ │ ├── SearchNode.java # Nœud de recherche
│ │ └── SearchResult.java # Résultat de recherche
│ ├── control/
│ │ └── SolverController.java # Contrôleur MVC
│ ├── view/
│ │ └── QueensGUI.java # Interface graphique
│ ├── ressources/
│ │ └── crown-gold.png # Icône des reines
│ └── Main.java # Point d'entrée
├── pom.xml # Configuration Maven
├── .gitignore # Fichiers à ignorer
├── .java-version # Version Java
└── README.md # Ce fichier



## 🎮 Utilisation

1. **Lancer l'application**
2. **Choisir la taille** de l'échiquier (4 à 20)
3. **Sélectionner un algorithme**:
   - DFS: Recherche en profondeur
   - BFS: Recherche en largeur
   - A* - H1(): A* avec heuristique de conflits
   - A* - H2(): A* avec heuristique de distance
4. **Cliquer sur "Résoudre"**
5. **Observer les résultats**:
   - Solution affichée visuellement
   - Temps d'exécution
   - Nombre de nœuds parcourus
   - Nombre de nœuds créés

## 📊 Comparaison des Algorithmes

| Algorithme | Complexité Temps | Complexité Espace | Optimal | Complet |
|------------|------------------|-------------------|---------|---------|
| DFS        | O(b^m)          | O(bm)            | ❌      | ✅      |
| BFS        | O(b^d)          | O(b^d)           | ✅      | ✅      |
| A* (H1)    | O(b^d)          | O(b^d)           | ✅*     | ✅      |
| A* (H2)    | O(b^d)          | O(b^d)           | ✅*     | ✅      |

*Si l'heuristique est admissible

## 🧪 Tests

Pour tester différentes tailles:
- **N=4**: Solution triviale
- **N=8**: Problème classique
- **N=10**: Complexité moyenne
- **N=12+**: Haute complexité

## 🤝 Contribution

Les contributions sont les bienvenues! N'hésite pas à:
1. Fork le projet
2. Créer une branche (`git checkout -b feature/amelioration`)
3. Commit tes changements (`git commit -m 'Ajout fonctionnalité'`)
4. Push vers la branche (`git push origin feature/amelioration`)
5. Ouvrir une Pull Request

## 📝 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👤 Auteur

**Ton Nom**
- GitHub: [@NabihSamy](https://github.com/NabihSamy)
- LinkedIn: [ABDELHADI Nabih Samy](https://www.linkedin.com/in/nabih-samy-abdelhadi-31538a243/)



### Heuristiques Implémentées

**H1 (ConflictHeuristic)**: Compte le nombre de conflits entre les reines déjà placées et ajoute le nombre de reines restantes à placer.

**H2 (DistanceHeuristic)**: Calcule la distance restante pour placer toutes les reines en tenant compte des conflits existants.

### Optimisations

- Utilisation de structures de données efficaces (HashSet pour éviter les états dupliqués)
- Interface graphique non-bloquante (SwingWorker)
- Adaptation dynamique de la taille de l'échiquier

---

⭐ N'oublie pas de mettre une étoile si ce projet t'a aidé!
