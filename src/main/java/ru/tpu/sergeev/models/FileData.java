package ru.tpu.sergeev.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files_info")
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", nullable = false)
    private  String path;

    @Column(name = "file_name_unique", nullable = false)
    private String filenameUnique;

    @Column(name = "file_name_original", nullable = false)
    private String filenameOriginal;

    @Column(name = "description", nullable = false)
    private String description;

}
