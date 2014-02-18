package badges;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;

public class Application {
	private static String FILE_TEXTS = "c:/temp/textlines.txt";
	private static String FILE_FRONT = "c:/temp/FirstPdf.pdf";
	private static String FILE_BACK = "c:/temp/FirstPdf.pdf";
	private static String FILE_OUT = "c:/temp/FirstPdf-out.pdf";

	public static void main(String[] args) {
		try {
			// Read Templates
			PdfReader pdfReaderFront = new PdfReader(FILE_FRONT);
			PdfReader pdfReaderBack = new PdfReader(FILE_BACK);
			
			// Validate Template Dimensions
			Rectangle dimFront = pdfReaderFront.getPageSize(1);
			Rectangle dimBack = pdfReaderBack.getPageSize(1);
			
			// Configure All Productions
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Float yOffset = 30f;
			Document document = new Document();
			FileOutputStream outStream = new FileOutputStream(FILE_OUT +".pdf");
			PdfSmartCopy copy = new PdfSmartCopy(document, outStream);
	        document.open();
	        
			for(String text : readTextLines(FILE_TEXTS)) {
				// Configure Next Production
				PdfImportedPage pageFront, pageBack;
		        PdfCopy.PageStamp stamp;
		        
		        // because PdfCopy caches already imported pages, we need to reread the one we'd like to stamp.
		        pdfReaderFront = new PdfReader(FILE_FRONT);
		        
		        // Add First Page
		        pageFront = copy.getImportedPage(pdfReaderFront, 1);
	            stamp = copy.createPageStamp(pageFront);
	            ColumnText.showTextAligned(
	                    stamp.getOverContent(), Element.ALIGN_CENTER,
	                    new Phrase(text, catFont),
	                    dimFront.getWidth()/2, dimFront.getHeight()-yOffset, 0);
	            stamp.alterContents();
	            copy.addPage(pageFront);
				
		        // Add Second Page
		        pageBack = copy.getImportedPage(pdfReaderBack, 1);
		        PdfDictionary pageDict = pdfReaderBack.getPageN(1);
	            pageDict.put(PdfName.ROTATE, new PdfNumber(180)); // rotate second page
	            copy.addPage(pageBack);
			}
            addMetaData(document);
			document.close();
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void addMetaData(Document document) {
		document.addTitle("noprobLAN Badges");
		document.addSubject("automatic generated");
		document.addKeywords("npl, noproblan, badge, lan party");
		document.addAuthor("Josua Schmid");
		document.addCreator("Josua Schmid");
	}
	
	private static List<String> readTextLines(String filepath) {
		List<String> result = new ArrayList<String>();
		try{
			FileInputStream fstream = new FileInputStream(filepath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				result.add(strLine);
			}
			in.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return Collections.unmodifiableList(result);
	}
}
