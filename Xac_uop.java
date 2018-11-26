import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Xac_uop extends Enemy{
    //up == left
    protected Image left_1 = new ImageIcon("data/image/monster_left6.png").getImage();
    protected Image left_2 = new ImageIcon("data/image/monster_left1.png").getImage();
    protected Image left_3 = new ImageIcon("data/image/monster_left2.png").getImage();
    //down == right
    protected Image right_1 = new ImageIcon("data/image/monster_right6.png").getImage();
    protected Image right_2 = new ImageIcon("data/image/monster_right1.png").getImage();
    protected Image right_3 = new ImageIcon("data/image/monster_right2.png").getImage();

    protected Image dead = new ImageIcon("data/image/effect_03.png").getImage();
    //lưu trạng thái oneal
    protected Image onealStatus = left_1;
    private boolean foundBomber = false;
    protected boolean isDead = false;
    private int lastBomberX = 0, lastBomberY = 0;

    protected LinkedList<xOy> directionToBomber = new LinkedList<xOy>();

    public Xac_uop(int x, int y){
        super(x,y);
    }

    public void move(Bomber player, Map map){
        int x;
        if (bX % 48 <= 3 && bY % 48 <= 3){
            if (player.bX != lastBomberX || player.bY != lastBomberY){
                directionToBomber.clear();
                for(xOy xoy : map.data){
                    xoy.notChecked = true;
                }
                foundBomber = false;
                findBomber(player, map,bX/48, bY/48, 0);
                lastBomberX = player.bX;
                lastBomberY = player.bY;
            }
        }
        if (!foundBomber){
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
        }else{
            direction = directionToBomber.get(1).direction;
            if (bX/48 == directionToBomber.get(1).x && bY/48 == directionToBomber.get(1).y && (bX%48 <= 3 && bY % 48 <= 3)){
                directionToBomber.remove(0);
                direction = directionToBomber.get(1).direction;
            }
                x = random.nextInt();
                if (x%4 == 0){
                    x = random.nextInt()%3;
                    if (x < 0) x = -x;
                    x++;
                    speed = x;
                }
                motionStatus++;
                if (direction == 1){
                    bY -= speed;
                }else if (direction == 2){
                    bY += speed;
                }else if (direction == 3){
                    bX -= speed;
                }else if (direction == 4){
                    bX += speed;
                }
        }
    }
 // thuat toan tìm kiếm theo chiều rộng : xác ướp tự tìm đường
    protected void findBomber(Bomber player, Map map, int x, int y, int dir){
        if (x == player.bX/48 && y == player.bY/48) {
            directionToBomber.add(new xOy(x,y,dir));
            foundBomber = true;
        }else if (!foundBomber){
              directionToBomber.add(new xOy(x,y,dir));
              map.data.get(y*map.width + x).notChecked = false;
              xOy[] listDir = {new xOy(x, y-1, 1), new xOy(x, y+1, 2), new xOy(x-1, y, 3), new xOy(x+1, y, 4)};
              sortDir(player, listDir);
              for(int i = 0; i < 4; i++){
                  if (map.data.get((listDir[i].y)*map.width + listDir[i].x).type != '*' &&  map.data.get((listDir[i].y)*map.width + listDir[i].x).type != '#' && map.data.get((listDir[i].y)*map.width + listDir[i].x).notChecked == true){
                      findBomber(player, map, listDir[i].x, listDir[i].y, listDir[i].direction);
                      map.data.get(listDir[i].y*map.width + listDir[i].x).notChecked = true;
                      if (!foundBomber) directionToBomber.remove(directionToBomber.size() - 1);
                  }
              }
        }
    }

    private void sortDir(Bomber player, xOy[] list){
        int n = list.length;
        for(int i = 0; i < n-1; i++){
            for(int j = i+1; j < n; j++){
                if (distance(player, list[i]) > distance(player, list[j])){
                    xOy temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
        }
    }

    private int distance(Bomber player, xOy location){
        int kq = (location.x - player.bX/48)*(location.x - player.bX/48) + (location.y - player.bY/48)*(location.y - player.bY/48);
        if (kq < 0) return -kq;
         else return kq;
    }

    public boolean  staticEntitiesCollision(Map map){
        Rectangle onealBound = new Rectangle(bX, bY, 48, 48);

            if (direction == 1){
                int index = (bY/48)*map.width + bX/48;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index++;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }

            if (direction == 3){
                int index = (bY/48)*map.width + bX/48;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index += map.width;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }



            if (direction == 2){
                int index = (bY/48 + 1)*map.width + bX/48;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index++;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
            }

            if (direction == 4){
                int index = (bY/48)*map.width + bX/48 + 1;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
                index += map.width;
                if (map.data.get(index).type == '#' || map.data.get(index).type == '*' || map.data.get(index).type == 'b'){
                    if (onealBound.intersects(new Rectangle(map.data.get(index).x, map.data.get(index).y, 48, 48))) return true;
                }
        }
        return false;
    }
}