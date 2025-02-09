package breakoutbasic.scenes;

import breakoutbasic.utils.Constants;
import breakoutbasic.Breakout;
import breakoutbasic.grid.Grid;
import breakoutbasic.objects.Ball;
import breakoutbasic.objects.Block;
import breakoutbasic.objects.Paddle;
import breakoutbasic.utils.CollisionChecker;
import breakoutbasic.utils.EdgeHit;
import breakoutbasic.utils.WindowUtils;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Random;

public class PlayScene extends AbstractScene {

    private boolean playing = false;

    private final Grid grid;

    private Ball ball;

    private final Paddle paddle;

    private Text startOrPauseText;

    private Text infoText;

    private Text addInfoText;

    private final Random random = new Random();

    public PlayScene(int n, int m) {
        this.getScene().setFill(Color.BLACK);
        this.grid = new Grid(this, n, m);

        // Creating paddle
        this.paddle = new Paddle(this, WindowUtils.getWindowWidth() / 2 - ((double) Constants.PADDLE_WIDTH / 2), WindowUtils.getWindowHeight() * 0.8, 1.0, Constants.PADDLE_HEIGHT, Constants.PADDLE_WIDTH);

        // Spawning ball
        spawnBall();

        // Add start or pause text
        addStartOrPauseText();

        // Setup Keyboard events
        setupKeyPressedEvents();
    }

    private void spawnBall() {
        // Reset ball position and velocity
        double[] vel = calculateStartVelForBall();

        // Creating ball
        this.ball = new Ball(this, this.paddle.getPosX() + paddle.getWidth() / 2 - Constants.BALL_RADIUS / 2d, this.paddle.getPosY() - 2 * paddle.getHeight(), vel[0], vel[1], Constants.BALL_RADIUS);
        ball.getNode().relocate(this.paddle.getPosX() + paddle.getWidth() / 2 - (Constants.BALL_RADIUS / 2), this.paddle.getPosY() - 2 * paddle.getHeight());
    }

    private double[] calculateStartVelForBall() {
        // Creating a random angle to start from
        // Interval for velX is 0.2 to 0.75, and it varies from a negative and a positive number
        // velY is calculated based on (maxVel - velX)
        double maxVel = 1.0;
        boolean positiveNumber = random.nextBoolean();
        double velX = random.nextDouble(0.2, 0.75);
        double velY = maxVel - velX;
        velX = (positiveNumber) ? velX : -velX;

        return new double[]{velX, -velY};
    }

    public void setupKeyPressedEvents() {
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                playing = !playing;
                addInfoText.setVisible(false);

                if (!playing) this.startOrPauseText.setText("Press ENTER to continue");
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                this.paddle.setMoveLeft(true);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT || event.getCode() == KeyCode.D) {
                this.paddle.setMoveRight(true);
            }
        });

        this.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                this.paddle.setMoveLeft(false);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT || event.getCode() == KeyCode.D) {
                this.paddle.setMoveRight(false);
            }
        });
    }

    public void addStartOrPauseText() {
        // Text to display start or pause information
        this.startOrPauseText = new Text("Press ENTER to start");
        this.startOrPauseText.setStyle("-fx-font-size: 48px;");
        this.startOrPauseText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.startOrPauseText);

        // Text to display controls
        this.infoText = new Text("Press 'a' to move left and 'd' to move right");
        this.infoText.setStyle("-fx-font-size: 32px;");
        this.infoText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.infoText);

        // Text to inform user that you can pause the game
        this.addInfoText = new Text("When started, you can press ENTER to pause the game");
        this.addInfoText.setStyle("-fx-font-size: 32px;");
        this.addInfoText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.addInfoText);


        // Manually set the positions initially
        addCenterTextListener(this.startOrPauseText, 2, 2);
        addCenterTextListener(this.infoText, 2, 1.8);
        addCenterTextListener(this.addInfoText, 2, 1.6);
    }

    private void addCenterTextListener(Text text, double xOffset, double yOffSet) {
        if (text == null) return;
        text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {

            // Make sure bounds of text are updated correctly before calculating new position
            Platform.runLater(() -> {
                double textWidth = newValue.getWidth();
                double textHeight = newValue.getHeight();

                text.setX((WindowUtils.getWindowWidth() - textWidth) / xOffset);
                text.setY((WindowUtils.getWindowHeight() - textHeight) / yOffSet);
            });
        });
    }

    public Grid getGrid() {
        return grid;
    }

    public void onTick() {
        // Handle unstarted or paused game
        if (!playing) {
            startOrPauseText.setVisible(true);
            infoText.setVisible(true);
            return;
        }

        // Dont display info text
        startOrPauseText.setVisible(false);
        infoText.setVisible(false);

        // Check for Victory
        if (this.grid.getAliveAmount() <= 0) {
            Breakout.getInstance().setCurrentScene(new VictoryScene());
            return;
        }

        // Check for GameOver
        if (this.ball.getPosY() >= WindowUtils.getWindowHeight()) {
            Breakout.getInstance().setCurrentScene(new GameOverScene());
            return;
        }

        // Move paddle left
        if (paddle.isMoveLeft()) {
            paddle.updatePosXLeft();
        }

        // Move paddle right
        if (paddle.isMoveRight()) {
            paddle.updatePosXRight();
        }

        // Check Collisions for paddle and ball
        EdgeHit ballPaddleHit = CollisionChecker.checkCollision(this.paddle, this.ball);
        if (ballPaddleHit == EdgeHit.YAXIS) {
            this.ball.setPosY(this.paddle.getPosY() - this.ball.getHeight() * 2);
            this.ball.setVelY(-Math.abs(this.ball.getVelY()));
        }


        // Check Collisions between ball and any blocks on the screen
        boolean flipX = false; // Should X direction be flippped
        boolean flipY = false;
        for (int i = 0; i < grid.getGrid().length; i++) {
            for (int j = 0; j < grid.getGrid()[i].length; j++) {
                Block block = grid.getGrid()[i][j];

                if (block == null) {
                    continue;
                }

                EdgeHit ballBlockHit = CollisionChecker.checkCollision(block, this.ball);

                if (ballBlockHit == EdgeHit.XAXIS) {
                    flipX = true;
                    grid.removeBlock(i, j);
                } else if (ballBlockHit == EdgeHit.YAXIS) {
                    flipY = true;
                    grid.removeBlock(i, j);
                } else if (ballBlockHit == EdgeHit.BOTH) {
                    flipX = true;
                    flipY = true;
                    grid.removeBlock(i, j);
                }
            }
        }
        if (flipX) ball.flipVelX();
        if (flipY) ball.flipVelY();

        // Call the ball onTick function for it to move.
        this.ball.onTick();
    }
}
