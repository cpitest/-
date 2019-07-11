package sbc.uitesttool.selenide.pageparts;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriverException;

import sbc.uitesttool.selenide.report.ScreenShot;

public class SelenideTestWatcher extends TestWatcher {

  	/* ------------------- ファイル登録エージングテスト ---------------- */
    /* 作成日: 	2018/10													 */
    /* 引数/返値：	なし												 */
    /* 動作： テスト失敗時にスクリーンショットを取得する				 */
    /*-------------------------------------------------------------------*/

	  // 失敗したときはスクリーンショットを撮る
	  @Override
	  protected void failed(Throwable e, Description description) {
	    super.failed(e, description);
	    try {
			   Calendar myCal = Calendar.getInstance();
			   DateFormat myFormat = new SimpleDateFormat("MMddHHmmss");
			   String fdate = myFormat.format(myCal.getTime());
			   ScreenShot.takesScreenshot("ErrorPic_"+fdate+".png");
		} catch (WebDriverException | IOException | InterruptedException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	  }

}
