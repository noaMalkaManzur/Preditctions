package adminManager.servlet;
import engine.api.Engine;
import engine.impl.EngineImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SetThreadPoolSizeServlet", urlPatterns = "/ThreadPool")
public class ThreadPoolServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String threadPoolParam = request.getParameter("threadPool");
        Engine engine = (Engine) getServletContext().getAttribute("engine");

        if (engine == null) {
            engine = new EngineImpl();
            getServletContext().setAttribute("engine", engine);
        }

        if (threadPoolParam != null) {
            try {
                int ThreadPoolSize = Integer.parseInt(threadPoolParam);
                engine.setThreadsAmount(ThreadPoolSize);
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid Thread amount");
            }
        } else {

            response.getWriter().write("Thread amounts parameter is missing");
        }
    }
}
