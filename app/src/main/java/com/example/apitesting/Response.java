package com.example.apitesting;

import java.util.List;

public class Response {
  List<Data> data;

    public List<Data> getData(String name) {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
