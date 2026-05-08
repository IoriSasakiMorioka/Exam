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
            int no,
            String schoolCd) throws Exception {

        List<SubjectScore> list = new ArrayList<>();

        String sql =
            "SELECT " +
            "  s.NO AS student_no, " +
            "  s.NAME AS student_name, " +
            "  t.POINT AS point " +
            "FROM STUDENT s " +
            "LEFT JOIN TEST t " +
            "  ON s.NO = t.STUDENT_NO " +
            " AND t.SUBJECT_CD = ? " +
            " AND t.NO = ? " +
            " AND t.SCHOOL_CD = ? " +
            "WHERE s.ENT_YEAR = ? " +
            "  AND s.CLASS_NUM = ? " +
            "  AND s.SCHOOL_CD = ? " +
            "ORDER BY s.NO";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            ps.setString(i++, subjectCd);
            ps.setInt(i++, no);
            ps.setString(i++, schoolCd);
            ps.setInt(i++, entYear);
            ps.setString(i++, classNum);
            ps.setString(i++, schoolCd);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubjectScore s = new SubjectScore();
                s.setStudentNo(rs.getString("student_no"));
                s.setStudentName(rs.getString("student_name"));
                int point = rs.getInt("point");
                if (rs.wasNull()) {
                    s.setPoint(null);
                } else {
                    s.setPoint(point);
                }
                list.add(s);
            }
        }
        return list;
    }
}
