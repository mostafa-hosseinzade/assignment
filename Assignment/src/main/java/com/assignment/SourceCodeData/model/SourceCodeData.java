package com.assignment.SourceCodeData.model;


import com.assignment.SourceCodeData.dto.SourceCodeDataDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "SOURCE_CODE_DATA")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceCodeData implements Serializable {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(
            name = "SOURCE_CODE_DATA_SEQ",
            sequenceName = "SOURCE_CODE_DATA_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "SOURCE_CODE_DATA_SEQ",
            strategy = GenerationType.SEQUENCE)
    private Long id;

    private String source;

    private String codeListCode;

    @Column(unique = true, nullable = false)
    private String code;

    private String displayValue;

    private String longDescription;

    private Date fromDate;

    private Date toDate;

    private Integer sortingPriority;

    public SourceCodeDataDto getTransferObject(){
        return SourceCodeDataDto.builder()
                .id(getId())
                .source(getSource())
                .codeListCode(getCodeListCode())
                .code(getCode())
                .displayValue(getDisplayValue())
                .longDescription(getLongDescription())
                .fromDate(getFromDate())
                .toDate(getToDate())
                .sortingPriority(getSortingPriority())
                .build();
    }

    public SourceCodeData adaptTransferToDomainObject(SourceCodeDataDto sourceCodeDataDto){
        this.setId(sourceCodeDataDto.getId());
        this.setSource(sourceCodeDataDto.getSource());
        this.setCodeListCode(sourceCodeDataDto.getCodeListCode());
        this.setCode(sourceCodeDataDto.getCode());
        this.setDisplayValue(sourceCodeDataDto.getDisplayValue());
        this.setLongDescription(sourceCodeDataDto.getLongDescription());
        this.setFromDate(sourceCodeDataDto.getFromDate());
        this.setToDate(sourceCodeDataDto.getToDate());
        this.setSortingPriority(sourceCodeDataDto.getSortingPriority());
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SourceCodeData sourceCodeData = (SourceCodeData) obj;
        if(getId() == null || sourceCodeData.getId() == null){
            return false;
        }
        return sourceCodeData.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}
