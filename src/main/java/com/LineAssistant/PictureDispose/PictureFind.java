package com.LineAssistant.PictureDispose;

import com.LineAssistant.ControlFlow.CommonalityMethod;
import com.LineAssistant.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  图片操作处理
 */
public class PictureFind {

    /**
     * 根据图片路径查询屏幕上对应位置
     * @param url
     * @return
     */
    public static List<Point> getResult(String url){
        List<Point> list = null;
        try {
            list = getResult(ImageIO.read(new FileInputStream(url)));
        }
        catch (IOException ioE){
            System.out.println(ioE.getMessage());
        }
        return list;
    }

    /**
     *  根据图片查找屏幕上对应的图片位置
     * @param image
     * @return  对应图片位置的中心点
     */
    public static List<Point> getResult(BufferedImage image){

        /** 保存确定对应的屏幕图片中心坐标 */
        List<Point> coord = new ArrayList<Point>();

        /** 获取屏幕图片及所有像素RGB模型数组 */
        BufferedImage screenPicture = getScreenPicture();
        int[][] screenRGB = getRGB(screenPicture);

        /** 根据目标图片获取所有像素RGB模型数组 */
        int[][] imageRGB = getRGB(image);
        int width = image.getWidth();
        int height = image.getHeight();

        /** 循环屏幕图片的RGB模型数组与目标的RGB模型数组进行对比 */
        for(int x = 0; x < screenRGB.length - width; x++){
            for(int y = 0; y < screenRGB[x].length - height; y++){
                /** 用目标图片四角RGB像素与屏幕截屏图片对应四角RGB像素对比 */
                if(imageRGB[0][0] == screenRGB[x][y] &&
                        imageRGB[0][height-1] == screenRGB[x][y + height-1] &&
                        imageRGB[width-1][0] == screenRGB[width-1 + x][y] &&
                        imageRGB[width-1][height-1] == screenRGB[x + width-1][y + height-1]){

                    if(isBoolean(x, y, imageRGB, screenRGB)){ coord.add(new Point(x + width/2, y + height/2)); }
                }
            }
        }
        return coord;
    }

    /**
     *  获取当前屏幕图片
     * @return
     */
    public static BufferedImage getScreenPicture(){
        CommonalityMethod.sleep(500);
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        BufferedImage image = Main.robot.createScreenCapture(new Rectangle(0, 0, width, height));
        return image;
    }

    /**
     *  获取指定图片的所有像素RGB模型数组
     * @param image
     * @return
     */
    public static int[][] getRGB(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[width][height];
        for (int x = 0; width > x; x++){
            for(int y = 0; height > y; y++){
                /** 使用getRGB(x, y)获取该点的颜色值是sRGB，而在实际应用中使用的是RGB，所以需要将sRGB转化成RGB，即bufImg.getRGB(w, h) & 0xFFFFFF */
                result[x][y] = image.getRGB(x, y) & 0xFFFFFF;
            }
        }
        return result;
    }

    /**
     *  目标图片与对应截图上的映射图片四角RGB像素相同, 填充屏幕截屏图片上对应图片与目标图片进行对比
     * @param x 映射图片左上角的x轴坐标
     * @param y 映射图片左上角的y轴坐标
     * @param imageRGB  目标图片的像素RGB模型数组
     * @param screenRGB 屏幕截图的像素RGB模型数组
     * @return  只要有一个像素不同就返回false
     */
    public static boolean isBoolean(int x, int y, int[][] imageRGB, int[][] screenRGB){
        for(int across = 0; across < imageRGB.length; across++){
            for(int endlong = 0; endlong < imageRGB[across].length; endlong++){
                if(imageRGB[across][endlong] != screenRGB[x + across][y + endlong]){return false;}
            }
        }
        return true;
    }

    /**
     * 两图对比全等
     * @param one
     * @param two
     * @return 如果两图全等返回true
     */
    public static boolean isImageCompare(BufferedImage one, BufferedImage two){
        int[][] oneArray = getRGB(one);
        int[][] twoArray = getRGB(two);

        for(int x = 0; x < oneArray.length; x++){
            for(int y = 0; y < oneArray[x].length; y++){
                if(oneArray[x][y] != twoArray[x][y]){ return false; }
            }
        }
        return true;
    }
}
