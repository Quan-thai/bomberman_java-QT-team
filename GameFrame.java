import java.awt.*;
import javax.swing.*;

// Tạo ra khung để chưa panel (panel là nơi vẽ các ảnh của game)
public class GameFrame extends JFrame {

    public MainGame MGame = new MainGame();
    public infoBar Bar;

    public GameFrame() {
        initUI();
    }

    private void initUI() {
        Bar = new infoBar(MGame);
        MGame.setInfoBar(Bar);
        add(Bar, BorderLayout.PAGE_START);
        add(MGame);
        setTitle("Bomber");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new GameFrame();
            }
        });
    }
}