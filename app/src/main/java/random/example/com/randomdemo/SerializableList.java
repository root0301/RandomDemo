package random.example.com.randomdemo;


import java.io.Serializable;
import java.util.List;

public class SerializableList implements Serializable {

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}