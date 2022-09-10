package com.co.dto;

public class boardDTO {
	
	public int num;
	public String title;
	public String content;
    public String id;
    public String nickname;
    public String date;
    public int view;

  	public boardDTO() {}
  	public boardDTO(int num, String title, String content, String id, String nickname, String date, int view) {
  		super();
  		this.num = num;
  		this.title = title;
  		this.content = content;
  		this.id = id;
  		this.nickname = nickname;
  		this.date = date;
  		this.view = view;
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
}