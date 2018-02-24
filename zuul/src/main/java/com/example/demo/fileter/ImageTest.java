package com.example.demo.fileter;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//二维码生成
public class ImageTest{
public static void main(String[]args)  {
        //二维码中保存的信息
        String content="hi，这是您的二维码！";
        //生成的二维码保存的路径
        String path="E:\\test";
        MultiFormatWriter multiFormatWrite=new MultiFormatWriter();
        Map hints=new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        // 按照指定的宽度，高度和附加参数对字符串进行编码
        //生成二维码
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    BitMatrix bitMatrix= null;
    try {
        bitMatrix = multiFormatWrite.encode(content, BarcodeFormat.QR_CODE,400,400,hints);
    } catch (WriterException e) {
        e.printStackTrace();
    }
        File file1=new File(path,sdf.format(new Date())+".jpg");
        // 写入文件
    try {
        MartixToImageWriter.writeToFile(bitMatrix,"jpg",file1);
    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println("二维码图片生成成功！");
        }
        }

