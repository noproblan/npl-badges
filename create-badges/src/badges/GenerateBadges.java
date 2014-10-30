package badges;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.BaseColor;
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

public class GenerateBadges {
	private static String FILE_TEXTS = "c:/temp/textlines.txt";
	private static String FILE_FRONT = "c:/temp/FirstPdf.pdf";
	private static String FILE_BACK = "c:/temp/FirstPdf.pdf";
	private static String FILE_OUT = "c:/temp/FirstPdf-out.pdf";
	private static Float STAMP_OFFSET_Y = 30f;
	
	/**
	 * 
	 * @param args textfile, front pdf template, back pdf template, output pdf, [stamp y offset]
	 * @return 0 on success, -1 on failure
	 */
	public static void main(String[] args) {
		// read regular command line arguments
		String currentFile = "none";
		try {
			FILE_TEXTS = args[0];
			FILE_FRONT = args[1];
			FILE_BACK = args[2];
			FILE_OUT = args[3];
			
			// check arguments (TODO extract repetitive code)
			currentFile = FILE_TEXTS;
			String fileType = Files.probeContentType(Paths.get(currentFile));
			
			/* Buggy on MAC OS X: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7133484 */
			if (!fileType.equals("text/plain")) {
				System.out.println(currentFile + " must be a text file but is " + fileType);
			}
			
			currentFile = FILE_FRONT;
			/* Buggy on MAC OS X: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7133484 */
			fileType = Files.probeContentType(Paths.get(currentFile));
			if (!fileType.equals("application/pdf")) {
				System.out.println(currentFile + " must be a pdf file but is " + fileType);
			}
			
			currentFile = FILE_BACK;
			/* Buggy on MAC OS X: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7133484 */
			fileType = Files.probeContentType(Paths.get(currentFile));
			if (!fileType.equals("application/pdf")) {
				System.out.println(currentFile + " must be a pdf file but is " + fileType);
			}
			
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Please provide "
					+"input text, "
					+"front template, "
					+"back template "
					+"and output file as command line arguments. \n"
					+"Eg: java -jar create-badges.jar ./lines.txt ./front.pdf ./back-pdf ./out.pdf \n"
					+"A 5th argument offset y as a number is optional.");
			return;
		} catch (IOException e) {
			System.out.println("File could not be accessed: " + currentFile);
			return;
		}
		
		// read optional command line arguments
		try {
			STAMP_OFFSET_Y = Float.parseFloat(args[4]);
		} catch(NumberFormatException e) {
			System.out.println("Offset must be a number.");
			return;
		} catch(ArrayIndexOutOfBoundsException e) {
			// that's ok for an optional argument
		}
		
		// do the work!
		createBadges(FILE_TEXTS, FILE_FRONT, FILE_BACK, FILE_OUT, STAMP_OFFSET_Y);
		System.out.println("success.");
	}

	private static void createBadges(String inputTextfile, String frontTemplatePdf, 
			String backTemplatePdf, String outputPdf, Float stampOffsetY) {
		try {
			// Read Templates
			PdfReader pdfReaderFront = new PdfReader(frontTemplatePdf);
			PdfReader pdfReaderBack = new PdfReader(backTemplatePdf);
			
			// Validate Template Dimensions
			Rectangle dimFront = pdfReaderFront.getPageSize(1);
			Rectangle dimBack = pdfReaderBack.getPageSize(1);
			
			// Configure All Productions
			Font catFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			catFont.setColor(BaseColor.WHITE);
			Document document = new Document();
			FileOutputStream outStream = new FileOutputStream(outputPdf);
			PdfSmartCopy copy = new PdfSmartCopy(document, outStream);
	        document.open();
	        
			for(String text : readTextLines(inputTextfile)) {
				// Configure Next Production
				PdfImportedPage pageFront, pageBack;
		        PdfCopy.PageStamp stamp;
		        
		        // Add First Page
		        pageFront = copy.getImportedPage(pdfReaderFront, 1);
	            copy.addPage(pageFront);
				
		        // because PdfCopy caches already imported pages, we need to reread the one we'd like to stamp.
		        pdfReaderBack = new PdfReader(backTemplatePdf);
	            
		        // Add Second Page with nickname
		        pageBack = copy.getImportedPage(pdfReaderBack, 1);
		        stamp = copy.createPageStamp(pageBack);
	            ColumnText.showTextAligned(
	                    stamp.getOverContent(), Element.ALIGN_CENTER,
	                    new Phrase(text, catFont),
	                    dimFront.getWidth()/2, dimFront.getHeight()-STAMP_OFFSET_Y, 0);
	            stamp.alterContents();
	            // rotate it
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
		} catch (Exception e){ //Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return Collections.unmodifiableList(result);
	}
}
