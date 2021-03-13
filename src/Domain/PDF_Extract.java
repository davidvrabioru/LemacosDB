package Domain;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
public class PDF_Extract {

   private String file_name;

public PDF_Extract(String name) throws IOException {

   PDDocument doc = new PDDocument();
   PDPage page = new PDPage();

   doc.addPage(page);
   PDPageContentStream contentStream = new PDPageContentStream(doc,page);
   PDFont font = PDType1Font.HELVETICA;

   contentStream.setFont(font,16);
   contentStream.beginText();
   contentStream.newLineAtOffset(5,760);
   contentStream.showText("LEMACOS");
   contentStream.showText("Raport perioada: 12-12-2021  ->  01-01-2021");
   contentStream.endText();

   contentStream.close();
   doc.save("test.pdf");



}
}
