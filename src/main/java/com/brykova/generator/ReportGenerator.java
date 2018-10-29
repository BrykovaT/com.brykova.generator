package com.brykova.generator;

import com.brykova.generator.xml.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

public class ReportGenerator {

    private static final String REPORT_NAME = "Report.txt";
    private static final Charset CHARSET = StandardCharsets.UTF_16;

    public static void main(String[] args) throws Exception {

        if (args == null || args.length == 0) {
            throw new RuntimeException("нужно задать параметры таблицы и настроек!");
        }
        File file = Paths.get(args[0]).toFile();
        File file2 = Paths.get(args[1]).toFile();

//        File file = Paths.get("source-data.tsv").toFile();
//        File file2 = Paths.get("settings.xml").toFile();

        try {
            List<String[]> allRows = Parser.parseTsv(file);
            Settings settings = Parser.parseXml(file2);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(REPORT_NAME), CHARSET));
            writer.write(Builder.generate(settings, allRows));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
