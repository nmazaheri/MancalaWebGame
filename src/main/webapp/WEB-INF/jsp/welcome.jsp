<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
	<%--<c:url value="/resources/text.txt" var="url"/>--%>
	<%--<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />--%>
	<%--Spring URL: ${springUrl} at ${time}--%>
	<%--<br>--%>
	<%--JSTL URL: ${url}--%>
	<%--<br>--%>
	<%--Message: ${message}--%>
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<link href="${mainCss}" rel="stylesheet" />

	<div id="gameboard">
		<img src="resources/images/leftScore.jpg" />
		<div id="playerSection">
			<img src="resources/images/topBoard.jpg" />
			<img src="resources/images/topBoard.jpg" />
			<img src="resources/images/topBoard.jpg" />
			<img src="resources/images/topBoard.jpg" />
			<img src="resources/images/topBoard.jpg" />
			<img src="resources/images/topBoard.jpg" />
		</div>

		<div id="playerSection">
			<img src="resources/images/bottomBoard.jpg" />
			<img src="resources/images/bottomBoard.jpg" />
			<img src="resources/images/bottomBoard.jpg" />
			<img src="resources/images/bottomBoard.jpg" />
			<img src="resources/images/bottomBoard.jpg" />
			<img src="resources/images/bottomBoard.jpg" />
		</div>
		<img src="resources/images/rightScore.jpg" />
	</div>
</body>

</html>
