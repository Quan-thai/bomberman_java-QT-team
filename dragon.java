import javax.swing.*;
import java.awt.*;

public class dragon extends Enemy {
    //up == left
    protected Image left_1 = new ImageIcon("data/image/boss_left_01.png").getImage();
    protected Image left_2= new ImageIcon("data/image/boss_left_02.png").getImage();
    protected Image left_3 = new ImageIcon("data/image/boss_left_03.png").getImage();
    //down == right
    protected Image right_1 = new ImageIcon("data/image/boss_right_01.png").getImage();
    protected Image right_2 = new ImageIcon("data/image/boss_right_02.png").getImage();
    protected Image right_3 = new ImageIcon("data/image/boss_right_03.png").getImage();

    protected Image dead = new ImageIcon("data/image/effect_03.png").getImage();
    //lưu trạng thái dragon
    protected Image dragonStatus = left_1;
    protected boolean isDead = false;
    public dragon(int x, int y){
        super(x,y);
    }

    public void move(Map map){
        int x;
        if (!staticEntitiesCollision(map)) {
            x = random.nextInt();
            if (x % 4 == 0) {
                x = random.nextInt() % 3;
                if (x < 0) x = -x;
                x++;
                speed = x;
            }
            motionStatus++;
            if (direction == 1) {
                bY -= speed;
                if (staticEntitiesCollision(map)) {
                    bY += speed;
                    motionStatus = 0;
                    do {
                        x = random.nextInt() % 4;
                        if (x < 0) x = -x;
                        x++;
                    } while (staticEntitiesCollision(map));
                    direction = x;
                }

            } else if (direction == 2) {
                bY += speed;
                if (staticEntitiesCollision(map)) {
                    bY -= speed;
                    motionStatus = 0;
                    do {
                        x = random.nextInt() % 4;
                        if (x < 0) x = -x;
                        x++;
                    } while (staticEntitiesCollision(map));
                    direction = x;
                }
            } else if (direction == 3) {
                bX -= speed;
                if (staticEntitiesCollision(map)) {
                    bX += speed;
                    motionStatus = 0;
                    do {
                        x = random.nextInt() % 4;
                        if (x < 0) x = -x;
                        x++;
                    } while (staticEntitiesCollision(map));
                    direction = x;
                }
            } else if (direction == 4) {
                bX += speed;
                if (staticEntitiesCollision(map)) {
                    bX -= speed;
                    motionStatus = 0;
                    do {
                        x = random.nextInt() % 4;
                        if (x < 0) x = -x;
                        x++;
                    } while (staticEntitiesCollision(map));
                    direction = x;
                }
            }
        }
    }

    public boolean  staticEntitiesCollision(Map map){

        Rectangle balloomBound = new Rectangle(bX, bY, 48, 48);
            if (direction == 1){
                int index = (bY/48)*map.width + bX/48;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index++;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }

            if (direction == 3){
                int index = (bY/48)*map.width + bX/48;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index += map.width;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }



            if (direction == 2){
                int index = (bY/48 + 1)*map.width + bX/48;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index++;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }

            if (direction == 4){
                int index = (bY/48)*map.width + bX/48 + 1;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index += map.width;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (balloomBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }

        return false;
    }
}