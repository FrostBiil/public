package breakoutadvance.utils;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum to store all images used in the game
 */
public enum Images {

    // Background files
    BACKGROUND_1(Constants.BACKGROUND_FILEPATH + "background1.png"),
    BACKGROUND_2(Constants.BACKGROUND_FILEPATH + "background2.png"),
    BACKGROUND_3(Constants.BACKGROUND_FILEPATH + "background12.png"),

    // Ball files
    BALL_BEIGE(Constants.BALL_FILEPATH + "beige.png"),
    BALL_BLACK(Constants.BALL_FILEPATH + "black.png"),
    BALL_BLUE(Constants.BALL_FILEPATH + "blue.png"),
    BALL_BLUE2(Constants.BALL_FILEPATH + "blue2.png"),
    BALL_BROWN(Constants.BALL_FILEPATH + "brown.png"),
    BALL_GREEN(Constants.BALL_FILEPATH + "green.png"),
    BALL_PINK(Constants.BALL_FILEPATH + "pink.png"),
    BALL_RED(Constants.BALL_FILEPATH + "red.png"),
    BALL_YELLOW(Constants.BALL_FILEPATH + "yellow.png"),

    // Paddle files
    PADDLE_BEIGE(Constants.PADDLE_FILEPATH + "beige.png"),
    PADDLE_BEIGELEFT(Constants.PADDLE_FILEPATH + "beigeLeft.png"),
    PADDLE_BEIGEMIDDLE(Constants.PADDLE_FILEPATH + "beigeMiddle.png"),
    PADDLE_BEIGERIGHT(Constants.PADDLE_FILEPATH + "beigeRight.png"),
    PADDLE_BLACK(Constants.PADDLE_FILEPATH + "black.png"),
    PADDLE_BLACKLEFT(Constants.PADDLE_FILEPATH + "blackLeft.png"),
    PADDLE_BLACKMIDDLE(Constants.PADDLE_FILEPATH + "blackMiddle.png"),
    PADDLE_BLACKRIGHT(Constants.PADDLE_FILEPATH + "blackRight.png"),
    PADDLE_BLUE(Constants.PADDLE_FILEPATH + "blue.png"),
    PADDLE_BLUELEFT(Constants.PADDLE_FILEPATH + "blueLeft.png"),
    PADDLE_BLUEMIDDLE(Constants.PADDLE_FILEPATH + "blueMiddle.png"),
    PADDLE_BLUERIGHT(Constants.PADDLE_FILEPATH + "blueRight.png"),
    PADDLE_BLUE2(Constants.PADDLE_FILEPATH + "blue2.png"),
    PADDLE_BLUE2LEFT(Constants.PADDLE_FILEPATH + "blue2Left.png"),
    PADDLE_BLUE2MIDDLE(Constants.PADDLE_FILEPATH + "blue2Middle.png"),
    PADDLE_BLUE2RIGHT(Constants.PADDLE_FILEPATH + "blue2Right.png"),
    PADDLE_BROWN(Constants.PADDLE_FILEPATH + "brown.png"),
    PADDLE_BROWNLEFT(Constants.PADDLE_FILEPATH + "brownLeft.png"),
    PADDLE_BROWNMIDDLE(Constants.PADDLE_FILEPATH + "brownMiddle.png"),
    PADDLE_BROWNRIGHT(Constants.PADDLE_FILEPATH + "brownRight.png"),
    PADDLE_GREEN(Constants.PADDLE_FILEPATH + "green.png"),
    PADDLE_GREENLEFT(Constants.PADDLE_FILEPATH + "greenLeft.png"),
    PADDLE_GREENMIDDLE(Constants.PADDLE_FILEPATH + "greenMiddle.png"),
    PADDLE_GREENRIGHT(Constants.PADDLE_FILEPATH + "greenRight.png"),
    PADDLE_PINK(Constants.PADDLE_FILEPATH + "pink.png"),
    PADDLE_PINKLEFT(Constants.PADDLE_FILEPATH + "pinkLeft.png"),
    PADDLE_PINKMIDDLE(Constants.PADDLE_FILEPATH + "pinkMiddle.png"),
    PADDLE_PINKRIGHT(Constants.PADDLE_FILEPATH + "pinkRight.png"),
    PADDLE_RED(Constants.PADDLE_FILEPATH + "red.png"),
    PADDLE_REDLEFT(Constants.PADDLE_FILEPATH + "redLeft.png"),
    PADDLE_REDMIDDLE(Constants.PADDLE_FILEPATH + "redMiddle.png"),
    PADDLE_REDRIGHT(Constants.PADDLE_FILEPATH + "redRight.png"),
    PADDLE_YELLOW(Constants.PADDLE_FILEPATH + "yellow.png"),
    PADDLE_YELLOWLEFT(Constants.PADDLE_FILEPATH + "yellowLeft.png"),
    PADDLE_YELLOWMIDDLE(Constants.PADDLE_FILEPATH + "yellowMiddle.png"),
    PADDLE_YELLOWRIGHT(Constants.PADDLE_FILEPATH + "yellowRight.png"),

    // BLOCK files
    BLOCK_BLUELEFT(Constants.BLOCK_FILEPATH + "blueLeft.png"),
    BLOCK_BLUEMIDDLE(Constants.BLOCK_FILEPATH + "blueMiddle.png"),
    BLOCK_BLUERIGHT(Constants.BLOCK_FILEPATH + "blueRight.png"),
    BLOCK_GREENLEFT(Constants.BLOCK_FILEPATH + "greenLeft.png"),
    BLOCK_GREENMIDDLE(Constants.BLOCK_FILEPATH + "greenMiddle.png"),
    BLOCK_GREENRIGHT(Constants.BLOCK_FILEPATH + "greenRight.png"),
    BLOCK_PINKLEFT(Constants.BLOCK_FILEPATH + "pinkLeft.png"),
    BLOCK_PINKMIDDLE(Constants.BLOCK_FILEPATH + "pinkMiddle.png"),
    BLOCK_PINKRIGHT(Constants.BLOCK_FILEPATH + "pinkRight.png"),
    BLOCK_REDLEFT(Constants.BLOCK_FILEPATH + "redLeft.png"),
    BLOCK_REDMIDDLE(Constants.BLOCK_FILEPATH + "redMiddle.png"),
    BLOCK_REDRIGHT(Constants.BLOCK_FILEPATH + "redRight.png"),
    BLOCK_YELLOWLEFT(Constants.BLOCK_FILEPATH + "yellowLeft.png"),
    BLOCK_YELLOWMIDDLE(Constants.BLOCK_FILEPATH + "yellowMiddle.png"),
    BLOCK_YELLOWRIGHT(Constants.BLOCK_FILEPATH + "yellowRight.png"),

    // Powerup files
    BOMB(Constants.IMAGE_PATH + "bomb.png"),
    HEART_EMPTY(Constants.IMAGE_PATH + "heart_empty.png"),
    HEART_FULL(Constants.IMAGE_PATH + "heart_full.png"),
    POWERUP_PADDLE(Constants.IMAGE_PATH + "paddleWidthPowerUp.png"),
    POWERUP_BALL(Constants.IMAGE_PATH + "powerup_plus_one.png");

    // Map to store the image objects
    private static final Map<String, Image> images = new HashMap<>();
    // File name of image
    private final String filename;

    Images(String filename) {
        this.filename = filename;
    }


    /**
     * Put an image into the map with a try-catch block.
     *
     * @param filepath the filepath to image
     */
    public static void putImage(String filepath) {
        try {
            // Load the image
            URL resource = FileUtils.getURL(filepath);
            Image image = new Image(resource.toString());
            // Put the key-image pair in the map
            images.put(filepath, image);
        } catch (Exception e) {
            System.err.println("Error putting image with key derived from URL: " + filepath + " | " + e.getMessage());
        }
    }

    /**
     * PreLoad all the image files asynchronously
     */
    public static void load() {

        for (Images image : Images.values()) {
            // Run sync, since we need the images at the start.
            try {
                putImage(image.filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getting an image
     *
     * @param key key
     * @return Image - if it exists, else null
     */
    public static Image getImage(String key) {
        if (!images.containsKey(key)) {
            System.err.println("Image key not found: " + key);
            return null;
        }
        return images.get(key);
    }

}
