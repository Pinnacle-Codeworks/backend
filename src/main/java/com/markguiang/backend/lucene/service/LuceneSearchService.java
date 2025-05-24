package com.markguiang.backend.lucene.service;

import com.markguiang.backend.event.enum_.EventStatus;
import com.markguiang.backend.event.model.Event;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LuceneSearchService {
    private final Directory directory;

    public LuceneSearchService(Directory directory) {
        this.directory = directory;
    }

    public Page<Event> eventSearch(String queryStr, Pageable pageable) throws Exception {
        List<Event> results = new ArrayList<>();

        Analyzer analyzer = CustomAnalyzer.builder()
                .withTokenizer("standard")
                .addTokenFilter("lowercase")
                .build();

        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            MultiFieldQueryParser parser = new MultiFieldQueryParser(
                    new String[]{"name", "description", "location"}, analyzer
            );

            TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(queryStr));
            CharTermAttribute charTermAttr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            List<String> tokens = new ArrayList<>();
            while (tokenStream.incrementToken()) {
                tokens.add(charTermAttr.toString());
            }
            tokenStream.end();
            tokenStream.close();

            // Build fuzzy query for each token and each field
            BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
            for (String token : tokens) {
                for (String field : new String[]{"name", "description", "location"}) {
                    FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term(field, token), 2); // maxEdits = 2
                    finalQuery.add(fuzzyQuery, BooleanClause.Occur.SHOULD);
                }
            }

            Query query = finalQuery.build();

            int page = pageable.getPageNumber();
            int size = pageable.getPageSize();
            int start = page * size;
            int end = start + size;

            TopDocs topDocs = searcher.search(query, end);
            ScoreDoc[] hits = topDocs.scoreDocs;

            if (start >= hits.length) return Page.empty(pageable);

            for (int i = start; i < Math.min(end, hits.length); i++) {
                Document doc = searcher.doc(hits[i].doc);
                Event event = new Event();

                if (doc.get("eventId") != null) {
                    event.setEventId(Long.parseLong(doc.get("eventId")));
                    event.setName(doc.get("name"));
                    event.setDescription(doc.get("description"));
                    event.setLocation(doc.get("location"));
                    event.setImgURL(doc.get("imgURL"));
                    event.setEventStatus(EventStatus.valueOf(doc.get("eventStatus")));
                }
                results.add(event);
            }

            return new PageImpl<>(results, pageable, topDocs.totalHits.value);
        }
    }
}
