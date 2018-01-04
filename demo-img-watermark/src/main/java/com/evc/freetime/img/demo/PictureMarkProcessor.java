package com.evc.freetime.img.demo;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @Author changle
 * @Time 18/1/4.
 * @Desc 为图片添加图片水印.
 * 转载自:http://blog.csdn.net/top_code/article/details/71756529
 */
public class PictureMarkProcessor {
    /**
     * @param args
     */
    public static void main(String[] args) {
        testPictureMark();
    }

    public static void testPictureMark() {

        File srcImageFile = new File("D:/test/desktop.png");
        File logoImageFile = new File("D:/test/tools.png");

        File outputRoateImageFile = new File("D:/test/desktop_pic_mark.jpg");

        createWaterMarkByIcon(srcImageFile, logoImageFile, outputRoateImageFile, 0);
    }

    public static void createWaterMarkByIcon(File srcImageFile, File logoImageFile,
                                      File outputImageFile, double degree) {

        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImageFile);

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),
                    srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            ImageIcon logoImgIcon = new ImageIcon(ImageIO.read(logoImageFile));
            Image logoImg = logoImgIcon.getImage();

            //旋转
            if (degree>0) {
                graphics.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getWidth() / 2);
            }

            float alpha = 0.8f; // 透明度
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            //水印 的位置
            graphics.drawImage(logoImg, buffImg.getWidth()/3, buffImg.getHeight()/2, null);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
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