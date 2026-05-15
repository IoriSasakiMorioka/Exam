package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
import tool.Util;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String f1 = req.getParameter("f1");
		String f2 = req.getParameter("f2");
		String f3 = req.getParameter("f3");
		String f4 = req.getParameter("f4");

		Map<String, String> errors = new HashMap<>();
		Map<String, String> pointMap = new HashMap<>();

		Util util = new Util();
		util.setEntYearSet(req);
		util.setClassNumSet(req);
		util.setSubjects(req);
		util.setNumSet(req);

		boolean allFilled = f1 != null && !f1.isEmpty() && !"0".equals(f1)
				&& f2 != null && !f2.isEmpty() && !"0".equals(f2)
				&& f3 != null && !f3.isEmpty()
				&& f4 != null && !f4.isEmpty() && !"0".equals(f4);

		if (!allFilled) {
			errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
			setFormAttributes(req, f1, f2, f3, f4, errors, null, pointMap);
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		int entYear = Integer.parseInt(f1);
		int testNo = Integer.parseInt(f4);

		SubjectDao subjectDao = new SubjectDao();
		Subject subject = subjectDao.get(f3, teacher.getSchool());
		if (subject == null) {
			errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
			setFormAttributes(req, f1, f2, f3, f4, errors, null, pointMap);
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		TestDao testDao = new TestDao();
		List<Test> tests = testDao.filter(entYear, f2, subject, testNo, teacher.getSchool());

		boolean hasPointError = false;
		for (Test row : tests) {
			String no = row.getStudent().getNo();
			String raw = req.getParameter("point_" + no);
			pointMap.put(no, raw);

			if (raw == null || raw.isEmpty()) {
				errors.put(no, "0～100の範囲で入力してください");
				hasPointError = true;
				continue;
			}
			try {
				int p = Integer.parseInt(raw.trim());
				if (p < 0 || p > 100) {
					errors.put(no, "0～100の範囲で入力してください");
					hasPointError = true;
				}
			} catch (NumberFormatException e) {
				errors.put(no, "0～100の範囲で入力してください");
				hasPointError = true;
			}
		}

		if (hasPointError) {
			setFormAttributes(req, f1, f2, f3, f4, errors, tests, pointMap);
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}

		List<Test> toSave = new ArrayList<>();
		for (Test row : tests) {
			String no = row.getStudent().getNo();
			int p = Integer.parseInt(req.getParameter("point_" + no).trim());
			Test t = new Test();
			t.setStudent(row.getStudent());
			t.setClassNum(row.getClassNum());
			t.setSubject(subject);
			t.setSchool(teacher.getSchool());
			t.setNo(testNo);
			t.setPoint(p);
			toSave.add(t);
		}

		testDao.save(toSave);
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}

	private void setFormAttributes(
			HttpServletRequest req,
			String f1,
			String f2,
			String f3,
			String f4,
			Map<String, String> errors,
			List<Test> tests,
			Map<String, String> pointMap) {

		if (f1 != null && !f1.isEmpty() && !"0".equals(f1)) {
			req.setAttribute("f1", Integer.parseInt(f1));
		} else {
			req.setAttribute("f1", 0);
		}
		req.setAttribute("f2", f2 != null ? f2 : "0");
		req.setAttribute("f3", f3 != null ? f3 : "");
		if (f4 != null && !f4.isEmpty()) {
			req.setAttribute("f4", Integer.parseInt(f4));
		} else {
			req.setAttribute("f4", 0);
		}
		req.setAttribute("errors", errors);
		req.setAttribute("point_map", pointMap != null ? pointMap : new HashMap<String, String>());
		if (tests != null) {
			req.setAttribute("tests", tests);
		}
	}
}
