package ar.com.avaco.nitrophyl.dto;

import java.time.LocalDateTime;
import ar.com.avaco.nitrophyl.domain.entities.fabricacion.TipoEventoOrdenCompra;
import lombok.Data;

@Data
public class OrdenCompraHistorialDTO {
	private Long id;
	private TipoEventoOrdenCompra tipoEvento;
	private String tipoEntidad;
	private Long idEntidad;
	private String motivo;
	private String detalle;
	private LocalDateTime fechaEvento;
}
