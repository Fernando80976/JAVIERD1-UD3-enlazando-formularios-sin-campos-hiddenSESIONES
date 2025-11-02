package goya.daw2.pruebas_plantillas;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class FormsController {

    static final String[] SIGNOS = { "", "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio",
            "Sagitario", "Capricornio", "Acuario", "Piscis" };
    static final String[] AFICCIONES = { "Deportes", "Juerga", "Lectura", "Relaciones sociales" };

    @GetMapping("/")
    String etapa1() {
        return "etapa1";
    }

    @PostMapping("/")
    String procesaEtapaX(
            @RequestParam(name = "numEtapa") Integer numEtapa,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "signo", required = false) String signo,
            @RequestParam(name = "aficciones", required = false) String aficciones,
            HttpSession session,
            Model modelo) {

        // nombre
        if (nombre != null) {
            session.setAttribute("nombre", nombre);
        } else if (session.getAttribute("nombre") != null) {
            nombre = (String) session.getAttribute("nombre");
        }

        // signo
        if (signo != null) {
            session.setAttribute("signo", signo);
        } else if (session.getAttribute("signo") != null) {
            signo = (String) session.getAttribute("signo");
        }

        // aficciones
        if (aficciones != null) {
            session.setAttribute("aficciones", aficciones);
        } else if (session.getAttribute("aficciones") != null) {
            aficciones = (String) session.getAttribute("aficciones");
        }

        String errores = "";

        if (numEtapa == 1 && (nombre == null || nombre.isBlank())) {
            errores = "Debes poner un nombre no vacío";
        } else if (numEtapa == 1 && (nombre.length() < 3 || nombre.length() > 10)) {
            errores = "La longitud del nombre debe estar entre 3 y 10";
        }

        if (numEtapa == 2 && (signo == null || signo.equals("0"))) {
            errores = "Debes seleccionar un signo";
        }

        if (numEtapa == 3 && (aficciones == null || aficciones.isBlank())) {
            errores = "Debes elegir al menos una aficción, no seas soso/a";
        }

        modelo.addAttribute("signos", SIGNOS);
        modelo.addAttribute("aficciones", AFICCIONES);

        if (!errores.isBlank()) {
            modelo.addAttribute("errores", errores);
            modelo.addAttribute("numEtapa", numEtapa);
            return "etapa" + numEtapa;
        }

        numEtapa++;
        modelo.addAttribute("numEtapa", numEtapa);

        if (numEtapa == 4) {
            ArrayList<String> respuestas = new ArrayList<>();
            respuestas.add(nombre);
            respuestas.add(signo);  // <- usar directamente el valor del formulario
            respuestas.add(aficciones);
            modelo.addAttribute("respuestas", respuestas);

            // Limpiar sesión si quieres finalizar
            session.invalidate();
        }


        return "etapa" + numEtapa;
    }
}
