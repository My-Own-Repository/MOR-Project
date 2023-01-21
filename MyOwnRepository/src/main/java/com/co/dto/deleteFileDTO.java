package com.co.dto;

public class deleteFileDTO {
	public String[] existing_Delete_fileNum;
	
	public deleteFileDTO() {}
	public deleteFileDTO(String[] existing_Delete_fileNum) {
		this.existing_Delete_fileNum = existing_Delete_fileNum;
	}
    public String[] getexisting_Delete_fileNum() {
        return existing_Delete_fileNum;
    }
 
    public void setexisting_Delete_fileNum(String[] existing_Delete_fileNum) {
        this.existing_Delete_fileNum = existing_Delete_fileNum;
    }
}
