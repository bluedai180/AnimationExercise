package com.blue.animationart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blue.animationart.view.MateBall;

/**
 * Created by huanggecheng on 2017/9/6.
 */

public class BezierDemo extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezierdemo);
    }

    public void secondbezierOnclick(View view){
        startActivity(new Intent(this, SecondBezierActivity.class));
    }
    public void thridbezierOnclick(View view){
        startActivity(new Intent(this, ThridBezierActivity.class));
    }
    public void drawPadBezierOnclick(View view){
        startActivity(new Intent(this, DrawPadActivity.class));
    }

    public void pathMorthingBezierOnclick(View view){
        startActivity(new Intent(this, PathMorphingActivity.class));
    }
    public void waveBezierOnclick(View view) {
        startActivity(new Intent(this, WaveActivity.class));
    }
    public void pathBezierOnclick(View view) {
        startActivity(new Intent(this, PathBezierActivity.class));
    }
    public void mateBallOnclick(View view) {
        startActivity(new Intent(this, MetaBallActivity.class));
    }
}
