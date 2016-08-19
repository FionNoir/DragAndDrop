package com.example.patrick.myapplication;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView txtvOne = null;
    private Timer longPressTimer;
    private final int LONGPRESSTIME_BEGIN = 300;

    boolean isDown= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.txtvOne).setOnTouchListener(myLongClick);
        findViewById(R.id.txtvTwo).setOnTouchListener(myLongClick);
        findViewById(R.id.txtvThree).setOnTouchListener(myLongClick);
        findViewById(R.id.txtvFour).setOnLongClickListener(longListen);
        findViewById(R.id.txtvFive).setOnLongClickListener(longListen);

//        findViewById(R.id.txtvOne).setOnLongClickListener(longListen);
//        findViewById(R.id.txtvTwo).setOnLongClickListener(longListen);
//        findViewById(R.id.txtvThree).setOnLongClickListener(longListen);
//        findViewById(R.id.txtvFour).setOnLongClickListener(longListen);
//        findViewById(R.id.txtvFive).setOnLongClickListener(longListen);

        findViewById(R.id.txtvOne).setOnDragListener(droplistener);
        findViewById(R.id.txtvTwo).setOnDragListener(droplistener);
        findViewById(R.id.txtvThree).setOnDragListener(droplistener);
        findViewById(R.id.txtvFour).setOnDragListener(droplistener);
        findViewById(R.id.txtvFive).setOnDragListener(droplistener);

       // txtvOne = (TextView) findViewById(R.id.txtvOne);

    }

    View.OnTouchListener myLongClick = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {

            final View currentView = v;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                longPressTimer = new Timer();
                longPressTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        if (isDown) {
                            Log.i("Touch Event", "LONGPress");
                            DragShadow dragShadow = new DragShadow(currentView);
                            ClipData data = ClipData.newPlainText("", "");
                            currentView.startDrag(data, dragShadow, currentView, 0);

                        }

                    }
                },LONGPRESSTIME_BEGIN);

//                then = (Long)System.currentTimeMillis();
                Log.i("Touch Event", "ACTION_DOWN");
//                System.out.println(then);
                isDown = true;
                return true;
            }

//            if (event.getAction() == MotionEvent.ACTION_MOVE && (((Long)System.currentTimeMillis() - then) > 1200) ) {
//
//                Log.i("Touch Event", "ACTION_MOVE");
//                System.out.println(then);
//
//            }

            //else if (((Long)System.currentTimeMillis() - then) > 1200) {
//            if (isDown && (((Long)System.currentTimeMillis() - then) > 1200)) {
//
//                Log.i("Touch Event", "Finger is down");
//                System.out.println(then);
//            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                isDown = false;
            }


//                DragShadow dragShadow = new DragShadow(v);
//                ClipData data = ClipData.newPlainText("", "");
//                v.startDrag(data, dragShadow, v, 0);
                return true;
           // }
           // return false;
        }

    };


    View.OnLongClickListener longListen = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            DragShadow dragShadow = new DragShadow(v);
            ClipData data = ClipData.newPlainText("", "");
            v.startDrag(data, dragShadow, v, 0);
            return false;
        }
    };

    View.OnDragListener droplistener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch (dragEvent)
            {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("Drag Event", "Entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("Drag Event", "Exited");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i("Drag Event", "Drop");

                    TextView target = (TextView) v;
                    TextView dragged = (TextView) event.getLocalState();
                    target.setText(dragged.getText());
                    break;
            }

            return true;
        }
    };

    //Shadow of the element
    private  class DragShadow extends View.DragShadowBuilder {

        ColorDrawable greyBox ;

        public DragShadow(View view) {
            super(view);
            greyBox = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            super.onDrawShadow(canvas);
            greyBox.draw(canvas);
        }
        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize,shadowTouchPoint);

            View v = getView();

            int height = v.getHeight()/2;
            int width = v.getWidth()/2;

            greyBox.setBounds(0,0,width, height);

            shadowSize.set(width, height);

            shadowTouchPoint.set((int)width/2,(int)height/2);
        }


    }

}
