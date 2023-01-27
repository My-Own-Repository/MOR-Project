<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">

	<link rel="stylesheet" type="text/css" href="../../../resources/css/write.css">
	<link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	
	<%
		Integer post_num = (Integer) request.getAttribute("post_num");			
		String post_id = (String) request.getAttribute("post_id");
		String post_nickname = (String) request.getAttribute("post_nickname");
		String post_title = (String) request.getAttribute("post_title");
		String post_content = (String) request.getAttribute("post_content");
	%> 

	<title>나만의 저장소 - MOR</title>
	
	<script src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		

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
		
	<br><br><p>자유게시판 - 글수정</p><br>
	
	
		<form id="update_form" action="/user/update_board" method="post" enctype="multipart/form-data">
			<input type="hidden" name="num" value="<%=post_num %>">
			<table class="update_table" border="1">		
				<tr>
	 				<th>작성자</th>
	 				<td style="text-align: left">&nbsp;&nbsp;<%=post_nickname %></td>
				</tr>
				<tr>
					<th>제목</th>
					<td> <input type="text" name="title" size = "65" value="<%=post_title %>"></td>
	 			</tr>
	 
			<tr>
				<th>내용</th> 
				<td>
												
								<c:forEach items="${fileViewer}" var="files">
									<div id="fileView_${files.file_num}" class="uploaded_fileView">
										<img src="/loadfiles.do/${files.file_num}" class="update_upload_imgs" id="imgView_${files.file_num}" alt="">
										<br>
										<video src="/loadfiles.do/${files.file_num}" class="update_upload_videos" id="videoView_${files.file_num}" controls></video>
									</div>
									<br>
									
									<script>
										function is_exist_file(){	
											// img src와 video src의 각각 고유의 id를 가지기 위해 
											// 이미지는 파일 번호, 동영상은 파일 이름을 아이디 값으로 주었다.
											
											// 각각 고유 id를 가지게 한 이유는 각 파일마다 숨길지 혹은 보이게 할지 판별할때 보다 용이하기 때문이다.
											
											/*
											const img_id = '${files.file_num}';
											const video_id = '${files.stored_file_name}';
											*/
											
											const img_id = 'imgView_'+'${files.file_num}';
											const video_id = 'videoView_'+'${files.file_num}';
																	
											const upload_img = document.getElementById(img_id);
											const upload_video = document.getElementById(video_id);
											
											var fileType = '${files.type}';
											
											var img_exist = false;
											var video_exist = false;
											
											// 각 해당 파일이 존재하면 exist 변수가 true가 됨
											
											if(fileType.includes("image") == true){
												img_exist = true;
											}				
											else if(fileType.includes("video") == true){
												video_exist = true;
											}
											else {
												alert("ERROR");
											}
											
											// 각 exist 변수가 true일 경우 보이고 false일 경우 숨김 처리됨
											
											
											if(img_exist == false){
												upload_img.style.display = 'none';
											}
											else if(img_exist == true){
												upload_img.style.display = 'block';
											}
														
											if(video_exist == false){
												upload_video.style.display = 'none';
											}
											else if(video_exist == true){
												upload_video.style.display = 'block';
											}	
											
											
										}
										is_exist_file();
							
									</script>

								</c:forEach>
								<br>
							
							
							<br>
							<br>
					<input type="hidden" name="id" value="<%=post_id%>">
					<div contenteditable="true" id="update_content_div" class="update_content_div">${SelectPost.content}</div>
					<textarea id="update_content_textarea" name="content" style=display:none></textarea>
					  
					<div id="existing_file_delete_div">
						<input type="hidden" name="existing_Delete_fileNum" value="-1">
					</div>
					
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
	</form>
	 
	
	<br>
	<div>
		<table border="1" class="downFile_table">
			<tr>
				<th class="downFile_th">&nbsp;첨부파일<a href="javascript:existing_allFileDelete()"><img src="../../../resources/img/trash.png" class="trash_img"></a></th>
			</tr>
			<tr>
				<td class="downFile_td">
					<div class="uploadedFiles">
						<c:forEach items="${fileDown}" var="f">
							<div id="existing_file_${f.file_num}" class="uploaded_fileList">
								<font size="2px" color="blue">&nbsp;${f.original_file_name}</font>
								<span style="font-size: 8px">&nbsp;&nbsp;${f.file_size}&nbsp;kb</span>&emsp;
								<a href="javascript:existing_file_delete(${f.file_num})"><img src="../../../resources/img/Red_X_img.png" class="delete_file_img"></a><br>
							</div>
						</c:forEach>
					</div>
				</td>
			</tr>		
		</table>
		<br>
		<table border="1" class="downFile_table">
			<tr>
				<th class="downFile_th">&nbsp;+ 첨부파일<a href="javascript:delete_allfile()"><img src="../../../resources/img/trash.png" class="trash_img"></a></th>
			</tr>
			<tr>
				<td class="downFile_td">
					<div id="uploadFiles" class="uploadFiles">
						
					</div>
				</td>
			</tr>		
		</table>
		<br><br>
	<div class="update_div">
		<input type="button" value="수정" class="small_write_btn2" onclick="submit_btn();">
		<input type="button" onclick="location.href='/user/posts?urlnum=<%=post_num%>'" value="취소" class="small_write_btn3">
		<br><br><br>
	</div>
	</div>
	<br><br><br>
	</div>
	</div>
	
	<script>
	var add_num = 0;
	// 파일을 업로드 할 수 있는 file 타입의 input을 하나 추가한다.
	function add_inputfile(){	

		var add_inputfile_div = document.getElementById('input_file_divs');			
		add_inputfile_div.innerHTML += ('<input type="file" id="' + add_num++ + '" class="input_file" name="files" multiple="multiple" onchange="is_img_video(this)"><br>');
		
	}
	add_inputfile();
	

	
	// 기존에 업로드했던 파일을 삭제하는 함수.
	// 이 함수에서 삭제된 파일의 고유번호를 input hidden 형식으로 form을 통해 컨트롤러로 보낸다.
	function existing_file_delete(delete_existing_fileNum){	

		
			// 파일삭제 이벤트가 발생할때마다 그때그때 해당 파일번호를 hidden으로 넘겨줌
		var target_file_deleteDiv = document.getElementById('existing_file_delete_div');
		var delete_Div_content = '';
		delete_Div_content += '<input type="hidden" name="existing_Delete_fileNum" value="'+ delete_existing_fileNum +'">'; 	// value="'+ delete_existing_fileNum +'"
				
		target_file_deleteDiv.innerHTML += delete_Div_content;	
		
		
		// 첨부되어 있던 파일 목록에서 해당 파일을 삭제한다.
		var existing_divId = 'existing_file_'+delete_existing_fileNum;		// 첨부된 파일 목록에서 삭제할 특정 div의 아이디 선언	
		var delete_file_div = document.getElementById(existing_divId);		
		delete_file_div.remove();
		
		
		
		// 첨부되어 있던 파일 view 삭제
		var delete_file_view = 'fileView_'+delete_existing_fileNum;
		var preview_remove_file = document.getElementById(delete_file_view);	
		preview_remove_file.remove();
		

	}
	
	// 첨부되었던 기존 파일들 전체 삭제하는 함수
	function existing_allFileDelete(){
		//alert("전체삭제 함수 실행은 된다");
		if(confirm("저장된 첨부파일들을 초기화 하시겠습니까?")){
			/*
			var target_file_deleteDiv = document.getElementById('existing_file_delete_div');
			var delete_Div_content = '';
		
			delete_Div_content += '<input type="hidden" name="existing_Delete_fileNum" value="all">';
			target_file_deleteDiv.innerHTML += delete_Div_content;
		
			*/
			var k = 0;
			var fileArr = new Array();
			<c:forEach items="${fileDown}" var="list">
				fileArr[k++] = ${list.file_num};
			</c:forEach>
			
			var target_file_deleteDiv = document.getElementById('existing_file_delete_div');
			var delete_Div_content = '';
			for(var i=0; i<fileArr.length; i++){
				delete_Div_content += '<input type="hidden" name="existing_Delete_fileNum" value="' + fileArr[i] + '">';
			}
			target_file_deleteDiv.innerHTML += delete_Div_content;
			
			
			
			var delete_file_div = document.getElementsByClassName("uploaded_fileList");		// 첨부되었던 기존 파일 목록 가져오기
			var hidden_preview = document.getElementsByClassName('uploaded_fileView');		// 첨부되었던 기존 파일 view 목록 가져오기
		
				
			// 기존 첨부파일 목록 개수만큼 반복
			for(var k=0; k<delete_file_div.length*3; k++){
				// 기존 파일 목록에서 파일을 전체 삭제한다.
				for(var i=0; i<delete_file_div.length; i++){
					delete_file_div[i].remove();					
				}	
				delete_file_div = document.getElementsByClassName("uploaded_fileList");
			}	
			
			
			// 기존 파일 view 전체 삭제							
			for(var k=0; k<hidden_preview.length*3; k++){
				for(var i=0; i<hidden_preview.length; i++){
					hidden_preview[i].remove();
				}
				hidden_preview = document.getElementsByClassName('uploaded_fileView');
			}
			
			
			
		}
	}
	
	
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
		// var update_content_div = "update_content_div";
		
		divId = 'divID_'+f.id;
		
		
		divAry = document.createElement('div');
		divAry.setAttribute("id", divId);
		divAry.setAttribute("class", update_content_div);
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
					document.querySelector("div#update_content_div").appendChild(divAry);
              		
            	}
            	
            	// 파일이 동영상/오디오 일때 수행
				else if("mpg" == fileType || "mpeg" == fileType || "mp4" == fileType || "ogg" == fileType || "webm" == fileType || "avi" == fileType || "wmv" == fileType || "mov" == fileType || "rm" == fileType || "ram" == fileType || 
						"swf" == fileType || "flv" == fileType || "wav" == fileType || "mp3" == fileType){
					
					// 3) 업로드 된 파일이 이미지 혹은 영상태그일 경우 새로운 div안에 img,video태그로 넣음
					divAry.innerHTML += '<video src="' + e.target.result + '"><br>';
					document.querySelector("div#update_content_div").appendChild(divAry);
					
					               
				}
            	

            }

            
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
		
		
		// input_file 삭제
		var input_filesID = n;
		var beforeFiles = document.getElementById(input_filesID);
		beforeFiles.value = null;
		
		// 미리보기 삭제
		var preview_divID = 'divID_'+n;
		var preview_hidden_div = document.getElementById(preview_divID);
		preview_hidden_div.remove();
		
	}
	
	function delete_allfile(){
		//alert("전체삭제 함수 실행은 된다");
		if(confirm("새로 첨부한 파일들을 초기화 하시겠습니까?")){
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
				
				// input type = "file" 전체 삭제		
				for(var i=0; i<beforeFiles.length; i++){
					beforeFiles[i].value = null;
				}
				beforeFiles = document.getElementsByName("files");
				
				// 파일 미리보기 전체 삭제			
				for(var i=0; i<hidden_preview.length; i++){
					hidden_preview[i].remove();
				}
				hidden_preview = document.getElementsByName('preview_files');
				
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
	
	function submit_btn(){	// existing_file_num_
		if(confirm('수정사항을 저장 하시겠습니까?')){
			var content;	// 사용자가 입력한 내용의 값	
			
			var copy = document.getElementById('update_content_div').innerText;		// 사용자가 div에 입력한 내용의 값을 form으로 전송할 textarea에 복사. 이를 위한 변수 copy 선언
			document.getElementById('update_content_textarea').value = copy;		// 사용자가 입력한 내용을 textarea로 복사.
			
			content = document.getElementById('update_content_textarea').value;		// 내용 유효성 검사시 사용할 변수
			
			// 내용의 유효성 검사를 위해 줄바꿈 문자, 공백 제거
			content = content.replace(/<br>/ig, '');
			content = content.replace(/&nbsp;/ig, '');
			content = content.replace(/\s/ig, '');
			
			
			if(content.length > 0 && content.length <= 1000){
				//document.getElementById('delete_file_form').submit();
				document.getElementById('update_form').submit();		
			}
			else if(content.length == 0){
				alert('ERROR\n내용을 입력해주세요.');
			}
			else if(content.length > 1000){
				alert('ERROR\n내용은 1000자 이내로 입력해주세요.');
			}
		}
			
		/* 유효성 검사 안하는 버전
		if(confirm('수정사항을 저장 하시겠습니까?')){
			var copy = document.getElementById('update_content_div').innerText;			
			document.getElementById('update_content_textarea').value = copy;
			
			document.getElementById('update_form').submit();
		}
		*/
	

	}
	</script>
	
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
	
	<c:if test="${another_msg == false}">
		<script>
			alert('ERROR\n접근할 수 없는 페이지입니다.');
		</script>
	</c:if>
	
	<c:if test="${effectiveness_msg == false}">
		<script>
			alert('ERROR\n제목 : 1~50자 이내\n내용 : 1~1000자 이내로 입력해주세요!');
		</script>
	</c:if>
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
	
</body>
</html>