package com.rest.prueba_rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="*", maxAge = 3600)
@RestController
@RequestMapping("/user")

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static List<Cliente> clientes = new ArrayList<>();
    static {
        clientes.add(new Cliente(
                "C",
                23445322,
                "Cesar",
                "Geovani",
                "Castillo",
                "Lozano",
                3001234567L,
                "Centro",
                "Bogota"
        ));
//        clientes.add(new Cliente(
//                "C",
//                87654321,
//                "Carla",
//                "María",
//                "Sánchez",
//                "López",
//                3012345678L,
//                "Sur",
//                "Cali"
//        ));
    }
    @GetMapping("/info")
    public ResponseEntity<Cliente> getClienteInfo(@RequestParam String tipoDocumento, @RequestParam String numeroDocumento) {
        logger.info("Solicitud recibida para tipoDocumento: {} y numeroDocumento: {}", tipoDocumento, numeroDocumento);
        try {
            if (tipoDocumento == null || numeroDocumento == null || tipoDocumento.isEmpty() || numeroDocumento.isEmpty()) {
                logger.error("Solicitud inválida: tipoDocumento o numeroDocumento está vacío");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (!tipoDocumento.equals("C") && !tipoDocumento.equals("P")) {
                logger.error("Tipo de documento inválido: {}", tipoDocumento);
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            }
            Cliente usuario = null;
            for (Cliente cliente : clientes) {
                if (cliente.getTipoDocumento().equals(tipoDocumento) && cliente.getNumeroDocumento() == Integer.parseInt(numeroDocumento)) {
                    usuario = cliente;
                    break;
                }
            }
            if (usuario == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error interno del servidor", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
