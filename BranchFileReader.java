package jp.co.iccom.hiratsuka_tomoya.calculate_sales;

public class BranchFileReader extends DefinitionFileReader {

	BranchFileReader(String folderPath) {
		super(folderPath, Constants.FILE_NAME_BRANCH, Constants.FILE_NAME_BRANCH_JAP);
	}

	public DefinitionData returnNewDataObject() {
		return new Branch();
	}
}
