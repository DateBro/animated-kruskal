import graphics.model.GraphicsEdgeWeightedGraph;

import javax.swing.*;
import java.awt.*;

/**
 * @author DateBro
 * @date 2019/2/22
 */
public class Main extends JFrame{
    private int height, width;
    private JMenuBar menuBar;
    private KruskalPanel kruskalPanel;
    private KruskalPanel backupKruskalPanel;
    private JComponent nowPanel;
    private NewGraphPanel newGraphPanel;

    Main(int height, int width) {
        this.height = height;
        this.width = width;
        setTitle("数据结构课设");

        initFrame();
        initKruskalPanel();
        initBackupPanel();
        initNewGraphPanel();

        while (true){
            if (nowPanel == kruskalPanel) {
                kruskalPanel.setVisible(true);
                backupKruskalPanel.setVisible(false);
                newGraphPanel.setVisible(false);
                kruskalPanel.normalInit();
                kruskalPanel.algoStart();
            } else if (nowPanel == backupKruskalPanel) {
                kruskalPanel.setVisible(false);
                newGraphPanel.setVisible(false);
                backupKruskalPanel.randomInit();
                backupKruskalPanel.setVisible(true);
                backupKruskalPanel.algoStart();
            } else if (nowPanel == newGraphPanel) {
                kruskalPanel.setVisible(false);
                backupKruskalPanel.setVisible(false);
                newGraphPanel.setVisible(true);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
//        nowPanel = kruskalPanel;
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
        newGraphPanel.setVisible(false);
        nowPanel = newGraphPanel;
    }

    public void changeBackupPanel() {
        nowPanel = backupKruskalPanel;
    }

    public void changeNewGraphPanel() {
        nowPanel = newGraphPanel;
    }

    public void changeBackupPanel(GraphicsEdgeWeightedGraph graph) {
        backupKruskalPanel.setGraphicsEdgeWeightedGraph(graph);
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

        Tessellation.addActionListener(e -> kruskalPanel.setInitialGraphFile("graphics/input/Tessellation.txt"));
        Rail.addActionListener(e -> kruskalPanel.setInitialGraphFile("graphics/input/Rail.txt"));
        k5.addActionListener(e -> kruskalPanel.setInitialGraphFile("graphics/input/k5.txt"));
        CP_4_14.addActionListener(e -> kruskalPanel.setInitialGraphFile("graphics/input/CP_4.14.txt"));
        CP_4_10.addActionListener(e -> kruskalPanel.setInitialGraphFile("graphics/input/CP_4.10.txt"));

        graphChoices.add(Tessellation);
        graphChoices.add(Rail);
        graphChoices.add(k5);
        graphChoices.add(CP_4_14);
        graphChoices.add(CP_4_10);
    }

    public KruskalPanel getKruskalPanel() {
        return kruskalPanel;
    }

    public static void main(String[] args) {
        new Main(920, 1600);
    }
}
