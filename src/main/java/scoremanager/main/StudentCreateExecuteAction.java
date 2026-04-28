package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {
 
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr = "";
		String studentNo = "";
		String studentName = "";
		String classNum = "";
		int entYear = 0;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();
		boolean error = true;

		entYearStr = req.getParameter("ent_year");
		studentNo = req.getParameter("no");
		studentName = req.getParameter("name");
		classNum = req.getParameter("class_num");
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		//リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// ビジネスロック
		if (entYearStr != null){
			entYear = Integer.parseInt(entYearStr);
		}
		// 10年前から1年後までをリストに追加
		for (int i = year - 10; i < year + 10; i++){
			entYearSet.add(i);
		}
		List<String> list = cNumDao.filter(teacher.getSchool());

		if (entYearStr.equals("0")){
			errors.put("f1", "入学年度を指定してください");
			req.setAttribute("errors", errors);
			req.setAttribute("no", studentNo);
			req.setAttribute("name", studentName);
			req.setAttribute("class_num", classNum);
			req.setAttribute("class_num_set", list);
			req.setAttribute("ent_year_set", entYearSet);
			req.getRequestDispatcher("student_create.jsp").forward(req, res);
			return;
		}else{
		try{
			Student student = new Student();
			student = sDao.get(studentNo);
			if (student != null){
				errors.put("f2", "学生番号が重複しています");
				req.setAttribute("errors", errors);
				req.setAttribute("ent_year", entYearStr);
				req.setAttribute("no", studentNo);
				req.setAttribute("name", studentName);
				req.setAttribute("class_num", classNum);
				req.setAttribute("class_num_set", list);
				req.setAttribute("ent_year_set", entYearSet);
				req.getRequestDispatcher("student_create.jsp").forward(req, res);
				return;
			}else {
				Student newStudent = new Student();
				newStudent.setNo(studentNo);
				newStudent.setName(studentName);
				newStudent.setEntYear(entYear);
				newStudent.setClassNum(classNum);
				newStudent.setAttend(true);
				newStudent.setSchool(teacher.getSchool());
				error = sDao.save(newStudent);
			}
		}catch (Exception e) {
			throw e;
		}
		req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
		}
	}
}
