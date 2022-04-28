package com.chia.cheng.arapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LeakActivity extends Activity {
    static Bitmap bitmap1;
    static Bitmap bitmap2;
    static Bitmap bitmap3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.launcbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] m = new byte[1024*1024];
                Intent intent = new Intent(LeakActivity.this, LeakActivity.class);
                startActivity(intent);
            }
        });
        ConstraintLayout layout = findViewById(R.id.background);
        layout.addView(new view(this));
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.anony_face);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bicycle_face);
        bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.fox_face_mesh_texture);


    }
    class view extends View {

        public view(Context context) {
            super(context);
        }

        public view(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public view(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public view(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.anony_face);
            canvas.drawBitmap(bitmap ,1, 1, null);
            super.onDraw(canvas);
        }
    }
}
