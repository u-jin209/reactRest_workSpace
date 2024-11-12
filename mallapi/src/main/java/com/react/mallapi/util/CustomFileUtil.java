package com.react.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${com.react.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempDir = new File(uploadPath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        uploadPath = tempDir.getAbsolutePath();

        log.info("===============================");
        log.info("Upload path: " + uploadPath);
    }

    public List<String> saveFile(List<MultipartFile> files) throws RuntimeException{
        log.info(files);
        List<String> uploadNames = new ArrayList<>();

        if(files == null || files.size() == 0) {
            log.info("none file");
            return null;
        }

        for(MultipartFile file : files){
            log.info(file.getOriginalFilename());
            String savedName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(file.getInputStream(), savePath);
                String contentType = file.getContentType();

                if(contentType != null || contentType.startsWith("image")){
                    Path thumbnailPath = Paths.get(uploadPath, "s_"+savedName);
                    Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumbnailPath.toFile());
                }

                uploadNames.add(savedName);
            }catch (IOException e){;
                throw new RuntimeException(e);
            }
        }


        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if(!resource.isReadable()){
            resource = new FileSystemResource(uploadPath+File.separator+"default.png");
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()) );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFile(List<String> fileNames) {
        if(fileNames == null || fileNames.size() == 0) {
            return;
        }
        fileNames.forEach(fileName -> {
            System.out.println(fileName);

            if(fileName!="C:\\Users\\USER\\reactRest_workSpace\\upload\\default.png"){

                //썸네일 삭제
                String thumbnailFileName = "s_"+fileName;

                Path thumbnailFilePath = Paths.get(uploadPath ,thumbnailFileName);
                Path filePath = Paths.get(uploadPath,fileName);

                try {
                    Files.deleteIfExists(filePath);
                    Files.deleteIfExists(thumbnailFilePath);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

        });
    }


}
