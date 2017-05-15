package com.example.user.project;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FloatWindow extends Service {

    private Context context;

    private boolean flagClick;
    private boolean flagView;

    private FloatView testView;
    private View inflateView;
    private WindowManager wm;
    private WindowManager.LayoutParams params;
    private WindowManager.LayoutParams floatWindowParams;

    private int start_x;
    private int start_y;
    private int prev_x;
    private int prev_y;

        @Nullable

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            context = this;

            testView = new FloatView(this);
            testView.setOnTouchListener(onTouchListener);
            params = new WindowManager.LayoutParams(
                    70,
                    70,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //터치 인식
                    PixelFormat.TRANSLUCENT); //투명

            params.gravity = Gravity.LEFT | Gravity.TOP;
            wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.addView(testView, params);

            inflateView = View.inflate(this, R.layout.float_menu, null);
            floatWindowParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //터치 인식
                    PixelFormat.TRANSLUCENT); //투명

        }

        private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        flagClick = true;

                        int x = (int) event.getRawX();
                        int y = (int) event.getRawY();

                        start_x = x;
                        start_y = y;
                        prev_x = params.x;
                        prev_y = params.y;
                    }
                    break;
                    case MotionEvent.ACTION_MOVE: {
                        flagClick = false;
                        int x = (int) (event.getRawX() - start_x);    //이동한 거리
                        int y = (int) (event.getRawY() - start_y);    //이동한 거리
                        params.x = prev_x + x;
                        params.y = prev_y + y;
                        wm.updateViewLayout(testView, params);

                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        if (flagClick) {
                            floatWindowParams.x = params.x + 70;
                            floatWindowParams.y = params.y - 50;
                            Log.v("@@@@@@@@@", "@@@@@@ floatWindowParams.x: " + floatWindowParams.x + " floatWindowParams.y: " + floatWindowParams.y);

                            inflateView = View.inflate(context, R.layout.float_menu, null);
                            inflateView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(inflateView);
                                    inflateView = null;
                                }
                            });
                            Button btn = (Button) inflateView.findViewById(R.id.btnend);
                            btn.setX(floatWindowParams.x);
                            btn.setY(floatWindowParams.y + 50);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onDestroy();
                                    if (inflateView != null)      //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
                                    {
                                        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(inflateView);
                                        inflateView = null;
                                    }
                                }
                            });

                            Button bt = (Button) inflateView.findViewById(R.id.btn);
                            bt.setX(floatWindowParams.x);
                            bt.setY(floatWindowParams.y);
                            bt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.nhn.android.search");
                                    if (intent != null) {
                                        context.startActivity(intent);
                                    }
                                    if (inflateView != null)      //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
                                    {
                                        ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(inflateView);
                                        inflateView = null;
                                    }
                                }
                            });
                            wm.addView(inflateView, floatWindowParams);
                            flagView = true;
                        }
                    }
                }
                return false;
            }
        };

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (testView != null)      //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
            {
                ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(testView);
                testView = null;
            }
            if (inflateView != null)      //서비스 종료시 뷰 제거. *중요 : 뷰를 꼭 제거 해야함.
            {
                if (flagView) {
                    ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(inflateView);
                }
                inflateView = null;
            }
            stopSelf();
        }
}
