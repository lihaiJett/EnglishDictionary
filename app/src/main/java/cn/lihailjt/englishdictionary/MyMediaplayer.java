package cn.lihailjt.englishdictionary;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MyMediaplayer {
    private MediaPlayer mediaPlayer;
    private static class MyMediaplayerHolder{
        private static MyMediaplayer INSTANCE = new MyMediaplayer();
    }
    private MyMediaplayer(){
        mediaPlayer = new MediaPlayer();
    }
    public static MyMediaplayer get(){
        return MyMediaplayerHolder.INSTANCE;
    }

    public void playWordSound(Context context, String word){
        try {
            Uri uri = Uri.parse("http://dict.youdao.com/dictvoice?audio="+word);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context.getApplicationContext(),uri);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
