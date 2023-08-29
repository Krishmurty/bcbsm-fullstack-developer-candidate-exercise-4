package com.krish.bcbs.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.krish.bcbs.model.ImageFileDao;
import com.krish.bcbs.repository.ImageFileRepository;

@Service
public class ImageFileService {

	@Autowired
    private ImageFileRepository imageFileRepo;
	
	public String addFile(String title, MultipartFile file) throws IOException{
		ImageFileDao photo = new ImageFileDao(title); 
        photo.setImage(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        photo = imageFileRepo.insert(photo); 
        return photo.getId(); 
		//return userDao.save(newUser);
		
	}

	public ImageFileDao getImagFile(String id) {
		return imageFileRepo.findById(id).get(); 
	}

}
