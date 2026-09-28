package ar.com.avaco.nitrophyl.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTrabajoResponseDTO {

	private CabeceraOrdenTabajoDTO cabecera;
	private List<ItemOrdenTrabajoDTO> items;

}
