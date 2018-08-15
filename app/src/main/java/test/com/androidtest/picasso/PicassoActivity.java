package test.com.androidtest.picasso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.ksy.picasso.Picasso;

import test.com.androidtest.R;

public class PicassoActivity extends AppCompatActivity {
    public static final String URL = "http://sf6-ttcdn-tos.pstatp.com/obj/web.business.image/201807135d0d774d3a72eae648779e94";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        ImageView image = (ImageView) findViewById(R.id.image_pic);
        Picasso.with(this).load(URL).into(image);
    }
}
