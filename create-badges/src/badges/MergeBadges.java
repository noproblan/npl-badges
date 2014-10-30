package badges;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class MergeBadges {

    /** Format of the resulting PDF files. */
    public static final String RESULT = "c:/result%dup.pdf";
    
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @param pow the PDF will be N-upped with N = Math.pow(2, pow);
     * @throws IOException
     * @throws DocumentException
     * @throws SQLException
     */
    public static void manipulatePdf(String src, String dest, int pow)
        throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        Rectangle badgeSize = reader.getPageSize(1);
        Document document = new Document(PageSize.A4.rotate(), 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.open();

        PdfContentByte cb = writer.getDirectContent();
        PdfImportedPage page;
        float offsetX, offsetY, factor;
        float borderX = 0.1f * badgeSize.getWidth();
        float borderY = 0.1f * badgeSize.getHeight();
        int badgeOffset = 0;
        int maxBadgesPerPage = 5;
        int total = reader.getNumberOfPages();
        for (int i = 0; i < total; ) {
            if (i % maxBadgesPerPage == 0) { // break after 5 Badges
                document.newPage();
                badgeOffset = 0;
            }

            factor = 1;
        	
        	// add badge front
            offsetX = badgeOffset * badgeSize.getWidth() + borderX;
            offsetY = document.getPageSize().getHeight() - 1 * badgeSize.getHeight() -borderY;
            page = writer.getImportedPage(reader, i+1);
            cb.addTemplate(page, factor, 0, 0, factor, offsetX, offsetY);
            
            // add badge back
            offsetX = offsetX + badgeSize.getWidth();
           // offsetY = document.getPageSize().getHeight() - 2 * badgeSize.getHeight() -borderY;
            page = writer.getImportedPage(reader, i+2);
            cb.addTemplate(page, -factor, 0, 0, -factor, offsetX, offsetY);
            
            badgeOffset++;
            i = i+2;
        }

        document.close();
        reader.close();
    }
    
    /**
     * Main method.
     * @param args no arguments needed
     * @throws DocumentException 
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) {
    	try {
			MergeBadges.manipulatePdf("c:/temp/gamer.pdf", "c:/temp/gamer-out.pdf", 2);
			//Merge.manipulatePdf("c:/temp/vip.pdf", "c:/temp/vip-out.pdf", 2);
			MergeBadges.manipulatePdf("c:/temp/team.pdf", "c:/temp/team-out.pdf", 2);
			MergeBadges.manipulatePdf("c:/temp/guest.pdf", "c:/temp/guest-out.pdf", 2);
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("success!");
    }
}