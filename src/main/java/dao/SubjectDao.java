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

public class SubjectDao extends Dao{

	public Subject get(String cd,School school) throws Exception {
		Subject subject = new Subject();
		subject.setSchool(school);
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd=? and cd=?");
			// プリペアードステートメントに学生番号をバインド
			statement.setString(1, school.getCd());
			statement.setString(2, cd);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()){
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
			}else {
				subject = null;
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
		return subject;
	}

	public List<Subject> filter(School school,boolean filter) throws Exception{
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement(
			"select * from subject where school_cd=? and is_true = ?");
			statement.setString(1, school.getCd());
			statement.setBoolean(2, filter);
			rSet = statement.executeQuery();
			while(rSet.next()){
				Subject subject = new Subject();
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				list.add(subject);
			}
		} catch (Exception e) {
			throw e;
		} finally {
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

	
	public boolean save(Subject subject) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {

			statement = connection.prepareStatement(
					"merge into subject key(school_cd,cd) values(?,?,?,?) ");
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());
			statement.setString(3, subject.getName());
			statement.setBoolean(4, true);
			
			// プリペアードステートメントを実行
			count = statement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
			statement.close();
			connection.close();
			return false;
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

		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	public boolean delete(Subject subject) throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		try{
			statement = connection.prepareStatement(
				"update subject set is_true = ? where school_cd = ? and cd = ?");
			statement.setBoolean(1, false);
			statement.setString(2, subject.getSchool().getCd());
			statement.setString(3, subject.getCd());
			count = statement.executeUpdate();
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
		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
	
	public boolean change(String cd,boolean back) throws Exception{
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try{
			statement = connection.prepareStatement(
					"update subject set is_true = true where cd = ?");
			statement.setString(1, cd);
			statement.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
			statement.close();
			connection.close();
		}
		
		return true;
	}
}
