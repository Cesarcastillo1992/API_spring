package com.rest.prueba_rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/info")
    public ResponseEntity<Cliente> getClienteInfo(@RequestParam String tipoDocumento, @RequestParam String numeroDocumento) {
        logger.info("Solicitud recibida para tipoDocumento: {} y numeroDocumento: {}", tipoDocumento, numeroDocumento);
        try {
            if (tipoDocumento == null || numeroDocumento == null || tipoDocumento.isEmpty() || numeroDocumento.isEmpty()) {
                logger.error("Solicitud inválida: tipoDocumento o numeroDocumento está vacío");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (!tipoDocumento.equals("C") && !tipoDocumento.equals("P")) {
                logger.error("Tipo de documento inválido: {}", tipoDocumento);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (!numeroDocumento.equals("23445322") || !tipoDocumento.equals("C")) {
                logger.warn("Cliente no encontrado para numeroDocumento: {}", numeroDocumento);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Cliente usuario = new Cliente(
                "Cesar",
                "Geovani",
                "Castillo",
                "Lozano",
                3001234567L,
                "Centro",
                "Bogota"
            );
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
