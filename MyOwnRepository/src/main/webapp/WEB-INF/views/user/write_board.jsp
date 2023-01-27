<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 
cellpadding="0" cellspacing="0"을 쓰기위한 설정
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
-->

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
	<link rel="stylesheet" type="text/css" href="../../../resources/css/write.css">
	<link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
	
	<%
		//String userNickname = (String) session.getAttribute("userNickname");  
	%> 
	
	
	<title>나만의 저장소 - MOR</title>
	
	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

</head>
 
<body>
	<div class="empty_write_div">
	<div class="total_write_div">
	<a href="/user/userMain/1">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div class="search_div">
		<input type="text" class="search_input" placeholder="검색어 입력">
		<input type="button" value="검색" class="search">
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">게시판</a>
			<ul class="submenu">
				<li><a href="/user/userMain">자유게시판</a></li>
				<li><a href="/user/secret_board">비밀게시판</a></li>
			</ul>
		</li>
		<li>
			<a href="#">저장소</a>
			<ul class="submenu">
				<li><a href="#">공유 저장소</a></li>
				<li><a href="#">나만의 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${member.nickname}</a>
		</li>
	</ul>
	
	<br><br>	
	<c:choose>
		<c:when test="${what == 0}">		<!-- 자유게시판 글쓰기 -->
			<p>자유게시판 - 글쓰기</p><br>
			<div>
			<form id="write_form" action="/user/write_board" method="post" enctype="multipart/form-data">
				<table border="1" class="write_form_table">
					<tr>
		 				<th>작성자</th>
		 				<td style="text-align: left">&nbsp;&nbsp;${member.nickname}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td><input type="text" name="title" id="write_title" class="input_text_title"></td>
		 			</tr>
		 
					<tr>
						<th>내용</th> 
						<td>
							<div contenteditable="true" id="write_content_div" class="write_content_div"></div>
							<textarea id="write_content_textarea" name="content" style=display:none></textarea>
							<input type="hidden" name="is_secret" value="0">
						</td>
						
					</tr>
		
					<tr>
						<th><input type="button" id="add_file_btn" class="add_file_btn" onClick="add_inputfile();" value="Add File"></th>
						<td>
							<div id="input_file_divs">
							
							</div>
		
						</td>
					</tr>
				</table>
						<input type="button" value="글쓰기" class="small_write_btn" onClick="submit_btn();">
						<input type="button" value="취소" class="small_write_btn" onClick="location.href='/user/userMain/1'">	
			</form>
			</div>
		</c:when>
		
		<c:when test="${what == 1}">		<!-- 비밀게시판 글쓰기 -->
			<p>비밀게시판 - 글쓰기</p><br>
			<div>
			<form id="write_form" action="/user/write_board" method="post" enctype="multipart/form-data">
				<table border="1" class="write_form_table">
					<tr>
		 				<th>작성자</th>
		 				<td style="text-align: left">&nbsp;&nbsp;${member.nickname}</td>
					</tr>
					<tr>
						<th>제목</th>
						<td><input type="text" name="title" id="write_title" class="input_text_title"></td>
		 			</tr>
		 
					<tr>
						<th>내용</th> 
						<td>
							<div contenteditable="true" id="write_content_div" class="write_content_div"></div>
							<textarea id="write_content_textarea" name="content" style=display:none></textarea>
							<input type="hidden" name="is_secret" value="1">
						</td>
						
					</tr>
		
					<tr>
						<th><input type="button" id="add_file_btn" class="add_file_btn" onClick="add_inputfile();" value="Add File"></th>
						<td>
							<div id="input_file_divs">
							
							</div>
		
						</td>
					</tr>
			
				</table>
				<br>
				<div class="secret_input_img_div">
					<img src="../../../resources/img/lock.png" alt="" class="lock_img">
				</div>
				&nbsp;
				<table border="1" class="secret_input_table">	
					<tr>
						<th class="secret_input_th">
							<span class="secretNum_input_span">비밀번호</span>
						</th>
						<td class="secret_input_td">
							<input type="password" id="pw1" name="secret_num" maxlength='4' class="secretNum_input">						
						</td>
					</tr>
					<tr>
						<th class="secret_input_th">
							<span class="secretNum_input_span">비밀번호 확인</span>
						</th>
						<td class="secret_input_td">						
							<input type="password" id="pw2" maxlength='4' class="secretNum_input">				
						</td>
					</tr>
				</table>
				
				<br><br>
						<input type="button" value="글쓰기" class="small_write_btn" onClick="submit_btn();">
						<input type="button" value="취소" class="small_write_btn" onClick="location.href='/user/secretBoard/1'">	
			</form>
			<br><br><br>
			</div>
		</c:when>
	</c:choose>
	

	<br>
	<div>
		<br><br>
		<table border="1" class="downFile_table">
			<tr>
				<th class="downFile_th">&nbsp;첨부파일<a href="javascript:delete_allfile()"><img src="../../../resources/img/trash.png" class="trash_img"></a></th>
			</tr>
			<tr>
				<td class="downFile_td">
					<div id="uploadFiles" class="uploadFiles">
						
					</div>
				</td>
			</tr>		
		</table>
		<br><br>
	</div>
	</div>
	</div>
	<br><br>
	<script>
	//var div_group_num = 0;
	var add_num = 0;
	//var files_index = 0;		// 파일 인덱스 선언 (파일 삭제시 사용됨)
	// 파일을 업로드 할 수 있는 file 타입의 input을 하나 추가한다.
	function add_inputfile(){
		//alert("input파일 넘버 >> " + add_num);
		var add_inputfile_div = document.getElementById('input_file_divs');			
		add_inputfile_div.innerHTML += ('<input type="file" id="' + add_num++ + '" class="input_file" name="files" multiple="multiple" onchange="is_img_video(this)"><br>');
		
	}
	add_inputfile();
	
	/*
		업로드 파일 개별 삭제가 도저히 안돼서 전체 삭제로 임시 구현했다.
		
		* 개별 삭제시 구현해야 할 목록
		(	
			1. 업로드된 첨부파일 목록 개별 삭제	O
			2. 미리보기 개별 삭제 X - reader.onload이 한번에 실행되는 구조라서 미리보기용 파일마다 개별 id를 줄수가없음.
			3. input file 개별 삭제 X - 왜 안되는지, 어떻게 해야할지 감이 안잡힘. 이게 가장 막막하다...
		)
	*/
	function is_img_video(f) {
		//var is_preview = true;		// 게시판에서는 파일이 이미지나, 동영상이 아닐 경우 미리보기를 지원하지 않는다.(다운로드도 지원x) 이를 위한 boolean
									// 저장소에서는 모든 유형의 파일업로드, 다운로드 지원.
		
		// 파일업로드 박스 생성버튼 비활성화
		var addFile_disa = document.getElementById('add_file_btn');		
		addFile_disa.value = "Add Lock";
		addFile_disa.style.background = "red";
		addFile_disa.disabled = true;							
		
		/*
		var 
		var inputFileDisable = document.getElementById('');
		*/						
							
		
		//var preview_id = '';
		var k=0;
					
		var file = f.files;
		
		var divAry;
		var divId = '';
		var divName = 'preview_files';
		
		divId = 'divID_'+f.id;
		
		
		divAry = document.createElement('div');
		divAry.setAttribute("id", divId);
		divAry.setAttribute("class", write_content_div);
		divAry.setAttribute("name", divName);
		
		
		for(var i=0; i<file.length; i++) {
			var fileList = f.files;		// 파일 받아와서 스크립트 내부 변수에 저장.
			var fileName = fileList[i].name;		// 파일 이름 추출
			
			var fileLength = fileName.length;		// 파일명 길이 추출		
			var fileDot = fileName.lastIndexOf(".");	// 파일의 확장자 추출		
			var fileType = fileName.substr(fileDot+1, fileLength).toLowerCase();	// 추출한 확장자를 소문자로 변경한다.
			
            var reader = new FileReader();
			
            
            reader.onload = function (e) {
    	
            	// 파일이 이미지일때 수행
            	if("jpg" == fileType || "jpeg" == fileType || "gif" == fileType || "png" == fileType || "bmp" == fileType){
					
					// 3) 업로드 된 파일이 이미지 혹은 영상태그일 경우 새로운 div안에 img,video태그로 넣음
					divAry.innerHTML += '<img src="' + e.target.result + '"><br>';
					document.querySelector("div#write_content_div").appendChild(divAry);
              		
            	}
            	
            	// 파일이 동영상/오디오 일때 수행
				else if("mpg" == fileType || "mpeg" == fileType || "mp4" == fileType || "ogg" == fileType || "webm" == fileType || "avi" == fileType || "wmv" == fileType || "mov" == fileType || "rm" == fileType || "ram" == fileType || 
						"swf" == fileType || "flv" == fileType || "wav" == fileType || "mp3" == fileType){
					
					// 3) 업로드 된 파일이 이미지 혹은 영상태그일 경우 새로운 div안에 img,video태그로 넣음
					divAry.innerHTML += '<video src="' + e.target.result + '"><br>';
					document.querySelector("div#write_content_div").appendChild(divAry);
					
					               
				}
            	

				/*	 // 자유게시판, 비밀게시판에서는 오로지 이미지, 영상 파일만 업로드 할 수 있도록 하며 다운로드 기능은 제공하지 않는다.
					 // 저장소, 비밀저장소에서는 모든 파일 업로드 가능, 다운로드 기능 제공.
				else{
					is_preview = false;		// 이미지나 영상 파일이 아닌 다른 유형의 파일이 들어오면 false값을 줌
					$('#file').val('');		// input file에서 업로드한 파일들을 지우며 초기화 시킴.
					alert("FAIL\n자유 게시판에서 지원하지 않는 유형의 파일입니다.\n자유 게시판 지원 파일유형 <이미지, 영상>");
					break; 
				}
				*/
            }
            /*	// 이미지,영상 파일이 아닌 다른 유형의 파일이 선택된 경우 미리보기, 다운로드 지원 x
            if(is_preview = true){
            	reader.readAsDataURL(f.files[i]);
                uploadFileList(f.files[i]);
            }
            */
            
            reader.readAsDataURL(f.files[i]);
           
     
        }
		uploadFileList(f);
        //files_index += 1;		// 파일 인덱스 추가 (파일업로드를 취소 할때 파일을 특정하기 위해 사용)
    }
	
	function uploadFileList(f){
		
		var files = f.files;
		
		//alert("받은 넘버 >> " + f.id);
		if(files.length != 0){
			var content = '';
			
			var fileName = new Array();		// 파일 이름(+확장자)
			var fileSize = new Array();		// 파일 사이즈
			
			content += '<div class="attached_file" id="attach_id_' + f.id + '"><div class="attached_file_line">';
			for(var i=0; i<files.length; i++){
				fileName[i] = files[i].name;
				fileSize[i] = files[i].size;
				
				content += '<font size="2px" color="blue">&nbsp;' + fileName[i] + '</font>';
				content += '<span style="font-size: 8px">&nbsp;&nbsp;' + fileSize[i] + '&nbsp;kb</span>&emsp;';
			}					
			content += '</div><a href="javascript:deleteFile(' + f.id + ')"><img src="../../../resources/img/Red_X_img.png" class="delete_file_img"></a><br></div>';
			
			var upfile = document.getElementById('uploadFiles');		// 업로드된 파일리스트를 보여줄 div 가져오기
			upfile.innerHTML += content;
					
			// 파일업로드 박스 비활성화
			var input_file_lock_ID = f.id;
			var input_file_lock = document.ElementById(input_file_lock_ID);
			input_file_lock.disabled = true;
		}				
	}

	function deleteFile(n){		
		//alert("파일 삭제 함수는 작동함");
		var attached_divId = 'attach_id_'+n;		// 첨부된 파일 목록에서 삭제할 특정 div의 아이디 선언, ex : (in_js_filenum_0) 
		
		// 첨부된 파일 목록 view에서 해당 파일을 삭제한다.
		var delete_file_div = document.getElementById(attached_divId);			
		delete_file_div.remove();
		
		
		
		// 미리보기 삭제
		var preview_divID = 'divID_'+n;
		var preview_hidden_div = document.getElementById(preview_divID);
		preview_hidden_div.remove();
		
		
		// input_file 삭제
		var input_filesID = n;
		var beforeFiles = document.getElementById(input_filesID);
		beforeFiles.value = null;
		
		// 파일을 삭제했으므로 파일 인덱스도 감소
		//files_index -= 1;
	}
	
	// 업로드 한 모든 파일관련 정보 삭제하는 함수
	function delete_allfile(){
		//alert("전체삭제 함수 실행은 된다");
		
		if(confirm("첨부한 파일들을 초기화 하시겠습니까?")){		
			var delete_file_div = document.getElementsByClassName("attached_file");		// 첨부된 파일 목록 가져오기
			var hidden_preview = document.getElementsByName('preview_files');		// 미리보기 목록 가져오기
			var beforeFiles = document.getElementsByName("files");		// input file 목록 가져오기
			
			
			// input 파일 목록 개수만큼 반복
			for(var k=0; k<beforeFiles.length; k++){
				// 첨부된 파일 목록 view에서 전체 파일을 삭제한다.
				for(var i=0; i<delete_file_div.length; i++){
					delete_file_div[i].remove();
				}
				delete_file_div = document.getElementsByClassName("attached_file");	
				
				// 파일 미리보기 전체 삭제			
				for(var i=0; i<hidden_preview.length; i++){
					hidden_preview[i].remove();
				}
				hidden_preview = document.getElementsByName('preview_files');
				
				// input type = "file" 전체 삭제		
				for(var i=0; i<beforeFiles.length; i++){
					beforeFiles[i].value = null;
				}
				beforeFiles = document.getElementsByName("files");
			}
						
			// 파일업로드 박스 생성버튼 활성화
			var addFile_disa = document.getElementById('add_file_btn');		
			addFile_disa.value = "Add File";
			addFile_disa.style.background = "black";
			addFile_disa.disabled = false;
			
			// 파일업로드 박스 활성화
			var input_file_lock_ID = n;
			var input_file_lock = document.ElementById(input_file_lock_ID);
			input_file_lock.disabled = false;
			
		}
	}
	
   
	function secret_password(){
	    var pw1 = document.getElementById("pw1").value;
	    var pw2 = document.getElementById("pw2").value;	    
	    
	    var numPattern = /([^0-9])/;
	    numPattern = pw1.match(numPattern);
	    
		if(pw1.length == 0 || pw1.length < 4 || numPattern != null){
	        alert("ERROR\n숫자로 구성된 4자리 비밀번호를 입력해주세요."); 
	        $("#pw1").focus();
	        return false;
	    }
		else if(pw2.length == 0 || pw2.length < 4){       			
    	    alert("ERROR\n비밀번호를 재확인해주세요."); 
    	    $("#pw2").focus();
    		return false;           	
		}
	    else if(pw2 != pw1){
	        alert("ERROR\n비밀번호가 서로 다릅니다. 비밀번호를 확인해주세요."); 
	        $("#pw2").focus();
	        return false; 
	    }
	    else {
	    	pw1 = Number(pw1);
	    	alert(typeof pw1);
	    }
	}
	
	function submit_btn(){
		var title = document.getElementById('write_title').value;		// 사용자가 입력한 제목의 값 가져오기
		var content;	// 사용자가 입력한 내용의 값	
			
		//content = content.replace(/<br>/ig, "");	// 내용에 줄바꿈만 한 경우 글 작성이 안되도록 줄바꿈 태그를 삭제한 후, 해당 내용의 길이를 검사할 예정.
		
		var copy = document.getElementById('write_content_div').innerText;		// 사용자가 div에 입력한 내용의 값을 form으로 전송할 textarea에 복사. 이를 위한 변수 copy 선언
		document.getElementById('write_content_textarea').value = copy;		// 사용자가 입력한 내용을 textarea로 복사.
		
		content = document.getElementById('write_content_textarea').value;		// 내용 유효성 검사시 사용할 변수
		
		// 제목과 내용의 유효성 검사를 위해 줄바꿈 문자, 공백 제거
		title = title.replace(/<br>/ig, '');
		title = title.replace(/&nbsp;/ig, '');
		title = title.replace(/\s/ig, '');
		
		content = content.replace(/<br>/ig, '');
		content = content.replace(/&nbsp;/ig, '');
		content = content.replace(/\s/ig, '');
		
		var is_secret = ${what}; 
		
		if(title.length > 0 && title.length <= 50 && content.length > 0 && content.length <= 1000){
			if(is_secret == 1){
				secret_password();
			}
			document.getElementById('write_form').submit();			
		}
		else if(title.length == 0){
			alert('ERROR\n제목을 입력해주세요.');
		}
		else if(title.length > 50){
			alert('ERROR\n제목은 50자 이내로 입력해주세요.');
		}
		else if(content.length == 0){
			alert('ERROR\n내용을 입력해주세요.');
		}
		else if(content.length > 1000){
			alert('ERROR\n내용은 1000자 이내로 입력해주세요.');
		}
		
	}
	
	</script>
	
	<c:if test="${b_msg == false}">
		<script>
			alert('ERROR\n제목 : 1~50자 이내\n내용 : 1~1000자 이내로 입력해주세요!');
		</script>
	</c:if>
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
	
	<br><br><br><br><br><br><br><br>
        <footer>
            <div class="foot-sector">
                <div class="footer-underline">
                <nav class="footerinfo-division-top">
                    <div class="inner">
                        <div class="link-about">
                            <a href="#">이용약관</a>
                            <a href="#"><b>개인정보처리방침</b></a>
                            <a href="#">사업자정보확인</a>

                        </div>
                        <div class="link-social">
                            <div class="link-social-item">
                                <a href="#">
                                    <img src="../../../resources/img/ic-faq-32.svg" alt="question-rogo">
                                    <span>FAQ</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-facebook-rogo-32.svg" alt="facebook-rogo">
                                    <span>페이스북</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-kakaoplus-rogo-32.svg" alt="kakaoplus-rogo">
                                    <span>카카오플러스</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-insta-rogo-32.svg" alt="insta-rogo">
                                    <span>인스타그램</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
                <address class = "footerinfo-division-bottom">
                
                <div class="company-info">
                    <div class="company-name">(주)MOR</div>
                    <div class="bundle">
                        <span>대표이사 : 주은상</span>
                        <span>사업자등록번호 : 000-00-00000</span>
                    </div>
                    <div class="bundle">
                        <span>호스팅사업자 : (주)MOR</span>
                        <span>주소 : 안산시 단원구 MOR (호수동)</span>
                    </div>
                    <div class="bundle">
                        <span>개인정보관리책임자 : 홍길동</span>
                    </div>
                </div>
                
                <div class="servicecenter">
                    <div class="center-phonenumber">
                        <b>고객센터 0000-0000</b>
                    </div>
                    <div class="center-info">
                        <span>영업시간</span>
                        <time>AM 00:00</time>
                        ~
                        <time>PM 11:59</time>
                        (주말 및 공휴일 휴무)
                    </div>
                    <div class="center-info">
                        <span>점심시간</span>
                        <time>AM 11:00</time>
                        ~
                        <time>PM 01:00</time>
                    </div>

                </div>
               
                </address>
                
                <address class = "footerinfo-division-bottom">
					<div class="company-info">               
	                    <div class="company-name">저작권</div>
	                    <div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/" title="폐물 아이콘">폐물 아이콘  제작자: Pavel Kozlov - Flaticon</a></span>
	                    </div>
						<div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/-" title="맹꽁이 자물쇠 아이콘">맹꽁이 자물쇠 아이콘  제작자: DinosoftLabs - Flaticon</a></span>
	                    </div>
	                    <div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/-" title="열린 자물쇠 아이콘">열린 자물쇠 아이콘  제작자: Freepik - Flaticon</a></span>
	                    </div>
	                </div>
				</address>

                <div class="safety"></div>
            </div>
        </footer>
        
</body>
</html>