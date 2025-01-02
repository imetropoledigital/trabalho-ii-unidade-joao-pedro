package com.fontes.bdnosqlapi.controller;

import com.fontes.bdnosqlapi.service.CollectionService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping(value = "/{collectionName}")
    public ResponseEntity<Document> addObject(@PathVariable String collectionName,
                                            @RequestBody Document json) {
        Document savedObject = collectionService.saveObject(collectionName, json);
        return ResponseEntity.ok(savedObject);
    }

    @GetMapping(value = "/{collectionName}")
    public ResponseEntity<List<Document>> getAllObjects(@PathVariable String collectionName) {
        List<Document> documents = collectionService.getAllObjects(collectionName);
        return ResponseEntity.ok(documents);
    }
}
