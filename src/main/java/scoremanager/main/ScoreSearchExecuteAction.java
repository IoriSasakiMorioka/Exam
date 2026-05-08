package scoremanager.main;

import java.util.List;

import bean.SubjectScore;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ScoreSearchExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        req.setCharacterEncoding("UTF-8");

        String entYearStr = req.getParameter("ent_year");
        String classNum   = req.getParameter("class_num");
        String subjectCd  = req.getParameter("subject_cd");
        String noStr      = req.getParameter("no");

        // ログインユーザから学校コードを取得
        // Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        // String schoolCd = teacher.getSchool();
        String schoolCd = "oom"; // 仮置き

        // 入力チェック
        if (entYearStr == null || entYearStr.isEmpty()
                || classNum == null || classNum.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()
                || noStr == null || noStr.isEmpty()) {

            req.setAttribute("message", "入学年度とクラスと科目と回数を選択してください");
            req.getRequestDispatcher("score_search.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);
        int no = Integer.parseInt(noStr);

        TestDao dao = new TestDao();
        List<SubjectScore> list =
                dao.findSubjectScores(entYear, classNum, subjectCd, no, schoolCd);

        req.setAttribute("scoreList", list);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subjectCd", subjectCd);
        req.setAttribute("no", no);

        req.getRequestDispatcher("subject_score_list.jsp").forward(req, res);
    }
}
