package com.yajirang.wonpandoligi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.yajirang.wonpandoligi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //룰렛 그리기 함수
    private LuckyWheel luckyWheel;

    List<WheelItem> wheelItems;

    String point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //xml 변수 저장
        luckyWheel = findViewById(R.id.luck_wheel);
        //점수판 데이터 생성
        generateWheelItems();

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                //변수에 아이템 저장
                WheelItem wheelitem = wheelItems.get(Integer.parseInt(point) - 1);

                //변수에 텍스트 저장
                String money = wheelitem.text;

                //메세지 출력
                Toast.makeText(MainActivity.this, money, Toast.LENGTH_SHORT).show();
            }
        });

        //버튼 함수
        Button start = findViewById(R.id.spin_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                point = String.valueOf(random.nextInt(6) + 1); //1~6
                luckyWheel.rotateWheelTo(Integer.parseInt(point));
            }
        });
        //return root;
    }

    //데이터 저장
    private void generateWheelItems(){
        wheelItems = new ArrayList<>();
        Drawable d = getResources().getDrawable(R.drawable.ic_money, null);
        Bitmap bitmap = drawableToBitmap(d);

        wheelItems.add(new WheelItem(Color.parseColor("#F44336"), bitmap, "text1"));
        wheelItems.add(new WheelItem(Color.parseColor("#E91E63"), bitmap, "text2"));
        wheelItems.add(new WheelItem(Color.parseColor("#F44336"), bitmap, "text3"));
        wheelItems.add(new WheelItem(Color.parseColor("#E91E63"), bitmap, "text4"));
        wheelItems.add(new WheelItem(Color.parseColor("#F44336"), bitmap, "text5"));
        wheelItems.add(new WheelItem(Color.parseColor("#E91E63"), bitmap, "text6"));

        // 점수판에 데이터 반환
        luckyWheel.addWheelItems(wheelItems);
    }

    public static Bitmap drawableToBitmap (Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}