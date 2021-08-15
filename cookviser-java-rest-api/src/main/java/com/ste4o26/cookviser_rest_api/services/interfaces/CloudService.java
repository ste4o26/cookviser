package com.ste4o26.cookviser_rest_api.services.interfaces;

import com.ste4o26.cookviser_rest_api.exceptions.ImageNotUploadedException;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String uploadImage(MultipartFile multipartFile) throws ImageNotUploadedException;
}
