/*
package com.co.mor;

import java.lang.reflect.Member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.dto.LoginDTO;
import com.co.service.MemberService;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private static final String LOGIN_MEMBER = "LOGIN_MEMBER";
	private final MemberService memberservice = null;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)  
    public String loginForm(@ModelAttribute("loginForm") LoginDTO vo) {
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@Validated @ModelAttribute("loginForm") LoginDTO vo
            , BindingResult bindingResult
            , HttpServletRequest request) throws Exception {

        if (bindingResult.hasErrors()) {
            return "login";
        }

       LoginDTO member = memberservice.loginMember(vo);

        if (member == null) {
        	bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        HttpSession session = request.getSession();		// 세션이 없으면 새로운 세션을 생성하고, 이미 존재하면 해당 세션을 반환

        session.setAttribute(LOGIN_MEMBER, member);	// 세션에 사용자 정보 저장

        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    
}
*/
