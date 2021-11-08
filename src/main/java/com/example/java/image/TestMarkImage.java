package com.example.java.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

/**
 * @author panzhi
 * @since 2021-10-29
 */
public class TestMarkImage {

    public static void main(String[] args) throws Exception {
        String path = "/Users/panzhi/Desktop/1234.jpeg";
        FileInputStream inputStream = new FileInputStream(path);
        int imgRotateAngle = TransferImage.getImgRotateAngle(inputStream);
        System.out.println("获取到图片应旋转角度为:" + imgRotateAngle);

        BufferedImage picture = ImageIO.read(inputStream);

    }
}
