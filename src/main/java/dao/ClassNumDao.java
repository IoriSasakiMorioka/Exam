package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class ClassNumDao extends Dao{

	public List<String> filter(School school) {
		List<String> list = new ArrayList<String>();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String cd = school.getCd();
		try{ 
			Connection connection = getConnection();
			statement = connection.prepareStatement("select * from class_num where school_cd = ?");
			statement.setString(1, cd);
			rSet = statement.executeQuery();
			while(rSet.next()){
				String num = rSet.getString("class_num");
				list.add(num);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean save(School school,String[] classNums)throws Exception {
		PreparedStatement statement = null;
		String cd = school.getCd();
		int count = 0;
		try {
			Connection connection = getConnection();
			for (String class_num : classNums){
				if(!class_num.equals("")){
				statement = connection.prepareStatement(
						"insert into class_num(school_cd,class_num)values(?,?)");
				statement.setString(1, cd);
				statement.setString(2, class_num);
				count += statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			statement.close();
		}
		if(count == classNums.length){
			return true;
		}else{
			return false;
		}
	}

}
