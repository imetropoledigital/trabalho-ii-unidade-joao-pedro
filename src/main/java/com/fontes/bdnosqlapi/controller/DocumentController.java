package com.fontes.bdnosqlapi.controller;

import com.fontes.bdnosqlapi.service.DocumentService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/collections")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/{collectionName}")
    public ResponseEntity<Document> addObject(@PathVariable String collectionName,
                                            @RequestBody Document json) {
        Document savedObject = documentService.saveDocument(collectionName, json);
        return ResponseEntity.ok(savedObject);
    }

    @GetMapping(value = "/{collectionName}")
    public ResponseEntity<List<Document>> getAllObjects(@PathVariable String collectionName) {
        List<Document> documents = documentService.getAllDocuments(collectionName);
        return ResponseEntity.ok(documents);
    }


}
