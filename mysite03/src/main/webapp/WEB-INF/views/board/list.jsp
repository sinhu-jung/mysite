<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value=${keyword }>
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th style="text-align:left">제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(map.list) }"/>
					<c:forEach var="vo" items="${map.list }" varStatus="status">
						<c:choose>
							<c:when test='${vo.depth == 0 }'>
								<tr>
									<td>${count-status.index }</td>
									<td style="text-align:left; padding-left:0px">
									<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }&hit=${vo.hit}">
									${vo.title }</a></td>
									<td>${vo.userName }</td>
									<td>${vo.hit }</td>
									<td>${vo.regDate }</td>
									<c:if test="${vo.userNo == authUser.no }">
										<td><a href="${pageContext.servletContext.contextPath }/board/delete?userNo=${vo.userNo}&no=${vo.no}" class="del"
										style='background-image:url("/mysite03/assets/images/recycle.png")'>삭제</a></td>
									</c:if>
								</tr>
							</c:when>
							<c:when test='${vo.depth >= 1 }'>
								<tr>
									<td>${count-status.index }</td>
									<td style="text-align:left; padding-left:${vo.depth * 20 }px">
									<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'/>
									<a href="${pageContext.servletContext.contextPath }/board/view?no=${vo.no }&hit=${vo.hit}">
									${vo.title }</a></td>
									<td>${vo.userName }</td>
									<td>${vo.hit }</td>
									<td>${vo.regDate }</td>
									<c:if test="${vo.userNo == authUser.no }">
										<td><a href="${pageContext.servletContext.contextPath }/board/delete?userNo=${vo.userNo}&no=${vo.no}" class="del" 
										style='background-image:url("/mysite03/assets/images/recycle.png")'>삭제</a></td>
									</c:if>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>				
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${map.prevPage > 0 }" >
							<li><a href="${pageContext.request.contextPath }/board?p=${map.prevPage }&kwd=${map.keyword }">◀</a></li>
						</c:if>
						
						<c:forEach begin="${map.beginPage }" end="${map.beginPage + map.listSize - 1 }" var="page">
							<c:choose>
								<c:when test="${map.endPage < page }">
									<li>${page }</li>
								</c:when> 
								<c:when test="${map.currentPage == page }">
									<li class="selected">${page }</li>
								</c:when>
								<c:otherwise> 
									<li><a href="${pageContext.request.contextPath }/board?p=${page }&kwd=${map.keyword }">${page }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${map.nextPage > 0 }" >
							<li><a href="${pageContext.request.contextPath }/board?p=${map.nextPage }&kwd=${map.keyword }">▶</a></li>
						</c:if>	
					</ul>
				</div>					
				
				<div class="bottom">
					<c:if test="${not empty authUser }">
						<a href="${pageContext.servletContext.contextPath }/board/write?msg=write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>