package com.assignment.SourceCodeData.service;

import com.assignment.SourceCodeData.dao.ISourceCodeDataRepository;
import com.assignment.SourceCodeData.dto.SourceCodeDataDto;
import com.assignment.SourceCodeData.exceptions.CodeValueRequiredExceptions;
import com.assignment.SourceCodeData.model.SourceCodeData;
import com.assignment.SourceCodeData.utils.CSVHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SourceCodeDataServiceTest {

    @Mock
    private ISourceCodeDataRepository iSourceCodeDataRepository;

    @Mock
    private CSVHelper csvHelper;

    private ISourceCodeDataService iSourceCodeDataService;

    @BeforeEach
    void setUp() throws Exception {
        iSourceCodeDataService = new SourceCodeDataService(iSourceCodeDataRepository, csvHelper);
    }

    @Test
    void save() {
        SourceCodeData sourceCodeData = SourceCodeData.builder()
                .source("test")
                .codeListCode("123sad")
                .code("12345")
                .displayValue("asd")
                .longDescription("asasdasd")
                .fromDate(new Date())
                .toDate(new Date())
                .sortingPriority(1)
                .build();

        iSourceCodeDataService.save(sourceCodeData.getTransferObject());

        ArgumentCaptor<SourceCodeData> userArgumentCaptor = ArgumentCaptor.forClass(SourceCodeData.class);
        verify(iSourceCodeDataRepository).save(userArgumentCaptor.capture());
        SourceCodeData captureUser = userArgumentCaptor.getValue();

        Assertions.assertThat(captureUser).isEqualTo(sourceCodeData);
    }

    @Test
    void findAll() {
    }

    @Test
    void findByCode() {
        String code = "1234";

        Assertions.assertThatThrownBy(() -> iSourceCodeDataService.findByCode(code))
                .isEqualTo(null);
    }

    @Test
    void deleteAll() {
    }

    @Test
    void saveAllWithUploadedCsvFile() {
    }
}