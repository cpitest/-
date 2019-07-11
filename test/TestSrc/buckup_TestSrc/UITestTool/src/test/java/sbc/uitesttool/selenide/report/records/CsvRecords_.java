package sbc.uitesttool.selenide.report.records;

import sbc.uitesttool.selenide.report.ReportStatus;

/**
 * CSVに出力する行をまとめた列挙体
 *
 */
public enum CsvRecords_ {
	/** 法人アカウント一覧に[ユーザー番号のプレフィックス]が表示されていること */
	IDX_X_X_WebUIファイル登録確認("X_X_X", "WebUIからのログイン～ファイルが登録～ログアウトエージング", true), //

    ;

    private String id; // 出図シートのid
    private String testDetail; // 出図シートの確認内容
    private boolean testAble; // selenideでテスト可能な場合はtrue, selenideでテスト不可能な場合はfalse

    public String getId() {
	return id;
    }

    public boolean isTestAble() {
	return testAble;
    }

    public String getTestDetail() {
	return testDetail;
    }

    public ReportStatus getDefaultStatus() {
	if (testAble) {
	    return ReportStatus.NG;
	}

	return ReportStatus.SKIP;
    }

    private CsvRecords_(String id, String testDetail, boolean testAble) {
	this.id = id;
	this.testDetail = testDetail;
	this.testAble = testAble;
    }
}

