package com.synesis.mofl.lnm.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;
/**
*
* @author Md. Emran Hossain
* @since 17 02, 2022
* @version 1.1
*/
@Getter
@Setter
public class FileMainRequest {

    private UUID id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String filetitle;

    @Size(min = 5, max = 300)
    private String filepath;
}
