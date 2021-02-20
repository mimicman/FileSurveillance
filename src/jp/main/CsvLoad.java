package jp.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CsvLoad {
    private static CsvLoad       csvLoad = new CsvLoad();
    ArrayList<String>      dir       = new ArrayList<String>();  // ディレクトリ
    ArrayList<String[]>    csvData  = new ArrayList<String[]>(); // CSVデータ
    private static final String filename = "test.csv";                // ファイル名

    private CsvLoad() {
        File file = new File(filename);
        FileInputStream   fileInputStream   = null;  // ファイルストリーム
        InputStreamReader inputStreamReader = null; // インプットストリームリーダー
        BufferedReader    bufferedReader    = null;  // バッファリーダー
        try {
            fileInputStream   = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader    = new BufferedReader(inputStreamReader);
            String line;
            while ( ( line = bufferedReader.readLine()) != null ) {
                String[] cols = line.split(",");
                csvData.add(cols);
            }
            // 読み込みデータの表示
            for(String[]row : csvData) {
                // 1行のCSV配列の長さまで繰り返し
                for(int n = 0; n < row.length; n++) {
                    dir.add(row[n]);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static CsvLoad getInstance() {
        return csvLoad;
    }
}
