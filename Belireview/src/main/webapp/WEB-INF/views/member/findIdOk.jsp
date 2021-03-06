<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/brw/resources/Jcss/join.css" rel="stylesheet">
<style type="text/css">
#sc_big {
	border: 1px solid #d7d7d7;
}
.ctTit {
    color: black;
    font-size: 25px;

}
</style>
<title>아이디 찾기 결과</title>
</head>
<body>
	<div id="wrap">
		<!-- header -->
		<div id="header">
			<h1>
			<center><h3 class="ctTit">아이디찾기 완료</h3></center>
				<br>
			</h1>
		</div>
		<!-- container -->
		<div id="container">
			<!-- content -->
			<div id="content">
				<div class="title" aria-live="assertive">
					<p></p>
				</div>
                <form id="join_form" method="GET" action="/user2/V2Join.nhn?m=begin" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <input type="hidden" id="token_sjoin" name="token_sjoin" value="uKgeb9OiD5AQjFM0">
                    <!-- 약관동의 -->
                    <div class="terms_p" id ="sc_big">
                        <p class="terms_chk_all">
								<span class="input_chk">	
									<hr>						
									<center>${NAME}님의 아이디는 <strong>${ID}</strong>입니다.</center>	
									<hr>																
								</span>
                        </p>
                           
                    </div>
                    <!-- //약관동의 -->           
                    <div class="btn_area double">
                        <span><a href="/brw/main.br" id="btnCancel" class="btn_type btn_default" role="button">메인으로</a></span>
                        <span><a href="loginForm.br" id="btnAgree" class="btn_type btn_primary" role="button">로그인</a></span>
                    </div>
                </form>	
		<br><br><br>
		</div>
	</div>

</body>
</html>

