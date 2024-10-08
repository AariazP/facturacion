package org.facturacion.facturacion.services.implementation;

import lombok.AllArgsConstructor;
import org.facturacion.facturacion.domain.*;
import org.facturacion.facturacion.dto.factura.FacturaItemDTO;
import org.facturacion.facturacion.dto.factura.CrearFacturaDTO;
import org.facturacion.facturacion.dto.factura.DetalleFacturaDTO;
import org.facturacion.facturacion.dto.factura.FacturaDTO;
import org.facturacion.facturacion.exceptions.cliente.ClienteNoExisteException;
import org.facturacion.facturacion.exceptions.producto.ProductoCantidadException;
import org.facturacion.facturacion.repositories.FacturaRepository;
import org.facturacion.facturacion.services.specification.ClienteService;
import org.facturacion.facturacion.services.specification.FacturaService;
import org.facturacion.facturacion.services.specification.ProductoService;
import org.facturacion.facturacion.services.specification.UsuarioService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IFacturaService implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final IDetalleFacturaService detalleFacturaService;
    private final Double IVA = 0.19;

    @Override
    public Integer obtenerSiguienteId() {
        return facturaRepository.obtenerSiguienteId();
    }

    @Override
    public FacturaDTO guardarFactura(CrearFacturaDTO facturaDTO) {

        Factura factura = new Factura();
        factura.setDetalleFacturaList(new ArrayList<>());

        agregarDetalleFactura(factura, facturaDTO);
        factura.setTotal(factura.getDetalleFacturaList().stream().mapToDouble(DetalleFactura::getValor).sum());

        agregarClienteFactura(factura, facturaDTO);
        agregarUsuarioFactura(factura, facturaDTO);
        factura.setFecha(new java.util.Date());

        factura.setSubTotal(factura.getTotal() - factura.getTotal() * IVA);
        facturaRepository.save(factura);
        factura.getDetalleFacturaList().forEach(this.detalleFacturaService::save);

        return FacturaDTO.fromEntity(factura);
    }

    private void agregarDetalleFactura(Factura factura, CrearFacturaDTO facturaDTO){
        facturaDTO.listDetalleFactura().forEach(detalle -> {
            DetalleFactura detalleFactura = DetalleFacturaDTO.toEntity(detalle);
            Producto producto = productoService.findById(detalle.codigoProducto());

            if(producto.getStock() < detalle.cantidad()){
                throw new ProductoCantidadException("No hay suficiente cantidad del producto "+ producto.getNombre()+ "para la factura");
            }else producto.setStock(producto.getStock() - detalle.cantidad());


            detalleFactura.setValor(producto.getPrecio() * detalle.cantidad());

            detalleFactura.setProducto(producto);
            detalleFactura.setFactura(factura);
            factura.getDetalleFacturaList().add(detalleFactura);
        });
    }


    private void agregarUsuarioFactura(Factura factura, CrearFacturaDTO facturaDTO){
        Usuario usuario = this.usuarioService.findById(facturaDTO.usuario());

        if(usuario == null){
            throw new ClienteNoExisteException("No se ha encontrado el usuario con el id "+ facturaDTO.usuario());
        }
        factura.setUsuario(usuario);
    }


    private void agregarClienteFactura(Factura factura, CrearFacturaDTO facturaDTO){
        Cliente cliente = this.clienteService.findByCedula(facturaDTO.cliente());
        if(cliente == null){
            throw new ClienteNoExisteException("No se ha encontrado el cliente con la cedula "+ facturaDTO.cliente());
        }
        factura.setCliente(cliente);
    }


    @Override
    public List<FacturaItemDTO> obtenerFacturas() {
        return facturaRepository.findAll().stream().map(FacturaItemDTO::fromEntity).toList();
    }


}
