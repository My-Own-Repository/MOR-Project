package com.co.dto;

public class FindIDPWDTO {
	public String which;
    public String id;
    public String pw;
    public String name;
    public String email;
    public int is_exist;
    public String sign_day;

  	public FindIDPWDTO() {}
  	public FindIDPWDTO(String which, String id, String pw, String name, String email, int is_exist, String sign_day) {
  		super();
  		this.which = which;
  		this.id = id;
  		this.pw = pw;
  		this.name = name;
  		this.email = email;
  		this.is_exist = is_exist;
  		this.sign_day = sign_day;
  	}
    
    public String getwhich() {
        return which;
    }
 
    public void setwhich(String which) {
        this.which = which;
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

    
    public String getemail() {
        return email;
    }
 
    public void setemail(String email) {
        this.email = email;
    }
    
    public int getis_exist() {
        return is_exist;
    }
 
    public void setis_admin(int is_exist) {
        this.is_exist = is_exist;
    }
    
    public String getsign_day() {
        return sign_day;
    }
 
    public void setsign_day(String sign_day) {
        this.sign_day = sign_day;
    }

}