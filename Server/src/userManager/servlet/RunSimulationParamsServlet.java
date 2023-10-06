package userManager.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import userManager.servlet.Api.ParametersToSimulation;

import java.io.IOException;
import java.io.PrintWriter;

import static constants.Constants.COUNTER;
import static constants.Constants.SIMULATIONNAME;


@WebServlet(name = "RunSimulationServlet", urlPatterns = "/runSimulationParams")

public class RunSimulationParamsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        try {
            String simulationName = request.getParameter(SIMULATIONNAME);
            int numSimulationToRun = Integer.parseInt(request.getParameter(COUNTER));
            ParametersToSimulation parameters = new ParametersToSimulation(simulationName, numSimulationToRun);
            //engine.checkIf
            String paramsJson = gson.toJson(parameters);

            try (PrintWriter out = response.getWriter()) {
                out.print(paramsJson);
                out.flush();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("Invalid input. Please provide valid parameters.");
        }

    }
}
