package dojo.spring.boot.model;

import lombok.Data;

@Data
public class Student {

    private String sid;
    private String name;
    private String gender;

    public Student() {

    }

    public Student(String sid, String name, String gender) {
        this.sid = sid;
        this.name = name;
        this.gender = gender;
    }
}
