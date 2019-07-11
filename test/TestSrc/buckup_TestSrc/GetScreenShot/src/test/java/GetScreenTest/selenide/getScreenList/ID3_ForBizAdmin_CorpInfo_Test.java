package GetScreenTest.selenide.getScreenList;

import static com.codeborne.selenide.Selenide.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;
import org.sahagin.runlib.external.PageDoc;

import com.codeborne.selenide.Configuration;

import GetScreenTest.selenide.report.ScreenShot;

//import sbc.uitesttool.selenide.report.ReportCsv;

//TODO:enumの処理共通化

@PageDoc("ログイン画面キャプチャ取得")
public class ID3_ForBizAdmin_CorpInfo_Test {
    // TODO:後で共通化する
    private static final String BASE_URL = "https://cvs.so.sh.airfolc.co.jp/forBiz/view/main/login.html";

    @BeforeClass
    public static void beforeClass() {
	Configuration.browser = BrowserType.CHROME;
	System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
    }

    @AfterClass
    public static void afterClass() {
	//ReportCsv.outputCsv();
    }

    @Test
    public void ログイン画面() throws InterruptedException, IOException {
	open(BASE_URL);

        //ID1_ログイン画面
	    Thread.sleep(10000);

    	/* スクリーンショット */
	    ScreenShot.takesScreenshot("ログイン画面.png");

    	Thread.sleep(10000);

    	close();
    }

}

