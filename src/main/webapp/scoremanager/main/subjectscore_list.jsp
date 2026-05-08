<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/common/base.jsp">
    <jsp:param name="title" value="成績一覧（科目）" />
</jsp:include>

<div class="container">

    <h2>成績一覧（科目）</h2>

    <div>
        入学年度：${entYear}<br>
        クラス：${classNum}<br>
        科目：${subjectName}
    </div>

    <c:if test="${empty scoreList}">
        <div class="error-message">
            学生情報が存在しませんでした。
        </div>
    </c:if>

    <c:if test="${not empty scoreList}">
        <table border="1">
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>1回</th>
                <th>2回</th>
            </tr>

            <c:forEach var="s" items="${scoreList}">
                <tr>
                    <td>${s.entYear}</td>
                    <td>${s.classNum}</td>
                    <td>${s.studentNo}</td>
                    <td>${s.studentName}</td>

                    <td>
                        <c:choose>
                            <c:when test="${s.point1 == 0}">－</c:when>
                            <c:otherwise>${s.point1}</c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${s.point2 == 0}">－</c:when>
                            <c:otherwise>${s.point2}</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </c:if>

    <div>
        <a href="ScoreSearch.action">検索画面に戻る</a>
    </div>

</div>
