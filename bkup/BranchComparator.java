package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.util.Comparator;

public class BranchComparator implements Comparator<Branch> {
//降順ソート用にオーバーライドしたComparetor

	public int compare(Branch b1, Branch b2){
		return b1.getAmount() > b2.getAmount() ? -1 : 1;
	}

}
