package com.example.ak_bappy.dx_ball;


import android.content.Context;
import android.content.DialogInterface;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.util.Random;


/**
 * Created by BAPPY on 12/22/2016.
 */

public class GameCanvas extends View {

    SoundPool s;
    Paint paint;
    Random rand = new Random();
    boolean firstTime = true;
    float x=0,y=0,dx=5,dy=5;
    float canvasWidth,canvasHeight;
    int score = 0;

    //Life Counter
    int remainingLife = 3;
    float fontSize = 35;
    int gamelevel = 1;
    int maxScore = 300;

    float barX = 0;
    float barHeight = 40;
    float barWidth;
    Rect Bar = new Rect();

    float radious = 25;

    Rect[] brick  = new Rect[50];
    int totalBricks = 20;
    float numberOfBricsPerRow = 5;
    float distanceBwtweenBricks = 20;
    int numberOfRows = 3;
    int bricID = 0;

    int sound1 = -1;
    int sound2 = -1;

    float upperBarHeight;


    public GameCanvas(Context context) {
        super(context);
        paint = new Paint();
        for(int i = 0; i < totalBricks; i++)
        {
            brick[i] = new Rect();
        }
        s = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        AssetManager aset = context.getAssets();
        AssetFileDescriptor descriptor;

        try {
            descriptor = aset.openFd("sound.wav");
            sound1 = s.load(descriptor,0);
            descriptor = aset.openFd("soundfire.wav");
            sound2 = s.load(descriptor,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void calculateNextPos(Canvas canvas)
    {
        if(x < radious || x > (canvasWidth-radious))
        {
            dx = -dx;
            s.play(sound1,1,1,0,0,1);
        }
        else if(y < radious+upperBarHeight || y > (canvasHeight-radious))
        {
            dy = -dy;
            s.play(sound1,1,1,0,0,1);
        }

        if(Bar.intersect((int)(x-radious),(int)(y-radious),(int)(x+radious),(int)(y+radious)))
        {
            dy = -dy;
            s.play(sound1,1,1,0,0,1);
        }


        if( y > canvasHeight-radious-barHeight+5  )
        {
            reset();
            remainingLife--;

            if(remainingLife == 0)
            {
                newGame();
                dx=0;
                dy=0;
            }

        }
        if(score == 400 && gamelevel == 2)
        {
            score=401;
            dx=0;
            dy=0;
            gameOver();

        }

        if(score == 300 && gamelevel == 1 ) {
            gamelevel = 2;
            reset();
            resetBrics();
            score = 0;
            remainingLife = 3;
            numberOfRows = 4;
            maxScore=400;
        }
        x += dx;
        y += dy;
        collisionDetection();
    }

    public void reset()
    {
        x = canvasWidth/2;
        y = canvasHeight-radious-barHeight;
        barWidth = canvasWidth/5;
        upperBarHeight = canvasHeight/7;
        barX = canvasWidth/2 - barWidth/2;
        dx=5;
        dy=5;
    }

    public void drawBrics(Canvas canvas)
    {
        float padding=25;
        float bricWidth = (canvasWidth - (padding*2 + distanceBwtweenBricks*numberOfBricsPerRow)) / numberOfBricsPerRow;
        float brickHeight = 50;
        float x = 50,y = upperBarHeight+30;
        bricID = 0;

        if(gamelevel == 2)
            paint.setColor(Color.rgb(rand.nextInt(255) + 1,rand.nextInt(255) + 1,rand.nextInt(255) + 1));
        paint.setColor(Color.rgb(178,34,34));
        for(int i = 1; i <= numberOfRows; i++)
        {
            for(int j = 0; j < numberOfBricsPerRow; j++)
            {
                if(brick[bricID] != null)
                {
                    if(gamelevel == 2)
                        paint.setColor(Color.rgb(rand.nextInt(255) + 1,rand.nextInt(255) + 1,rand.nextInt(255) + 1));
                    brick[bricID].set((int)x,(int)y,(int)(x+bricWidth),(int)(y+brickHeight));
                    canvas.drawRect(brick[bricID],paint);
                }
                bricID++;
                x += bricWidth+distanceBwtweenBricks;
            }
            y += brickHeight+50;
            x = 50;
        }
    }

    public void drawBall(Canvas canvas)
    {
        //paint.setColor(Color.rgb(rand.nextInt(255) + 1,rand.nextInt(255) + 1,rand.nextInt(255) + 1));
        paint.setColor(Color.rgb(178,34,34));
        canvas.drawCircle(x,y, radious,paint);
    }

    public void drawBar(Canvas canvas)
    {
        paint.setColor(Color.rgb(51,51,51));
        Bar.set((int)barX, (int)(canvasHeight-barHeight), (int)(barX+barWidth), (int)(canvasHeight));
        canvas.drawRect(Bar, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        barX = (int)event.getX();
        barX -= barWidth/2;

        if((int)event.getX() <= barWidth/2)
            barX = 0;
        if(((int)event.getX()+ barWidth/2) >= canvasWidth)
            barX = canvasWidth - barWidth;
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(firstTime)
        {
            firstTime = false;
            canvasHeight = canvas.getHeight();
            canvasWidth = canvas.getWidth();
            x = canvasWidth/2;
            y = canvasHeight-radious-barHeight;
            barWidth = canvasWidth/6;
            upperBarHeight = canvasHeight/7;
            barX = canvasWidth/2 - barWidth/2;
        }

        calculateNextPos(canvas);
        canvas.drawRGB(255,255,255);
        drawBall(canvas);
        drawUpperBar(canvas);
        drawBrics(canvas);
        drawBar(canvas);
        invalidate();
    }

    public void drawUpperBar(Canvas canvas)
    {
        paint.setColor(Color.rgb(219,112,147));
        canvas.drawRect(0,0,canvasWidth,upperBarHeight,paint);
        drawLifeText(canvas);
        drawScore(canvas);
    }

    private void collisionDetection()
    {
        for(int i = 0; i < totalBricks; i++)
        {
            if(brick[i] != null)
            {
                if(brick[i].intersect((int)(x-radious),(int)(y-radious),(int)(x+radious),(int)(y+radious)))
                {
                    s.play(sound2,1,1,0,0,1);
                    brick[i] = null;
                    dy = -dy;
                    score += 20;
                }
            }
        }
    }

    public void drawLifeText(Canvas canvas)
    {
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(fontSize);
        canvas.drawText("Life: ",canvasWidth/18,upperBarHeight/2+fontSize/2,paint);
        drawLifes(canvas);
    }

    public void drawLifes(Canvas canvas)
    {
        float cx = canvasWidth/4-20;
        float cy = upperBarHeight/2+10;
        float radiousOfLifeCircle = 22;

        paint.setColor(Color.GREEN);
        if(remainingLife > 0)
            canvas.drawCircle(cx,cy,radiousOfLifeCircle,paint);
        if(remainingLife > 1)
            canvas.drawCircle(cx+60,cy,radiousOfLifeCircle,paint);
        if(remainingLife > 2)
            canvas.drawCircle(cx+120,cy,radiousOfLifeCircle,paint);
    }

    public void drawScore(Canvas canvas)
    {
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(fontSize);
        canvas.drawText("Score ",canvasWidth-canvasWidth/2+50,upperBarHeight/2+fontSize/2,paint);
        canvas.drawText(String.valueOf(score),canvasWidth-canvasWidth/4,upperBarHeight/2+fontSize/2,paint);
    }

    public void newGame()
    {
        android.support.v7.app.AlertDialog.Builder alertDlg = new android.support.v7.app.AlertDialog.Builder(getContext());
        alertDlg.setTitle("Game Over");
        alertDlg.setMessage("Do you want to Restart Game?");
        alertDlg.setIcon(android.R.drawable.ic_dialog_alert);
        alertDlg.setCancelable(false);
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
                resetBrics();
                score = 0;
                remainingLife = 3;
            }
        });
        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            System.exit(0);            }
        });
        alertDlg.create().show();
    }

    public void gameOver()
    {
        Toast.makeText(getContext(),"gameover",Toast.LENGTH_SHORT).show();
        android.support.v7.app.AlertDialog.Builder alertDlg = new android.support.v7.app.AlertDialog.Builder(getContext());
        alertDlg.setTitle("Game Over");
        alertDlg.setMessage("Game Over....! Do you want to Restart Game?");
        alertDlg.setIcon(android.R.drawable.ic_dialog_alert);
        alertDlg.setCancelable(false);
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
                resetBrics();
                score = 0;
                remainingLife = 3;
                gamelevel = 1;
                numberOfRows = 3;

            }
        });

        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);            }
        });
        alertDlg.create().show();
    }

    public void resetBrics()
    {
        for(int i = 0; i < totalBricks; i++)
        {
            brick[i] = new Rect();
        }
    }
}
