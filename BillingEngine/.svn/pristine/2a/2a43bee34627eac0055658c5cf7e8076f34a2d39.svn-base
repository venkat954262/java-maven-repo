
package com.arbiva.apfgc.invoice.utils;
/**
Gowthami
*/

import java.net.URL;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
	private int _pg = 0;
    private BaseFont font;
           
    PdfTemplate template;
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
	//private static Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.NORMAL);
	  @Override
	    public void onOpenDocument(PdfWriter writer, Document document) {
	        super.onOpenDocument(writer, document);
	        template = writer.getDirectContent().createTemplate(50, 50);
	        try {
	            font = BaseFont.createFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
	        }catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }

    public void onStartPage(PdfWriter writer, Document document) {
    	try{
    		 PdfContentByte canvas = writer.getDirectContent();
    	        Rectangle rect = new Rectangle(36, 36, 559, 806);
    	        rect.setBorder(Rectangle.BOX);
    	        rect.setBorderWidth(1);
    	        rect.setBorderColor(BaseColor.LIGHT_GRAY); 
    	        canvas.rectangle(rect);
    	        
    	        URL imgURL1 = getClass().getResource("/templates/APSFL _Revised_Logo.png"); 
    	        
    	        Image img1 = Image.getInstance(imgURL1);
    	        img1.scaleToFit(130f,130f);
    	        img1.scalePercent(8f);//Size of the image
    	        
    	        URL imgURL2 = getClass().getResource("/templates/APFIBER_Revised_Logo.png"); 
    	        
    	        Image img2 = Image.getInstance(imgURL2);
    	        img2.scaleToFit(110f,110f);
    	        img2.scalePercent(8f);//Size of the image
    	        
    	        // a table1 with three columns for header images
    	        PdfPTable table1 = new PdfPTable(3);
    	        table1.setWidthPercentage(100f);
    	        
    	        // the cell object
    	        PdfPCell cell;
    	       
    	        cell = new PdfPCell(img1);
    	        cell.setRowspan(2);
    	        cell.setBorderColor(BaseColor.WHITE);
    	        cell.setPadding(5);
    	        cell.setPaddingRight(50);
    	        table1.addCell(cell);
    	        
    	        cell = new PdfPCell(new Phrase("Andhra Pradesh State FiberNet Ltd.\n(A Govt. of A.P Enterprise)",titleFont));
    	        cell.setPadding(0);
    	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    	        cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
    	        cell.setBorderColor(BaseColor.WHITE);
    	        table1.addCell(cell);
    	        
    	        cell = new PdfPCell(img2);
    	        cell.setPadding(5);
    	        cell.setPaddingRight(-20);
    	        cell.setBorderColor(BaseColor.WHITE);
    	        table1.addCell(cell);
    	        
    	      /*  PdfPTable table2 = new PdfPTable(1);
    	        table2.setWidthPercentage(100f);
    	        PdfPCell cell1;
    	        cell1 = new PdfPCell(new Phrase("3 rd Floor, NTR Administrative Block, Pandit Nehru Bus Station,NH-65, Vijayawada - 500013  \nweb : www.apsfl.in | E-mail: apsfl@ap.gov.in | phone No : +91 866 5100005.",FONT));
    	        cell1.setPadding(8);
    	        cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    	        cell1.setBorderColor(BaseColor.WHITE);
    	        table2.addCell(cell1);*/
    	        
                document.add(table1);
               // document.add(table2);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
   
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
    	
        _pg++;
        PdfContentByte cb = writer.getDirectContent();
        cb.beginText();
        try {
            Rectangle pageSize = document.getPageSize();
            cb.setFontAndSize(font, 7);
            cb.setTextMatrix(pageSize.getRight(310), pageSize.getBottom(30));
            String s = "Page " + _pg +" "+ "of"+" ";
            cb.showText(s);
            cb.addTemplate(template, pageSize.getRight(330) + font.getWidthPoint(s, 10), pageSize.getBottom(30));
            cb.endText();
            cb.beginText();
            cb.setTextMatrix(pageSize.getRight(570), pageSize.getBottom(20));
            cb.showText(" Andhra Pradesh State FiberNet Ltd.(A Govt. of A.P Enterprise), 3 rd Floor, NTR Administrative Block,  ");
            cb.endText();
            cb.beginText();
            cb.setTextMatrix(pageSize.getRight(570), pageSize.getBottom(10));
            cb.showText(" Pandit Nehru Bus Station,NH-65, Vijayawada - 500013  web : www.apsfl.in | E-mail: apsfl@ap.gov.in | phone No : +91 866 5100005");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        cb.endText();
    }
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        super.onCloseDocument(writer, document);

        template.beginText();
        try {
            template.setFontAndSize(font, 7);
            template.setTextMatrix(0f, 0f);
            template.showText("" + (writer.getPageNumber() - 1));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        template.endText();
    }
}