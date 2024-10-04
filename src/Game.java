import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

    public static final int ROCK_DELAY = 90;

    private Boolean paused;
    
    private int pauseDelay;
    private int restartDelay;
    private int rockDelay;

    private Fish fish;
    private ArrayList<Rock> rocks;
    private Keyboard keyboard;

    public int score;
    public Boolean gameover;
    public Boolean started;
    public final int title;
    
    public Game() {
        this.title = 0;
		keyboard = Keyboard.getInstance();
        restart();
    }

    public void restart() {
        paused = false;
        started = false;
        gameover = false;

        score = 0;
        pauseDelay = 0;
        restartDelay = 0;
        rockDelay = 0;

        fish = new Fish();
        rocks = new ArrayList<Rock>();
    }

    public void update() {
        watchForStart();

        if (!started)
            return;

        watchForPause();
        watchForReset();

        if (paused)
            return;

        fish.update();

        if (gameover)
            return;

        moveRocks();
        checkForCollisions();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<Render>();
        renders.add(new Render(0, 0, "lib/bg.png"));
        for (Rock rock : rocks)
            renders.add(rock.getRender());
        renders.add(new Render(0, 0, "lib/foreground.png"));
        renders.add(fish.getRender());
        return renders;
    }

    private void watchForStart() {

        if (!started && keyboard.isDown(KeyEvent.VK_SPACE)) {
            started = true;
        }
    }

    private void watchForPause() {
        if (pauseDelay > 0)
            pauseDelay--;

        if (keyboard.isDown(KeyEvent.VK_P) && pauseDelay <= 0) {
            paused = !paused;
            pauseDelay = 10;
        }
    }

    private void watchForReset() {
        if (restartDelay > 0)
            restartDelay--;

        if (keyboard.isDown(KeyEvent.VK_R) && restartDelay <= 0) {
            restart();
            restartDelay = 10;
            return;
        }
    }

    private void moveRocks() {
        rockDelay--;

        if (rockDelay < 0) {
            rockDelay = ROCK_DELAY;
            Rock northRock = null;
            Rock southRock = null;

            // Look for rocks off the screen
            for (Rock rock : rocks) {
                if (rock.x - rock.width < 0) {
                    if (northRock == null) {
                        northRock = rock;
                    } else if (southRock == null) {
                        southRock = rock;
                        break;
                    }
                }
            }

            if (northRock == null) {
                Rock rock = new Rock("north");
                rocks.add(rock);
                northRock = rock;
            } else {
                northRock.reset();
            }

            if (southRock == null) {
                Rock rock = new Rock("south");
                rocks.add(rock);
                southRock = rock;
            } else {
                southRock.reset();
            }

            northRock.y = southRock.y + southRock.height + 150;
        }

        for (Rock rock : rocks) {
            rock.update();
        }
    }

    private void checkForCollisions() {

        for (Rock rock : rocks) {
            if (rock.collides(fish.x, fish.y, fish.width, fish.height)) {
                gameover = true;
                fish.dead = true;
                Sound.RunMusic("res/gos.wav");
            } else if (rock.x == fish.x && rock.orientation.equalsIgnoreCase("south")) {
                score++;
                Sound.RunMusic("res/score.wav");
            }
        }

        // Ground + Fish collision
        if (fish.y + fish.height > App.HEIGHT - 80) {
            gameover = true;
            Sound.RunMusic("res/gos.wav");
            fish.y = App.HEIGHT - 80 - fish.height;
        }
    }
}
