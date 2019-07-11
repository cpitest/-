package sbc.uitesttool.selenide.pageparts;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
// com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

public enum WebUiRegistFile{

	//トップページ
	ログイン名($("#txtId")), //
	パスワード($("#txtPw")), //
	ログイン($("#lnkLogin")), //
	ログイン名を保存する($("#chkSaveId")),//
	パスワードを保存する($("#chkSavePw")),//


	//ネットワークかんたん登録
	次回から表示しない($("#chkReferenceDisable")),//

	//マイボックス
	ユーザー番号($("#lblHeaderUserID")),//
	文書を登録する($("#Img6")), //
	画像を登録する($("#Img7")), //
	文書の登録($("#Img3")), //

	//ファイル登録
	ファイル選択($("#divSelectButton")), //
	ファイルを指定($("#FileUpload")), //
	ファイル登録ON($("#upload_on")), //
	ファイル登録OFF($("#upload_off")), //
	このファイルを登録($("#ibtnUpload")), //
	用紙選択($("#ddlIptPaperSize")), //
	登録押下($("#modal_Upload_img")), //
	マイボックスに戻る($("#ImgFinishJavaScript")), //
	ステータスを検証($(Selectors.byAttribute("src","img_ja/ic_stat_ready.png"))), //
	登録容量($("#lblUsableSpace")),


	//ファイル削除
	すべて選択を押下($("#ibtnSelectAllTop")), //
	選択したファイルを削除($("#Img12")), //
	削除するを押下($("#Img15")), //

	//ログアウト
	ログアウト($("#HeaderLogout")),
	ログアウト確認($(Selectors.byAttribute("src", "img_ja/btn_login.png"))),

	;

	private WebUiRegistFile(SelenideElement element) {
		this.element = element;
	}

	private SelenideElement element;

	public SelenideElement getElement() {
		return element;
	}

	/**
	 * 対象オブジェクトのある位置までスクロールする
	 * @return
	 */
	public SelenideElement スクロール() throws InterruptedException {
		SelenideElement result = element;
		for (int i = 0; i < 3; i++) { // 縦に長い画面だと一度ではスクロールしきってくれない可能性がある
			result = result.scrollTo();
			Thread.sleep(1000); // スクロール待ち
		}

		return result;
	}

	/**
	 * inputタグの値を検証する
	 * @param actual 比較対象する値
	 * @return
	 */
	public SelenideElement セットされた値を検証(String actual) {
		return element.shouldBe(Condition.value(actual));
	}

	/**
	 * チェックボックスにチェックが入っているかを検証する
	 * @param actual true=チェックが入っている, false=チェックが入っていない
	 * @return
	 */
	public void チェックボックスがチェックされているかを検証(boolean actual) {
		String actualValue = null;
		if (actual) {
			actualValue = "true";
		}

		assertThat("チェックボックス検証NG HTML=" + element.getAttribute("checked") + ", 比較対象=" + actualValue,
			element.getAttribute("checked"), is(actualValue));
	}

	/**
	 * 対象のエレメントが編集不可状態であるかを検証する
	 * @param actual true=編集不可, false=編集可能
	 * @return
	 */
	public void 編集不可状態であるかの検証(boolean actual) {
		String actualValue = null;
		if (actual) {
			actualValue = "true";
		}

		assertThat("編集不可状態であるかの検証NG HTML=" + element.getAttribute("disabled") + ", 比較対象=" + actualValue,
			element.getAttribute("disabled"), is(actualValue));
	}

	/**
	 * 対象のエレメントに指定した文字列が含まれているかを検証する
	 * @param actual 比較対象する値
	 * @return
	 */
	public SelenideElement 表示文言を検証(String actual) {
		return element.shouldHave(Condition.text(actual));
	}

	/**
	 * 対象のエレメントの画像ソースが指定したURLであるかを検証する
	 * @param actual 比較対象する値（画像のURL)
	 * @return
	 */
	public void 画像のソースを検証(String actual) {
		assertThat("画像のソース検証NG HTML=" + element.getAttribute("src") + ", 比較対象=" + actual, element.getAttribute("src"),
			containsString(actual));
	}

	/**
	 * 対象のエレメントの画像のAltが指定した文字列であるかを検証する
	 * @param actual 比較対象する値
	 * @return
	 */
    public void 画像のaltを検証(String actual) {
		assertThat("画像のAlt検証NG HTML=" + element.getAttribute("alt") + ", 比較対象=" + actual, element.getAttribute("alt"),
			is(actual));
	}

    /**
	 * 対象のエレメントの画像のSrcが指定した文字列であるかを検証する
	 * @param actual 比較対象する値
	 * @return
	 */
    public void 画像のsrcを検証(String actual) {
		assertThat("画像のSrc検証NG HTML=" + element.getAttribute("src") + ", 比較対象=" + actual, element.getAttribute("src"),
			is(actual));
	}




	/**
	 * クリックする
	 * @return
	 */
	public void クリック() {
			element.click();
	}


	/**
	 * ダブルクリックする
	 * @return
	 */
	public void ダブルクリック() {
		element.doubleClick();
	}

	/**
	 * 右クリックする
	 * @return
	 */
	public void 右クリック() {
		element.contextClick();
	}

	/**
	 * テキストを上書きで入力する
	 * @param value 入力する値
	 * @return
	 */
	public void テキストを上書き(String value) {
		element.setValue(value);
	}

	/**
	 * テキストを追記で入力する
	 * @param value 入力する値
	 * @return
	 */
	public void テキストに追記(String value) {
		element.append(value);
	}

	/**
	 * 対象エレメント上でEnterキーを押下する
	 * @return
	 */
	public void Enterキー押下() {
		element.pressEnter();
	}

	/**
	 * 対象エレメント上でESCキーを押下する
	 * @return
	 */
	public void ESCキー押下() {
		element.pressEscape();
	}

	/**
	 * 対象エレメント上でTABキーを押下する
	 * @return
	 */
	public void TABキー押下() {
		element.pressTab();
	}

	/**
	 * 対象エレメント上でTABキーとEnterキーを押下する
	 * @return
	 */
	public void TABEnterキー押下() {
		element.pressTab();
		element.pressEnter();
	}


	/**
	 * Selectボックスを指定した文字列の内容で選択する
	 * @param text 選択対象となる文字列
	 * @return
	 */
	public void Selectボックス選択(String text) {
		element.selectOption(text); // 文字列を指定して選択
	}

	/**
	 * ラジオボタンを指定した文字列の値で選択する
	 * @param text チェックを入れる対象の値
	 * @return
	 */
	public void ラジオボタン選択(String value) {
		element.selectRadio(value); // valueを指定して選択
	}

	/**
	 * チェックボックスに選択を入れる/外す
	 * @param value true=チェックを入れる, false=チェックを外す
	 * @return
	 */
	public void チェックボックス選択(boolean value) {
		element.setSelected(value);
	}

	/**
	 * input type=file に指定したファイルをセットする <br />
	 * ファイルは"src/test/resources/"配下に配置すること
	 *
	 * @param fileName
	 *            アップロード対象のファイル名
	 */
	public void アップロードするファイルを選択する(String fileName){
		String path = "src/test/resources/";
		element.uploadFile(new File(path + fileName));
	}

	/**
	 * 確認ダイアログのボタンを押す
	 *
	 * @param value
	 *            true=OKを押す、false=キャンセルを押す
	 */
	public static void 確認ダイアログのボタン押下(boolean value){
		if(value){
			confirm();
		} else{
			dismiss();
		}
	}

	/**
	 * 指定されたミリ秒分待機する
	 *
	 * @param sleepMilliSec
	 *            待機する時間（ミリ秒）
	 * @throws InterruptedException
	 */
	public static void 待機(int sleepMilliSec) throws InterruptedException{
		Thread.sleep(sleepMilliSec);
	}
}
