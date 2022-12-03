package ls8.homework;

import ls8.homework.model.Home;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

public class JacksonTest {

    @Test
    void jsonParseJacksonTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/home.json");
        Home home = objectMapper.readValue(file, Home.class);

        assertThat(home.numbers).contains(1);
        assertThat(home.card).isEqualTo("gold");
        assertThat(home.username.firstname).isEqualTo("Kurt");
        assertThat(home.username.lastname).isEqualTo("Cobain");
        assertThat(home.flag).isTrue();
    }

}
