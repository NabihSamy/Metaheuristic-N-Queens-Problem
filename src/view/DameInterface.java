package src.view;
import javax.swing.*;
import java.awt.*;

public class DameInterface extends JFrame {
    private JPanel boardPanel;
    private JTextField damesField;
    private int boardSize = 10; // taille initiale

    public DameInterface() {
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

        // Bouton "Done"
        JButton doneButton = new JButton("GO");
        doneButton.setFont(new Font("Arial", Font.ITALIC, 14));
        doneButton.setBounds(75, 65, 60, 25);

        // Les deux actions font la même chose
        Runnable applySizeChange = () -> {
            try {
                int newSize = Integer.parseInt(damesField.getText());
                if (newSize >= 4 && newSize <= 20) {
                    boardSize = newSize;
                    rebuildBoard();
                } else {
                    JOptionPane.showMessageDialog(this, "Entrez une taille entre 4 et 20.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
            }
        };

        doneButton.addActionListener(e -> applySizeChange.run());
        damesField.addActionListener(e -> applySizeChange.run());

        // Sélection d'algo (boutons radio)
        JRadioButton dfsButton = new JRadioButton("DFS");
        dfsButton.setBounds(40, 100, 110, 25);
        JRadioButton bfsButton = new JRadioButton("BFS");
        bfsButton.setBounds(40, 130, 110, 25);
        JRadioButton aStar1Button = new JRadioButton("A* - H1()");
        aStar1Button.setBounds(40, 160, 110, 25);
        JRadioButton aStar2Button = new JRadioButton("A* - H2()");
        aStar2Button.setBounds(40, 190, 110, 25);
        aStar1Button.setSelected(true);

        ButtonGroup algoGroup = new ButtonGroup();
        algoGroup.add(dfsButton);
        algoGroup.add(bfsButton);
        algoGroup.add(aStar1Button);
        algoGroup.add(aStar2Button);

        // Temps affiché
        JTextField timeField = new JTextField("0.034 s");
        timeField.setBounds(50, 235, 110, 35);
        timeField.setHorizontalAlignment(JTextField.CENTER);
        timeField.setFont(new Font("Arial", Font.BOLD, 18));
        timeField.setEditable(false);

        // Parcouru
        JLabel parcouruLabel = new JLabel("Parcouru");
        parcouruLabel.setBounds(40, 285, 60, 20);
        JTextField parcouruField = new JTextField("241");
        parcouruField.setBounds(120, 285, 60, 22);
        parcouruField.setHorizontalAlignment(JTextField.RIGHT);
        parcouruField.setEditable(false);

        // Créé
        JLabel creeLabel = new JLabel("Créé");
        creeLabel.setBounds(40, 315, 60, 20);
        JTextField creeField = new JTextField("2400");
        creeField.setBounds(120, 315, 60, 22);
        creeField.setHorizontalAlignment(JTextField.RIGHT);
        creeField.setEditable(false);

        // Ajout des composants à droite
        rightPanel.add(damesField);
        rightPanel.add(doneButton);
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

    private void rebuildBoard() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
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
        // Placement dames exemple
        int placedQueens = Math.min(boardSize, 10);
        ImageIcon originalIcon = new ImageIcon("resources/crown-gold.png");
        Image img = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon queenIcon = new ImageIcon(img);
        for (int i = 0; i < placedQueens; i++) {
            int row = i, col = (2 * i) % boardSize;
            int idx = row * boardSize + col;
            JPanel cell = (JPanel) boardPanel.getComponent(idx);
            cell.setLayout(new BorderLayout());
            cell.add(new JLabel(queenIcon), BorderLayout.CENTER);
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DameInterface::new);
    }
}
