package com.example.java.image;


import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 给图像添加水印
 * @author panzhi
 * @since 2021-10-29
 */
public class ImageWaterMarkUtil {



    /**
     * 给图像添加文字水印
     * @param srcImgPath     原始文件地址
     * @param targetImgPath  目标文件地址
     * @param text           水印内容
     * @param color          水印文字颜色
     * @param font           水印文字字体
     * @param alpha          水印透明度
     * @param positionWidth  水印横向位置
     * @param positionHeight 水印纵向位置
     * @param degree         水印图片旋转角度
     * @param location       水印的位置，左上角、右上角、左下角、右下角、居中
     */
    public static void markImage(String srcImgPath,
                                 String targetImgPath,
                                 String text,
                                 Color color,
                                 Font font,
                                 float alpha,
                                 int positionWidth,
                                 int positionHeight,
                                 Integer degree,
                                 String location) {
        try {
            // 1、读取源图片
            Image srcImg = ImageIO.read(new File(srcImgPath));
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            BufferedImage buffImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

            // 2、得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 3、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImgWidth, srcImgHeight, Image.SCALE_SMOOTH), 0, 0, null);
            // 4、设置水印旋转
            if (null != degree) {
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 5、设置水印文字颜色
            g.setColor(color);
            // 6、设置水印文字Font
            g.setFont(font);
            // 7、设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 8、水印图片的位置
            int x = 0, y = 0;
            if (StringUtils.equals(location, "left-top")) {
                x = 30;
                y = font.getSize();
            } else if (StringUtils.equals(location, "right-top")) {
                x = srcImgWidth - getWatermarkLength(text, g) - 30;
                y = font.getSize();
            } else if (StringUtils.equals(location, "left-bottom")) {
                x += 30;
                y = buffImg.getHeight() - font.getSize();
            } else if (StringUtils.equals(location, "right-bottom")) {
                x = srcImgWidth - getWatermarkLength(text, g) - 30;
                y = srcImgHeight - font.getSize();
            } else if (StringUtils.equals(location, "center")) {
                x = (srcImgWidth - getWatermarkLength(text, g)) / 2;
                y = srcImgHeight / 2;
            } else {
                //自定义位置
                x = positionWidth;
                y = positionHeight;
            }
            // 9、第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y)
            g.drawString(text, x, y);
            // 10、释放资源
            g.dispose();
            // 11、生成图片
            ImageIO.write(buffImg, "png", new File(targetImgPath));
            System.out.println("图片完成添加水印文字");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算填充的水印长度
     * @param text
     * @param g
     * @return
     */
    private static int getWatermarkLength(String text, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(text.toCharArray(), 0, text.length());
    }

    public static void main(String[] args) {
        String srcImgPath = "/Users/panzhi/Desktop/11-rotate.jpeg"; //原始文件地址
        String targetImgPath = "/Users/panzhi/Desktop/1-rotate-copy.jpg"; //目标文件地址
        String text = "复 印 无 效"; //水印文字内容
        Color color = Color.red; //水印文字颜色
        Font font = new Font("宋体", Font.BOLD, 60); //水印文字字体
        float alpha = 0.4f; //水印透明度
        int positionWidth = 320; //水印横向位置坐标
        int positionHeight = 450; //水印纵向位置坐标
        Integer degree = -30; //水印旋转角度
        String location = "center"; //水印的位置
        //给图片添加文字水印
        markImage(srcImgPath, targetImgPath, text, color, font, alpha, positionWidth, positionHeight, degree, location);
    }
}
