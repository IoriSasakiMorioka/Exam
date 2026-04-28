package tool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.beans.editors.IntegerEditor;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;

public class Util {

	public Teacher getUser(HttpServletRequest req) throws Exception{
		HttpSession session = req.getSession();
		return (Teacher)session.getAttribute("user");
	}

	public void setClassNumSet(HttpServletRequest req) throws Exception{
		ClassNumDao cDao = new ClassNumDao();
		List<String> list = cDao.filter(getUser(req).getSchool());
		req.setAttribute("class_num_set", list);
		return;
	}

	public void setEntYearSet(HttpServletRequest req) throws Exception{
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後までをリストに追加
		for (int i = year - 10; i < year + 11; i++){
			entYearSet.add(i);
		}
		req.setAttribute("ent_year_set", entYearSet);
		
	}

	public void setSubjects(HttpServletRequest req) throws Exception{
		SubjectDao sDao = new SubjectDao();
		List<Subject> list = sDao.filter(getUser(req).getSchool(),true);
		req.setAttribute("subjects", list);
		return;
	}

	public void setNumSet(HttpServletRequest req) throws Exception{
		List<Integer> numSet = new ArrayList<>();
		numSet.add(1);
		numSet.add(2);
		req.setAttribute("num_set", numSet);
	}
}
