package com.goormthon.demo.controller;

import com.goormthon.demo.common.BaseResponse;
import com.goormthon.demo.service.S3Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.goormthon.demo.common.BaseExceptionStatus.SUCCESS;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class UploadController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    @ApiOperation(value = "파일 업로드", notes = "단일 파일 업로드")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "4001-지원하지않는파일, 4002-업로드실패"),
            @ApiResponse(responseCode = "500", description = "서버 예외")
    })
    public ResponseEntity<BaseResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String msg = s3Service.uploadFile(multipartFile, "content");

        BaseResponse baseResponse = new BaseResponse(SUCCESS);
        baseResponse.setMessage(msg);

        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/uploads")
    @ApiOperation(value = "파일 업로드", notes = "다중 파일 업로드")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "4001-지원하지않는파일, 4002-업로드실패"),
            @ApiResponse(responseCode = "500", description = "서버 예외")
    })
    public ResponseEntity<BaseResponse> uploadFiles(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
        s3Service.uploadFiles(multipartFiles, "content");

        return ResponseEntity.ok(new BaseResponse(SUCCESS));
    }
}
