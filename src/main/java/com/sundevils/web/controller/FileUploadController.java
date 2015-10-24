package com.sundevils.web.controller;

import handlers.externaluserHandlers.FileUpload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String crunchifyDisplayForm() {
		return "uploadfile";
	}

	@RequestMapping(value = "/savefiles", method = RequestMethod.POST)
	public String crunchifySave(HttpServletRequest request,HttpServletResponse response,HttpSession session,
			@ModelAttribute("uploadForm") FileUpload uploadForm,
			Model map ) throws IllegalStateException, IOException {
		boolean success = false;
		String ssnvalue = (String)session.getAttribute("SSN_Value");
		System.out.println(ssnvalue);
		String saveDirectory = "C:/AccountOpeningDocuments/" + ssnvalue + "/";
		File directory = new File(saveDirectory); 
		if (directory.exists()) 
		{ 
			System.out.println("Directory already exists ..."); 
		} 
		else 
		{ 
			System.out.println("Directory not exists, creating now"); 
			success = directory.mkdirs(); 
			if (success)
			{ 
				System.out.printf("Successfully created new directory : %s%n", saveDirectory);
			} 
			else 
			{ 
				System.out.printf("Failed to create new directory: %s%n", saveDirectory); 
			} 
		}

		List<MultipartFile> crunchifyFiles = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
			for (MultipartFile multipartFile : crunchifyFiles) {

				String fileName = multipartFile.getOriginalFilename();
				if (!"".equalsIgnoreCase(fileName)) {
					// Handle file content - multipartFile.getInputStream()
					multipartFile
					.transferTo(new File(saveDirectory + fileName));
					fileNames.add(fileName);
				}
			}
		}

		map.addAttribute("files", fileNames);
		return "uploadfilesuccess";
	}
}