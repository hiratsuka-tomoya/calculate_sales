package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.util.Comparator;

public class DefinitionDataComparator implements Comparator<DefinitionData> {
	// 降順ソート用にオーバーライドしたComparetor

	public int compare(DefinitionData d1, DefinitionData d2) {
		return d2.getAmount().compareTo(d1.getAmount());
	}
}
