package graphics.model;

import java.awt.*;

/**
 * @author DateBro
 * @date 2019/2/22
 */
public class Code {

    public static final int YELLOW = 1;
    public static final int RED = 2;
    public static final int BLUE = 3;
    public static final int GREEN = 4;
    public static final int BLACK = 5;
    public static final int GLASS_GREEN = 6;
    public static final int YELLOW2 = 7;

    private int height, width, x, y;
    private int color;
    private String codeStr;

    public Code(int x, int y, int width, int height, String codeStr) {
        this.x = x;
        this.codeStr = codeStr;
        this.width = width;
        this.height = height;
        this.y = y;
        this.color = GLASS_GREEN;
    }

    public void paintCode(Graphics2D g) {
        Stroke s = g.getStroke();
        setPaintColor(color,g);

        // 绘制代码背景颜色
        g.fillRect(x, y, width, height);
        g.setStroke(s);

        // 绘制代码文字
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font1 = new Font("微软雅黑", Font.BOLD, 20);
        g.setFont(font1);
        g.drawString(codeStr, x + 35, y + 20);
    }

    private void setPaintColor(int color,Graphics2D g2) {
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
            case YELLOW2:
                g2.setColor(new Color(254, 197, 21));
                break;
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setCodeStr(String str) {
        codeStr = str;
    }
}
