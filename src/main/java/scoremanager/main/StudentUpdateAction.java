package scoremanager.main;

import java.util.List;

import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String No = req.getParameter("no");
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(No);
		ClassNumDao cDao = new ClassNumDao();
		List<String> list = cDao.filter(student.getSchool());
		req.setAttribute("student", student);
		req.setAttribute("class_num_set", list);

		req.getRequestDispatcher("student_update.jsp").forward(req, res);


	}
}
