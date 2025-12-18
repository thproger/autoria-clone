package ua.autoria.demo1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.autoria.demo1.models.Model;
import ua.autoria.demo1.services.ModelService;

import java.util.List;

@RestController
@AllArgsConstructor
public class ModelController {
    private ModelService modelService;
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/models/add/{name}")
    public void addModel(@PathVariable String name) {
        modelService.add(name);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/models/delete/{name}")
    public void deleteModel(@PathVariable String name) {
        modelService.delete(name);
    }

    @GetMapping("/models/")
    public ResponseEntity<List<Model>> getModels() {
        return new ResponseEntity<>(modelService.getAll(), HttpStatus.OK);
    }
}
