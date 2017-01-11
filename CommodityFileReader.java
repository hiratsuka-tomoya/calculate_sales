package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class CommodityFileReader extends DefinitionFileReader {

	CommodityFileReader(String folderPath) {
		super(folderPath, Constants.FILE_NAME_COMMODITY, Constants.FILE_NAME_COMMODITY_JAP);
	}

	public DefinitionData returnNewDataObject() {
		return new Commodity();
	}
}
