package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Calculate_sales {

	public static void main(String[] args){

		//コマンドライン引数からフォルダパスを取得
		String folderPath = args[0];
		String folderPath_output = args[1];

		//マップ・コレクション宣言
		HashMap<String,Branch> branchList = new HashMap<String,Branch>();
		HashMap<String,Product> productList = new HashMap<String,Product>();
		ArrayList<Sales> salesList = new ArrayList<Sales>();

		//クラス宣言
		BranchListReader branchListReader = new BranchListReader(folderPath, Constants.FILE_NAME_BRANCH);
		ProductListReader productListReader = new ProductListReader(folderPath, Constants.FILE_NAME_PRODUCT);
		SalesListReader salesListReader = new SalesListReader(folderPath);

		//ファイルを読み込む
		branchList = branchListReader.getBranchList();
		productList = productListReader.getProductList();
		salesList = salesListReader.getSalesList();

		//集計
		for(Sales sales: salesList) {
			//▲合計額が10桁を超えた場合のエラー処理未実装
			//▲支店または商品に該当がなかった場合のエラー未実装
			branchList.get(sales.getBranchCode()).addAmount(sales.getAmount());
			productList.get(sales.getProductCode()).addAmount(sales.getAmount());
		}

		//ソート用リスト　要素にインスタンスを持つHashMapでの簡単なソートのやり方がわからなかった！！！
		ArrayList<Branch> branchList_sort = new ArrayList<Branch>(branchList.values());
		ArrayList<Product> productList_sort = new ArrayList<Product>(productList.values());

		Collections.sort(branchList_sort, new BranchComparator());
		Collections.sort(productList_sort, new ProductComparator());

		//ファイル出力
		try {
			String filePathBranch_output = folderPath_output + "\\" + Constants.FILE_NAME_BRANCH_OUTPUT;
			String filePathProduct_output = folderPath_output + "\\" + Constants.FILE_NAME_PRODUCT_OUTPUT;
			File newFileBranch_output = new File(filePathBranch_output);
			File newFileProduct_output = new File(filePathProduct_output);
			//既存なら削除
			if(newFileBranch_output.exists()) {
				newFileBranch_output.delete();
			}
			if(newFileProduct_output.exists()) {
				newFileProduct_output.delete();
			}
			//ファイル作成
			newFileBranch_output.createNewFile();
			newFileProduct_output.createNewFile();

			//支店別集計ファイルに書き込み
			FileWriter fileWriter_branch = new FileWriter(newFileBranch_output);
			BufferedWriter bw_branch = new BufferedWriter(fileWriter_branch);
			for(Branch br : branchList_sort){
				bw_branch.write(br.getCode() + "," + br.getName() + "," + br.getAmount());
				bw_branch.newLine();
			}
			bw_branch.close();

			//商品別集計ファイルに書き込み
			FileWriter fileWriter_product = new FileWriter(newFileProduct_output);
			BufferedWriter bw_product = new BufferedWriter(fileWriter_product);
			for(Product pr : productList_sort){
				bw_product.write(pr.getCode() + "," + pr.getName() + "," + pr.getAmount());
				bw_product.newLine();
			}
			bw_product.close();

		}catch(IOException e){
		    System.out.println(e);
		}

	}
}
