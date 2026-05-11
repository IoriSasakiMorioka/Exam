<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/common/base.jsp">
    <jsp:param name="title" value="成績参照" />
</jsp:include>

<div class="container">

    <h2>成績参照</h2>

    <!-- 検索条件不足エラー -->
    <c:if test="${not empty message}">
        <div class="error-message">
            ${message}
        </div>
    </c:if>

    <!-- 検索フォーム -->
    <form action="ScoreSearchExecute.action" method="post">
        <div>
            <label>入学年度</label>
            <input type="text" name="ent_year" value="${entYear}" />
        </div>
        <div>
            <label>クラス</label>
            <input type="text" name="class_num" value="${classNum}" />
        </div>
        <div>
            <label>科目</label>
            <input type="text" name="subject_cd" value="${subjectCd}" />
        </div>
        <div>
            <label>学生番号</label>
            <input type="text" name="student_no" value="${studentNo}" />
        </div>
        <div>
            <button type="submit">検索</button>
        </div>
    </form>

</div>
