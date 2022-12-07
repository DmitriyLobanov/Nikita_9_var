package ru.tpu.sergeev.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tpu.sergeev.controllers.AuthController;
import ru.tpu.sergeev.dto.FileDto;
import ru.tpu.sergeev.models.FileData;
import ru.tpu.sergeev.repositories.FileDataRepository;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileDataService {
    private final FileDataRepository fileDataRepository;
    public static Logger slf4jLogger = LoggerFactory.getLogger(FileDataService.class);

    private FileDto mapFileDataToFileDto(FileData fileData) {
        FileDto fileDto = new FileDto();
        fileDto.setId(fileData.getId());
        fileDto.setFilenameOriginal(fileData.getFilenameOriginal());
        fileDto.setPath(fileData.getPath());
        fileDto.setFilenameUnique(fileData.getFilenameUnique());
        fileDto.setDescription(fileData.getDescription());
        return fileDto;
    }

    public void deleteFileDataById(Long id) {
        slf4jLogger.info("Delete file with id {}", id);
        fileDataRepository.deleteById(id);
    }

    public List<FileDto> findAll() {
        return fileDataRepository.findAll().stream().map(this::mapFileDataToFileDto).collect(Collectors.toList());
    }
    public void storeFile(MultipartFile multipartFile, FileDto fileDto) {
        String fileNameOriginal = multipartFile.getOriginalFilename();
        String fileNameUnique = UUID.randomUUID().toString();
        String extension = null;
        if (fileNameOriginal != null) {
            extension = fileNameOriginal.substring((fileNameOriginal.lastIndexOf(".") + 1));
        }
        fileNameUnique = fileNameUnique + "." + extension;
        String pathname = "src/main/resources/uploaded_files/";
        File folder = new File(pathname);
        if (!folder.exists()) {
            folder.mkdir();
        }
        pathname = pathname + fileNameUnique;
        slf4jLogger.info("Сохранение файла {} по пути {}", multipartFile.getOriginalFilename(), pathname);
        File target = new File(pathname);
        try (OutputStream os = new FileOutputStream(target)) {
            InputStream is = multipartFile.getInputStream();
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            os.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileData fileData = new FileData(null, pathname, fileNameUnique, fileNameOriginal, fileDto.getDescription());
        fileDataRepository.save(fileData);

    }

    public List<FileDto> searchByDescription(String description) {
        List<FileData> fileDtoList = fileDataRepository.findByDescriptionContainingIgnoreCase(description);
        return fileDtoList.stream().map(this::mapFileDataToFileDto).collect(Collectors.toList());
    }
}
