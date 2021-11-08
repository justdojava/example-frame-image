package com.example.java.image;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

/**
 * @author panzhi
 * @since 2021-10-29
 */
public class EXIFTest {

    public static void main(String[] args) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(new File("/Users/panzhi/Desktop/11.jpeg"));

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(String.format("[%s] - %s = %s",
                        directory.getName(), tag.getTagName(), tag.getDescription()));
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }

    }
}
