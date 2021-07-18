package com.ste4o26.cookviser_rest_api.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ste4o26.cookviser_rest_api.services.interfaces.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CloudServiceImpl implements CloudService {
    private static final String URL_KEY = "url";
    private static final String TEMP_FILE_PREFIX = "temp-file";
    private final Cloudinary cloudinary;

    @Autowired
    public CloudServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE_PREFIX, multipartFile.getOriginalFilename());

        return this.cloudinary
                .uploader()
                .upload(tempFile, ObjectUtils.emptyMap())
                .get(URL_KEY)
                .toString();
    }
}
