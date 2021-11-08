package com.example.java.image;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author panzhi
 * @since 2021-10-29
 */
public class ImageTest {

    // 图片路径
    private static final String imgPath = "/Users/panzhi/Desktop/1234-1-7.jpeg";

    // 颜色转换，Key：水印颜色，Value：需要转换成的颜色，null 表示背景色
    private static final Map<Color, Color> COLOR_MAP = new HashMap<>();

    // 设置转换关系
    static {
//        COLOR_MAP.put(new Color(255, 204, 204), null);
//        COLOR_MAP.put(new Color(245,194,194), new Color(242,242,242));
        COLOR_MAP.put(new Color(255, 0, 0), new Color(255,255,255));
    }

    public static void main(String[] args) throws Exception {
        File file = new File(imgPath);
        BufferedImage bufferedImage = ImageIO.read(file);

//        //水印替换成白色
        Color disColor = new Color(255, 255, 255);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                int color = bufferedImage.getRGB(i, j);
                Color oriColor = new Color(color);
                int red = oriColor.getRed();
                int greed = oriColor.getGreen();
                int blue = oriColor.getBlue();
                String total = red +  "" +  greed + "" + blue;
                //245,245,245是灰色  这里是把当前像素点由灰色替换成白色
                if(150 < red && red <= 255){
                    bufferedImage.setRGB(i, j, disColor.getRGB());
                }
            }
        }

//
//        int background = bufferedImage.getRGB(0, 0); // 背景色取左上角第一个像素的颜色
//        for (int x = 0; x < bufferedImage.getWidth(); x++) {
//            for (int y = 0; y < bufferedImage.getHeight(); y++) {
//                int rgb = bufferedImage.getRGB(x, y);
//                for (Map.Entry<Color, Color> colorEntry : COLOR_MAP.entrySet()) {
//                    if (rgb == colorEntry.getKey().getRGB()) {
//                        // 水印替换为背景色
//                        bufferedImage.setRGB(x, y, colorEntry.getValue() == null ? background : colorEntry.getValue().getRGB());
//                        break;
//                    }
//                }
//            }
//        }


        String type = imgPath.substring(imgPath.lastIndexOf(".") + 1);
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(type);
        ImageWriter writer = it.next();
        File f = new File("/Users/panzhi/Desktop/1234-1-14.jpeg");
        f.getParentFile().mkdirs();
        ImageOutputStream ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        writer.write(bufferedImage);
        bufferedImage.flush();
        ios.flush();
        ios.close();
    }
}
