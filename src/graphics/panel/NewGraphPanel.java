package graphics.panel;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
import graphics.frame.Main;
import graphics.model.Code;
import graphics.model.GraphicsEdgeWeightedGraph;
import graphics.model.NumberLabeledVertex;
import graphics.model.WeightedLabeledEdge;
import utils.CodeArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author DateBro
 * @date 2019/2/25
 */

public class NewGraphPanel extends JPanel {
    private int height;
    private int width;
    private int xLocate = 0;
    private int yLocate = 0;
    private Main mainBoard;
    private JButton doneButton;
    private Stack<NumberLabeledVertex> selectedVertex;
    private ArrayList<NumberLabeledVertex> vertexArrayList;
    private NumberLabeledVertex currentVertex;
    private ArrayList<WeightedLabeledEdge> edgeArrayList;
    private int currentValue;
    private Code[] codes;
    public boolean doneFlag = false;

    public NewGraphPanel(int width, int height, Main mainBoard) {
        this.mainBoard = mainBoard;
        this.height = height;
        this.width = width;
        initPanel();
        initDoneButton();
        initCodes();
        reInit();
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler());
        new PaintThread().start();
    }

    public void waitDone() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (doneFlag) {
                break;
            }
        }
    }

    public void reInit() {
        vertexArrayList = new ArrayList<>();
        edgeArrayList = new ArrayList<>();
        selectedVertex = new Stack<>();
        currentVertex = null;
        currentValue = 0;
        new PaintThread().start();
    }

    private void initPanel() {
        this.setLayout(null);
        this.setBounds(xLocate, yLocate, width, height);
        this.setBackground(Color.white);
        this.setVisible(false);
        this.setFocusable(true);
    }

    private void initDoneButton() {
        doneButton = new JButton("完成");
        doneButton.setBounds(400, 825, 80, 40);
        doneButton.setContentAreaFilled(false);
        doneButton.setForeground(Color.white);
        this.add(doneButton);
        doneButton.addActionListener(e -> {
            doneFlag = true;
            GraphicsEdgeWeightedGraph graphicsEdgeWeightedGraph = new GraphicsEdgeWeightedGraph(vertexArrayList, edgeArrayList);
            mainBoard.changeBackupPanel(graphicsEdgeWeightedGraph);
        });
    }

    private void initCodes() {
        In in = new In("graphics/input/KruskalCodes.txt");
        CodeArray codeArray = new CodeArray(in);
        codes = codeArray.codes();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        for (NumberLabeledVertex v : vertexArrayList)
            v.paintVertex(g2);
        for (WeightedLabeledEdge e : edgeArrayList)
            e.paintEdge(g2);
        for (Code code : codes)
            code.paintCode(g2);

        Stroke s = g2.getStroke();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 820, 1600, 80);
        g2.setStroke(s);
    }

    public NumberLabeledVertex find(Point2D p) {
        for (NumberLabeledVertex v : vertexArrayList)
            if (v.contains(p))
                return v;
        return null;
    }

    public void addVertex(Point2D p) {
        double x = p.getX();
        double y = p.getY();

        currentVertex = new NumberLabeledVertex(x, y, currentValue++);
        vertexArrayList.add(currentVertex);
        repaint();
    }

    public void remove(NumberLabeledVertex vertex) {
        if (vertex == null)
            return;
        if (vertex == currentVertex)
            currentVertex = null;
        vertexArrayList.remove(vertex);
        currentValue--;
        repaint();
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            currentVertex = find(event.getPoint());
            if (currentVertex == null)
                addVertex(event.getPoint());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            currentVertex = find(e.getPoint());
            if (currentVertex != null && e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1)
                remove(currentVertex);
            if (currentVertex == null && e.getClickCount() == 2) {
                while (!selectedVertex.isEmpty())
                    selectedVertex.pop();
            } else if (currentVertex != null && e.getClickCount() == 2) {
                setSelectedVertex(currentVertex);
            }
        }

        private void setSelectedVertex(NumberLabeledVertex vertex) {
            if (selectedVertex.size() >= 2) {
                NumberLabeledVertex vertex1 = selectedVertex.pop();
                vertex1.setBorderColor(NumberLabeledVertex.BLACK);
                NumberLabeledVertex vertex2 = selectedVertex.pop();
                vertex2.setBorderColor(NumberLabeledVertex.BLACK);

                selectedVertex.push(vertex);
                vertex.setBorderColor(NumberLabeledVertex.RED);
                repaint();
            } else if (selectedVertex.size() == 1) {
                NumberLabeledVertex vertex1 = selectedVertex.pop();
                WeightedLabeledEdge edge = new WeightedLabeledEdge(vertex, vertex1, StdRandom.uniform(1, 21));
                edgeArrayList.add(edge);
                vertex1.setBorderColor(NumberLabeledVertex.BLACK);

                repaint();
            } else {
                selectedVertex.push(vertex);
                vertex.setBorderColor(NumberLabeledVertex.RED);
                repaint();
            }
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentVertex != null) {
                int x = e.getX();
                int y = e.getY();
                currentVertex.setLocation(x, y);
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (find(e.getPoint()) == null) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
