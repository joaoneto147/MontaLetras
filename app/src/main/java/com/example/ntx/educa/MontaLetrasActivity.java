package com.example.ntx.educa;

import android.content.ClipData;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Collections;


public class MontaLetrasActivity extends AppCompatActivity implements Cloneable {

    ImageView figura;
    ImageView input1, input2, input3;
    ImageView silabaInput1, silabaInput2, silabaInput3;
    ImageView silaba1, silaba2, silaba3;
    MontaLetras montaLetras;
    MediaPlayer mp;
    MediaPlayer mediaPlayer;

    DBHelper dbHelper;

    SQLiteDatabase db;

    @Override public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);

        if ((hasFocus) && (!mediaPlayer.isPlaying())){
            mediaPlayer = MediaPlayer.create(MontaLetrasActivity.this, R.raw.stage1);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } else if (!hasFocus){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_monta_letras);

        dbHelper = new DBHelper(this.getBaseContext());
        db = dbHelper.getReadableDatabase();

        figura = (ImageView) findViewById(R.id.figura);

        input1 = (ImageView) findViewById(R.id.input1);
        input2 = (ImageView) findViewById(R.id.input2);
        input3 = (ImageView) findViewById(R.id.input3);

        silabaInput1 = (ImageView) findViewById(R.id.silabaInput1);
        silabaInput2 = (ImageView) findViewById(R.id.silabaInput2);
        silabaInput3 = (ImageView) findViewById(R.id.silabaInput3);

        silaba1 = (ImageView) findViewById(R.id.silaba1);
        silaba2 = (ImageView) findViewById(R.id.silaba2);
        silaba3 = (ImageView) findViewById(R.id.silaba3);

        silabaInput1.setOnDragListener(dragListener);
        silabaInput2.setOnDragListener(dragListener);
        silabaInput3.setOnDragListener(dragListener);


        mp = new MediaPlayer();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });


        mediaPlayer = MediaPlayer.create(MontaLetrasActivity.this, R.raw.stage1);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        montaLetras = MontaLetrasDAO.buscarDadosNivelAtual(db, "ADM");
        DesenharImagensNivel();
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            mediaPlayer.stop();
        }
    };

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ((event.getAction() == MotionEvent.ACTION_MOVE)) {
                ClipData data = ClipData.newPlainText("simple_text", "text");

                View.DragShadowBuilder sb = new View.DragShadowBuilder(v);
                v.startDrag(data, sb, v, 0);
            }
            return true;
        }

    };

    public void SilabaInseridaLocal(Integer tagSilaba){
        String silaba = montaLetras.getSilabaTag(tagSilaba);
        switch (tagSilaba){
            case 0:
                silabaInput1.setImageResource(getImageDrawableResId(silaba));
                break;
            case 1:
                silabaInput2.setImageResource(getImageDrawableResId(silaba));
                break;
            case 2:
                silabaInput3.setImageResource(getImageDrawableResId(silaba));
                break;
        }
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

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            final View dest = (View) event.getLocalState();
            int dragEvent = event.getAction();

            switch (dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    Boolean sucesso = false;

                    ImageView silabaClicada = (ImageView) v;
                    ImageView silabaInput = (ImageView) dest;

                    if (silabaClicada.getTag() == silabaInput.getTag()){
                        Integer posic = (Integer) silabaClicada.getTag();
                        sucesso = true;
                        SilabaInseridaLocal((Integer) silabaInput.getTag());
                        montaLetras.getSilabas().get(posic).setLocalCorreto(true);
                    }

                    if (sucesso){
                        dest.setOnTouchListener(null);
                        playSom(R.raw.sucesso);
                        if (montaLetras.VerificarFimJogo()){
                            montaLetras.TrocarNivel(db);
                            DesenharImagensNivel();
                        }
                    }else{
                        playSom(R.raw.wrong);
                        vibrar();
                    }

                    break;
            }
            return true;
        }
    };

    private void vibrar() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
            }
        }, 30);
    }

    public int getImageDrawableResId(String imageId) {
        Resources resources = getResources();
        return resources.getIdentifier(imageId, "drawable", this.getApplicationContext().getPackageName());
    }

    private void DesenharImagensNivel(){

        silabaInput1.setImageResource(getImageDrawableResId("inputletras"));
        silabaInput1.setTag(0);
        silabaInput2.setImageResource(getImageDrawableResId("inputletras"));
        silabaInput2.setTag(1);
        silabaInput3.setImageResource(getImageDrawableResId("inputletras"));
        silabaInput3.setTag(2);

        silaba1.setOnTouchListener(onTouchListener);
        silaba2.setOnTouchListener(onTouchListener);
        silaba3.setOnTouchListener(onTouchListener);

        figura.setImageResource(getImageDrawableResId(montaLetras.getNomeFigura()));

        Collections.shuffle(montaLetras.getSilabas());

        silaba1.setImageResource(getImageDrawableResId(montaLetras.getSilabas().get(0).getSilaba()));
        silaba1.setTag(montaLetras.getSilabas().get(0).getPosic());

        silaba2.setImageResource(getImageDrawableResId(montaLetras.getSilabas().get(1).getSilaba()));
        silaba2.setTag(montaLetras.getSilabas().get(1).getPosic());

        if (montaLetras.getNivel() > 13){
            silaba3.setVisibility(View.VISIBLE);
            silabaInput3.setVisibility(View.VISIBLE);
            input3.setVisibility(View.VISIBLE);
            silaba3.setImageResource(getImageDrawableResId(montaLetras.getSilabas().get(2).getSilaba()));
            silaba3.setTag(montaLetras.getSilabas().get(2).getPosic());
        } else{
            silaba3.setVisibility(View.GONE);
            silabaInput3.setVisibility(View.GONE);
            input3.setVisibility(View.GONE);
        }

    }


}
