package jp.main;

import java.awt.TrayIcon;
import java.io.File;
import java.util.TimerTask;

public class Run extends TimerTask{
    private CsvLoad csvload    = CsvLoad.getInstance();
    private int    CsvloadSize = csvload.dir.size();
    private long[] size        = new long[CsvloadSize];
    private long[] firstSize   = new long[CsvloadSize];

    // コンストラクタ
    Run() {
        for (int i = 0 ; i < CsvloadSize ; i++) {
            firstSize[i] = new File(csvload.dir.get(i)).length();
        }
    }

    public void run() {
        //System.out.println("タスク実行：" + new Date());
        // CSVから読み取ったファイル数だけループ
        for (int i = 0 ; i < CsvloadSize ; i++) {
            size[i] = new File(csvload.dir.get(i)).length();
            if (firstSize[i] != size[i]) {
                MainUpdateCheker.icon.displayMessage("メッセージ", "ファイルが更新されました", TrayIcon.MessageType.INFO);
                firstSize[i] = size[i];
            }
        }
    }
}
