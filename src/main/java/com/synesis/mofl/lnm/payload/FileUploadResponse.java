package com.synesis.mofl.lnm.payload;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
/**
*
* @author Md. Emran Hossain
* @since 17 02, 2022
* @version 1.1
*/
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileUploadResponse implements Serializable {

    private static final long serialVersionUID = -8466851069088978415L;
    private Boolean successFlag;
    private String message;
    private FileMainRequest data;
    private Long count;
}