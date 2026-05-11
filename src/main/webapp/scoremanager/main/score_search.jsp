<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/common/base.jsp">
    <jsp:param name="title" value="成績参照検索" />
</jsp:include>

<div class="container">

    <h2>成績参照検索</h2>

    <c:if test="${not empty message}">
        <div class="error">${message}</div>
    </c:if>

    <form action="ScoreSearchExecute.action" method="post">

        <div>
            <label>入学年度</label>
            <input type="text" name="ent_year" />
            <!-- 本来はセレクトボックスにしてもOK -->
        </div>

        <div>
            <label>クラス</label>
            <input type="text" name="class_num" />
        </div>

        <div>
            <label>科目コード</label>
            <input type="text" name="subject_cd" />
        </div>

        <div>
            <label>回数</label>
            <input type="text" name="no" />
        </div>

        <div>
            <button type="submit">検索</button>
        </div>

    </form>

</div>
