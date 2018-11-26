import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bomb {
    protected Image bomb_1 = new ImageIcon("data/image/boom1.png").getImage();
    protected Image bomb_2 = new ImageIcon("data/image/boom2.png").getImage();
    protected Image bomb_3 = new ImageIcon("data/image/boom3.png").getImage();
    protected Image bombStatus = bomb_1;

    protected Image bomb_exploded_1 = new ImageIcon("data/image/bombbang_mid_2.png").getImage();

    protected Image explosion_horizontal_left_last_1 = new ImageIcon("data/image/bombbang_left_1.png").getImage();

    protected Image explosion_horizontal_right_last_1 = new ImageIcon("data/image/bombbang_right_1.png").getImage();

    protected Image explosion_vertical_top_last_1 = new ImageIcon("data/image/bombbang_up_1.png").getImage();

    protected Image explosion_vertical_down_last_1 = new ImageIcon("data/image/bombbang_down_1.png").getImage();

    protected boolean exploded = false;
    protected int timeCountingDown = 120;// thoi gian boom se no
    protected int effectTimeOfFlame = 20;// thoi gian boom no ton tai
    protected int east, west, south, north;
    protected int x, y;

    public Bomb(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void flameCollision(MainGame mainGame){
        int effectRange = mainGame.player.bombEffectRange;
        ArrayList<Rectangle> listRecangle = new ArrayList<Rectangle>();
        listRecangle.add(new Rectangle(x, y, 48,48));
        for(int i = 1; i <= east; i++){
            listRecangle.add(new Rectangle(x+i*48, y, 48,48));
        }
        for(int i = 1; i <= west; i++){
            listRecangle.add(new Rectangle(x-i*48, y, 48,48));
        }
        for(int i = 1; i <= south; i++){
            listRecangle.add(new Rectangle(x, y+i*48, 48,48));
        }
        for(int i = 1; i <= north; i++){
            listRecangle.add(new Rectangle(x, y-i*48, 48,48));
        }
        int n = listRecangle.size();
        for(Rectangle rectangle : listRecangle){
            if (rectangle.intersects(new Rectangle(mainGame.player.bX, mainGame.player.bY, 48, 48))){
                mainGame.player.motionStatus = 0;
                mainGame.player.isDead = true;
            }
            for (dragon bl : mainGame.ballooms){
                if (rectangle.intersects(new Rectangle(bl.bX, bl.bY, 48, 48))){
                    bl.motionStatus = 0;
                    bl.isDead = true;
                }
            }
            for (Xac_uop ol : mainGame.oneals){
                if (rectangle.intersects(new Rectangle(ol.bX, ol.bY, 48, 48))){
                    ol.motionStatus = 0;
                    ol.isDead = true;
                }
            }
            for (Bomb bomb : mainGame.player.bombs){
                if (bomb.timeCountingDown > 0){
                    if (rectangle.intersects(new Rectangle(bomb.x, bomb.y, 48, 48))){
                        bomb.timeCountingDown = 0;
                    }
                }
            }
        }
    }
}