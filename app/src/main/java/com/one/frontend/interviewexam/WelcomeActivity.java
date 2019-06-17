package com.one.frontend.interviewexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView imgShow;
    private LinearLayout imgWrap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imgShow = findViewById(R.id.img_show);
        imgWrap = findViewById(R.id.imgWrap);
        final AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.3f);
        alphaAnim.setDuration(1500);
        alphaAnim.setFillAfter(true);
        alphaAnim.setRepeatMode(AlphaAnimation.REVERSE);
        alphaAnim.setRepeatCount(2);
        imgShow.startAnimation(alphaAnim);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.1f);
                alphaAnim.setDuration(1300);
                alphaAnim.setFillAfter(true);
                imgWrap.startAnimation(alphaAnim);
                alphaAnim.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(WelcomeActivity.this, UserInfoActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
