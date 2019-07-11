package sbc.uitesttool.selenide.report;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import sbc.uitesttool.selenide.report.records.CsvRecords_;

/**
 * 結果CSV出力処理を行うクラス
 *
 */
public class ReportCsv {
    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String CSV_OUTPUT_PATH = "." + SEPARATOR + "report" + SEPARATOR;

    private static Map<CsvRecords_, ReportStatus> reportByResult = new HashMap<>();


    /**
     * 対象テスト結果にOKをつける
     *
     * @param record
     */
    public static void chekedOK(CsvRecords_ record) {
	reportByResult.put(record, ReportStatus.OK);
    }

    /**
     * 対象テスト結果にNGをつける
     *
     * @param testId
     */
    public static void chekedNG(CsvRecords_ record) {
	reportByResult.put(record, ReportStatus.NG);
    }

    /**
     * 結果CSVを出力する
     */
    public static void outputCsv() {
	PrintWriter pw = null;
	try {
	    Path pathByDirectory = Paths.get(CSV_OUTPUT_PATH);
	    if (!Files.exists(pathByDirectory)) {
		Files.createDirectories(pathByDirectory);
	    }

	    Path pathByFile = pathByDirectory.resolve("report.csv");
	    if (Files.exists(pathByFile)) {
		Files.delete(pathByFile);
	    }

	    pw = new PrintWriter(new BufferedWriter(
		    new OutputStreamWriter(new FileOutputStream(pathByFile.toFile(), true), "Shift-JIS")));

	    for (CsvRecords_ csvRecords : CsvRecords_.values()) {
		StringBuilder sb = new StringBuilder();
		sb.append(csvRecords.getId());
		sb.append(",");
		sb.append(csvRecords.getTestDetail());
		sb.append(",");
		sb.append(getStatus(csvRecords));

		System.out.println(sb.toString());
		pw.println(sb.toString());
	    }
	} catch (Exception e) {
	    System.out.println("CSV出力に失敗\r\n" + e.getMessage() + "\r\n" + e.getStackTrace());
	} finally {
	    if (pw != null) {
		pw.close();
	    }
	}
    }

    /**
     * テスト結果を返す
     *
     * @param csvRecords
     *            対象のテスト項目
     * @return
     */
    private static String getStatus(CsvRecords_ csvRecords) {
	ReportStatus status = reportByResult.get(csvRecords);
	if (status != null) {
	    return status.getValue();
	}

	return csvRecords.getDefaultStatus().getValue();
    }
}
