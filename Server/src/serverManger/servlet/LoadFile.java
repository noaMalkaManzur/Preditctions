package serverManger.servlet;
import Defenitions.WorldDefinitionDTO;
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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "loadFile", urlPatterns = "/loadFile")
public class LoadFile extends HttpServlet {

    Map<String,WorldDefinitionDTO> worldsDTO = new LinkedHashMap<>();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Engine engine = new EngineImpl();
        getServletContext().setAttribute("engine", engine);
        String filePath = req.getParameter("filePath");

        try {
            if (filePath != null && !filePath.isEmpty()) {
                engine.loadXmlFiles(filePath);
                WorldDefinitionDTO worldDefinitionDTO = engine.getWorldDefinitionDTO();
                //worldsDTO.put(worldDefinitionDTO.getName(),worldDefinitionDTO);
            } else {
                res.sendError(400, "No file path was provided.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(400, "Error processing the request: " + e.getMessage());
        }
    }
}
