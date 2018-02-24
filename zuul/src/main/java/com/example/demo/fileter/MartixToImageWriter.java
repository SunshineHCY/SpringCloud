package com.example.demo.fileter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

public final class MartixToImageWriter {

    //私有不可更改的变量：生成二维码图片的颜色
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    //空的构造方法
    public MartixToImageWriter() {
        // TODO Auto-generated constructor stub
    }
    /**
     * 静态方法
     * BufferedImage是Image的一个子类，BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便的操作这个图片，
     * 通常用来做图片修改操作如大小变换、图片变灰、设置图片透明或不透明等。
     * @param matrix 编码形式
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix)
    {
        //图片的宽度和高度
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //BufferedImage.TYPE_INT_RGB将图片变为什么颜色
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for(int x = 0;x < width;x++)
        {
            for(int y = 0;y < height;y++)
            {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    /**
     * 静态方法 用于生成图片
     * @param matrix 编码形式
     * @param format 图片类型
     * @param file 文件（图片路径，图片名称）
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix,String format,File file) throws IOException
    {
        BufferedImage image = toBufferedImage(matrix);
        if(!ImageIO.write(image, format, file))
        {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }
    /**
     * 输出
     * @param matrix
     * @param format
     * @param stream
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix,String format,OutputStream stream) throws IOException
    {
        BufferedImage image = toBufferedImage(matrix);
        if(!ImageIO.write(image, format, stream))
        {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}



