<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/common/base.jsp">
    <jsp:param name="title" value="科目別成績一覧" />
</jsp:include>

<div class="container">

    <h2>科目別成績一覧</h2>

    <!-- 検索条件の表示-->
    <div class="search-condition">
        <p>入学年度：${entYear}</p>
        <p>クラス：${classNum}</p>
        <p>科目：${subjectName}</p>
    </div>

    <!-- 学生情報が存在しない場合 -->
    <c:if test="${empty scoreList}">
        <div class="no-data">
            学生情報が存在しませんでした
        </div>
    </c:if>

    <!-- 一覧表示 -->
    <c:if test="${not empty scoreList}">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>得点</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${scoreList}">
                    <tr>
                        <td>${s.studentNo}</td>
                        <td>${s.studentName}</td>

                        <td>
                            <c:choose>
                                <c:when test="${s.point == null}">
                                    -
                                </c:when>
                                <c:otherwise>
                                    ${s.point}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="back-link">
        <a href="ScoreSearch.action">検索画面に戻る</a>
    </div>

</div>
