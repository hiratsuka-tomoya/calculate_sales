package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.util.Comparator;

public class DefinitionDataComparator implements Comparator<DefinitionData> {
	// 降順ソート用にオーバーライドしたComparetor

	public int compare(DefinitionData d1, DefinitionData d2) {
		if (d1.getAmount() > d2.getAmount()) {
			return -1;
		} else if (d1.getAmount() == d2.getAmount()) {
			return 0;
		} else {
			return 1;
		}
	}
}
