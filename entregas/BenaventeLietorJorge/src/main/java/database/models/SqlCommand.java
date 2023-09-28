package database.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class SqlCommand {

    private final String command;
    private final List<Object> params = new ArrayList<>();

    public void addParam(Object param) {
        params.add(param);
    }


}
