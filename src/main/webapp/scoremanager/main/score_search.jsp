<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>成績参照検索</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="container mt-4">

    <h2>成績参照検索</h2>

    <form action="ScoreSearchExecute.action" method="post" class="mb-4">

        <div class="mb-3">
            <label class="form-label">入学年度</label>
            <input type="text" name="ent_year" class="form-control" />
        </div>

        <div class="mb-3">
            <label class="form-label">クラス</label>
            <input type="text" name="class_num" class="form-control" />
        </div>

        <div class="mb-3">
            <label class="form-label">科目コード</label>
            <input type="text" name="subject_cd" class="form-control" />
        </div>

        <div class="mb-3">
            <label class="form-label">回数</label>
            <input type="text" name="no" class="form-control" />
        </div>

        <button type="submit" class="btn btn-primary">検索</button>

    </form>

</body>
</html>
