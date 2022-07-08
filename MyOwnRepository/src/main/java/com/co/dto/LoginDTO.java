package com.co.dto;

public class LoginDTO {
	 
    public String id;
    public String name;
    public String pw;
    public String sex;
    public String number;
    public String email;
    

  	public LoginDTO() {}
  	public LoginDTO(String id, String name, String pw, String sex, String number, String email) {
  		super();
  		this.id = id;
  		this.name = name;
  		this.pw = pw;
  		this.sex = sex;
  		this.number = number;
  		this.email = email;
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

    public String getsex() {
        return sex;
    }
   
    public void setsex(String sex) {
        this.sex = sex;
    }
    public String getnumber() {
        return number;
    }
 
    public void setnumber(String number) {
        this.number = number;
    }
    
    public String getemail() {
        return email;
    }
 
    public void setemail(String email) {
        this.email = email;
    }
}