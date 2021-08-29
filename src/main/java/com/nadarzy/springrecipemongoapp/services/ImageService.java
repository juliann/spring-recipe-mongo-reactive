package com.nadarzy.springrecipemongoapp.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {
  public Mono<Void> saveImageFile(String id, MultipartFile multipartFile);
}
