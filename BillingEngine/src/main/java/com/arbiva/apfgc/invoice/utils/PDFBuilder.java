
package com.arbiva.apfgc.invoice.utils;
/**
Gowthami
*/


import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.arbiva.apfgc.invoice.dto.AdjustmentDTO;
import com.arbiva.apfgc.invoice.dto.BillInfoDTO;
import com.arbiva.apfgc.invoice.dto.CurrentChargesSummaryDTO;
import com.arbiva.apfgc.invoice.dto.DataUsageDTO;
import com.arbiva.apfgc.invoice.dto.EnterpriseBillInfoDTO;
import com.arbiva.apfgc.invoice.dto.OnetimeChargesDTO;
import com.arbiva.apfgc.invoice.dto.OtherChargesDTO;
import com.arbiva.apfgc.invoice.dto.PackageDTO;
import com.arbiva.apfgc.invoice.dto.PaymentDTO;
import com.arbiva.apfgc.invoice.dto.RecurringDTO;
import com.arbiva.apfgc.invoice.dto.RevenueShareDTO;
import com.arbiva.apfgc.invoice.dto.SummaryDTO;
import com.arbiva.apfgc.invoice.dto.TaxDTO;
import com.arbiva.apfgc.invoice.dto.TelephoneUsageDTO;
import com.arbiva.apfgc.invoice.dto.UsageDTO;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {
	private static final Logger LOGGER = Logger.getLogger(PDFBuilder.class);
	private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
	private static Font headingBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.BOLD);
	private static Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,Font.NORMAL);
	private static Font bigBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
	private static Font FONT = new Font(Font.FontFamily.TIMES_ROMAN, 7);
	private static Font urlFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLUE);
	
	
	
	private static Paragraph a = new Paragraph("* In case of overdue / defaults, the right to deactivate your services, is reserved.",FONT);
	private static Paragraph a1 = new Paragraph("* All disputes are subject to Vijayawada jurisdiction only. ",FONT);
	private static Paragraph a2 = new Paragraph("* GST Regn No :37AANCA9069P1ZM.",FONT);
	private static Paragraph a3 = new Paragraph("* GST is currently charged at 18%.",FONT);
	private static Paragraph c3 = new Paragraph("* SAC Code :998419.",FONT);
	private static Paragraph a4 = new Paragraph("* PAN No.:AANCA9069P and CIN No.:U72200TG2015SGC101155.",FONT);
	private static Paragraph a5 = new Paragraph("* All the Amounts and Taxes shown are in Indian Rupees.",FONT);
	private static Paragraph a6 = new Paragraph("* This Invoice is system generated hence signature and stamp is not required. ",FONT);
	private static Paragraph a7 = new Paragraph("* E&OE Errors and omissions excepted. ",FONT);
	private static Paragraph a8 = new Paragraph("* Payment done through Cheques are subject to realization. ",FONT);
	
	private static Paragraph b = new Paragraph("* Now you can make your payments online by using your Net Banking or Master / Visa / Diner  Club credit cards and Master / Maestro / Visa / Rupay debit cards, please visit http://bss.apsfl.co.in/css for further details. ",FONT);
	private static Paragraph b2 = new Paragraph("* Cash payments should be made to the authorized persons only. ",FONT);
	private static Paragraph b4 = new Paragraph("* Cheques should be in favour of M/s Andhra Pradesh State FiberNet Limited, Vijayawada. ",FONT);
	private static Paragraph b5 = new Paragraph("* Outstanding amounts must be paid through cheques or pay online within the due date mentioned. ",FONT);
	private static Paragraph b6 = new Paragraph("* Out station cheques and post dated cheques will not be accepted. Penal action will be levied in case of cheque bounce. ",FONT);
	private static Paragraph b7 = new Paragraph("* Late payment charges applicable on non-payment of bill on or before due date is Rs.100 or 2% of invoice value whichever is higher subject to maximum charge of Rs 300. ",FONT);
	private static Paragraph b9 = new Paragraph("* For any Queries & Complaints on Billing, Renewals or Technical queries Call :1800 599 5555 ( Toll Free ) or mail to edt.apsfl@ap.gov.in or mail to apsfl@ap.gov.in. ",FONT);
	private static Paragraph b8 = new Paragraph("* Account Details for payment through bank Account No. 085411100002421   IFSC code: ANDB0000606  Andhra Bank, Vijayawada. ",FONT);
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	LOGGER.info("Inside Override method");
    }
    
    protected void generatePDF(BillInfoDTO billInfoDTO,Document doc,PdfWriter writer)
            throws Exception {
    	LOGGER.info("Inside generatePDF");
    	//List<Book> listBooks = (List<Book>) model.get("listBooks");
    	
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
        doc.open();
        //For Border
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBorderColor(BaseColor.LIGHT_GRAY); 
        canvas.rectangle(rect);
        
        PdfPCell cell;

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
       
        Phrase firstLine = new Phrase("Name And Address of Customer",headingBoldFont);
        Phrase secondLine = new Phrase(billInfoDTO.getFullName(),headingFont);
        Phrase thirdLine = new Phrase(billInfoDTO.getAddress(),headingFont);
        Phrase fourthLine = new Phrase("Phone Number:-"+billInfoDTO.getPhoneNumber(),headingFont);
        Phrase fifthhLine = new Phrase("GST Number: "+"N/A",headingFont);
        cell = new PdfPCell();
        cell.addElement(firstLine);
        cell.addElement(secondLine);
        cell.addElement(thirdLine);
        cell.addElement(fourthLine);
        cell.addElement(fifthhLine);
        cell.setRowspan(7);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
       
        cell = new PdfPCell(new Phrase("Customer ID",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(billInfoDTO.getCustomerId(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Number",headingFont));
        //cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(billInfoDTO.getAccountNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Phone Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(billInfoDTO.getPhoneNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Invoice Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("APSFL/TP/"+DateFormate.DateFormateYYYYMMwithslash(billInfoDTO.getBillDate())+"/"+billInfoDTO.getBillNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Invoice Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYYwithslash(billInfoDTO.getBillDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Period",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(billInfoDTO.getBillPeriodFrom())+" to "+DateFormate.DateFormateDDMMYYY(billInfoDTO.getBillPeriodTo()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Payment Due Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(billInfoDTO.getDueDate()),headingFont));
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
        
        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getPrevBalance()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getLastPayment()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getBalanceAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getAdjustments()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getCurrentBillAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getAmountPayable()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        PdfPTable table4 = new PdfPTable(3);
        table4.setWidthPercentage(97f);
        table4.setWidths(new int[]{2,2,1});
        
        PdfPTable innertable41 = new PdfPTable(3);
        innertable41.setWidthPercentage(100f);
        innertable41.setWidths(new int[]{1,1,1});
        innertable41.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Last Payment",headingBoldFont));
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
        
        List<PaymentDTO> paymentsListObj = billInfoDTO.getPayments();
        LOGGER.info("last payment details count:"+paymentsListObj.size());
        Iterator<PaymentDTO> it = paymentsListObj.iterator();
        
        while(it.hasNext()){
        	PaymentDTO paymentDTOObj = it.next();
        	LOGGER.info("Description :"+paymentDTOObj.getDescription()+" date:"+paymentDTOObj.getDate()+" Amount:"+paymentDTOObj.getAmount());
        	 cell = new PdfPCell(new Phrase(paymentDTOObj.getDescription(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             innertable41.addCell(cell);
             
             cell = new PdfPCell(new Phrase(paymentDTOObj.getDate(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             innertable41.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(paymentDTOObj.getAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             innertable41.addCell(cell);
        }
        
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
        
        cell = new PdfPCell(new Phrase("Installation Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        innertable42.addCell(cell);
        
        List<PackageDTO> packagesListObj = billInfoDTO.getPackages();
        
        Iterator<PackageDTO> it1 = packagesListObj.iterator();
        
        List<String> packageList = new ArrayList<String>();
        while(it1.hasNext()){
        	
        PackageDTO PackageDTOObj = it1.next();
			if (!packageList.contains(PackageDTOObj.getDescription())) {
				cell = new PdfPCell(new Phrase(PackageDTOObj.getDescription(), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				innertable42.addCell(cell);

				cell = new PdfPCell(new Phrase(PackageDTOObj.getDate(), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				innertable42.addCell(cell);
				packageList.add(PackageDTOObj.getDescription());
			}
        }
        
        cell = new PdfPCell();
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setRowspan(15);
        cell.addElement(innertable41);
        cell.addElement(innertable42);
        table4.addCell(cell);
        table4.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Summary Of Current Charges",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Monthly  Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        //total charge
        BigDecimal totalCharge = new BigDecimal("0.00");
        
        SummaryDTO summaryDTOObj = billInfoDTO.getSummary();
        
        TaxDTO taxDTOObj = billInfoDTO.getTaxDTO();
        BigDecimal srvcTax=new BigDecimal(0.0);
        BigDecimal gstTax=new BigDecimal(0.0);
        LOGGER.info("srvc : " + taxDTOObj.getServiceTax());
        if(taxDTOObj.getServiceTax()!=null){
        	  srvcTax=taxDTOObj.getServiceTax();
              gstTax=srvcTax.divide(new BigDecimal("2"));
              gstTax=gstTax.setScale(2,BigDecimal.ROUND_DOWN);
        }
       
        LOGGER.info("ChargeType Flag : " + summaryDTOObj.getChargeTypeFlag());
        logger.info("BillInfoDTOItObj.getRecurringCharges() : "+billInfoDTO.getRecurringCharges().size());
		BigDecimal srvc_cost = new BigDecimal(0.00);
		BigDecimal catchup_cost = new BigDecimal(0.00);
		BigDecimal vod_cost = new BigDecimal(0.00);
		if (billInfoDTO.getRecurringCharges() != null && !billInfoDTO.getRecurringCharges().isEmpty()) {
			int i = 0;
			List<RecurringDTO> list = billInfoDTO.getRecurringCharges();
			while(i < list.size()) {
				RecurringDTO recurringDTO = list.get(i);
				if(recurringDTO.getChargeType().equals("Service Rental Charges")) {
					srvc_cost = recurringDTO.getCharges();
				}
				if(recurringDTO.getChargeType().equals("Catchup Charges")) {
					catchup_cost = recurringDTO.getCharges();
				}
				if(recurringDTO.getChargeType().equals("VOD Charges")) {
					vod_cost = recurringDTO.getCharges();
				}
				i++;
			}
		} 
		cell = new PdfPCell(new Phrase(String.valueOf(srvc_cost),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        totalCharge = totalCharge.add(srvc_cost);
		
        //commented existing recurring charges
        /*if(billInfoDTO.getRecurringCharges()!=null && !billInfoDTO.getRecurringCharges().isEmpty()){
        	if(billInfoDTO.getRecurringCharges().size()>0){
	        	 cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getRecurringCharges().get(0).getCharges()),headingFont));
	             cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	             table4.addCell(cell);
        	}
        	else{
           	 	cell = new PdfPCell(new Phrase("0.00",headingFont));
                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                table4.addCell(cell);
           }
        }
        else{
        	 cell = new PdfPCell(new Phrase("0.00",headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
             table4.addCell(cell);
        }*/
       
        /*cell = new PdfPCell(new Phrase("CPE EMI Cost",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        BigDecimal cpeEMICOST=new BigDecimal(0.00);
        
        if(summaryDTOObj.getChargeTypeFlag()!=null && !summaryDTOObj.getChargeTypeFlag().isEmpty()){
	        if(summaryDTOObj.getChargeTypeFlag().equalsIgnoreCase("8")){
	         cpeEMICOST=summaryDTOObj.getRecurringCharges();
	       	 cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getRecurringCharges()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	            table4.addCell(cell);
	       }else{
	    	   cell = new PdfPCell(new Phrase(String.valueOf("0.00"),headingFont));
	           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	           table4.addCell(cell);
	       }
        }else{
    	   cell = new PdfPCell(new Phrase(String.valueOf("0.00"),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           table4.addCell(cell);
       }*/
        BigDecimal cpeEMICOST=new BigDecimal(0.00);
        if(summaryDTOObj.getChargeTypeFlag()!=null && !summaryDTOObj.getChargeTypeFlag().isEmpty()){
        	if(summaryDTOObj.getChargeTypeFlag().equalsIgnoreCase("8")){
	         cpeEMICOST=summaryDTOObj.getRecurringCharges();
        	}
	       }
        
        BigDecimal reCharge=new BigDecimal("0.00");
        reCharge = srvc_cost;
        /*if(billInfoDTO.getRecurringCharges()!=null && !billInfoDTO.getRecurringCharges().isEmpty()){
     	    reCharge=billInfoDTO.getRecurringCharges().get(0).getCharges();
        }*/
     	   
        //=reCharge.add(summaryDTOObj.getUsageCharges()).add(summaryDTOObj.getInternetusageCharges()).add(summaryDTOObj.getValueAddedCharges()).add(summaryDTOObj.getOnetimeCharges()).add(summaryDTOObj.getDiscountsOrAdjustments()).add(cpeEMICOST).add(catchup_cost).add(vod_cost);
        
        cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getUsageCharges()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        totalCharge = totalCharge.add(summaryDTOObj.getUsageCharges());
        
        cell = new PdfPCell(new Phrase("Internet Usage Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getInternetusageCharges()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        totalCharge = totalCharge.add(summaryDTOObj.getInternetusageCharges());
        
        cell = new PdfPCell(new Phrase("Value Added Services",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        //Commented Value added as it is considered in VOD
        //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getValueAddedCharges()),headingFont));
        cell = new PdfPCell(new Phrase(String.valueOf(0.00),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Recurring Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());

        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getOnetimeCharges().add(cpeEMICOST)),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        totalCharge = totalCharge.add(summaryDTOObj.getOnetimeCharges()).add(cpeEMICOST);
        
        /**VOD Charges**/
        cell = new PdfPCell(new Phrase("VOD Charges",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        //logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());
        BigDecimal vod_total_cost = new BigDecimal("0.0");
        
        vod_total_cost = vod_total_cost.add(catchup_cost).add(vod_cost).divide(new BigDecimal("2"), 2);
        cell = new PdfPCell(new Phrase(String.valueOf(vod_total_cost),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        totalCharge = totalCharge.add(vod_total_cost);
        /****/
        
        cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getDiscountsOrAdjustments()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        totalCharge = totalCharge.add(summaryDTOObj.getDiscountsOrAdjustments());
        
        cell = new PdfPCell(new Phrase("Total",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(totalCharge),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Tax",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        /**Total Tax**/
        /*double tax = totalCharge.doubleValue();
        tax = tax * 18/100;*/
        BigDecimal tax = totalCharge.multiply(new BigDecimal("18")).divide(new BigDecimal("100"),2);
        /****/
        
        //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax()),headingFont));
        cell = new PdfPCell(new Phrase(String.valueOf(tax),headingFont));
        //totalCharge = totalCharge.add(summaryDTOObj.getTotalTax());
        totalCharge = totalCharge.add(tax);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Late Fee",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getLateFee()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Grand Total",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax().add(summaryDTOObj.getLateFee()).add(totalCharge)),headingFont));
        cell = new PdfPCell(new Phrase(String.valueOf(totalCharge.add(summaryDTOObj.getLateFee())),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tax Details Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CGST 9%",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        //cell = new PdfPCell(new Phrase(String.valueOf(gstTax),headingFont));
        cell = new PdfPCell(new Phrase(String.valueOf(tax.divide(new BigDecimal("2"),2)),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("SGST 9%",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table4.addCell(cell);
        
        //cell = new PdfPCell(new Phrase(String.valueOf(gstTax),headingFont));
        cell = new PdfPCell(new Phrase(String.valueOf(tax.divide(new BigDecimal("2.00"))),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        table4.addCell(cell);
        
        PdfPTable secondPageRecChargesTable = new PdfPTable(6);
        secondPageRecChargesTable.setWidthPercentage(97f);
        secondPageRecChargesTable.setWidths(new int[]{1,3,3,2,2,2});
        secondPageRecChargesTable.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Monthly Charges",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charge Type",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Start Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("End Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charges (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageRecChargesTable.addCell(cell);
        
        
        List<RecurringDTO> recurringChargesListObj = billInfoDTO.getRecurringCharges();
        Iterator<RecurringDTO> recurringit = recurringChargesListObj.iterator();
        int i=1;
        BigDecimal total_recurring = new BigDecimal("0.00");
        while(recurringit.hasNext()){
        	RecurringDTO recurringDTOObj = recurringit.next();
        	if(!recurringDTOObj.getChargeType().equals("Catchup Charges") && !recurringDTOObj.getChargeType().equals("VOD Charges")) {
	        	cell = new PdfPCell(new Phrase(String.valueOf(i),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageRecChargesTable.addCell(cell);
	      
	            cell = new PdfPCell(new Phrase(recurringDTOObj.getDescription(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageRecChargesTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(recurringDTOObj.getChargeType(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageRecChargesTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getStartDate()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageRecChargesTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getEndDate()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageRecChargesTable.addCell(cell);
	            
	            total_recurring = total_recurring.add(recurringDTOObj.getCharges());
	            cell = new PdfPCell(new Phrase(String.valueOf(recurringDTOObj.getCharges()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageRecChargesTable.addCell(cell);
	            
	            i=i+1;
        	}
        }
        
        cell = new PdfPCell(new Phrase("",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageRecChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageRecChargesTable.addCell(cell);
        
        //cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getTotalRecurringCharges()),headingFont));
        cell = new PdfPCell(new Phrase(String.valueOf(total_recurring),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageRecChargesTable.addCell(cell);
        
        PdfPTable thirdPageotherChargesTextTable = new PdfPTable(1);
        thirdPageotherChargesTextTable.setWidthPercentage(97f);
       
        cell = new PdfPCell(new Phrase("Other Charges / Discounts & Adjustments",bigBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(BaseColor.WHITE);
        thirdPageotherChargesTextTable.addCell(cell); 
        
        
        
        PdfPTable thirdPageVODChargesTable = new PdfPTable(4);
        thirdPageVODChargesTable.setWidthPercentage(97f);
        thirdPageVODChargesTable.setWidths(new int[]{1,3,2,2});
        thirdPageVODChargesTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("VOD / Movie Charges",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(4);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        thirdPageVODChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageVODChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("VOD/ Movie Name",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageVODChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageVODChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageVODChargesTable.addCell(cell);
        
        recurringChargesListObj = billInfoDTO.getRecurringCharges();
        recurringit = recurringChargesListObj.iterator();
        int vod_count=1;
        BigDecimal total_vod = new BigDecimal("0.00");
        while(recurringit.hasNext()){
        	RecurringDTO recurringDTOObj = recurringit.next();
        	if(recurringDTOObj.getChargeType().equals("Catchup Charges") || recurringDTOObj.getChargeType().equals("VOD Charges")) {
	        	cell = new PdfPCell(new Phrase(String.valueOf(vod_count),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            thirdPageVODChargesTable.addCell(cell);
	      
	            cell = new PdfPCell(new Phrase(recurringDTOObj.getDescription(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            thirdPageVODChargesTable.addCell(cell);
	            
	           /* cell = new PdfPCell(new Phrase(recurringDTOObj.getChargeType(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            thirdPageVODChargesTable.addCell(cell);*/
	            
	            cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getStartDate()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            thirdPageVODChargesTable.addCell(cell);
	            
	            /*cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getEndDate()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            thirdPageVODChargesTable.addCell(cell);*/
	            
	            total_vod = total_vod.add(recurringDTOObj.getCharges().divide(new BigDecimal("2"), 2));
	            cell = new PdfPCell(new Phrase(String.valueOf(recurringDTOObj.getCharges().divide(new BigDecimal("2"), 2)),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            thirdPageVODChargesTable.addCell(cell);
	            
	            vod_count=vod_count+1;
        	}
        }
        
       cell = new PdfPCell(new Phrase("",headingFont));
       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
       thirdPageVODChargesTable.addCell(cell);
       
       cell = new PdfPCell(new Phrase("",headingFont));
       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
       thirdPageVODChargesTable.addCell(cell);
        
       cell = new PdfPCell(new Phrase("Total",headingFont));
       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
       thirdPageVODChargesTable.addCell(cell);
       
       cell = new PdfPCell(new Phrase(String.valueOf(total_vod),headingFont));
       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
       thirdPageVODChargesTable.addCell(cell);
        
       /*List<OtherChargesDTO> otherChargesListObj = billInfoDTO.getVodOrMovieCharges();
       
       Iterator<OtherChargesDTO> it2 = otherChargesListObj.iterator();
       int chargesSnoCount = 1;
       while(it2.hasNext())
       {
    	   OtherChargesDTO otherChargesDTO = it2.next();
    	   
    	   cell = new PdfPCell(new Phrase(String.valueOf(chargesSnoCount),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thirdPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(otherChargesDTO.getVodName(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thirdPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(otherChargesDTO.getDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thirdPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(otherChargesDTO.getCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thirdPageVODChargesTable.addCell(cell);
           
           chargesSnoCount = chargesSnoCount+1;
       }*/
       
        PdfPTable thirdPageOneTimeChargesTable = new PdfPTable(4);
        thirdPageOneTimeChargesTable.setWidthPercentage(97f);
        thirdPageOneTimeChargesTable.setWidths(new int[]{1,3,2,2});
        thirdPageOneTimeChargesTable.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Recurring Charges",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(4);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        
        List<OnetimeChargesDTO> oneTimeChargesList = billInfoDTO.getOnetimeCharges();
        
        Iterator<OnetimeChargesDTO> it3 = oneTimeChargesList.iterator();
        int onetimeChargesSnoCount = 1;
        while(it3.hasNext())
        {
        OnetimeChargesDTO onetimeChargesDTO = it3.next();
        cell = new PdfPCell(new Phrase(String.valueOf(onetimeChargesSnoCount),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(onetimeChargesDTO.getDescription(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(onetimeChargesDTO.getDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(onetimeChargesDTO.getCharges()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        thirdPageOneTimeChargesTable.addCell(cell);
        
        onetimeChargesSnoCount = onetimeChargesSnoCount+1;
        }
        
           cell = new PdfPCell(new Phrase("",headingFont));
	       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	       thirdPageOneTimeChargesTable.addCell(cell);
	       
	       cell = new PdfPCell(new Phrase("",headingFont));
	       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	       thirdPageOneTimeChargesTable.addCell(cell);
	        
	       cell = new PdfPCell(new Phrase("Total",headingFont));
	       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	       thirdPageOneTimeChargesTable.addCell(cell);
	       
	       cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getTotalOneTimeCharges()),headingFont));
	       cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	       thirdPageOneTimeChargesTable.addCell(cell);
        
        PdfPTable thirdPageDisAdjChargesTable = new PdfPTable(4);
        thirdPageDisAdjChargesTable.setWidthPercentage(97f);
        thirdPageDisAdjChargesTable.setWidths(new int[]{1,3,2,2});
        thirdPageDisAdjChargesTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(4);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        thirdPageDisAdjChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageDisAdjChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageDisAdjChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageDisAdjChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        thirdPageDisAdjChargesTable.addCell(cell);
        
       List<AdjustmentDTO> discountsListObj =  billInfoDTO.getDiscOrAdjustemnts();
       
       Iterator<AdjustmentDTO> it4 = discountsListObj.iterator();
       int adjSNOCount = 1;
       while(it4.hasNext()){
    	   AdjustmentDTO adjustmentDTOObj = it4.next();
    	   
    	   cell = new PdfPCell(new Phrase(String.valueOf(adjSNOCount),headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thirdPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(adjustmentDTOObj.getDescription(),headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thirdPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(adjustmentDTOObj.getDate()),headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thirdPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(adjustmentDTOObj.getCharges()),headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thirdPageDisAdjChargesTable.addCell(cell);
           
           adjSNOCount = adjSNOCount+1;
       }
        
        
        //doc.add(table1);
        doc.add(table2);
        doc.add(table3);
        doc.add(table4);
        //doc.add(comGenSignatureTable);
        //doc.add(OESignatureTable);
        doc.add(secondPageRecChargesTable);
        doc.add(thirdPageotherChargesTextTable);
        doc.add(thirdPageVODChargesTable);
        doc.add(thirdPageOneTimeChargesTable);
        doc.add(thirdPageDisAdjChargesTable);
        /******************************************* FIRST PAGE END*****************************************************************/
        
       /******************************************* SECOND PAGE START*****************************************************************/
        
        doc.newPage();
        
        PdfPTable secondPageHeaderTable = new PdfPTable(8);
        secondPageHeaderTable.setWidthPercentage(97f);
        secondPageHeaderTable.setWidths(new int[]{1, 1, 1,1,1,1,1,1});
        secondPageHeaderTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("Account Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(billInfoDTO.getAccountNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(billInfoDTO.getBillNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(billInfoDTO.getBillDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Paymentet Due Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(billInfoDTO.getDueDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageHeaderTable.addCell(cell);
        
        PdfPTable secondPageInternetTable = new PdfPTable(4);
        secondPageInternetTable.setWidthPercentage(97f);
        secondPageInternetTable.setWidths(new int[]{1,1,1,1});
        secondPageInternetTable.setSpacingAfter(10f);
        
        cell = new PdfPCell(new Phrase("Internet Data Usage",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(4);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        secondPageInternetTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageInternetTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageInternetTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Units (GB)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageInternetTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Duration (HH:MM:SS)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageInternetTable.addCell(cell); 	 	
        
        List<DataUsageDTO> dataUsageListObj = billInfoDTO.getDataUsages();
        Iterator<DataUsageDTO> dataUsageit = dataUsageListObj.iterator();
        while(dataUsageit.hasNext()){
        	DataUsageDTO dataUsageDTOObj = dataUsageit.next();
        	
        	cell = new PdfPCell(new Phrase(String.valueOf(i),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            secondPageInternetTable.addCell(cell);
            
        	cell = new PdfPCell(new Phrase(dataUsageDTOObj.getDescr(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            secondPageInternetTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getUnits()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            secondPageInternetTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getDuration()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            secondPageInternetTable.addCell(cell);
            
            i=i+1;
        }
        
        PdfPTable fourthPagetelephonetext = new PdfPTable(1);
        fourthPagetelephonetext.setWidthPercentage(97f);
        fourthPagetelephonetext.setWidths(new int[]{1});
        fourthPagetelephonetext.setSpacingAfter(15f);
        
        cell = new PdfPCell(new Phrase("Telephone - "+billInfoDTO.getPhoneNumber(),bigBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(BaseColor.WHITE);
        fourthPagetelephonetext.addCell(cell);
        
        
        PdfPTable fourthPageitemisedtext = new PdfPTable(1);
        fourthPageitemisedtext.setWidthPercentage(97f);
        fourthPageitemisedtext.setWidths(new int[]{1});
        
        cell = new PdfPCell(new Phrase("Itemised Call Details",bigBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorderColor(BaseColor.WHITE);
        fourthPageitemisedtext.addCell(cell);
        
        
        PdfPTable fourthPageUsageChargesTable = new PdfPTable(4);
        fourthPageUsageChargesTable.setWidthPercentage(97f);
        fourthPageUsageChargesTable.setWidths(new int[]{2,1,1,2});
        fourthPageUsageChargesTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(4);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        fourthPageUsageChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Type of Usage",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageUsageChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Units",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageUsageChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageUsageChargesTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageUsageChargesTable.addCell(cell);
        
        
        List<TelephoneUsageDTO> phoneusageListObj = billInfoDTO.getTelephoneUsages();
        Iterator<TelephoneUsageDTO> teleit = phoneusageListObj.iterator();
        while(teleit.hasNext())
        {
        	TelephoneUsageDTO telephoneUsageDTOObj = teleit.next();
        	
        	cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getTypeOfUsage(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageUsageChargesTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getUnits(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageUsageChargesTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getDuration(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageUsageChargesTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(telephoneUsageDTOObj.getCharges()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageUsageChargesTable.addCell(cell);
        }
        PdfPTable fourthPageoffnetlocalTable = new PdfPTable(6);
        fourthPageoffnetlocalTable.setWidthPercentage(97f);
        fourthPageoffnetlocalTable.setWidths(new int[]{1,1,3,2,1,2});
        fourthPageoffnetlocalTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("Local",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        fourthPageoffnetlocalTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetlocalTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Time",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetlocalTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetlocalTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetlocalTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Units",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetlocalTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetlocalTable.addCell(cell);
        
        List<UsageDTO> offnetLocalListObj = billInfoDTO.getOffnetLocalSummary();
        
        Iterator<UsageDTO> offnetit = offnetLocalListObj.iterator();
        while(offnetit.hasNext())
        {
        	UsageDTO usageDTO =offnetit.next();
        	
        	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);
            
            String duration=usageDTO.getDuration().replace(".00", "");
            /*int hour=(Integer.parseInt(duration))/60;
            int minutes=(Integer.parseInt(duration))%60;*/
            
            cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);
            
            /*cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);*/
            
            cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetlocalTable.addCell(cell);
        }
        
        
        PdfPTable fourthPageoffnetstdTable = new PdfPTable(6);
        fourthPageoffnetstdTable.setWidthPercentage(97f);
        fourthPageoffnetstdTable.setWidths(new int[]{1,1,3,2,1,2});
        fourthPageoffnetstdTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("STD",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        fourthPageoffnetstdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetstdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Time",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetstdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetstdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetstdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Units",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetstdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetstdTable.addCell(cell);
        
        
        List<UsageDTO> offnetSTDListObj = billInfoDTO.getOffnetSTDSummary();
        
        Iterator<UsageDTO> offnetSTDit = offnetSTDListObj.iterator();
        while(offnetSTDit.hasNext())
        {
        	UsageDTO usageDTO =offnetSTDit.next();
        	
        	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
            
            /*cell = new PdfPCell(new Phrase(usageDTO.getTime(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);*/
            
            cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
            
            String duration=usageDTO.getDuration().replace(".00", "");
            /*int hour=(Integer.parseInt(duration))/60;
            int minutes=(Integer.parseInt(duration))%60;*/
            
            cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
            
            /*cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);*/
            
            cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
        }
        
        PdfPTable fourthPageoffnetisdTable = new PdfPTable(6);
        fourthPageoffnetisdTable.setWidthPercentage(97f);
        fourthPageoffnetisdTable.setWidths(new int[]{1,1,3,2,1,2});
        fourthPageoffnetisdTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("ISD",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        fourthPageoffnetisdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetisdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Time",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetisdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetisdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetisdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Units",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetisdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetisdTable.addCell(cell);
        
        
        List<UsageDTO> offnetISDListObj = billInfoDTO.getOffnetISDSummary();
        
        Iterator<UsageDTO> offnetISDit = offnetISDListObj.iterator();
        while(offnetISDit.hasNext())
        {
        	UsageDTO usageDTO =offnetISDit.next();
        	
        	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);
            
           /* cell = new PdfPCell(new Phrase(usageDTO.getTime(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);*/
            
            
            cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);
            
            String duration=usageDTO.getDuration().replace(".00", "");
            /*int hour=(Integer.parseInt(duration))/60;
            int minutes=(Integer.parseInt(duration))%60;*/
            
            cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetstdTable.addCell(cell);
            
           /* cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);*/
            
            cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetisdTable.addCell(cell);
        }
        
        PdfPTable fourthPageoffnetmobileTable = new PdfPTable(6);
        fourthPageoffnetmobileTable.setWidthPercentage(97f);
        fourthPageoffnetmobileTable.setWidths(new int[]{1,1,3,2,1,2});
        fourthPageoffnetmobileTable.setSpacingAfter(10f);
        
        
        cell = new PdfPCell(new Phrase("Landline",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        fourthPageoffnetmobileTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetmobileTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Time",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetmobileTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetmobileTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetmobileTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Units",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetmobileTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        fourthPageoffnetmobileTable.addCell(cell);
        
        List<UsageDTO> offnetMobileListObj = billInfoDTO.getOffnetMobileSummary();
        
        Iterator<UsageDTO> offnetMobileDit = offnetMobileListObj.iterator();
        while(offnetMobileDit.hasNext())
        {
        	UsageDTO usageDTO =offnetMobileDit.next();
        	
        	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);
            
            String duration=usageDTO.getDuration().replace(".00", "");
            /*int hour=(Integer.parseInt(duration))/60;
            int minutes=(Integer.parseInt(duration))%60;*/
            
            cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);
            
           /* cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);*/
            
            cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            fourthPageoffnetmobileTable.addCell(cell);
        }
       
       

		 	
        doc.add(secondPageHeaderTable);
        //doc.add(secondPageCustDetailsTable);
       // doc.add(secondPageRecChargesTable);
        doc.add(secondPageInternetTable);
        doc.add(fourthPagetelephonetext);
        doc.add(fourthPageitemisedtext);
        doc.add(fourthPageUsageChargesTable);
        doc.add(fourthPageoffnetlocalTable);
        doc.add(fourthPageoffnetstdTable);
        doc.add(fourthPageoffnetisdTable);
        doc.add(fourthPageoffnetmobileTable);
        
        a.setSpacingBefore(10);
        //a7.setSpacingAfter(10);
        
        a.setIndentationLeft(10);
        a1.setIndentationLeft(10);
        a2.setIndentationLeft(10);
        a3.setIndentationLeft(10);
        c3.setIndentationLeft(10);
        a4.setIndentationLeft(10);
        a5.setIndentationLeft(10);
        a6.setIndentationLeft(10);
        a8.setIndentationLeft(10);
        a7.setIndentationLeft(10);
        
        b.setIndentationLeft(10);
        b2.setIndentationLeft(10);
        b4.setIndentationLeft(10);
        b8.setIndentationLeft(10);
        b5.setIndentationLeft(10);
        b6.setIndentationLeft(10);
        b7.setIndentationLeft(10);
        b9.setIndentationLeft(10);
        
        doc.add(a);
        doc.add(a1);
        doc.add(a2);
        doc.add(a3);
        doc.add(c3);
        doc.add(a4);
        doc.add(a5);
        doc.add(a6);
        doc.add(a8);
        doc.add(a7);
        
        doc.add(b);
        doc.add(b2);
        doc.add(b4);
        doc.add(b8);
        doc.add(b5);
        doc.add(b6);
        doc.add(b7);
        doc.add(b9);
        
        /******************************************* SECOND PAGE END*****************************************************************/
        
        /******************************************* THIRD PAGE START*****************************************************************/
        
     
        /******************************************* FOURTH PAGE END*****************************************************************/
        doc.close();
         
    }

	public void generatePDFForEnterprise(EnterpriseBillInfoDTO enterpriseBillInfoDTO, Document doc, PdfWriter writer) throws Exception {
		LOGGER.info("Inside generatePDFForEnterprise");
		LOGGER.info("srvc Tax : " + enterpriseBillInfoDTO.getServiceTax());
		BigDecimal srvcTax = new BigDecimal(0.0);
		BigDecimal gstTax = new BigDecimal(0.0);
		if(enterpriseBillInfoDTO.getServiceTax()!=null){
			 srvcTax=enterpriseBillInfoDTO.getServiceTax();
			 gstTax=srvcTax.divide(new BigDecimal("2"));
			gstTax=gstTax.setScale(2,BigDecimal.ROUND_DOWN);
		}
		
		 HeaderFooterPageEvent event = new HeaderFooterPageEvent(); 
	    	writer.setPageEvent(event);
        doc.open();
       
        //For Border
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBorderColor(BaseColor.LIGHT_GRAY); 
        canvas.rectangle(rect);
        
        PdfPCell cell;
       
        //A table2 for Customer Address 2241-2318
        PdfPTable table2 = new PdfPTable(3);
        table2.setWidthPercentage(97f);
        table2.setWidths(new int[]{3, 1, 1});
        table2.setSpacingAfter(10f);
        
        
       // cell = new PdfPCell(new Phrase("Account Summary",headingBoldFont));
        cell = new PdfPCell(new Phrase("Tax Invoice",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setColspan(3);
        table2.addCell(cell);
        
        List<BillInfoDTO> BillInfoDTOList1= enterpriseBillInfoDTO.getIndividualBillInfoList();
        Iterator<BillInfoDTO> BillInfoDTOIt1=BillInfoDTOList1.iterator();
        BillInfoDTO BillInfoDTOItObj1=null;
        while(BillInfoDTOIt1.hasNext()){
     	   
     	    BillInfoDTOItObj1 = BillInfoDTOIt1.next();
        }
        
        Phrase firstLine = new Phrase("Name And Address of Customer",headingBoldFont);
        Phrase secondLine = new Phrase(enterpriseBillInfoDTO.getFullName(),headingFont);
        Phrase thirdLine = new Phrase(enterpriseBillInfoDTO.getAddress(),headingFont);
        logger.info("enterpriseBillInfoDTO.getPhoneNumber(): "+enterpriseBillInfoDTO.getPhoneNumber());
        if(enterpriseBillInfoDTO.getPhoneNumber()!=null && !enterpriseBillInfoDTO.getPhoneNumber().isEmpty())
        {
        	/* if(enterpriseBillInfoDTO.getPhoneNumber().split(",").length>1)
             enterpriseBillInfoDTO.setPhoneNumber(enterpriseBillInfoDTO.getPhoneNumber().split(",")[0]);*/
        	String phoneArr[]=enterpriseBillInfoDTO.getPhoneNumber().split(",");
        	 if(phoneArr.length>0){
        		 enterpriseBillInfoDTO.setPhoneNumber(phoneArr[0]);
        	 }
        }
        else{
        	logger.info("Phone no is null so setting to empty");
        	enterpriseBillInfoDTO.setPhoneNumber("");
        }
        	Phrase fourthLine = new Phrase("Phone Number "+enterpriseBillInfoDTO.getPhoneNumber(),headingFont);
        	Phrase fifthLine = new Phrase("GST Number "+"N/A",headingFont);
        
        
        cell = new PdfPCell();
        cell.addElement(firstLine);
        cell.addElement(secondLine);
        cell.addElement(thirdLine);
        cell.addElement(fourthLine);
        cell.addElement(fifthLine);
        cell.setRowspan(7);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
       if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("E"))
    	   enterpriseBillInfoDTO.setBillType("Enterprise Customer ID");
       
       if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("C"))
    	   enterpriseBillInfoDTO.setBillType("Customer ID");
       
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getBillType(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getEnterpriseCustomerId(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Payment Customer ID",headingFont));
        //cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPaymentCustomerId(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Phone Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPhoneNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Invoice Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("APSFL/TP/"+DateFormate.DateFormateYYYYMMwithslash(enterpriseBillInfoDTO.getBillDate())+"/"+enterpriseBillInfoDTO.getBillNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Invoice Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYYwithslash(enterpriseBillInfoDTO.getBillDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Period",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(enterpriseBillInfoDTO.getBillFromDate())+" to "+DateFormate.DateFormateDDMMYYY(enterpriseBillInfoDTO.getBillToDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Payment Due Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(enterpriseBillInfoDTO.getDueDate()),headingFont));
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
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getPrevBalance()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getLastPayment()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getBalanceAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAdjustments()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getCurrentBillAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAmountPayable()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        //A table4 for Last Payment
        PdfPTable table4 = new PdfPTable(4);
        table4.setWidthPercentage(97f);
        table4.setWidths(new int[]{2,2,1,1});
        table4.setSpacingAfter(10f);
       
        cell = new PdfPCell(new Phrase("Last Payment",headingBoldFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        List<PaymentDTO> paymentsListObj = enterpriseBillInfoDTO.getPayments();
        LOGGER.info("last payment count:"+paymentsListObj.size());
        Iterator<PaymentDTO> it = paymentsListObj.iterator();
        
        while(it.hasNext()){
        	PaymentDTO paymentDTOObj = it.next();
        	LOGGER.info("cafno:"+paymentDTOObj.getCafno()+" Description :"+paymentDTOObj.getDescription()+" date:"+paymentDTOObj.getDate()+" Amount:"+paymentDTOObj.getAmount());
	       	 cell = new PdfPCell(new Phrase(paymentDTOObj.getCafno(),headingFont));
	         cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	         table4.addCell(cell);
         
        	 cell = new PdfPCell(new Phrase(paymentDTOObj.getDescription(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             table4.addCell(cell);
             
             cell = new PdfPCell(new Phrase(paymentDTOObj.getDate(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             table4.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(paymentDTOObj.getAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             table4.addCell(cell);
        }
        
        PdfPTable secondPageTable3 = new PdfPTable(5);
        secondPageTable3.setWidthPercentage(97f);
        secondPageTable3.setWidths(new int[]{2,1,1,1,1});
        secondPageTable3.setSpacingAfter(10f);
       
        cell = new PdfPCell(new Phrase("Summary of Current Charges - All Accounts",headingBoldFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setColspan(6);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Name",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount(Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tax (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        List<CurrentChargesSummaryDTO> CurrentChargesSummaryDTO = enterpriseBillInfoDTO.getAllAccountCurrentCharges();
        
        Iterator<CurrentChargesSummaryDTO> CCSDTOit = CurrentChargesSummaryDTO.iterator();
        
        while(CCSDTOit.hasNext()){
        	CurrentChargesSummaryDTO CurrentChargesSummaryDTOObj = CCSDTOit.next();
        	
	       	 cell = new PdfPCell(new Phrase(CurrentChargesSummaryDTOObj.getAccountNumber(),headingFont));
	         cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	         secondPageTable3.addCell(cell);
         
        	 cell = new PdfPCell(new Phrase(CurrentChargesSummaryDTOObj.getAccountName(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(CurrentChargesSummaryDTOObj.getAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(CurrentChargesSummaryDTOObj.getTax()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(CurrentChargesSummaryDTOObj.getTotalAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
        }
        
        cell = new PdfPCell(new Phrase("Total Charges (Rs.)",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAllAccountCurrentChargesTotalAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAllAccountCurrentChargesTotalTax()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAllAccountCurrentChargesTotalofTotalAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tax Details Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CGST 9%",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(gstTax),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("SGST 9%",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(gstTax),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        //doc.add(table1);
        doc.add(table2);
        doc.add(table3);
        doc.add(table4);
        doc.add(secondPageTable3);
        
        /***********************************************************3rd Page Starts******************************************************/
       List<BillInfoDTO> BillInfoDTOList= enterpriseBillInfoDTO.getIndividualBillInfoList();
       Iterator<BillInfoDTO> BillInfoDTOIt=BillInfoDTOList.iterator();
       while(BillInfoDTOIt.hasNext()){
    	   doc.newPage();
    	   BillInfoDTO BillInfoDTOItObj = BillInfoDTOIt.next();
    	   
    	 //A table2 for Customer Address 2794 - 2851 commented by gowthami
           PdfPTable ThridPageTable2 = new PdfPTable(3);
           ThridPageTable2.setWidthPercentage(97f);
           ThridPageTable2.setWidths(new int[]{3, 1, 1});
           ThridPageTable2.setSpacingAfter(10f);
           
           
         //  cell = new PdfPCell(new Phrase("Account Summary",headingBoldFont));
           cell = new PdfPCell(new Phrase("Tax Invoice",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           cell.setColspan(3);
           ThridPageTable2.addCell(cell);
           
           Phrase ThridPagefirstLine = new Phrase("Name And Address of Customer",headingBoldFont);
           Phrase ThridPagesecondLine = new Phrase(BillInfoDTOItObj.getFullName(),headingFont);
           Phrase ThridPagethirdLine = new Phrase(BillInfoDTOItObj.getAddress(),headingFont);
           Phrase ThridPagefourthLine = new Phrase("Phone Number :- "+enterpriseBillInfoDTO.getPhoneNumber(),headingFont);
           
           cell = new PdfPCell();
           cell.addElement(ThridPagefirstLine);
           cell.addElement(ThridPagesecondLine);
           cell.addElement(ThridPagethirdLine);
           cell.addElement(ThridPagefourthLine);
           cell.setRowspan(4);
           cell.setFixedHeight(78f);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
          
           if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("E"))
        	   enterpriseBillInfoDTO.setBillType("Enterprise Customer ID");
           
           if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("C"))
        	   enterpriseBillInfoDTO.setBillType("Customer ID");
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getBillType(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getEnterpriseCustomerId(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Payment Customer ID",headingFont));
           //cell.setColspan(2);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPaymentCustomerId(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Account Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(BillInfoDTOItObj.getAccountNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Phone Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPhoneNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           //Table for Package Details
           PdfPTable thridPageTable3 = new PdfPTable(2);
           thridPageTable3.setWidthPercentage(97f);
           thridPageTable3.setWidths(new int[]{3,2});
           thridPageTable3.setSpacingAfter(10f);
          
           cell = new PdfPCell(new Phrase("Package Details",headingBoldFont));
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           thridPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Installation Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable3.addCell(cell);
           
           List<PackageDTO> packagesListObj = BillInfoDTOItObj.getPackages();
           
           Iterator<PackageDTO> it1 = packagesListObj.iterator();
           
           List<String> packageList = new ArrayList<String>();
           
           while(it1.hasNext()){
           	
           PackageDTO PackageDTOObj = it1.next();
				if (!packageList.contains(PackageDTOObj.getDescription())) {
					cell = new PdfPCell(new Phrase(PackageDTOObj.getDescription(), headingFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					thridPageTable3.addCell(cell);

					cell = new PdfPCell(new Phrase(PackageDTOObj.getDate(), headingFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					thridPageTable3.addCell(cell);
					packageList.add(PackageDTOObj.getDescription());
				}
           }
           
           PdfPTable thridPageTable4 = new PdfPTable(2);
           thridPageTable4.setWidthPercentage(97f);
           thridPageTable4.setWidths(new int[]{2,1});
           thridPageTable4.setSpacingAfter(10f);
           
           BigDecimal totalCharge = new BigDecimal("0.0");
           cell = new PdfPCell(new Phrase("Summary of Current Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(" Amount (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Monthly  Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           SummaryDTO summaryDTOObj = BillInfoDTOItObj.getSummary();
           BigDecimal srvcTax1=new BigDecimal(0.0);
           BigDecimal gstTax1=new BigDecimal(0.0);
           if(summaryDTOObj.getTotalTax()!=null){
        	    srvcTax1=summaryDTOObj.getTotalTax();
                gstTax1=srvcTax1.divide(new BigDecimal("2"));
               gstTax1=gstTax1.setScale(2,BigDecimal.ROUND_DOWN);
           }
           
           TaxDTO taxDTOObj = BillInfoDTOItObj.getTaxDTO();
           logger.info("BillInfoDTOItObj.getRecurringCharges() : "+BillInfoDTOItObj.getRecurringCharges().size());
           BigDecimal totalRecCharge=new BigDecimal("0.00");
           BigDecimal vod_cost = new BigDecimal("0.00");
           BigDecimal catchup_cost = new BigDecimal("0.00");
           if(BillInfoDTOItObj.getRecurringCharges()!=null && !BillInfoDTOItObj.getRecurringCharges().isEmpty())
           { 
        	   if(BillInfoDTOItObj.getRecurringCharges().size()>0){
        		   for(int i=0;i<BillInfoDTOItObj.getRecurringCharges().size();i++)
        		   {
        			   	BigDecimal recCharge=new BigDecimal("0.00");
        			   	
        			   	
        			   	//if(!BillInfoDTOItObj.getRecurringCharges().get(i).getChargeType().equals("VOD Charges") && !BillInfoDTOItObj.getRecurringCharges().get(i).getChargeType().equals("Catchup Charges")) {
	        			    recCharge=BillInfoDTOItObj.getRecurringCharges().get(i).getCharges();
	        			    if(BillInfoDTOItObj.getRecurringCharges().get(i).getChargeType().equals("VOD Charges"))
	        			    	vod_cost = vod_cost.add(recCharge);
	        			    if(BillInfoDTOItObj.getRecurringCharges().get(i).getChargeType().equals("Catchup Charges"))
	        			    	catchup_cost = catchup_cost.add(recCharge);
	        			    if(BillInfoDTOItObj.getRecurringCharges().get(i).getChargeType().equals("Service Rental Charges")) {
	        			    	totalRecCharge=totalRecCharge.add(recCharge);
	        			    }
	        			    logger.info("recCharge : " + recCharge + "...... totalRecCharge : "+totalRecCharge);
        			   	//}
        		   }
        	   }
        	   
	           if(BillInfoDTOItObj.getRecurringCharges().size()>0){
	        	   cell = new PdfPCell(new Phrase(String.valueOf(totalRecCharge),headingFont));
	               cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	               thridPageTable4.addCell(cell);
	               totalCharge = totalCharge.add(totalRecCharge);
	           }
	           else{
	        	   cell = new PdfPCell(new Phrase("0.00",headingFont));
	               cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	               thridPageTable4.addCell(cell);
	           }
           }else{
        	   cell = new PdfPCell(new Phrase("0.00",headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
               thridPageTable4.addCell(cell);
           }
          
        
           logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());
           BigDecimal cpeEMICOST=new BigDecimal(0.00);
           if(summaryDTOObj.getChargeTypeFlag()!=null && !summaryDTOObj.getChargeTypeFlag().isEmpty()){
        	   if(summaryDTOObj.getChargeTypeFlag().equalsIgnoreCase("8")){
            	   cpeEMICOST=summaryDTOObj.getRecurringCharges();
        	   }
              }
           
           BigDecimal recCharge=new BigDecimal("0.00");
           if(BillInfoDTOItObj.getRecurringCharges()!=null && !BillInfoDTOItObj.getRecurringCharges().isEmpty()){
        	    recCharge=BillInfoDTOItObj.getRecurringCharges().get(0).getCharges();
           }
        	   
           //BigDecimal totalCharge=totalRecCharge.add(summaryDTOObj.getUsageCharges()).add(summaryDTOObj.getInternetusageCharges()).add(summaryDTOObj.getValueAddedCharges()).add(summaryDTOObj.getOnetimeCharges()).add(summaryDTOObj.getDiscountsOrAdjustments()).add(cpeEMICOST);
           
           cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getUsageCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(summaryDTOObj.getUsageCharges());
           
           cell = new PdfPCell(new Phrase("Internet Usage Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getInternetusageCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(summaryDTOObj.getInternetusageCharges());
           
           cell = new PdfPCell(new Phrase("Value Added Services",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getValueAddedCharges()),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(0.00),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Recurring Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());

           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getOnetimeCharges().add(cpeEMICOST)),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(cpeEMICOST);
           
           cell = new PdfPCell(new Phrase("VOD",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getValueAddedCharges()),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(vod_cost.add(catchup_cost).divide(new BigDecimal("2"),2)),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(vod_cost.add(catchup_cost).divide(new BigDecimal("2"),2));
           
           cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getDiscountsOrAdjustments()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(summaryDTOObj.getDiscountsOrAdjustments());
           
           cell = new PdfPCell(new Phrase("Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(totalCharge),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Total Tax",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           BigDecimal tax = totalCharge.multiply(new BigDecimal("18")).divide(new BigDecimal("100"));
           
           //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax()),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(tax),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(tax);
           
           cell = new PdfPCell(new Phrase("Late Fee",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getLateFee()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           totalCharge = totalCharge.add(summaryDTOObj.getLateFee());
           
           cell = new PdfPCell(new Phrase("Grand Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           //cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax().add(summaryDTOObj.getLateFee()).add(totalCharge)),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(totalCharge),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Tax Details Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("CGST 9%",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           //cell = new PdfPCell(new Phrase(String.valueOf(gstTax1),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(tax.divide(new BigDecimal("2"),2)),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("SGST 9%",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           //cell = new PdfPCell(new Phrase(String.valueOf(gstTax1),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(tax.divide(new BigDecimal("2"),2)),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
         //Table3 for Package Details
           PdfPTable fourthPageTable3 = new PdfPTable(6);
           fourthPageTable3.setWidthPercentage(97f);
           fourthPageTable3.setWidths(new int[]{1,2,2,1,1,1});
           fourthPageTable3.setSpacingAfter(10f);
          
           cell = new PdfPCell(new Phrase("Monthly Charges",headingBoldFont));
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge Type",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Start Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("End Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           List<RecurringDTO> recurringChargesListObj = BillInfoDTOItObj.getRecurringCharges();
           Iterator<RecurringDTO> recurringit = recurringChargesListObj.iterator();
           int i=1;
           while(recurringit.hasNext()){
           	RecurringDTO recurringDTOObj = recurringit.next();
           	if(!recurringDTOObj.getChargeType().equals("VOD Charges") && !recurringDTOObj.getChargeType().equals("Catchup Charges")) {
           	   cell = new PdfPCell(new Phrase(String.valueOf(i),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(recurringDTOObj.getDescription(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(recurringDTOObj.getChargeType(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getStartDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getEndDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(recurringDTOObj.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               i=i+1;
           	}
           }
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           //cell = new PdfPCell(new Phrase(String.valueOf(BillInfoDTOItObj.getTotalRecurringCharges()),headingFont));
           cell = new PdfPCell(new Phrase(String.valueOf(totalRecCharge),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           PdfPTable fifthPageotherChargesTextTable = new PdfPTable(1);
           fifthPageotherChargesTextTable.setWidthPercentage(97f);
          
           cell = new PdfPCell(new Phrase("Other Charges / Discounts & Adjustments",bigBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setBorderColor(BaseColor.WHITE);
           fifthPageotherChargesTextTable.addCell(cell); 
           
           
           
           PdfPTable fifthPageVODChargesTable = new PdfPTable(4);
           fifthPageVODChargesTable.setWidthPercentage(97f);
           fifthPageVODChargesTable.setWidths(new int[]{1,3,2,2});
           fifthPageVODChargesTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("VOD / Movie Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("VOD/ Movie Name",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           recurringChargesListObj = BillInfoDTOItObj.getRecurringCharges();
           recurringit = recurringChargesListObj.iterator();
           i=1;
           while(recurringit.hasNext()){
           	RecurringDTO recurringDTOObj = recurringit.next();
           	if(recurringDTOObj.getChargeType().equals("VOD Charges") || recurringDTOObj.getChargeType().equals("Catchup Charges")) {
           	   cell = new PdfPCell(new Phrase(String.valueOf(i),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fifthPageVODChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(recurringDTOObj.getDescription(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fifthPageVODChargesTable.addCell(cell);
               
               /*cell = new PdfPCell(new Phrase(recurringDTOObj.getChargeType(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);*/
               
               cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getStartDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fifthPageVODChargesTable.addCell(cell);
               
               /*cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getEndDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);*/
               
               cell = new PdfPCell(new Phrase(String.valueOf(recurringDTOObj.getCharges().divide(new BigDecimal("2"),2)),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fifthPageVODChargesTable.addCell(cell);
               
               i=i+1;
           	}
           }
           
          /*List<OtherChargesDTO> otherChargesListObj = BillInfoDTOItObj.getVodOrMovieCharges();
          
          Iterator<OtherChargesDTO> it2 = otherChargesListObj.iterator();
          int chargesSnoCount = 0;
          while(it2.hasNext())
          {
       	   OtherChargesDTO otherChargesDTO = it2.next();
       	   
       	   cell = new PdfPCell(new Phrase(String.valueOf(chargesSnoCount),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(otherChargesDTO.getVodName(),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(otherChargesDTO.getDate()),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(String.valueOf(otherChargesDTO.getCharges()),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              chargesSnoCount = chargesSnoCount+1;
          }*/
           
           PdfPTable fifthPageOneTimeChargesTable = new PdfPTable(4);
           fifthPageOneTimeChargesTable.setWidthPercentage(97f);
           fifthPageOneTimeChargesTable.setWidths(new int[]{1,3,2,2});
           fifthPageOneTimeChargesTable.setSpacingAfter(10f);
           
           cell = new PdfPCell(new Phrase("Recurring Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           
           List<OnetimeChargesDTO> oneTimeChargesList = BillInfoDTOItObj.getOnetimeCharges();
           
           Iterator<OnetimeChargesDTO> it3 = oneTimeChargesList.iterator();
           int onetimeChargesSnoCount = 1;
           while(it3.hasNext())
           {
           OnetimeChargesDTO onetimeChargesDTO = it3.next();
           cell = new PdfPCell(new Phrase(String.valueOf(onetimeChargesSnoCount),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(onetimeChargesDTO.getDescription(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(onetimeChargesDTO.getDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(onetimeChargesDTO.getCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           onetimeChargesSnoCount = onetimeChargesSnoCount+1;
           }
           
           cell = new PdfPCell(new Phrase("Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(BillInfoDTOItObj.getTotalOneTimeCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           PdfPTable fifthPageDisAdjChargesTable = new PdfPTable(4);
           fifthPageDisAdjChargesTable.setWidthPercentage(97f);
           fifthPageDisAdjChargesTable.setWidths(new int[]{1,3,2,2});
           fifthPageDisAdjChargesTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
          List<AdjustmentDTO> discountsListObj =  BillInfoDTOItObj.getDiscOrAdjustemnts();
          
          Iterator<AdjustmentDTO> it4 = discountsListObj.iterator();
          int adjSNOCount = 0;
          while(it4.hasNext()){
       	   AdjustmentDTO adjustmentDTOObj = it4.next();
       	   
       	   cell = new PdfPCell(new Phrase(String.valueOf(adjSNOCount),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(adjustmentDTOObj.getDescription(),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(adjustmentDTOObj.getDate()),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(String.valueOf(adjustmentDTOObj.getCharges()),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              adjSNOCount = adjSNOCount+1;
          }
           
          // doc.add(table1);
           doc.add(ThridPageTable2);
           doc.add(thridPageTable3);
           doc.add(thridPageTable4);
           doc.add(fourthPageTable3);
           doc.add(fifthPageotherChargesTextTable);
           doc.add(fifthPageVODChargesTable);
           doc.add(fifthPageOneTimeChargesTable);
           doc.add(fifthPageDisAdjChargesTable);
           
           /***********************************************************3rd Page End******************************************************/
           
           /***********************************************************4th Page Starts******************************************************/
           doc.newPage();
           
        //Table 4 for page4
           
           PdfPTable fourthPageTable1 = new PdfPTable(8);
           fourthPageTable1.setWidthPercentage(97f);
           fourthPageTable1.setWidths(new int[]{1, 1, 1,1,1,1,1,1});
           fourthPageTable1.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Account Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(BillInfoDTOItObj.getAccountNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Bill Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(BillInfoDTOItObj.getBillNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Bill Date",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(BillInfoDTOItObj.getBillDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Paymentet Due Date",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(BillInfoDTOItObj.getDueDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           PdfPTable fourthPageInternetTable = new PdfPTable(3);
           fourthPageInternetTable.setWidthPercentage(97f);
           fourthPageInternetTable.setWidths(new int[]{1,1,1});
           fourthPageInternetTable.setSpacingAfter(10f);
           
           cell = new PdfPCell(new Phrase("Internet Data Usage",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(3);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fourthPageInternetTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageInternetTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units (GB)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageInternetTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration (HH:MM:SS)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageInternetTable.addCell(cell); 	 	
           
           List<DataUsageDTO> dataUsageListObj = BillInfoDTOItObj.getDataUsages();
           Iterator<DataUsageDTO> dataUsageit = dataUsageListObj.iterator();
           
           while(dataUsageit.hasNext()){
           	DataUsageDTO dataUsageDTOObj = dataUsageit.next();
           	
           	cell = new PdfPCell(new Phrase(dataUsageDTOObj.getDescr(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageInternetTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getUnits()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageInternetTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getDuration()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageInternetTable.addCell(cell); 	
           }
           
           PdfPTable sixthPagetelephonetext = new PdfPTable(1);
           sixthPagetelephonetext.setWidthPercentage(97f);
           sixthPagetelephonetext.setWidths(new int[]{1});
           sixthPagetelephonetext.setSpacingAfter(15f);
           
           cell = new PdfPCell(new Phrase("Telephone - "+BillInfoDTOItObj.getPhoneNumber(),bigBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setBorderColor(BaseColor.WHITE);
           sixthPagetelephonetext.addCell(cell);
           
           
           PdfPTable fourthPageitemisedtext = new PdfPTable(1);
           fourthPageitemisedtext.setWidthPercentage(97f);
           fourthPageitemisedtext.setWidths(new int[]{1});
           
           cell = new PdfPCell(new Phrase("Itemised Call Details",bigBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setBorderColor(BaseColor.WHITE);
           fourthPageitemisedtext.addCell(cell);
           
           
           PdfPTable sixthPageUsageChargesTable = new PdfPTable(4);
           sixthPageUsageChargesTable.setWidthPercentage(97f);
           sixthPageUsageChargesTable.setWidths(new int[]{2,1,1,2});
           sixthPageUsageChargesTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Type of Usage",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           
           List<TelephoneUsageDTO> phoneusageListObj = BillInfoDTOItObj.getTelephoneUsages();
           Iterator<TelephoneUsageDTO> teleit = phoneusageListObj.iterator();
           while(teleit.hasNext())
           {
           	TelephoneUsageDTO telephoneUsageDTOObj = teleit.next();
           	
           	cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getTypeOfUsage(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getDuration(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(telephoneUsageDTOObj.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
           }
           PdfPTable sixthPageoffnetlocalTable = new PdfPTable(6);
           sixthPageoffnetlocalTable.setWidthPercentage(97f);
           sixthPageoffnetlocalTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetlocalTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Local",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           List<UsageDTO> offnetLocalListObj = BillInfoDTOItObj.getOffnetLocalSummary();
           
           Iterator<UsageDTO> offnetit = offnetLocalListObj.iterator();
           while(offnetit.hasNext())
           {
           	UsageDTO usageDTO =offnetit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
           }
           
           
           PdfPTable sixthPageoffnetstdTable = new PdfPTable(6);
           sixthPageoffnetstdTable.setWidthPercentage(97f);
           sixthPageoffnetstdTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetstdTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("STD",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           
           List<UsageDTO> offnetSTDListObj = BillInfoDTOItObj.getOffnetSTDSummary();
           
           Iterator<UsageDTO> offnetSTDit = offnetSTDListObj.iterator();
           while(offnetSTDit.hasNext())
           {
           	UsageDTO usageDTO =offnetSTDit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
           }
           
           PdfPTable sixthPageoffnetisdTable = new PdfPTable(6);
           sixthPageoffnetisdTable.setWidthPercentage(97f);
           sixthPageoffnetisdTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetisdTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("ISD",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           
           List<UsageDTO> offnetISDListObj = BillInfoDTOItObj.getOffnetISDSummary();
           
           Iterator<UsageDTO> offnetISDit = offnetISDListObj.iterator();
           while(offnetISDit.hasNext())
           {
           	UsageDTO usageDTO =offnetISDit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
           }
           
           PdfPTable sixthPageoffnetmobileTable = new PdfPTable(6);
           sixthPageoffnetmobileTable.setWidthPercentage(97f);
           sixthPageoffnetmobileTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetmobileTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Landline",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           List<UsageDTO> offnetMobileListObj = BillInfoDTOItObj.getOffnetMobileSummary();
           
           Iterator<UsageDTO> offnetMobileDit = offnetMobileListObj.iterator();
           while(offnetMobileDit.hasNext())
           {
           	UsageDTO usageDTO =offnetMobileDit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
           }
           
        
        
           doc.add(fourthPageTable1);
           //doc.add(fourthPageTable3);
           doc.add(fourthPageInternetTable);
           doc.add(sixthPagetelephonetext);
           doc.add(sixthPageUsageChargesTable);
           doc.add(sixthPageoffnetlocalTable);
           doc.add(sixthPageoffnetstdTable);
    	   doc.add(sixthPageoffnetisdTable);
    	   doc.add(sixthPageoffnetmobileTable);
    	   
    	   a.setSpacingBefore(10);
         //  a7.setSpacingAfter(10);
           
           a.setIndentationLeft(10);
           a1.setIndentationLeft(10);
           a2.setIndentationLeft(10);
           a3.setIndentationLeft(10);
           c3.setIndentationLeft(10);
           a4.setIndentationLeft(10);
           a5.setIndentationLeft(10);
           a6.setIndentationLeft(10);
           a8.setIndentationLeft(10);
           a7.setIndentationLeft(10);
           
           b.setIndentationLeft(10);
           b2.setIndentationLeft(10);
           b4.setIndentationLeft(10);
           b8.setIndentationLeft(10);
           b5.setIndentationLeft(10);
           b6.setIndentationLeft(10);
           b7.setIndentationLeft(10);
           b9.setIndentationLeft(10);
           
           doc.add(a);
           doc.add(a1);
           doc.add(a2);
           doc.add(a3);
           doc.add(c3);
           doc.add(a4);
           doc.add(a5);
           doc.add(a6);
           doc.add(a8);
           doc.add(a7);
           
           doc.add(b);
           doc.add(b2);
           doc.add(b4);
           doc.add(b8);
           doc.add(b5);
           doc.add(b6);
           doc.add(b7);
           doc.add(b9);
           
           /***********************************************************4th Page End******************************************************/
	}
       doc.close();
	 }
    
	public void generatePDFWithRevShare(BillInfoDTO billInfoDTO,Document doc,PdfWriter writer, boolean detailedflag,String detailType)
            throws Exception {
    	LOGGER.info("Inside generatePDF");
    	//List<Book> listBooks = (List<Book>) model.get("listBooks");
    	LOGGER.info("PDFBuilder::generatePDFWithRevShare::Create PDF Start");
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
        doc.open();
        //For Border
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBorderColor(BaseColor.LIGHT_GRAY); 
        canvas.rectangle(rect);
        
        PdfPCell cell;

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
        
        if(!detailedflag) {
	        //A table2 for Customer Address
	        PdfPTable table2 = new PdfPTable(3);
	        table2.setWidthPercentage(97f);
	        table2.setWidths(new int[]{3, 1, 1});
	        table2.setSpacingAfter(10f);
	        
	        /**Added to Show month along with Demand Letter*/
	        int year = Integer.parseInt(billInfoDTO.getBillPeriodFrom().substring(0, 4));
	        int month = Integer.parseInt(billInfoDTO.getBillPeriodFrom().substring(5, 7));
	        int dueMonth = 0;
	        int dueYear = 0;
	        if(month == 12) {
	        	dueMonth = 1;
	        	dueYear = year + 1;
	        }else {
	        	dueMonth = month+1;
	        	dueYear = year;
	        }
	        
	        String duedate = null;
			if (dueMonth > 9) {
				duedate = "07-" + dueMonth + "-" + dueYear;
			} else {
				duedate = "07-0" + dueMonth + "-" + dueYear;
			}
	        String billMonth = null;
	        switch (month) {
			case 1:
				billMonth = "January";
				break;
			case 2:
				billMonth = "Febraury";
				break;
			case 3:
				billMonth = "March";
				break;
			case 4:
				billMonth = "April";
				break;
			case 5:
				billMonth = "May";
				break;
			case 6:
				billMonth = "June";
				break;
			case 7:
				billMonth = "July";
				break;
			case 8:
				billMonth = "August";
				break;
			case 9:
				billMonth = "September";
				break;
			case 10:
				billMonth = "October";
				break;
			case 11:
				billMonth = "November";
				break;
			case 12:
				billMonth = "December";
				break;
			}
	        /***/
	        
	        /**Modified to Demand Letter From Bill**/
	        /**Added year and month and changing font only for year and month to bold**/
	        //cell = new PdfPCell(new Phrase("Demand Letter for the Month of "+billMonth+","+year,headingBoldFont));
	        cell = new PdfPCell();
	        Phrase text = new Phrase("Demand Letter for the Month of ",headingFont);
	        Phrase yearmonth = new Phrase(billMonth+","+year,headingBoldFont);
	        text.add(yearmonth);
	        Paragraph paragraph = new Paragraph(text);
	        paragraph.setAlignment(Element.ALIGN_CENTER);
	        cell.addElement(paragraph);
	        //cell.setHorizontalAlignment(Paragraph.ALIGN_CENTER);
	        cell.setColspan(3);
	        table2.addCell(cell);
	       
	        Phrase firstLine = new Phrase("Name And Address of Customer",headingBoldFont);
	        Phrase secondLine = new Phrase(billInfoDTO.getFullName(),headingFont);
	        Phrase thirdLine = new Phrase(billInfoDTO.getAddress(),headingFont);
	        Phrase fourthLine = new Phrase("Phone Number:-"+billInfoDTO.getPhoneNumber(),headingFont);
	        Phrase fifthhLine = new Phrase("GST Number: "+"N/A",headingFont);
	        cell = new PdfPCell();
	        cell.addElement(firstLine);
	        cell.addElement(secondLine);
	        cell.addElement(thirdLine);
	        cell.addElement(fourthLine);
	        cell.addElement(fifthhLine);
	        cell.setRowspan(7);
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table2.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Customer ID",headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        cell.setFixedHeight(25f);
	        table2.addCell(cell);
	        
	        
	        cell = new PdfPCell(new Phrase(billInfoDTO.getCustomerId(),headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        table2.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Account Number",headingFont));
	        //cell.setColspan(2);
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        cell.setFixedHeight(25f);
	        table2.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(billInfoDTO.getAccountNumber(),headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        table2.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Phone Number",headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        cell.setFixedHeight(25f);
	        table2.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(billInfoDTO.getPhoneNumber(),headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        table2.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Payment Due Date",headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        cell.setFixedHeight(25f);
	        table2.addCell(cell);
	        
	        /**Modified to Change Payment Due Date, From 21 after bill generation date, 7 of a month**/
	        /*cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(billInfoDTO.getDueDate()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table2.addCell(cell);*/
	        cell = new PdfPCell(new Phrase(duedate,headingFont));
	        /**Modified to vertical center alignment**/
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        /****/
	        table2.addCell(cell);
	        /****/
	        
	        //A table3 for Payment Details
	        /**Modified to 5 columns from 6**/
	        PdfPTable table3 = new PdfPTable(5);
	        table3.setWidthPercentage(97f);
	        table3.setWidths(new int[]{1,1,1,1,1});
	        table3.setSpacingAfter(10f);
	        /****/
	        
	        cell = new PdfPCell(new Phrase("Payment Details",headingBoldFont));
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        cell.setColspan(5);/*Modified to 5 from 6*/
	        table3.addCell(cell);
	        
	        /**Modified to Previous Month Demand from Previous Due From Previous Balance*/
	        cell = new PdfPCell(new Phrase("Previous Month Demand",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Last Payment",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Balance Amount",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        /**Commented to Remove Adjustments**/
	        /*cell = new PdfPCell(new Phrase("Adjustments",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);*/
	        /****/
	        
	        /**Modified to Current Due Amount From Current Bill Amount*/
	        cell = new PdfPCell(new Phrase("Current Due Amount",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Total Amount Payable",headingBoldFont));
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
	        
	        /**Commented to Remove Adjustments**/
	        /*cell = new PdfPCell(new Phrase("D",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);*/
	        /****/
	        
	        /**Modified to D from E**/
	        cell = new PdfPCell(new Phrase("D",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        /****/
	        
	        /**Modified to D+C from E+C-D**/
	        cell = new PdfPCell(new Phrase("D+C",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        /****/
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getPrevBalance()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getLastPayment()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getBalanceAmount()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        /**Commented to Remove Adjustments**/
	        /*cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getAdjustments()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);*/
	        /****/
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getCurrentBillAmount()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(billInfoDTO.getAmountPayable()),headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table3.addCell(cell);
	        
	        /**Modified to PdfPTable(2) from PdfPTable(3) and setWidths(new int[]{2,1}); from setWidths(new int[]{2,2,1});**/
	        PdfPTable table4 = new PdfPTable(2);
	        table4.setWidthPercentage(97f);
	        table4.setWidths(new int[]{2,1});
	        /****/
	        
	        cell = new PdfPCell(new Phrase("Summary Of Current month Charges",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Monthly  Charges",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        SummaryDTO summaryDTOObj = billInfoDTO.getSummary();
	        
	        TaxDTO taxDTOObj = billInfoDTO.getTaxDTO();
	        BigDecimal srvcTax=new BigDecimal(0.0);
	        BigDecimal gstTax=new BigDecimal(0.0);
	        LOGGER.info("srvc : " + taxDTOObj.getServiceTax());
	        if(taxDTOObj.getServiceTax()!=null){
	        	  srvcTax=taxDTOObj.getServiceTax();
	              gstTax=srvcTax.divide(new BigDecimal("2"));
	              gstTax=gstTax.setScale(2,BigDecimal.ROUND_DOWN);
	        }
	       
	        LOGGER.info("ChargeType Flag : " + summaryDTOObj.getChargeTypeFlag());
	        
	        RevenueShareDTO srvcrent = null;
	        RevenueShareDTO stdusage = null;
	        RevenueShareDTO localusage = null;
	        
	        BigDecimal monthlycharge_apsfl = new BigDecimal("0.0");
	        BigDecimal monthlycharge_thirdparty = new BigDecimal("0.0");
	        BigDecimal telephonecharge_apsfl = new BigDecimal("0.0");
	        BigDecimal telephonecharge_thirdparty = new BigDecimal("0.0");
	        
	        if(billInfoDTO.getRevenueShareDTO()!=null && !billInfoDTO.getRevenueShareDTO().isEmpty()){
	        	logger.info("BillInfoDTOItObj.getRevenueShareDTO() : "+billInfoDTO.getRevenueShareDTO().size());
	        	if(billInfoDTO.getRevenueShareDTO().size()>0){
					srvcrent = billInfoDTO.getRevenueShareDTO().get("SRVCRENT");
					stdusage = billInfoDTO.getRevenueShareDTO().get("STDUSAGE");
					localusage = billInfoDTO.getRevenueShareDTO().get("LOCALUSAGE");
					
					if (srvcrent != null) {
						monthlycharge_apsfl = monthlycharge_apsfl.add(srvcrent.getApsflshare());
						monthlycharge_thirdparty = monthlycharge_thirdparty
								.add(srvcrent.getLmoshare())
								.add(srvcrent.getMsoshare());
					}
					
					if(stdusage!=null) {
						telephonecharge_apsfl = telephonecharge_apsfl.add(stdusage.getApsflshare());
						telephonecharge_thirdparty = telephonecharge_thirdparty
								.add(stdusage.getLmoshare())
								.add(stdusage.getMsoshare());
					}
					
					if(localusage!=null) {
						telephonecharge_apsfl = telephonecharge_apsfl.add(localusage.getApsflshare());
						telephonecharge_thirdparty = telephonecharge_thirdparty
								.add(localusage.getLmoshare())
								.add(localusage.getMsoshare());
					}
	        	}
	        }
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(monthlycharge_apsfl), headingFont));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			table4.addCell(cell);
	        
	        /****/
	       
	        BigDecimal cpeEMICOST=new BigDecimal(0.00);
	        if(summaryDTOObj.getChargeTypeFlag()!=null && !summaryDTOObj.getChargeTypeFlag().isEmpty()){
	        	if(summaryDTOObj.getChargeTypeFlag().equalsIgnoreCase("8")){
		         cpeEMICOST=summaryDTOObj.getRecurringCharges();
	        	}
		       }
	        
	        BigDecimal reCharge=new BigDecimal("0.00");
	        if(billInfoDTO.getRecurringCharges()!=null && !billInfoDTO.getRecurringCharges().isEmpty()){
	     	    reCharge=billInfoDTO.getRecurringCharges().get(0).getCharges();
	        }
	     	   
	        BigDecimal totalCharge=reCharge.add(summaryDTOObj.getUsageCharges()).add(summaryDTOObj.getInternetusageCharges()).add(summaryDTOObj.getValueAddedCharges()).add(summaryDTOObj.getOnetimeCharges()).add(summaryDTOObj.getDiscountsOrAdjustments()).add(cpeEMICOST);
	        
	        cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        /**Modified for Telephone Usage Charges as per Share**/
	        /*cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getUsageCharges()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);*/
	        cell = new PdfPCell(new Phrase(String.valueOf(telephonecharge_apsfl),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        /****/
	        
	        cell = new PdfPCell(new Phrase("Internet Usage Charges",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getInternetusageCharges()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Value Added Services",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getValueAddedCharges()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        /**Added New Row in Summary of Current Charges Table in PDF for Third Party Services**/
	        cell = new PdfPCell(new Phrase("Third Party Services",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        BigDecimal thirdPartyService_charge = new BigDecimal("0.0");
	        thirdPartyService_charge = thirdPartyService_charge
	        		.add(monthlycharge_thirdparty)
	        		.add(telephonecharge_thirdparty);
	        
			cell = new PdfPCell(new Phrase(String.valueOf(thirdPartyService_charge), headingFont));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			table4.addCell(cell);
			
	        /****/
	        
			/**Modified to CPE Rental Charges from Recurring Charges**/
	        cell = new PdfPCell(new Phrase("CPE Rental Charges",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        /****/
	        logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());
	
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getOnetimeCharges().add(cpeEMICOST)),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getDiscountsOrAdjustments()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Total",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(totalCharge),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        /**Modified to Total Tax [CGST 9% + SGST 9%] from Total Tax**/
	        cell = new PdfPCell(new Phrase("Total Tax [CGST 9% + SGST 9%]",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        /****/
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        /**Modified to Late Payment Fee From Late Fee**/
	        cell = new PdfPCell(new Phrase("Late Payment Fee",headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        /****/
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getLateFee()),headingFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        
	        /**Modified title to Bold from General**/
	        cell = new PdfPCell(new Phrase("Grand Total",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        table4.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax().add(summaryDTOObj.getLateFee()).add(totalCharge)),headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        table4.addCell(cell);
	        table4.setSpacingAfter(10f);
	        
	        
	        PdfPTable secondPageInternetTable = new PdfPTable(4);
	        secondPageInternetTable.setWidthPercentage(97f);
	        secondPageInternetTable.setWidths(new int[]{1,1,1,1});
	        secondPageInternetTable.setSpacingAfter(10f);
	        
	        cell = new PdfPCell(new Phrase("Internet Data Usage",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        cell.setColspan(4);
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        secondPageInternetTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        secondPageInternetTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        secondPageInternetTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Units (GB)",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        secondPageInternetTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Duration (HH:MM:SS)",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        secondPageInternetTable.addCell(cell); 	 	
	        /**Added here from line 779 as i is used here**/
	        int i=1;
	        /****/
	        List<DataUsageDTO> dataUsageListObj = billInfoDTO.getDataUsages();
	        Iterator<DataUsageDTO> dataUsageit = dataUsageListObj.iterator();
	        while(dataUsageit.hasNext()){
	        	DataUsageDTO dataUsageDTOObj = dataUsageit.next();
	        	
	        	cell = new PdfPCell(new Phrase(String.valueOf(i),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageInternetTable.addCell(cell);
	            
	        	cell = new PdfPCell(new Phrase(dataUsageDTOObj.getDescr(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageInternetTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getUnits()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageInternetTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getDuration()),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            secondPageInternetTable.addCell(cell);
	            
	            i=i+1;
	        }
	        
	        PdfPTable fourthPageUsageChargesTable = new PdfPTable(4);
	        fourthPageUsageChargesTable.setWidthPercentage(97f);
	        fourthPageUsageChargesTable.setWidths(new int[]{2,1,1,2});
	        fourthPageUsageChargesTable.setSpacingAfter(10f);
	        
	        cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	        cell.setColspan(4);
	        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        fourthPageUsageChargesTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Type of Usage",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        fourthPageUsageChargesTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Units",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        fourthPageUsageChargesTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        fourthPageUsageChargesTable.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        fourthPageUsageChargesTable.addCell(cell);
	        
	        List<TelephoneUsageDTO> phoneusageListObj = billInfoDTO.getTelephoneUsages();
	        Iterator<TelephoneUsageDTO> teleit = phoneusageListObj.iterator();
	        while(teleit.hasNext())
	        {
	        	TelephoneUsageDTO telephoneUsageDTOObj = teleit.next();
	        	
	        	cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getTypeOfUsage(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            fourthPageUsageChargesTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getUnits(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            fourthPageUsageChargesTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getDuration(),headingFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            fourthPageUsageChargesTable.addCell(cell);
	            
	            /**Added Hyper Link**/
		        //String url = "http://localhost:8080/BillingEngine/invoice/customerPDFGeneration/"+billInfoDTO.getCustomerId()+"/"+billInfoDTO.getInvMonth()+"/"+billInfoDTO.getInvYear()+"/"+billInfoDTO.getCustomerType()+"/"+bill;
		        //String url = "http://localhost:8080/BillingEngine/invoice/customerPDFGeneration/";
		        //Anchor anchor = new Anchor("http://localhost:8080/BillingEngine/invoice/customerPDFGeneration/10001530/01/2018/INDIVIDUAL/3");
		        Anchor anchor = new Anchor(String.valueOf(telephoneUsageDTOObj.getCharges()), urlFont);
		        //String url = "http://localhost:8080/css/billingenginesubmit?year="+billInfoDTO.getInvYear()+"&month="+billInfoDTO.getInvMonth()+"&detailedflag=true&detailType="+telephoneUsageDTOObj.getTypeOfUsage();
		        String url = "http://localhost:8080/BillingEngine/invoice/customerPDFGeneration/"+billInfoDTO.getCustomerId()+"/" + billInfoDTO.getInvMonth() +"/"+ billInfoDTO.getInvYear() +"/"+ billInfoDTO.getCustomerType() +"/"+ true +"/"+ telephoneUsageDTOObj.getTypeOfUsage();
		        //String url = "http://localhost:8080/css/billingenginesubmit?year=2018&month=02&detailedflag=true&detailType="+telephoneUsageDTOObj.getTypeOfUsage();
		        anchor.setReference(url);
		        //anchor.setReference("http://localhost:8080/BillingEngine/invoice/customerPDFGeneration/10001530/01/2018/INDIVIDUAL/3");
		        /****/
	            
	            Phrase phrase = new Phrase();
	            phrase.add(anchor);
	            cell = new PdfPCell(phrase);
	            //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            fourthPageUsageChargesTable.addCell(cell);
	        }
	        
	        /****/
	        
	        
	        
	        //doc.add(table1);
	        doc.add(table2);
	        doc.add(table3);
	        doc.add(table4);
	        doc.add(secondPageInternetTable);
	        doc.add(fourthPageUsageChargesTable);
	        //doc.add(comGenSignatureTable);
	        //doc.add(OESignatureTable);
	        /**Commented to Remove Monthly Charges Table, Other Charges/Discounts & Adjustments(VOD/Movie Charges, Recurring Charges, Discounts/Adjustments)**/
	        /*doc.add(secondPageRecChargesTable);
	        doc.add(thirdPageotherChargesTextTable);
	        doc.add(thirdPageVODChargesTable);
	        doc.add(thirdPageOneTimeChargesTable);
	        doc.add(thirdPageDisAdjChargesTable);*/
	        /****/
	        /******************************************* FIRST PAGE END*****************************************************************/
	        
	       /******************************************* SECOND PAGE START*****************************************************************/
	        
	      //  doc.add(secondPageHeaderTable);
	        //doc.add(secondPageCustDetailsTable);
	       // doc.add(secondPageRecChargesTable);
	       // doc.add(secondPageInternetTable);
	    //    doc.add(fourthPagetelephonetext);
	    //    doc.add(fourthPageitemisedtext);
	        // doc.add(fourthPageUsageChargesTable);
	     /**   doc.add(fourthPageoffnetlocalTable);
	        doc.add(fourthPageoffnetstdTable);
	        doc.add(fourthPageoffnetisdTable);
	        doc.add(fourthPageoffnetmobileTable);
	        **/
	        a.setSpacingBefore(10);
	        //a7.setSpacingAfter(10);
	        
	        a.setIndentationLeft(10);
	        a1.setIndentationLeft(10);
	        a2.setIndentationLeft(10);
	        a3.setIndentationLeft(10);
	        c3.setIndentationLeft(10);
	        a4.setIndentationLeft(10);
	        a5.setIndentationLeft(10);
	       //  a6.setIndentationLeft(10);
	        a8.setIndentationLeft(10);
	        a7.setIndentationLeft(10);
	        
	        b.setIndentationLeft(10);
	        b2.setIndentationLeft(10);
	        b4.setIndentationLeft(10);
	        b8.setIndentationLeft(10);
	        b5.setIndentationLeft(10);
	        b6.setIndentationLeft(10);
	        b7.setIndentationLeft(10);
	        b9.setIndentationLeft(10);
	        
	        doc.add(a);
	        doc.add(a1);
	        doc.add(a2);
	        doc.add(a3);
	        doc.add(c3);
	        doc.add(a4);
	        doc.add(a5);
	        // doc.add(a6);
	        doc.add(a8);
	        doc.add(a7);
	        
	        doc.add(b);
	        doc.add(b2);
	        doc.add(b4);
	        doc.add(b8);
	        doc.add(b5);
	        doc.add(b6);
	        doc.add(b7);
	        doc.add(b9);
        }else {
        	PdfPTable fourthPagetelephonetext = new PdfPTable(1);
            fourthPagetelephonetext.setWidthPercentage(97f);
            fourthPagetelephonetext.setWidths(new int[]{1});
            fourthPagetelephonetext.setSpacingAfter(15f);
            
            cell = new PdfPCell(new Phrase("Telephone - "+billInfoDTO.getPhoneNumber(),bigBoldFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setBorderColor(BaseColor.WHITE);
            fourthPagetelephonetext.addCell(cell);
            
            doc.add(fourthPagetelephonetext);
            switch (detailType) {
			case "LOCAL":
				PdfPTable fourthPageoffnetlocalTable = new PdfPTable(6);
	            fourthPageoffnetlocalTable.setWidthPercentage(97f);
	            fourthPageoffnetlocalTable.setWidths(new int[]{1,1,3,2,1,2});
	            fourthPageoffnetlocalTable.setSpacingAfter(10f);
	            
	            cell = new PdfPCell(new Phrase("Local",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            cell.setColspan(6);
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Date",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Time",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Units",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetlocalTable.addCell(cell);
	            
	            List<UsageDTO> offnetLocalListObj = billInfoDTO.getOffnetLocalSummary();
	            
	            Iterator<UsageDTO> offnetit = offnetLocalListObj.iterator();
	            while(offnetit.hasNext())
	            {
	            	UsageDTO usageDTO =offnetit.next();
	            	
	            	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);
	                
	                String duration=usageDTO.getDuration().replace(".00", "");
	                /*int hour=(Integer.parseInt(duration))/60;
	                int minutes=(Integer.parseInt(duration))%60;*/
	                
	                cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);
	                
	                /*cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);*/
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetlocalTable.addCell(cell);
	            }
	            doc.add(fourthPageoffnetlocalTable);
				break;
				
			case "STD":
				PdfPTable fourthPageoffnetstdTable = new PdfPTable(6);
	            fourthPageoffnetstdTable.setWidthPercentage(97f);
	            fourthPageoffnetstdTable.setWidths(new int[]{1,1,3,2,1,2});
	            fourthPageoffnetstdTable.setSpacingAfter(10f);
	            
	            cell = new PdfPCell(new Phrase("STD",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            cell.setColspan(6);
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Date",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Time",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Units",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetstdTable.addCell(cell);
	            
	            
	            List<UsageDTO> offnetSTDListObj = billInfoDTO.getOffnetSTDSummary();
	            
	            Iterator<UsageDTO> offnetSTDit = offnetSTDListObj.iterator();
	            while(offnetSTDit.hasNext())
	            {
	            	UsageDTO usageDTO =offnetSTDit.next();
	            	
	            	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);
	                
	                /*cell = new PdfPCell(new Phrase(usageDTO.getTime(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);*/
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);
	                
	                String duration=usageDTO.getDuration().replace(".00", "");
	                /*int hour=(Integer.parseInt(duration))/60;
	                int minutes=(Integer.parseInt(duration))%60;*/
	                
	                cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);
	                
	                /*cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);*/
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetstdTable.addCell(cell);
	            }
	            doc.add(fourthPageoffnetstdTable);
	            break;
			case "ISD":
				PdfPTable fourthPageoffnetisdTable = new PdfPTable(6);
	            fourthPageoffnetisdTable.setWidthPercentage(97f);
	            fourthPageoffnetisdTable.setWidths(new int[]{1,1,3,2,1,2});
	            fourthPageoffnetisdTable.setSpacingAfter(10f);
	            
	            cell = new PdfPCell(new Phrase("ISD",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            cell.setColspan(6);
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Date",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Time",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Units",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetisdTable.addCell(cell);
	            
	            
	            List<UsageDTO> offnetISDListObj = billInfoDTO.getOffnetISDSummary();
	            
	            Iterator<UsageDTO> offnetISDit = offnetISDListObj.iterator();
	            while(offnetISDit.hasNext())
	            {
	            	UsageDTO usageDTO =offnetISDit.next();
	            	
	            	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);
	                
	               /* cell = new PdfPCell(new Phrase(usageDTO.getTime(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);*/
	                
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);
	                
	                String duration=usageDTO.getDuration().replace(".00", "");
	                /*int hour=(Integer.parseInt(duration))/60;
	                int minutes=(Integer.parseInt(duration))%60;*/
	                
	                cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);
	                
	               /* cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);*/
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetisdTable.addCell(cell);
	            }
	            doc.add(fourthPageoffnetisdTable);
	            break;
			case "LANDLINE":
				PdfPTable fourthPageoffnetmobileTable = new PdfPTable(6);
	            fourthPageoffnetmobileTable.setWidthPercentage(97f);
	            fourthPageoffnetmobileTable.setWidths(new int[]{1,1,3,2,1,2});
	            fourthPageoffnetmobileTable.setSpacingAfter(10f);
	            
	            cell = new PdfPCell(new Phrase("Landline",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	            cell.setColspan(6);
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Date",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Time",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Units",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
	            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	            fourthPageoffnetmobileTable.addCell(cell);
	            
	            List<UsageDTO> offnetMobileListObj = billInfoDTO.getOffnetMobileSummary();
	            
	            Iterator<UsageDTO> offnetMobileDit = offnetMobileListObj.iterator();
	            while(offnetMobileDit.hasNext())
	            {
	            	UsageDTO usageDTO =offnetMobileDit.next();
	            	
	            	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);
	                
	                String duration=usageDTO.getDuration().replace(".00", "");
	                /*int hour=(Integer.parseInt(duration))/60;
	                int minutes=(Integer.parseInt(duration))%60;*/
	                
	                cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);
	                
	               /* cell = new PdfPCell(new Phrase(usageDTO.getDuration(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);*/
	                
	                cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);
	                
	                cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
	                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	                fourthPageoffnetmobileTable.addCell(cell);
	            }
	            doc.add(fourthPageoffnetmobileTable);
	            break;
			}
            
            /*doc.add(fourthPageitemisedtext);
            doc.add(fourthPageUsageChargesTable);*/
        }
        
        /******************************************* SECOND PAGE END*****************************************************************/
        
        /******************************************* THIRD PAGE START*****************************************************************/
        
     
        /******************************************* FOURTH PAGE END*****************************************************************/
        doc.close();
        LOGGER.info("PDFBuilder::generatePDFWithRevShare::Create PDF END");
         
    }

	public void generatePDFForEnterpriseWithRevShare(EnterpriseBillInfoDTO enterpriseBillInfoDTO, Document doc, PdfWriter writer) throws Exception {
		LOGGER.info("Inside generatePDFForEnterprise");
		LOGGER.info("srvc Tax : " + enterpriseBillInfoDTO.getServiceTax());
		BigDecimal srvcTax = new BigDecimal(0.0);
		BigDecimal gstTax = new BigDecimal(0.0);
		if(enterpriseBillInfoDTO.getServiceTax()!=null){
			 srvcTax=enterpriseBillInfoDTO.getServiceTax();
			 gstTax=srvcTax.divide(new BigDecimal("2"));
			gstTax=gstTax.setScale(2,BigDecimal.ROUND_DOWN);
		}
		
		 HeaderFooterPageEvent event = new HeaderFooterPageEvent(); 
	    	writer.setPageEvent(event);
        doc.open();
       
        //For Border
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBorderColor(BaseColor.LIGHT_GRAY); 
        canvas.rectangle(rect);
        
        PdfPCell cell;
       
        //A table2 for Customer Address 2241-2318
        PdfPTable table2 = new PdfPTable(3);
        table2.setWidthPercentage(97f);
        table2.setWidths(new int[]{3, 1, 1});
        table2.setSpacingAfter(10f);
        
        /**Added to Show month along with Tax Invoice*/
        int year = Integer.parseInt(enterpriseBillInfoDTO.getBillFromDate().substring(0, 4));
        int month = Integer.parseInt(enterpriseBillInfoDTO.getBillFromDate().substring(5, 7));
        int dueMonth = 0;
        int dueYear = 0;
        if(month == 12) {
        	dueMonth = 1;
        	dueYear = year + 1;
        }else {
        	dueMonth = month+1;
        	dueYear = year;
        }
        
        String duedate = null;
		if (month > 9) {
			duedate = "07-" + dueMonth + "-" + year;
		} else {
			duedate = "07-0" + dueMonth + "-" + year;
		}
        String billMonth = null;
        switch (month) {
		case 1:
			billMonth = "January";
			break;
		case 2:
			billMonth = "Febraury";
			break;
		case 3:
			billMonth = "March";
			break;
		case 4:
			billMonth = "April";
			break;
		case 5:
			billMonth = "May";
			break;
		case 6:
			billMonth = "June";
			break;
		case 7:
			billMonth = "July";
			break;
		case 8:
			billMonth = "August";
			break;
		case 9:
			billMonth = "September";
			break;
		case 10:
			billMonth = "October";
			break;
		case 11:
			billMonth = "November";
			break;
		case 12:
			billMonth = "December";
			break;
		}
        /***/
        
        
       // cell = new PdfPCell(new Phrase("Account Summary",headingBoldFont));
        /**Modified to Tax Invoice for the Month from Tax Invoice**/
        //cell = new PdfPCell(new Phrase("Tax Invoice",headingBoldFont));
        cell = new PdfPCell();
        Phrase text = new Phrase("Tax Invoice for the Month of ",headingFont);
        Phrase yearmonth = new Phrase(billMonth+","+year,headingBoldFont);
        text.add(yearmonth);
        Paragraph paragraph = new Paragraph(text);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setColspan(3);
        table2.addCell(cell);
        /****/
        
        List<BillInfoDTO> BillInfoDTOList1= enterpriseBillInfoDTO.getIndividualBillInfoList();
        Iterator<BillInfoDTO> BillInfoDTOIt1=BillInfoDTOList1.iterator();
        BillInfoDTO BillInfoDTOItObj1=null;
        while(BillInfoDTOIt1.hasNext()){
     	   
     	    BillInfoDTOItObj1 = BillInfoDTOIt1.next();
        }
        
        Phrase firstLine = new Phrase("Name And Address of Customer",headingBoldFont);
        Phrase secondLine = new Phrase(enterpriseBillInfoDTO.getFullName(),headingFont);
        Phrase thirdLine = new Phrase(enterpriseBillInfoDTO.getAddress(),headingFont);
        logger.info("enterpriseBillInfoDTO.getPhoneNumber(): "+enterpriseBillInfoDTO.getPhoneNumber());
        if(enterpriseBillInfoDTO.getPhoneNumber()!=null && !enterpriseBillInfoDTO.getPhoneNumber().isEmpty())
        {
        	/* if(enterpriseBillInfoDTO.getPhoneNumber().split(",").length>1)
             enterpriseBillInfoDTO.setPhoneNumber(enterpriseBillInfoDTO.getPhoneNumber().split(",")[0]);*/
        	String phoneArr[]=enterpriseBillInfoDTO.getPhoneNumber().split(",");
        	 if(phoneArr.length>0){
        		 enterpriseBillInfoDTO.setPhoneNumber(phoneArr[0]);
        	 }
        }
        else{
        	logger.info("Phone no is null so setting to empty");
        	enterpriseBillInfoDTO.setPhoneNumber("");
        }
        	Phrase fourthLine = new Phrase("Phone Number "+enterpriseBillInfoDTO.getPhoneNumber(),headingFont);
        	Phrase fifthLine = new Phrase("GST Number "+"N/A",headingFont);
        
        
        cell = new PdfPCell();
        cell.addElement(firstLine);
        cell.addElement(secondLine);
        cell.addElement(thirdLine);
        cell.addElement(fourthLine);
        cell.addElement(fifthLine);
        cell.setRowspan(7);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
       if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("E"))
    	   enterpriseBillInfoDTO.setBillType("Enterprise Customer ID");
       
       if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("C"))
    	   enterpriseBillInfoDTO.setBillType("Customer ID");
       
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getBillType(),headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(25f);
        /****/
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getEnterpriseCustomerId(),headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        /****/
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Payment Customer ID",headingFont));
        //cell.setColspan(2);
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(25f);
        /****/
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPaymentCustomerId(),headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        /****/
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Phone Number",headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(25f);
        /****/
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPhoneNumber(),headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        /****/
        table2.addCell(cell);
        
        /**Commented To hide Invoice Number, Invoice Date, Bill Period**/
        /*cell = new PdfPCell(new Phrase("Invoice Number",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("APSFL/TP/"+DateFormate.DateFormateYYYYMMwithslash(enterpriseBillInfoDTO.getBillDate())+"/"+enterpriseBillInfoDTO.getBillNumber(),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Invoice Date",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYYwithslash(enterpriseBillInfoDTO.getBillDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Bill Period",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);
        
        cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(enterpriseBillInfoDTO.getBillFromDate())+" to "+DateFormate.DateFormateDDMMYYY(enterpriseBillInfoDTO.getBillToDate()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        table2.addCell(cell);*/
        /****/
        
        cell = new PdfPCell(new Phrase("Payment Due Date",headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(25f);
        /****/
        table2.addCell(cell);
        
        /**Modified Due date to 7 of a month**/
        //cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(enterpriseBillInfoDTO.getDueDate()),headingFont));
        cell = new PdfPCell(new Phrase(duedate,headingFont));
        /**Modified to vertical center alignment**/
        //cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        /****/
        table2.addCell(cell);
        /****/
        
      //A table3 for Payment Details
        /**Modified to 5 columns from 6**/
        PdfPTable table3 = new PdfPTable(5);
        table3.setWidthPercentage(97f);
        table3.setWidths(new int[]{1,1,1,1,1});
        table3.setSpacingAfter(10f);
        /****/
        
        cell = new PdfPCell(new Phrase("Payment Details",headingBoldFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(5);//Modified to 5 from 6
        table3.addCell(cell);
        
        /**Modified to Previous Month Demand from Previous Balance**/
        cell = new PdfPCell(new Phrase("Previous Month Demand",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        /****/
        
        cell = new PdfPCell(new Phrase("Last Payment",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Balance Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        /**Commented to Remove Adjustments**/
        /*cell = new PdfPCell(new Phrase("Adjustments",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);*/
        /****/
        
        /**Modified to Current Due Amount From Current Bill Amount**/
        cell = new PdfPCell(new Phrase("Current Due Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Amount Payable",headingBoldFont));
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
        
        /**Commented to Remove Adjustments**/
        /*cell = new PdfPCell(new Phrase("D",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);*/
        /****/
        
        /**Modified to D from E**/
        cell = new PdfPCell(new Phrase("D",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        /****/
        
        /**Modified to D+C from E+C-D**/
        cell = new PdfPCell(new Phrase("D+C",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        /****/
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getPrevBalance()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getLastPayment()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getBalanceAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        /**Commented to Remove Adjustments**/
        /*cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAdjustments()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);*/
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getCurrentBillAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        
        /**Modified to Bold Font**/
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAmountPayable()),headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table3.addCell(cell);
        /****/
        
        /**Commented to Remove Last Payment**/
        /*//A table4 for Last Payment
        PdfPTable table4 = new PdfPTable(4);
        table4.setWidthPercentage(97f);
        table4.setWidths(new int[]{2,2,1,1});
        table4.setSpacingAfter(10f);
       
        cell = new PdfPCell(new Phrase("Last Payment",headingBoldFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setColspan(6);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Date",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table4.addCell(cell);
        
        List<PaymentDTO> paymentsListObj = enterpriseBillInfoDTO.getPayments();
        LOGGER.info("last payment count:"+paymentsListObj.size());
        Iterator<PaymentDTO> it = paymentsListObj.iterator();
        
        while(it.hasNext()){
        	PaymentDTO paymentDTOObj = it.next();
        	LOGGER.info("cafno:"+paymentDTOObj.getCafno()+" Description :"+paymentDTOObj.getDescription()+" date:"+paymentDTOObj.getDate()+" Amount:"+paymentDTOObj.getAmount());
	       	 cell = new PdfPCell(new Phrase(paymentDTOObj.getCafno(),headingFont));
	         cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	         table4.addCell(cell);
         
        	 cell = new PdfPCell(new Phrase(paymentDTOObj.getDescription(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             table4.addCell(cell);
             
             cell = new PdfPCell(new Phrase(paymentDTOObj.getDate(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             table4.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(paymentDTOObj.getAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             table4.addCell(cell);
        }*/
        /****/
        
        PdfPTable secondPageTable3 = new PdfPTable(5);
        secondPageTable3.setWidthPercentage(97f);
        secondPageTable3.setWidths(new int[]{2,1,1,1,1});
        secondPageTable3.setSpacingAfter(10f);
       
        cell = new PdfPCell(new Phrase("Summary of Current Charges - All Accounts",headingBoldFont));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setColspan(6);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Number",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Account Name",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount(Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Tax (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        secondPageTable3.addCell(cell);
        
        List<CurrentChargesSummaryDTO> CurrentChargesSummaryDTO = enterpriseBillInfoDTO.getAllAccountCurrentCharges();
        
        Iterator<CurrentChargesSummaryDTO> CCSDTOit = CurrentChargesSummaryDTO.iterator();
        
        while(CCSDTOit.hasNext()){
        	CurrentChargesSummaryDTO CurrentChargesSummaryDTOObj = CCSDTOit.next();
        	
	       	 cell = new PdfPCell(new Phrase(CurrentChargesSummaryDTOObj.getAccountNumber(),headingFont));
	         cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	         secondPageTable3.addCell(cell);
         
        	 cell = new PdfPCell(new Phrase(CurrentChargesSummaryDTOObj.getAccountName(),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(CurrentChargesSummaryDTOObj.getAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(CurrentChargesSummaryDTOObj.getTax()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
             
             cell = new PdfPCell(new Phrase(String.valueOf(CurrentChargesSummaryDTOObj.getTotalAmount()),headingFont));
             cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
             secondPageTable3.addCell(cell);
        }
        
        cell = new PdfPCell(new Phrase("Total Charges (Rs.)",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAllAccountCurrentChargesTotalAmount()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAllAccountCurrentChargesTotalTax()),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        /**Modified to Bold Font**/
        cell = new PdfPCell(new Phrase(String.valueOf(enterpriseBillInfoDTO.getAllAccountCurrentChargesTotalofTotalAmount()),headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        /****/
        
        cell = new PdfPCell(new Phrase("Tax Details Description",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CGST 9%",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(gstTax),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("SGST 9%",headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",headingBoldFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        cell = new PdfPCell(new Phrase(String.valueOf(gstTax),headingFont));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        secondPageTable3.addCell(cell);
        
        //doc.add(table1);
        doc.add(table2);
        doc.add(table3);
        /**Commented to Remove Last Payment**/
        //doc.add(table4);
        /****/
        doc.add(secondPageTable3);
        
        /***********************************************************3rd Page Starts******************************************************/
       List<BillInfoDTO> BillInfoDTOList= enterpriseBillInfoDTO.getIndividualBillInfoList();
       Iterator<BillInfoDTO> BillInfoDTOIt=BillInfoDTOList.iterator();
       while(BillInfoDTOIt.hasNext()){
    	   doc.newPage();
    	   BillInfoDTO BillInfoDTOItObj = BillInfoDTOIt.next();
    	   
    	 //A table2 for Customer Address 2794 - 2851 commented by gowthami
           PdfPTable ThridPageTable2 = new PdfPTable(3);
           ThridPageTable2.setWidthPercentage(97f);
           ThridPageTable2.setWidths(new int[]{3, 1, 1});
           ThridPageTable2.setSpacingAfter(10f);
           
           
           
         //  cell = new PdfPCell(new Phrase("Account Summary",headingBoldFont));
           /**Modified to Tax Invoice for the Month from Tax Invoice**/
           //cell = new PdfPCell(new Phrase("Tax Invoice",headingBoldFont));
           cell = new PdfPCell();
           cell.addElement(paragraph);
           //cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           cell.setColspan(3);
           ThridPageTable2.addCell(cell);
           /****/
           
           Phrase ThridPagefirstLine = new Phrase("Name And Address of Customer",headingBoldFont);
           Phrase ThridPagesecondLine = new Phrase(BillInfoDTOItObj.getFullName(),headingFont);
           Phrase ThridPagethirdLine = new Phrase(BillInfoDTOItObj.getAddress(),headingFont);
           Phrase ThridPagefourthLine = new Phrase("Phone Number :- "+enterpriseBillInfoDTO.getPhoneNumber(),headingFont);
           
           cell = new PdfPCell();
           cell.addElement(ThridPagefirstLine);
           cell.addElement(ThridPagesecondLine);
           cell.addElement(ThridPagethirdLine);
           cell.addElement(ThridPagefourthLine);
           cell.setRowspan(4);
           cell.setFixedHeight(78f);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
          
           if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("E"))
        	   enterpriseBillInfoDTO.setBillType("Enterprise Customer ID");
           
           if(enterpriseBillInfoDTO.getBillType().equalsIgnoreCase("C"))
        	   enterpriseBillInfoDTO.setBillType("Customer ID");
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getBillType(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getEnterpriseCustomerId(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Payment Customer ID",headingFont));
           //cell.setColspan(2);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPaymentCustomerId(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Account Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(BillInfoDTOItObj.getAccountNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Phone Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           
           cell = new PdfPCell(new Phrase(enterpriseBillInfoDTO.getPhoneNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           ThridPageTable2.addCell(cell);
           /**Commented to Remove Package Details**/
          /* //Table for Package Details
           PdfPTable thridPageTable3 = new PdfPTable(2);
           thridPageTable3.setWidthPercentage(97f);
           thridPageTable3.setWidths(new int[]{3,2});
           thridPageTable3.setSpacingAfter(10f);
          
           cell = new PdfPCell(new Phrase("Package Details",headingBoldFont));
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           thridPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Installation Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable3.addCell(cell);
           
           List<PackageDTO> packagesListObj = BillInfoDTOItObj.getPackages();
           
           Iterator<PackageDTO> it1 = packagesListObj.iterator();
           
           while(it1.hasNext()){
           	
           PackageDTO PackageDTOObj = it1.next();
           cell = new PdfPCell(new Phrase(PackageDTOObj.getDescription(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase(PackageDTOObj.getDate(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable3.addCell(cell);
           }*/
           /****/
           
           PdfPTable thridPageTable4 = new PdfPTable(2);
           thridPageTable4.setWidthPercentage(97f);
           thridPageTable4.setWidths(new int[]{2,1});
           thridPageTable4.setSpacingAfter(10f);
           
           cell = new PdfPCell(new Phrase("Summary of Current Month Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(" Amount (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Monthly  Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           SummaryDTO summaryDTOObj = BillInfoDTOItObj.getSummary();
           BigDecimal srvcTax1=new BigDecimal(0.0);
           BigDecimal gstTax1=new BigDecimal(0.0);
           if(summaryDTOObj.getTotalTax()!=null){
        	    srvcTax1=summaryDTOObj.getTotalTax();
                gstTax1=srvcTax1.divide(new BigDecimal("2"));
               gstTax1=gstTax1.setScale(2,BigDecimal.ROUND_DOWN);
           }
           
           TaxDTO taxDTOObj = BillInfoDTOItObj.getTaxDTO();
           logger.info("BillInfoDTOItObj.getRecurringCharges() : "+BillInfoDTOItObj.getRecurringCharges().size());
           BigDecimal totalRecCharge=new BigDecimal("0.00");
           if(BillInfoDTOItObj.getRecurringCharges()!=null && !BillInfoDTOItObj.getRecurringCharges().isEmpty())
           { 
        	   if(BillInfoDTOItObj.getRecurringCharges().size()>0){
        		   for(int i=0;i<BillInfoDTOItObj.getRecurringCharges().size();i++)
        		   {
        			   	BigDecimal recCharge=new BigDecimal("0.00");
        			    recCharge=BillInfoDTOItObj.getRecurringCharges().get(i).getCharges();
        			    totalRecCharge=totalRecCharge.add(recCharge);
        			    logger.info("recCharge : " + recCharge + "...... totalRecCharge : "+totalRecCharge);
        		   }
        	   }
        	   /**Commented**/
	           /*if(BillInfoDTOItObj.getRecurringCharges().size()>0){
	        	   cell = new PdfPCell(new Phrase(String.valueOf(totalRecCharge),headingFont));
	               cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	               thridPageTable4.addCell(cell);
	           }
	           else{
	        	   cell = new PdfPCell(new Phrase("0.00",headingFont));
	               cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	               thridPageTable4.addCell(cell);
	           }*/
           }/*else{
        	   cell = new PdfPCell(new Phrase("0.00",headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
               thridPageTable4.addCell(cell);
           }*/
           /*****/
           
           /**Modified Monthly Charges to APSFL Share based on charge Code SRVCRENT **/
           BigDecimal monthlycharge_apsfl = new BigDecimal("0.0");
           BigDecimal monthlycharge_thirdparty = new BigDecimal("0.0");
           BigDecimal telephonecharge_apsfl = new BigDecimal("0.0");
           BigDecimal telephonecharge_thirdparty = new BigDecimal("0.0");
           if(enterpriseBillInfoDTO.getCustomerType().equalsIgnoreCase("ENTERPRISE") && enterpriseBillInfoDTO.getCustomerSubType().equalsIgnoreCase("GOVT")) {
				cell = new PdfPCell(new Phrase(String.valueOf(totalRecCharge), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				thridPageTable4.addCell(cell);
			} else {
				logger.info("BillInfoDTOItObj.getRevenueShareDTO() : "+BillInfoDTOItObj.getRevenueShareDTO().size());
				RevenueShareDTO srvcrent = null;
				RevenueShareDTO stdusage = null;
				RevenueShareDTO localusage = null;

				if (BillInfoDTOItObj.getRevenueShareDTO() != null && !BillInfoDTOItObj.getRevenueShareDTO().isEmpty()) {
					if (BillInfoDTOItObj.getRevenueShareDTO().size() > 0) {
						srvcrent = BillInfoDTOItObj.getRevenueShareDTO().get("SRVCRENT");
						stdusage = BillInfoDTOItObj.getRevenueShareDTO().get("STDUSAGE");
						localusage = BillInfoDTOItObj.getRevenueShareDTO().get("LOCALUSAGE");

						if (srvcrent != null) {
							monthlycharge_apsfl = monthlycharge_apsfl.add(srvcrent.getApsflshare());
							monthlycharge_thirdparty = monthlycharge_thirdparty.add(srvcrent.getLmoshare())
									.add(srvcrent.getMsoshare());
						}

						if (stdusage != null) {
							telephonecharge_apsfl = telephonecharge_apsfl.add(stdusage.getApsflshare());
							telephonecharge_thirdparty = telephonecharge_thirdparty.add(stdusage.getLmoshare())
									.add(stdusage.getMsoshare());
						}

						if (localusage != null) {
							telephonecharge_apsfl = telephonecharge_apsfl.add(localusage.getApsflshare());
							telephonecharge_thirdparty = telephonecharge_thirdparty.add(localusage.getLmoshare())
									.add(localusage.getMsoshare());
						}
					}
				}
				cell = new PdfPCell(new Phrase(String.valueOf(monthlycharge_apsfl), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				thridPageTable4.addCell(cell);
           }
           /*****/
           
           logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());
           BigDecimal cpeEMICOST=new BigDecimal(0.00);
           if(summaryDTOObj.getChargeTypeFlag()!=null && !summaryDTOObj.getChargeTypeFlag().isEmpty()){
        	   if(summaryDTOObj.getChargeTypeFlag().equalsIgnoreCase("8")){
            	   cpeEMICOST=summaryDTOObj.getRecurringCharges();
        	   }
              }
           
           BigDecimal recCharge=new BigDecimal("0.00");
           if(BillInfoDTOItObj.getRecurringCharges()!=null && !BillInfoDTOItObj.getRecurringCharges().isEmpty()){
        	    recCharge=BillInfoDTOItObj.getRecurringCharges().get(0).getCharges();
           }
        	   
           BigDecimal totalCharge=totalRecCharge.add(summaryDTOObj.getUsageCharges()).add(summaryDTOObj.getInternetusageCharges()).add(summaryDTOObj.getValueAddedCharges()).add(summaryDTOObj.getOnetimeCharges()).add(summaryDTOObj.getDiscountsOrAdjustments()).add(cpeEMICOST);
           
           cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           /**Modified for Telephone Usage Charges as per Share**/
			if (enterpriseBillInfoDTO.getCustomerType().equalsIgnoreCase("ENTERPRISE")
					&& enterpriseBillInfoDTO.getCustomerSubType().equalsIgnoreCase("GOVT")) {
				cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getUsageCharges()), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				thridPageTable4.addCell(cell);
			} else {
				cell = new PdfPCell(new Phrase(String.valueOf(telephonecharge_apsfl), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				thridPageTable4.addCell(cell);
			}
           /*****/
           
           cell = new PdfPCell(new Phrase("Internet Usage Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getInternetusageCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Value Added Services",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getValueAddedCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
			/**Added New Row in Summary of Current Charges Table in PDF for Third Party Services**/
           if(enterpriseBillInfoDTO.getCustomerType().equalsIgnoreCase("ENTERPRISE") && enterpriseBillInfoDTO.getCustomerSubType().equalsIgnoreCase("GOVT")) {
        	   
           }else {
				cell = new PdfPCell(new Phrase("Third Party Services", headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				thridPageTable4.addCell(cell);

				BigDecimal thirdPartyService_charge = new BigDecimal("0.0");
				thirdPartyService_charge = thirdPartyService_charge.add(monthlycharge_thirdparty)
						.add(telephonecharge_thirdparty);

				cell = new PdfPCell(new Phrase(String.valueOf(thirdPartyService_charge), headingFont));
				cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				thridPageTable4.addCell(cell);
           }

			/****/
           
           /**Modified to CPE Rental Charges from Recurring Charges**/
           cell = new PdfPCell(new Phrase("CPE Rental Charges",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           logger.info("summaryDTOObj.getChargeTypeFlag()" +summaryDTOObj.getChargeTypeFlag()+ "summaryDTOObj.getRecurringCharges() :" +summaryDTOObj.getRecurringCharges());
           /****/

           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getOnetimeCharges().add(cpeEMICOST)),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getDiscountsOrAdjustments()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(totalCharge),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           /**Modified to Total Tax[CGST 9% + SGST 9%]**/
           cell = new PdfPCell(new Phrase("Total Tax [CGST 9% + SGST 9%]",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           /****/
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           /**Modified to Late Payment Fee From Late Payment**/
           cell = new PdfPCell(new Phrase("Late Payment Fee",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           /****/
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getLateFee()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           /**Modified to bold Font**/
           cell = new PdfPCell(new Phrase("Grand Total",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(summaryDTOObj.getTotalTax().add(summaryDTOObj.getLateFee()).add(totalCharge)),headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           /****/
           
           /**Commented to Remove Tax Details Description**/
           /*cell = new PdfPCell(new Phrase("Tax Details Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Amount (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("CGST 9%",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(gstTax1),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase("SGST 9%",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           thridPageTable4.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(gstTax1),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
           thridPageTable4.addCell(cell);*/
           
           /**Commented to Remove Monthly Charges table, Other Charges/ Discounts and Adjustments**/
         //Table3 for Package Details
           /*PdfPTable fourthPageTable3 = new PdfPTable(6);
           fourthPageTable3.setWidthPercentage(97f);
           fourthPageTable3.setWidths(new int[]{1,2,2,1,1,1});
           fourthPageTable3.setSpacingAfter(10f);
          
           cell = new PdfPCell(new Phrase("Monthly Charges",headingBoldFont));
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge Type",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Start Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("End Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable3.addCell(cell);
           
           List<RecurringDTO> recurringChargesListObj = BillInfoDTOItObj.getRecurringCharges();
           Iterator<RecurringDTO> recurringit = recurringChargesListObj.iterator();
           int i=1;
           while(recurringit.hasNext()){
           	RecurringDTO recurringDTOObj = recurringit.next();
           	
           	cell = new PdfPCell(new Phrase(String.valueOf(i),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(recurringDTOObj.getDescription(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(recurringDTOObj.getChargeType(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getStartDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(recurringDTOObj.getEndDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageTable3.addCell(cell);
               
               *//**Modified For Showing Monthly Charge as shown in Summary**//*
				if (recurringDTOObj.getChargeType().equalsIgnoreCase("Service Rental Charges")) {
					cell = new PdfPCell(new Phrase(String.valueOf(monthlycharge_apsfl), headingFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					fourthPageTable3.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(String.valueOf(recurringDTOObj.getCharges()), headingFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					fourthPageTable3.addCell(cell);
				}
               *//****//*
               
               i=i+1;
           }
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(BillInfoDTOItObj.getTotalRecurringCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fourthPageTable3.addCell(cell);
           
           PdfPTable fifthPageotherChargesTextTable = new PdfPTable(1);
           fifthPageotherChargesTextTable.setWidthPercentage(97f);
          
           cell = new PdfPCell(new Phrase("Other Charges / Discounts & Adjustments",bigBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setBorderColor(BaseColor.WHITE);
           fifthPageotherChargesTextTable.addCell(cell); 
           
           
           
           PdfPTable fifthPageVODChargesTable = new PdfPTable(4);
           fifthPageVODChargesTable.setWidthPercentage(97f);
           fifthPageVODChargesTable.setWidths(new int[]{1,3,2,2});
           fifthPageVODChargesTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("VOD / Movie Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("VOD/ Movie Name",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageVODChargesTable.addCell(cell);
           
           
          List<OtherChargesDTO> otherChargesListObj = BillInfoDTOItObj.getVodOrMovieCharges();
          
          Iterator<OtherChargesDTO> it2 = otherChargesListObj.iterator();
          int chargesSnoCount = 0;
          while(it2.hasNext())
          {
       	   OtherChargesDTO otherChargesDTO = it2.next();
       	   
       	   cell = new PdfPCell(new Phrase(String.valueOf(chargesSnoCount),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(otherChargesDTO.getVodName(),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(otherChargesDTO.getDate()),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(String.valueOf(otherChargesDTO.getCharges()),headingFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
              fifthPageVODChargesTable.addCell(cell);
              
              chargesSnoCount = chargesSnoCount+1;
          }
           
           PdfPTable fifthPageOneTimeChargesTable = new PdfPTable(4);
           fifthPageOneTimeChargesTable.setWidthPercentage(97f);
           fifthPageOneTimeChargesTable.setWidths(new int[]{1,3,2,2});
           fifthPageOneTimeChargesTable.setSpacingAfter(10f);
           
           cell = new PdfPCell(new Phrase("Recurring Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           
           List<OnetimeChargesDTO> oneTimeChargesList = BillInfoDTOItObj.getOnetimeCharges();
           
           Iterator<OnetimeChargesDTO> it3 = oneTimeChargesList.iterator();
           int onetimeChargesSnoCount = 1;
           while(it3.hasNext())
           {
           OnetimeChargesDTO onetimeChargesDTO = it3.next();
           cell = new PdfPCell(new Phrase(String.valueOf(onetimeChargesSnoCount),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(onetimeChargesDTO.getDescription(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(onetimeChargesDTO.getDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(onetimeChargesDTO.getCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           onetimeChargesSnoCount = onetimeChargesSnoCount+1;
           }
           
           cell = new PdfPCell(new Phrase("Total",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase(String.valueOf(BillInfoDTOItObj.getTotalOneTimeCharges()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           fifthPageOneTimeChargesTable.addCell(cell);
           
           PdfPTable fifthPageDisAdjChargesTable = new PdfPTable(4);
           fifthPageDisAdjChargesTable.setWidthPercentage(97f);
           fifthPageDisAdjChargesTable.setWidths(new int[]{1,3,2,2});
           fifthPageDisAdjChargesTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Discounts / Adjustments",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("S.No",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fifthPageDisAdjChargesTable.addCell(cell);
           
          List<AdjustmentDTO> discountsListObj =  BillInfoDTOItObj.getDiscOrAdjustemnts();
          
          Iterator<AdjustmentDTO> it4 = discountsListObj.iterator();
          int adjSNOCount = 0;
          while(it4.hasNext()){
       	   AdjustmentDTO adjustmentDTOObj = it4.next();
       	   
       	   cell = new PdfPCell(new Phrase(String.valueOf(adjSNOCount),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(adjustmentDTOObj.getDescription(),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(adjustmentDTOObj.getDate()),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              cell = new PdfPCell(new Phrase(String.valueOf(adjustmentDTOObj.getCharges()),headingBoldFont));
              cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              fifthPageDisAdjChargesTable.addCell(cell);
              
              adjSNOCount = adjSNOCount+1;
          }*/
           /****/
           
          // doc.add(table1);
           /**Commented to Remove Package Details, Monthly Charges table, Other Charges/ Discounts and Adjustments**/
           doc.add(ThridPageTable2);
           //doc.add(thridPageTable3);
           doc.add(thridPageTable4);
           //doc.add(fourthPageTable3);
           //doc.add(fifthPageotherChargesTextTable);
           //doc.add(fifthPageVODChargesTable);
           //doc.add(fifthPageOneTimeChargesTable);
           //doc.add(fifthPageDisAdjChargesTable);
           /****/
           
           /***********************************************************3rd Page End******************************************************/
           
           /***********************************************************4th Page Starts******************************************************/
           doc.newPage();
           
        //Table 4 for page4
           
           PdfPTable fourthPageTable1 = new PdfPTable(8);
           fourthPageTable1.setWidthPercentage(97f);
           fourthPageTable1.setWidths(new int[]{1, 1, 1,1,1,1,1,1});
           fourthPageTable1.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Account Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(BillInfoDTOItObj.getAccountNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Bill Number",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(BillInfoDTOItObj.getBillNumber(),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Bill Date",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(BillInfoDTOItObj.getBillDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Paymentet Due Date",headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(BillInfoDTOItObj.getDueDate()),headingFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageTable1.addCell(cell);
           
           PdfPTable fourthPageInternetTable = new PdfPTable(3);
           fourthPageInternetTable.setWidthPercentage(97f);
           fourthPageInternetTable.setWidths(new int[]{1,1,1});
           fourthPageInternetTable.setSpacingAfter(10f);
           
           cell = new PdfPCell(new Phrase("Internet Data Usage",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(3);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           fourthPageInternetTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Description",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageInternetTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units (GB)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageInternetTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration (HH:MM:SS)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           fourthPageInternetTable.addCell(cell); 	 	
           
           List<DataUsageDTO> dataUsageListObj = BillInfoDTOItObj.getDataUsages();
           Iterator<DataUsageDTO> dataUsageit = dataUsageListObj.iterator();
           
           while(dataUsageit.hasNext()){
           	DataUsageDTO dataUsageDTOObj = dataUsageit.next();
           	
           	cell = new PdfPCell(new Phrase(dataUsageDTOObj.getDescr(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageInternetTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getUnits()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageInternetTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(dataUsageDTOObj.getDuration()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               fourthPageInternetTable.addCell(cell); 	
           }
           
           PdfPTable sixthPagetelephonetext = new PdfPTable(1);
           sixthPagetelephonetext.setWidthPercentage(97f);
           sixthPagetelephonetext.setWidths(new int[]{1});
           sixthPagetelephonetext.setSpacingAfter(15f);
           
           cell = new PdfPCell(new Phrase("Telephone - "+BillInfoDTOItObj.getPhoneNumber(),bigBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setBorderColor(BaseColor.WHITE);
           sixthPagetelephonetext.addCell(cell);
           
           
           PdfPTable fourthPageitemisedtext = new PdfPTable(1);
           fourthPageitemisedtext.setWidthPercentage(97f);
           fourthPageitemisedtext.setWidths(new int[]{1});
           
           cell = new PdfPCell(new Phrase("Itemised Call Details",bigBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setBorderColor(BaseColor.WHITE);
           fourthPageitemisedtext.addCell(cell);
           
           
           PdfPTable sixthPageUsageChargesTable = new PdfPTable(4);
           sixthPageUsageChargesTable.setWidthPercentage(97f);
           sixthPageUsageChargesTable.setWidths(new int[]{2,1,1,2});
           sixthPageUsageChargesTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Telephone Usage Charges",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(4);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Type of Usage",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charges(Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageUsageChargesTable.addCell(cell);
           
           
           List<TelephoneUsageDTO> phoneusageListObj = BillInfoDTOItObj.getTelephoneUsages();
           Iterator<TelephoneUsageDTO> teleit = phoneusageListObj.iterator();
           while(teleit.hasNext())
           {
           	TelephoneUsageDTO telephoneUsageDTOObj = teleit.next();
           	
           	cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getTypeOfUsage(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(telephoneUsageDTOObj.getDuration(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(telephoneUsageDTOObj.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageUsageChargesTable.addCell(cell);
           }
           PdfPTable sixthPageoffnetlocalTable = new PdfPTable(6);
           sixthPageoffnetlocalTable.setWidthPercentage(97f);
           sixthPageoffnetlocalTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetlocalTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Local",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetlocalTable.addCell(cell);
           
           List<UsageDTO> offnetLocalListObj = BillInfoDTOItObj.getOffnetLocalSummary();
           
           Iterator<UsageDTO> offnetit = offnetLocalListObj.iterator();
           while(offnetit.hasNext())
           {
           	UsageDTO usageDTO =offnetit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetlocalTable.addCell(cell);
           }
           
           
           PdfPTable sixthPageoffnetstdTable = new PdfPTable(6);
           sixthPageoffnetstdTable.setWidthPercentage(97f);
           sixthPageoffnetstdTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetstdTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("STD",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetstdTable.addCell(cell);
           
           
           List<UsageDTO> offnetSTDListObj = BillInfoDTOItObj.getOffnetSTDSummary();
           
           Iterator<UsageDTO> offnetSTDit = offnetSTDListObj.iterator();
           while(offnetSTDit.hasNext())
           {
           	UsageDTO usageDTO =offnetSTDit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetstdTable.addCell(cell);
           }
           
           PdfPTable sixthPageoffnetisdTable = new PdfPTable(6);
           sixthPageoffnetisdTable.setWidthPercentage(97f);
           sixthPageoffnetisdTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetisdTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("ISD",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetisdTable.addCell(cell);
           
           
           List<UsageDTO> offnetISDListObj = BillInfoDTOItObj.getOffnetISDSummary();
           
           Iterator<UsageDTO> offnetISDit = offnetISDListObj.iterator();
           while(offnetISDit.hasNext())
           {
           	UsageDTO usageDTO =offnetISDit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetisdTable.addCell(cell);
           }
           
           PdfPTable sixthPageoffnetmobileTable = new PdfPTable(6);
           sixthPageoffnetmobileTable.setWidthPercentage(97f);
           sixthPageoffnetmobileTable.setWidths(new int[]{1,1,3,2,1,2});
           sixthPageoffnetmobileTable.setSpacingAfter(10f);
           
           
           cell = new PdfPCell(new Phrase("Landline",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           cell.setColspan(6);
           cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Date",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Time",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Called Number",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Duration",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Units",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           cell = new PdfPCell(new Phrase("Charge (Rs.)",headingBoldFont));
           cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           sixthPageoffnetmobileTable.addCell(cell);
           
           List<UsageDTO> offnetMobileListObj = BillInfoDTOItObj.getOffnetMobileSummary();
           
           Iterator<UsageDTO> offnetMobileDit = offnetMobileListObj.iterator();
           while(offnetMobileDit.hasNext())
           {
           	UsageDTO usageDTO =offnetMobileDit.next();
           	
           	cell = new PdfPCell(new Phrase(DateFormate.DateFormateDDMMYYY(usageDTO.getDate()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(DateUtil.toTime(usageDTO.getTime()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getCalledNumber(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               String duration=usageDTO.getDuration().replace(".00", "");
               cell = new PdfPCell(new Phrase(DateUtil.getDateFromMillis(duration),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(usageDTO.getUnits(),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
               
               cell = new PdfPCell(new Phrase(String.valueOf(usageDTO.getCharges()),headingFont));
               cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
               sixthPageoffnetmobileTable.addCell(cell);
           }
           
        
        
           doc.add(fourthPageTable1);
           //doc.add(fourthPageTable3);
           doc.add(fourthPageInternetTable);
           doc.add(sixthPagetelephonetext);
           doc.add(sixthPageUsageChargesTable);
           doc.add(sixthPageoffnetlocalTable);
           doc.add(sixthPageoffnetstdTable);
    	   doc.add(sixthPageoffnetisdTable);
    	   doc.add(sixthPageoffnetmobileTable);
    	   
    	   a.setSpacingBefore(10);
         //  a7.setSpacingAfter(10);
           
           a.setIndentationLeft(10);
           a1.setIndentationLeft(10);
           a2.setIndentationLeft(10);
           a3.setIndentationLeft(10);
           c3.setIndentationLeft(10);
           a4.setIndentationLeft(10);
           a5.setIndentationLeft(10);
           // a6.setIndentationLeft(10);
           a8.setIndentationLeft(10);
           a7.setIndentationLeft(10);
           
           b.setIndentationLeft(10);
           b2.setIndentationLeft(10);
           b4.setIndentationLeft(10);
           b8.setIndentationLeft(10);
           b5.setIndentationLeft(10);
           b6.setIndentationLeft(10);
           b7.setIndentationLeft(10);
           b9.setIndentationLeft(10);
           
           doc.add(a);
           doc.add(a1);
           doc.add(a2);
           doc.add(a3);
           doc.add(c3);
           doc.add(a4);
           doc.add(a5);
        //    doc.add(a6);
           doc.add(a8);
           doc.add(a7);
           
           doc.add(b);
           doc.add(b2);
           doc.add(b4);
           doc.add(b8);
           doc.add(b5);
           doc.add(b6);
           doc.add(b7);
           doc.add(b9);
           
           /***********************************************************4th Page End******************************************************/
	}
       doc.close();
	 }
	
}

