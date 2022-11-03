package com.assignment.SourceCodeData.web.rest;

import com.assignment.SourceCodeData.dto.SourceCodeDataDto;
import com.assignment.SourceCodeData.service.ISourceCodeDataService;
import com.assignment.SourceCodeData.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/sourcedata")
@AllArgsConstructor
public class SourceCodeDataRestController {

    private final ISourceCodeDataService iSourceCodeDataService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFileAndSaveData(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            iSourceCodeDataService.saveAllWithUploadedCsvFile(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file because: " + e.getMessage() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<SourceCodeDataDto>> findAll(){
       return ResponseEntity.ok(iSourceCodeDataService.findAll());
    }

    @GetMapping("/find-by-code/{codes}")
    public ResponseEntity<SourceCodeDataDto> findByCode(@PathVariable("codes") String code){
        return ResponseEntity.ok(iSourceCodeDataService.findByCode(code));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAll(){
        return ResponseEntity.ok(iSourceCodeDataService.deleteAll());
    }


}
