package com.fontes.bdnosqlapi.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Document saveDocument(String collectionName, Document document) {
        Document providedDocument = mongoTemplate.save(document, collectionName);
        String id = providedDocument.getObjectId("_id").toString();
        Document newDocument = new Document("_id", id);
        providedDocument.remove("_id");
        newDocument.putAll(providedDocument);
        return newDocument;
    }

    public List<Document> getAllDocuments(String collectionName) {
        return mongoTemplate.findAll(Document.class, collectionName).stream()
                .map(document -> {
                    String id = document.getObjectId("_id").toString();
                    document.remove("_id");
                    Document newDocument = new Document("_id", id);
                    newDocument.putAll(document);
                    return newDocument;
                })
                .collect(Collectors.toList());
    }

    public Document getDocument(String collectionName, String id) {
        Document document = mongoTemplate.findById(id, Document.class, collectionName);
        Document newDocument = new Document("_id", id);
        // doc pode ser nulo
        document.remove("_id");
        newDocument.putAll(document);
        return newDocument;
    }
}
