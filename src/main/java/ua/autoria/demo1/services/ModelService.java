package ua.autoria.demo1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.autoria.demo1.dao.ModelDAO;
import ua.autoria.demo1.models.Model;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelService {
    private ModelDAO modelDAO;

    public void add(String name) {
        modelDAO.save(new Model(name));
    }

    public List<Model> getAll() {
        return modelDAO.findAll();
    }

    public void delete(String name) {
        modelDAO.delete(new Model(name));
    }
}
