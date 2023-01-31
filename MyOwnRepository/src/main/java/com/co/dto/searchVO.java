package com.co.dto;

public class searchVO {
	public int is_secret;
	public int is_repo;
	public String search_filter;
	public String content;
	
	public searchVO() {}
	public searchVO(int is_secret, int is_repo, String search_filter, String content) {
		this.is_secret = is_secret;
		this.is_repo = is_repo;
		this.search_filter = search_filter;
		this.content = content;
	}
	
    public int getis_repo() {
    	return is_repo;
    }
    public void setis_repo(int is_repo) {
    	this.is_repo = is_repo;
    }
    
	public int getis_secret() {
    	return is_secret;
    }
    public void setis_secret(int is_secret) {
    	this.is_secret = is_secret;
    }
	
    
    public String getsearch_filter() {
    	return search_filter;
    }
    public void setsearch_filter(String search_filter) {
    	this.search_filter = search_filter;
    }
    
    public String getcontent() {
    	return content;
    }
    public void setcontent(String content) {
    	this.content = content;
    }
    
    
}



/*
package com.co.dto;

public class searchVO {
	public int num;
	public int is_secret;
	public int is_repo;
	public int is_exist;
	public String search_filter;
	public String title;
	public String content;
	public String nickname;
	
	public searchVO() {}
	public searchVO(int num, int is_secret, int is_repo, int is_exist, String search_filter, String title, String content, String nickname) {
		this.num = num;
		this.is_secret = is_secret;
		this.is_repo = is_repo;
		this.is_exist = is_exist;
		this.search_filter = search_filter;
		this.title = title;
		this.content = content;
		this.nickname = nickname;
	}
	
	public int getnum() {
    	return num;
    }
    public void setnum(int num) {
    	this.num = num;
    }
	
    public int getis_repo() {
    	return is_repo;
    }
    public void setis_repo(int is_repo) {
    	this.is_repo = is_repo;
    }
    
	public int getis_secret() {
    	return is_secret;
    }
    public void setis_secret(int is_secret) {
    	this.is_secret = is_secret;
    }
	
    public int getis_exist() {
        return is_exist;
    }
 
    public void setis_exist(int is_exist) {
        this.is_exist = is_exist;
    }
    
    public String getsearch_filter() {
    	return search_filter;
    }
    public void setsearch_filter(String search_filter) {
    	this.search_filter = search_filter;
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
    
    public String getnickname() {
    	return nickname;
    }
    public void setnickname(String nickname) {
    	this.nickname = nickname;
    }
    
}


*/