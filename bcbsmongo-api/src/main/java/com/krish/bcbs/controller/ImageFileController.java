package com.krish.bcbs.controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
    @ResponseBody
    public ResponseEntity<ImageFileDao> getImages(@PathVariable String id) {
    	ImageFileDao img = imageFileService.getImagFile(id);
        //model.addAttribute("title", img.getFileName());
        //model.addAttribute("image", Base64.getEncoder().encodeToString(img.getImage().getData()));
        //return model;
    	return new ResponseEntity<>(img,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/imageFileAsBytes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImagesAsBytes(@PathVariable String id) {
    	ImageFileDao img = imageFileService.getImagFile(id);
        //model.addAttribute("title", img.getFileName());
        //model.addAttribute("image", Base64.getEncoder().encodeToString(img.getImage().getData()));
        //return model;
    	return new ResponseEntity<>(img.getImage().getData(),HttpStatus.OK);
    }
    
    @RequestMapping(value = "/zipImageFiles", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> getZipImages(@RequestParam String fileIdList, HttpServletResponse response) {
    	List<String> strFilesId = new ArrayList<>(Arrays.asList(fileIdList.split(",")));
    	    	
    	return ResponseEntity.ok()
    			.header("Content-Disposition","attachment;filename=\"zippedFiles.zip\"")
    			.contentType(MediaType.valueOf("application/zip"))
    			.body(out -> build(strFilesId,response.getOutputStream()));
        //model.addAttribute("title", img.getFileName());
        //model.addAttribute("image", Base64.getEncoder().encodeToString(img.getImage().getData()));
        //return model;
    	//return new ResponseEntity<>(img,HttpStatus.OK);
    }
    
    public ZipOutputStream build(List<String> strFilesId, ServletOutputStream responseOutputStream) throws IOException {

    	try(ZipOutputStream zipOutputStream = new ZipOutputStream(responseOutputStream)) {
            for(String strFileID: strFilesId) {
            	ImageFileDao img = imageFileService.getImagFile(strFileID.trim());
                ByteArrayResource fileSystemResource = new ByteArrayResource(img.getImage().getData());
                ZipEntry zipEntry = new ZipEntry(img.getFileName());
                zipEntry.setSize(fileSystemResource.contentLength());
                zipEntry.setTime(System.currentTimeMillis());

                zipOutputStream.putNextEntry(zipEntry);

                StreamUtils.copy(fileSystemResource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
        	return zipOutputStream;
        } catch (IOException ioe) {
            throw ioe;
        }
    }
}
