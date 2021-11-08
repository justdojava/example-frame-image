package com.example.java.image;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 给图像添加水印
 * @author panzhi
 * @since 2021-10-29
 */
public class ImageFullWaterMarkUtil {



    /**
     * 给图像添加多处文字水印
     * @param srcImgPath     原始文件地址
     * @param targetImgPath  目标文件地址
     * @param text           水印内容
     * @param color          水印文字颜色
     * @param font           水印文字字体
     * @param alpha          水印透明度
     * @param startWidth     水印横向起始位置
     * @param degree         水印图片旋转角度
     * @param interval       高度间隔
     */
    public static void fullMarkImage(String srcImgPath,
                                 String targetImgPath,
                                 String text,
                                 Color color,
                                 Font font,
                                 float alpha,
                                 int startWidth,
                                 Integer degree,
                                 Integer interval) {
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
            int x = startWidth;
            int y = font.getSize();
            int space = srcImgHeight / interval;
            for (int i = 0; i < space; i++) {
                //如果最后一个坐标的y轴比height高，直接退出
                if (((y + font.getSize()) > srcImgHeight) || ((x + getWatermarkLength(text,g))  > srcImgWidth)) {
                    break;
                }
                //9、进行绘制
                g.drawString(text, x, y);
                x += getWatermarkLength(text,g);
                y += font.getSize() + interval;
            }
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
        String srcImgPath = "/Users/panzhi/Desktop/Jietu.jpg"; //原始文件地址
        String targetImgPath = "/Users/panzhi/Desktop/Jietu-copy-full.jpg"; //目标文件地址
        String text = "复 印 无 效"; //水印文字内容
        Color color = Color.red; //水印文字颜色
        Font font = new Font("宋体", Font.BOLD, 30); //水印文字字体
        float alpha = 0.4f; //水印透明度
        int startWidth = 30; //水印横向位置坐标
        Integer degree = -0; //水印旋转角度
        Integer interval = 100; //水印的位置
        //给图片添加文字水印
        fullMarkImage(srcImgPath, targetImgPath, text, color, font, alpha, startWidth, degree, interval);
    }
}
