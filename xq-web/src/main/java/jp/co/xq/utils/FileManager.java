package jp.co.xq.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * CSVファイルを操作するロジック
 *
 */
@Component
public class FileManager {

  /**
   * 指定ディレクトリ内のファイル一覧を取得する
   *
   * @return
   *         ファイルのリストを返す
   *         存在しない場合は空のリストを返す
   *
   * @throws IOException
   */
  public static List<Path> getFileList(String targetDirPath)
      throws IOException {

    Path path = Paths.get(targetDirPath);
    List<Path> results = Files.list(path).collect(Collectors.toList());

    return results;
  }

  /**
   * 指定したファイルの内容をStringのリストとして返す
   * 1行をひとつの要素とする
   *
   * @param filePath
   * @param cs
   * @param headerExists
   * @return
   * @throws IOException
   */
  public static List<String> getFileContentsAsStringList(Path filePath,
      Charset cs, boolean headerExists) throws IOException {

    List<String> results = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(filePath, cs)) {
      String line = null;
      if (headerExists) {
        // ヘッダありの場合最初の一行は読み飛ばす
        br.readLine();
      }
      while ((line = br.readLine()) != null) {
        results.add(line);
      }

    } catch (IOException e) {
      // 呼び出し元に投げる
      throw e;
    }

    return results;

  }

  /**
   * srcFilePathからdstFilePathへファイルを移動させる
   *
   * @param srcFilePath
   * @param dstFilePath
   * @throws IOException
   */
  public static void moveFile(Path srcFilePath, Path dstFilePath)
      throws IOException {

    Files
        .move(srcFilePath, dstFilePath.resolve(srcFilePath.getFileName()),
            StandardCopyOption.REPLACE_EXISTING); // RBPRJ-10053
  }

  /**
   * 文字列 line をファイルの最後に追記する
   * ファイルが存在しない場合は作成する
   *
   * @param line
   * @param filePath
   * @param cs
   * @return
   * @throws IOException
   */
  public static void appendLineToFile(String line, Path filePath, Charset cs)
      throws IOException {

    // ファイルが存在しなければ作成する。追記モードで開く。
    try (BufferedWriter bw = Files
        .newBufferedWriter(filePath, cs, StandardOpenOption.CREATE,
            StandardOpenOption.APPEND)) {
      bw.write(line);
      bw.newLine();
    } catch (IOException e) {
      // 呼び出し元に投げる
      throw e;
    }
  }

  /**
   * 文字列のリスト lines を順にファイルに追記する
   *
   * @param lines
   * @param filePath
   * @param cs
   * @throws IOException
   */
  public static void appendLinesToFile(List<String> lines, Path filePath,
      Charset cs) throws IOException {

    try (BufferedWriter bw = Files
        .newBufferedWriter(filePath, cs, StandardOpenOption.CREATE,
            StandardOpenOption.APPEND)) {
      for (String line : lines) {
        bw.write(line);
        bw.newLine();
      }

    } catch (IOException e) {
      // 呼び出し元に投げる
      throw e;
    }
  }

}
