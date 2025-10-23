package mbt.todomvc.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static String optimizeImage(String base64ImageString) {
        String[] parts = base64ImageString.split(",");
        String imageString = parts.length > 1 ? parts[1] : parts[0]; // Handle cases with or without "data:image/jpeg;base64," prefix

        byte[] decodedBytes = Base64.getDecoder().decode(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //
        int newWidth = 500; // Desired width
        int newHeight = (int) (originalImage.getHeight() * ((double) newWidth / originalImage.getWidth())); // Maintain aspect ratio
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);

        //
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageOutputStream ios = null;
        try {
            ios = ImageIO.createImageOutputStream(bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.9f); // Adjust quality (0.0f to 1.0f)

        try {
            writer.write(null, new IIOImage(resizedImage, null, null), param);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ios.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.dispose();
        byte[] optimizedImageBytes = bos.toByteArray();

        //
        String optimizedBase64Image = Base64.getEncoder().encodeToString(optimizedImageBytes);
        return "data:image/jpeg;base64," + optimizedBase64Image;
    }
}
