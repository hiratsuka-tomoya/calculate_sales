package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public abstract class DefinitionFileReader {
//定義ファイルの種類に応じて子クラスでファイル名と生成するオブジェクトを指定して利用

	private String fileName;
	private String folderPath;
	private String fileNameJap;		//ファイルの日本語名「◯◯定義ファイル」

	DefinitionFileReader(String folderPath, String fileName, String fileNameJap){
		//ファイル名、ファイルパスを取得
		this.fileName = fileName;
		this.fileNameJap = fileNameJap;
		this.folderPath = folderPath;
	}

	//DifinitionDataか子クラスのオブジェクトを新しく作成して返す
	public abstract DefinitionData returnNewDataObject();

	//定義ファイルを読み込み、内容を登録した定義データオブジェクトをハッシュマップに追加し、ハッシュマップを返す
	//エラーが発生したらnullを返す
	public HashMap<String,DefinitionData> getDifinitionDataMap(){

		HashMap<String,DefinitionData> difDataList = new HashMap<String,DefinitionData>();
		File file = new File(folderPath, fileName);
		BufferedReader br = null;

		//ファイルの存在を確認
		if(file.exists() == false){
			System.out.println(fileNameJap + "が存在しません");
			return null;
		}

		try{
			br = new BufferedReader(new FileReader(file));
			String strLine;

			//ファイル末尾まで一行ずつ読み込むループ
			while((strLine = br.readLine()) != null) {

				//一行をカンマで分割
				String[] strSplit = strLine.split(",",0);

				//要素数をチェック
				if (strSplit.length != Constants.COLUMN_NUM_DIFINITION_FILE) {
					System.out.println(fileNameJap + "のフォーマットが不正です");
					return null;
				}

				//定義データオブジェクトを作成し、定義ファイル一行分の内容を登録
				DefinitionData difData = returnNewDataObject();
				if (difData.setCode(strSplit[0]) == false) {
					return null;
				}
				difData.setName(strSplit[1]);

				//作成した定義データオブジェクトをハッシュマップに追加
				difDataList.put(difData.getCode(),difData);

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

		return difDataList;

	}
}
