package com.example.ntx.educa;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button btnMontaSilabas = (Button) findViewById(R.id.btnMontaSilabas);
        btnMontaSilabas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MontaLetrasActivity.class);
                startActivity(intent);
            }
        });


        Button btnJogoMemoria = (Button) findViewById(R.id.btnMemoria);
        btnJogoMemoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MemoriaActivity.class);
                startActivity(intent);
            }
        });

        Button btnNomeImagem = (Button) findViewById(R.id.btnNomeImagem);
        btnNomeImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NomeImagemActivity.class);
                startActivity(intent);
            }
        });

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.menu);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

    }

    @Override public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);

        if ((hasFocus) && (!mediaPlayer.isPlaying())){
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.menu);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } else if (!hasFocus){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }
    }
}
