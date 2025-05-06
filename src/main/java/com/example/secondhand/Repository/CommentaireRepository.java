package com.example.secondhand.Repository;
import com.example.secondhand.Entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByAnnonceId(Long annonceId);
    List<Commentaire> findByParentId(Long parentId);
}