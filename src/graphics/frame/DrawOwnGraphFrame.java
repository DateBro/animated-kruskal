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

public class DrawOwnGraphFrame extends JFrame{
    private int height, width;
    private JMenuBar menuBar;
    private KruskalPanel kruskalPanel;
    private KruskalPanel backupKruskalPanel;
    private JComponent nowPanel;
    private NewGraphPanel newGraphPanel;
//    private JButton drawGraphButton;
//    private JButton resetButton;

    private boolean nomoreChange = false;

    public DrawOwnGraphFrame(int height, int width) {
        this.height = height;
        this.width = width;
        setTitle("Kruskal Animation");

        initFrame();
        initKruskalPanel();
        initBackupPanel();
        initNewGraphPanel();
//        initDrawGraphButton();
//        initResetButton();

        while (true) {
            StdOut.println("choices");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (nomoreChange)
                continue;

            if (nowPanel == kruskalPanel) {
                kruskalPanel.setVisible(true);
                backupKruskalPanel.setVisible(false);
                newGraphPanel.setVisible(false);
                kruskalPanel.normalInit();
                nomoreChange = true;
                kruskalPanel.algoStart();
            }
            if (nowPanel == backupKruskalPanel) {
                kruskalPanel.setVisible(false);
                newGraphPanel.setVisible(false);
                backupKruskalPanel.setVisible(true);
                nomoreChange = true;
                backupKruskalPanel.algoStart();
            }
            if (nowPanel == newGraphPanel) {
                kruskalPanel.setVisible(false);
                backupKruskalPanel.setVisible(false);
                newGraphPanel.setVisible(true);
                nomoreChange = true;
                newGraphPanel.waitDone();
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
        kruskalPanel.setVisible(true);
    }

    private void initBackupPanel() {
        backupKruskalPanel = new KruskalPanel(width, height, this);
        getContentPane().add(backupKruskalPanel);
        backupKruskalPanel.setVisible(false);
    }

    private void initNewGraphPanel() {
        newGraphPanel = new NewGraphPanel(width, height, this);
        getContentPane().add(newGraphPanel);
//        nowPanel = newGraphPanel;
        newGraphPanel.setVisible(false);
    }

//    private void initResetButton() {
//        resetButton = new JButton("重置权值");
//        resetButton.setBounds(300, 825, 160, 40);
//        resetButton.setContentAreaFilled(false);
//        resetButton.setForeground(Color.BLACK);
//        this.add(resetButton);
//        resetButton.addActionListener(e -> {
//            kruskalPanel.resetFlag = true;
//            this.changeKruskalPanel();
////            mainBoard.changeBackupPanel();
//        });
//    }

//    private void initDrawGraphButton() {
//        drawGraphButton = new JButton("绘制图表");
//        drawGraphButton.setBounds(100, 825, 160, 40);
//        drawGraphButton.setContentAreaFilled(false);
//        drawGraphButton.setForeground(Color.white);
//        this.add(drawGraphButton);
//        drawGraphButton.addActionListener(e -> {
//            this.changeNewGraphPanel();
//        });
//    }

    public void changeKruskalPanel() {
        kruskalPanel = new KruskalPanel(width, height, this);
        kruskalPanel.randomInit();
        kruskalPanel.setVisible(true);
        nowPanel = kruskalPanel;
    }

    public void changeBackupPanel() {
        nowPanel = backupKruskalPanel;
        backupKruskalPanel.randomInit();
        kruskalPanel.stopFlag = true;
        backupKruskalPanel.stopFlag = true;
//        kruskalPanel = new KruskalPanel(width, height, this);
//        kruskalPanel.setVisible(false);
//        kruskalPanel.initFlag();
        backupKruskalPanel.setStartFlag(true);
        backupKruskalPanel.setCanGoFlag(true);
        backupKruskalPanel.setPauseFlag(false);
        backupKruskalPanel.stopFlag = false;
        nomoreChange = false;
    }

    public void changeNewGraphPanel() {
        newGraphPanel = new NewGraphPanel(width, height, this);
        nomoreChange = false;
        nowPanel = newGraphPanel;
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
