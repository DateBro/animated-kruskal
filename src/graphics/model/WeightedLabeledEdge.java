package graphics.model;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/**
 * @author DateBro
 * @date 2019/2/22
 */

public class WeightedLabeledEdge implements Comparable<WeightedLabeledEdge> {

    public static final int YELLOW = 1;
    public static final int RED = 2;
    public static final int BLUE = 3;
    public static final int GREEN = 4;
    public static final int BLACK = 5;
    public static final int GRAY = 6;

    private final int v;
    private final int w;
    private int weight;
    private int graphicsColor = BLACK;
    private NumberLabeledVertex labeledV;
    private NumberLabeledVertex labeledW;

    public WeightedLabeledEdge(NumberLabeledVertex labeledV, NumberLabeledVertex labeledW, int weight) {
        this.labeledV = labeledV;
        this.labeledW = labeledW;
        v = labeledV.getValue();
        w = labeledW.getValue();
        this.weight = weight;
    }

    public NumberLabeledVertex eitherLabeledVertex() {
        return labeledV;
    }

    public NumberLabeledVertex otherLabeledVertex(NumberLabeledVertex labeledVertex) {
        if (labeledVertex == labeledV) {
            return labeledW;
        } else if (labeledVertex == labeledW) {
            return labeledV;
        } else {
            throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    public void paintEdge(Graphics2D g2) {
        Stroke s = g2.getStroke();
        setPaintColor(graphicsColor, g2);

        g2.setStroke(s);
        drawWeightLabeledLine(g2, labeledV, labeledW);
    }

    public void setGraphicsColor(int color) {
        graphicsColor = color;
    }

    private void drawWeightLabeledLine(Graphics2D g2, NumberLabeledVertex v, NumberLabeledVertex w) {
        Stroke s = g2.getStroke();

        double radius = labeledV.getRadius();
        double x1 = labeledV.getCenterX();
        double y1 = labeledV.getCenterY();
        double x2 = labeledW.getCenterX();
        double y2 = labeledW.getCenterY();

        double weightX1 = x1;
        double weightY1 = y1;
        double weightX2 = x2;
        double weightY2 = y2;
        int weightYOffset = -3;
        double rotateAngle = 0;

        if (x2 == x1 && y1 < y2) {
            y1 += radius;
            y2 -= radius;
            rotateAngle = +Math.PI / 2;
        } else if (x2 == x1 && y1 > y2) {
            y1 -= radius;
            y2 += radius;
            rotateAngle = +Math.PI / 2;
            weightX1 = x2;
            weightY1 = y2;
            weightX2 = x1;
            weightY2 = y1;
        } else if (y1 == y2 && x1 < x2) {
            x1 += radius;
            x2 -= radius;
            rotateAngle = 0;
        } else if (y1 == y2 && x1 > x2) {
            x1 -= radius;
            x2 += radius;
            rotateAngle = 0;
            weightX1 = x2;
            weightY1 = y2;
            weightX2 = x1;
            weightY2 = y1;
        } else {
            double slopeAngle = Math.atan((y2 - y1) / (x2 - x1));
            double slopeSin = Math.sin(slopeAngle);
            double slopeCos = Math.cos(slopeAngle);

            if (x1 > x2) {
                x1 -= radius * slopeCos;
                y1 -= radius * slopeSin;
                x2 += radius * slopeCos;
                y2 += radius * slopeSin;
                weightX1 = x2;
                weightY1 = y2;
                weightX2 = x1;
                weightY2 = y1;
            } else {
                x1 += radius * slopeCos;
                y1 += radius * slopeSin;
                x2 -= radius * slopeCos;
                y2 -= radius * slopeSin;
            }

            rotateAngle = slopeAngle;
        }
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        Stroke lineStroke = new BasicStroke(3.0f);
        g2.setStroke(lineStroke);
        g2.draw(line);
        g2.setStroke(s);

        drawWeightLabel(g2, rotateAngle, weightYOffset, weightX1, weightY1, weightX2, weightY2);
    }

    private void drawWeightLabel(Graphics2D g2, double rotateAngle, int yOffset, double x1, double y1, double x2, double y2) {
        String str = weight + "";
        double xMid = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)) / 2;

        AffineTransform originalStatus = g2.getTransform();
        g2.translate(x1, y1);
        g2.rotate(rotateAngle);
        g2.drawString(str, (float) xMid, (float) yOffset);
        g2.setTransform(originalStatus);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int newWeight) {
        weight = newWeight;
    }

    private void setPaintColor(int color, Graphics2D g2) {
        switch (color) {
            case YELLOW:
                g2.setColor(Color.yellow);
                break;
            case RED:
                g2.setColor(Color.red);
                break;
            case BLUE:
                g2.setColor(new Color(75, 101, 186));
                break;
            case BLACK:
                g2.setColor(Color.BLACK);
                break;
            case GREEN:
                g2.setColor(Color.GREEN);
                break;
            case GRAY:
                g2.setColor(Color.LIGHT_GRAY);
        }
    }

    @Override
    public int compareTo(WeightedLabeledEdge that) {
        return Double.compare(this.getWeight(), that.getWeight());
    }
}
