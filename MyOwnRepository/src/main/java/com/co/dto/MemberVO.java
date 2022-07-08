package com.co.dto;

public class MemberVO {
	 
    public String id;
    public String pw;
    public String name;
    public String nickname;
    public String phone_number;
    public String email;
    public int is_admin;
    

  	public MemberVO() {}
  	public MemberVO(String id, String pw, String name, String nickname, String phone_number, String email, int is_admin) {
  		super();
  		this.id = id;
  		this.pw = pw;
  		this.name = name;
  		this.nickname = nickname;
  		this.phone_number = phone_number;
  		this.email = email;
  		this.is_admin = is_admin;
  	}
    
    public String getid() {
        return id;
    }
 
    public void setid(String id) {
        this.id = id;
    }
 
    public String getpw() {
        return pw;
    }
 
    public void setpw(String pw) {
        this.pw = pw;
    }
 
    public String getname() {
        return name;
    }
 
    public void setname(String name) {
        this.name = name;
    }

    public String getnickname() {
        return nickname;
    }
   
    public void setnickname(String nickname) {
        this.nickname = nickname;
    }
    public String getphone_number() {
        return phone_number;
    }
 
    public void setphone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    
    public String getemail() {
        return email;
    }
 
    public void setemail(String email) {
        this.email = email;
    }
    public int getis_admin() {
        return is_admin;
    }
 
    public void setis_admin(int is_admin) {
        this.is_admin = is_admin;
    }
    

}