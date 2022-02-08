<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%@ include file="../include/includeFile.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<!-- handlebars cdn -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js" ></script>
<!-- 탬플릿 소스 -->
<script id="template_source" type="text/x-handlebars-template">
{{#each .}}
	<hr>
	{{#levelSpace relevel}} <!-- levelSpace : 헬퍼의 이름 -->
	{{/levelSpace}}
	<div id="reply{{rnum}}">
		번호 :{{rnum}} 
			restep: <span id="restep{{rnum}}">{{restep}}</span>  
			relevel: <span id="relevel{{rnum}}">{{relevel}}</span><br>
		이메일 : {{email}} <br>
		내용 : {{content}} <br> 
		<button class="btnReReplyAdd" value="{{rnum}}">댓글</button>
		<button class="btnReReplyModify" value="{{rnum}}">수정</button>
		<button class="btnReReplyRemove" value="{{rnum}}">삭제</button>
	</div>
{{/each}}
</script>

<script type="text/javascript">
	//핸들바의 헬퍼 작성
	//relevel(레벨)에 따라서 반복문 헬퍼
	Handlebars.registerHelper('levelSpace', function(relevel) {
		var result='';
		for(var i=0; i<relevel; i++){
			result += '*';
		}
		return result;
	});

	$(function() {
		//삭제버튼을 클릭했을때
		$('#btnRemove').on('click', function() {
			if (confirm('삭제하시겠습니까?'))
				location.href = '${path}/board/remove?bnum=${result.board.bnum}';
		});
		
		//수정버튼을 클릭했을때
		$('#btnModify').on('click', function() {
			location.href = '${path}/board/modify?bnum=${result.board.bnum}';
		});
		//좋아요를 클릭
		$('#likeUp').on('click', function() {
			var bnum = '${result.board.bnum}';
			console.log(bnum);
			$.ajax({
				url :'${path}/board/likecnt/'+ bnum,
				type:'put',
				dataType:'text', 
				success: function(data) {
					console.log(data);
					$('#likecnt').html(data);
					
				},
				error: function(e) {
					console.log(e);
					alert('실패');
				}
			});
		});
		
		//싫어요를 클릭
		$('#dislikeUp').on('click', function() {
			var bnum = '${result.board.bnum}';
			console.log(bnum);
			$.ajax({
				url :'${path}/board/dislikecnt/'+ bnum,
				type:'put',
				dataType:'text', //전송받을 데이터 타입
				success: function(data) {
					console.log(data);
					$('#dislikecnt').html(data);
					
				},
				error: function(e) {
					console.log(e);
					alert('실패');
				}
			});
		});
		//----------------------------------------------------------------
		//댓글 처리
		//댓글리스트 조회 함수
		function replyList() {
			//댓글창이 게시물 테이블 아래에 위치:댓글리스트 조회시 유지하기 위해서
			$('#divReply').insertAfter('#tblBoardDetail');
			$('#divReply').hide();
			
			var bnum = ${result.board.bnum};
			$.ajax({
				url:'${path}/reply/list/'+bnum,
				type:'get',
				dataType:'json', 
				success: function(data) {
					console.log(data);
					var source = $('#template_source').html();
					var template = Handlebars.compile(source); //template객체 생성
					console.log(template(data));
					$('#divReplyList').html(template(data));
				},
				error: function(e) {
					console.log(e);
					alert('실패');
				}				
			});
		}
		
		//댓글추가
		$('#btnReplyAdd').on('click', function() {
			var bnum = ${result.board.bnum}; //원본글(게시물)의 번호
			var email = $('#replyemail').val();
			var content = $('#replycontent').val();
			var restep = $('#restep').val();; //부모글순서
			var relevel = $('#relevel').val();; //부모글레벨
			if (email==''){
				alert('댓글작성자 이메일을 입력해 주세요');
				$('#replyemail').focus();
				return ; //함수의 종료
			}else if (content ==''){
				alert('댓글을 입력해 주세요');
				$('#replycontent').focus();
				return ; //함수의 종료
			}
			
			$.ajax({
				url:'${path}/reply/', //매핑url
				type:'post', //method방식:추가
				//json의 문자열로 변경
				data: JSON.stringify({bnum,email,content,restep,relevel}),//보낼 데이터 
				contentType:'application/json', //보낼 데이터 타입
				dataType:'text', //받을데이터 타입
				success: function(data) {
					console.log(data);
					//댓글리스트 
					replyList();
				},
				error: function(e) {
					console.log(e);
					alert('실패');
				}
			});
		});
		
		//댓글의 댓글 추가
		//동적으로 이벤트 달기
		//부모의 선택자로 자식의 엘리먼트에 이벤트를 달아준다
		$('#divReplyList').on('click', '.btnReReplyAdd', function() {
			//추가창 보이기
			//#divReply를 '#reply'+rnum 뒤로 이동
			var rnum = $(this).val();		
			$('#divReply').insertAfter('#reply'+rnum);
			$('#divReply').show();
			
			//restep 과 relevel을 읽어서 댓글창에 set(부모의 정보를 알기위해서)
			var restep = $('#restep'+rnum).text();
			var relevel = $('#relevel'+rnum).text();
			$('#restep').val(restep);
			$('#relevel').val(relevel);
			$('#replyemaul').val('')
			$('#replycontent').val('')
		});
		
		//원본글의 댓글을 클릭했을때
		$('#btnReply').on('click', function() {
			//게시물 테이블 아래에 위치
			$('#divReply').insertAfter('#tblBoardDetail'); 
			$('#divReply').show();
			$('#restep').val("0");
			$('#relevel').val("0");
			
		});
		
		//댓글의 삭제버튼을 클릭했을때
		$('#divReplyList').on('click','.btnReReplyRemove', function(){
			if (!confirm('삭제하시겠습니까?')){
				return ;
			}
			var rnum = $(this).val();
			alert(rnum);
			$.ajax({
				url:'${path}/reply/'+rnum,
				type:'delete', //삭제
				dataType:'text',
				success: function(data) {
					console.log(data);
					//댓글리스트 
					replyList();
				},
				error: function(e) {
					console.log(e);
					alert('실패');
				}
			});
		});
		//댓글의 수정버튼을 클릭했을때
		$('#divReplyList').on('click','.btnReReplyModify', function(){
			//수정창 보이기
			var rnum= $(this).val();
			$('#divReplyModify').insertAfter('#reply'+rnum);
			$('#divReplyModify').show();
			
			//rnum세팅
			$('#rnum').val(rnum);
			//content세팅
			$('#replycontentModify').val($('#content'+rnum).text());
		});
		
		//수정창의 수정버튼을 클릭했을때
		$('#btnReplyModify').on('click', function() {
			
			var rnum = $('#rnum').val();
			var content = $('#replycontentModify').val();
			console.log(rnum);
			console.log(content);
			$.ajax({
				url:'${path}/reply/'
				type:'put', //수정 method
				data:JSON.stringify({rnum,content}),
				contentType:'application/json',
				dataType:'text',
				success: function(data) {
					console.log(data);
					//댓글리스트 
					replyList();
				},
				error: function(e) {
					console.log(e);
					alert('실패');
				}
			});
		});
		
		
		//댓글 입력창 숨기기
		$('#divReply').hide();
		//댓글 수정창 숨기기
		$('#divReplyModify').hide();
		//댓글리스트 조회 함수 호출
		replyList();
	}); 	

</script>
</head>
<body>
<div class="container">
	<%@ include file="../header.jsp" %>
	<h2>상세조회</h2>
<%-- 	${result.board}
	<hr>
	${result.bflist} --%>
		
	<table id="tblBoardDetail">
		<tr>
			<th>번호</th>
			<td>${result.board.bnum}</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${result.board.email}</td>
		</tr>	
		<tr>
			<th>제목</th>
			<td>${result.board.subject}</td>
		</tr>	
		<tr>
			<th>내용</th>
			<td><pre>${result.board.content}</pre></td>
		</tr>	
		<tr>
			<th>파일</th>
			<td>
				<c:forEach var="boardfile" items="${result.bflist}">
					${boardfile.filename}<br>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				조회수:${result.board.readcnt}
				<i class="far fa-thumbs-up" id="likeUp"></i>
					<span id="likecnt">${result.board.likecnt}</span>
				<i class="far fa-thumbs-down" id="dislikeUp"></i>
					<span id="dislikecnt">${result.board.dislikecnt}</span>
			</td>
		</tr>		
		<tr>
			<th>등록일자</th>
			<td><fmt:formatDate value="${result.board.regidate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
		</tr>
		<tr>
			<th>수정일자</th>
			<td><fmt:formatDate value="${result.board.modidate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button id="btnModify">수정</button>
				<button id="btnRemove">삭제</button>
				<button id="btnReply">댓글</button>
			</td>
		</tr>
	</table>
	<!-- 댓글 입력 창-->
	<div class="card mb-2" id="divReply">
		<div class="card-header bg-light">
		        <i class="fa fa-comment fa"></i> REPLY 추가
		        restep : <input type="text" id="restep" size="3">
		        relevel : <input type="text" id="relevel" size="3">
		</div>
		<div class="card-body">
			<ul class="list-group list-group-flush">
			    <li class="list-group-item">
				<div class="form-inline mb-2">
					<label for="replyemail"><i class="fas fa-envelope"></i></label>
					<input type="email" class="form-control ml-2" placeholder="이메일을 입력...." id="replyemail">
				</div>
				<textarea class="form-control" id="replycontent" rows="3"></textarea>
				<button type="button" class="btn btn-dark mt-3" id="btnReplyAdd">추가</button>
			    </li>
			</ul>
		</div>
	</div>
	<!--댓글 수정창-->
		<div class="card mb-2" id="divReplyModify">
			<div class="card-header bg-light">
		        <i class="fa fa-comment fa"></i> REPLY 수정
		        rnum <input type="text" id="rnum"> 
		</div>
		<div class="card-body">
			<ul class="list-group list-group-flush">
			    <li class="list-group-item">
				<div class="form-inline mb-2">	
				</div>
				<textarea class="form-control" id="replycontentModify" rows="3"></textarea>
				<button type="button" class="btn btn-dark mt-3" id="btnReplyModify">수정</button>
			    </li>
			</ul>
		</div>
	</div>
	
	<!-- 댓글조회 -->
	<div id="divReplyList"></div>
	
</div>



</body>
</html>