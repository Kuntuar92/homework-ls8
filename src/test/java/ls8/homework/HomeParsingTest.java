package ls8.homework;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeParsingTest {

        ClassLoader cl = HomeParsingTest.class.getClassLoader();


    @Test

    void zipHomeParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("Lesson.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        )
        {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String en = entry.getName();

                if (en.contains(".xlsx")){
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("Абдельдинов");
                    System.out.println("Faile:" + en);
            }
                else if (en.contains(".pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("Что должно быть сделано к сдаче дома");
                    System.out.println("Faile:" + en);
                }
                else if (en.contains(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(0)[0]).contains("1");
                    System.out.println("Faile:" + en );
                }
            }
        }
    }
}
