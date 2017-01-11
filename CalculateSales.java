package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.File;
import java.util.ArrayList;
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
				System.out.println(sales.getFileName() + "の支店コードが不正です");
				return;
			}
			if (commodityMap.containsKey(sales.getCommodityCode())) {
				if (commodityMap.get(sales.getCommodityCode()).addAmount(sales.getAmount()) == false) {
					// 売上の合計額が10桁を超えていれば終了
					return;
				}
			} else {
				// 該当の商品が存在しなければ終了
				System.out.println(sales.getFileName() + "の商品コードが不正です");
				return;
			}
		}

		//出力クラス宣言
		Outputter outputter = new Outputter();

		// 支店別集計ファイル出力
		if (outputter.outputCalculateFile(branchMap, folderPath, Constants.FILE_NAME_BRANCH_OUTPUT) == false) {
			return;
		}

		// 商品別集計ファイル出力
		if (outputter.outputCalculateFile(commodityMap, folderPath, Constants.FILE_NAME_COMMODITY_OUTPUT) == false) {
			return;
		}
	}
}
