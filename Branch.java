package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Branch extends DefinitionData {

	boolean setCode(String code) {
		if (code.matches("\\d{3}")) {
			super.setCode(code);
			return true;
		} else {
			System.out.println("支店定義ファイルのフォーマットが不正です");
			return false;
		}
	}
}
