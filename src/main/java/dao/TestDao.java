package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.SubjectScore;
import bean.Test;

public class TestDao extends Dao {

	/**
	 * 入学年度・クラス・科目・回数に該当する学生と成績（未登録は 0）を取得する。
	 */
	public List<Test> filter(int entYear, String classNum, Subject subject, int testNo, School school)
			throws Exception {

		List<Test> list = new ArrayList<>();

		String sql =
			"SELECT "
				+ " s.no AS student_no, "
				+ " s.name AS name, "
				+ " s.ent_year AS ent_year, "
				+ " s.class_num AS class_num, "
				+ " s.is_attend AS is_attend, "
				+ " s.school_cd AS school_cd, "
				+ " COALESCE(t.point, 0) AS point "
				+ "FROM student s "
				+ "LEFT JOIN test t ON s.no = t.student_no "
				+ " AND t.subject_cd = ? "
				+ " AND t.school_cd = ? "
				+ " AND t.no = ? "
				+ "WHERE s.ent_year = ? "
				+ " AND s.class_num = ? "
				+ " AND s.school_cd = ? "
				+ "ORDER BY s.no";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			int i = 1;
			ps.setString(i++, subject.getCd());
			ps.setString(i++, school.getCd());
			ps.setInt(i++, testNo);
			ps.setInt(i++, entYear);
			ps.setString(i++, classNum);
			ps.setString(i++, school.getCd());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Student st = new Student();
				st.setNo(rs.getString("student_no"));
				st.setName(rs.getString("name"));
				st.setEntYear(rs.getInt("ent_year"));
				st.setClassNum(rs.getString("class_num"));
				st.setAttend(rs.getBoolean("is_attend"));
				st.setSchool(school);

				Test test = new Test();
				test.setStudent(st);
				test.setClassNum(st.getClassNum());
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(testNo);
				test.setPoint(rs.getInt("POINT"));
				list.add(test);
			}
		}
		return list;
	}

	/**
	 * 成績を一括保存する（MERGE）。
	 */
	public boolean save(List<Test> tests) throws Exception {
		if (tests == null || tests.isEmpty()) {
			return true;
		}

		String sql =
			"merge into test key(school_cd, student_no, subject_cd, no) "
				+ "values(?, ?, ?, ?, ?)";

		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			for (Test t : tests) {
				ps.setString(1, t.getSchool().getCd());
				ps.setString(2, t.getStudent().getNo());
				ps.setString(3, t.getSubject().getCd());
				ps.setInt(4, t.getNo());
				ps.setInt(5, t.getPoint());
				ps.addBatch();
			}
			ps.executeBatch();
		}
		return true;
	}

    public List<SubjectScore> findSubjectScores(
            int entYear,
            String classNum,
            String subjectCd,
            String schoolCd) throws Exception {

        List<SubjectScore> list = new ArrayList<>();

        String sql =
            "SELECT " +
            " s.ENT_YEAR, " +
            " s.CLASS_NUM, " +
            " s.NO AS STUDENT_NO, " +
            " s.NAME AS STUDENT_NAME, " +
            " COALESCE(t1.POINT, 0) AS POINT1, " +
            " COALESCE(t2.POINT, 0) AS POINT2 " +
            "FROM STUDENT s " +
            "LEFT JOIN TEST t1 ON s.NO = t1.STUDENT_NO " +
            " AND t1.NO = 1 " +
            " AND t1.SUBJECT_CD = ? " +
            " AND t1.SCHOOL_CD = ? " +
            "LEFT JOIN TEST t2 ON s.NO = t2.STUDENT_NO " +
            " AND t2.NO = 2 " +
            " AND t2.SUBJECT_CD = ? " +
            " AND t2.SCHOOL_CD = ? " +
            "WHERE s.ENT_YEAR = ? " +
            " AND s.CLASS_NUM = ? " +
            " AND s.SCHOOL_CD = ? " +
            "ORDER BY s.NO";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            ps.setString(i++, subjectCd); // t1.SUBJECT_CD
            ps.setString(i++, schoolCd);  // t1.SCHOOL_CD
            ps.setString(i++, subjectCd); // t2.SUBJECT_CD
            ps.setString(i++, schoolCd);  // t2.SCHOOL_CD
            ps.setInt(i++, entYear);
            ps.setString(i++, classNum);
            ps.setString(i++, schoolCd);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubjectScore s = new SubjectScore();
                s.setEntYear(rs.getInt("ENT_YEAR"));
                s.setClassNum(rs.getString("CLASS_NUM"));
                s.setStudentNo(rs.getString("STUDENT_NO"));
                s.setStudentName(rs.getString("STUDENT_NAME"));
                s.setPoint1(rs.getInt("POINT1"));
                s.setPoint2(rs.getInt("POINT2"));
                list.add(s);
            }
        }
        return list;
    }
}
