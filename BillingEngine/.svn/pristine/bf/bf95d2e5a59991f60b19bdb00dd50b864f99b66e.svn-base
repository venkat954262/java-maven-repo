package com.arbiva.apfgc.invoice.utils;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/21718712/itext-pdfcontentbyte-rectanglerectangle-does-not-behave-as-expected
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 

public class Testing {
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
	private static Font headingBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.BOLD);
	private static Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.NORMAL);
	
    public static void main(String[] args) throws DocumentException, IOException {
    //	style="text-align:center; font-family:arial; font-size:16px; font-weight:bold;
    	
    	
	    Document doc = new Document();
	    PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("F://APFIBER_Revised_Logo.pdf"));
        doc.open();
        
        //For Border
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBorderColor(BaseColor.LIGHT_GRAY); 
        canvas.rectangle(rect);
        
        Image img1 = Image.getInstance("F://APSFL _Revised_Logo.png");
        img1.scaleToFit(130f,130f);
        img1.scalePercent(8f);//Size of the image
        
        Image img2 = Image.getInstance("F://APFIBER_Revised_Logo.png");
        img2.scaleToFit(110f,110f);
        img2.scalePercent(9f);//Size of the image
        
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
        
        cell = new PdfPCell(new Phrase("\nAndhra Pradesh State Fibernet Ltd.\n(A Govt. of A.P Enterprise)",titleFont));
        cell.setPadding(0);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorderColor(BaseColor.WHITE);
        table1.addCell(cell);
        
        cell = new PdfPCell(img2);
        cell.setPadding(5);
        cell.setPaddingRight(-20);
        cell.setBorderColor(BaseColor.WHITE);
        table1.addCell(cell);
        
        //A table2 for Customer Address
        PdfPTable table2 = new PdfPTable(3);
        table2.setWidthPercentage(97f);
        table2.setWidths(new int[]{3, 1, 1});
        table2.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("Bill",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setColspan(3);
        table2.addCell(cell);
        
        /*Paragraph p = new Paragraph("Name And Address of Customer",headingBoldFont);
        p.add(new Chunk("\nSubha Lakshmanarao Pugathota\nH No 1-6, Kapulapeta, Eluru Mandal, BHEEMAVARAM, BHEEMAVARAM, West Godavari,\nAndhra Pradesh, 534004\nTelephone Number: 8816510103",headingFont));
        */
        Phrase firstLine = new Phrase("Name And Address of Customer",headingBoldFont);
        Phrase secondLine = new Phrase("Subha Lakshmanarao Pugathota\nH No 1-6, Kapulapeta, Eluru Mandal, BHEEMAVARAM, BHEEMAVARAM, West Godavari,\nAndhra Pradesh, 534004\nTelephone Number: 8816510103",headingFont);
        
        cell = new PdfPCell();
        cell.addElement(firstLine);
        cell.addElement(secondLine);
        cell.setRowspan(6);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
       
        cell = new PdfPCell(new Phrase("Customer ID",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("100002137",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Number",headingFont));
        //cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("100013323",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Phone Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("8816510103",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Number & Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("200000326 - 17-03-2017",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Period",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("01-02-2017 to 28-02-2017",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Payment Due Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("01-04-2017",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        //A table3 for Payment Details
        PdfPTable table3 = new PdfPTable(6);
        table3.setWidthPercentage(97f);
        table3.setWidths(new int[]{1,1,1,1,1,1});
        table3.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Payment Details",headingBoldFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Previous Balance",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Last Payment",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Balance Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Adjustments",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Current Bill Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount Payable",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("A",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("B",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("C=A-B",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("D",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("E",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("E+C-D",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("149.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("-149.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("204.07",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("55.07",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        PdfPTable table4 = new PdfPTable(3);
        table4.setWidthPercentage(97f);
        table4.setWidths(new int[]{2,2,1});
        
        PdfPTable innertable41 = new PdfPTable(3);
        innertable41.setWidthPercentage(100f);
        innertable41.setWidths(new int[]{1,1,1});
        innertable41.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Payments",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(3);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        innertable41.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        innertable41.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        innertable41.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        innertable41.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CASH",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        innertable41.addCell(cell);
        
        cell = new PdfPCell(new Phrase("20-02-2017",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        innertable41.addCell(cell);
        
        cell = new PdfPCell(new Phrase("149.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        innertable41.addCell(cell);
        
        
        PdfPTable innertable42 = new PdfPTable(2);
        innertable42.setWidthPercentage(100f);
        innertable42.setWidths(new int[]{1,1});
        innertable42.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Package Details",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(2);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        innertable42.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        innertable42.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        innertable42.addCell(cell);
        
        cell = new PdfPCell(new Phrase("HomeBasic",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        innertable42.addCell(cell);
        
        cell = new PdfPCell(new Phrase("20-02-2017",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        innertable42.addCell(cell);
        
        cell = new PdfPCell();
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setRowspan(12);
        cell.addElement(innertable41);
        cell.addElement(innertable42);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Summary Of Current Charges",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Recurring Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("47.89",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Usage Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("One Time Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("149.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Tax",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("7.18",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Late Fee",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Charges (Rs.)",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("204.07",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tax Details Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Service Tax (14%)",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("6.70",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Swachh Bharat Cess + Krishi Kalyan Cess",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.48",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Entertainment Tax",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("0.00",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        doc.add(table1);
        doc.add(table2);
        doc.add(table3);
        doc.add(table4);
        
        doc.close();
        System.out.println("Done123");
    }
}  
