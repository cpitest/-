package sbc.uitesttool.selenide.report;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;

import com.codeborne.selenide.WebDriverRunner;

public class ScreenShot {
    /** スクリーンショットの出力先のフォルダ名 */
    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String SCREENSHOT_OUTPUT_PATH = "." + SEPARATOR + "screenshot" + SEPARATOR;

    /**
     * 対象画面のキャプチャを出力する
     *
     * @param driver
     * @param outPutFileName
     *            出力するキャプチャのファイル名
     * @throws WebDriverException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void takesScreenshot(String outPutFileName)
	    throws WebDriverException, IOException, InterruptedException {

	takeScreenshot(outPutFileName, SCREENSHOT_OUTPUT_PATH);
    }

    /**
     * 対象画面のキャプチャを出力する
     *
     * @param outPutFileName
     *            出力するキャプチャのファイル名
     * @param outputPath
     *            キャプチャを出力する先のパス
     * @throws WebDriverException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void takeScreenshot(String outPutFileName, String outputPath)
	    throws WebDriverException, IOException, InterruptedException {

	// スクリーンショットを取る前に少し待つ（画面がロードしきれていない可能性があるため）
	Thread.sleep(1000);

	// 対象ディレクトリが存在しないならば作成する
	Path pathByDirectory = Paths.get(outputPath);
	if (!Files.exists(pathByDirectory)) {
	    Files.createDirectories(pathByDirectory);
	}

	// 同名のファイルを削除
	Path pathByFile = pathByDirectory.resolve(outPutFileName);
	if (Files.exists(pathByFile)) {
	    Files.delete(pathByFile);
	}

	WebDriver driver = WebDriverRunner.getWebDriver();

	TakesScreenshot ts = (TakesScreenshot) new Augmenter().augment(driver);

	// JS実行用のExecuter
	JavascriptExecutor jexec = (JavascriptExecutor) driver;

	// 画面サイズで必要なものを取得
	int innerH = Integer.parseInt(String.valueOf(jexec.executeScript("return window.innerHeight")));
	int innerW = Integer.parseInt(String.valueOf(jexec.executeScript("return window.innerWidth")));
	int scrollH = Integer
		.parseInt(String.valueOf(jexec.executeScript("return document.documentElement.scrollHeight")));

	// イメージを扱うための準備
	BufferedImage img = new BufferedImage(innerW, scrollH, BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();

	// スクロールを行うかの判定
	if (innerH > scrollH) {
	    BufferedImage imageParts = ImageIO.read(ts.getScreenshotAs(OutputType.FILE));
	    g.drawImage(imageParts, 0, 0, null);
	} else {
	    int scrollableH = scrollH;
	    int i = 0;

	    // スクロールしながらなんどもイメージを結合していく
	    while (scrollableH > innerH) {
		BufferedImage imageParts = ImageIO.read(ts.getScreenshotAs(OutputType.FILE));
		g.drawImage(imageParts, 0, innerH * i, null);
		scrollableH = scrollableH - innerH;
		i++;
		jexec.executeScript("window.scrollTo(0," + innerH * i + ")");
		Thread.sleep(100);
	    }

	    // 一番下まで行ったときは、下から埋めるように貼り付け
	    BufferedImage imageParts = ImageIO.read(ts.getScreenshotAs(OutputType.FILE));
	    g.drawImage(imageParts, 0, scrollH - innerH, null);
	}

	Path path = Paths.get(SCREENSHOT_OUTPUT_PATH);
	if (!Files.exists(path)) {
	    Files.createDirectories(path);
	}
	ImageIO.write(img, "png", pathByFile.toFile());
    }
}
