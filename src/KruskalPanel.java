import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;
import graphics.model.*;
import utils.CodeArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author DateBro
 * @date 2019/2/22
 */
public class KruskalPanel extends JPanel {
    private int height;
    private int width;
    private int xLocate = 0;
    private int yLocate = 0;
    private static int speed = 300;
    private static boolean stopFlag = false;
    private static boolean pauseFlag = true;
    private static boolean resetFlag = false;
    private static boolean canGoFlag = false;

    private Main mainBoard;
    private JButton accelerateButton;
    private JButton decelerateButton;
    private JButton startOrPauseButton;
    private JButton drawGraphButton;
    private JButton resetButton;

    private Code[] codes;
    private GraphicsEdgeWeightedGraph graphicsEdgeWeightedGraph;

    public KruskalPanel(int width, int height, Main mainBoard) {
        this.mainBoard = mainBoard;
        this.height = height;
        this.width = width;

        initPanel();
        initAccelerateButton();
        initDecelerateButton();
        initPauseButton();
        initDrawGraphButton();
        initResetButton();
        initCodes("graphics/input/KruskalCodes.txt");

        canGoFlag = true;
        pauseFlag = false;

        new PaintThread().start();
    }

    public void algoStart(String kruskalGraphFile) {
        initKruskalGraph(kruskalGraphFile);

        Queue<WeightedLabeledEdge> mst;
        MinPQ<WeightedLabeledEdge> pq;
        mst = new Queue<>();
        pq = new MinPQ<>();
        for (WeightedLabeledEdge e : graphicsEdgeWeightedGraph.edges())
            pq.insert(e);
        UF uf = new UF(graphicsEdgeWeightedGraph.V());

        while (true) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (canGoFlag) {
                // 算法的主要部分
                while (!pq.isEmpty()) {
                    while (pauseFlag) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (stopFlag || resetFlag)
                            break;
                    }
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (stopFlag || resetFlag)
                        break;

                    WeightedLabeledEdge e = pq.delMin();
                    NumberLabeledVertex labeledV = e.eitherLabeledVertex(), labeledW = e.otherLabeledVertex(labeledV);
                    int v = labeledV.getValue();
                    int w = labeledW.getValue();
                    if (!uf.connected(v, w)) {
                        uf.union(v, w);
                        mst.enqueue(e);
                        labeledV.setBorderColor(NumberLabeledVertex.RED);
                        labeledW.setBorderColor(NumberLabeledVertex.RED);
                        e.setGraphicsColor(WeightedLabeledEdge.RED);
                    } else {
                        e.setGraphicsColor(WeightedLabeledEdge.GRAY);
                    }
                }

                if (stopFlag) {
                    stopFlag = false;
                    canGoFlag = false;
                    break;
                }
                if (resetFlag)
                    resetFlag = false;
                canGoFlag = false;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        for (Code code : codes) {
            code.paintCode(g2);
        }

        g2.setColor(Color.BLACK);
        graphicsEdgeWeightedGraph.paintKruskalGraph(g2);

        Stroke s = g2.getStroke();
        g2.setColor(Color.BLACK);
        g2.fillRect(0,820,1600,80);
        g2.setStroke(s);

        g2.setColor(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Font font1 = new Font("微软雅黑", Font.BOLD, 18);
        g2.setFont(font1);
        g2.drawString("速度 : " + (70 - speed / 10), 850, 850);
        g2.setStroke(s);
    }

    private class PaintThread extends Thread{
        @Override
        public void run() {
            while(true){
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void initPanel() {
        this.setLayout(null);
        this.setBounds(xLocate, yLocate, width, height);
        this.setBackground(Color.white);
        this.setVisible(false);
        this.setFocusable(true);
    }

    private void initAccelerateButton() {
        accelerateButton = new JButton("加速");
        accelerateButton.setBounds(600, 825, 80, 40);
        accelerateButton.setContentAreaFilled(false);
        accelerateButton.setForeground(Color.white);
        this.add(accelerateButton);
        accelerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (speed > 50) {
                    speed -= 50;
                }
                System.out.println("accelerateButton now speed : " + speed);
            }
        });
    }

    private void initDecelerateButton() {
        decelerateButton =new JButton("减速");
        decelerateButton.setBounds(700,825,80,40);
        decelerateButton.setContentAreaFilled(false);
        decelerateButton.setForeground(Color.white);
        this.add(decelerateButton);
        decelerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(speed<600){
                    speed+=50;
                }
                System.out.println("decelerateButton now speed : "+speed);
            }
        });
    }

    private void initPauseButton() {
        startOrPauseButton = new JButton("开始");
        startOrPauseButton.setBounds(500,825,80,40);
        startOrPauseButton.setContentAreaFilled(false);
        startOrPauseButton.setForeground(Color.white);
        this.add(startOrPauseButton);
        startOrPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseFlag) {
                    pauseFlag = false;
                    startOrPauseButton.setText("暂停");
                } else {
                    pauseFlag = true;
                    startOrPauseButton.setText("开始");
                }
            }
        });
    }

    private void initResetButton() {
        resetButton = new JButton("重置");
        resetButton.setBounds(400,825,80,40);
        resetButton.setContentAreaFilled(false);
        resetButton.setForeground(Color.white);
        this.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFlag=true;
            }
        });
    }

    private void initDrawGraphButton() {
        drawGraphButton = new JButton("绘制图表");
        drawGraphButton.setBounds(200, 825, 160, 40);
        drawGraphButton.setContentAreaFilled(false);
        drawGraphButton.setForeground(Color.white);
        this.add(drawGraphButton);
        drawGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 绘制图表相关
                System.out.println("绘制图表");
            }
        });
    }

    private void initCodes(String inputFileName) {
        In in = new In(inputFileName);
        CodeArray codeArray = new CodeArray(in);
        codes = codeArray.codes();
    }

    private void initKruskalGraph(String inputFileName) {
        In in = new In(inputFileName);
        graphicsEdgeWeightedGraph = new GraphicsEdgeWeightedGraph(in);
    }
}
