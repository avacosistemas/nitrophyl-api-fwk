package ar.com.avaco.nitrophyl.informe;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import ar.com.avaco.fwk.core.exception.ErrorValidationException;
import ar.com.avaco.fwk.core.utils.DateUtils;
import ar.com.avaco.nitrophyl.domain.entities.formula.ConfiguracionPrueba;
import ar.com.avaco.nitrophyl.domain.entities.formula.ConfiguracionPruebaCondicion;
import ar.com.avaco.nitrophyl.domain.entities.lote.Ensayo;
import ar.com.avaco.nitrophyl.domain.entities.lote.EnsayoResultado;
import ar.com.avaco.nitrophyl.domain.entities.lote.Lote;
import ar.com.avaco.nitrophyl.domain.entities.reporte.ReporteLoteConfiguracionCliente;
import ar.com.avaco.nitrophyl.informe.PDFUtils.RoundRectangle;

public class InformeCalidadBuilder {

	private static final ClassLoader CLASS_LOADER = new InformeCalidadBuilder().getClass().getClassLoader();

	public static void agregarObservacionesInforme(Document document, String string) throws DocumentException {
		PdfPCell cellBorder = new PdfPCell();
		cellBorder.setCellEvent(new RoundRectangle());
		cellBorder.setBorder(Rectangle.NO_BORDER);
		cellBorder.setPadding(10f);
		cellBorder.setPaddingTop(0);
		cellBorder.addElement(new Phrase(string, PDFUtils.fontText));

		PdfPTable tableBorder = new PdfPTable(1);
		tableBorder.setWidthPercentage(100);
		tableBorder.addCell(cellBorder);

		document.add(tableBorder);
	}

	public static Ensayo generarEnsayoVacio(ConfiguracionPrueba parametro) {
		Ensayo ensayo = new Ensayo();
		ensayo.setConfiguracionPrueba(parametro);
		parametro.getParametros().forEach(x -> {
			EnsayoResultado er = new EnsayoResultado();
			er.setConfiguracionPruebaParametro(x);
			ensayo.getResultados().add(er);
		});
		return ensayo;
	}

	public static Map<Long, Ensayo> generarMapaEnsayos(Set<Ensayo> ensayos) {
		Map<Long, Ensayo> mapa = new HashMap<Long, Ensayo>();
		ensayos.forEach(x -> {
			mapa.put(x.getConfiguracionPrueba().getMaquina().getId(), x);
		});
		return mapa;
	}

	public static Element generarEncabezado(String empresa, Long revision, String fecha)
			throws BadElementException, MalformedURLException, IOException, URISyntaxException, DocumentException {
		PdfPTable table = PDFUtils.generateTable(3);
		PdfPCell cell = PDFUtils.getPDFPCell();
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		URL resource = null;
		if (empresa.equals("NITROPHYL")) {
			resource = CLASS_LOADER.getResource("nitro-logo.jpg");
		} else {
			resource = CLASS_LOADER.getResource("elasint-logo.jpg");
		}
		cell.addElement(Image.getInstance(new File(resource.toURI()).getAbsolutePath()));
		table.addCell(cell);
		cell.setPhrase(
				new Phrase("INFORME DE CALIDAD", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK)));
		table.addCell(cell);

		PdfPTable tabladerecha = PDFUtils.generateTable(2);
		tabladerecha.setWidths(new float[] { 35, 65 });

		PdfPCell fila = PDFUtils.getPDFPCell();
		fila.setPhrase(new Phrase("Fecha: ", PDFUtils.fontHeaderTable));
		fila.setBorder(0);
		tabladerecha.addCell(fila);

		fila = PDFUtils.getPDFPCell();
		fila.setPhrase(new Phrase("05/12/2024", PDFUtils.fontText));
		fila.setBorder(0);
		tabladerecha.addCell(fila);

		fila = PDFUtils.getPDFPCell();
		fila.setPhrase(new Phrase("Cdigo:", PDFUtils.fontHeaderTable));
		fila.setBorder(0);
		tabladerecha.addCell(fila);

		fila = PDFUtils.getPDFPCell();
		fila.setPhrase(new Phrase("R-LAB-006", PDFUtils.fontText));
		fila.setBorder(0);
		tabladerecha.addCell(fila);

		fila = PDFUtils.getPDFPCell();
		fila.setPhrase(new Phrase("Rev.: ", PDFUtils.fontHeaderTable));
		fila.setBorder(0);
		tabladerecha.addCell(fila);

		fila = PDFUtils.getPDFPCell();
		fila.setPhrase(new Phrase(revision + " - " + fecha, PDFUtils.fontText));
		fila.setBorder(0);
		tabladerecha.addCell(fila);

		PdfPCell celdatabladerecha = PDFUtils.generarCeldaBordeRedondeado(null, null);
		celdatabladerecha.addElement(tabladerecha);
		celdatabladerecha.setBorder(0);

		table.addCell(celdatabladerecha);

		Paragraph p = new Paragraph();
		p.add(table);

		return p;
	}

	public static void generarFirma(Document document)
			throws BadElementException, MalformedURLException, IOException, URISyntaxException, DocumentException {
		PdfPTable table = PDFUtils.generateTable(2);
		PdfPCell cell = PDFUtils.getPDFPCell();
		cell.setBorder(0);

		URL resource = new InformeCalidadBuilder().getClass().getClassLoader().getResource("firmas.jpg");
		Image firmaRomina = Image.getInstance(new File(resource.toURI()).getAbsolutePath());
		firmaRomina.setWidthPercentage(100);
		cell.addElement(firmaRomina);
		table.addCell(cell);

		cell = PDFUtils.getPDFPCell();
		cell.setBorder(0);
//		resource = getClass().getClassLoader().getResource("firma-graciela.jpg");
//		Image firmagraciela = Image.getInstance(new File(resource.toURI()).getAbsolutePath());
//		firmagraciela.setAlignment(Element.ALIGN_RIGHT);
//		firmagraciela.setWidthPercentage(50);
		table.addCell(cell);

		document.add(table);
	}

	public static Element addDatosLotes(Lote lote) throws DocumentException {
		String fecha = DateUtils.toString(lote.getFecha(), DateUtils.PATTERN_dd_MM_yy_HH_mm);
		String material = lote.getFormula().getMaterial().getNombre();
		String formula = lote.getFormula().getNombre();
		String norma = lote.getFormula().getNorma();
		String loteNro = lote.getNroLote();

		PdfPTable table = new PdfPTable(new float[] { 20, 40, 20, 20 });
		table.setWidthPercentage(100);
		PdfPCell cell = PDFUtils.getPDFPCell();

		cell.setPhrase(new Phrase("FECHA", PDFUtils.fontHeaderTable));
		table.addCell(cell);
		cell.setPhrase(new Phrase(fecha, PDFUtils.fontText));
		table.addCell(cell);
		cell.setPhrase(new Phrase("MATERIAL", PDFUtils.fontHeaderTable));
		table.addCell(cell);
		cell.setPhrase(new Phrase(material, PDFUtils.fontText));
		table.addCell(cell);
		cell.setPhrase(new Phrase("OBJETIVO", PDFUtils.fontHeaderTable));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Aprobacin de Lote", PDFUtils.fontText));
		table.addCell(cell);
		cell.setPhrase(new Phrase("GRADO", PDFUtils.fontHeaderTable));
		table.addCell(cell);
		cell.setPhrase(new Phrase(formula, PDFUtils.fontText));
		table.addCell(cell);

		cell.setPhrase(new Phrase("REF. NORMA", PDFUtils.fontHeaderTable));
		cell.setBorder(0);
		table.addCell(cell);
		cell.setPhrase(new Phrase(norma, PDFUtils.fontText));
		cell.setBorder(0);
		table.addCell(cell);
		cell.setPhrase(new Phrase("LOTE Nro.", PDFUtils.fontHeaderTable));
		cell.setBorder(0);
		table.addCell(cell);
		cell.setPhrase(new Phrase(loteNro, PDFUtils.fontText));
		cell.setBorder(0);
		table.addCell(cell);

		return table;

	}

	public static Element addEnsayo(Ensayo ensayo, ReporteLoteConfiguracionCliente config) throws DocumentException {

		Paragraph p = new Paragraph();

		boolean mostrarParametros = false;
		boolean mostrarResultados = false;
		boolean mostrarCondiciones = false;
		boolean mostraObervacionesParametros = false;
		boolean mostrarTodasLasPruebas = false;

		if (config != null) {
			mostrarParametros = config.getMostrarParametros();
			mostrarResultados = config.getMostrarResultados();
			mostrarCondiciones = config.getMostrarCondiciones();
			mostraObervacionesParametros = config.getMostrarObservacionesParametro();
			mostrarTodasLasPruebas = config.getMaquina() == null;
		}

		// Principal: si no muestra parametros el resto no importa
		if (mostrarParametros) {

			int rowspanpruebas = ensayo.getResultados().size();
			int rowspancondiciones = ensayo.getConfiguracionPrueba().getCondiciones().isEmpty() ? 0 : 1; // ensayo.getConfiguracionPrueba().getCondiciones().size();

			// Armo la tabla de resultados
			// Contiene prueba, min, max, resultado y norma
			float[] colsConResultado = new float[] { 25, 30, 10, 10, 10, 20 };

			float[] cols = colsConResultado;

			PdfPTable tableResultados = new PdfPTable(cols);
			tableResultados.setWidthPercentage(100);
			tableResultados.setSpacingAfter(20);

			// Cabecera

			String observacionesMaquina = "";
			if (StringUtils.isNotBlank(ensayo.getConfiguracionPrueba().getMaquina().getObservacionesReporte())) {
				observacionesMaquina = ensayo.getConfiguracionPrueba().getMaquina().getObservacionesReporte();
			}
			String observacionesParametrizacion = mostraObervacionesParametros
					? ensayo.getConfiguracionPrueba().getObservacionesReporte()
					: "";
			boolean hayObservaciones = StringUtils.isNotEmpty(observacionesParametrizacion)
					|| StringUtils.isNotEmpty(observacionesMaquina);

			boolean hayCondiciones = mostrarCondiciones && !ensayo.getConfiguracionPrueba().getCondiciones().isEmpty();

			boolean first = true;

			int cantidadPruebas = ensayo.getResultados().size();
			int posPrueba = 1;

			PdfPCell cell;

			Set<EnsayoResultado> resultadossinorden = ensayo.getResultados();

			List<EnsayoResultado> resultados = resultadossinorden.stream()
					.sorted(Comparator.comparing(EnsayoResultado::getPosicion)).collect(Collectors.toList());

			Map<String, String> resultadosError = new HashMap<>();

			for (EnsayoResultado resultado : resultados) {

				boolean existePrueba = config.getPruebas().stream().filter(
						x -> x.getId() == resultado.getConfiguracionPruebaParametro().getMaquinaPrueba().getId())
						.findAny().isPresent();

				boolean mostrarPrueba = mostrarTodasLasPruebas || existePrueba;

				if (mostrarPrueba) {
					boolean ultimo = posPrueba == cantidadPruebas;
					posPrueba++;

					Double minimo = resultado.getConfiguracionPruebaParametro().getMinimo();
					Double maximo = resultado.getConfiguracionPruebaParametro().getMaximo();
					String nombre = resultado.getConfiguracionPruebaParametro().getMaquinaPrueba().getNombre();
					String norma = resultado.getConfiguracionPruebaParametro().getNorma();

					if (first) {

						cell = PDFUtils.getPDFPCell();

						cell.setPhrase(new Phrase(resultado.getConfiguracionPruebaParametro().getMaquinaPrueba()
								.getMaquina().getNombre().toUpperCase(), PDFUtils.fontHeaderTable));
						tableResultados.addCell(cell);

						cell = PDFUtils.getPDFPCell();
						cell.setPhrase(new Phrase("Nombre", PDFUtils.fontHeaderTable));
						tableResultados.addCell(cell);

						cell = PDFUtils.getPDFPCell();
						cell.setPhrase(new Phrase("Min", PDFUtils.fontHeaderTable));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableResultados.addCell(cell);

						cell = PDFUtils.getPDFPCell();
						cell.setPhrase(new Phrase("Max", PDFUtils.fontHeaderTable));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableResultados.addCell(cell);

						if (mostrarResultados) {
							cell = PDFUtils.getPDFPCell();
							cell.setPhrase(new Phrase("Valor", PDFUtils.fontHeaderTable));
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableResultados.addCell(cell);
						} else {
							cell = PDFUtils.getPDFPCell();
							cell.setPhrase(new Phrase("", PDFUtils.fontHeaderTable));
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tableResultados.addCell(cell);
						}

						cell = PDFUtils.getPDFPCell();
						cell.setPhrase(new Phrase("Norma", PDFUtils.fontHeaderTable));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tableResultados.addCell(cell);

						cell = PDFUtils.getPDFPCell();
						cell.setRowspan(rowspanpruebas);
						cell.setPhrase(new Phrase("Ensayo", PDFUtils.fontHeaderTable));
						cell.setBorder(0);
						cell.setBorderWidthRight(1);
						cell.setBorderWidthBottom(1);
						cell.setBorderColorRight(PDFUtils.COLOR_GRIS_BORDES);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						if (!hayObservaciones && !hayCondiciones) {
							cell.setBorderWidthBottom(0);
						}
						tableResultados.addCell(cell);

						first = false;
					}

					cell = PDFUtils.getPDFPCell();
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPhrase(new Phrase(nombre, PDFUtils.fontText));
					if (!hayObservaciones && !hayCondiciones && ultimo) {
						cell.setBorderWidthBottom(0);
					}
					tableResultados.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(minimo != null ? String.format("%.2f", minimo) : "", PDFUtils.fontText));
					if (!hayObservaciones && !hayCondiciones && ultimo) {
						cell.setBorderWidthBottom(0);
					}
					tableResultados.addCell(cell);

					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPhrase(new Phrase(maximo != null ? String.format("%.2f", maximo) : "", PDFUtils.fontText));
					if (!hayObservaciones && !hayCondiciones && ultimo) {
						cell.setBorderWidthBottom(0);
					}
					tableResultados.addCell(cell);

					if (mostrarResultados) {
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

						if (resultado.getRedondeo() == null) {
							resultadosError.put(nombre, "No tiene resultado");
						} else {
							cell.setPhrase(
									new Phrase(String.format("%.2f", resultado.getRedondeo()), PDFUtils.fontText));
							if (!hayObservaciones && !hayCondiciones && ultimo) {
								cell.setBorderWidthBottom(0);
							}
							tableResultados.addCell(cell);
						}
					} else {
						cell = PDFUtils.getPDFPCell();
						cell.setPhrase(new Phrase("", PDFUtils.fontHeaderTable));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tableResultados.addCell(cell);
					}

					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPhrase(new Phrase(norma, PDFUtils.fontText));

					if (!hayObservaciones && !hayCondiciones && ultimo) {
						cell.setBorderWidthBottom(0);
					}

					tableResultados.addCell(cell);
				}
			}

			if (!resultadosError.isEmpty()) {
				throw new ErrorValidationException("Faltan cargar resultados en la mquina "
						+ ensayo.getConfiguracionPrueba().getMaquina().getNombre(), resultadosError);
			}

			first = true;

			// Condiciones

			if (hayCondiciones) {

				cell = PDFUtils.getPDFPCell();
				cell.setPhrase(new Phrase("Condiciones", PDFUtils.fontHeaderTable));
				cell.setRowspan(rowspancondiciones);
				cell.setBorder(0);
				cell.setBorderWidthRight(1);
				cell.setBorderColorRight(PDFUtils.COLOR_GRIS_BORDES);
				cell.setBorderWidthBottom(1);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				first = false;
				if (!hayObservaciones) {
					cell.setBorderWidthBottom(0);
				}
				tableResultados.addCell(cell);

				String condiciones = "";

				first = true;

				for (ConfiguracionPruebaCondicion condicion : ensayo.getConfiguracionPrueba().getCondiciones()) {

					if (!first) {
						condiciones += " - ";
					}
					first = false;

					condiciones += condicion.getNombre() + ": " + String.format("%.2f", condicion.getValor());

				}

				cell = PDFUtils.getPDFPCell();
				cell.setColspan(cols.length - 1);
				cell.setPhrase(new Phrase(condiciones, PDFUtils.fontText));

				if (!hayObservaciones) {
					cell.setBorderWidthBottom(0);
				}

				tableResultados.addCell(cell);

			}

			p.add(tableResultados);

			if (hayObservaciones) {

				cell = PDFUtils.getPDFPCell();
				cell.setPhrase(new Phrase("Observaciones", PDFUtils.fontHeaderTable));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderColorRight(PDFUtils.COLOR_GRIS_BORDES);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthRight(1);
				tableResultados.addCell(cell);

				int colspan = cols.length;
				cell = PDFUtils.getPDFPCell();
				String string = observacionesMaquina;
				if (StringUtils.isNotBlank(observacionesParametrizacion)
						&& StringUtils.isNotBlank(observacionesMaquina)) {
					string += System.lineSeparator();
				}
				string += observacionesParametrizacion;
				cell.setPhrase(new Phrase(string, PDFUtils.fontText));
				cell.setColspan(colspan - 1);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidthBottom(0);
				tableResultados.addCell(cell);
			}

		}

		return p;

	}

}
