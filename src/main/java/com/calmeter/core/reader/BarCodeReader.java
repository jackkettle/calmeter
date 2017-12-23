package com.calmeter.core.reader;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Component
public class BarCodeReader {

    private Map<DecodeHintType, Object> hintsMap;

    public BarCodeReader() {
        Map<DecodeHintType, Object> hintsMap = new EnumMap<>(DecodeHintType.class);
        hintsMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hintsMap.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        hintsMap.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
        this.hintsMap = hintsMap;
    }

    public BufferedImage resize(BufferedImage tmpImage) {
        if (tmpImage.getHeight() > READER_HEIGHT || tmpImage.getWidth() > READER_HEIGHT) {
            Dimension newMaxSize = new Dimension(READER_WIDTH, READER_HEIGHT);
            return Scalr.resize(tmpImage, Scalr.Method.QUALITY, newMaxSize.width, newMaxSize.height);
        }
        return tmpImage;
    }

    public Optional<String> getCodeFromImage(File tmpImage) throws IOException {
        InputStream in = new FileInputStream(tmpImage);
        BufferedImage bufferedImage = ImageIO.read(in);
        return getCodeFromImage(bufferedImage);
    }

    public Optional<String> getCodeFromImage(BufferedImage tmpImage) {

        tmpImage = resize(tmpImage);
        LuminanceSource tmpSource = new BufferedImageLuminanceSource(tmpImage);
        BinaryBitmap tmpBitmap = new BinaryBitmap(new HybridBinarizer(tmpSource));
        MultiFormatReader tmpBarcodeReader = new MultiFormatReader();
        for (int i = 0; i < 3; i++) {
            try {
                Result tmpResult = tmpBarcodeReader.decode(tmpBitmap, this.hintsMap);
                return Optional.of(String.valueOf(tmpResult.getText()));
            } catch (Exception exception) {
                logger.info("Could not read barcode. Rotating...");
                tmpBitmap = tmpBitmap.rotateCounterClockwise();
            }
        }
        return Optional.empty();
    }

    private static final Integer READER_WIDTH = 720;

    private static final Integer READER_HEIGHT = 720;

    private static Logger logger = LoggerFactory.getLogger(BarCodeReader.class);

}
