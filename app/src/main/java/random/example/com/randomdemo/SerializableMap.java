package random.example.com.randomdemo;

import java.io.Serializable;
import java.util.Map;

public class SerializableMap implements Serializable {

    private Map<String,Integer> map;

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}