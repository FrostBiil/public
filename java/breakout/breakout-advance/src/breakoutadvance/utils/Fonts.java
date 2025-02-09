package breakoutadvance.utils;

import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enum used to setting font paths, and loading these
 */
public enum Fonts {
    // Fonts
    BLACEB(Constants.FONT_FILEPATH + "BLACEB__.ttf"),
    BLACKWOOD(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf");

    private static final ConcurrentHashMap<String, Font> fonts = new ConcurrentHashMap<>(); // String and fonts
    private final String filename; // File name

    Fonts(String filename) {
        this.filename = filename;
    }

    /**
     * Method to run through all fonts, and call loadFont() for each of them
     */
    public static void load() {
        for (Fonts font : Fonts.values()) {
            loadFont(font.filename, Constants.DEFAULT_FONT_SIZE);
        }
    }

    /**
     * Method to load fonts
     *
     * @param filePath filepath
     * @param size size
     * @throws IOException if unable to load font
     */
    public static void loadFont(String filePath, double size) {
        try {
            // Use a resource stream to support loading from JAR
            InputStream resourceStream = FileUtils.getInputStream(filePath);
            if (resourceStream == null) {
                throw new IOException("Font file not found: " + filePath);
            }
            Font font = Font.loadFont(resourceStream, size);
            fonts.put(filePath, font);
        } catch (IOException | IllegalStateException e) {
            System.err.println("Error loading font: " + filePath + " - " + e.getMessage());
        }
    }


    /**
     * Getting font, to be able to use it throughout the program
     *
     * @param filePath filepath
     * @param size size
     * @return Font; or null if the loading fails.
     */
    public static Font getFont(String filePath, double size) {
        // If the font is not loaded, load it on demand
        return fonts.computeIfAbsent(filePath, path -> {
            loadFont(path, size);
            return fonts.get(path);
        });
    }

    /**
     * Overload of getting font, if there is not specified a font size.
     *
     * @param filePath filepath
     * @return Font
     */
    public static Font getFont(String filePath) {
        return getFont(filePath, Constants.DEFAULT_FONT_SIZE);
    }
}
