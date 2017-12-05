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
import java.io.File;
import java.io.IOException;
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

    public Optional<String> getCodeFromImage(File file, Boolean useHints) throws Exception {

        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage.getHeight() > READER_HEIGHT || bufferedImage.getWidth() > READER_HEIGHT) {
            Dimension newMaxSize = new Dimension(READER_WIDTH, READER_HEIGHT);
            bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, newMaxSize.width, newMaxSize.height);
        }

        if (useHints)
            return this.getCodeFromImage(bufferedImage, this.hintsMap);
        else
            return this.getCodeFromImage(bufferedImage, new HashMap<>());
    }

    public Optional<String> getCodeFromImage(File file, Map<DecodeHintType, Object> hints) throws Exception {
        if (file == null || file.getName().trim().isEmpty())
            throw new IllegalArgumentException("File not found, or invalid file name.");

        BufferedImage tmpImage;
        try {
            tmpImage = ImageIO.read(file);
        } catch (IOException tmpIoe) {
            throw new Exception(tmpIoe.getMessage());
        }

        if (tmpImage == null)
            throw new IllegalArgumentException("Could not decode image.");

        return getCodeFromImage(tmpImage, hints);
    }

    private Optional<String> getCodeFromImage(BufferedImage tmpImage, Map<DecodeHintType, Object> hints) throws Exception {

        LuminanceSource tmpSource = new BufferedImageLuminanceSource(tmpImage);
        BinaryBitmap tmpBitmap = new BinaryBitmap(new HybridBinarizer(tmpSource));
        MultiFormatReader tmpBarcodeReader = new MultiFormatReader();

        for (int i = 0; i < 3; i++) {
            try {
                Result tmpResult;
                if (hints != null && !hints.isEmpty())
                    tmpResult = tmpBarcodeReader.decode(tmpBitmap, hints);
                else
                    tmpResult = tmpBarcodeReader.decode(tmpBitmap);

                return Optional.of(String.valueOf(tmpResult.getText()));
            } catch (Exception exception) {
                logger.info("Rotating...");
                tmpBitmap = tmpBitmap.rotateCounterClockwise();
            }
        }
        return Optional.empty();
    }

    private static final Integer READER_WIDTH = 720;

    private static final Integer READER_HEIGHT = 720;

    private static Logger logger = LoggerFactory.getLogger(BarCodeReader.class);

}
