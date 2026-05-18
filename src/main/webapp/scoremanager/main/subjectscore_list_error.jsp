<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>成績一覧（科目）</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body {
        background-color: #f8f9fa;
    }
    .section-title {
        font-size: 1.4rem;
        font-weight: bold;
        margin-bottom: 1rem;
        border-left: 6px solid #0d6efd;
        padding-left: 10px;
    }
    .search-box {
        background: #fff;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 0 0 6px rgba(0,0,0,0.1);
        margin-bottom: 2rem;
    }
</style>

</head>

<body class="container mt-4">

    <h2 class="mb-4">成績一覧（科目）</h2>

    <!-- 科目情報 -->
    <div class="search-box">
        <div class="section-title">科目情報</div>

        <form action="SubjectScoreList.action" method="get">

            <div class="row mb-3">
                <div class="col-4">
                    <label class="form-label">入学年度</label>
                    <select name="ent_year" class="form-select">
                        <option value="2021">2021</option>
                        <option value="2022">2022</option>
                    </select>
                </div>

                <div class="col-4">
                    <label class="form-label">クラス</label>
                    <select name="class_num" class="form-select">
                        <option value="201">201</option>
                        <option value="202">202</option>
                    </select>
                </div>

                <div class="col-4">
                    <label class="form-label">科目</label>
                    <select name="subject_cd" class="form-select">
                        <option value="1001">情報処理基礎知識Ⅰ</option>
                        <option value="1002">情報処理基礎知識Ⅱ</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn btn-secondary">検索</button>

        </form>
    </div>

    <!-- 学生情報 -->
    <div class="search-box">
        <div class="section-title">学生情報</div>

        <form action="SubjectScoreList.action" method="get">

            <div class="row mb-3">
                <div class="col-6">
                    <label class="form-label">学生番号を入力してください</label>
                    <input type="text" name="student_no" class="form-control">
                </div>
            </div>

            <button type="submit" class="btn btn-secondary">検索</button>

        </form>
    </div>

</body>
</html>
