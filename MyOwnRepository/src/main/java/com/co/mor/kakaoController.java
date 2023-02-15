
package com.co.mor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;
import com.co.mor.HomeController.SessionConst;
import com.co.service.KakaoService;
import com.co.service.MemberService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Controller
public class kakaoController {
	
	@Inject
    private KakaoService kakaoService;  
	
	@Inject
    private MemberService memberService;
	
	// 카카오 로그인
	// REST API 방식이므로 로그인 성공 후 페이지이동은 ModelAndView(redirect:/url) 방식을 사용했다.
    @ResponseBody
    @RequestMapping(value = "/login/kakao", method = RequestMethod.GET)
    public ModelAndView redirectkakao(@RequestParam String code, HttpSession session, Model model, HttpServletRequest request) throws Exception {
        System.out.println("code:: " + code);

        // 접속토큰 get
        String kakaoToken = kakaoService.getReturnAccessToken(code);
        System.out.println("접속토큰 >>>>>>>>>>>>> "+kakaoToken);
        
        // 접속자 정보 get
        Map<String, Object> result = kakaoService.getUserInfo(kakaoToken);
        System.out.println("result:: " + result);
        String snsId = (String) result.get("id");
        String userName = (String) result.get("nickname");
        String email = (String) result.get("email");
        String userpw = snsId;

        // 분기
        MemberVO memberVO = new MemberVO();
        // 일치하는 snsId 없을 시 회원가입
        System.out.println(memberService.kakaoLogin(snsId));
        if (memberService.kakaoLogin(snsId) == null) {
        	System.out.println("카카오로 회원가입");
        	
        	
        	memberVO.setid(snsId);
        	         
            memberVO.setpw(userpw);
            memberVO.setname(userName);
            memberVO.setSNSid(snsId);
            memberVO.setemail(email);
            memberService.kakaoJoin(memberVO);
        }

        // 일치하는 snsId가 있으면 멤버객체에 담음.
        LoginDTO l_member = memberService.kakaoLogin(snsId);      
        
        if (l_member == null) {
        	model.addAttribute("KLmsg", false);
        	ModelAndView mav = new ModelAndView("redirect:/LoginPage");
        	return mav;
        }           
        else {
        	session = request.getSession();		// 세션이 없으면 새로운 세션을 생성하고, 이미 존재하면 해당 세션을 반환
        	session.setAttribute(SessionConst.LOGIN_MEMBER, l_member);

        	// 로그아웃 처리 시, 사용할 토큰 값 
        	session.setAttribute("kakaoToken", kakaoToken);

        	ModelAndView mav = new ModelAndView("redirect:/user/userMain");
        	return mav;
        }
    }
}
