package com.calmeter.core.tests.reader;

import com.calmeter.core.reader.BarCodeReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BarCodeReaderTests extends AbstractTestNGSpringContextTests {

    private BarCodeReader barCodeReader;

    private List<File> testFiles;

    @BeforeClass
    public void setUp() throws Exception {
        this.barCodeReader = new BarCodeReader();
        this.testFiles = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("reader");
        Files.newDirectoryStream(Paths.get(url.toURI()), path -> path.toFile().isFile())
                .forEach(file -> this.testFiles.add(file.toFile()));

        logger.info("Total test files: {}", this.testFiles.size());

        Assert.assertEquals(6, this.testFiles.size());
    }

    @Test
    public void barcodeTest1() throws Exception {
        Optional<String> codeWrapper = barCodeReader.getCodeFromImage(testFiles.get(0), true);
        Assert.assertTrue(codeWrapper.isPresent());
        Assert.assertEquals("5054269382013", codeWrapper.get());
    }

    @Test
    public void barcodeTest2() throws Exception {
        Optional<String> codeWrapper = barCodeReader.getCodeFromImage(testFiles.get(1), true);
        Assert.assertTrue(codeWrapper.isPresent());
        Assert.assertEquals("5051140152871", codeWrapper.get());
    }

    @Test
    public void barcodeTest3() throws Exception {
        Optional<String> codeWrapper = barCodeReader.getCodeFromImage(testFiles.get(2), true);
        Assert.assertTrue(codeWrapper.isPresent());
        Assert.assertEquals("10041475", codeWrapper.get());
    }

    @Test
    public void barcodeTest4() throws Exception {
        Optional<String> codeWrapper = barCodeReader.getCodeFromImage(testFiles.get(3), true);
        Assert.assertTrue(codeWrapper.isPresent());
        Assert.assertEquals("03231319", codeWrapper.get());
    }

    @Test
    public void barcodeTest5() throws Exception {
        Optional<String> codeWrapper = barCodeReader.getCodeFromImage(testFiles.get(4), true);
        Assert.assertTrue(codeWrapper.isPresent());
        Assert.assertEquals("5010338200091", codeWrapper.get());
    }

    @Test
    public void barcodeTest6() throws Exception {
        Optional<String> codeWrapper = barCodeReader.getCodeFromImage(testFiles.get(5), true);
        Assert.assertTrue(codeWrapper.isPresent());
        Assert.assertEquals("5410673059000", codeWrapper.get());
    }

    private static Logger logger = LoggerFactory.getLogger(BarCodeReaderTests.class);

}
