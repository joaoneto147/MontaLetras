package com.example.ntx.educa;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

public class NomeImagemActivity extends AppCompatActivity {

    NomeImagem nomeImagem;
    Integer  nivelAtualVirtual = 1;
    String objetoCorreto;
    ImageView figura;
    ImageView img1, img2, img3, img4;
    MediaPlayer mp;
    MediaPlayer mediaPlayer;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nome_imagem);

        dbHelper = new DBHelper(this.getBaseContext());
        db = dbHelper.getReadableDatabase();

        figura = (ImageView) findViewById(R.id.figNomeImagem);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);

        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);
        img3.setOnClickListener(onClickListener);
        img4.setOnClickListener(onClickListener);

        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        nomeImagem = NomeImagemDAO.buscarDadosNomeImagem(db);
        IniciarJogo();
    }

    public int getImageDrawableResId(String imageId, String tipo) {
        Resources resources = getResources();
        return resources.getIdentifier(tipo + imageId, "drawable", this.getApplicationContext().getPackageName());
    }

    private void IniciarJogo(){
        figura.setImageResource(getImageDrawableResId(nomeImagem.nomesFiguras.get(nivelAtualVirtual + 1), "s"));

        img1.setImageResource(getImageDrawableResId(nomeImagem.nomesFiguras.get(nivelAtualVirtual), ""));
        img2.setImageResource(getImageDrawableResId(nomeImagem.nomesFiguras.get(nivelAtualVirtual + 1),  ""));
        img3.setImageResource(getImageDrawableResId(nomeImagem.nomesFiguras.get(nivelAtualVirtual + 2),  ""));
        img4.setImageResource(getImageDrawableResId(nomeImagem.nomesFiguras.get(nivelAtualVirtual + 3),  ""));

        objetoCorreto = nomeImagem.nomesFiguras.get(nivelAtualVirtual + 1);
        img2.setTag(objetoCorreto);

        img1.setTag(nomeImagem.nomesFiguras.get(nivelAtualVirtual));
        img3.setTag(nomeImagem.nomesFiguras.get(nivelAtualVirtual + 2));
        img4.setTag(nomeImagem.nomesFiguras.get(nivelAtualVirtual + 3));
    }

    public int getRawId(String somId) {
        Resources resources = getResources();
        return resources.getIdentifier(somId, "raw", this.getApplicationContext().getPackageName());
    }

    public void playSom(Integer musica){
        try{
            if (mp.isPlaying()) {
                mp.stop();
            }
            mp.reset();
            AssetFileDescriptor afd = null;

            afd = getResources().openRawResourceFd(musica);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepareAsync();
        }catch (IOException e){
            Log.e("", e.getMessage());
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getTag().equals(objetoCorreto)){
                playSom(getRawId(objetoCorreto));
                nivelAtualVirtual ++;

                if (nivelAtualVirtual == 20){
                    nivelAtualVirtual = 1;
                }

                IniciarJogo();
            }else{
                playSom(getRawId((String) view.getTag()));
            }

        }
    };
}
