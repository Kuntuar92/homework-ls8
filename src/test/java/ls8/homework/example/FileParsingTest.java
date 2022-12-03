package ls8.homework.example;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import ls8.homework.model.Glossary;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;

public class FileParsingTest {

    ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void pdfParseTest() throws Exception{
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedPDF = $("a[href='junit-user-guide-5.9.1.pdf']").download();
        try(InputStream is = new FileInputStream(downloadedPDF)){
            com.codeborne.pdftest.PDF content = new PDF(downloadedPDF);
            assertThat(content.author).contains("Sam Brannen");
        }
    }

    @Test
    void xslxParseTest() throws Exception {
        try (InputStream resourceAsStream = cl.getResourceAsStream("burabai.xlsx")) {
            XLS content = new XLS(resourceAsStream);
            assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("Абдельдинов");
        }
    }


    @Test

    void csvParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("qaguru.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(resource)))
        {
            List<String[]> content = reader.readAll();

            assertThat(content.get(0)[1]).contains("lesson");
        }

    }

    @Test

    void zipParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("tst.zip");
                ZipInputStream zis = new ZipInputStream(resource))
        {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).isEqualTo("test.csv");
            }
        }
    }

    @Test
    void jsonParseTest() throws Exception {
        Gson gson = new Gson();
        try (
                InputStream resource = cl.getResourceAsStream("glossary.json");
                InputStreamReader reader = new InputStreamReader(resource)) {

            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            assertThat(jsonObject.get("title").getAsString()).isEqualTo("example glossary");
            assertThat(jsonObject.get("GlossDiv").getAsJsonObject().get("title").getAsString()).isEqualTo("S");
            assertThat(jsonObject.get("GlossDiv").getAsJsonObject().get("flag").getAsBoolean()).isTrue();
        }
    }

    @Test
    void jsonParseImprovedTest() throws Exception {
        Gson gson = new Gson();
        try (
                InputStream resource = cl.getResourceAsStream("glossary.json");
                InputStreamReader reader = new InputStreamReader(resource)) {

            Glossary jsonObject = gson.fromJson(reader, Glossary.class);
            assertThat(jsonObject.title).isEqualTo("example glossary");
            assertThat(jsonObject.glossDiv.title).isEqualTo("S");
            assertThat(jsonObject.glossDiv.flag).isTrue();
        }
    }
}
