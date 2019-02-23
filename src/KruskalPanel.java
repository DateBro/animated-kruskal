import edu.princeton.cs.algs4.In;
import graphics.model.Code;
import graphics.model.GraphicsEdgeWeightedGraph;
import graphics.model.GraphicsKruskalMST;

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
    private int x_locate = 0;
    private int y_locate = 0;
    private static int N = 15;
    private static int speed = 300;
    private static boolean stopFlag =false;
    private static boolean pauseFlag = true;
    private static boolean resetFlag =false;
    private static boolean canGo=false;
    private static  int a[]=new int [N];
    private static int a2[] = new int[N];

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
        initCodes();

        initKruskalGraph("graphics/input/Tessellation.txt");

        GraphicsKruskalMST mst = new GraphicsKruskalMST(graphicsEdgeWeightedGraph);

        new PaintThread().start();
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

    public  void swap(int a [],int j,int i){
        int tmp = a[i];
        a[i]=a[j];
        a[j]=tmp;
    }

    private void pause(){
        while(pauseFlag){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        this.setBounds(x_locate, y_locate, width, height);
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

    private void initCodes() {
        codes = new Code[7];

        codes[0]=new Code(1060,590,530,30,"Sort E edges by increasing weight");
        codes[1]=new Code(1060,620,530,30,"T = {}");
        codes[2]=new Code(1060,650,530,30,"for (i = 0; i < edgeList.length; i++)");
        codes[3]=new Code(1060,680,530,30,"    if adding e = edgeList[i] does not form a cycle");
        codes[4]=new Code(1060,710,530,30,"        add e to T");
        codes[5] = new Code(1060, 740, 530, 30, "    else ignore e");
        codes[6] = new Code(1060, 770, 530, 30, "MST = T");
    }

    private void initKruskalGraph(String inputFileName) {
        In in = new In(inputFileName);
        graphicsEdgeWeightedGraph = new GraphicsEdgeWeightedGraph(in);
    }
}
