package ui.login;


import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Home extends JPanel {

    private List<ModelLocation> locations;
    private int index = 0;
    private HomeOverlay homeOverlay;
    private static MediaPlayerFactory factory;
    private static EmbeddedMediaPlayer mediaPlayer;

    public Home() {
        init();
        testData();
    }

    private void init() {
        factory = new MediaPlayerFactory();
        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
        Canvas canvas = new Canvas();
        mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(canvas));
        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                if (newTime >= mediaPlayer.status().length() - 1000) {
                    mediaPlayer.controls().setPosition(0);
                }
            }
        });
        setLayout(new BorderLayout());
        add(canvas);
    }

    private void testData() {

//        Font f = new Font("Goudy Stout", Font.PLAIN, 40);
        String title = "CHƯƠNG TRÌNH QUẢN LÝ \n\t\tHIỆU THUỐC TÂY";

        String description = "\n\t\tHUỲNH THANH GIANG - 22716371\n\n" +
                "\t\tNGUYỄN THỊ MỸ DUYÊN - 22721461 \n\n" +
                "\t\tHỒ QUANG NHÂN - 22715701\n\n" +
                "\t\tPHAN PHƯỚC HIỆP - 22719711";


        locations = new ArrayList<>();
        locations.add(new ModelLocation(title,
                description,
                "src/main/java/ui/login/bg_ptud.mp4"));

//        locations.add(new ModelLocation("",
//                "",
//                "src/main/java/ui/login/bg_dna_digital.mp4"));
//
//        locations.add(new ModelLocation("",
//                "",
//                "src/main/java/ui/login/videoCoffee_Bg3.mp4"));

    }

    public void initOverlay(JFrame frame) {
        homeOverlay = new HomeOverlay(frame, locations);
        homeOverlay.getOverlay().setEventHomeOverlay(index1 -> {
            play(index1);
        });
        mediaPlayer.overlay().set(homeOverlay);
        mediaPlayer.overlay().enable(true);
    }

    public void play(int index) {
        this.index = index;
        ModelLocation location = locations.get(index);
        if (mediaPlayer.status().isPlaying()) {
            mediaPlayer.controls().stop();
        }
        mediaPlayer.media().play(location.getVideoPath());
        mediaPlayer.controls().play();
        homeOverlay.getOverlay().setIndex(index);

    }

    public HomeOverlay getHomeOverlay() {
        return homeOverlay;
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.controls().stop();
            mediaPlayer.release();
        }
        if (factory != null) {
            factory.release();
        }
    }

}
