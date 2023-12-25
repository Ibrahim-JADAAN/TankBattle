package org.ibrahimjadaan.spaceinvaders;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class Sounds {

    static final File ENEMY_BULLET_FILE = new File("D:\\TankBattle\\src\\main\\resources\\sounds\\cute_bullet.mp3");
    static final File PLAYER_BULLET_FILE = new File("D:\\TankBattle\\src\\main\\resources\\sounds\\bullet_3.wav");
    static File GAME_OVER_FILE = new File("D:\\TankBattle\\src\\main\\resources\\sounds\\game_over.wav");

    //static File enemy_bullet_file = new File("D:\\TankBattle\\src\\main\\resources\\sounds\\cute_bullet.mp3");
    //static File enemy_bullet_file = new File("D:\\TankBattle\\src\\main\\resources\\sounds\\cute_bullet.mp3");

    static Media med_enemy_bullet = new Media(ENEMY_BULLET_FILE.toURI().toString());
    static Media med_player_bullet = new Media(PLAYER_BULLET_FILE.toURI().toString());
    static Media med_game_over = new Media(GAME_OVER_FILE.toURI().toString());
    static MediaPlayer mediaPlayer1;
    static MediaPlayer mediaPlayer2;
    static MediaPlayer mediaPlayer3 = new MediaPlayer(med_game_over);


    public static void enemyBulletSound(){
        mediaPlayer1 = new MediaPlayer(med_enemy_bullet);
        mediaPlayer1.play();
    }

    public static void playerBulletSound(){
        mediaPlayer2 = new MediaPlayer(med_player_bullet);
        mediaPlayer2.play();
    }

    public static void gameOverSound(){

        MediaPlayer.Status status = mediaPlayer3.getStatus();
        if (status != MediaPlayer.Status.PLAYING) {
            mediaPlayer3.play();
        }

    }




}
