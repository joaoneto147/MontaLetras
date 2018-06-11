package com.example.ntx.educa;

import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class MontaLetrasActivity extends AppCompatActivity implements Cloneable {

    ViewGroup.LayoutParams params1, params2, params3;

    ImageView figura;
    ImageView input1, input2, input3;
    ImageView silabaInput1, silabaInput2, silabaInput3;
    ImageView silaba1, silaba2, silaba3;
    MontaLetras montaLetras;


    MediaPlayer sucess;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_monta_letras);

        dbHelper = new DBHelper(this.getBaseContext());
        db = dbHelper.getReadableDatabase();
        dbHelper.onUpgrade(db, 1, 2);

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

        params1 = silaba1.getLayoutParams();
        params2 = silaba2.getLayoutParams();
        params3 = silaba3.getLayoutParams();

        silabaInput1.setOnDragListener(dragListener);
        silabaInput2.setOnDragListener(dragListener);
        silabaInput3.setOnDragListener(dragListener);

        sucess = MediaPlayer.create(MontaLetrasActivity.this, R.raw.sucesso);

        MediaPlayer mediaPlayer = MediaPlayer.create(MontaLetrasActivity.this, R.raw.stage1);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        montaLetras = MontaLetrasDAO.buscarDadosNivelAtual(db, "ADM");
        DesenharImagensNivel();
    }

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

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            final View view = (View) event.getLocalState();
            int dragEvent = event.getAction();

            switch (dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    Boolean sucesso = false;
                    ImageView input = null;

                    if (view.getId() == silaba1.getId()){
                        if (v.getId() == silabaInput1.getId()){
                            sucesso = true;
                            input = silabaInput1;
                            silabaInput1.setImageDrawable(silaba1.getDrawable());

                            montaLetras.getSilabas().get(0).setLocalCorreto(true);
                        }
                    }else if (view.getId() == silaba2.getId()){
                        if (v.getId() == silabaInput2.getId()){
                            sucesso = true;
                            input = silabaInput2;
                            silabaInput2.setImageDrawable(silaba2.getDrawable());
                            montaLetras.getSilabas().get(1).setLocalCorreto(true);
                        }
                    }else if (view.getId() == silaba3.getId()){
                        if (v.getId() == silabaInput3.getId()){
                            sucesso = true;
                            input = silabaInput3;
                            silabaInput3.setImageDrawable(silaba3.getDrawable());
                            montaLetras.getSilabas().get(2).setLocalCorreto(true);
                        }
                    }

                    if (sucesso){
                        view.setOnTouchListener(null);
//                        view.animate()
//                                .x(input.getX())
//                                .y(input.getY())
//                                .setDuration(700)
//                                .start();
                        sucess.start();
                        if (montaLetras.VerificarFimJogo()){
                            montaLetras.TrocarNivel(db);
                            DesenharImagensNivel();
                        }
                    }else{
                        MediaPlayer mediaPlayer = MediaPlayer.create(MontaLetrasActivity.this, R.raw.wrong);
                        mediaPlayer.start();
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
        silabaInput2.setImageResource(getImageDrawableResId("inputletras"));
        silabaInput3.setImageResource(getImageDrawableResId("inputletras"));

        silaba1.setOnTouchListener(onTouchListener);
        silaba2.setOnTouchListener(onTouchListener);
        silaba3.setOnTouchListener(onTouchListener);

        silaba1.setLayoutParams(params1);
        silaba2.setLayoutParams(params2);
        silaba3.setLayoutParams(params3);

        figura.setImageResource(getImageDrawableResId(montaLetras.getNomeFigura()));
        silaba1.setImageResource(getImageDrawableResId(montaLetras.getSilabas().get(0).getSilaba()));
        silaba2.setImageResource(getImageDrawableResId(montaLetras.getSilabas().get(1).getSilaba()));

        if (montaLetras.getSilabas().size() == 3){
            silaba3.setVisibility(View.VISIBLE);
            silabaInput3.setVisibility(View.VISIBLE);
            input3.setVisibility(View.VISIBLE);
            silaba3.setImageResource(getImageDrawableResId(montaLetras.getSilabas().get(2).getSilaba()));
        }

    }


}
