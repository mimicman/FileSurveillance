package jp.main;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTable;

public class UpdateChekerFrame extends JFrame{

    private CsvLoad csvload = CsvLoad.getInstance();

    static void watching(){
        UpdateChekerFrame frame = new UpdateChekerFrame("監視対象管理");
        // 可視化する
        frame.setVisible(true);
    }

    // 監視対象ファイルをCSVから読み込み
    UpdateChekerFrame(String title){
        String[][] tabledata = new String[csvload.dir.size()+1][3];
        tabledata[0][0] = "監視対象";
        tabledata[0][1] = "タイプ";
        tabledata[0][2] = "状態";
        for(int i = 0; i < csvload.dir.size();i++ ) {
            // ファイル名取得
            String fileName = csvload.dir.get(i);
            tabledata[i][0] = fileName;

            // 拡張子取得
            String extension = fileName.substring(fileName.lastIndexOf("."));
            // タイプに拡張子を設定
            tabledata[i][1] = extension;

            tabledata[i][2] = "状態";
        }
        String[] columnNames = {"監視対象" , "タイプ", "状態"};

        // 表形式の形でデータJtableの作成
        JTable table = new JTable(tabledata,columnNames);
        // JTableの大きさを指定
        setBounds(600, 300, 400, 400);
        // ウィンドウを閉じるときの処理
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //
        Container contentPane = getContentPane();
        contentPane.add(table,BorderLayout.NORTH);
    }
}
