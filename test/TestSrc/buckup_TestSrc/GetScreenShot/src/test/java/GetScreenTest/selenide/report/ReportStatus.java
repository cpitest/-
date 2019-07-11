package GetScreenTest.selenide.report;

/**
 * テスト結果
 *
 */
public enum ReportStatus{
	OK("OK"), //
	NG("NG"), //
	SKIP("SKIP"); // selenideではテストできない項目

	private String value;

	public String getValue(){
		return value;
	}

	private ReportStatus(String value){
		this.value = value;
	}
}
