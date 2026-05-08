package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.SubjectScore;

public class TestDao extends Dao {

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
