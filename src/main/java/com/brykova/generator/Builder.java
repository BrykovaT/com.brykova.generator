package com.brykova.generator;

import com.brykova.generator.xml.Column;
import com.brykova.generator.xml.Settings;

import java.util.Collections;
import java.util.List;

public class Builder {
    /**
     * формируем отчет
     * @param settings параметры страницы
     * @param data данные
     * @return строка выходного отчета
     */
    public static String generate(Settings settings, List<String[]> data) {
        Integer pageWidth = settings.getPage().getWidth();
        Integer pageHeight = settings.getPage().getHeight();

        StringBuilder builder = new StringBuilder();
        List<String[]> restData = addPage(builder, settings.getColumns(), data, pageWidth, pageHeight);
        while (restData != null && !restData.isEmpty()) {
            builder.append("~");
            builder.append(System.lineSeparator());
            restData = addPage(builder, settings.getColumns(), restData, pageWidth, pageHeight);
        }
        return builder.toString();
    }

    /**
     * формируем страницу отчета
     * @return список необработанный список строк
     */
    private static List<String[]> addPage(StringBuilder builder, List<Column> columns, List<String[]> data, Integer pageWidth, Integer pageHeight) {
        List<String[]> result = null;
        int curHeight = addHeader(builder, columns);
        curHeight += addRowSeparator(builder, pageWidth);
        for (int y = 0; y < data.size(); y++) {
            int neededHeight = calcRowHeight(data.get(y), columns) + 1;
            if (curHeight + neededHeight <= pageHeight) {
                addRow(builder, columns, data.get(y));
                addRowSeparator(builder, pageWidth);
                curHeight += neededHeight;
            } else {
                result = data.subList(y, data.size());
                break;
            }
        }
        return result;
    }

    /**
     * вычисляем высоту строки данных
     * @return максимальная высота, которую будет занимать строка
     */
    private static int calcRowHeight(String[] row, List<Column> columns) {
        int maxHeight = 0;
        for(int i = 0; i < row.length; i++) {
            int height = calcCellHeight(row[i], columns.get(i).getWidth());
            if (maxHeight < height) {
                maxHeight = height;
            }
        }
        return maxHeight;
    }

    /**
     * вычисляем высоту ячейки
     * @return высота ячейки
     */

    private static int calcCellHeight(String word, Integer width) {
        if (word.length() <= width) {
            return 1;
        } else {
            return (int) Math.ceil(word.length() * 1.0 / width);
        }
    }

    /**
     * формируем данные строки
     */
    private static void addRow(StringBuilder builder, List<Column> columns, String[] array) {
        generateRow(builder, columns, array);
    }

    private static int generateRow(StringBuilder builder, List<Column> columns, String[] array) {
        int rowHeight = calcRowHeight(array, columns);
        for (int y = 0; y < rowHeight; y++) {
            builder.append("|");
            for (int i = 0; i < array.length; i++) {
                builder.append(" ");
                String word;
                if (array[i].length() > columns.get(i).getWidth()) {
                    int endIndex = Math.min((y + 1) * columns.get(i).getWidth(), array[i].length());
                    int beginIndex = y * columns.get(i).getWidth();
                    if (beginIndex > endIndex) {
                        word = "";
                    } else {
                        word = array[i].substring(beginIndex, endIndex).trim();
                    }
                } else if (y == 0) {
                    word = array[i];
                } else {
                    word = "";
                }
                builder.append(word);
                if (columns.get(i).getWidth() > word.length()) {
                    builder.append(String.join("", Collections.nCopies(columns.get(i).getWidth() - word.length(), " ")));
                }
                builder.append(" |");
            }
            builder.append(System.lineSeparator());
        }
        return rowHeight;
    }

    /**
     * формируем заголовок колонки
     * @return высоту заголовка
     */
    private static int addHeader(StringBuilder builder, List<Column> columns) {
        String[] array = new String[columns.size()];
        for(int i = 0; i < array.length; i++) {
            array[i] = columns.get(i).getTitle();
        }
        return generateRow(builder, columns, array);
    }

    /**
     * формируем разделитель строк данных
     * @return высоту разделителя
     */
    private static int addRowSeparator(StringBuilder builder, Integer width) {
        builder.append(String.join("", Collections.nCopies(width, "-")));
        builder.append(System.lineSeparator());
        return 1;
    }
}
