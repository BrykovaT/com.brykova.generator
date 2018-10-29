package com.brykova.generator;

import com.brykova.generator.xml.Settings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Parser {

    private static final Charset CHARSET = StandardCharsets.UTF_16;

    public static List<String[]> parseTsv(File file) {
        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator(System.lineSeparator());
        TsvParser parser = new TsvParser(settings);
        return parser.parseAll(file, CHARSET);
    }

    public static Settings parseXml(File file) throws JAXBException {
        Settings result = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        result = (Settings) jaxbUnmarshaller.unmarshal(file);
        return result;
    }
}
