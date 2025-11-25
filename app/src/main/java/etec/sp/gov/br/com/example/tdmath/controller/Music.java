package etec.sp.gov.br.com.example.tdmath.controller;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import etec.sp.gov.br.com.example.tdmath.R;

public class Music {
    private Integer telaCodigo = 0;
    private MediaPlayer musics; // da classe
    public MediaPlayer playMusic(Context context, int telaCodigo) throws IOException {
        int[] Musics = { // array para arquivos de som
                R.raw.m1,
                R.raw.m2,
                R.raw.m3,
                R.raw.m4,
                R.raw.m5,
                R.raw.m6,
                R.raw.m7
        };

        Integer musica;
        switch (telaCodigo) {
            case 1:
                musica = Musics[0];
                break;
            case 2:
                musica = Musics[1];
                break;
            case 3:
                musica = Musics[2];
                break;
            case 4:
                musica = Musics[3];
                break;
            case 5:
                musica = Musics[4];
                break;
            default:
                musica = Musics[0];
                break;
        }

        // https://youtu.be/mT-IAA8KjUU?si=avLJC9CtFFpM0es_
        // como usar MediaPlayer com arquivos de som
        musics = MediaPlayer.create(context, musica);
        musics.setLooping(true);
        return musics;
    }
}
