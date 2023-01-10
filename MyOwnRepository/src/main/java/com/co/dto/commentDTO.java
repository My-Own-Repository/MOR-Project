package com.co.dto;

public class commentDTO {
	
	public int b_num;
	public int c_num;
	public String nickname;
	public String content;
    public String date;
    public int is_exist;

  	public commentDTO() {}
  	public commentDTO(int b_num, int c_num, String nickname, String content, String date, int is_exist) {
  		super();
  		this.b_num = b_num;
  		this.c_num = c_num;
  		this.nickname = nickname;
  		this.content = content;
  		this.date = date;
  		this.is_exist = is_exist;
  	}
    
    public int getb_num() {
        return b_num;
    }
 
    public void setb_num(int b_num) {
        this.b_num = b_num;
    }
 
    public int getc_num() {
        return c_num;
    }
 
    public void setc_num(int c_num) {
        this.c_num = c_num;
    }
    
    public String getnickname() {
        return nickname;
    }
 
    public void setnickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getcontent() {
        return content;
    }
 
    public void setcontent(String content) {
        this.content = content;
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
}