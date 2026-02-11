package com.geovannycode.springfly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Order(1) 
public class DocumentIngestionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DocumentIngestionService.class);

    private final VectorStore vectorStore;

    @Value("classpath:rag/springfly-terms-of-service.md")
    private Resource termsOfServiceResource;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) {
        log.info("Starting document ingestion for RAG...");

        try {
            // Load terms of service document
            TextReader textReader = new TextReader(termsOfServiceResource);
            textReader.getCustomMetadata().put("source", "terms-of-service");
            textReader.getCustomMetadata().put("type", "policy");

            List<Document> documents = textReader.get();
            log.info("Loaded {} documents from terms of service", documents.size());

            // Split documents into chunks for better retrieval
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.apply(documents);
            log.info("Split into {} chunks", chunks.size());

            // Store in vector database
            vectorStore.add(chunks);
            log.info("Successfully ingested {} document chunks into vector store", chunks.size());

        } catch (Exception e) {
            log.error("Error during document ingestion", e);
            log.warn("RAG functionality may be limited without vector store data");
        }
    }
}
