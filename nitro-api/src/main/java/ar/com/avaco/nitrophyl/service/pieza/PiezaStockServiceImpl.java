package ar.com.avaco.nitrophyl.service.pieza;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.avaco.fwk.core.component.service.NJBaseService;
import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.nitrophyl.domain.entities.pieza.Pieza;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStock;
import ar.com.avaco.nitrophyl.domain.entities.pieza.PiezaStockLote;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaRepository;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaStockRepository;
import ar.com.avaco.nitrophyl.repository.pieza.PiezaStockCabeceraRepository;

@Service("piezaStockService")
public class PiezaStockServiceImpl extends NJBaseService<Long, PiezaStockLote, PiezaStockRepository>
		implements PiezaStockService {

	@Autowired
	private PiezaRepository piezaRepository;
	@Autowired
	private PiezaStockCabeceraRepository cabeceraRepository;

	@Resource(name = "piezaStockRepository")
	void setPiezaStockRepository(PiezaStockRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public PiezaStockLote ingresar(Long idPieza, String lotes, Integer cantidad) {
		
		// Validaciones de valores
		if (cantidad == null || cantidad <= 0)
			throw new ErrorValidationException("La cantidad ingresada debe ser mayor a cero", null);
		
		// Obtengo la pieza bloqueada de escritura exclusiva
		Pieza pieza = piezaRepository.bloquearParaStock(idPieza)
				.orElseThrow(() -> new ErrorValidationException("No existe la pieza indicada", null));
		
		// Armo la firma de lotes
		String firma = normalizarLotes(lotes);
		
		// Obtengo la cabecera
		PiezaStock cabecera = cabeceraRepository.buscarPorPiezaParaActualizar(idPieza).orElse(null);
		if (cabecera == null) {
			// Si no existe la creo
			cabecera = cabeceraRepository.save(PiezaStock.builder().pieza(pieza).stockFisico(0).stockReservado(0).build());
		}
		
		// Busco la pieza stock por lote
		PiezaStockLote saldo = repository.buscarExactoParaActualizar(idPieza, firma).orElse(null);
		if (saldo == null) {
			// Si no existe la creo
			saldo = PiezaStockLote.builder().piezaStock(cabecera).firmaLotes(firma)
					.stockFisico(0).stockReservado(0).build();
		}
		// Ajusto los stocks de la cabecera y detalle por lote
		saldo.incrementarStockFisico(cantidad);
		cabecera.incrementarStockFisico(cantidad);
		cabeceraRepository.save(cabecera);
		return this.save(saldo);
	}

	@Override
	public PiezaStock obtenerCabecera(Long idPieza) {
		return cabeceraRepository.findByPiezaId(idPieza).orElse(null);
	}

	@Override
	@Transactional
	public void modificarStockReservado(Long idPiezaStockLote, Integer variacion) {
		aplicarVariaciones(idPiezaStockLote, 0, variacion);
	}

	@Override
	@Transactional
	public void aplicarVariaciones(Long idPiezaStockLote, Integer variacionFisico, Integer variacionReservado) {
		PiezaStockLote saldo = repository.buscarPorIdParaActualizar(idPiezaStockLote)
				.orElseThrow(() -> new ErrorValidationException("No existe el saldo de stock indicado", null));
		PiezaStock cabecera = cabeceraRepository.buscarPorPiezaParaActualizar(saldo.getPiezaStock().getPieza().getId())
				.orElseThrow(() -> new ErrorValidationException("No existe la cabecera de stock de la pieza", null));
		int fisico = variacionFisico == null ? 0 : variacionFisico;
		int reservado = variacionReservado == null ? 0 : variacionReservado;
		int nuevoFisico = saldo.getStockFisico() + fisico;
		int nuevoReservado = saldo.getStockReservado() + reservado;
		if (nuevoFisico < 0 || nuevoReservado < 0 || nuevoReservado > nuevoFisico)
			throw new ErrorValidationException("Las variaciones dejarían un saldo físico o reservado inválido", null);
		saldo.setStockFisico(nuevoFisico);
		saldo.setStockReservado(nuevoReservado);
		cabecera.setStockFisico(cabecera.getStockFisico() + fisico);
		cabecera.setStockReservado(cabecera.getStockReservado() + reservado);
		if (cabecera.getStockFisico() < 0 || cabecera.getStockReservado() < 0
				|| cabecera.getStockReservado() > cabecera.getStockFisico())
			throw new ErrorValidationException("Las variaciones dejarían un total de stock de pieza inválido", null);
		this.update(saldo);
		cabeceraRepository.save(cabecera);
	}

	@Override
	@Transactional
	public PiezaStockLote buscar(Long idPieza, String lotes) {
		return repository.buscarExactoParaActualizar(idPieza, normalizarLotes(lotes))
				.orElseThrow(() -> new ErrorValidationException("No existe el grupo de stock de origen", null));
	}

	@Override
	public List<PiezaStockLote> listarPorPieza(Long idPieza) {
		return repository.listarPorPieza(idPieza);
	}

	@Override
	public List<PiezaStockLote> listarPorLote(String codigoLote) {
		return repository.listarPorLote(codigoLote);
	}

	public static String normalizarLotes(String lotes) {
		if (lotes == null || lotes.trim().isEmpty()) return "";
		return java.util.Arrays.stream(lotes.split(","))
				.map(String::trim).filter(s -> !s.isEmpty()).distinct().sorted()
				.collect(java.util.stream.Collectors.joining(","));
	}
}
