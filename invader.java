package invadem;

import java.util.*;


public class invader{
    int life = 1;
    int x;
    int y;
    int width;
    int height;
    boolean moveStatus;
    int maxStep;
    int type; //0 - normal invader, 1 - power, 2 amour

    public invader() {
        width=16;
        height = 16;
        maxStep = 0;
        this.life = 1;
        this.type = 0;
    }
        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }
        public int getLife(){ return life;}
        public void setLife(int life){ this.life = life;}
        public void reduceLife(){ this.life--;}

        public void setPosition(int a, int b)
        {
            x = a;
            y = b;
        }

        public boolean getMoveStatus()
        {
            moveStatus = !moveStatus;
            return !moveStatus;
        }

        public int getMaxStep()
        {
            maxStep++;
            return maxStep;
        }

        public void resetStep()
        {
            maxStep = 0;
        }


        public int getWidth(){
            return width;

        }
        public int getHeight(){
            return height;
        }

        public void killed()
        {
            life = 0;
        }

//set the type of invader
        public void setType(int type){ this.type = type;}
        public int getType(){return type;}

}