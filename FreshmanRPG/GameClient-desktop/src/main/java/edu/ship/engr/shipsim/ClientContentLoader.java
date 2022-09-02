package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.datasource.ContentLoader;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientContentLoader
{
    public static void execute()
    {
        try
        {
            initializeLocalFiles();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void initializeLocalFiles() throws IOException
    {
        Path contentPath = ContentLoader.getContentPath();

        if (!Files.exists(contentPath))
        {
            Files.createDirectory(contentPath);
        }

        FileUtils.cleanDirectory(contentPath.toFile());

        Path archivePath = Path.of(contentPath.toString(), "resources.zip");
        File resourcesZip = FileUtils.toFile(archivePath.toUri().toURL());

        InputStream stream = ClientContentLoader.class.getResourceAsStream("resources.zip");

        if (stream != null)
        {
            FileUtils.copyInputStreamToFile(stream, resourcesZip);
        }

        extractZip(archivePath, contentPath);

        FileUtils.delete(resourcesZip);
    }

    /**
     * @param archivePath
     * @param contentPath
     */
    private static void extractZip(Path archivePath, Path contentPath)
    {
        try (
                InputStream inputStream = Files.newInputStream(archivePath);
                ArchiveInputStream archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, inputStream);
        )
        {
            ArchiveEntry archiveEntry = null;
            while ((archiveEntry = archiveInputStream.getNextEntry()) != null)
            {
                Path path = Paths.get(contentPath.toString(), archiveEntry.getName());
                File file = path.toFile();
                if (archiveEntry.isDirectory())
                {
                    if (!file.isDirectory())
                    {
                        file.mkdirs();
                    }
                }
                else
                {
                    File parent = file.getParentFile();
                    if (!parent.isDirectory())
                    {
                        parent.mkdirs();
                    }

                    try (OutputStream outputStream = Files.newOutputStream(path))
                    {
                        IOUtils.copy(archiveInputStream, outputStream);
                    }
                }
            }
        }
        catch (IOException | ArchiveException e)
        {
            e.printStackTrace();
        }
    }
}
