package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		ClassNumDao cNumDao = new ClassNumDao();

		//DBからデータ取得
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		//リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後までをリストに追加
		for (int i = year - 10; i < year + 11; i++){
			entYearSet.add(i);
		}
		List<String> list = cNumDao.filter(teacher.getSchool());

		// リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);

		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}
