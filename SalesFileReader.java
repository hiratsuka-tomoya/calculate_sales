package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
		FileReader fr = null;
		BufferedReader br = null;

		try{
			//ディレクトリ内のファイル一覧を取得
			File dir = new File(folderPath);
			String[] fileNames = dir.list();

			//ファイル名が売上ファイルの形式に一致するものを検索し、データをsalesListに追加
			for(String fileName: fileNames){
				//3ファイル見つけていれば検索を終了
				if (fileCnt >= 3) {
					break;
				}else if (fileName.matches("\\d{8}.rcd")){
					fileCnt++;

					String filePath = CalculateSales.getFilePath(folderPath, fileName);
					File file = new File(filePath);

					//対象がファイルかどうかチェック
					if (file.isFile() == false) {
						System.out.println("売上ファイル名が連番になっていません");
						return null;
					}

					fr = new FileReader(filePath);
					br = new BufferedReader(fr);
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

					//行数、金額のフォーマットをチェック
					if ((strLineList.size() != Constants.ROW_NUM_SALES_FILE) ||
							(strLineList.get(2).matches("\\d*") == false)){
						System.out.println(fileName + "のフォーマットが不正です");
						return null;
					}

					//売上オブジェクトにデータを登録
					sales.setBranchCode(strLineList.get(0));
					sales.setProductCode(strLineList.get(1));
					sales.setAmount(Long.parseLong(strLineList.get(2)));

					//売上オブジェクトをリストに追加
					salesList.add(sales);
				}
			}

			if (fileCnt < Constants.FILE_NUM_SALES) {
				System.out.println(Constants.ERROR_MASSAGE_OTHER);
				return null;
			}

		}catch(FileNotFoundException e){
			  System.out.println(Constants.ERROR_MASSAGE_OTHER);
			  return null;
		}catch(IOException e){
			  System.out.println(Constants.ERROR_MASSAGE_OTHER);
			  return null;
		}finally {
			try {
				if (fr != null) {
					fr.close();
				}
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
