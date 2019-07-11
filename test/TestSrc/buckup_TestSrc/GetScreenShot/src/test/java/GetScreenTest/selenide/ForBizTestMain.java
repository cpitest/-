package GetScreenTest.selenide;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;
import org.sahagin.runlib.external.PageDoc;

import com.codeborne.selenide.Configuration;

import GetScreenTest.selenide.getScreenList.ID3_ForBizAdmin_CorpInfo_Test;

//import sbc.uitesttool.selenide.report.ReportCsv;

//TODO:enumの処理共通化

@PageDoc("ログインエージング")
public class ForBizTestMain {

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
    public void TestSuiteMain() throws InterruptedException, IOException {

    	//ID1_ログイン画面
    	ID3_ForBizAdmin_CorpInfo_Test id1 = new ID3_ForBizAdmin_CorpInfo_Test();
    	id1.ログイン画面();



    }

}
