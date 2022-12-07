package ru.tpu.sergeev.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tpu.sergeev.dto.FileDto;
import ru.tpu.sergeev.services.FileDataService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final FileDataService fileDataService;

    @RequestMapping
    public String getAllFiles(Model model) {
        List<FileDto> files = fileDataService.findAll();
        model.addAttribute("files", files);
        model.addAttribute("fileDto", new FileDto());
        return "admin-page";
    }

    @PostMapping
    public String removeFile(FileDto fileDto) {
        Path path = Path.of(fileDto.getPath());
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileDataService.deleteFileDataById(fileDto.getId());
        return "redirect:/admin";
    }
}
