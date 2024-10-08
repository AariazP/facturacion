package org.facturacion.facturacion.controllers;

import lombok.AllArgsConstructor;
import org.facturacion.facturacion.dto.cliente.ActualizarClienteDTO;
import org.facturacion.facturacion.dto.cliente.ClienteDTO;
import org.facturacion.facturacion.dto.cliente.CrearClienteDTO;
import org.facturacion.facturacion.services.specification.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>>  listarClientes(){
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<ClienteDTO> obtenerClienteCedula(@PathVariable String cedula){
        return ResponseEntity.ok(clienteService.obtenerClientePorCedula(cedula));
    }

    @GetMapping("/verificar-cliente/{cedula}")
    public ResponseEntity<Boolean> verificarSiExiteCliente(@PathVariable String cedula){
        return ResponseEntity.ok(clienteService.verificarSiExiteCliente(cedula));
    }

    @PostMapping("/guardar")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody CrearClienteDTO crearClienteDTO){
        return ResponseEntity.ok(clienteService.crearCliente(crearClienteDTO));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@RequestBody ActualizarClienteDTO clienteDTO, @PathVariable Integer id){
        return ResponseEntity.ok(clienteService.actualizarCliente(clienteDTO, id));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminarCliente(@PathVariable Integer id){
        return ResponseEntity.ok(clienteService.eliminarCliente(id));
    }

    @GetMapping("/verificar-eliminado/{cedula}")
    public ResponseEntity<Boolean> verificarEliminado(@PathVariable String cedula){
        return ResponseEntity.ok(clienteService.verificarEliminado(cedula));
    }

    @GetMapping("/recuperar-cliente/{cedula}")
    public void recuperarCliente(@PathVariable String cedula){
        clienteService.recuperarCliente(cedula);
    }

}
