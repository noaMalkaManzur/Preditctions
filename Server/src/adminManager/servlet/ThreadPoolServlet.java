package adminManager.servlet;
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
        String threadpoolParam = request.getParameter("threadpool");

        if (threadpoolParam != null) {
            try {
                int ThreadPoolSize = Integer.parseInt(threadpoolParam);
                try(PrintWriter out = response.getWriter()){
                    out.print("ThreadPoolSize is set to: " + ThreadPoolSize);
                    out.flush();
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("Invalid 'threadpool' parameter");
            }
        } else {

            response.getWriter().write("'threadpool' parameter is missing");
        }
    }
}
