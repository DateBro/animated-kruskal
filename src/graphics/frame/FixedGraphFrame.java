package graphics.frame;

import edu.princeton.cs.algs4.StdOut;
import graphics.model.GraphicsEdgeWeightedGraph;
import graphics.panel.KruskalPanel;
import graphics.panel.NewGraphPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author DateBro
 * @date 2019/2/22
 */

public class FixedGraphFrame extends JFrame{
    private int height, width;
    private JMenuBar menuBar;
    private KruskalPanel kruskalPanel;
    private KruskalPanel backupKruskalPanel;
    private JComponent nowPanel;
    private boolean nomoreChange = false;

    public FixedGraphFrame(int height, int width) {
        this.height = height;
        this.width = width;
        setTitle("Kruskal Animation");
        initFrame();
        initKruskalPanel();
        initBackupPanel();

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (nomoreChange)
                continue;

            if (nowPanel == kruskalPanel) {
                kruskalPanel.setVisible(true);
                backupKruskalPanel.setVisible(false);
                kruskalPanel.normalInit();
                nomoreChange = true;
                kruskalPanel.algoStart();
            }
            if (nowPanel == backupKruskalPanel) {
                kruskalPanel.setVisible(false);
                backupKruskalPanel.setVisible(true);
                nomoreChange = true;
                backupKruskalPanel.algoStart();
            }
        }
    }

    private void initFrame() {
        initGraphMenu();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screensize.width;
        int screenHeight = screensize.height;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
        setLayout(null);
        setVisible(true);
    }

    private void initKruskalPanel() {
        kruskalPanel = new KruskalPanel(width, height, this);
        getContentPane().add(kruskalPanel);
        nowPanel = kruskalPanel;
        kruskalPanel.drawGraphButton.setVisible(false);
        kruskalPanel.setVisible(true);
    }

    private void initBackupPanel() {
        backupKruskalPanel = new KruskalPanel(width, height, this);
        getContentPane().add(backupKruskalPanel);
        backupKruskalPanel.setVisible(false);
    }

    public void changeKruskalPanel() {
        kruskalPanel = new KruskalPanel(width, height, this);
        kruskalPanel.randomInit();
        kruskalPanel.setVisible(true);
        nowPanel = kruskalPanel;
    }

    public void changeBackupPanel() {
        nowPanel = backupKruskalPanel;
        backupKruskalPanel.randomInit();
        kruskalPanel = new KruskalPanel(width, height, this);
        kruskalPanel.setVisible(false);
        kruskalPanel.initFlag();
        backupKruskalPanel.setStartFlag(true);
        backupKruskalPanel.setCanGoFlag(true);
        backupKruskalPanel.setPauseFlag(false);
        backupKruskalPanel.algoStart();
    }

    public void changeBackupPanel(GraphicsEdgeWeightedGraph graph) {
        backupKruskalPanel.setGraphicsEdgeWeightedGraph(graph);
        nomoreChange = false;
        nowPanel = backupKruskalPanel;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private void initGraphMenu() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu graphChoices = new JMenu("图示");
        graphChoices.addSeparator();
        menuBar.add(graphChoices);
        JMenuItem Tessellation = new JMenuItem("Tessellation");
        JMenuItem Rail = new JMenuItem("Rail");
        JMenuItem k5 = new JMenuItem("k5");
        JMenuItem CP_4_14 = new JMenuItem("CP_4.14");
        JMenuItem CP_4_10 = new JMenuItem("CP_4.10");

        Tessellation.addActionListener(e -> {
            backupKruskalPanel.updateOriginGraph("graphics/input/Tessellation.txt");
            this.changeBackupPanel();
        });
        Rail.addActionListener(e -> {
            backupKruskalPanel.updateOriginGraph("graphics/input/Rail.txt");
            this.changeBackupPanel();
        });
        k5.addActionListener(e -> {
            backupKruskalPanel.updateOriginGraph("graphics/input/k5.txt");
            this.changeBackupPanel();
        });
        CP_4_14.addActionListener(e -> {
            backupKruskalPanel.updateOriginGraph("graphics/input/CP_4.14.txt");
            this.changeBackupPanel();
        });
        CP_4_10.addActionListener(e -> {
            backupKruskalPanel.updateOriginGraph("graphics/input/CP_4.10.txt");
            this.changeBackupPanel();
        });

        graphChoices.add(Tessellation);
        graphChoices.add(Rail);
        graphChoices.add(k5);
        graphChoices.add(CP_4_14);
        graphChoices.add(CP_4_10);
    }

    public static void main(String[] args) {
        new DrawOwnGraphFrame(920, 1600);
    }
}

