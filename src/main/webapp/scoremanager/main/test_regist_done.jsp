<%-- 成績登録完了 test_regist_done.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="my-4 text-center">
			<h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績登録完了</h2>
			<p class="mb-4">成績の登録が完了しました。</p>
			<a class="btn btn-primary me-2" href="TestRegist.action">成績管理一覧へ</a>
			<a class="btn btn-outline-secondary" href="Menu.action">メニューへ</a>
		</section>
	</c:param>
</c:import>
