package GetScreenTest.selenide.getScreenList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;
import org.sahagin.runlib.external.PageDoc;

import com.codeborne.selenide.Configuration;

public class Common_GetPropertyFiles {

	@PageDoc("全ソースで使用する共通関数")
	public static class CommonFunction {
	    // TODO:後で共通化する

	    @BeforeClass
	    public static void beforeClass() {
			Configuration.browser = BrowserType.CHROME;
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	    }

	    @AfterClass
	    public static void afterClass() {
	    }
	}

    @Test

 	/* ------------------- ファイル登録エージングテスト ---------------- */
    /* 作成日: 	2018/08/29												 */
    /* 引数/返値：	なし												 */
    /* 動作： 設定値をプロパティファイルから読み込む					 */
    /*-------------------------------------------------------------------*/
	/* ------ プロパティファイルの読み込み ------ */

    public void プロパティファイルの読み込み() throws InterruptedException, IOException {

    	File f = new File("C:\\Users\\CPI-126\\Desktop\\GetScreenTest\\src\\test\\resources\\getscreencap.properties");
		InputStream is = new FileInputStream(f);
		Properties properties = new Properties();
		properties.load(is);

		//プロパティファイルから値を取得
		String FBAuname = properties.getProperty("forbizAUserName");	//ユーザー名
		String FBApass = properties.getProperty("forbizAPassword");	//パスワード

		//プロパティファイルの読み込み関数は、メイン関数の中に入れる？

    }


}
