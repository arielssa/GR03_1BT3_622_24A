package com.monedero.util;
import com.monedero.model.Cuenta;
import com.monedero.model.Transaccion;
import com.monedero.model.Transferencia;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class PDFGenerator {
    /**
     * Genera un PDF a partir de una lista de transacciones y escribe el contenido en el flujo de salida proporcionado.
     *
     * @param movimientos   Lista de transacciones a incluir en el PDF.
     * @param outputStream  Flujo de salida donde se guardará el PDF.
     * @param cuenta   Cuenta asociada a los movimientos.
     * @throws IOException Si ocurre un error al generar el PDF.
     */
    public static void generarPDF(List<Transaccion> movimientos, OutputStream outputStream, Cuenta cuenta) throws IOException {
        movimientos.sort(Comparator.comparing(Transaccion::getFecha));
        try (PDDocument document = new PDDocument()) {
            int pageNumber = 1;
            Collections.reverse(movimientos);
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Informe de Movimientos de la Cuenta");
                contentStream.endText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float margin = 50;
                float yStart = 700;
                float yPosition = yStart;
                float rowHeight = 20f;
                // Ancho de columnas y encabezados
                float[] columnWidths = {80, 80, 60, 80, 80, 80};
                String[] headers = {"Tipo", "Fecha", "Valor", "Concepto", "Etiqueta", "Saldo Después"};
                // Dibujar encabezados de columna
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                yPosition -= rowHeight;
                for (int i = 0; i < headers.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + getColumnXOffset(i, columnWidths), yPosition - 5);
                    contentStream.showText(headers[i]);
                    contentStream.endText();
                }
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                yPosition -= rowHeight;
                double saldoDespues = cuenta.getBalance();
                for (Transaccion movimiento : movimientos) {
                    if (yPosition < 100) {
                        addFooter(contentStream, page, pageNumber++);
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, true);
                        yPosition = yStart;
                    }
                    List<String> data = movimiento.extraerInformacionTransaccion();
                    data.add(String.format("%.2f", saldoDespues));
                    saldoDespues = movimiento.calcularBalanceAntesDeTransaccion(saldoDespues, cuenta.getId());
                    for (int i = 0; i < data.size(); i++) {
                        // Aplicar color solo en las columnas "Tipo" (índice 0) y "Valor" (índice 2)
                        if (i == 0 || i == 2) {
                            String tipo = movimiento.getClass().getSimpleName();
                            if ("Ingreso".equals(tipo)) {
                                contentStream.setNonStrokingColor(Color.GREEN);
                            } else if ("Egreso".equals(tipo)) {
                                contentStream.setNonStrokingColor(Color.RED);
                            } else if ("Transferencia".equals(tipo)) {
                                Transferencia transferencia = (Transferencia) movimiento;
                                if (transferencia.getCuentaOrigen().getId() == cuenta.getId()) {
                                    contentStream.setNonStrokingColor(Color.RED); // transferencia de salida
                                } else {
                                    contentStream.setNonStrokingColor(Color.GREEN); // transferencia de entrada
                                }
                            }
                        } else {
                            contentStream.setNonStrokingColor(Color.BLACK); // Color negro para otras columnas
                        }
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + getColumnXOffset(i, columnWidths), yPosition - 5);
                        contentStream.showText(data.get(i));
                        contentStream.endText();
                    }
                    // Restaurar el color al negro para la siguiente fila
                    contentStream.setNonStrokingColor(Color.BLACK);
                    yPosition -= rowHeight;
                }
                addFooter(contentStream, page, pageNumber);
            } finally {
                contentStream.close();
            }
            document.save(outputStream);
        }
    }
    private static float getColumnXOffset(int columnIndex, float[] columnWidths) {
        float offset = 0;
        for (int i = 0; i < columnIndex; i++) {
            offset += columnWidths[i];
        }
        return offset;
    }
    private static void addFooter(PDPageContentStream contentStream, PDPage page, int pageNumber) throws IOException {
        float footerY = 30;
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(250, footerY + 10);
        contentStream.showText("MonederoVirtual");
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 100, footerY + 10);
        contentStream.showText("Página " + pageNumber);
        contentStream.endText();
    }
}