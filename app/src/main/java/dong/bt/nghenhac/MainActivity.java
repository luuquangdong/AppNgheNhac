package dong.bt.nghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SeekBar sbTime;
    TextView tvSongName;
    TextView tvCurrentTime;
    TextView tvMaxTime;
    ImageButton ibPlay_Pause;
    ImageButton ibNext;
    ImageButton ibPrevious;
    MediaPlayer player;
    private List<Song> songsList;
    int index;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibPlay_Pause = findViewById(R.id.ibPlay_Pause);
        ibNext = findViewById(R.id.ibNext);
        ibPrevious = findViewById(R.id.ibPrevious);
        tvSongName = findViewById(R.id.tvSongName);
        sbTime = findViewById(R.id.sbTime);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvMaxTime = findViewById(R.id.tvMaxTime);

        songsList = new ArrayList<>();
        createSong();
        index = 0;
        khoiTao();
        runTimer();
    }


    public void addSong(String name, int id){
        Song song = new Song(name, id);
        songsList.add(song);
    }

    void createSong(){
        addSong("Anh thanh niên", R.raw.anhthanhnien);
        addSong("Tình sầu thiên thu muôn lối", R.raw.tinhsauthienthumuonloi);
        addSong("Sợ rằng em biết anh còn yêu em", R.raw.sorangembietanhconyeuem);
    }

    public void khoiTao(){
        Song song = songsList.get(index);
        player = MediaPlayer.create(this, song.getId());

        updateView(song.getTenBaiHat());
    }

    private void updateView(String songName){
        tvSongName.setText(songName);
        tvCurrentTime.setText(miliSecToMin(player.getCurrentPosition()));
        tvMaxTime.setText(miliSecToMin(player.getDuration()));
        sbTime.setMax(player.getDuration());
    }

    private void runTimer(){
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                // nếu nó đang chạy thì cập nhật
                if(player.isPlaying()){
                    tvCurrentTime.setText(miliSecToMin(player.getCurrentPosition()));
                    sbTime.setProgress(player.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    private String miliSecToMin(int time){
        int min = (int)(time/1000/60);
        int sec = (int)((time/1000)%60);
        return String.format("%02d:%02d", min, sec).toString();
    }

    public void onClickPlay_Pause(View view) {
        if(player.isPlaying()){
            player.pause();
            ibPlay_Pause.setImageResource(R.drawable.ic_play);
        }else{
            player.start();
            ibPlay_Pause.setImageResource(R.drawable.ic_pause);
        }
    }

    public void onClickNext(View view) {
        index++;
        if(index >= songsList.size()) {
            index = 0;
        }
        Song song = songsList.get(index);
        player.stop();
        player = MediaPlayer.create(this, song.getId());
        player.start();
        ibPlay_Pause.setImageResource(R.drawable.ic_pause);

        updateView(song.getTenBaiHat());
    }

    public void onClickPrevious(View view) {
        index--;
        if(index < 0) {
            index = songsList.size()-1;
        }
        Song song = songsList.get(index);
        player.stop();
        player = MediaPlayer.create(this, song.getId());
        player.start();
        ibPlay_Pause.setImageResource(R.drawable.ic_pause);

        updateView(song.getTenBaiHat());
    }
}
