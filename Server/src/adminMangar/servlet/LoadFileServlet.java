package adminMangar.servlet;
import Defenitions.WorldDefinitionDTO;
import com.google.gson.Gson;
import engine.api.Engine;
import engine.impl.EngineImpl;
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
    private Engine engine;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        if(engine == null){
            engine = new EngineImpl();
            //getServletContext().setAttribute("engine", engine);
        }
        String filePath = req.getParameter("filePath");
        Gson gson = new Gson();

        try {
            if (filePath != null && !filePath.isEmpty()) {
                engine.loadXmlFiles(filePath);
                //Map<String,WorldDefinitionDTO> worldsDefinitionDTO = engine.getWorldsDefinitionDTO();
                //String jsonRes = gson.toJson(worldDefinitionDTO);
                try(PrintWriter out = res.getWriter()){
                    out.print("file Loaded Successfully");
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
