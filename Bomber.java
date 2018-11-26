import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.ResponseCache;
import java.util.ArrayList;

// Nhân vật bomber
public class Bomber{
    protected int bX, bY, dX, dY; // toạ độ của nhân vật
    protected int  speed = 4; // tốc độ di chuyển
    protected int motionStatus = 0; //quyết định hình của nhân vật sẽ đc vẽ lên panel
    protected int direction = 2; // hướng di chuyển
    //các hình ảnh về chuyển động của nhân vật
    //up: direction = 1;
    public Image up_1 = new ImageIcon("data/image/player_up_1.png").getImage();
    public Image up_2 = new ImageIcon("data/image/player_up_3.png").getImage();
    public Image up_3 = new ImageIcon("data/image/player_up_5.png").getImage();
    //down: direction = 2;
    public Image down_1 = new ImageIcon("data/image/player_down_1.png").getImage();
    public Image down_2 = new ImageIcon("data/image/player_down_2.png").getImage();
    public Image down_3 = new ImageIcon("data/image/player_down_4.png").getImage();
    //left: direction = 3;
    public Image left_1 = new ImageIcon("data/image/player_left_1.png").getImage();
    public Image left_2 = new ImageIcon("data/image/player_left_2.png").getImage();
    public Image left_3 = new ImageIcon("data/image/player_left_3.png").getImage();
    //right: direction = 4;
    public Image right_1 = new ImageIcon("data/image/player_right_1.png").getImage();
    public Image right_2 = new ImageIcon("data/image/player_right_2.png").getImage();
    public Image right_3 = new ImageIcon("data/image/player_right_3.png").getImage();
    //dead
    public Image dead_1 = new ImageIcon("data/image/effect_08.png").getImage();
    public Image dead_2 = new ImageIcon("data/image/effect_10.png").getImage();
    public Image dead_3 = new ImageIcon("data/image/effect_11.png").getImage();
    //lưu trạng thái nhân vật
    public Image bomberStatus = down_1;
    public ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    public int bombQuantity = 1;
    protected int bombEffectRange = 1;
    public boolean isDead = false;
    private boolean isMoving = false;
    private boolean throughBomb = false;

    public Bomber(int x, int y){
        bX = x;
        bY = y;
    }

    //kiểm tra va chạm với các vật thế đứng yên: wall, brick, xử lý va chạm
    public boolean staticEntitiesCollision(Map map, int motionDelay){
        int motion = motionStatus % motionDelay;
        Rectangle bomberBound = new Rectangle();
        if (direction == 1){
            if (motion < motionDelay/2){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (motion >= motionDelay/2){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            int index = (bY/48)*map.width + bX/48;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index+1).type != '#' && map.data.get(index+1).type != '*' && map.data.get(index).type != 'b' && bX % 48 >= 35)
                    bX += speed;
                    return true;
                }
            }
            index++;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index-1).type != '#' && map.data.get(index-1).type != '*' && bX % 48 <= 35)
                    bX -= speed;
                    return true;
                }
            }
        }

        if (direction == 2){
            if (motion < motionDelay/2){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (motion >= motionDelay/2){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            int index = (bY/48 + 1)*map.width + bX/48;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index+1).type != '#' && map.data.get(index+1).type != '*' && map.data.get(index).type != 'b' && bX % 48 >= 35)
                    bX += speed;
                    return true;
                }
            }
            index++;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index-1).type != '#' && map.data.get(index-1).type != '*' && bX % 48 <= 35)
                    bX -= speed;
                    return true;
                }
            }
        }

        if (direction == 3){
            if (motion <= motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (motionDelay/4 < motion && motion <= 2*motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (2*motionDelay/4 < motion && motion <= 3*motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (motion > 3*motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            int index = (bY/48)*map.width + bX/48;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index+map.width).type != '#' && map.data.get(index+map.width).type != '*' && map.data.get(index).type != 'b' && bY % 48 >= 35)
                    bY += speed;
                    return true;
                }
            }
            index += map.width;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index-map.width).type != '#' && map.data.get(index-map.width).type != '*' && bY % 48 <= 18)
                    bY -= speed;
                    return true;
                }
            }
        }

        if (direction == 4){
            if (motion <= motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (motionDelay/4 < motion && motion <= 2*motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (2*motionDelay/4 < motion && motion <= 3*motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            else if (motion > 3*motionDelay/4){
                bomberBound = new Rectangle(bX, bY, 42, 42);
            }
            int index = (bY/48)*map.width + bX/48 + 1;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index+map.width).type != '#' && map.data.get(index+map.width).type != '*' && map.data.get(index).type != 'b' && bY % 48 >= 35)
                    bY += speed;
                    return true;
                }
            }
            index += map.width;
            if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || (map.data.get(index).type == 'b' && !throughBomb)){
                if (bomberBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) {
                    if (map.data.get(index-map.width).type != '#' && map.data.get(index-map.width).type != '*' && bY % 48 <= 18)
                    bY -= speed;
                    return true;
                }
            }
        }
        return false;
    }
    // setup key di chuyển : nhập vào
    public void keyPressed(KeyEvent e, Map map, int MOTION_DELAY) {
        int key = e.getKeyCode();
        isMoving = true;
        switch (key) {
            case KeyEvent.VK_UP:
                if (direction == 4){
                    bX += speed;
                    if (staticEntitiesCollision(map, MOTION_DELAY)){
                       bX -= 4+speed;
                    }else{
                        bX -= speed;
                    }
                }
                direction = 1;
                dY = -speed;
                dX = 0;
                break;
            case KeyEvent.VK_DOWN:
                if (direction == 4){
                    bX += speed;
                    if (staticEntitiesCollision(map, MOTION_DELAY)){
                        bX -= 4+speed;
                    }else{
                        bX -= speed;
                    }
                }
                direction = 2;
                dY = speed;
                dX = 0;
                break;
            case KeyEvent.VK_LEFT:
                direction = 3;
                dX = -speed;
                dY = 0;
                break;
            case KeyEvent.VK_RIGHT:
                direction = 4;
                dX = speed;
                dY = 0;
                break;
            case KeyEvent.VK_SPACE:
                if (bombQuantity > 0){
                    int x = bX + 48/2;
                    int y = bY + 48/2;
                    x -= x%48;
                    y -= y%48;
                    if (map.data.get(map.width*(y/48) + x/48).type != 'b'){
                        bombs.add(new Bomb(x, y));
                        map.data.get(map.width*(y/48) + x/48).type = 'b';
                        bombQuantity--;
                        throughBomb = true;
                    }
                }
                break;
        }
    }
    // setup key di chuyển nhả ra
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        isMoving = false;
        motionStatus = 0;
        switch (key) {
            case KeyEvent.VK_UP:
                dY = 0;
                break;
            case KeyEvent.VK_DOWN:
                dY = 0;
                break;
            case KeyEvent.VK_LEFT:
                dX = 0;
                break;
            case KeyEvent.VK_RIGHT:
                dX = 0;
                break;
        }
    }

    public void move(Map map, int MOTION_DELAY){
        if (!isDead){
            if (isMoving){
                motionStatus++;
            }
            if (!staticEntitiesCollision(map, MOTION_DELAY)) {
                bX += dX;
                bY += dY;
                int n = bombs.size();
                if (n > 0){
                    if (bombs.get(n-1).x/48 != bX / 48 || bombs.get(n-1).y/48 != bY / 48)
                        throughBomb = false;
                }
                if (staticEntitiesCollision(map, MOTION_DELAY)){
                    bX -= dX;
                    bY -= dY;
                }
            }
        }
    }
}