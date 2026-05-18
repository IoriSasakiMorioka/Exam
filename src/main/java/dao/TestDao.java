package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao{

	private String baseSql = "select * from test";

	public Test get(Student student,Subject subject,School school,int no) throws Exception{
		Test test = new Test();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(
					"select * from test where"
					+ "student_no=? and subject_cd=? and "
					+ "school_cd =? and no = ?");
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			
			ResultSet rSet = statement.executeQuery();
			
			if(rSet.next()){
				test.setStudent(student);
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(no);
				test.setPoint(rSet.getInt("point"));
			}else{
				test = null;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
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
		return test;
	}
	
	private List<Test> postFilter(ResultSet rSet,School school)throws Exception {
		List<Test> list = new ArrayList<>();
		try{
			// リザルトセットを全権操作
			while(rSet.next()){
				Test test = new Test();
				Student student = new Student();
				Subject subject = new Subject();
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("student_no"));
				student.setName(rSet.getString("student_name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setSchool(school);
				subject.setCd(rSet.getString("subject_cd"));
				subject.setName(rSet.getString("subject_name"));
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setClassNum(rSet.getString("class_num"));
				test.setNo(rSet.getInt("num"));
				test.setPoint(rSet.getInt("point"));
				
				// リストに追加
				list.add(test);
			}

		}catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			list = null;
		}

		return list;
	}

	public List<Test> filter(int entYear,String classNum,Subject subject,
			int num, School school) throws Exception{
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String condition = ""
				+ "select t.student_no,"
				+ "st.ent_year, "
				+ "st.name as student_name, "
				+ "s.cd,t.subject_cd,"
				+ "s.name as subject_name, "
				+ "st.name as student_name, "
				+ "t.no as num, "
				+ "t.point,"
				+ "t.class_num,"
				+ "t.school_cd "
				+ "from test as t "
				+ "join subject as s on t.subject_cd = s.cd and t.school_cd = s.school_cd "
				+ "join student as st on t.student_no = st.no "
				+ "where st.ent_year = ? "
				+ "and t.class_num = ? "
				+ "and subject_cd = ? "
				+ "and t.no = ? ";
		String order = " order by st.no ";

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(condition + order );
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);
			if(list.isEmpty()){
				list = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
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

	public boolean save(List<Test> list) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		
		for (Test test:list){
			save(test,connection);
		}
		connection.close();
		return true;
	}
	
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
		int count = 0;
		try{
			statement = connection.prepareStatement(
					"merge into test key(student_no,subject_cd,school_cd,no)values(?,?,?,?,?,?)");
			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getCd());
			statement.setString(3, test.getSchool().getCd());
			statement.setInt(4, test.getNo());
			statement.setInt(5, test.getPoint());
			statement.setString(6, test.getClassNum());
			count = statement.executeUpdate();
		}catch (Exception e) {
			throw e;
		}
		
		statement.close();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

}
