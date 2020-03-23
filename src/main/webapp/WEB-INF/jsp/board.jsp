<%--suppress HtmlRequiredAltAttribute --%>
<%--Renders game board--%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<spring:url value="resources/css/style.css" var="mainCss"/>
<link href="${mainCss}" rel="stylesheet"/>

<body>
<h1>Mancala Game</h1>

<table>
    <tr>
        <%-- player one score --%>
        <td>
            <div class="pit middle">${pitStones[0]}</div>
            <img src="resources/images/leftScore.jpg"/>
        </td>

        <%-- main board --%>
        <td>
            <table>
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/1"><img
                                src="resources/images/topBoard.jpg"/>
                            <div class="pit top">${pitStones[1]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/2"><img
                                src="resources/images/topBoard.jpg"/>
                            <div class="pit top">${pitStones[2]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/3"><img
                                src="resources/images/topBoard.jpg"/>
                            <div class="pit top">${pitStones[3]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/4"><img
                                src="resources/images/topBoard.jpg"/>
                            <div class="pit top">${pitStones[4]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/5"><img
                                src="resources/images/topBoard.jpg"/>
                            <div class="pit top">${pitStones[5]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/6"><img
                                src="resources/images/topBoard.jpg"/>
                            <div class="pit top">${pitStones[6]}</div>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/13"><img
                                src="resources/images/bottomBoard.jpg"/>
                            <div class="pit bottom">${pitStones[13]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/12"><img
                                src="resources/images/bottomBoard.jpg"/>
                            <div class="pit bottom">${pitStones[12]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/11"><img
                                src="resources/images/bottomBoard.jpg"/>
                            <div class="pit bottom">${pitStones[11]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/10"><img
                                src="resources/images/bottomBoard.jpg"/>
                            <div class="pit bottom">${pitStones[10]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/9"><img
                                src="resources/images/bottomBoard.jpg"/>
                            <div class="pit bottom">${pitStones[9]}</div>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/input/8"><img
                                src="resources/images/bottomBoard.jpg"/>
                            <div class="pit bottom">${pitStones[8]}</div>
                        </a>
                    </td>
                </tr>
            </table>
        </td>

        <%-- player two score --%>
        <td>
            <div class="pit middle">${pitStones[7]}</div>
            <img src="resources/images/rightScore.jpg"/>
        </td>
    </tr>
</table>

<br>
<c:choose>
    <c:when test="${gameMessage != null}">
        <h2 class="message">${gameMessage}</h2>
    </c:when>
    <c:otherwise>
        <p>Current Player: ${currentPlayer}</p>
    </c:otherwise>
</c:choose>
</body>
</html>