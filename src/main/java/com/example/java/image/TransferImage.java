package com.example.java.image;

import com.alibaba.fastjson.JSON;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author pzblog
 * @since 2021-10-29
 */
public class TransferImage {

    public static void main(String[] args) throws IOException {
        String path = "/Users/panzhi/Desktop/11.jpeg";
        int result = getImgRotateAngle(new FileInputStream(path));
        System.out.println(result);
    }


    public static int getImgRotateAngle(InputStream inputStream) {
        int rotateAngle = 0;
        try {
            Metadata metadata = JpegMetadataReader.readMetadata(inputStream);
            Iterable<Directory> directories = metadata.getDirectories();
            for (Directory directory : directories) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(JSON.toJSONString(tag));

                    int tagType = tag.getTagType();
                    //照片拍摄角度信息
                    if (274 == tagType) {
                        String description = tag.getDescription();
                        //Left side, bottom (Rotate 270 CW)
                        switch (description) {
                            //顺时针旋转90度
                            case "Right side, top (Rotate 90 CW)":
                                rotateAngle = 90;
                                break;
                            case "Left side, bottom (Rotate 270 CW)":
                                rotateAngle = 270;
                                break;
                            case "Bottom, right side (Rotate 180)":
                                rotateAngle = 180;
                                break;
                            default:
                                rotateAngle = 0;
                                break;
                        }
                    }

                }
            }
            return rotateAngle;
        } catch (Exception e) {
            return 0;
        }
    }

}
