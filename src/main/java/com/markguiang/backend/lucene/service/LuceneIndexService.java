package com.markguiang.backend.lucene.service;

import com.markguiang.backend.event.model.Event;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LuceneIndexService {
    private final Directory directory;

    public LuceneIndexService(Directory directory) {
        this.directory = directory;
    }

    public void indexEvent(Event event) throws IOException {
        Analyzer indexAnalyzer = CustomAnalyzer.builder()
                .withTokenizer("standard")
                .addTokenFilter("lowercase")
                .addTokenFilter("edgeNGram", "minGramSize", "3", "maxGramSize", "10")
                .build();

        IndexWriterConfig config = new IndexWriterConfig(indexAnalyzer);
        try (IndexWriter writer = new IndexWriter(directory, config)) {
            Document doc = new Document();

            if (event.getEventId() != null)
                doc.add(new StringField("eventId", String.valueOf(event.getEventId()), Field.Store.YES));
            if (event.getName() != null)
                doc.add(new TextField("name", event.getName(), Field.Store.YES));
            if (event.getDescription() != null)
                doc.add(new TextField("description", event.getDescription(), Field.Store.YES));
            if (event.getLocation() != null)
                doc.add(new TextField("location", event.getLocation(), Field.Store.YES));
            if (event.getImgURL() != null)
                doc.add(new StringField("imgURL", event.getImgURL(), Field.Store.YES));
            if (event.getEventStatus() != null)
                doc.add(new StringField("eventStatus", event.getEventStatus().name(), Field.Store.YES));

            writer.updateDocument(new Term("eventId", String.valueOf(event.getEventId())), doc);
        }
    }
}
