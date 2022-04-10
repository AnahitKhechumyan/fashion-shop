package com.example.fashionshop.controller;
import com.example.fashionshop.model.commons.Image;
import com.example.fashionshop.model.dto.requestDto.ResponseDto;
import com.example.fashionshop.service.ImageService;
import com.example.fashionshop.service.ProductService;
import com.example.fashionshop.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/image")
public class ImageController {
    private final String IMAGE_URL_MAPPING_POST_FIX = "/get";

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add/{product_id}")
    void addImage(@PathVariable("product_id") long productId,
                  @RequestParam("image") MultipartFile[] multipartFile,
                  @RequestHeader String userId) {
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is unauthorized, please sign in first:"
            );
        }
        String serverUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String requestMapping = this.getClass().getAnnotation(RequestMapping.class).value()[0];
        String imageMappingPath = serverUrl+ "/" +requestMapping + IMAGE_URL_MAPPING_POST_FIX;

        imageService.saveImagesToFolder(productId, multipartFile, imageMappingPath);
    }

    @GetMapping(value = "/get/{folder_name}/{img_name}")
    ResponseEntity<byte[]> getImagesByProductId(@PathVariable("folder_name") String folderName,
                                                @PathVariable("img_name") String imageName) throws IOException {

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageService.readByFolderNameAndImageName(folderName, imageName));
    }

    @PutMapping("/update/{product_id}")
    ResponseEntity<ResponseDto> update(@PathVariable("product_id") long productId,
                                       @RequestParam("image") MultipartFile[] images,
                                       @RequestHeader String userId){
        if (!UserValidator.checkUserAuthorized(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "user is unauthorized, please sign in first:"
            );
        }
        Image updated = imageService.update(productId, images);
        System.out.println(updated);
        ResponseDto responseDto = new ResponseDto("Image updated.");
        responseDto.addInfo("productId", String.valueOf(productId));
        return ResponseEntity.ok(responseDto);

    }
}