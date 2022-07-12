package com.yajirang.wonpandoligi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    int roullete_count = 2;

    String option_text1;
    String option_text2;
    String option_text3;
    String option_text4;
    String option_text5;
    String option_text6;
    String option_text7;
    String option_text8;



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

        //룰렛 카운트 텍스트뷰
        TextView r_count = findViewById(R.id.rouletteCountTv);
        r_count.setText(String.valueOf(roullete_count));



        //xml 변수 저장
        luckyWheel = findViewById(R.id.luck_wheel);
        //점수판 데이터 생성
        generateWheelItems();

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                //변수에 아이템 저장
                //WheelItem wheelitem = wheelItems.get(Integer.parseInt(point) - 1);
                WheelItem wheelitem = wheelItems.get(Integer.parseInt(point) - 1);

                //변수에 텍스트 저장
                String money = wheelitem.text;

                //메세지 출력
                Toast.makeText(MainActivity.this, money, Toast.LENGTH_SHORT).show();
            }
        });

        //스타트버튼
        Button start = findViewById(R.id.spin_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                point = String.valueOf(random.nextInt(6) + 1); //1~6
                luckyWheel.rotateWheelTo(Integer.parseInt(point));
            }
        });


        //마이너스버튼
        Button minus = findViewById(R.id.minusBtn);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(roullete_count < 9 && roullete_count > 2){
                    roullete_count -= 1;
                    r_count.setText(String.valueOf(roullete_count));
                }

            }
        });

        //플러스버튼
        Button plus = findViewById(R.id.plusBtn);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(roullete_count < 8 && roullete_count > 1){
                    roullete_count += 1;
                    r_count.setText(String.valueOf(roullete_count));
                }

            }
        });

    }

    //데이터 저장
    private void generateWheelItems(){
        wheelItems = new ArrayList<>();
        Drawable d = getResources().getDrawable(R.drawable.ic_money, null);
        Bitmap bitmap = drawableToBitmap(d);

        // TODO: 2022-07-12 wheelitems 반복문 생성 필요, roulettecount 까지 반복문 생성, 텍스트는 배열 또는 구조체로 생성 후 반복문을 통해 삽입, 색상에 맞는 if문 포함 필요


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