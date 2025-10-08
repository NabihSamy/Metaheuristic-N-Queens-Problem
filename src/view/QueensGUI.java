package src.view;
import javax.swing.*;
import java.awt.*;

import src.algorithm.AlgorithmType;
import src.control.SolverController;

public class QueensGUI extends JFrame {
    private JPanel boardPanel;
    private JTextField damesField;
    private JTextField timeField;
    private JTextField parcouruField;
    private JTextField creeField;
    private JRadioButton dfsButton;
    private JRadioButton bfsButton;
    private JRadioButton aStar1Button;
    private JRadioButton aStar2Button;
    private int boardSize = 8;
    private SolverController controller;

    public QueensGUI() {
        controller = new SolverController();
        
        setTitle("♛ Dame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau de droite
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(210, 0));
        rightPanel.setLayout(null);

        // Champ modifiable pour la taille
        damesField = new JTextField(String.valueOf(boardSize));
        damesField.setBounds(50, 30, 110, 35);
        damesField.setFont(new Font("Arial", Font.BOLD, 20));
        damesField.setHorizontalAlignment(JTextField.CENTER);

        // Bouton "GO" - Lance la résolution
        JButton goButton = new JButton("GO");
        goButton.setFont(new Font("Arial", Font.ITALIC, 14));
        goButton.setBounds(75, 65, 60, 25);

        // Action pour lancer la résolution
        Runnable solveNQueens = () -> {
            try {
                int newSize = Integer.parseInt(damesField.getText());
                if (newSize >= 4 && newSize <= 20) {
                    boardSize = newSize;
                    
                    // Déterminer l'algorithme sélectionné
                    AlgorithmType algo;
                    if (dfsButton.isSelected()) algo            = SolverController.DFS;
                    else if (bfsButton.isSelected()) algo       = SolverController.BFS;
                    else if (aStar1Button.isSelected()) algo    = SolverController.ASTAR_H1;
                    else if (aStar2Button.isSelected()) algo    = SolverController.ASTAR_H2;
                    else algo = SolverController.ASTAR_H1; // par défaut
                    
                    // Désactiver les contrôles pendant le calcul
                    goButton.setEnabled(false);
                    damesField.setEnabled(false);
                    timeField.setText("Calcul...");
                    
                    // Lancer le solver dans un thread séparé pour ne pas bloquer l'UI
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            controller.solve(boardSize, algo);
                            return null;
                        }
                        
                        @Override
                        protected void done() {
                            // Mettre à jour l'affichage avec les résultats
                            updateResults();
                            rebuildBoard();
                            
                            // Réactiver les contrôles
                            goButton.setEnabled(true);
                            damesField.setEnabled(true);
                        }
                    };
                    worker.execute();
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Entrez une taille entre 4 et 20.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
            }
        };

        goButton.addActionListener(e -> solveNQueens.run());
        damesField.addActionListener(e -> solveNQueens.run());

        // Sélection d'algo (boutons radio)
        dfsButton = new JRadioButton("DFS");
        dfsButton.setBounds(40, 100, 110, 25);
        bfsButton = new JRadioButton("BFS");
        bfsButton.setBounds(40, 130, 110, 25);
        aStar1Button = new JRadioButton("A* - H1()");
        aStar1Button.setBounds(40, 160, 110, 25);
        aStar2Button = new JRadioButton("A* - H2()");
        aStar2Button.setBounds(40, 190, 110, 25);
        aStar1Button.setSelected(true);

        ButtonGroup algoGroup = new ButtonGroup();
        algoGroup.add(dfsButton);
        algoGroup.add(bfsButton);
        algoGroup.add(aStar1Button);
        algoGroup.add(aStar2Button);

        // Temps affiché
        timeField = new JTextField("--");
        timeField.setBounds(50, 235, 110, 35);
        timeField.setHorizontalAlignment(JTextField.CENTER);
        timeField.setFont(new Font("Arial", Font.BOLD, 18));
        timeField.setEditable(false);

        // Parcouru
        JLabel parcouruLabel = new JLabel("Parcouru");
        parcouruLabel.setBounds(40, 285, 60, 20);
        parcouruField = new JTextField("--");
        parcouruField.setBounds(120, 285, 60, 22);
        parcouruField.setHorizontalAlignment(JTextField.RIGHT);
        parcouruField.setEditable(false);

        // Créé
        JLabel creeLabel = new JLabel("Créé");
        creeLabel.setBounds(40, 315, 60, 20);
        creeField = new JTextField("--");
        creeField.setBounds(120, 315, 60, 22);
        creeField.setHorizontalAlignment(JTextField.RIGHT);
        creeField.setEditable(false);

        // Ajout des composants à droite
        rightPanel.add(damesField);
        rightPanel.add(goButton);
        rightPanel.add(dfsButton);
        rightPanel.add(bfsButton);
        rightPanel.add(aStar1Button);
        rightPanel.add(aStar2Button);
        rightPanel.add(timeField);
        rightPanel.add(parcouruLabel);
        rightPanel.add(parcouruField);
        rightPanel.add(creeLabel);
        rightPanel.add(creeField);

        // Plateau initial
        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        rebuildBoard();
        setVisible(true);
    }

    private void updateResults() {
        if (controller.hasResult()) {
            timeField.setText(controller.getElapsedTimePretty());
            parcouruField.setText(String.valueOf(controller.getParcouru()));
            creeField.setText(String.valueOf(controller.getCree()));
        }
    }

    private void rebuildBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        
        // Créer toutes les cases
        for (int row = 0; row < boardSize; row++) {
            boolean white = (row % 2 == 0);
            for (int col = 0; col < boardSize; col++) {
                JPanel square = new JPanel();
                square.setPreferredSize(new Dimension(40, 40));
                square.setBackground(white ? Color.WHITE : Color.BLACK);
                boardPanel.add(square);
                white = !white;
            }
        }
        
        // Placer les reines si une solution existe
        if (controller.hasResult()) {
            int[] queens = controller.getQueens();
            if (queens.length > 0) {
                ImageIcon originalIcon = new ImageIcon("resources/crown-gold.png");
                Image img = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                ImageIcon queenIcon = new ImageIcon(img);
                
                for (int row = 0; row < queens.length; row++) {
                    int col = queens[row];
                    if (col >= 0 && col < boardSize) {
                        int idx = row * boardSize + col;
                        JPanel cell = (JPanel) boardPanel.getComponent(idx);
                        cell.setLayout(new BorderLayout());
                        cell.add(new JLabel(queenIcon), BorderLayout.CENTER);
                    }
                }
            }
        }
        
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QueensGUI::new);
    }
}