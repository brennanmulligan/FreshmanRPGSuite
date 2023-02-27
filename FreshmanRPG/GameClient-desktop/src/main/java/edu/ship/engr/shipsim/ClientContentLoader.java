package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.datasource.ContentLoader;
import edu.ship.engr.shipsim.model.OptionsManager;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientContentLoader
{
    private static final Path resourcesPath = Path.of("src", "main", "resources");

    public static void execute()
    {
        // If not in IntelliJ, FILE_MANIFEST.txt is required
        if (!OptionsManager.getSingleton().isRunningInIntelliJ())
        {
            Objects.requireNonNull(getFileManifest()).forEach(ClientContentLoader::copyFileToContentPath);
        }
    }

    /**
     * Copies a file with the specified name from the "src/main/resources" directory to the content path.
     *
     * @param fileName the name of the file to copy
     * @throws IOException if an I/O error occurs while copying the file
     */
    @SneakyThrows(IOException.class)
    private static void copyFileToContentPath(String fileName)
    {
        Path contentPath = ContentLoader.getContentPath();
        Path targetPath = Path.of(contentPath.toString(), fileName);

        // Create the content path directory if it doesn't exist
        if (!Files.exists(contentPath))
        {
            Files.createDirectories(contentPath);
        }

        // Get an InputStream for the file from the "src/main/resources" directory
        InputStream s = getFileStream(resourcesPath, fileName);

        // If the InputStream is not null, copy the file to the target path
        if (s != null)
        {
            FileUtils.copyInputStreamToFile(s, targetPath.toFile());
        }
    }

    /**
     * Gets the contents of the file "FILE_MANIFEST.txt" as a list of strings.
     *
     * @return a list of strings containing the contents of the file, or null if the file cannot be found
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException           if an I/O error occurs while reading the file
     */
    @SneakyThrows({FileNotFoundException.class, IOException.class})
    public static List<String> getFileManifest()
    {
        // Get an InputStream for the file
        InputStream s = getFileStream(Path.of(""), "FILE_MANIFEST.txt");

        // If the InputStream is null, return null
        if (s == null)
        {
            return null;
        }

        // Read the contents of the file into a list of strings
        try (InputStreamReader in = new InputStreamReader(s);
             BufferedReader br = new BufferedReader(in))
        {
            return br.lines().collect(Collectors.toList());
        }
    }

    /**
     * Gets an InputStream for a file with the specified name in the specified directory.
     *
     * @param dirPath  the path to the directory containing the file
     * @param fileName the name of the file
     * @return an InputStream for the file
     * @throws FileNotFoundException if the file cannot be found
     */
    @Nullable
    private static InputStream getFileStream(Path dirPath, String fileName) throws FileNotFoundException
    {
        if (OptionsManager.getSingleton().isRunningInIntelliJ())
        {

            // If running in IntelliJ, create a FileInputStream for the file
            File file = Path.of(dirPath.toString(), fileName).toFile();
            return new FileInputStream(file);
        }
        else
        {
            // If running outside of IntelliJ, get the resource from inside the jar file
            String resourcePath = "/" + fileName;
            return ClientContentLoader.class.getResourceAsStream(resourcePath);
        }
    }
}
