package com.LeadTo.Soft.services;

import java.awt.Color;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LeadTo.Soft.*;
import com.LeadTo.Soft.models.PrintModel;
import com.LeadTo.Soft.models.Productos;
import com.LeadTo.Soft.models.impresion;

@Service
public class PrintServices {
	@Autowired
	PrintModel printModel;

	public PrintModel obtenerUsuarios(impresion p) throws IOException, PrintException {
		
		PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
		patts.add(Sides.DUPLEX);
		//PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
		PrintService ps = findPrintService(p.getN_impresora());
		Path tempFile = Files.createTempFile(p.getFolio(), ".txt");
		List<String> content = new ArrayList<>();
		content.add(p.getFolio());
		content.add(p.getFecha());
		content.add(p.getMesa());
		for (Productos prod : p.getProductos()) {
			content.add(prod.getCantidad()+"-"+prod.getProducto());
			content.add(prod.getObservaciones());
			
		}
		content.add("");
		content.add("");

		
	    Files.write(tempFile, content, StandardOpenOption.APPEND);
		FileInputStream fis = new FileInputStream(tempFile.toString());
		Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
		DocPrintJob pj = ps.createPrintJob();
		pj.print(pdfDoc, patts);
		fis.close();

		byte[] space = { 27, 100, 5 };
		espacio(space, p);
		 byte[] cortar = { 0x1B, 0x69 };
		 corte(cortar, p);
		//cashdrawerOpen();

		printModel.setExito("se imprimio correctamente");
		return printModel;
	}

	public String espacio(byte[] espacio,impresion p) {
		PrintService[] psEspacio = PrintServiceLookup.lookupPrintServices(null, null);
		PrintService printer = null;
		// search printer
		printer =findPrintService(p.getN_impresora());

		DocPrintJob jobEspacio = printer.createPrintJob();
		DocFlavor flavorCut = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(espacio, flavorCut, null);
		try {
			jobEspacio.print(doc, null);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "Se dio correctamente el espacio";
	}

	public String cashdrawerOpen() {

		byte[] open = { 27, 112, 48, 55, 121 };
 
		byte[] cutter = {29, 86,49};
		PrintService printer = null;
		// search printer
		printer = PrintServiceLookup.lookupDefaultPrintService();

		PrintServiceAttributeSet printserviceattributeset = new HashPrintServiceAttributeSet();
		printserviceattributeset.add(new PrinterName(printer.getName(), null));
		PrintService[] printservice = PrintServiceLookup.lookupPrintServices(null, printserviceattributeset);
		if (printservice.length != 1) {
			System.out.println("Printer not found");
		}
		PrintService pservice = printservice[0];
		DocPrintJob job = pservice.createPrintJob();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(open, flavor, null);
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		try {

			job.print(doc, aset);
		} catch (PrintException ex) {
			System.out.println(ex.getMessage());
			return ex.getMessage();
		}
		return "La caja se abrio correctamente";
	}

	public void corte(byte[] corte,impresion p) {
		try {
			DocPrintJob job = findPrintService(p.getN_impresora()).createPrintJob();
			byte[] bytes = { 27, 109 };
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			Doc doc = new SimpleDoc(bytes, flavor, null);
			job.print(doc, null);
		} catch (PrintException ex) {

		}
	}

	private static PrintService findPrintService(String printerName) {
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printService : printServices) {
			if (printService.getName().trim().equals(printerName)) {
				return printService;
			}
		}
		return null;
	}

	
}
