package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SalesFileReader {

	String folderPath;
	long firstFileNumber;	//1つ目の売上ファイルの番号
	int fileCnt;			//売上ファイル数

	SalesFileReader(String folderPath){
		this.folderPath = folderPath;
		fileCnt = 0;
	}

	//売上ファイルを読み込み、売上データを追加したリストを返す
	public ArrayList<Sales> getSalesList(){

		ArrayList<Sales> salesList = new ArrayList<Sales>();
		BufferedReader br = null;

		try{
			//ディレクトリ内のファイル一覧を取得
			File dir = new File(folderPath);
			String[] fileNames = dir.list();

			//ファイル名が売上ファイルの形式に一致するものを検索し、データをsalesListに追加
			for(String fileName: fileNames){

				File file = new File(folderPath, fileName);

				if (fileName.matches("\\d{8}.rcd") && file.isFile() == true){
					fileCnt++;

					br = new BufferedReader(new FileReader(file));
					String strLine;
					ArrayList<String> strLineList = new ArrayList<String>();
					Sales sales = new Sales(fileName);

					if(fileCnt == 1){
						//1ファイル目なら番号を記憶
						firstFileNumber = Integer.parseInt(fileName.substring(1,8));
					}else {
						//2ファイル目以降ならチェック
						if(Integer.parseInt(fileName.substring(1,8)) != firstFileNumber + (fileCnt - 1)){
							//ファイル名が連番か
							System.out.println("売上ファイル名が連番になっていません");
							return null;
						}
					}

					//ファイル末尾まで一行ずつリストに追加
					while((strLine = br.readLine()) != null) {
						strLineList.add(strLine);
					}

					//行数をチェック
					if (strLineList.size() != Constants.ROW_NUM_SALES_FILE){
						System.out.println(fileName + "のフォーマットが不正です");
						return null;
					}

					//金額のフォーマットをチェック
					if (strLineList.get(2).matches("\\d*") == false) {
						System.out.println(Constants.ERROR_MASSAGE_OTHER);
						return null;
					}

					//売上オブジェクトにデータを登録
					sales.setBranchCode(strLineList.get(0));
					sales.setCommodityCode(strLineList.get(1));
					sales.setAmount(Long.parseLong(strLineList.get(2)));

					//売上オブジェクトをリストに追加
					salesList.add(sales);
				}
			}

		}catch(IOException e){
			  System.out.println(Constants.ERROR_MASSAGE_OTHER);
			  return null;
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println(Constants.ERROR_MASSAGE_OTHER);
			}
		}
		return salesList;
	}
}
