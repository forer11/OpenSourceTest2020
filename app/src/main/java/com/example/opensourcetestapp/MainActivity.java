package com.example.opensourcetestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.nisrulz.sensey.MovementDetector;
import com.github.nisrulz.sensey.Sensey;

public class MainActivity extends AppCompatActivity {
    boolean moved = false;
    private TextView textView;
    private LottieAnimationView studentAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        studentAnimation = findViewById(R.id.animation);
        Sensey.getInstance().init(MainActivity.this);
        Sensey.getInstance().startMovementDetection(movementListener);

    }

    private void playAnimation() {
        studentAnimation.setVisibility(View.VISIBLE);
        studentAnimation.setProgress(0);
        studentAnimation.playAnimation();
    }

    private void stopAnimation() {
        studentAnimation.setVisibility(View.GONE);
        studentAnimation.cancelAnimation();
    }


    MovementDetector.MovementListener movementListener = new MovementDetector.MovementListener() {
        @Override
        public void onMovement() {
            // Movement detected, do something
            if (!moved) {
                Toast.makeText(MainActivity.this, "I moved", Toast.LENGTH_SHORT).show();
                textView.setText("Moving!");
                moved = true;
                playAnimation();
            }
        }

        @Override
        public void onStationary() {
            // Movement stopped, do something
            if (moved) {
                Toast.makeText(MainActivity.this,
                        "I stopped moving",
                        Toast.LENGTH_SHORT).show();
                textView.setText("Not Moving!");
                moved = false;
                stopAnimation();
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop Sensey and release the context held by it
        Sensey.getInstance().stopMovementDetection(movementListener);
        Sensey.getInstance().stop();
    }
}
