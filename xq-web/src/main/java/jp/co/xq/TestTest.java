package jp.co.xq;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.xq.utils.FileManager;

/**
 * @author tian
 * @since 2019/02/08
 */
public class TestTest {

  static int _BATCH_FILE_LIMIT = 5;
  static int _BATCH_MAX_SIZE = 1012;

  public static void main(String[] args) throws Exception {
    // for (Path path : getBrandItemFileList()) {
    // System.out.println(path);
    // }
    // System.out.println(15 == 0xf ? (0xd != 13 ? 10 : 11) : 1 );

    // System.out.println("\u2014" + " -> " + "\u2015");
    // System.out.println("\u301C" + " -> " + "\u2015");
    // System.out.println("\u2016" + " -> " + "\u2225");
    // System.out.println("\u2212" + " -> " + "\uFF0D");
    // System.out.println("\u00A2" + " -> " + "\uFFE0");
    // System.out.println("\u00A3" + " -> " + "\uFFE1");
    // System.out.println("\u00AC" + " -> " + "\uFFE2");
    // System.out.println("\u00A0" + " -> " + "");
    // System.out.println("\u0012" + " -> " + "");
    // System.out.println("\u0018" + " -> " + "");
    // System.out.println("\u0020" + " -> " + "");

    System.out
        .println(
            BigDecimal.valueOf(123.0).compareTo(BigDecimal.valueOf(123.0000)));
  }

  public static List<Path> getBrandItemFileList() throws IOException {

    List<Path> fileList = FileManager.getFileList("c:/csv");
    fileList = fileList
        .stream()
          .filter(path -> !Files.isDirectory(path))
          .collect(Collectors.toList());
    List<Path> resultFilePathList = new ArrayList<>();
    // 処理ファイル数制限
    int size = fileList.size() > _BATCH_FILE_LIMIT ? _BATCH_FILE_LIMIT
        : fileList.size();
    int processLineCount = 0;
    for (int i = 0; i < size; i++) {
      Path tmpPath = fileList.get(i);
      // directoryは処理しない
      if (Files.isDirectory(tmpPath)) {
        continue;
      }
      // ファイルの内容を取得
      processLineCount += FileManager
          .getFileContentsAsStringList(tmpPath, Charset.forName("Shift-jis"),
              true)
            .size();

      resultFilePathList.add(tmpPath);
      // 最大処理件数超 or 処理ファイル数超の場合
      if (processLineCount >= _BATCH_MAX_SIZE) {
        break;
      }
    }
    System.out
        .println(("処理対象ファイル件数：" + resultFilePathList.size() + "  データライン数："
            + processLineCount));
    return resultFilePathList;
  }
}
