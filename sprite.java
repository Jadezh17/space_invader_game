package invadem;
import java.util.*;


public class sprite {
    int type;//1-left, 2-top, 3-right, 4-solid
    int hit;
    int x;
    int y;
    int width;
    int height;
    int life;


    ArrayList<sprite> sprites;


    public sprite(int type, int hit){
        this.type = type;
        this.hit = hit;
        this.width = 8;
        this.height = 8;

    }

    public int getLife(){
        return life;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;

    }
    public int getHeight(){
        return height;
    }


    public void setPosition(int a, int b)
    {
        this.x = a;
        this.y = b;
    }

    public int getType()
    {
        return type;
    }


    public int getHit()
    {
        return hit;
    }

    public void hit()
    {
        hit++;
    }





}
