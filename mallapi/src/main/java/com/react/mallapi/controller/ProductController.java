package com.react.mallapi.controller;

import com.react.mallapi.dto.PageRequestDTO;
import com.react.mallapi.dto.PageResponseDTO;
import com.react.mallapi.dto.ProductDTO;
import com.react.mallapi.service.ProductService;
import com.react.mallapi.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

    @PostMapping("/")
    public Map<String, Long> register(ProductDTO productDTO){
        log.info("register : " + productDTO);
        List<MultipartFile> files = productDTO.getFiles();
        List<String> uploadedFileNames = fileUtil.saveFile(files);

        productDTO.setUploadFileNames(uploadedFileNames);
        log.info("uploadedFileNames : " + uploadedFileNames);
        Long pno = productService.register(productDTO);

        return Map.of("RESULT", pno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> view(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){
        return productService.getList(pageRequestDTO);
    }


    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno){
        return productService.get(pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable("pno") Long pno, ProductDTO productDTO){
        productDTO.setPno(pno);

        ProductDTO oldProductDTO = productService.get(pno);

        List<MultipartFile> files = productDTO.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFile(files);

        List<String> uploadedFileNames = fileUtil.saveFile(files);

        if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()){
            uploadedFileNames.addAll(currentUploadFileNames);
        }
        productService.modify(productDTO);

        List<String> oldFileNames = oldProductDTO.getUploadFileNames();

        if(oldFileNames != null && oldFileNames.size() > 0){
            List<String> removeFileNames =
            oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName)  == -1).collect(Collectors.toList());

            fileUtil.deleteFile(removeFileNames);
        }

        return Map.of("RESULT", "SUCCESS");

    }

    @DeleteMapping("/{pno}")
    public Map<String, String> delete(@PathVariable("pno") Long pno){

        List<String> oldFileNames = productService.get(pno).getUploadFileNames();

        productService.remove(pno);
        fileUtil.deleteFile(oldFileNames);

        return Map.of("RESULT", "SUCCESS");
    }

}
