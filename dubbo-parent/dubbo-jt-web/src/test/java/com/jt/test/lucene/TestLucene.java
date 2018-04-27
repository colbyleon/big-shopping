package com.jt.test.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class TestLucene {

    /**
     * 1. 创建索引的文件夹
     * 2. 定义文件输出的对象以及配置文件
     * 3. 创建文章对象
     * 4. 为文章添加索引的属性，并添加索引值 title :惠普战.....
     * 5. 对外输出索引文件
     */
    @Test
    public void createIndex() throws IOException {
       /* // 1. 创建索引的文件夹
        Directory directory = FSDirectory.open(new File("./index"));

        // 2. 创建一个分词器 标准分词器
        //Analyzer analyzer = new StandardAnalyzer();  国外的分词器不好用
        IKAnalyzer ikAnalyzer = new IKAnalyzer();     // 对中文支持良好

        // 3. 输出对象的版本的定义
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, ikAnalyzer);

        // 4. 定义输出对象
        IndexWriter writer = new IndexWriter(directory, config);

        // 5. 定义文档对象
        Document document = new Document();

        // 6. 为文章添加索引的属性和属性值
        *//**
         * Stored:是否存储，数据是否需要页面展现true
         * StringField:将属性的值全部当成一个索引
         * TextField：会将索引值分散
         *//*
        document.add(new LongField("itemId", 654564L, Field.Store.NO));
        document.add(new TextField("title", "有哪些比较好的中文分词方案？", Field.Store.YES));
        document.add(new TextField("salePoint", "本题已收录至知乎圆桌：机器之能 X 语言之美，更多「人工智能」相关话题欢迎关注讨论。", Field.Store.NO));

        writer.addDocument(document);
        writer.close();*/
    }
}
