<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
   <link rel="stylesheet" href="/brw/resources/css/list3.css">
   <script src="<c:url value='/resources/js/common.js'/>" charset="utf-8"></script>
   <script>
  		 $(window).scroll(function(){
					// 현재 브라우저 스크롤 위치 + 브라우저창 길이 + 1 > 현재 보고있는 문서의 길이(현재 보이는 스크롤의 길이)
				if($(window).scrollTop() + $(window).height() + 0.5 > $(document).height()){
					if ($(".list:hidden").length != 0) {  //숨겨진 리스트가 있으면
						//해당 아이디 div 태그 안에 로딩 이미지 추가
						$("#loading").append("<img src='/brw/resources/images/ican/loading.gif'>"); 
				
						//0.5초뒤 해당 데이터 보여주고 로딩이미지 삭제
						setTimeout(function(){
							$(".list:hidden").slice(0,8).show();
							$("#loading img").remove();
					}, 500)
				}else{
					return;
				}
			}
					
			if($(window).scrollTop() > 700){
				$('#gogotop').slideDown();			
			}else{
				$('#gogotop').slideUp();	
			}
		});

		$(function(){
			$('.list').css('display','none');
			$('.list').slice(0,8).show();
			$('#gogotop').hide();
			
		
  			$('#gogotop').click(function () {
  			    $('body,html').animate({
  			        scrollTop: 0
  			    }, 300);
  			    return false;
  			});
  			$('.reload').click(function(){
  				$('body,html').animate({
  			        scrollTop: 0
  			    }, 1);
  				location.reload();
  			})
  		})
   </script>
</head>
<body>
<div id="bg_color1" style="background: #f5f6f7">
	<div class="row a contentList" >
		<c:forEach items="${list}" var="movieList" >
			 <div class="col-sm-6 col-md-3 list">
			 	<!-- <a href="#this" class="detail"> -->
			 	<a href="/brw/movie/movieDetail.br?MOVIE_NO=${movieList.MOVIE_NO}">
			    <div class="thumbnail">
			      <img width="300px" height="400px" src="/brw/resources/images/movie/poster/${movieList.MOVIE_POSTER_IMAGE}">
			      <%-- <input type='hidden' class='IDX' value="${dramaList.DRAMA_NO}"> --%>
			      <div class="caption">
			        <h3 class="name">${movieList.MOVIE_NAME}</h3>
			        <p>${movieList.MOVIE_DATE}・${movieList.MOVIE_COUNTRY}・${movieList.MOVIE_GENRE}</p>
			      </div>
			    </div>
			    </a>
			  </div>
		</c:forEach> 
	</div>
	
	<div id="loading"></div>
	
	<p class="totop"> 
			<button type="button" class="fa fa-angle-up fa-2x" id="gogotop"></button>
			<!-- <button type="button" class="btn btn-primary btn-lg btn-block reload">Re</button> -->
				<!-- <button type="button" class="btn btn-default btn-lg btn-block btt">Back to top</button>
			 -->
	</p>
	
	<%-- <nav>
		<div class='pag-center'>
			<ul class='pagination believe-pag'>
				${page}
			</ul>
		</div>
	</nav> --%>
	<%@ include file="/WEB-INF/views/include/include-body.jspf" %>
	<!-- <script>
	 $(document).ready(function(){
         $(".detail").on("click", function(e){ //글쓰기 버튼
             e.preventDefault();
             detail();
         });
     });
	 function detail(){
		 var comSubmit = new ComSubmit();
         comSubmit.setUrl("<c:url value='/drama/dramaDetail.br' />");
         comSubmit.addParam("DRAMA_NO",$(".IDX").val())
         comSubmit.submit();
	 }
	</script> -->
</div>
</body>
</html>