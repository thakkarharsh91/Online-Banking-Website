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
import org.springframework.web.servlet.ModelAndView;

import utilities.SendEmail;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/upload", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView crunchifySave(HttpServletRequest request,HttpServletResponse response,HttpSession session,
			@ModelAttribute("uploadForm") FileUpload uploadForm,
			Model map ) throws IllegalStateException, IOException {
		boolean success = false;
		ModelAndView model = new ModelAndView("uploadfile");

		List<MultipartFile> crunchifyFiles = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
			for (MultipartFile multipartFile : crunchifyFiles) {

				String fileName = multipartFile.getOriginalFilename();
				String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				System.out.println(extension);
				if(extension.equals("doc")||extension.equals("docx")||extension.equals("pdf")||extension.equals("png")||extension.equals("jpg")||extension.equals("jpeg"))
				{
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
					if (!"".equalsIgnoreCase(fileName)) {
						
						
						multipartFile
						.transferTo(new File(saveDirectory + fileName));
						fileNames.add(fileName);
						String firstname= (String)session.getAttribute("CUSTOMER_NAME");
						String accounttype = (String)session.getAttribute("ACCOUNT_TYPE");
						String email= (String)session.getAttribute("CUSTOMER_EMAIL");
						SendEmail sendemail=new SendEmail();
						sendemail.sendEmailApplication(firstname, "" , accounttype, email);
						
						
						map.addAttribute("files", fileNames);
						
						model.setViewName("uploadfilesuccess");
					}
				}
				else
				{ 
					System.out.println("Inside Else");

					model.addObject("Successful", " You are only allowed to uplaod the .doc, .docx, .pdf, .jpg, .png files");
					model.setViewName("uploadfile");
				}
			}
		}

		return model;
	}

}