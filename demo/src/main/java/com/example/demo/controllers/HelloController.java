package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.Usuario;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/primerSpring")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(Model model) {
        Usuario usuario = new Usuario("Fabian", "Veschi");
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Aplicacion en spring");
        return "vista";
    }

    @ModelAttribute(name = "lista")
    public List<Usuario> lista() {
        Usuario usuario1 = new Usuario("Fabian", "Veschi");
        Usuario usuario2 = new Usuario("Juan", "Perez");
        Usuario usuario3 = new Usuario("Pedro", "Aznar");
        usuario3.setEmail("mail@mail.com");
        return Arrays.asList(usuario1, usuario2, usuario3);
    }

    @GetMapping("/parametros")
    public String parametros(Model model,
                             @RequestParam(required = false, defaultValue = "sin clave", name = "clave") String password,
                             @RequestParam(required = false, defaultValue = "sin usuario") String usuario) {
        model.addAttribute("titulo", "Aplicacion en spring");
        model.addAttribute("clave", password);
        model.addAttribute("login", usuario);
        return "vista";
    }

    @GetMapping("/parametros-request")
    public String parametrosRequest(Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Aplicacion en spring");
        model.addAttribute("clave", request.getParameter("clave"));
        model.addAttribute("login", request.getParameter("usuario"));
        return "vista";
    }

    @GetMapping("/parametros/{name}")
    public String path(Model model, @PathVariable String name) {
        model.addAttribute("titulo", "Aplicacion en spring");
        model.addAttribute("login", name);
        return "vista";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public Usuario guardar(@RequestBody Usuario usuario) {
        System.out.println(usuario.getApellido() + " " + usuario.getNombre());
        return usuario;
    }
}

