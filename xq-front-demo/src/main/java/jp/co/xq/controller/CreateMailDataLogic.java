//package jp.co.xq.controller;
//
//import jp.co.rakuten.brandavenue.dlink.batch.sendmail.constant.SendMailConst;
//import jp.co.rakuten.brandavenue.dlink.batch.sendmail.db.sale.InputFileDataDao;
//import jp.co.rakuten.brandavenue.dlink.batch.sendmail.db.sale.ReceptionManagementDao;
//import jp.co.rakuten.brandavenue.dlink.batch.sendmail.db.sale.dto.MailDataDto;
//import jp.co.rakuten.firstparty.base.annotation.ErrorHandle;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.axis.utils.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * １．メール送信CSVファイルを生成して、指定のパスに格納する
// * ・完了、エラー、保留のデータをそれぞれCSVに出力する
// * ・既に存在するファイルを上書きする
// * ２．メール送信用パラメータをパラメータCSVに出力する
// *
// */
//@Component
//@RequiredArgsConstructor
//@ErrorHandle(id = "CreateMailDataLogic")
//@Slf4j
//public class CreateMailDataLogic {
//
//  /** メール送信データ作成済み（送信ずみとする）1 */
//  private static final String _MAIL_FLAG_SUCCESS = "1";
//  /** 送信先メールアドレス登録されないため、送信不可 */
//  private static final String _MAIL_FLAG_ERROR = "2";
//  /** CSVデータ0件 ファイル生成しない */
//  private static final String _MAIL_FLAG_EMPTY_CSV = "3";
//
//  /** セールＤＢ，基幹ＤＢ，テクマトへの登録完了 2 */
//  private static final String _STATUS_PROCESS_END = "2";
//  /** 該当商品がないため、登録処理をスキップ 3 保留 */
//  private static final String _STATUS_RETENTION = "3";
//
//  /** 上代エラー (8) */
//  private static final String _STATUS_JYOUDAI_ERROR = "8";
//  /** エラーステータス (9) */
//  private static final String _STATUS_ERROR = "9";
//
//  /** ファイル文字コード */
//  private static final String _FILE_CHARACTER_CODE = "Shift_JIS";
//
//  @Autowired
//  private ReceptionManagementDao _receptionManagementDao;
//
//  @Autowired
//  private InputFileDataDao _inputFileDataDao;
//
//  public List<MailDataDto> getMailDataList() {
//    log.info("【開始】メール送信データ取得する");
//    List<MailDataDto> mailDataDtoList = null;
//    // _inputFileDataDao
//    // .getInputFileDataList();
//
//    log.info("【終了】メール送信データ取得する");
//    return mailDataDtoList;
//
//  }
//
//  public MailDataDto processMailData(MailDataDto mailDataDto) {
//    if (mailDataDto == null) {
//      return null;
//    }
//    if (StringUtils.isEmpty(mailDataDto.getSendMailAddress())) {
//      // 送信メール未設定
//      _receptionManagementDao
//          .updateMailFlg(_MAIL_FLAG_ERROR, mailDataDto.getReceiptNumber());
//      return null;
//    }
//
//    if (mailDataDto.getReceiptBranchNumber() == null) {
//      // 送信CSVデータなし
//      _receptionManagementDao
//          .updateMailFlg(_MAIL_FLAG_EMPTY_CSV, mailDataDto.getReceiptNumber());
//      return null;
//    }
//
//    // そのまま返す
//    return mailDataDto;
//  }
//
//  public void createSendMailCsv(List<MailDataDto> mailDataDtoList) {
//    String receiptNumber = mailDataDtoList.get(0).getReceiptNumber().toString();
//    Map<String, List<MailDataDto>> dtoListMap = mailDataDtoList
//        .stream()
//          .collect(Collectors.groupingBy(MailDataDto::getStatus));
//
//    // 成功のデータを出力する
//    // TODO 販売開始日の取得方法確認必要
//    List<MailDataDto> endDataList = dtoListMap.get(_STATUS_PROCESS_END);
//    if (endDataList != null) {
//
//      createInputFileDataCsv(endDataList, SendMailConst.FILE_PATH_SUCCESS,
//          receiptNumber);
//    }
//
//    // エラー部分のデータを出力する
//    List<MailDataDto> errDataList = dtoListMap.get(_STATUS_ERROR);
//    List<MailDataDto> jdErrDataList = dtoListMap.get(_STATUS_JYOUDAI_ERROR);
//    // ステータスが 8,9のデータ合弁
//    List<MailDataDto> allErrDataList = new ArrayList<>();
//    if (errDataList != null) {
//      allErrDataList.addAll(endDataList);
//    }
//    if (jdErrDataList != null) {
//      allErrDataList.addAll(jdErrDataList);
//    }
//    if (allErrDataList.size() > 0) {
//      createInputFileDataCsv(errDataList, SendMailConst.FILE_PATH_ERROR,
//          receiptNumber);
//    }
//
//    // 保留分のデータを出力する
//    List<MailDataDto> retentionDataList = dtoListMap.get(_STATUS_RETENTION);
//    if (retentionDataList != null) {
//      createInputFileDataCsv(retentionDataList,
//          SendMailConst.FILE_PATH_RETENTION, receiptNumber);
//    }
//
//    // メールフラグを１に更新する
//    _receptionManagementDao
//        .updateMailFlg(_MAIL_FLAG_SUCCESS, Integer.parseInt(receiptNumber));
//  }
//
//  private void createInputFileDataCsv(List<MailDataDto> endDataList,
//      String filePath, String fileName) {
//    // ヘッダー取得
//    List<Object> headers = new ArrayList<>();
//    for (int i = 0; i < SendMailConst.CSV_HEADER_FIELDS.length; i++) {
//      headers.add(SendMailConst.CSV_HEADER_FIELDS[i][0]);
//    }
//    // CSV内容取得
//    int size = endDataList.size();
//    List<List<Object>> dataList = new ArrayList<>();
//    for (int i = 0; i < size; i++) {
//      List<Object> fields = new ArrayList<>();
//      for (int j = 0; j < SendMailConst.CSV_HEADER_FIELDS.length; j++) {
//        fields
//            .add(getResultByFieldName(SendMailConst.CSV_HEADER_FIELDS[j][1],
//                endDataList.get(i)));
//      }
//      dataList.add(fields);
//    }
//    createCsvFile(headers, dataList, filePath, fileName);
//  }
//
//  private void writeHeader(List<Object> header, BufferedWriter csvWriter)
//      throws IOException {
//    for (Object data : header) {
//      StringBuffer stringBuffer = new StringBuffer();
//      String rowStr = stringBuffer
//          .append(data)
//            .append(SendMailConst.CSV_SEPERATOR)
//            .toString();
//      csvWriter.write(rowStr);
//    }
//    csvWriter.newLine();
//  }
//
//  private void writeContent(List<Object> dataList, BufferedWriter csvWriter)
//      throws IOException {
//    for (Object data : dataList) {
//      StringBuffer stringBuffer = new StringBuffer();
//      String rowStr = stringBuffer
//          .append("\"")
//            .append(data)
//            .append("\"")
//            .append(SendMailConst.CSV_SEPERATOR)
//            .toString();
//      csvWriter.write(rowStr);
//    }
//    csvWriter.newLine();
//  }
//
//  /**
//   * CSVファイル作成
//   *
//   * @param head
//   * @param dataList
//   *          ファイルデータ
//   * @param outPutPath
//   *          CSVファイル格納パス
//   * @param filename
//   *          ファイル名
//   * @return
//   */
//  private File createCsvFile(List<Object> head, List<List<Object>> dataList,
//      String outPutPath, String filename) {
//    File csvFile = null;
//    BufferedWriter csvWriter = null;
//    try {
//      csvFile = new File(
//          outPutPath + File.separator + filename + SendMailConst.FILE_SUFFIX);
//      // フォルダ存在しない場合、フォルダ新規生成する
//      File parent = csvFile.getParentFile();
//      if (parent != null && !parent.exists()) {
//        parent.mkdirs();
//      }
//      csvFile.createNewFile();
//
//      // ファイル書き込み shift-jis
//      csvWriter = new BufferedWriter(new OutputStreamWriter(
//          new FileOutputStream(csvFile), _FILE_CHARACTER_CODE), 1024);
//      writeHeader(head, csvWriter);
//
//      for (List<Object> data : dataList) {
//        writeContent(data, csvWriter);
//      }
//      csvWriter.flush();
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      try {
//        csvWriter.close();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//    return csvFile;
//  }
//
//  /**
//   * dtoクラスから指定フィールド名の値を取得する
//   *
//   * @param fieldName
//   *          フィールド名
//   * @param dto
//   *          dtoクラスインスタンス
//   * @return フィールド値
//   */
//  private static Object getResultByFieldName(String fieldName, Object dto) {
//    try {
//      Field field = dto.getClass().getDeclaredField(fieldName);
//      field.setAccessible(true);
//      return field.get(dto) == null ? "" : field.get(dto);
//    } catch (NoSuchFieldException e) {
//      e.printStackTrace();
//    } catch (IllegalAccessException e) {
//      e.printStackTrace();
//    }
//    return null;
//  }
//}