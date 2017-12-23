package com.calmeter.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileUtils {

    public File convert(MultipartFile file) throws IOException
    {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        logger.info("Path: {}", convFile.getAbsolutePath());
        fos.close();
        return convFile;
    }

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

}
