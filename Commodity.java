package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Commodity extends DefinitionData {

	boolean setCode(String code) {
		if (code.matches("\\w{8}")) {
			super.setCode(code);
			return true;
		} else {
			System.out.println("商品定義ファイルのフォーマットが不正です");
			return false;
		}
	}
}
