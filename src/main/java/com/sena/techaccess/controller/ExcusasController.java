package com.sena.techaccess.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sena.techaccess.model.Excusas;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.service.IExcusasService;
import com.sena.techaccess.service.IFichaService;

@Controller
@RequestMapping("/Excusas")
public class ExcusasController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcusasController.class);

	@Autowired
	private IExcusasService excusasService;

	@Autowired
	private IFichaService fichaservice;
	
}

	