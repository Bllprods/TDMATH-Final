package etec.sp.gov.br.com.example.tdmath.telas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import etec.sp.gov.br.com.example.tdmath.R;
import etec.sp.gov.br.com.example.tdmath.controller.BaseActivity;
import etec.sp.gov.br.com.example.tdmath.controller.MapController;
import etec.sp.gov.br.com.example.tdmath.model.Mapa;
import etec.sp.gov.br.com.example.tdmath.model.nivel;

public class MenuFases extends BaseActivity {
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fases);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        int fontSize = sharedPref.getInt("font_size", 20);
        Config.updateFontSize(findViewById(R.id.main), fontSize);

        Toolbar header = findViewById(R.id.header);
        setSupportActionBar(header);

        container = findViewById(R.id.LnLm);

        MapController map = new MapController(this);
        ArrayList<Mapa> mapas = map.consultaMapas();

        int index = 0;

        for (Mapa m : mapas) {
            final Mapa mapaLocal = m;

            LinearLayout containerMini = new LinearLayout(this);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            containerMini.setOrientation(LinearLayout.VERTICAL);

            int marginPx = dpToPx(16);
            linearParams.setMargins(marginPx, marginPx, marginPx, marginPx);

            linearParams.gravity = (index % 2 == 0) ? Gravity.START : Gravity.END;
            containerMini.setLayoutParams(linearParams);

            // ============================
            // BOTÃO DO MUNDO
            // ============================

            ImageButton MundoBtn = new ImageButton(this);

            // GARANTIR nome certo da imagem
            String nomeImg = "imgmap" + mapaLocal.getIdMapa();

            int recursoImg = getResources().getIdentifier(
                    nomeImg,
                    "drawable",
                    getPackageName()
            );

            int sizePx = dpToPx(140);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
            params.gravity = Gravity.CENTER_HORIZONTAL;

            MundoBtn.setLayoutParams(params);
            MundoBtn.setBackground(null);

            if (recursoImg != 0) {
                MundoBtn.setImageResource(recursoImg);
            } else {
                MundoBtn.setImageResource(android.R.color.transparent);
            }

            MundoBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
            MundoBtn.setAdjustViewBounds(true);

            MundoBtn.setClickable(false);

            containerMini.addView(MundoBtn);

            // ============================
            // FASES
            // ============================

            LinearLayout ctFases = new LinearLayout(this);
            ctFases.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lpFases = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lpFases.setMargins(dpToPx(8), dpToPx(4), dpToPx(8), dpToPx(8));
            ctFases.setLayoutParams(lpFases);
            ctFases.setVisibility(View.GONE);

            List<nivel> niveis = mapaLocal.getNiveis();

            if (niveis == null || niveis.isEmpty()) {
                TextView vazio = new TextView(this);
                vazio.setText("Nenhum nível disponível");
                vazio.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize - 2);
                ctFases.addView(vazio);
            } else {
                for (nivel nv : niveis) {
                    LinearLayout linhaNivel = new LinearLayout(this);
                    linhaNivel.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams lpLinha = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    lpLinha.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
                    linhaNivel.setLayoutParams(lpLinha);

                    ImageButton levelBtn = new ImageButton(this);
                    LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(dpToPx(56), dpToPx(56));
                    levelBtn.setLayoutParams(lpBtn);
                    levelBtn.setBackgroundResource(R.drawable.fasescir);
                    levelBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    TextView levelTxt = new TextView(this);
                    levelTxt.setText("Fase " + nv.getIdNivel());
                    levelTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize - 2);

                    levelBtn.setOnClickListener(v -> {
                        Intent fase = new Intent(MenuFases.this, tela_jogos.class);
                        fase.putExtra("idMapa", mapaLocal.getIdMapa());
                        fase.putExtra("idNivel", nv.getIdNivel());
                        startActivity(fase);
                    });

                    linhaNivel.addView(levelBtn);
                    linhaNivel.addView(levelTxt);
                    ctFases.addView(linhaNivel);
                }
            }

            containerMini.addView(ctFases);

            containerMini.setOnClickListener(v -> {
                ctFases.setVisibility(ctFases.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });

            container.addView(containerMini);
            index++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mapas_page, menu);
        return true;
    }

    protected int getcodeAct() {
        return 5;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }
}
