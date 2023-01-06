package com.LeadTo.Soft.controllers;

import java.io.IOException;

import javax.print.PrintException;

import com.LeadTo.Soft.models.PrintModel;
import com.LeadTo.Soft.models.impresion;
import com.LeadTo.Soft.services.PrintServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	PrintServices printService;
	@Autowired
	impresion i;

	@CrossOrigin(origins = { "https://localhost:8080", "http://www.ta2.mx", "https://www.ta2.mx", "https://www.ta1.mx",
			"http://leadtosoftmexico.club" })
	@PostMapping(path = "/imprime", consumes = { MediaType.APPLICATION_JSON_VALUE })

	public PrintModel imprime(@RequestBody impresion i) throws IOException, PrintException {
		Logger logger = Logger.getLogger(UsuarioController.class.getName());

		// Create a file handler object
		FileHandler handler = new FileHandler("logs.txt");
		handler.setFormatter(new SimpleFormatter());

		// Add file handler as
		// handler of logs
		logger.addHandler(handler);

		// set Logger level()
		logger.setLevel(Level.FINER);

		// set Logger level()
		logger.setLevel(Level.FINER);

		// call throwing method with class
		// name = GFG and method name = main
		// and IO Exception as Thrown object
		logger.throwing(UsuarioController.class.getName(), UsuarioController.class.getMethods()[0].getName(),
				new IOException());
		return printService.obtenerUsuarios(i);

	}

//	 @CrossOrigin(origins = "http://leadtosoftmexico.club")
//		@PostMapping(path = "/imprimelead",
//	    consumes = {MediaType.APPLICATION_JSON_VALUE})
//
//		public PrintModel imprimelead(@RequestBody  impresion i) throws IOException, PrintException {
//			return printService.obtenerUsuarios(i);
//		}
//	
}