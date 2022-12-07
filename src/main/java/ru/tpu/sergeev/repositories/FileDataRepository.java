package ru.tpu.sergeev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tpu.sergeev.models.FileData;

import java.util.List;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {
    List<FileData> findByDescriptionContainingIgnoreCase(String description);
}
