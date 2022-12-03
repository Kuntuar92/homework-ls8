package ls8.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Home {
    public ArrayList<Integer> numbers;
    public Boolean flag;
    public String card;
    @SerializedName("Obj")
    public Username username;


    public static class Username{
        public String firstname;
        public String lastname;

    }
}
