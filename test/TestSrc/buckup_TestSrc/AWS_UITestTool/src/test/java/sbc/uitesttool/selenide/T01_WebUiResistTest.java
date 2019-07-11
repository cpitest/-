package sbc.uitesttool.selenide;

import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.openqa.selenium.remote.BrowserType;
import org.sahagin.runlib.external.PageDoc;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit.TextReport;

import sbc.uitesttool.selenide.pageparts.SelenideTestWatcher;
import sbc.uitesttool.selenide.pageparts.WebUiRegistFile;
import sbc.uitesttool.selenide.report.ReportCsv;

//TODO:enumの処理共通化

@PageDoc("一般ユーザのファイル登録テスト")
public class T01_WebUiResistTest {
    // TODO:後で共通化する
    private static final String BASE_URL = "https://nwp.shcloudio.com/sharp_netprint/ja/top.aspx";

   	/* ------------------- テスト実行失敗時のエビデンス取得------------------------- */
    /* 作成日: 	2018/10																 */
    /* 引数/返値：	なし												 			 */
    /* 動作：テスト失敗時のモニタログ/失敗時のキャプチャ取得 						 */
    /*-------------------------------------------------------------------------------*/

    /* debug code Start---->*/
    //実行失敗時に左側のコンソールに失敗した要素のID取得時間が出る
    @Rule
    public TestRule report = new TextReport();

    //実行失敗時にスクリーンキャプチャを取得する
    @Rule
    public SelenideTestWatcher watcher = new SelenideTestWatcher();

   /* <-----debug code End>*/

    @BeforeClass
    public static void beforeClass() {
	Configuration.browser = BrowserType.CHROME;
	System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");

	//見つからない場合は5分待つ
	Configuration.timeout = 300000;

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
	String logfile ="C:\\Users\\CPI-148\\Documents\\DeskTop\\02.AutomationTool\\TestSrc\\AWS_UITestTool\\report\\debuglog";

	/* ------ プロパティファイルの読み込み ------ */

	File f = new File("C:\\Users\\CPI-148\\Documents\\DeskTop\\02.AutomationTool\\TestSrc\\AWS_UITestTool\\src\\test\\resources\\webui.properties");
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
		   Calendar myCal = Calendar.getInstance();
		   DateFormat myFormat = new SimpleDateFormat("MMddHHmmss");
		   String fdate = myFormat.format(myCal.getTime());
		   PrintStream out =new PrintStream(logfile+"\\Debuglog_"+fdate+".log");
		   System.setOut(out);

	 	   LocalDateTime day1 = LocalDateTime.now();
	 	   System.out.println(day1+":\t"+"テスト開始");

	   }catch(FileNotFoundException e){
		   e.printStackTrace();
	   }


	//stringをintに変換
	int iloop = Integer.parseInt(sloop);

		//ID_x_x_ログインエージング
		Thread.sleep(10000);

    	/* *****ログイン******/
    	WebUiRegistFile.ログイン名.テキストを上書き(uname);
    	WebUiRegistFile.パスワード.テキストを上書き(pass);
    	WebUiRegistFile.ログイン.クリック();

    	Thread.sleep(5000);

    	/* 簡単操作が出ているときの処理 */
    	WebUiRegistFile.次回から表示しない.TABEnterキー押下();
    	Thread.sleep(5000);

    	//ログイン完了確認：ユーザ番号確認
    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");
    	Thread.sleep(5000);

    	//複数回テスト処理
    	resetforloop: for(int count = 0; count < iloop; count++){

    	//ループ毎にCookieを消去する
    	//WebDriverRunner.getWebDriver().manage().deleteAllCookies();

    	/* 文書ファイルを登録する */
     	for(String doc : documenttype){
	    	//文書を登録するを押す
	    	WebUiRegistFile.文書を登録する.クリック();
	    	Thread.sleep(5000);

    		//アップロードファイルを指定する
	    	WebUiRegistFile.ファイルを指定.アップロードするファイルを選択する(doc);
	    	Thread.sleep(5000);

	    	//---条件文【[このファイルを登録する]ボタンがグレイアウトしている間はアップロードファイル入力をやり直す】---
	    	boolean fuploadactive = $("#ibtnUpload").exists();
	    	boolean fuploadnonactive = $("#Img5").exists();

	    	if(fuploadactive == true) {
		    	//このファイルを登録する
		    	WebUiRegistFile.このファイルを登録.クリック();
		    	Thread.sleep(5000);
	    	}
	    	else if(fuploadnonactive == true){
	    		System.out.print("【このファイルを登録する】ボタンが見つかりません：ファイルが正しく入力されませんでした");
		    	while(fuploadnonactive == true ) {
		    		//アップロードファイルを指定する
			    	WebUiRegistFile.ファイルを指定.アップロードするファイルを選択する(doc);
			    	Thread.sleep(5000);
		    	}
		    	//このファイルを登録する
		    	WebUiRegistFile.このファイルを登録.クリック();
		    	Thread.sleep(5000);
	    	}
	    	else {
	    		System.out.print("【このファイルを登録する】ボタンが見つかりません：不明なエラーが発生しました\n");
	    		System.out.print("ファイル登録ループの最初に戻ります");

	    		// ループ回数カウント
	           	rescount=count+1;

	           //デバッグログ用現在時間の取得
	     	   LocalDateTime day2 = LocalDateTime.now();
	     	   System.out.println(day2+":\t"+"単体：ループ回数："+rescount+"回目：不明なエラーが発生しました");

	    		break resetforloop;
	    	}

	    	//フラグの初期化
	    	fuploadactive = false;

	    	//ファイル登録設定の画面遷移確認： 登録に必要な情報を入力・設定してください
	    	//用紙サイズを変更する：A4
	    	WebUiRegistFile.用紙選択.Selectボックス選択("A4");
	    	Thread.sleep(5000);

	    	//登録
	    	WebUiRegistFile.登録押下.クリック();
	    	Thread.sleep(5000);

	    	//マイボックスに戻る
	    	WebUiRegistFile.マイボックスに戻る.クリック();
	    	Thread.sleep(5000);

	    	//マイボックス遷移確認：：ユーザ番号確認
	    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");

	    	//ステータス完了アイコンが表示されていることを確認
	    	WebUiRegistFile.ステータスを検証.画像のソースを検証("img_ja/ic_stat_ready.png");
	    	Thread.sleep(5000);

	    	//ファイルの削除
	    	WebUiRegistFile.すべて選択を押下.クリック();
	    	Thread.sleep(5000);

			//選択したファイルを削除
	    	WebUiRegistFile.選択したファイルを削除.クリック();
	    	Thread.sleep(5000);

			//削除ボックス遷移確認：選択したファイルを削除します
	    	WebUiRegistFile.削除するを押下.クリック();
	    	Thread.sleep(5000);

	    	//削除完了確認：HDDの登録ファイル容量が0であること
	    	WebUiRegistFile.登録容量.表示文言を検証("0MB");
	    	Thread.sleep(5000);
    	}

       	/* 画像ファイルを登録する */
     	for(String photo : phototype){
	    	//画像を登録するを押す
     		WebUiRegistFile.画像を登録する.クリック();
	    	Thread.sleep(5000);	//wait

    		//アップロードファイルを指定する
	    	WebUiRegistFile.ファイルを指定.アップロードするファイルを選択する(photo);
	    	Thread.sleep(5000);

	    	//---条件文【[このファイルを登録する]ボタンがグレイアウトしている間はアップロードファイル入力をやり直す】---
	    	boolean puploadactive = $("#ibtnUpload").exists();
	    	boolean puploadnonactive = $("#Img5").exists();

	    	if(puploadactive == true) {
		    	//このファイルを登録する
		    	WebUiRegistFile.このファイルを登録.クリック();
		    	Thread.sleep(5000);
	    	}
	    	else if(puploadnonactive == true){
	    		System.out.print("【このファイルを登録する】ボタンが見つかりません：ファイルが正しく入力されませんでした");
		    	while(puploadnonactive == true ) {
		    		//アップロードファイルを指定する
			    	WebUiRegistFile.ファイルを指定.アップロードするファイルを選択する(photo);
			    	Thread.sleep(5000);
		    	}
		    	//このファイルを登録する
		    	WebUiRegistFile.このファイルを登録.クリック();
		    	Thread.sleep(5000);
	    	}
	    	else {
	    		System.out.print("【このファイルを登録する】ボタンが見つかりません：不明なエラーが発生しました");
	    		System.out.print("ファイル登録ループの最初に戻ります");

	    		// ループ回数カウント
	           	rescount=count+1;

	           //デバッグログ用現在時間の取得
	     	   LocalDateTime day2 = LocalDateTime.now();
	     	   System.out.println(day2+":\t"+"単体：ループ回数："+rescount+"回目：不明なエラーが発生しました");


	    		break resetforloop;

	    	}

	    	//フラグの初期化
	    	puploadactive = false;

	    	//マイボックスに戻る
	    	WebUiRegistFile.マイボックスに戻る.クリック();
	    	Thread.sleep(5000);

	    	//マイボックス遷移確認：ユーザ番号確認
	    	WebUiRegistFile.ユーザー番号.表示文言を検証("ユーザー番号");

	    	//ステータス完了アイコンが表示されていることを確認
	    	WebUiRegistFile.ステータスを検証.画像のソースを検証("img_ja/ic_stat_ready.png");
	    	Thread.sleep(5000);

	    	//ファイルの削除
	    	WebUiRegistFile.すべて選択を押下.クリック();
	    	Thread.sleep(5000);

			//選択したファイルを削除
	    	WebUiRegistFile.選択したファイルを削除.クリック();
	    	Thread.sleep(5000);

			//削除ボックス遷移確認：選択したファイルを削除します
	    	WebUiRegistFile.削除するを押下.クリック();
	    	Thread.sleep(5000);

	    	//削除完了確認：HDDの登録ファイル容量が0であること
	    	WebUiRegistFile.登録容量.表示文言を検証("0MB");
	    	Thread.sleep(5000);
    	}


     	//ログアウト
    	//WebUiRegistFile.ログアウト.クリック();

    	//ログアウト確認
     	//WebUiRegistFile.ログアウト確認.画像のソースを検証("img_ja/btn_login.png");

	    //CSVにOKをつける
       	//ReportCsv.chekedOK(CsvRecords_.IDX_X_X_WebUIファイル登録確認);

       	// ループ回数カウント
       	rescount=count+1;

       //デバッグログ用現在時間の取得
 	   LocalDateTime day2 = LocalDateTime.now();
 	   System.out.println(day2+":\t\t"+"ループ回数："+rescount+"回成功しました");

    }
    /* ------ LOGEND ------ */
    LocalDateTime day3 = LocalDateTime.now();
  	System.out.println(day3+":\t"+"正常終了");

    Thread.sleep(10000);

    close();
    }

}
