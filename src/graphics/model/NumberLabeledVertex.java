package graphics.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * @author DateBro
 * @date 2019/2/22
 */
public class NumberLabeledVertex {

    private static final double vertexRadius = 40;
    public static final int YELLOW = 1;
    public static final int RED = 2;
    public static final int BLUE = 3;
    public static final int GREEN = 4;
    public static final int BLACK = 5;
    public static final int GLASS_GREEN = 6;
    public static final int YELLOW_2 = 7;
    public static final int WHITE = 8;
    private int borderColor = BLACK;
    private Ellipse2D innerOval;

    // x,y 代表绘制圆形时其矩形左上角坐标
    private double x, y;
    private double centerX, centerY;
    private int value;

    public NumberLabeledVertex(double centerX, double centerY, int value) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.value = value;

        this.x = centerX - vertexRadius / 2;
        this.y = centerY - vertexRadius / 2;
    }

    public void paintVertex(Graphics2D g2) {
        Stroke s = g2.getStroke();

        initPaintSetting(borderColor, g2, s);
        innerOval = new Ellipse2D.Double(x, y, vertexRadius, vertexRadius);
        Stroke ovalBorderStroke = new BasicStroke(3.0f);
        g2.setStroke(ovalBorderStroke);
        g2.draw(innerOval);

        initPaintSetting(borderColor, g2, s);
        Font font1 = new Font("微软雅黑", Font.PLAIN, 18);
        g2.setFont(font1);
        String strToDraw = value + "";
        if (value >= 100) {
            g2.drawString(strToDraw, (float) x +3, (float) y + 26);
        } else if (value < 10) {
            g2.drawString(strToDraw, (float) x + 13, (float) y + 26);
        } else {
            g2.drawString(strToDraw, (float) x + 8, (float) y+ 26);
        }
    }

    private void initPaintSetting(int color, Graphics2D g2, Stroke s) {
        setPaintColor(color, g2);
        g2.setStroke(s);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
            case GLASS_GREEN:
                g2.setColor(new Color(82, 188, 105));
                break;
            case YELLOW_2:
                g2.setColor(new Color(255, 138, 39));
                break;
            case WHITE:
                g2.setColor(Color.WHITE);
                break;
        }
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getValue() {
        return value;
    }

    public double getRadius() {
        return vertexRadius / 2;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public boolean contains(Point2D p) {
        if (p == null || innerOval == null) {
            return false;
        } else {
            return innerOval.contains(p);
        }
    }

    public void setLocation(double newX, double newY) {
        this.centerX = newX;
        this.centerY = newY;

        this.x = centerX - vertexRadius / 2;
        this.y = centerY - vertexRadius / 2;
    }
}

