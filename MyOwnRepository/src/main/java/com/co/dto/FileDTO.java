package com.co.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {
	
	public int file_num;
	public int b_num;
	public String user_id;
	public String original_file_name;
    public String stored_file_name;
    public String stored_path;
    public String type;
    public long file_size;
    public String date;
    public int is_exist;			// 게시글이 지워지거나 클라이언트에서 파일이 지워지면 is_exist값이 0(false)이 되며 비활성화가 됨.
    								// 서버pc에는 파일이, db에는 파일의 정보(경로 등)을 그대로 남김.
    
    public MultipartFile uploadFile;

  	public FileDTO() {}
  	public FileDTO(int file_num, int b_num, String user_id, String original_file_name, String stored_file_name, String stored_path, String type, long file_size, String date, int is_exist, MultipartFile uploadFile) {
  		super();
  		this.file_num = file_num;
  		this.b_num = b_num;
  		this.user_id = user_id;
  		this.original_file_name = original_file_name;
  		this.stored_file_name = stored_file_name;
  		this.stored_path = stored_path;
  		this.type = type;
  		this.file_size = file_size;
  		this.is_exist = is_exist;
  		this.date = date;
  		this.uploadFile = uploadFile;
  	}
    
    public int getfile_num() {
        return file_num;
    }
 
    public void setfile_num(int file_num) {
        this.file_num = file_num;
    }
 
    public int getb_num() {
        return b_num;
    }
 
    public void setb_num(int b_num) {
        this.b_num = b_num;
    }
    public String getuser_id() {
        return user_id;
    }
 
    public void setuser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public String getoriginal_file_name() {
        return original_file_name;
    }
 
    public void setoriginal_file_name(String original_file_name) {
        this.original_file_name = original_file_name;
    }

    public String getstored_file_name() {
        return stored_file_name;
    }
 
    public void setstored_file_name(String stored_file_name) {
        this.stored_file_name = stored_file_name;
    }

    public String getstored_path() {
    	return stored_path;
    }
    
    public void setstored_path(String stored_path) {
    	this.stored_path = stored_path;
    }
    
    public String gettype() {
        return type;
    }
 
    public void settype(String type) {
        this.type = type;
    }
    
    public long getfile_size() {
        return file_size;
    }
   
    public void setfile_size(long file_size) {
        this.file_size = file_size;
    }
    
    public String getdate() {
    	return date;
    }
    
    public void setdate(String date) {
    	this.date = date;
    }
    
    public int getis_exist() {
        return is_exist;
    }
 
    public void setis_exist(int is_exist) {
        this.is_exist = is_exist;
    }
    
    public MultipartFile getuploadFile() {
    	return uploadFile;
    }
    
    public void setuploadFile(MultipartFile uploadFile) {
    	this.uploadFile = uploadFile;
    }
    
}