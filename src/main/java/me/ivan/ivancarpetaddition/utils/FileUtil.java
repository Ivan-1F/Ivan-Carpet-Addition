package me.ivan.ivancarpetaddition.utils;

import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static String readFile(String path) throws IOException {
        InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Null input stream from path " + path);
        }
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

    public static File loadResource(String path) throws IOException {
        URL resource = FileUtil.class.getClassLoader().getResource(path);
        if (resource == null) {
            throw new IOException("Failed to load resource from path " + path);
        }
        return new File(resource.getFile());
    }

    public static List<String> listDir(String path) throws IOException {
        File[] list = loadResource(path).listFiles();
        return list == null ? Lists.newArrayList() : Arrays.stream(list).map(File::getName).collect(Collectors.toList());
    }

    public static String getFileExtension(String path) {
        return FilenameUtils.getExtension(path);
    }

    public static String removeFileExtension(String path) {
        return FilenameUtils.removeExtension(path);
    }
}
