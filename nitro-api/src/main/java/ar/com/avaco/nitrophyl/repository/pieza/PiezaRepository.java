package ar.com.avaco.nitrophyl.repository.pieza;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.com.avaco.fwk.core.component.repository.NJRepository;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.dto.PiezaComboDTO;

public interface PiezaRepository extends NJRepository<Long, Pieza>, PiezaRepositoryCustom {

	Pieza findByCodigoAndVigenteAndDetalleFormulaFormulaId(String codigoInterno, boolean b, Long idFormula);

	boolean existsByDetalleFormulaFormulaId(Long idFormula);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Pieza p SET p.faltantes = ?2 where p.id = ?1")
	void actualizarFaltantes(Long idPieza, String faltantes);

	@Query("SELECT DISTINCT new ar.com.avaco.nitrophyl.dto.PiezaComboDTO(" +
		       "p.id, p.denominacion, f.nombre, p.codigo, pc.nombrePiezaPersonalizado) " +
		       "FROM Pieza p " +
		       "LEFT JOIN p.detalleFormula.formula f " +
		       "LEFT JOIN p.clientes pc " +
		       "WHERE p.vigente = true " +
		       "AND (" +
		       ":nombre IS NULL " +
		       "OR LOWER(p.denominacion) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
		       "OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
		       "OR LOWER(f.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
		       "OR LOWER(pc.nombrePiezaPersonalizado) LIKE LOWER(CONCAT('%', :nombre, '%'))" +
		       ") " +
		       "AND (:soloDelCliente = false OR :idCliente IS NULL OR pc.cliente.id = :idCliente) " +
		       "ORDER BY p.denominacion")
	List<PiezaComboDTO> listPiezasCombo(
	    @Param("nombre") String nombre,
	    @Param("idCliente") Long idCliente,
	    @Param("soloDelCliente") boolean soloDelCliente);

	boolean existsByCodigoAndDetalleFormulaFormulaId(String codigo, Long idMaterial);

}
