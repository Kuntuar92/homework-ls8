package ls8.homework.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;

public class SelenideFilesTest {

    static {
        //Configuration.fileDownload = FileDownloadMode.FOLDER.PROXY; // нет атрибута href
    }

    @Test
    void SelenideDownloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedFile = $("#raw-url").download();
        try(InputStream is = new FileInputStream(downloadedFile);) {
            byte[] bytes = is.readAllBytes();
            String textContent = new String(bytes, StandardCharsets.UTF_8);
            assertThat(textContent).contains("This repository is the home of the next generation of JUnit");
        }
    }

    @Test
    void selenideUploadFile() {
        open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("cat.jpg");
        $("div.qq-file-info").shouldHave(text("cat.jpg"));
    }
}
