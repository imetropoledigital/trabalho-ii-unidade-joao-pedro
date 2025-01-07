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
    public ResponseEntity<Document> addDocument(@PathVariable String collectionName,
                                            @RequestBody Document json) {
        Document savedObject = documentService.saveDocument(collectionName, json);
        return ResponseEntity.ok(savedObject);
    }

    // não aecita query json (encoded)
    @GetMapping(value = "/{collectionName}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable String collectionName,
                                                       @RequestParam(value = "query", required = false) String query) {
        List<Document> documents = documentService.getDocuments(collectionName, query);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(value = "/{collectionName}/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable String collectionName, @PathVariable String id) {
        Document document = documentService.getDocument(collectionName, id);
        return ResponseEntity.ok(document);
    }
}
