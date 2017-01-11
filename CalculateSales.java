package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateSales {

	public static void main(String[] args) {

		// コマンドライン引数の数をチェック
		if (args.length != 1) {
			System.out.println(Constants.ERROR_MASSAGE_OTHER);
			return;
		}

		// コマンドライン引数からフォルダパスを取得
		String folderPath = args[0];

		// コマンドライン引数のパスのディレクトリが存在するか確認
		File dir = new File(folderPath);
		if (dir.exists() == false) {
			System.out.println(Constants.ERROR_MASSAGE_OTHER);
			return;
		}

		// マップ・コレクション生成
		Map<String, ? extends DefinitionData> branchMap = new HashMap<String, Branch>();
		Map<String, ? extends DefinitionData> commodityMap = new HashMap<String, Commodity>();
		List<Sales> salesList = new ArrayList<Sales>();

		// リーダーインスタンス生成
		DefinitionFileReader branchFileReader = new BranchFileReader(folderPath);
		DefinitionFileReader commodityFileReader = new CommodityFileReader(folderPath);
		SalesFileReader salesFileReader = new SalesFileReader(folderPath);

		// ファイルを読み込む
		if ((branchMap = branchFileReader.getDifinitionDataMap()) == null) {
			return;
		}
		if ((commodityMap = commodityFileReader.getDifinitionDataMap()) == null) {
			return;
		}
		if ((salesList = salesFileReader.getSalesList()) == null) {
			return;
		}

		// 集計
		for (Sales sales : salesList) {
			if (branchMap.containsKey(sales.getBranchCode())) {
				if (branchMap.get(sales.getBranchCode()).addAmount(sales.getAmount()) == false) {
					// 売上の合計額が10桁を超えていれば終了
					return;
				}
			} else {
				// 該当の支店が存在しなければ終了
				System.out.println(sales.fileName + "の支店コードが不正です");
				return;
			}
			if (commodityMap.containsKey(sales.getCommodityCode())) {
				if (commodityMap.get(sales.getCommodityCode()).addAmount(sales.getAmount()) == false) {
					// 売上の合計額が10桁を超えていれば終了
					return;
				}
			} else {
				// 該当の商品が存在しなければ終了
				System.out.println(sales.fileName + "の商品コードが不正です");
				return;
			}
		}

		// 支店別集計ファイル出力
		if (outputCalculateFile(branchMap, folderPath, Constants.FILE_NAME_BRANCH_OUTPUT) == false) {
			return;
		}

		// 商品別集計ファイル出力
		if (outputCalculateFile(commodityMap, folderPath, Constants.FILE_NAME_COMMODITY_OUTPUT) == false) {
			return;
		}
	}

	// 集計ファイル出力
	// staticにしないとstatic mainから呼び出せないが、これをstaticにするのは不適切？
	public static Boolean outputCalculateFile(Map<String, ? extends DefinitionData> map, String folderPath,
			String fileName) {

		ArrayList<DefinitionData> sortList = new ArrayList<DefinitionData>(map.values()); // ソート用リスト
		String filePathOutput = getFilePath(folderPath, fileName);
		File fileOutput = new File(filePathOutput);
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

	// ファイルパスを返す
	public static String getFilePath(String folderPath, String fileName) {
		// フォルダパスの末尾がパスセパレーターかどうかで分岐
		if (folderPath.substring(folderPath.length()) == File.separator) {
			return folderPath + fileName;
		} else {
			return folderPath + File.separator + fileName;
		}
	}
}
