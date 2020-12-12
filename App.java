package invadem;
import java.util.*;
import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;



import processing.core.PFont;
import processing.core.PImage;
import processing.core.PApplet;





public class App extends PApplet {
    tank t;
    ArrayList<projectile> ps;
    ArrayList<invader> iv;
    ArrayList<barrier> bs;
    PImage img;
    int tankX;
    int tankY;
    PImage specialProjectile;
    int px;
    int py = 100;
    int invaderX;
    int invaderY;
    PImage invader;
    PImage barrier_l;
    PImage barrier_t;
    PImage barrier_r;
    PImage barrier_solid;
    PImage barrier_l2;
    PImage barrier_t2;
    PImage barrier_r2;
    PImage barrier_solid2;
    PImage bullets;
    PImage next_level;
    ArrayList<invader> killedInvaders;
    ArrayList<projectile> usedBullets;
    ArrayList<barrier> destroyedBarriers;
    ArrayList<PImage> specialInvadersPower;
    ArrayList<PImage> specialInvadersArmour;
    int frameCount;
    int invaderFireCount;
    int invaderMoveCount;
    int invaderMoveRate;
    int invaderFireRate;
    PImage gameover;
    int laggingtime;
    int score;
    PFont font;
    int highScore;
    int gameLevel;
    PImage power_invader;
    PImage amour_invader;
    PImage power2_invader;
    PImage amour2_invader;



    int currentSpriteIndex;




    public App() {
        //Set up your objects. Loading 1st
        // start the music
        music();


        this.t = new tank();
        this.ps = new ArrayList<projectile>();
        this.iv = new ArrayList<invader>();
        this.bs = new ArrayList<barrier>();
        this.killedInvaders = new ArrayList<invader>();
        this.usedBullets = new ArrayList<projectile>();
        this.destroyedBarriers = new ArrayList<barrier>();
        this.specialInvadersPower = new ArrayList<PImage>();
        this.specialInvadersArmour = new ArrayList<PImage>();

        //inital framcount beginning at zero
        this.frameCount = 0;
        //the moment of the invader
        this.invaderMoveCount = 1;
        this.invaderMoveRate = 2;
        // the rate of fire
        this.invaderFireRate = 100;
        this.invaderFireCount = 5;
        // high score
        highScore = 10000;
        // the current level the game
        this.gameLevel = 1;





    }

    public void setup() {

        frameRate(60);

        //loading all images
        img = loadImage("tank1.png");
        invader = loadImage("invader1.png");
        bullets = loadImage("projectile.png");
        next_level = loadImage("nextlevel.png");
        gameover = loadImage("gameover.png");
        power_invader = loadImage("invader1_power.png");//line 2
        amour_invader = loadImage("invader1_armoured.png"); //line 1
        specialProjectile = loadImage("projectile_lg.png");
        power2_invader = loadImage("invader2_power.png");//line 2
        amour2_invader = loadImage("invader2_armoured.png");

        specialInvadersPower.add(power_invader);
        specialInvadersPower.add(power2_invader);
        specialInvadersArmour.add(amour_invader);
        specialInvadersArmour.add(amour2_invader);




        //loading things initally here where the games starts
        loadingDefaultInvaders();
        loadingDefaultBarriers();

    }
// the inital setup - position of tank , invader, size of canavas
    public void settings() {
        tankX +=320;
        tankY+=455;
        invaderX +=250;
        invaderY +=100;
        size(640, 480);



    }

    public void loadingDefaultBarriers()
    {
        //loading 3 barriers initially when game starts

        int bx = 220; //increase by 100 repeat 3
        int by = 420; //never change

        int m = 0;
        while(m<3)
        {


            barrier b = new barrier(m,bx, by);


            bs.add(b);

            bx = bx + 100;

            m++;
        }


    }

    public void drawBarrier(barrier b)
    {
        //draw barrier frame
        for(sprite s: b.getSprites())
        {
            //draw each barrier sprite
            image(set_image(s.getType(),s.getHit()),s.getX(),s.getY());
        }

    }

    public void music(){
//trying to import music
        String sound ="src/main/resources/sound.wav";

        try
        {
            InputStream in = new FileInputStream(sound);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);

        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
            ContinuousAudioDataStream loop = null;
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
    }


    public void fire_music(){
//the music everytime it fires
        String sound ="src/main/resources/fire.wav";

        try
        {
            InputStream in = new FileInputStream(sound);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
            ContinuousAudioDataStream loop = null;

        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
    }




    public void loadingDefaultInvaders()
    {
        //loading 30 invaders initially when game starts
        int rn = 0;
        int ln = 0;
        int ix = 200;
        int iy = 100;
        int m = 0;
        while(m<40)
        {
            if (ln == 10)
            {
                ln = 0;
                ix = 200;
                iy = iy +20;
                rn++;
            }

            if (ln >0)
            {
                ix =  ix + 20;
            }

            ln++;

            invader i = new invader();

            //set up different type of invader

            if (gameLevel == 1)
            {
                i.setType(0);//normal invader
            }
            else {
                if (rn == 3 && rn ==2) {
                    i.setType(0);//normal invader life 1
                } else if (rn == 1) {
                    i.setType(1); //super life 1
                } else if (rn ==0) {
                    i.setType(2); //amour life 3
                    i.setLife(3);
                }
            }



            i.setPosition(ix, iy);
            iv.add(i);

            m++;


        }
    }


    public void draw() {
        //Main Game Loop

        background(0, 0, 0);
        frameCount++;
        laggingtime++;

        //font and draw to screen
        String scoring = "Score:" + Integer.toString(score);
        String life = "Life:" + Integer.toString(t.getLife());
        String highestScore = "Highest Score:" + Integer.toString(highScore);
        font = createFont("src/main/resources/PressStart2P-Regular.ttf", 13);
        textFont(font);
        text(scoring, 10, 30);
        text(highestScore, 380, 30);
        text(life, 200, 30);


        if (frameCount % invaderMoveRate == 0)//2s = 120 frames
        {
            invaderMoveCount = 1;
        } else {
            invaderMoveCount++;
        }

        if (frameCount % invaderFireRate == 0)//Random fire every 5s = 300 frames
        {
            invaderFireCount = 1;//reset invader fire power to 0
        } else {
            invaderFireCount++;
        }

        // detect killed object
        for (projectile p : ps) {
            //check collision with invader
            for (invader i : iv) {
//                if ((collision(p, i)) == true)
                if (collision(p.getX(), p.getY(), p.getWidth(), p.getHeight()
                        , i.getX(), i.getY(), i.getWidth(), i.getHeight()) == true) {

                    //move invader from list
                    i.reduceLife();
                    if (i.getLife() == 0) {
                        killedInvaders.add(i);
                    }
                    score += 100;

                    usedBullets.add(p);
                    continue;

                }
            }


            //check collision with barrier
            for (barrier b : bs) {
                for (sprite s : b.getSprites()) {
                    if (collision(p.getX(), p.getY(), p.getWidth(), p.getHeight()
                            , s.getX(), s.getY(), s.getWidth(), s.getHeight()) == true ) {
                        usedBullets.add(p);
                        s.hit();
                        continue;
                    }

                }

                if (b.destroyed())
                    destroyedBarriers.add(b);

            }

            //check tank is hit by projectile or not
            if (collision(p.getX(), p.getY(), p.getWidth(), p.getHeight()
                    , t.getX(), t.getY(), t.getWidth(), t.getHeight()) == true) {
                //move invader from list


                if (p.getType() == 1) {
                    t.killed();

                    usedBullets.add(p);
                    continue;
                } else if (p.getType() == 0 || p.getType() == 2) {
                    t.hit(1);
                    usedBullets.add(p);
                    continue;
                }

            }

        }

        //remove used objects
        for (projectile p : usedBullets) {
            ps.remove(p);
        }


        for (invader i : killedInvaders) {
            iv.remove(i);
        }

        for (barrier b : destroyedBarriers) {
            bs.remove(b);
        }

        //check tank is life
        if (t.is_killed()) {
            //Game Over.

            GameOver();
            return;
        }


        // life invaders are all killed
        if (iv.size() == 0) {
            NextLevel();


            try {
                image(next_level, 240, 200);
                Thread.sleep(1000);
            }
            catch( InterruptedException ex){
            Thread.currentThread().interrupt();
            }
        return;

        }

        //pick random invader to fire projecdtile
        if (iv.size() > 0 && invaderFireCount == invaderFireRate)
        {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(iv.size());
            invader fi = iv.get(randomInt);
            projectile pi = new projectile(fi.getType());
            pi.setPosition(fi.getX(), fi.getY() + 88);//this number to avoid self killing invader
            pi.setDirection(1); //this set bullets from invader to tank

            ps.add(pi);
        }

        //draw each invader
        for(invader i:iv)
        {
            //Move every 2s that is 120 frame refresh count
            if (invaderMoveCount == invaderMoveRate)
            {
                //move every 2nd frame
                if (i.getMaxStep() <= 30)
                {
                    i.setPosition(i.getX() + 1, i.getY());
                    if (i.getMaxStep() == 30)
                    {
                        //mvoe down 8 pixel every 30 frames
                        i.setPosition(i.getX(), i.getY()+8);
                    }
                }
                else
                {
                    //oppositte move after 30 steps
                    i.setPosition(i.getX()-1, i.getY());

                    if (i.getMaxStep() == 60)
                    {
                        //reset max step = 0 when step is 60
                        i.resetStep();
                    }
                }

            }
// if type is 2 --> armour invader
            if(i.getType() == 2 )
            {
               for(PImage s: specialInvadersArmour){
                   image(s,i.getX(),i.getY());


               }

            }
            // if type is 1 draw the power invader
            else if(i.getType() == 1)
            {
                for(PImage p : specialInvadersPower){
                    image(p,i.getX(),i.getY());
                }

            }
            else {
                //loading normal invader
                image(invader,i.getX(),i.getY());
            }


        }

        //draw life projectile
        for(projectile p: ps)
        {
            int py = p.move();
            int px = p.getX() +11;
            if (p.getType() ==1){
                image(specialProjectile,px,py);
            }
            else if  (py>0 && px>0)
            {
                image(bullets, px, py);
            }
        }


        //draw barrier left
        for(barrier b:bs)
        {
            drawBarrier(b);
        }

        for (invader i:iv)
        {
            for(barrier b:bs)
            {
                for (sprite s : b.getSprites())
                {

                    if (collision(i.getX(), i.getY(), i.getWidth(), i.getHeight()
                            , s.getX(), s.getY(), s.getWidth(), s.getHeight()) == true)
                    {

                        GameOver();


                    }


                }
            }
        }


        for(projectile p:ps)
        {

            if (collision(p.getX(),p.getY(),p.getWidth(),p.getHeight()
                    ,tankX,tankY,t.getWidth(),t.getHeight()) == true && p.getType() !=3)
            {
                t.hit(1);
                usedBullets.add(p);
            }
            if (t.is_killed())
            {
                image(gameover,240,200);
                if(score>highScore){
                    highScore = score;
                }
                reset_lose();
            }
        }

        //draw tank
        image(img, tankX, tankY);


    }


    public void GameOver()
    {
        //control application to stop. Waiting for user interactiion to restart game.



        if(score>highScore){
            highScore = score;
        }

        keyPressed();
        try
        {
            // key pressed and lag time to allow the game to restart
            String restart = ("Press up to restart");
            font = createFont("src/main/resources/PressStart2P-Regular.ttf", 13);
            textFont(font);
            text(restart, 190, 250);
            image(gameover,240,200);
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            System.out.println("Exception error: " + ex);

        }
    }

    public void NextLevel()
    {
        //set level + 1 and reset all objects to beginning. Waiting seconds to start next level
        gameLevel++;
        reset_win();


    }

    public PImage set_image(int type, int hit)
    {
        PImage p = loadImage("empty.png");
        if (type == 1)
        {
            //draw left sprite
            if (hit == 0) {
                p = loadImage("barrier_left1.png");
            } else if (hit == 1) {
                p = loadImage("barrier_left2.png");
            } else if (hit == 2) {
                p = loadImage("barrier_left3.png");
            } else {
                p = loadImage("empty.png");
            }
        }
        else if (type == 2)
        {
            //draw top sprite
            if (hit == 0)
            {
                p = loadImage("barrier_top1.png");
            }
            else if (hit == 1)
            {
                p = loadImage("barrier_top2.png");
            }
            else if (hit == 2)
            {
                p = loadImage("barrier_top3.png");
            }
            else
            {
                p = loadImage("empty.png");
            }

        }
        else if (type == 3)
        {
            //draw right sprite
            if (hit == 0)
            {
                p = loadImage("barrier_right1.png");
            }
            else if (hit == 1)
            {
                p = loadImage("barrier_right2.png");
            }
            else if (hit == 2)
            {
                p = loadImage("barrier_right3.png");
            }
            else
            {
                p = loadImage("empty.png");
            }
        }
        else if (type == 4)
        {
            //draw solid sprite
            if (hit == 0)
            {
                p = loadImage("barrier_solid1.png");
            }
            else if (hit == 1)
            {
                p = loadImage("barrier_solid2.png");
            }
            else if (hit == 2)
            {
                p = loadImage("barrier_solid3.png");
            }
            else
            {
                p = loadImage("empty.png");
            }
        }
        else
        {
            //load nothing
            p = loadImage("empty.png");
        }


        return p;

    }


    public void keyPressed(){
        //loading when keyprocess


        if (key == CODED){
            if (keyCode == RIGHT){
                if ((tankX+10 >=180) && (tankX+10 <=460)){
                    tankX+=5;
                    t.setPosition(tankX, tankY);


                }
            }
            if (keyCode == LEFT) {
                if ((tankX - 10 >= 180) && (tankX - 10) <= 460) {
                    tankX -= 5;

                }
            }
            if (keyCode == UP){
                reset_lose();

            }




        }
        if (key == ' ')
        {
            //create a new projectile
            fire_music();
            projectile p = new projectile(3); // tank fire
            p.setDirection(0); //set bullet fire by tank toward invader

            p.setPosition(tankX, tankY -8);


            ps.add(p);

        }
        if (key =='a'){
            NextLevel();
        }


    }


    public void move(){
        System.out.println("move...");
        int i= 0;
        int j = 0;

        while (i<30){
            invaderY +=1;
            //draw();
            i+=1;
        }
        while (j<10){
            invaderX+=1;
            //draw();
            j+=1;
        }



    }


    public boolean collision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
    {

        if (((x1>(x2-w2/2)) && x1<(x2+w2/2)) && ((y1>(y2-h2/2)) && y1<(y2+h2/2)))
            //Collision has been detected
            return true;
        else

            return false;
    }

    public void reset_lose(){
        //reintialising all the object and reseting the countst , reloading DefaultBrries and invaders
        if (laggingtime % 4 ==0) {
            this.t = new tank();
            this.ps = new ArrayList<projectile>();
            this.iv = new ArrayList<invader>();
            this.bs = new ArrayList<barrier>();
            this.killedInvaders = new ArrayList<invader>();
            this.usedBullets = new ArrayList<projectile>();
            this.destroyedBarriers = new ArrayList<barrier>();
            score = 0;


            this.frameCount = 0;
            this.invaderFireCount = 5;
            this.invaderMoveCount = 1;
            if (invaderMoveRate > gameLevel) this.invaderMoveRate = this.invaderMoveRate - gameLevel;
            this.invaderFireRate =100;


            loadingDefaultBarriers();
            loadingDefaultInvaders();




        }

    }

    public void reset_win(){
        //reintialising all the object and reseting the countst , reloading DefaultBrries and invaders
        if (laggingtime % 4 ==0) {
            this.t = new tank();
            this.ps = new ArrayList<projectile>();
            this.iv = new ArrayList<invader>();
            this.bs = new ArrayList<barrier>();
            this.killedInvaders = new ArrayList<invader>();
            this.usedBullets = new ArrayList<projectile>();
            this.destroyedBarriers = new ArrayList<barrier>();


            this.frameCount = 0;
            this.invaderFireCount = 3;
            this.invaderMoveCount = 1;
            if (invaderMoveRate > gameLevel) this.invaderMoveRate = this.invaderMoveRate - gameLevel;
            this.invaderFireRate =50;


            loadingDefaultBarriers();
            loadingDefaultInvaders();




        }

    }



    public static void main(String[] args) {



        PApplet.main("invadem.App");

        System.out.println("main...");


    }







}
