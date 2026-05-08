package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ScoreSearchAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 必要なら年度リストや科目リストをセットする
        // req.setAttribute("ent_year_set", ...);
        // req.setAttribute("subject_list", ...);

        req.getRequestDispatcher("score_search.jsp").forward(req, res);
    }
}
