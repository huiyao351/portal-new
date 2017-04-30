package com.gtis.portal.util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: jibo
 * Date: 11-8-24
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class SignUitl {
    private static String DEFAULT_FONT_NAME="楷体_GB2312";
    private static int DEFAULT_FONT_SIZE=24;
    private static Color DEFAULT_COLOR=Color.DARK_GRAY;
    private static int IMAGE_MAX_WIDTH=2000;
    /**
     * 生成一张带有用户姓名的图片
     * @param userName
     * @param outputStream
     * @throws Exception
     */
    public static void BuildSignImage(String userName,OutputStream outputStream) throws  Exception{
    	BuildTextImage(outputStream,userName,100,50,"华文新魏",24,new Color(26,29,54));
    }

    public static void BuildTextImage(OutputStream outputStream,String text,int width,int height,
                                      String fontName,int fontSize,Color fontColor) throws  Exception{
        if (StringUtils.isBlank(text))
            return ;
        if (StringUtils.isBlank(fontName))
            fontName=      DEFAULT_FONT_NAME;
        if (fontSize==0)
            fontSize= DEFAULT_FONT_SIZE;
        if (fontColor==null)
            fontColor= DEFAULT_COLOR;
        Font textFont=new Font(fontName, Font.BOLD, fontSize);
        BufferedImage imageTemp = new BufferedImage(IMAGE_MAX_WIDTH, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gTemp = imageTemp.createGraphics();
        gTemp.setFont(textFont);
        FontMetrics  fm=gTemp.getFontMetrics(textFont);
        int textWidth=fm.stringWidth(text);//长度
        gTemp.setBackground(Color.WHITE);//设置背景色
        gTemp.clearRect(0, 0, IMAGE_MAX_WIDTH, height);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        gTemp.setColor(fontColor);
        gTemp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gTemp.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        gTemp.drawString(text, 0, 32);
        gTemp.dispose();
        imageTemp.flush();

        BufferedImage imageText = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gImageText = imageText.createGraphics();
        gImageText.drawImage(imageTemp,0,0,width,height,0,0,textWidth,height,null);
        gImageText.dispose();
        imageText.flush();
        ImageIO.write(imageText, "jpg", outputStream);
    }

    public static void main(String[] param)throws  Exception{
        OutputStream img = new FileOutputStream("c:\\1.jpg");
        SignUitl.BuildSignImage("管理员",img);
    }
}
