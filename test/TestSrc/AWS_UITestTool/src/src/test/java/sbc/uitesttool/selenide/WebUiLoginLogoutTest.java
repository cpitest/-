package sbc.uitesttool.selenide;

import static com.codeborne.selenide.Selenide.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;
import org.sahagin.runlib.external.PageDoc;

import com.codeborne.selenide.Configuration;

import sbc.uitesttool.selenide.pageparts.WebUiRegistFile;
import sbc.uitesttool.selenide.report.ReportCsv;

//TODO:enumの処理共通化

@PageDoc("ログインエージング")
public class WebUiLoginLogoutTest {
    // TODO:後で共通化する
    private static final String BASE_URL = "https://cvs.so.sh.airfolc.co.jp/sharp_netprint/ja/top.aspx";

    @BeforeClass
    public static void beforeClass() {
	Configuration.browser = BrowserType.CHROME;
	System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
    }

    @AfterClass
    public static void afterClass() {
	ReportCsv.outputCsv();
    }

    @Test
    public void ログインテスト() throws InterruptedException, IOException {
	open(BASE_URL);

    for(int i = 0; i < 2; i++){
	    //ID_x_x_ログインエージング
	    Thread.sleep(10000);

    	/* *****ログイン******/
    	WebUiRegistFile.ログイン名.テキストを上書き("flashbackdisco.popo@gmail.com");
    	WebUiRegistFile.パスワード.テキストを上書き("cpinfo00");
    	WebUiRegistFile.ログイン.クリック();

    	Thread.sleep(20000);	//wait

    	/* 簡単操作が出ているときの処理 */
    	/* 次回から表示しないチェックON */

    	WebUiRegistFile.次回から表示しない.チェックボックス選択(true);
    	Thread.sleep(10000);

	    WebUiRegistFile.次回から表示しない.TABEnterキー押下();
		Thread.sleep(30000);

    	//ログイン完了確認：ユーザ番号確認
    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");
    	Thread.sleep(5000);

    	//ログアウト
    	WebUiRegistFile.ログアウト.クリック();

    	//ログアウト確認
    	WebUiRegistFile.ログアウト確認.画像のソースを検証("img_ja/btn_login.png");

    }

    	Thread.sleep(10000);

    	close();
    }

}

