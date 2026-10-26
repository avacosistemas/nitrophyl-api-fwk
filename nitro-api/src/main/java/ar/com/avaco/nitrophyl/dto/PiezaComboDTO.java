/**
 * 
 */
package ar.com.avaco.nitrophyl.dto;

public class PiezaComboDTO {

	private Long id;

	private String denominacion;

	private String formula;

	private String codigo;

	private String codigoCliente;

	public PiezaComboDTO(Long id, String denominacion, String formula, String codigo, String codigoCliente) {
		super();
		this.id = id;
		this.denominacion = denominacion;
		this.codigo = codigo;
		this.formula = formula;
		this.codigoCliente = codigoCliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

}
