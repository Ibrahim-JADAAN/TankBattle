package org.ibrahimjadaan.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.security.cert.CRLReason;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javafx.scene.input.KeyCode.SPACE;

public class TankGame extends Application {

    static final Random RAND = new Random();
    static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 50;
    public static final int MOVE = 3;
    static final Image PLAYER_IMG_UP = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\player_up.png");
    static final Image PLAYER_IMG_LEFT = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\player_left.png");
    static final Image PLAYER_IMG_RIGHT = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\player_right.png");
    static final Image PLAYER_IMG_DOWN = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\player_down.png");
    static final Image WALL_IMG = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\redBrick.png");

    //static final Image CLOUD_IMG = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\cloud.png");
    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_H = 128;
    static final Image EXPLOSION_IMG = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\explosions.png");
    static final int EXPLOSION_STEPS = 1;
    //static final Image ENEMY_IMG = new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\tanks\\enemy_tank.png");


    static final Image[] ENEMY_IMG = {
            new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\redBrick.png"),
            new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\redBrick.png"),
            new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\redBrick.png"),
            new Image("D:\\TankBattle\\src\\main\\resources\\Pictures\\redBrick.png"),
    };

    final int MAX_ENEMY_TANK = 3;
    final int MAX_SHOTS = 1;
    boolean gameOver = false;
    private static GraphicsContext gc;
    Tank player;
    List<Enemy> enemies = new ArrayList<>();
    List<Shot> playerShots;
    private double keyX;
    private double keyY;
    private int score;
    String bullet_sound = "D:\\TankBattle\\src\\main\\resources\\sounds\\bullet.mp3";

    //start
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //canvas.setCursor(Cursor.MOVE);
        //canvas.setOnMouseMoved(e -> keyX = e.getX());
//        stage.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
//                    if (key.getCode() == SPACE) {
//                        if (playerShots.size() < MAX_SHOTS) {
//                            playerShots.add(player.shoot());
//                            mediaPlayer = new MediaPlayer(med_bullet);
//                            mediaPlayer.play();
//                            System.out.println(player.shoot().dir);
//                        }
//                        System.out.println(player.getPosX() + "  ** " + player.getPosY());
//                        if(gameOver) {
//                            gameOver = false;
//                            setup();
//                        }
//                }
//        });

        keyX = WIDTH / 2;
        keyY = HEIGHT - PLAYER_SIZE;

        //canvas.setOnKeyPressed();

        stage.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == SPACE) {
                //System.out.println("bullet");
                if (playerShots.isEmpty()) {
                    playerShots.add(player.shoot(this.player));

                    Sounds.playerBulletSound();
                    // Bullet sound
                    //mediaPlayer = new MediaPlayer(med_bullet);
                    //mediaPlayer.play();

                } else if (playerShots.size() < MAX_SHOTS) {
                    //playerShots.add(player.shoot());
                    if (playerShots.get(0).posX - player.getPosX() > 20 && player.getPosX() - playerShots.get(0).posX > 20) {
                        playerShots.add(player.shoot(this.player));
                        //System.out.println(playerShots.get(0).posY - player.getPosY()  > 20 && player.getPosY() - playerShots.get(0).posY > 20);
                    }

                    if (playerShots.get(0).posY - player.getPosY() > 20 && player.getPosY() - playerShots.get(0).posY > 20) {
                        playerShots.add(player.shoot(this.player));
                    }

//                            switch (playerShots.get(playerShots.size() -1).dir) {
//                                case right:
//                                    if(playerShots.get(playerShots.size() -1).posX - player.getPosX()  > 20){
//                                        playerShots.add(player.shoot());
//                                    }
//                                    break;
//                                case down:
//                                    if(playerShots.get(playerShots.size() -1).posY - player.getPosY() > 20){
//                                        playerShots.add(player.shoot());
//                                    }
//                                    break;
//                                case left:
//                                    if(player.getPosX() - playerShots.get(playerShots.size() -1).posX > 20){
//                                        playerShots.add(player.shoot());
//                                    }
//                                    break;
//                                case up:
//                                    if(player.getPosY() - playerShots.get(playerShots.size() -1).posY > 20){
//                                        playerShots.add(player.shoot());
//                                    }
//                                    break;
//                            }

                }
            }

            //System.out.println(player.getPosX() + "  ** " + player.getPosY());
            if (gameOver) {
                gameOver = false;
                setup();
            }
        });

        stage.addEventFilter(KeyEvent.ANY, key -> {
            switch (key.getCode()) {
                case RIGHT:
                    player.direction = Dir.right;
                    player.setImg(PLAYER_IMG_RIGHT);
                    if (player.getPosX() + MOVE <= 750) keyX = player.getPosX() + MOVE * 4;
                    break;
                case DOWN:
                    player.direction = Dir.down;
                    player.setImg(PLAYER_IMG_DOWN);
                    if (player.getPosY() + MOVE < 550) keyY = player.getPosY() + MOVE * 4;
                    break;
                case LEFT:
                    player.direction = Dir.left;
                    player.setImg(PLAYER_IMG_LEFT);
                    if (player.getPosX() - MOVE >= 0) keyX = player.getPosX() - MOVE * 4;
                    break;
                case UP:
                    player.direction = Dir.up;
                    player.setImg(PLAYER_IMG_UP);
                    if (player.getPosY() - MOVE >= 0) keyY = player.getPosY() - MOVE * 4;
                    break;
            }
        });

//        stage.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
//            if(key.getCode() == SPACE){
//                if(playerShots.size() < MAX_SHOTS) playerShots.add(player.shoot());
//                System.out.println(player.getPosX() + "  ** " + player.getPosY());
//                if(gameOver) {
//                    gameOver = false;
//                    setup();
//                }
//            }
//        });

//        canvas.setOnMouseClicked(e -> {
//            if(playerShots.size() < MAX_SHOTS) playerShots.add(player.shoot());
//            System.out.println(player.getPosX() + "  ** " + player.getPosY());
//            if(gameOver) {
//                gameOver = false;
//                setup();
//            }
//        });

        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("B A T T L E   T A N K");
        stage.setAlwaysOnTop(true);
        stage.getIcons().add((PLAYER_IMG_RIGHT));
        stage.show();

    }


    // Setup the game
    private void setup() {
        playerShots = new ArrayList<>();
        for (int i = 0; i < enemies.size() - 1; i++) {
            enemies.get(i).enemyShots = new ArrayList<>();
        }
        player = new Tank(WIDTH / 2, HEIGHT - PLAYER_SIZE / 2, PLAYER_SIZE, PLAYER_IMG_UP, Dir.up);
        score = 0;
        for (int i = 0; i < MAX_ENEMY_TANK; i++) {
            enemies.add(newEnemyTank());
        }
        //IntStream.range(0, MAX_ENEMY_TANK).mapToObj(i -> this.newEnemyTank()).forEach(enemies::add);
    }

    //run Graphics
    private void run(GraphicsContext gc) {
        gc.setFill(Color.DARKOLIVEGREEN);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60, 20);
        System.out.println("Your score is: " + score);

        //Cloud cloud = new Cloud();
        //cloud.draw();
        gc.drawImage(WALL_IMG, 100, 200, 50, 70);
        gc.drawImage(WALL_IMG, 250, 400, 90, 30);
        gc.drawImage(WALL_IMG, 480, 100, 50, 110);
        gc.drawImage(WALL_IMG, 600, 450, 60, 140);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            //System.out.println("game over");
            gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2, HEIGHT / 2.5);
            System.out.println("Your score is: " + score);
            //	return;
        }

        if (gameOver) {
            gameOver = false;

            Sounds.gameOverSound();

            for (Enemy value : enemies) {
                value.enemyShots.clear();
            }
            enemies.clear();
            playerShots.clear();
            //Platform.exit();
        }


        player.update();
        player.draw();


        player.posX = (int) keyX;
        player.posY = (int) keyY;

//        enemies.stream().peek(TankGame.Tank::update).peek(TankGame.Tank::draw).forEach(e ->  {
//            if(player.collide(e) && !player.exploding) {
//                player.explode();
//            }
//        });

        for (Enemy value : enemies) {

            boolean b = RAND.nextBoolean();
            int r = RAND.nextInt(1,499);

            if(value.enemyShots.isEmpty() && b && r % 10 == 0){
                //System.out.println("Enemies are shooting");
                value.enemyShots.add(value.shoot(value));
                Sounds.enemyBulletSound();
            }

            value.update();// Update the enemy tank
            value.draw();// Draw the enemy tank



            for (int j = 0; j < value.enemyShots.size(); j++) {
                Shot shot = value.enemyShots.get(j);
                //enemies.get(i).enemyShots.get(j).update();
                value.enemyShots.get(j).draw();





                if (shot.posY < 0 || shot.posX < 0 || shot.posY > HEIGHT || shot.posX > WIDTH || shot.toRemove) {
                    value.enemyShots.remove(j);
                    continue;
                }

                if (shot.collide(player) && !player.exploding) {
                    score = 0;
                    System.out.println("Your score is: " + score);
                    player.explode();
                    // Player explosion sound to be added here
                    Sounds.playerExplosionSound();
                    shot.toRemove = true;
                }

                value.updateEnemyShot(value.enemyShots);
                if (value.collide(player) && !value.exploding) {
                    // Enemy explosion sound to be added here
                    Sounds.enemyExplosionSound();
                    value.explode();
                }
            }


//            enemyShots.get(i).update();
//            enemyShots.get(i).draw();

            // Check for collision with the player
            if (player.collide(value) && !player.exploding) {
                playerShots.clear();
                player.explode();
            }

        }


        //******************************************************************************

        for (int i = playerShots.size() - 1; i >= 0; i--) {
            Shot shot = playerShots.get(i);
            if (shot.posY < 0 || shot.posX < 0 || shot.posY > HEIGHT || shot.posX > WIDTH || shot.toRemove) {
                playerShots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();

            for (Tank enemy : enemies) {
                if (shot.collide(enemy) && !enemy.exploding) {
                    score++;
                    enemy.explode();
                    Sounds.enemyExplosionSound();
                    shot.toRemove = true;
                }
            }
        }


        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).destroyed) {
                enemies.set(i, newEnemyTank());
            }
        }
        gameOver = player.destroyed;

    }

    //players
    public class Tank {
        int posX, posY, size;
        Dir direction;
        boolean exploding, destroyed;
        Image img;
        int explosionStep = 0;

        public void setImg(Image img) {
            this.img = img;
        }

        public int getPosX() {
            return posX;
        }

        public int getPosY() {
            return posY;
        }

        public Tank(int posX, int posY, int size, Image img, Dir direction) {
            this.posX = posX;
            this.posY = posY;
            this.size = size;
            this.img = img;
            this.direction = direction;
        }

        public Shot shoot(Tank player) {

            int x, y;

            y = switch (player.direction) {
                case right -> {
                    x = posX + size / 2 - Shot.size * 2;
                    yield posY + size / 2 - Shot.size / 2;
                }
                case down -> {
                    x = posX + size / 2 - Shot.size / 2;
                    yield posY + 2;
                }
                case left -> {
                    x = posX + size / 2 + Shot.size;
                    yield posY + size / 2 - Shot.size / 2;
                }
                case up -> {
                    x = posX + size / 2 - Shot.size / 2;
                    yield posY + 3 * Shot.size;
                }
            };
            //return new Shot(x, y, player.direction);
            return new Shot(x, y, direction);
        }


        public void update() {
            if (exploding) explosionStep++;
            destroyed = explosionStep > EXPLOSION_STEPS;
        }

        public void draw() {
            if (exploding) {
                gc.drawImage(EXPLOSION_IMG, explosionStep % EXPLOSION_COL * EXPLOSION_W, (explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1, EXPLOSION_W, EXPLOSION_H, posX, posY, size, size);
            } else {
                gc.drawImage(img, posX, posY, size, size);
            }
        }

        public boolean collide(Tank other) {
            int d = distance(this.posX + size / 2, this.posY + size / 2, other.posX + other.size / 2, other.posY + other.size / 2);
            return d < other.size / 2 + this.size / 2;
        }

        public void explode() {
            exploding = true;
            explosionStep = -1;
        }
    }

    // cloud
//    public class Cloud{
//        int posX, posY;
//        private double opacity;
//
//        public Cloud(){
//
//            posX = RAND.nextInt(20,650);
//            posY = RAND.nextInt(20,500);
//            opacity = RAND.nextFloat();
//        }
//
//
//        public void draw() {
//            if(opacity > 0.8) opacity-=0.02;
//            if(opacity < 0.1) opacity+=0.02;
//            gc.setFill(Color.rgb(r, g, b, opacity));
//            gc.setGlobalAlpha(opacity);
//
//            // Draw the image
//            gc.drawImage(image, 0, 0);
//
//            gc.drawImage(CLOUD_IMG, posX, posY, 90, 160);
//            gc.setGlobalAlpha(1.0);
//            //gc.fillOval(posX, posY, w, h);
//
//            gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
//            gc.fillOval(50, 50, 80, 60);
//            gc.fillOval(120, 50, 80, 60);
//            gc.fillOval(80, 30, 80, 60);
//            gc.fillOval(70, 70, 80, 60);
//            gc.fillOval(130, 70, 80, 60);
//            posY+= RAND.nextInt(4,7);
//            posX+=RAND.nextInt(4,7);
//        }
//    }
//

    //bullets
    public class Shot {
        Dir dir;
        public boolean toRemove;
        int posX, posY;
        int shotSpeed = 50;
        static final int size = 12;


        public Shot(int posX, int posY, Dir dir) {
            this.posX = posX;
            this.posY = posY;
            this.dir = dir;
        }

        public void update() {

            for (Shot playerShot : playerShots) {

                //playerShots.get(i).dir
                switch (playerShot.dir) {
                    case right:
                        playerShot.posX += shotSpeed;
                        //posX -= shotSpeed;
                        break;
                    case down:
                        playerShot.posY += shotSpeed;
                        //posY += shotSpeed;
                        break;
                    case left:
                        playerShot.posX -= shotSpeed;
                        //posX += shotSpeed;
                        break;
                    case up:
                        playerShot.posY -= shotSpeed;
                        //posY -= shotSpeed;
                        break;
                }
            }
        }

        public void draw() {

            gc.setFill(Color.YELLOW);
            if (score >= 10) {
            gc.setFill(Color.DARKRED);
            shotSpeed = 60;
            gc.fillOval(posX-5, posY-5, size+8, size+8);
            } else {
            gc.fillOval(posX, posY, size, size);
             }
        }

        public boolean collide(Tank Tank) {
            int distance = distance(this.posX + size / 2, this.posY + size / 2, Tank.posX + Tank.size / 2, Tank.posY + Tank.size / 2);
            return distance < Tank.size / 2 + size / 2;
        }


    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    ////**************************************************************************************************

    //computer player
    public class Enemy extends Tank {
        int SPEED = (score / 3) + 5;
        List<Shot> enemyShots;
        int shotSpeed = 50;

        Image im;

        public Enemy(int posX, int posY, int size, Image image, Dir direction) {
            super(posX, posY, size, image, direction);

            enemyShots = new ArrayList<>();
            this.im = image;

        }

        public void updateEnemyShot(List<Shot> shots) {

            //for(int i = 0; i< enm.size(); i++) {

            for (Shot shot : shots) {

                switch (shot.dir) {
                    case right:
                        shot.posX += shotSpeed;
                        //posX -= shotSpeed;
                        break;
                    case down:
                        shot.posY += shotSpeed;
                        //posY += shotSpeed;
                        break;
                    case left:
                        shot.posX -= shotSpeed;
                        //posX += shotSpeed;
                        break;
                    case up:
                        shot.posY -= shotSpeed;
                        //posY -= shotSpeed;
                        break;
                }
            }
        }

        public void update() {
            super.update();


            if (!exploding && !destroyed) {

//                for (int i = 0; i < Bombs.size(); i++) {
//                    int r = RAND.nextInt(1,2400);
//                    if(r == 100){
//                        Bombs.get(i).direction = Dir.up;
//                    }
//                    if(r == 2){
//                        Bombs.get(i).direction = Dir.down;
//                    }
//                    if(r == 3){
//                        Bombs.get(i).direction = Dir.right;
//                    }
//                    if(r == 4){
//                        Bombs.get(i).direction = Dir.left;
//                    }
//                    switch (Bombs.get(i).direction) {
//                        case right:
//                            Bombs.get(i).setImg(PLAYER_IMG_RIGHT);
//                            if (Bombs.get(i).getPosX() + MOVE <= 750){
//                                keyX = player.getPosX() + MOVE;
//                            }
//                            else {
//                                Bombs.get(i).direction = Dir.left;
//                            }
//                            break;
//                        case down:
//                            Bombs.get(i).setImg(PLAYER_IMG_DOWN);
//                            if (Bombs.get(i).posY + SPEED < HEIGHT - PLAYER_SIZE){
//                                Bombs.get(i).posY += SPEED;
//                            }
//                            else {
//                                Bombs.get(i).direction = Dir.up;
//                            }
//                            break;
//                        case left:
//                            Bombs.get(i).setImg(PLAYER_IMG_LEFT);
//                            if (player.getPosX() - MOVE >= 0){
//                                keyX = player.getPosX() - MOVE;
//                            }
//                            else {
//                                Bombs.get(i).direction = Dir.right;
//                            }
//                            break;
//                        case up:
//                            Bombs.get(i).setImg(PLAYER_IMG_UP);
//                            if (Bombs.get(i).posY >= 0){
//                                Bombs.get(i).posY -= SPEED;
//                            }
//                            else {
//                                Bombs.get(i).direction = Dir.down;
//                            }
//                            break;
//                    }
//                }


                if (posY > HEIGHT) {
                    SPEED = SPEED * -1;
                }
                if (posY < 0) {
                    SPEED = SPEED * -1;
                }
                if (posX > WIDTH) {
                    SPEED = SPEED * -1;
                }
                if (posX < 0) {
                    SPEED = SPEED * -1;
                }

                int b = RAND.nextInt(0, 50);

                if (b == 1) {
                    direction = Dir.up;
                }
                if (b == 2) {
                    direction = Dir.right;
                }
                if (b == 3) {
                    direction = Dir.left;
                }
                if (b == 4) {
                    direction = Dir.down;
                }

                switch (direction) {
                    case right:
                        if (posX + SPEED < WIDTH - PLAYER_SIZE) {
                            posX += SPEED;
                            img = PLAYER_IMG_RIGHT;
                        } else {
                            direction = Dir.left;
                        }
                        break;
                    case down:
                        if (posY + SPEED < HEIGHT - PLAYER_SIZE) {
                            posY += SPEED;
                            img = PLAYER_IMG_DOWN;
                        } else {
                            direction = Dir.up;
                        }
                        break;
                    case left:
                        if (posX - SPEED > 0) {
                            posX -= SPEED;
                            img = PLAYER_IMG_LEFT;
                        } else {
                            direction = Dir.right;
                        }
                        break;
                    case up:
                        if (posY - SPEED > 0) {
                            posY -= SPEED;
                            img = PLAYER_IMG_UP;
                        } else {
                            direction = Dir.down;
                        }
                        break;
                }
            }
        }

        public Shot shoot(Enemy enemy) {

            int x, y;

            y = switch (enemy.direction) {
                case right -> {
                    x = posX + size / 2 - Shot.size * 2;
                    yield posY + size / 2 - Shot.size / 2;
                }
                case down -> {
                    x = posX + size / 2 - Shot.size / 2;
                    yield posY + 2;
                }
                case left -> {
                    x = posX + size / 2 + Shot.size;
                    yield posY + size / 2 - Shot.size / 2;
                }
                case up -> {
                    x = posX + size / 2 - Shot.size / 2;
                    yield posY + 3 * Shot.size;
                }
            };
            //return new Shot(x, y, player.direction);
            return new Shot(x, y, direction);
        }
    }

    Enemy newEnemyTank() {
        // ENEMY_IMG[ENEMY_IMG.length - 1]
        return new Enemy(50 + RAND.nextInt(WIDTH - 100), 0, PLAYER_SIZE, PLAYER_IMG_DOWN, Dir.down);
    }

    public static void main(String[] args) {
        launch();
    }
}