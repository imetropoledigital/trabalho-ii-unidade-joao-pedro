package com.fontes.bdnosqlapi.service;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
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

    //caso a query esteja vazia, irá retornar todos os documentos da coleção
    public List<Document> getDocuments(String collectionName, String queryJson, String fields) {
        Document query = (queryJson != null && !queryJson.isEmpty())
                ? Document.parse(queryJson)
                : new Document();

        Document projection = new Document();
        if(fields != null && !fields.isEmpty()) {
            String[] fieldArray = fields.split(",");
            for(String field : fieldArray) {
                if(field.startsWith("-")) {
                    projection.put(field.substring(1), 0);
                }
                else {
                    projection.put(field, 1);
                }
            }
        }
        return mongoTemplate.find(new BasicQuery(query, projection), Document.class, collectionName).stream()
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

    public Document updateDocument(String collectionName, String id, Document newDocument) {
        ObjectId objectId = new ObjectId(id);
        newDocument.put("_id", objectId);
        mongoTemplate.save(newDocument, collectionName);
        newDocument.remove("_id");
        Document returnedDocument = new Document("_id", id);
        returnedDocument.putAll(newDocument);
        return returnedDocument;
    }
}
