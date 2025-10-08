package src.view;

import javax.swing.*;
import javax.imageio.ImageIO;
import src.control.SolverController;
import src.model.Board;
import src.model.SearchResult;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QueensGUI extends JFrame {
    private JPanel boardPanel;
    private JComboBox<Integer> sizeCombo;
    private JButton dfsButton;
    private JButton bfsButton;
    private JButton astarConflictButton;
    private JButton astarDistanceButton;
    private JLabel statusLabel;
    private SolverController controller;
    private Board currentBoard;
    private int cellSize = 60;
    private BufferedImage crownImage;
    
    public QueensGUI() {
        controller = new SolverController(this);
        initComponents();
        loadCrownImage();
    }
    
    private void initComponents() {
        setTitle("N-Queens Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Board Panel
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        currentBoard = new Board(8);
        updateBoardSize();
        add(boardPanel, BorderLayout.CENTER);
        
        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        controlPanel.add(new JLabel("Taille:"));
        sizeCombo = new JComboBox<>(new Integer[]{4, 5, 6, 7, 8, 9, 10, 12});
        sizeCombo.setSelectedItem(8);
        sizeCombo.addActionListener(_ -> {
            int size = (Integer) sizeCombo.getSelectedItem();
            currentBoard = new Board(size);
            updateBoardSize();
        });
        controlPanel.add(sizeCombo);
        
        dfsButton = new JButton("DFS");
        dfsButton.addActionListener(_ -> {
            int size = (Integer) sizeCombo.getSelectedItem();
            controller.solveDFS(size);
        });
        controlPanel.add(dfsButton);
        
        bfsButton = new JButton("BFS");
        bfsButton.addActionListener(_ -> {
            int size = (Integer) sizeCombo.getSelectedItem();
            controller.solveBFS(size);
        });
        controlPanel.add(bfsButton);
        
        astarConflictButton = new JButton("A* (Conflits)");
        astarConflictButton.addActionListener(_ -> {
            int size = (Integer) sizeCombo.getSelectedItem();
            controller.solveAStarConflict(size);
        });
        controlPanel.add(astarConflictButton);
        
        astarDistanceButton = new JButton("A* (Distance)");
        astarDistanceButton.addActionListener(_ -> {
            int size = (Integer) sizeCombo.getSelectedItem();
            controller.solveAStarDistance(size);
        });
        controlPanel.add(astarDistanceButton);
        
        statusLabel = new JLabel("Prêt");
        controlPanel.add(statusLabel);
        
        add(controlPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void loadCrownImage() {
        try {
            crownImage = ImageIO.read(new File("resources/crown-gold.png"));
        } catch (IOException e) {
            System.err.println("Image crown-gold.png non trouvée");
        }
    }
    
    private void updateBoardSize() {
        int size = currentBoard.getSize();
        cellSize = Math.min(600 / size, 80);
        boardPanel.setPreferredSize(new Dimension(cellSize * size, cellSize * size));
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    
    private void drawBoard(Graphics g) {
        if (currentBoard == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int size = currentBoard.getSize();
        
        // Draw chessboard
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if ((row + col) % 2 == 0) {
                    g2d.setColor(new Color(240, 217, 181));
                } else {
                    g2d.setColor(new Color(181, 136, 99));
                }
                g2d.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
        
        // Draw queens
        for (int row = 0; row < size; row++) {
            int col = currentBoard.getQueenColumn(row);
            if (col != -1) {
                int x = col * cellSize;
                int y = row * cellSize;
                
                if (crownImage != null) {
                    int imgSize = (int)(cellSize * 0.8);
                    int offset = (cellSize - imgSize) / 2;
                    g2d.drawImage(crownImage, x + offset, y + offset, imgSize, imgSize, boardPanel);
                } else {
                    g2d.setColor(Color.RED);
                    g2d.fillOval(
                        x + cellSize / 4,
                        y + cellSize / 4,
                        cellSize / 2,
                        cellSize / 2
                    );
                }
            }
        }
    }
    
    public void displaySolution(SearchResult result) {
        if (result.isSuccess()) {
            currentBoard = result.getSolution();
            updateBoardSize();
        }
    }
    
    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}
