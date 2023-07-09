package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luciad.imageio.webp.WebPWriteParam;
import com.tinify.Source;
import com.tinify.Tinify;
import enums.ImageOptions;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericUtils {

    public static String toAcronym(String string) {
        return string.replaceAll("\\B.|\\P{L}", "");
    }

    /**
     * Since properties can be only Strings (both key and values) we must assure that the map key and values are Strings
     * This is the reason why we force this cast by concatenating and Empty String
     *
     * @param map      map
     * @param filePath filePath
     * @param <K>      key
     * @param <V>      value
     * @throws IOException
     */
    public static <K, V> void serializeMap(Map<K, V> map, String filePath) throws IOException {
        Map<String, String> parsedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            parsedMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        Properties properties = new Properties();
        properties.putAll(parsedMap);
        properties.store(new FileOutputStream(filePath), null);
    }

    public static HashMap<String, String> deserializeMap(String filePath) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        Properties properties = new Properties();
        properties.load(new FileInputStream(filePath));
        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.get(key).toString());
        }
        return map;
    }

    public static <T> T deserialize(Class<T> object, String filePath) throws IOException {
        Path currentDir = Paths.get("");
        Path absolutePath = currentDir.toAbsolutePath();
        Path fullPath = Paths.get(absolutePath + filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(fullPath);
        return objectMapper.readValue(jsonData, object);
    }

    public static int romanNumberToInt(String romanNumber) {
        int result = 0;
        int[] decimal = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < decimal.length; i++) {
            while (romanNumber.indexOf(roman[i]) == 0) {
                result += decimal[i];
                romanNumber = romanNumber.substring(roman[i].length());
            }
        }
        return result;
    }

    public static void removeEvenIndexesFromList(List<?> list) {
        if (list.isEmpty()) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (i % 2 != 0) {
                list.remove(i);
            }
        }
    }

    public static void removeOddIndexesFromList(List<?> list) {
        if (list.isEmpty()) {
            return;
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (i % 2 == 0) {
                list.remove(i);
            }
        }
    }

    public static void printElements(Collection<?> collection) {
        for (Object element : collection) {
            System.out.println(element);
        }
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), value)).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static String removeParenthesisFromString(final String string) {
        int indexOfOpenParenthesis = string.indexOf('(');
        int indexOfCloseParenthesis = string.indexOf(')');

        String replaced = string;

        if (indexOfOpenParenthesis != -1 || indexOfCloseParenthesis != -1) {
            String parenthesis = string.substring(indexOfOpenParenthesis, indexOfCloseParenthesis + 1);
            replaced = string.replace(parenthesis, "").trim();

        }
        return replaced;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static <T1, T2> void iterateSimultaneously(Iterable<T1> c1, Iterable<T2> c2, BiConsumer<T1, T2> consumer) {
        Iterator<T1> i1 = c1.iterator();
        Iterator<T2> i2 = c2.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            consumer.accept(i1.next(), i2.next());
        }
    }

    public static void downloadImage(String url, String name, Map<ImageOptions, Object> options, String... folders) throws Exception {
        int attempts = 0;
        while (attempts < 3) { // We attempt to download it in desired format 3 times
            try {
                doDownloadImage(url, name, options, folders); //First we try to download it in the desired format
                break;
            } catch (FileNotFoundException notFound) {
                break;
                //we do nothing, file doesn't exist
            } catch (Exception e) {
                attempts++;
                if (attempts >= 3) {
                    options.put(ImageOptions.FORMAT, getExtensionFromURL(url));
                    doDownloadImage(url, name, options, folders); //If it fails, we will download it with its real format
                }
            }
        }
    }

    public static void downloadImage(String url, String name, String format, Integer width, Integer height, String... folders) throws Exception {
        int attempts = 0;
        while (attempts < 3) { // We attempt to download it in desired format 3 times
            try {
                doDownloadImage(url, name, format, width, height, folders); //First we try to download it in the desired format
                break;
            } catch (FileNotFoundException notFound) {
                break;
                //we do nothing, file doesn't exist
            } catch (Exception e) {
                attempts++;
                if (attempts >= 3) {
                    doDownloadImage(url, name, getExtensionFromURL(url), width, height, folders); //If it fails, we will download it with its real format
                }
            }
        }
    }

    private static void doDownloadImage(String url, String name, String format, Integer width, Integer height, String... folders) throws Exception {
        URL imageURL = new URL(url);
        String filename = name + "." + format;
        String directory = compoundDirectory(filename, folders);
        File file = new File(directory);
        createDirectoryIfNotExists(file);
        InputStream input = imageURL.openStream();
        if (format.equals("webp")) {
            BufferedImage saveImage = ImageIO.read(input);
            optimizeImage(saveImage, directory, width, height);
        } else {
            byte[] bytes = IOUtils.toByteArray(input);
            FileUtils.writeByteArrayToFile(file, bytes);
        }
    }

    private static void doDownloadImage(String url, String name, Map<ImageOptions, Object> options, String... folders) throws Exception {
        URL imageURL = new URL(url);
        String format = (String) options.get(ImageOptions.FORMAT);
        String filename = name + "." + format;
        String directory = compoundDirectory(filename, folders);
        File file = new File(directory);
        createDirectoryIfNotExists(file);

        if (options.containsKey(ImageOptions.USE_EXTERNAL_API)) {
            Tinify.setKey("FMCJhS4RvlK99hCG2wLTyNFrB70Y6fGw");
            Source source = Tinify.fromFile(url.replace("file:/", ""));
            source.toFile(directory);
        } else {
            InputStream input = imageURL.openStream();
            if (options.containsKey(ImageOptions.PNG_MINIFY)) {
                BufferedImage saveImage = ImageIO.read(input);
                optimizeImage(saveImage, directory, options);
            } else {
                byte[] bytes = IOUtils.toByteArray(input);
                FileUtils.writeByteArrayToFile(file, bytes);
            }
        }
    }

    private static void createDirectoryIfNotExists(File file) {
        file.getParentFile().mkdirs();
    }

    public static String compoundDirectory(String fileName, String... folders) {
        StringJoiner joiner = new StringJoiner(File.separator);
        for (String folder : folders) {
            joiner.add(folder);
        }
        joiner.add(fileName);
        return joiner.toString();
    }

    public static String getExtensionFromURL(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    public static void optimizeImage(BufferedImage rawImage, String path, Integer width, Integer height) throws
            Exception {
        //BufferedImage rawImage = ImageIO.read(new File(path));
        if (Objects.nonNull(width) && Objects.nonNull(height)) {
            rawImage = resize(rawImage, width, height);
        }

        // Obtain a WebP ImageWriter instance
        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        // Configure encoding parameters
        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);

        // Configure the output on the ImageWriter
        writer.setOutput(new FileImageOutputStream(new File(path)));

        // Encode
        writer.write(null, new IIOImage(rawImage, null, null), writeParam);
    }

    public static void optimizeImage(BufferedImage rawImage, String path, Map<ImageOptions, Object> options) throws Exception {
        try {
            //BufferedImage rawImage = ImageIO.read(new File(path));
            Integer width = (Integer) options.get(ImageOptions.WIDTH);
            Integer height = (Integer) options.get(ImageOptions.HEIGHT);

            if ((Objects.nonNull(width) && Objects.nonNull(height)) || options.containsKey(ImageOptions.PNG_MINIFY)) {
                if ((Objects.isNull(width) && Objects.isNull(height))) {
                    width = rawImage.getWidth();
                    height = rawImage.getHeight();
                }
                rawImage = resize(rawImage, width, height);
            }

            // Obtain a WebP ImageWriter instance
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

            // Configure encoding parameters
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

            if (options.containsKey(ImageOptions.MAX_COMPRESSION_QUALITY)) {
                writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);
            } else {
                writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);

            }

            // Configure the output on the ImageWriter
            writer.setOutput(new FileImageOutputStream(new File(path)));

            // Encode
            writer.write(null, new IIOImage(rawImage, null, null), writeParam);
        } catch (Exception ex) {

        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        try {
            return Thumbnails.of(img).size(newW, newH).asBufferedImage();
        } catch (Exception e) {
            return null;
        }
    }

    public static Set<String> listFilesFromDir(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static Set<String> listFileFromDirRecursively(String dir) throws IOException {
        return Files.find(Paths.get(dir),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile())
                .map(path -> path.toString())
                .collect(Collectors.toSet());
    }


    public static String mergeStrings(String s1, String s2) {
        if (s1.length() > s2.length()) {
            StringJoiner joiner = new StringJoiner(StringUtils.SPACE);
            String[] parts = s1.split("\\s+");
            for (String part : parts) {
                if (!part.equals(s2)) {
                    joiner.add(part);
                }
            }
            s1 = joiner.toString();
        } else {
            StringJoiner joiner = new StringJoiner(StringUtils.SPACE);
            String[] parts = s2.split("\\s+");
            for (String part : parts) {
                if (!part.equals(s1)) {
                    joiner.add(part);
                }
            }
            s2 = joiner.toString();
        }
        String lastWord = s1.substring(s1.lastIndexOf(" ") + 1);
        String firstWord = s2.split(" ")[0];
        if (firstWord.equals(lastWord)) {
            return s1 + s2.substring(lastWord.length());
        } else {
            return s1 + " " + s2;
        }
    }

    public static <T> T firstNonNull(T first, T second) {
        if (first != null) {
            return first;
        } else if (second != null) {
            return second;
        } else {
            return null;
        }
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }
}
