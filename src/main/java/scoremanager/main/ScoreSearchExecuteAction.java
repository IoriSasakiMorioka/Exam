package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.SubjectScore;
import bean.Teacher;
import dao.SubjectDao;
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

        // 入力チェック（画面設計書の通り）
        if (entYearStr == null || entYearStr.isEmpty()
                || classNum == null || classNum.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()) {

            req.setAttribute("message", "入学年度とクラスと科目を選択してください");
            req.getRequestDispatcher("score_search.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        // ログインユーザの学校コード
        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        String schoolCd = teacher.getSchool();

        // 科目名を取得（画面設計書に必要）
        SubjectDao sdao = new SubjectDao();
        Subject subject = sdao.get(subjectCd, schoolCd);
        String subjectName = subject.getName();

        // 成績一覧を取得
        TestDao dao = new TestDao();
        List<SubjectScore> list =
                dao.findSubjectScores(entYear, classNum, subjectCd, schoolCd);

        // JSP に渡す値
        req.setAttribute("scoreList", list);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subjectCd", subjectCd);
        req.setAttribute("subjectName", subjectName);

        // 科目別成績一覧画面へ
        req.getRequestDispatcher("subject_score_list.jsp").forward(req, res);
    }
}
