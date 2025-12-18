package ua.autoria.demo1.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.autoria.demo1.models.Model;

import java.util.List;

@Repository
public interface ModelDAO extends JpaRepository<Model,Integer> {
    List<Model> findAll();

}
