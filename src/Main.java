import javax.swing.*;
import java.awt.*;

/**
 * @author DateBro
 * @date 2019/2/22
 */
public class Main extends JFrame{
    private int height, width;

    Main(int height, int width) {
        this.height = height;
        this.width = width;
        setTitle("数据结构课设");

        initFrame();
        showKruskalMST();
    }

    private void initFrame() {
        //得到屏幕大小
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screensize.width;
        int screenHeight = screensize.height;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, width, height);
        setLayout(null);
        setVisible(true);
    }

    public void showKruskalMST() {
        KruskalPanel kruskalPanel = new KruskalPanel(width, height, this);
        getContentPane().add(kruskalPanel);
        kruskalPanel.setVisible(true);
    }

    public static void main(String[] args) {
        new Main(900, 1600);
    }
}
