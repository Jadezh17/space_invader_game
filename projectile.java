package invadem;

public class projectile{
    int x =100;
    int y =480;
    int width;
    int height;
    int direction; //0 -- up; 1 -- down
    int type; //0 - invader, 1 -- power invader, 2 -- super, 3 -- tank

    public projectile(int type)
    {
        //contructor
        this.width =1;
        this.height =3;
        direction = 0;
        this.type = type;
    }

    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }

    public void setPosition(int a, int b)
    {
        x = a;
        y = b;
    }

    public int move()
    {
        if (y>0)
        {
            if (direction == 0)
                y--;
            else
                y++;
        }
        return y;
    }
    public int getWidth(){
        return width;

    }
    public int getHeight(){
        return height;
    }
// if the direction bullet going up or down
    public int getDirection(){return direction;}


    public void setDirection(int direction)
    {
        this.direction = direction;
    }
// what type of projectile by which invader
    public int getType(){return type;}


}