package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SalesListReader {

	String folderPath;

	SalesListReader(String folderPath){
		this.folderPath = folderPath;
	}

	//売上ファイルを読み込み、ArrayList<Sales>を返す
	public ArrayList<Sales> getSalesList(){

		ArrayList<Sales> salesList = new ArrayList<Sales>();

		try{
			//ディレクトリ内のファイル一覧を取得
			File dir = new File(folderPath);
			String[] fileNames = dir.list();

			//ファイル名が売上ファイルの形式に一致するものを検索し、
			//内容をsalesListに登録
			//▲連番判定未実装
			for(String fileName: fileNames){
				if(fileName.matches("0000000\\d.rcd")){
					if(true){
						String filePath = folderPath + "\\" + fileName;
						FileReader fr = new FileReader(filePath);
						BufferedReader br = new BufferedReader(fr);
						String strLine;
						//ファイル末尾まで、最大三回一行ずつ読み込む
						//4行以上あればエラー出力
						int cnt = 0;
						Sales sales = new Sales();
						while((strLine = br.readLine()) != null) {
							if (cnt >=3){
								System.out.println("<" + fileName + ">のフォーマットが不正です");
								//エラー処理未実装
							}

							switch (cnt){
							case 0:
								sales.setBranchCode(strLine);
								break;
							case 1:
								sales.setProductCode(strLine);
								break;
							case 2:
								sales.setAmount(Integer.parseInt(strLine));
								break;
							}
							cnt++;
						}
						salesList.add(sales);
						br.close();
					}
				}
			}


		}catch(FileNotFoundException e){
			  System.out.println(e);
		}catch(IOException e){
			  System.out.println(e);
		}

		return salesList;

	}
}
