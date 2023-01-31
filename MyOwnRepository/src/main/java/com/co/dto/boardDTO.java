package com.co.dto;

public class boardDTO {
	
	public int num;
	public String title;
	public String content;
	//public String view_content;
    public String id;
    public String nickname;
    public int comment;
    public String date;
    public int view;
    public int is_secret;		// 비밀 게시글/저장소 판별
    public int secret_num;		// 비밀 게시글의 비밀번호
    public int is_repo;		// 저장소 판별
    public int is_exist;	// 게시글 삭제 여부

  	public boardDTO() {}
  	public boardDTO(int num, String title, String content, String id, String nickname, int comment, String date, int view, int is_secret, int secret_num, int is_repo, int is_exist) {
  		super();
  		this.num = num;
  		this.title = title;
  		this.content = content;
  		this.id = id;
  		this.nickname = nickname;
  		this.comment = comment;
  		this.date = date;
  		this.view = view;
  		this.is_secret = is_secret;
  		this.secret_num = secret_num;
  		this.is_repo = is_repo;
  		this.is_exist = is_exist;
  		//this.view_content = view_content;
  	}
    
    public int getnum() {
        return num;
    }
 
    public void setnum(int num) {
        this.num = num;
    }
 
    public String gettitle() {
        return title;
    }
 
    public void settitle(String title) {
        this.title = title;
    }
    public String getcontent() {
        return content;
    }
 
    public void setcontent(String content) {
        this.content = content;
    }
    /*
    public String getview_content() {
        return view_content;
    }
 
    public void setview_content(String view_content) {
        this.view_content = view_content;
    }
    */
    public String getid() {
        return id;
    }
 
    public void setid(String id) {
        this.id = id;
    }

    public String getnickname() {
        return nickname;
    }
 
    public void setnickname(String nickname) {
        this.nickname = nickname;
    }

    public int getcomment() {
    	return comment;
    }
    
    public void setcomment(int comment) {
    	this.comment = comment;
    }
    
    public String getdate() {
        return date;
    }
   
    public void setdate(String date) {
        this.date = date;
    }
    public int getview() {
        return view;
    }
 
    public void setview(int view) {
        this.view = view;
    }
    
    public int getis_secret() {
    	return is_secret;
    }
    public void setis_secret(int is_secret) {
    	this.is_secret = is_secret;
    }
    
    public int getsecret_num() {
    	return secret_num;
    }
    
    public void setsecret_num(int secret_num) {
    	this.secret_num = secret_num;
    }
    
    public int getis_repo() {
    	return is_repo;
    }
    public void setis_repo(int is_repo) {
    	this.is_repo = is_repo;
    }
    
    public int getis_exist() {
        return is_exist;
    }
 
    public void setis_exist(int is_exist) {
        this.is_exist = is_exist;
    }
}