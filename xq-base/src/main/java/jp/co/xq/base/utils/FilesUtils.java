package jp.co.xq.base.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.YearMonth;

import static jp.co.xq.base.common.BaseConstants.DOT;

/**
 * @authore hujiajia
 * @date 2018/07/26
 */
public class FilesUtils {
    private static final Logger logger = LoggerFactory.getLogger(FilesUtils.class);
    private static final String PDF_SUFFIX = "pdf";
    private static final int YM_LEN = 6;


    /**
     * @param src
     * @param dest
     * @throws Exception
     */
    public static boolean zipFiles(Path src, Path dest) {
        ZipArchiveOutputStream stream = null;
        InputStream in = null;
        try {
            File zipFile = src.toFile();
            stream = new ZipArchiveOutputStream(dest.toFile());
            in = new FileInputStream(zipFile);
            ZipArchiveEntry entry = new ZipArchiveEntry(zipFile, zipFile.getName());
            stream.putArchiveEntry(entry);
            IOUtils.copy(in, stream);
            stream.closeArchiveEntry();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                in.close();
                stream.finish();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void unZip(File file) throws Exception {
        InputStream stream = new FileInputStream(file);
        ZipArchiveInputStream inputStream = new ZipArchiveInputStream(stream);
        ZipArchiveEntry entry = null;
        while ((entry = inputStream.getNextZipEntry()) != null) {
            System.out.println(entry.getName());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            org.apache.commons.io.IOUtils.copy(inputStream, outputStream);
            System.out.println(outputStream.toString());
        }
        inputStream.close();
        stream.close();
    }

    /**
     * PDFファイルの格式を確認する
     *
     * @param f PDFファイル
     * @return
     */
    public static boolean checkPdfFormat(File f) {
        if (f.isDirectory()) {
            return false;
        }
        String name = f.getName();
        String suffix = name.substring(name.indexOf(DOT) + DOT.length());
        return suffix.equalsIgnoreCase(PDF_SUFFIX);
    }

    /**
     * 年月フォルダを確認する。
     * 正確の格式：yyyyMM
     *
     * @param file 年月フォルダ
     * @return
     */
    public static boolean checkYMFileFormat(File file) {
        if (!file.isDirectory()) {
            return false;
        }
        String name = file.getName();
        if (name.length() != YM_LEN) {
            return false;
        }
        try {
            YearMonth.of(Integer.valueOf(name.substring(0,4)),Integer.valueOf(name.substring(4)));
            return true;
        } catch (Exception e) {
            logger.error("Wrong format of year and month.",e);
            return false;
        }

    }
}
