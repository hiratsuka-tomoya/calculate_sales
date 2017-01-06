package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ProductListReader {

	String filePath;

	ProductListReader(String folderPath, String fileName){
		//ファイルパスを取得
		filePath = folderPath + "\\" + fileName;
	}

	//商品定義ファイルを読み込み、HashMap<String,Product>を返す
	public HashMap<String,Product> getProductList(){

		HashMap<String,Product> productList = new HashMap<String,Product>();

		try{
			File file = new File(filePath);
			//ファイルの存在を確認
			if(file.exists() == false){
				System.out.println("商品定義ファイルが存在しません");
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String strLine;
			//ファイル末尾まで一行ずつ読み込む
			while((strLine = br.readLine()) != null) {
				//一行をカンマで分割して、コード,名前をproductListに登録
				String[] strSplit = strLine.split(",",0);

				//ファイルフォーマットのチェック

				if(strSplit.length > 1){
					//要素数が3つ以上
				}else if(strSplit[0].matches("\\d\\d\\d") == false){
					//しょうひんこーｄ
				}
				Product product = new Product();
				product.setCode(strSplit[0]);
				product.setName(strSplit[1]);
				productList.put(product.getCode(),product);
			}
			br.close();
		}catch(FileNotFoundException e){
			  System.out.println(e);
		}catch(IOException e){
			  System.out.println(e);
		}

		return productList;

	}
}
