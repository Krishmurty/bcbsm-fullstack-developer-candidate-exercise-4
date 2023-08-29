package com.krish.bcbs.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.krish.bcbs.model.ImageFileDao;

public interface ImageFileRepository extends MongoRepository<ImageFileDao, String> { }
