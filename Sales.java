package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class Sales{

	private String fileName;
	private String branchCode;
	private String commodityCode;
	private Long amount;

	Sales (String fileName) {
		this.setFileName(fileName);
		amount = new Long(0);
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
