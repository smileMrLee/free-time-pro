package com.evc.freetime.img.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author changle
 * @time 18/1/4.
 * description 为图片添加文字水印.
 * 转载自:http://blog.csdn.net/top_code/article/details/71756529
 */
public class TextMarkProcessor {

    /**
     * @param args
     */
    public static void main(String[] args) {

        testTextMark();
    }

    public static void testTextMark() {
        File srcImgFile = new File("D:/test/desktop.png");
        String logoText = "[ 天使的翅膀 ]";

        File outputRotateImageFile = new File("D:/test/desktop_text_mark.jpg");

        createWaterMarkByText(srcImgFile, logoText, outputRotateImageFile, 0);
    }

    public static void createWaterMarkByText(File srcImgFile, String logoText,
                                      File outputImageFile, double degree) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImgFile);

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),
                    srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if (degree>0) {
                graphics.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("宋体", Font.BOLD, 36));

            float alpha = 0.8f;
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            graphics.drawString(logoText, buffImg.getWidth()/3, buffImg.getHeight()/2);
            graphics.dispose();

            os = new FileOutputStream(outputImageFile);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
