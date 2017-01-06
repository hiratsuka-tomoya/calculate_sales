package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BranchListReader {

	String filePath;

	BranchListReader(String folderPath, String fileName){
		//ファイルパスを取得
		filePath = folderPath + "\\" + fileName;
	}

	//支店定義ファイルを読み込み、HashMap<String,Branch>を返す
	public HashMap<String,Branch> getBranchList(){

		HashMap<String,Branch> branchList = new HashMap<String,Branch>();

		try{
			File file = new File(filePath);
			//ファイルの存在を確認
			if(file.exists() == false){
				System.out.println("支店定義ファイルが存在しません");
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String strLine;
			//ファイル末尾まで一行ずつ読み込む
			while((strLine = br.readLine()) != null) {
				//一行をカンマで分割して、コード,名前をbranchListに登録
				String[] strSplit = strLine.split(",",0);
				Branch branch = new Branch();
				branch.setCode(strSplit[0]);
				branch.setName(strSplit[1]);
				branchList.put(branch.getCode(),branch);
			}
			br.close();
		}catch(FileNotFoundException e){
			  System.out.println(e);
		}catch(IOException e){
			  System.out.println(e);
		}

		return branchList;

	}
}
