package breakoutadvance.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Utility class for file operations.
 *
 *  * SOURCES used to create this class:
 *  *  - https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
 *  *  - https://stackoverflow.com/questions/1429172/how-to-list-the-files-inside-a-jar-file
 */
public class FileUtils {

    /**
     * Retrieves the URL of a resource given its relative path.
     *
     * @param path The relative path of the resource.
     * @return The URL of the resource.
     */
    public static URL getURL(String path) {
        URL resource = FileUtils.class.getClassLoader().getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return resource;
    }

    /**
     * Retrieves the input stream of a resource given its relative path.
     *
     * @param resourcePath The relative path of the resource.
     * @return The input stream of the resource.
     */
    public static InputStream getInputStream(String resourcePath) {
        return FileUtils.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    /**
     * Get a list of resource files in a directory. Works for both files and jar.
     *
     * @param path The path to the directory.
     * @return A list of resource file paths.
     * @throws IOException If an error occurs.
     */
    public static List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        // Get URL of the directory
        URL dirURL = FileUtils.class.getClassLoader().getResource(path);

        if (dirURL == null) {
            throw new IOException("Resource directory not found, URL is null: " + path);
        }

        // Handle different ways files are stored to both support jar files and normal file systems
        switch (dirURL.getProtocol()) {

            // Read files from a file system
            case "file" -> {
                try (InputStream stream = dirURL.openStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                     filenames.addAll(reader.lines().map(line -> path + "/" + line).toList());
                }
            }

            // Read files from a JAR file
            case "jar" -> {
                // Read files from a JAR file

                /*
                Notes:
                | A jar file has the following format:
                | jar:file:/path/to/jarfile.jar!/some/path
                | So by taking the substring from the 5th character to the first '!' character, we get the path to the jar file.
                | Like it was a path.
                 */
                String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries(); // Get all files in the jar file
                    while (entries.hasMoreElements()) {
                        String name = entries.nextElement().getName();
                        if (name.startsWith(path) && !name.equals(path + "/")) {
                            filenames.add(name);
                        }
                    }
                }
            }
            default -> throw new IOException("Unsupported protocol: " + dirURL.getProtocol());
        }

        return filenames;
    }

    /**
     * Get an InputStream for a resource file.
     *
     * @param resourcePath The path to the resource.
     * @return The InputStream for the resource.
     */
    public static InputStream getResourceAsStream(String resourcePath) {
        return FileUtils.class.getClassLoader().getResourceAsStream(resourcePath);
    }
}
