# N-Queens Solver 👑

A solver for the **N-Queens problem**, implementing several search algorithms with a **Swing-based graphical interface**.

![Interface](docs/screenshot.png)

---

## 📋 Description

This project provides an implementation of the N-Queens problem using various search algorithms:

* **DFS** (Depth-First Search)
* **BFS** (Breadth-First Search)
* **A*** with two different heuristics:

  * **H1:** Conflict-based heuristic
  * **H2:** Distance-based heuristic

---

## 🔧 Requirements

* **Java:** Version 25
* **Maven:** 3.6+ (optional, for automatic dependency management)

### Tested and Compatible Versions

| Java Version | Status       | Test Date    |
| ------------ | ------------ | ------------ |
| Java 25      | ✅ Compatible | October 2025 |

---

## 🚀 Installation & Execution

### Method : 

Clone the repository:

```bash
git clone https://github.com/NabihSamy/Metaheuristic-N-Queens-Problem.git
cd Metaheuristic-N-Queens-Problem
```

Create the compilation directory:

```bash
mkdir -p bin
mkdir -p bin\ressources
```

Compile all files:

```bash
javac -d bin -encoding UTF-8 src/model/*.java src/algorithm/*.java src/algorithm/heuristic/*.java src/control/*.java src/view/*.java src/Main.java
```

Copy resources:

```bash
xcopy ressources bin\ressources /E /I /Y
```

Run the program:

```bash
java -cp bin src.Main
```

---

## 📁 Project Structure

```
nqueens-solver/
├── src/
│   ├── algorithm/
│   │   ├── SearchAlgorithm.java        # Common interface
│   │   ├── DepthFirstSearch.java       # DFS algorithm
│   │   ├── BreadthFirstSearch.java     # BFS algorithm
│   │   ├── AStarSearch.java            # A* algorithm
│   │   └── heuristic/
│   │       ├── Heuristic.java          # Heuristic interface
│   │       ├── ConflictHeuristic.java  # H1: Conflict-based
│   │       └── DistanceHeuristic.java  # H2: Distance-based
│   ├── model/
│   │   ├── Board.java                  # Board state
│   │   ├── SearchNode.java             # Search node
│   │   └── SearchResult.java           # Search result
│   ├── control/
│   │   └── SolverController.java       # MVC controller
│   ├── view/
│   │   └── QueensGUI.java              # Graphical interface
│   ├── ressources/
│   │   └── crown-gold.png              # Queen icon
│   └── Main.java                       # Entry point
├── .gitignore
├── .java-version
└── README.md
```

---

## 🎮 Usage

1. **Launch the application**
2. **Choose the board size** (4–20)
3. **Select an algorithm:**

   * DFS: Depth-First Search
   * BFS: Breadth-First Search
   * A* – H1: A* with conflict heuristic
   * A* – H2: A* with distance heuristic
4. **Click “Solve”**
5. **Observe the results:**

   * Visual board solution
   * Execution time
   * Number of visited nodes
   * Number of created nodes

---

## 📊 Algorithm Comparison

| **Algorithm** | **Time Complexity** | **Space Complexity** | **Complete** |
|----------------|---------------------|----------------------|---------------|
| **DFS**        | O(b^m)              | O(bm)                | ✅ |
| **BFS**        | O(b^d)              | O(b^d)               | ✅ |
| **A*** (H1)    | O(b^d)              | O(b^d)               | ✅ |
| **A*** (H2)    | O(b^d)              | O(b^d)               | ✅ |

### Notes
- **b** → branching factor  
- **m** → maximum search depth  
- **d** → depth of a valid solution  
- **DFS** is generally **faster and more memory-efficient** than BFS for N-Queens.  
- **BFS** is theoretically complete but **impractical for large N** due to exponential memory growth.  
- **A*** with heuristics significantly **reduces the number of explored states** by focusing on promising configurations.  

---

### 🧠 Performance Summary

For the **N-Queens problem**:
- **DFS** is the **most practical** uninformed search method. It quickly finds valid solutions while keeping memory usage low.  
- **BFS** explores all possibilities level by level, which guarantees completeness but makes it **inefficient beyond small N**.  
- **A*** (with **H1** or **H2**) offers the **best balance** between exploration cost and runtime efficiency.  
- **H1 (Conflict Heuristic)** typically performs **better than H2**, as it directly minimizes queen conflicts during search.  

---

---


## 📝 License

This project is licensed under the **MIT License**.
See the `LICENSE` file for details.

---

## 👤 Author

**Nabih Samy ABDELHADI**

* GitHub: [@NabihSamy](https://github.com/NabihSamy)
* LinkedIn: [ABDELHADI Nabih Samy](https://www.linkedin.com/in/nabih-samy-abdelhadi-31538a243/)

---

### Implemented Heuristics

**H1 (ConflictHeuristic):**
Counts the number of conflicts among already placed queens and adds the number of remaining queens to be placed.

**H2 (DistanceHeuristic):**
Calculates the remaining distance to place all queens while accounting for existing conflicts.

---

### Optimizations

* Efficient data structures (e.g., `HashSet` to avoid duplicate states)
* Non-blocking GUI with `SwingWorker`
* Dynamic chessboard size adjustment

---

⭐ **Don’t forget to star this project if you found it useful!**

