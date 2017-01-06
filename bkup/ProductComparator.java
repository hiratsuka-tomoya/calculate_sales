package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
//降順ソート用にオーバーライドしたComparetor

	public int compare(Product p1, Product p2){
		return p1.getAmount() > p2.getAmount() ? -1 : 1;
	}

}
