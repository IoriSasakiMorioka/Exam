package scoremanager.main;

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

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		Util util = new Util();
		util.setEntYearSet(req);
		util.setClassNumSet(req);
		util.setSubjects(req);
		util.setNumSet(req);

		String f1 = req.getParameter("f1");
		String f2 = req.getParameter("f2");
		String f3 = req.getParameter("f3");
		String f4 = req.getParameter("f4");

		Map<String, String> errors = new HashMap<>();

		boolean hasAny = hasMeaningfulValue(f1, true)
				|| hasMeaningfulValue(f2, true)
				|| hasMeaningfulValue(f3, false)
				|| hasMeaningfulValue(f4, true);

		boolean allFilled = hasMeaningfulValue(f1, true)
				&& hasMeaningfulValue(f2, true)
				&& hasMeaningfulValue(f3, false)
				&& hasMeaningfulValue(f4, true);

		if (hasAny && !allFilled) {
			errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
		} else if (allFilled) {
			int entYear = Integer.parseInt(f1);
			int testNo = Integer.parseInt(f4);

			SubjectDao subjectDao = new SubjectDao();
			Subject subject = subjectDao.get(f3, teacher.getSchool());
			if (subject == null) {
				errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
			} else {
				TestDao testDao = new TestDao();
				List<Test> tests = testDao.filter(entYear, f2, subject, testNo, teacher.getSchool());
				req.setAttribute("tests", tests);
			}
		}

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
		req.setAttribute("point_map", new HashMap<String, String>());

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}

	private boolean hasMeaningfulValue(String s, boolean treatZeroAsEmpty) {
		if (s == null || s.isEmpty()) {
			return false;
		}
		if (treatZeroAsEmpty && "0".equals(s)) {
			return false;
		}
		return true;
	}
}