package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Sales{

	String fileName;
	String branchCode;
	String commodityCode;
	long amount;

	Sales (String fileName) {
		this.fileName = fileName;
	}

	void setAmount(long amount){
		this.amount = amount;
	}

	void setBranchCode(String code) {
		this.branchCode = code;
	}

	void setCommodityCode(String code) {
		this.commodityCode = code;
	}

	String getBranchCode(){
		return branchCode;
	}

	String getCommodityCode(){
		return commodityCode;
	}

	long getAmount() {
		return amount;
	}

}
