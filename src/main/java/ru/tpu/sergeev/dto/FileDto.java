package ru.tpu.sergeev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String path;
    private String filenameOriginal;
    private String filenameUnique;
    private String description;
}
