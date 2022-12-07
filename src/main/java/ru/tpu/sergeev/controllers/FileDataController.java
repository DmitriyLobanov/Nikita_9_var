package ru.tpu.sergeev.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tpu.sergeev.dto.FileDto;
import ru.tpu.sergeev.services.FileDataService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.List;

@Controller
@RequestMapping("/files")
@AllArgsConstructor
public class FileDataController {

    private final FileDataService fileDataService;

    @GetMapping("/upload")
    public String showUploadPage(Model model) {
        model.addAttribute("fileDto", new FileDto());
        return "upload-page";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file, FileDto fileDto) {
        fileDataService.storeFile(file, fileDto);
        return "redirect:/files";
    }

    @GetMapping("/download")
    public void downloadFile(FileDto fileDto, HttpServletRequest request,
                                               HttpServletResponse response ) {

        System.out.println(fileDto);
        File file = new File(fileDto.getPath());
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"" + fileDto.getFilenameOriginal() + "\""));
            response.setContentLength((int) file.length());
            try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                FileCopyUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @GetMapping()
    public String getFilesList(@RequestParam(defaultValue = "") String searchString, Model model) {
        List<FileDto> fileDtoList = fileDataService.searchByDescription(searchString);
        model.addAttribute("fileList", fileDtoList);
        model.addAttribute("fileDto", new FileDto());
        return "files-list";
    }
}
