package serverManger.servlet;
import Defenitions.WorldDefinitionDTO;
import com.google.gson.Gson;
import engine.api.Engine;
import engine.impl.EngineImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet(name = "loadFile", urlPatterns = "/loadFile")
public class LoadFile extends HttpServlet {

    Map<String,WorldDefinitionDTO> worldsDTO = new LinkedHashMap<>();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Engine engine = new EngineImpl();
        getServletContext().setAttribute("engine", engine);
        String filePath = req.getParameter("filePath");
        Gson gson = new Gson();

        try {
            if (filePath != null && !filePath.isEmpty()) {
                engine.loadXmlFiles(filePath);
                WorldDefinitionDTO worldDefinitionDTO = engine.getWorldDefinitionDTO();
                String jsonRes = gson.toJson(worldDefinitionDTO);
                worldsDTO.put(worldDefinitionDTO.getSimulationName(),worldDefinitionDTO);
                try(PrintWriter out = res.getWriter()){
                    out.print(jsonRes);
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
