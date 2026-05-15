<%-- 成績管理一覧（成績登録） test_regist.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="my-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理一覧</h2>

			<form method="get" action="TestRegist.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-3">
						<label class="form-label" for="f1">入学年度</label>
						<select class="form-select" id="f1" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="f2">クラス</label>
						<select class="form-select" id="f2" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="f3">科目</label>
						<select class="form-select" id="f3" name="f3">
							<option value="">--------</option>
							<c:forEach var="subject" items="${subjects}">
								<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="f4">回数</label>
						<select class="form-select" id="f4" name="f4">
							<option value="0">--------</option>
							<c:forEach var="num" items="${num_set}">
								<option value="${num}" <c:if test="${num == f4}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-1 text-center mt-4">
						<button class="btn btn-secondary" type="submit">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("filter")}</div>
				</div>
			</form>

			<c:if test="${not empty tests}">
				<form method="post" action="TestRegistExecute.action">
					<input type="hidden" name="f1" value="${f1}">
					<input type="hidden" name="f2" value="${f2}">
					<input type="hidden" name="f3" value="${f3}">
					<input type="hidden" name="f4" value="${f4}">

					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
						</tr>
						<c:forEach var="row" items="${tests}">
							<tr>
								<td>${row.student.entYear}</td>
								<td>${row.student.classNum}</td>
								<td>${row.student.no}</td>
								<td>${row.student.name}</td>
								<td>
									<c:choose>
										<c:when test="${not empty point_map[row.student.no]}">
											<input type="number" class="form-control" name="point_${row.student.no}"
												min="0" max="100" value="${point_map[row.student.no]}">
										</c:when>
										<c:otherwise>
											<input type="number" class="form-control" name="point_${row.student.no}"
												min="0" max="100" value="${row.point}">
										</c:otherwise>
									</c:choose>
									<div class="text-warning">${errors.get(row.student.no)}</div>
								</td>
							</tr>
						</c:forEach>
					</table>

					<div class="text-end my-3">
						<button class="btn btn-primary" type="submit">登録して終了</button>
					</div>
				</form>
			</c:if>
		</section>
	</c:param>
</c:import>
