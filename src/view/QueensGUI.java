package src.view;

import javax.swing.*;
import javax.imageio.ImageIO;
import src.control.SolverController;
import src.model.Board;
import src.model.SearchResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

public class QueensGUI extends JFrame {
    private JPanel boardPanel;
    private JSpinner sizeSpinner;
    private JRadioButton dfsRadio;
    private JRadioButton bfsRadio;
    private JRadioButton astarH1Radio;
    private JRadioButton astarH2Radio;
    private JRadioButton geneticRadio;
    private ButtonGroup algorithmGroup;
    private JLabel timeLabel;
    private JLabel parcoruLabel;
    private JLabel creeLabel;
    private JButton solveButton;
    private SolverController controller;
    private Board currentBoard;
    private int cellSize = 50;
    private BufferedImage crownImage;
    
    public QueensGUI() {
        controller = new SolverController(this);
        initComponents();
        loadCrownImage();
    }
    
    private void initComponents() {
        setTitle("Dame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(220, 220, 220));
        
        // Panel principal avec bordure
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(220, 220, 220));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Board Panel (gauche)
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        currentBoard = new Board(10);
        updateBoardSize();
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        
        // Control Panel (droite)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(220, 220, 220));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Size Spinner
        JPanel sizePanel = new JPanel();
        sizePanel.setBackground(new Color(220, 220, 220));
        sizePanel.setMaximumSize(new Dimension(250, 50));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(10, 4, 20, 1));
        sizeSpinner.setFont(new Font("Arial", Font.BOLD, 24));
        ((JSpinner.DefaultEditor) sizeSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        sizeSpinner.setPreferredSize(new Dimension(200, 45));
        sizeSpinner.addChangeListener(_ -> {
            int size = (Integer) sizeSpinner.getValue();
            currentBoard = new Board(size);
            updateBoardSize();
        });
        sizePanel.add(sizeSpinner);
        rightPanel.add(sizePanel);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Algorithm Radio Buttons
        algorithmGroup = new ButtonGroup();
        
        dfsRadio = createAlgorithmRadio("DFS", false);
        rightPanel.add(dfsRadio);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        bfsRadio = createAlgorithmRadio("BFS", false);
        rightPanel.add(bfsRadio);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        astarH1Radio = createAlgorithmRadio("A* - H1()", false);
        rightPanel.add(astarH1Radio);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        astarH2Radio = createAlgorithmRadio("A* - H2()", false);
        rightPanel.add(astarH2Radio);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        geneticRadio = createAlgorithmRadio("Génétique", true);
        rightPanel.add(geneticRadio);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Time Label
        JPanel timePanel = new JPanel();
        timePanel.setBackground(Color.WHITE);
        timePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        timePanel.setMaximumSize(new Dimension(250, 45));
        timePanel.setPreferredSize(new Dimension(230, 40));
        timeLabel = new JLabel("0.000 s", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timePanel.add(timeLabel);
        rightPanel.add(timePanel);
        
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Parcouru (Nodes Explored)
        parcoruLabel = new JLabel("0", SwingConstants.CENTER);
        parcoruLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel parcoruPanel = createStatPanel("Parcouru", parcoruLabel);
        rightPanel.add(parcoruPanel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Crée (Nodes Created)
        creeLabel = new JLabel("0", SwingConstants.CENTER);
        creeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel creePanel = createStatPanel("Crée", creeLabel);
        rightPanel.add(creePanel);

        
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        // Bouton Solve en bas
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(220, 220, 220));
        solveButton = new JButton("Résoudre");
        solveButton.setFont(new Font("Arial", Font.BOLD, 16));
        solveButton.setPreferredSize(new Dimension(150, 40));
        solveButton.addActionListener(_ -> solve());
        bottomPanel.add(solveButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        setSize(900, 700);
        setLocationRelativeTo(null);
    }
    
    private JRadioButton createAlgorithmRadio(String text, boolean selected) {
        JRadioButton radio = new JRadioButton(text);
        radio.setFont(new Font("Arial", Font.PLAIN, 16));
        radio.setBackground(new Color(220, 220, 220));
        radio.setSelected(selected);
        radio.setMaximumSize(new Dimension(250, 30));
        
        // Ajouter un indicateur circulaire vert pour la sélection
        radio.setIcon(new CircleIcon(20, Color.GRAY));
        radio.setSelectedIcon(new CircleIcon(20, new Color(100, 200, 100)));
        
        algorithmGroup.add(radio);
        return radio;
    }
    
    private JPanel createStatPanel(String label, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(220, 220, 220));
        panel.setMaximumSize(new Dimension(250, 40));
        
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(labelText, BorderLayout.WEST);
        
        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setBackground(Color.WHITE);
        valuePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        valuePanel.setPreferredSize(new Dimension(120, 35));
        valuePanel.add(valueLabel, BorderLayout.CENTER);
        panel.add(valuePanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void loadCrownImage() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("/ressources/crown-gold.png");
            
            if (imageStream != null) {
                crownImage = ImageIO.read(imageStream);
                imageStream.close();
            } else {
                System.err.println("Image crown-gold.png non trouvée dans /ressources/");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image: " + e.getMessage());
        }
    }
    
    private void updateBoardSize() {
        int size = currentBoard.getSize();
        cellSize = Math.min(500 / size, 50);
        boardPanel.setPreferredSize(new Dimension(cellSize * size, cellSize * size));
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    
    private void drawBoard(Graphics g) {
        if (currentBoard == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int size = currentBoard.getSize();
        
        // Dessiner l'échiquier
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if ((row + col) % 2 == 0) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
        
        // Dessiner les reines
        for (int row = 0; row < size; row++) {
            int col = currentBoard.getQueenColumn(row);
            if (col != -1) {
                int x = col * cellSize;
                int y = row * cellSize;
                
                if (crownImage != null) {
                    int imgSize = (int)(cellSize * 0.75);
                    int offset = (cellSize - imgSize) / 2;
                    g2d.drawImage(crownImage, x + offset, y + offset, imgSize, imgSize, boardPanel);
                } else {
                    // Fallback si l'image n'est pas chargée
                    g2d.setColor(new Color(255, 215, 0));
                    g2d.fillOval(x + cellSize/4, y + cellSize/4, cellSize/2, cellSize/2);
                }
            }
        }
    }
    
    private void solve() {
        int size = (Integer) sizeSpinner.getValue();
        
        if (dfsRadio.isSelected()) {
            controller.solveDFS(size);
        } else if (bfsRadio.isSelected()) {
            controller.solveBFS(size);
        } else if (astarH1Radio.isSelected()) {
            controller.solveAStarConflict(size);
        } else if (astarH2Radio.isSelected()) {
            controller.solveAStarDistance(size);
        } else if (geneticRadio.isSelected()) {
            controller.solveGenetic(size);
        }
    }
    
    public void displaySolution(SearchResult result) {
        if (result.isSuccess()) {
            currentBoard = result.getSolution();
            updateBoardSize();
            
            // Mettre à jour les statistiques
            timeLabel.setText(String.format("%.3f s", result.getTimeMillis() / 1000.0));
            parcoruLabel.setText(String.valueOf(result.getNodesExplored()));
            creeLabel.setText(String.valueOf(result.getNodesExplored() * 2)); // Estimation
        }
    }
    
    public void setStatus(String status) {
        // Peut être utilisé pour d'autres informations si nécessaire
    }
    
    // Classe interne pour l'icône circulaire des radio buttons
    private class CircleIcon implements Icon {
        private int size;
        private Color color;
        
        public CircleIcon(int size, Color color) {
            this.size = size;
            this.color = color;
        }
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.fillOval(x, y, size, size);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawOval(x, y, size, size);
            g2d.dispose();
        }
        
        @Override
        public int getIconWidth() {
            return size;
        }
        
        @Override
        public int getIconHeight() {
            return size;
        }
    }
}