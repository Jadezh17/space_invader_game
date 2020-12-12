
package invadem;
import java.util.*;

public class tank {
    int life;
    int x;
    int y;
    int width;
    int height;


    public tank(){

        this.life = 3;
        this.x = 250;
        this.y = 300;
        width = 22;
        height = 16;


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

    public void hit(int count){

        if ( life-count>=0)
            life  = life - count;
        if(life ==0){
            is_killed();
        }

    }

    public void setPosition(int a, int b)
    {
        this.x = a;
        this.y = b;
    }
    public boolean is_killed(){
        if (life <=0){
            return true;
        }
        return false;
    }
    public void killed(){
        life =0;

    }




}
