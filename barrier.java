package invadem;
import java.util.*;


public class barrier {
    int lHit;
    int tHit;
    int rHit;
    int sHit;
    int position; //0 - left, 1 - top, 2 - right
    int x;
    int y;
    int width;
    int height;
    ArrayList<sprite> sprites;
    ArrayList<sprite> destroyed;


    public barrier(int bPostion, int bx, int by)
    {
        width = 2;
        height =2;
        this.position = bPostion;
        x = bx;
        y = by;


        this.sprites = new ArrayList<sprite>();
        this.destroyed = new ArrayList<sprite>();
        loadSprite();

    }

    private void loadSprite() {
        //loading a left, a top, a right, plus 7 solids left leg sprites, 7 solids right leg sprites
        int st = 1;
        int m = 0;
        int mx = x;
        int my = y;

        while (m < 19) {
            sprite s = new sprite(st, 0);
            s.setPosition(mx, my);

            if (st < 4) {
                st++;
            }


            if (m < 2) //top 3 barriers
            {
                mx = mx + 8;
            } else if (m < 10) //7 left leg of barriers
            {
                mx = x;
                my = my + 2;
            } else if (m == 10) {

                mx = x + 16;
                my = y + 2;
            } else // 7 right leg of barriers
            {
                my = my + 2;
            }


            sprites.add(s);


            m++;
        }
    }

    public boolean destroyed()
    {
        //clean sprite hit over 3
        //check each sprite life
        for(sprite s:sprites)
        {
            if (s.getHit() >2)
            {
                destroyed.add(s);
            }
        }

        //remove sprite hit over 2
        for(sprite s:destroyed)
        {
            sprites.remove(s);
        }

        //return true if no sprite left
        if (sprites.size() == 0)
            return true;
        else
            return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    public void setPosition(int a, int b)
    {
        this.x = a;
        this.y = b;
    }

    public int getWidth(){
        return width;

    }
    public int getHeight(){
        return height;
    }

    public ArrayList<sprite> getSprites()
    {
        return sprites;
    }






}
