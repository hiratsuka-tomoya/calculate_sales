package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class DefinitionData {
//定義ファイルデータ

	private String code;
	private String name;
	private Long amount;

	DefinitionData() {
		amount = new Long(0);
	}

	@Override
	public String toString() {
	    return String.format("%s,%s,%s", this.code, this.name, this.amount);
	}

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

	public Long getAmount() {
		return amount;
	}

	String getCode(){
		return code;
	}

	String getName(){
		return name;
	}
}
