package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{
	private String baseSql = ""
			+ "select s.name as subject_name, t.subject_cd,t.no, t.point " 
			+ "from test as t "
			+ "join subject as s on t.subject_cd = s.cd and t.school_cd = s.school_cd "
			+ "where t.student_no = ? "
			+ "and t.school_cd = ? "
			+ "order by s.cd";
	

	private List<TestListStudent> postfilter(ResultSet rSet)throws Exception{
		List<TestListStudent> list = new ArrayList<>();
		try{
			while(rSet.next()){
				TestListStudent tStudent = new TestListStudent();
				tStudent.setSubjectName(rSet.getString("subject_name"));
				tStudent.setSubjectCd(rSet.getString("subject_cd"));
				tStudent.setNum(rSet.getInt("no"));
				tStudent.setPoint(rSet.getInt("point"));
				list.add(tStudent);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		return list;

	}

	public List<TestListStudent> filter(Student student)throws Exception{
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try{
			statement = connection.prepareStatement(
					baseSql);
			statement.setString(1, student.getNo());
			statement.setString(2, student.getSchool().getCd());
			rSet = statement.executeQuery();
			list = postfilter(rSet);
		}catch (Exception e) {
			return list;
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
