package sbc.uitesttool.selenide;

import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.BrowserType;
import org.sahagin.runlib.external.PageDoc;

import com.codeborne.selenide.Configuration;

import sbc.uitesttool.selenide.pageparts.WebUiRegistFile;
import sbc.uitesttool.selenide.report.ReportCsv;
import sbc.uitesttool.selenide.report.records.CsvRecords_;

//TODO:enumの処理共通化

@PageDoc("一般ユーザのファイル登録テスト")
public class WebUiResistTest {
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

   	/* ------------------- ファイル登録エージングテスト ---------------- */
    /* 作成日: 	2018/08													 */
    /* 引数/返値：	なし												 */
    /* 動作：WebUIからのログイン・ファイル登録・削除・ログアウト 		 */
    /*-------------------------------------------------------------------*/

    @Test
    public void ファイル登録テスト() throws InterruptedException, IOException {
	open(BASE_URL);
   	/* ------ 変数の宣言 ------ */

	//回数カウント変数
	int rescount = 0;

	//logの出力先
	String logfile ="C:\\Users\\CPI-126\\Desktop\\UITestTool\\report\\Debuglog";

	/* ------ プロパティファイルの読み込み ------ */

	File f = new File("C:\\Users\\CPI-126\\Desktop\\UITestTool\\src\\test\\resources\\webui.properties");
	InputStream is = new FileInputStream(f);
	Properties properties = new Properties();
	properties.load(is);

	//プロパティファイルから値を取得
	String uname = properties.getProperty("username");	//ユーザー名
	String pass = properties.getProperty("password");	//パスワード
	String sloop = properties.getProperty("loop");		//ループ回数
	String sexcel = properties.getProperty("fexcel");	//テストファイル名（Excel)
	String sword = properties.getProperty("fword");		//テストファイル名（Word)
	String sppt = properties.getProperty("fppt");		//テストファイル名（パワーポイント)
	String spdf = properties.getProperty("fpdf");		//テストファイル名（PDF)
	String spng = properties.getProperty("fpng");		//テストファイル名（png)
	String sjpeg = properties.getProperty("fjpeg");		//テストファイル名（jpeg)

	//テスト原稿LIST(文書)
	List<String> documenttype = new ArrayList<String>();
	documenttype.add(sexcel);
	documenttype.add(sword);
	documenttype.add(sppt);
	documenttype.add(spdf);

	//テスト原稿LIST(画像)
	List<String> phototype =  new ArrayList<String>();
	phototype.add(spng);
	phototype.add(sjpeg);

  	/* デバッグログ用Logファイルの作成 */
	   try{
		   PrintStream out =new PrintStream(logfile+"test.log");
		   System.setOut(out);

	   }catch(FileNotFoundException e){
		   e.printStackTrace();
	   }


	//stringをintに変換
	int iloop = Integer.parseInt(sloop);

	//複数回テスト処理
    for(int count = 0; count < iloop; count++){

	    //ID_x_x_ログインエージング
	    Thread.sleep(10000);

    	/* *****ログイン******/
    	WebUiRegistFile.ログイン名.テキストを上書き(uname);
    	WebUiRegistFile.パスワード.テキストを上書き(pass);
    	WebUiRegistFile.ログイン.クリック();

    	Thread.sleep(20000);

    	/* 簡単操作が出ているときの処理 */
    	WebUiRegistFile.次回から表示しない.TABEnterキー押下();
    	Thread.sleep(20000);

    	//ログイン完了確認：ユーザ番号確認
    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");
    	Thread.sleep(5000);

    	/* 文書ファイルを登録する */
     	for(String doc : documenttype){
	    	//文書を登録するを押す
	    	WebUiRegistFile.文書を登録する.クリック();
	    	Thread.sleep(5000);

	    	//テキストボックスを押す
	    	WebUiRegistFile.ファイルを指定.アップロードするファイルを選択する(doc);
	    	Thread.sleep(10000);

	    	//このファイルを登録する
	    	WebUiRegistFile.このファイルを登録.クリック();
	    	Thread.sleep(10000);

	    	//ファイル登録設定の画面遷移確認： 登録に必要な情報を入力・設定してください
	    	//用紙サイズを変更する：A4
	    	WebUiRegistFile.用紙選択.Selectボックス選択("A4");
	    	Thread.sleep(10000);

	    	//登録
	    	WebUiRegistFile.登録押下.クリック();
	    	Thread.sleep(10000);

	    	//マイボックスに戻る
	    	WebUiRegistFile.マイボックスに戻る.クリック();
	    	Thread.sleep(35000);
	    	//マイボックス遷移確認：：ユーザ番号確認
	    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");

	    	//ステータス完了アイコンが表示されていることを確認
	    	WebUiRegistFile.ステータスを検証.画像のソースを検証("img_ja/ic_stat_ready.png");
	    	Thread.sleep(10000);

	    	//ファイルの削除
	    	WebUiRegistFile.すべて選択を押下.クリック();
	    	Thread.sleep(5000);

			//選択したファイルを削除
	    	WebUiRegistFile.選択したファイルを削除.クリック();
	    	Thread.sleep(5000);

			//削除ボックス遷移確認：選択したファイルを削除します
	    	WebUiRegistFile.削除するを押下.クリック();
	    	Thread.sleep(10000);

	    	//削除完了確認：HDDの登録ファイル容量が0であること
	    	WebUiRegistFile.登録容量.表示文言を検証("0MB");
	    	Thread.sleep(5000);
    	}

       	/* 画像ファイルを登録する */
     	for(String photo : phototype){
	    	//画像を登録するを押す
     		WebUiRegistFile.画像を登録する.クリック();
	    	Thread.sleep(5000);	//wait

	    	//テキストボックスを押す
	    	WebUiRegistFile.ファイルを指定.アップロードするファイルを選択する(photo);
	    	Thread.sleep(10000);

	    	//このファイルを登録する
	    	WebUiRegistFile.このファイルを登録.クリック();
	    	Thread.sleep(10000);

	    	//マイボックスに戻る
	    	WebUiRegistFile.マイボックスに戻る.クリック();
	    	Thread.sleep(35000);

	    	//マイボックス遷移確認：ユーザ番号確認
	    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");

	    	//ステータス完了アイコンが表示されていることを確認
	    	WebUiRegistFile.ステータスを検証.画像のソースを検証("img_ja/ic_stat_ready.png");
	    	Thread.sleep(10000);

	    	//ファイルの削除
	    	WebUiRegistFile.すべて選択を押下.クリック();
	    	Thread.sleep(5000);

			//選択したファイルを削除
	    	WebUiRegistFile.選択したファイルを削除.クリック();
	    	Thread.sleep(5000);

			//削除ボックス遷移確認：選択したファイルを削除します
	    	WebUiRegistFile.削除するを押下.クリック();
	    	Thread.sleep(10000);

	    	//削除完了確認：HDDの登録ファイル容量が0であること
	    	WebUiRegistFile.登録容量.表示文言を検証("0MB");
	    	Thread.sleep(5000);
    	}

    	//ログアウト
    	WebUiRegistFile.ログアウト.クリック();

    	//ログアウト確認
    	WebUiRegistFile.ログアウト確認.画像のソースを検証("img_ja/btn_login.png");

	    //CSVにOKをつける
       	ReportCsv.chekedOK(CsvRecords_.IDX_X_X_WebUIファイル登録確認);

       	// ループ回数カウント
       	rescount=count+1;

       //デバッグログ用現在時間の取得
 	   LocalDateTime day = LocalDateTime.now();
 	   System.out.println(day+":\t"+"ループ回数："+rescount+"回成功しました");

    }

    Thread.sleep(10000);

    close();
    }

}

