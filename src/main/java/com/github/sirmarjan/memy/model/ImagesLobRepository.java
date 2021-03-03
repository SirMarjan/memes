package com.github.sirmarjan.memy.model;

import com.github.sirmarjan.memy.model.entities.ImageLob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImagesLobRepository extends JpaRepository<ImageLob, Long> {
}
