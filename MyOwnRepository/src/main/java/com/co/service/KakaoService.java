
package com.co.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoService {
	// 서비스단에서도 request를 사용할 수 있도록 설정!
	@Autowired(required=true)
	private HttpServletRequest request;
	
	public String getReturnAccessToken(String code) {
	   	 System.out.println("서비스단 code >>>>>>>>>>>>>>> "+ code);
	        String access_token = "";
	        String refresh_token = "";
	        String reqURL = "https://kauth.kakao.com/oauth/token";
	                 
	        StringBuffer myTotalURL = request.getRequestURL();		// 사이트의 전체 경로를 myURL 변수에 저장  
	        													// (http://localhost:9000/~~~~) , (http://mors.myvnc.com:9000/~~~) 등
	        String myURL = "";
	        
	        if(myTotalURL.indexOf("http://localhost") == -1) {
	       	 myURL = "&redirect_uri=http://mors.myvnc.com:9000/login/kakao";
	        }
	        else {
	       	 myURL = "&redirect_uri=http://localhost:9000/login/kakao";
	        }
	        
	        System.out.println("myURL >>>>>>>>>>>>>>> "+ myURL);
	        
	        
	       try {
	           URL url = new URL(reqURL);
	           HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            //HttpURLConnection 설정 값 셋팅
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);


	            // buffer 스트림 객체 값 셋팅 후 요청
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id=68e5fcd4712a35905ac2280d35e61313");  //앱 KEY VALUE
	            sb.append(myURL); 	// 앱 경로
	            sb.append("&code=" + code);
	            bw.write(sb.toString());
	            bw.flush();

	            //  RETURN 값 result 변수에 저장
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String br_line = "";
	            String result = "";

	            while ((br_line = br.readLine()) != null) {
	                result += br_line;
	            }

	            JsonParser parser = new JsonParser();
	            JsonElement element = parser.parse(result);


	            // 토큰 값 저장 및 리턴
	            access_token = element.getAsJsonObject().get("access_token").getAsString();
	            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return access_token;
	    }

	    public Map<String,Object> getUserInfo(String access_token) {
	           Map<String,Object> resultMap =new HashMap<>();
	           String reqURL = "https://kapi.kakao.com/v2/user/me";
	            try {
	                URL url = new URL(reqURL);
	                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                conn.setRequestMethod("GET");

	               //요청에 필요한 Header에 포함될 내용
	                conn.setRequestProperty("Authorization", "Bearer " + access_token);

	                int responseCode = conn.getResponseCode();
	                System.out.println("responseCode : " + responseCode);

	                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	                String br_line = "";
	                String result = "";

	                while ((br_line = br.readLine()) != null) {
	                    result += br_line;
	                }
	               System.out.println("response:" + result);


	               JsonParser parser = new JsonParser();
	               JsonElement element = parser.parse(result);
	               System.out.println("element:: " + element);
	               JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	               JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	               System.out.println("id:: "+element.getAsJsonObject().get("id").getAsString());
	               String id = element.getAsJsonObject().get("id").getAsString();
	               String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	               String email = kakao_account.getAsJsonObject().get("email").getAsString();
	               System.out.println("email:: " + email);
	               resultMap.put("nickname", nickname);
	               resultMap.put("id", id);
	               resultMap.put("email", email);          

	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            return resultMap;
	        }
}
