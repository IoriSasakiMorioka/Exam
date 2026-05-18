package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            // 例: /scoremanager/main/ScoreSearch.action
            String path = req.getServletPath().substring(1);

            // 例: scoremanager.main.ScoreSearchAction
            String name = path.replace(".action", "Action").replace('/', '.');

            // Actionクラスを生成
            Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

            // Action実行
            action.execute(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }
}
