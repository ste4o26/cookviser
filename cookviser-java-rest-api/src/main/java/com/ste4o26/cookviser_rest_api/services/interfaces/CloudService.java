package com.ste4o26.cookviser_rest_api.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {
    String uploadImage(MultipartFile multipartFile) throws IOException;
}
