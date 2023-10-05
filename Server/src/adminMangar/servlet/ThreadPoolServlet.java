package adminMangar.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ThreadPoolServlet extends HttpServlet {
    @WebServlet(name = "SetThreadPoolSizeServlet", urlPatterns = "/ThreadPool")
    public class LoginServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String threadpoolParam = request.getParameter("threadpool");

            if (threadpoolParam != null) {
                try {
                    int ThreadPoolSize = Integer.parseInt(threadpoolParam);
                    response.getWriter().write("ThreadPoolSize is set to: " + ThreadPoolSize);
                } catch (NumberFormatException e) {
                    response.getWriter().write("Invalid 'threadpool' parameter");
                }
            } else {

                response.getWriter().write("'threadpool' parameter is missing");
            }
        }
    }
}
