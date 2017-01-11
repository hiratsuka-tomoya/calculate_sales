package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Outputter {

	// 集計ファイル出力
	public Boolean outputCalculateFile(Map<String, ? extends DefinitionData> map, String folderPath,
			String fileName) {

		ArrayList<DefinitionData> sortList = new ArrayList<DefinitionData>(map.values()); // ソート用リスト
		File fileOutput = new File(folderPath, fileName);
		BufferedWriter bw = null;

		// 売上で降順ソート
		Collections.sort(sortList, new DefinitionDataComparator());

		// 出力ファイルが既に存在するなら削除
		if (fileOutput.exists()) {
			if (fileOutput.canWrite()) {
				fileOutput.delete();
			} else {
				// 既存かつロックされていればエラー表示
				System.out.println(Constants.ERROR_MASSAGE_OTHER);
				return false;
			}
		}

		// 出力ファイル作成
		try {
			fileOutput.createNewFile();
		} catch (IOException e) {
			System.out.println(Constants.ERROR_MASSAGE_OTHER);
			return false;
		}

		try {
			bw = new BufferedWriter(new FileWriter(fileOutput));
			for (DefinitionData dd : sortList) {
				// リストの内容を一行ずつファイルに書き込む
				bw.write(dd.getCode() + "," + dd.getName() + "," + dd.getAmount());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println(Constants.ERROR_MASSAGE_OTHER);
			return false;
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				System.out.println(Constants.ERROR_MASSAGE_OTHER);
				return false;
			}
		}
		return true;
	}
}
