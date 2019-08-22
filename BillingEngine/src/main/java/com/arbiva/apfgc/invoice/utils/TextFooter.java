package com.arbiva.apfgc.invoice.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
 
public class TextFooter {
	PdfTemplate template;
	/* Image total;
	    Font font;*/
    /*class MyFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
        Font font;
        PdfTemplate t;
        Image total;
        int totalCount=0;
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            t = writer.getDirectContent().createTemplate(30, 16);
            try {
            	totalCount++;
                total = Image.getInstance(t);
                total.setRole(PdfName.ARTIFACT);
                //font =  new Font(BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
                font = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            } catch (IOException ioe) {
                throw new ExceptionConverter(ioe);
            }
        }
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("this is a header", ffont);
            Phrase footer = new Phrase("this is a footer", ffont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    
        public void onEndPage(PdfWriter writer, Document document) { 
        	PdfPTable table = new PdfPTable(3); 
        	try { 
        		table.setWidths(new int[]{24, 24, 2}); 
        	table.setTotalWidth(770); 
        	table.getDefaultCell().setFixedHeight(20); 
        	table.getDefaultCell().setBorder(Rectangle.BOTTOM); 
        	table.addCell(new Phrase("Page", ffont)); 
        	table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT); 
        	//table.addCell(new Phrase(String.format(" %d ",totalCount),ffont)); 
        	table.addCell(new Phrase(String.format("Page %d of %d", writer.getPageNumber(),totalCount), ffont)); 
        	PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(totalCount))); 
        	cell.setBorder(Rectangle.BOTTOM); 
        	table.addCell(cell); 
        	
        	table.addCell(new Phrase(String.format(" %d ",totalCount),ffont)); 
        	
        	PdfContentByte canvas = writer.getDirectContent(); 
        	canvas.beginMarkedContentSequence(PdfName.ARTIFACT); 
        	table.writeSelectedRows(0, -1, 36, 30, canvas); canvas.endMarkedContentSequence(); 
        	} catch (DocumentException de) { throw new ExceptionConverter(de); } }

    }
    */
	public class test extends PdfPageEventHelper{
	    private int _pg = 0;
	    private BaseFont font;

	    @Override
	    public void onEndPage(PdfWriter writer, Document document) {
	        _pg++;
	        PdfContentByte cb = writer.getDirectContent();
	        cb.beginText();
	        try {
	            Rectangle pageSize = document.getPageSize();
	            cb.setFontAndSize(font, 8);
	            cb.setTextMatrix(pageSize.getRight(300), pageSize.getBottom(15));
	            String s = "Page " + _pg +" "+ "of";
	            cb.showText(s);
	            cb.addTemplate(template, pageSize.getRight(310) + font.getWidthPoint(s, 10), pageSize.getBottom(15));
	        } catch (Exception exc) {
	           // logger.warn("got Exception : " + exc.getMessage());
	        }
	        cb.endText();
	    }

	    @Override
	    public void onOpenDocument(PdfWriter writer, Document document) {
	        super.onOpenDocument(writer, document);
	        template = writer.getDirectContent().createTemplate(50, 50);
	        try {
	            font = BaseFont.createFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
	        } catch (Exception exc) {
	           // logger.warn("got Exception : " + exc.getMessage());
	        }
	    }

	    @Override
	    public void onCloseDocument(PdfWriter writer, Document document) {
	        super.onCloseDocument(writer, document);

	        template.beginText();
	        try {
	            template.setFontAndSize(font, 8);
	            template.setTextMatrix(0f, 0f);
	            template.showText("" + (writer.getPageNumber() - 1));
	        } catch (Exception ex) {
	          //  logger.error(ex);
	        }
	        template.endText();
	    }

	}


 
    public static final String DEST = "E:\\page_footer.pdf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TextFooter().createPdf(DEST);
        
    }
    
 
    public void createPdf(String filename) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        test event = new test();
        writer.setPageEvent(event);
        // step 3
        document.open();
        // step 4
        for (int i = 0; i < 3; ) {
            i++;
            document.add(new Paragraph("Test " + i));
            document.newPage();
        }
        // step 5
        document.close();
    }
    
}