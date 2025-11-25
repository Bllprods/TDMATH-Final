package etec.sp.gov.br.com.example.tdmath.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Mapa {
    private int idMapa;
    private String nome;
    private String imgUrlMap;

    public Mapa(int idMapa, String nome, String imgUrlMap) {
        this.idMapa = idMapa;
        this.nome = nome;
        this.imgUrlMap = imgUrlMap;
    }

    public int getIdMapa() { return idMapa; }
    public String getNome() { return nome; }
    public String getImgUrlMap() { return imgUrlMap; }
}
