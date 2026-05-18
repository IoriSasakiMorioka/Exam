package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.javafx.css.CssError.InlineStyleParsingError;

import java.util.HashMap;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListStudent;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	private String baseSql = ""
			+ "select st.ent_year, t.class_num, t.student_no, st.name, t.no, t.point "
			+ "from test as t "
			+ "join student as st on t.student_no = st.no and t.class_num = st.class_num "
			+ "where st.ent_year = ? "
			+ "and t.class_num = ? "
			+ "and t.subject_cd = ? "
			+ "order by st.no";

	private List<TestListSubject> postfilter(ResultSet rSet)throws Exception{
		List<TestListSubject> list = new ArrayList<>();
		String student_no = "";
		TestListSubject tSubject = null;
		try{
			while(rSet.next()){
				if(!rSet.getString("student_no").equals(student_no)){
					if(tSubject != null){
						list.add(tSubject);
					}
					Map<Integer, Integer> map = new HashMap<>();
					student_no = rSet.getString("student_no");
					tSubject = new TestListSubject();
					tSubject.setPoints(map);
					tSubject.setEntYear(rSet.getInt("ent_year"));
					tSubject.setStudentNo(rSet.getString("student_no"));
					tSubject.setStudentName(rSet.getString("name"));
					tSubject.setClassNum(rSet.getString("class_num"));
					tSubject.putPoint(rSet.getInt("no"), rSet.getInt("point"));
				}
				tSubject.putPoint(rSet.getInt("no"), rSet.getInt("point"));
			}
			if(!student_no.equals("")){
				list.add(tSubject);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public List<TestListSubject> filter(int entYear,String classNum,
			Subject subject, School school)throws Exception{
		List<TestListSubject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try{
			statement = connection.prepareStatement(
					baseSql);
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			rSet = statement.executeQuery();
			list = postfilter(rSet);
		}catch (Exception e) {
			throw e;
		}finally {
			// プリペアードステートメントを閉じる
			if (statement !=null) {
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null){
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}
