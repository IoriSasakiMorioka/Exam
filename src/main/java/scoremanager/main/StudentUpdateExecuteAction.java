package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		int entYear = Integer.parseInt(req.getParameter("ent_year"));
		String No = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("class_num");
		boolean isAttend = false;
		isAttend = Boolean.parseBoolean(req.getParameter("is_attend"));

		StudentDao sDao = new StudentDao();
		Student student = sDao.get(No);
		student.setNo(No);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		sDao.save(student);

		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);


	}
}
