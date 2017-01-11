package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class DefinitionData {
//定義ファイルデータ

	private String code;
	private String name;
	private long amount;

	boolean setCode(String code) {
		this.code = code;
		return true;
	}

	boolean setName(String name){
		this.name = name;
		return true;
	}

	boolean addAmount(long amount){
		this.amount += amount;
		if(this.amount > Constants.MAX_AMOUNT) {
			System.out.println("合計金額が10桁を超えました");
			return false;
		}
		return true;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getAmount() {
		return amount;
	}

	String getCode(){
		return code;
	}

	String getName(){
		return name;
	}
}
