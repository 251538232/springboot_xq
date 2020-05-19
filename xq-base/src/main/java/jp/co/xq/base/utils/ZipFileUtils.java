package jp.co.xq.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;

/**
 * @authore tian
 * @date 2019/01/15
 */
public class ZipFileUtils {

  /**
   * CSVファイルをzipする
   * 
   * @param srcFile
   * @return
   */
  public static String zipFile(File srcFile, File destFile) {
    ZipArchiveOutputStream stream = null;
    InputStream in = null;
    try {
      stream = new ZipArchiveOutputStream(destFile);
      in = new FileInputStream(srcFile);
      ZipArchiveEntry entry = new ZipArchiveEntry(srcFile, srcFile.getName());
      stream.putArchiveEntry(entry);
      IOUtils.copy(in, stream);
      stream.closeArchiveEntry();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } finally {
      try {
        in.close();
        stream.finish();
        stream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return destFile.getPath();
  }
}
