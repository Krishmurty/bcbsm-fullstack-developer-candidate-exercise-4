package com.krish.bcbs.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ImageFile")
public class ImageFileDao {
  @Id
  private String id;

  @NotBlank
  @Size(max = 30)
  private String fileName;

  private Binary image;

  public ImageFileDao() {
  }

  public ImageFileDao(String fileName) {
    this.fileName = fileName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

public Binary getImage() {
	return image;
}

public void setImage(Binary image) {
	this.image = image;
}


}
