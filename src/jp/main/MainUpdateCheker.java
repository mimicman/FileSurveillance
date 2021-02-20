package jp.main;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class MainUpdateCheker {
    static TrayIcon  icon;  // アイコン
    static TimerTask task  = new Run();   // 時間管理
    static Timer     timer = new Timer(); // タイマー
    private static final int timerInterval = 10000; // タイマー時間間隔（ミリ秒）

    public static void main(String args[]) throws InterruptedException{
        try{
            //メニューアイテムの作成
            MenuItem watchingItem = new MenuItem("監視対象一覧");
            MenuItem stopItem      = new MenuItem("一時停止");
            MenuItem restartItem  = new MenuItem("再開");
            MenuItem exitItem      = new MenuItem("終了");
            //
            restartItem.setEnabled(false);

            // アイコンの作成
            // デスクトップのシステム・トレイを作成
            SystemTray tray = SystemTray.getSystemTray();
            icon = new TrayIcon(ImageIO.read(new File("tray_icon.png")));
            // システム・トレイにアイコンをセット
            tray.add(icon);

            // 監視対象一覧
            watchingItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UpdateChekerFrame.watching();
                }
            });

            // 一時停止メニュー
            stopItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    icon.displayMessage("お知らせ","一時停止しました",MessageType.INFO);
                    // タスクの中止
                    task.cancel();
                    task = null;
                    // 一時停止メニュー項目を選択可能にする
                    stopItem.setEnabled(false);
                    // 再開メニュー項目を選択可能にする
                    restartItem.setEnabled(true);
                }
            });

            // 再開
            restartItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    icon.displayMessage("お知らせ","再開しました",MessageType.INFO);
                    if (task == null) {
                        task = new Run();
                    }
                    timer.schedule(task, 0, timerInterval);
                    // 一時停止メニュー項目を選択可能にする
                    stopItem.setEnabled(true);
                    // 再開メニュー項目を選択不可能にする
                    restartItem.setEnabled(false);
                }
            });

            // 終了メニュー
            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            // ポップアップメニュー
            PopupMenu menu = new PopupMenu();
            // メニューにメニューアイテムを追加
            menu.add(stopItem);     // 一時停止メニュー
            menu.add(restartItem);  // 再開メニュー
            menu.add(exitItem);     // 終了メニュー
            menu.add(watchingItem); // 開始対象一覧メニュー
            // アイコンにメニューをセット
            icon.setPopupMenu(menu);

        }catch (IOException ex) {
             ex.printStackTrace();
        }catch (AWTException ex) {
             ex.printStackTrace();
        }

        // 10秒間隔でタスクを実行
        timer.schedule(task, 0, timerInterval);
    }
}
