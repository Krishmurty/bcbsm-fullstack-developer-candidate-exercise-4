package com.krish.bcbs.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.krish.bcbs.model.ImageFileDao;
import com.krish.bcbs.service.ImageFileService;

@RestController
@CrossOrigin()
public class ImageFileController {

	@Autowired
	ImageFileService imageFileService;
	
    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam("title") String title, 
    		  @RequestParam("image") MultipartFile image) throws IOException{
    	String id = imageFileService.addFile(title, image);
        return id;
    }
    
    @RequestMapping(value = "/imageFiles/{id}", method = RequestMethod.GET)
    public Model getPhoto(@PathVariable String id, Model model) {
    	ImageFileDao img = imageFileService.getImagFile(id);
        model.addAttribute("title", img.getFileName());
        model.addAttribute("image", 
          Base64.getEncoder().encodeToString(img.getImage().getData()));
        //return "photos";
        return model;
    }
}
