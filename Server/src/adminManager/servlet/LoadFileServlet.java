package adminManager.servlet;
import Defenitions.WorldDefinitionDTO;
import com.google.gson.Gson;
import engine.api.Engine;
import engine.impl.EngineImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "LoadFileServlet", urlPatterns = "/loadFile")
public class LoadFileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        ServletContext context = getServletContext();
        Engine engine = (Engine) context.getAttribute("engine");

        if (engine == null) {
            engine = new EngineImpl();
            context.setAttribute("engine", engine);
        }
        String filePath = req.getParameter("filePath");
        try {
            if (filePath != null && !filePath.isEmpty()) {
                engine.loadXmlFiles(filePath);
                try (PrintWriter out = res.getWriter()) {
                    out.print("File Loaded Successfully");
                    out.flush();
                }
            } else {
                res.sendError(400, "No file path was provided.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(400, "Error processing the request: " + e.getMessage());
        }
    }
}