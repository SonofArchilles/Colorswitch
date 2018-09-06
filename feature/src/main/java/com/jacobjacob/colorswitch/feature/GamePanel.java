package com.jacobjacob.colorswitch.feature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.jacobjacob.colorswitch.feature.MainThread.canvas;

//https://www.youtube.com/watch?v=OojQitoAEXs 9/50

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    private RectPlayer player;
    private Point playerPoint;

    //
    public int Playerheight = 0;
    public int colorRed = 0;
    public int colorGreen = colorRed + 85;
    public int colorBlue = colorGreen + 85;
    public int xvalue;
    public double cR = 0;
    public double cG = 0;
    public double cB = 0;
    public double xvalue1;
    public double yvalue1;
    public int Farbmodus = 1;
    public int AnzahlFarbmodi = 4;
    public float xmouse;
    public float ymouse;
    public float xscreen = getRight();
    public float yscreen = getTop();
    //

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        player = new RectPlayer(new Rect(Playerheight,Playerheight,0,0), Color.rgb (0, 55, 33));
        playerPoint = new Point(Playerheight/2,Playerheight/2);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}
            retry = false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Farbmodus = Farbmodus + 1;
                if (Farbmodus > AnzahlFarbmodi) {
                    Farbmodus = 0;
                }
            case MotionEvent.ACTION_MOVE:
                xmouse = (int) event.getX();
                ymouse = (int) event.getY();
                playerPoint.set((int) event.getX(), (int) event.getY());
                switch (event.getAction()) {
                }
                return true;
        }
        return true;
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.rgb(colorRed,colorGreen,colorBlue));
        //player.draw(canvas);
    }

    public void update(){
        if (Farbmodus == 0) {
            colorRed = colorRed + 1;
            colorGreen = colorRed + 85;
            colorBlue = colorRed + 170;

            if (colorRed > 255)
                colorRed = 0;
            if (colorGreen > 255)
                colorGreen = colorGreen - 255;
            if (colorBlue > 255)
                colorBlue = colorBlue - 255;
        }
        if (Farbmodus == 1){
            xvalue = xvalue + 1;
            if (xvalue > 255)
                xvalue = 0;
            cR = 255/2 + 255/2 * Math.sin ( 0.025 * xvalue);
            cG = 255/2 + 255/2 * Math.sin ( 0.025 * xvalue + 2 * Math.PI/3);
            cB = 255/2 + 255/2 * Math.sin ( 0.025 * xvalue + 4 * Math.PI /3 );
            colorRed = (int) cR;
            colorGreen = (int) cG;
            colorBlue = (int) cB;
        }
        if (Farbmodus == 2){
            xvalue = xvalue + 1;
            if (xvalue > 255)
                xvalue = 0;
            cR = 255/2 + 255/2 * Math.sin ( 0.025 * xvalue);
            cG = 255/2 + 255/2 * Math.sin ( 0.025 * xvalue + Math.PI/3);
            colorRed = (int) (cR + (int) cG) /2;
            colorGreen = (int) (cR + (int) cG) /2;
            colorBlue = (int) (cR + (int) cG) /2;
        }

        if (Farbmodus == 3){
            xscreen = (float)getRight();
            yscreen = (float)getBottom();
            xvalue1 = xmouse/xscreen;
            yvalue1 = ymouse/yscreen;
            //cR = (255/2*(Math.sin (xvalue))) - yvalue;
            //cG = (255/2*(Math.sin (xvalue + 2 * Math.PI/3))) - yvalue;
            //cB = (255/2*(Math.sin (xvalue + 4 * Math.PI /3 ))) - yvalue;
            cR = (yvalue1 * 255 * ((Math.sin (1 * Math.PI * xvalue1))));
            cG = (yvalue1 * 255 * ((Math.sin (1 * Math.PI * xvalue1 + 2 * Math.PI / 3))));
            cB = (yvalue1 * 255 * ((Math.sin (1 * Math.PI * xvalue1 + 4 * Math.PI / 3 ))));
            cR = cR * 2;
            cG = cG * 2;
            cB = cB * 2;
            if(cR<0)
                cR=-cR;
            if(cG<0)
                cG=-cG;
            if(cB<0)
                cB=-cB;
            if(cR>255)
                cR=255;
            if(cG>255)
                cG=255;
            if(cB>255)
                cB=255;
            colorRed = (int) cR;
            colorGreen = (int) cG;
            colorBlue = (int) cB;
            //System.out.print(" RED: " + colorRed + " GREEN: " + colorGreen + " BLUE; " + colorBlue);
            //System.out.print(" "+"Xmouse:"+" "+(int)xmouse+" "+"Right:"+" "+(int)xscreen+" "+"Xwert:"+" "+xvalue1+" "+"Ymouse:"+" "+(int)ymouse+" "+"Bottom:"+" "+(int)yscreen+" "+"Ywert:"+" "+yvalue1);
            //System.out.print(" RED: " + colorRed + " GREEN: " + colorGreen + " BLUE; " + colorBlue+" "+"X:"+" "+xvalue1+" "+"Y:"+" "+yvalue1+" ");
            }
        }
    }
