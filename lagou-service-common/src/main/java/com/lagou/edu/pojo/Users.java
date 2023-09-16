package com.lagou.edu.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name="users")
@Accessors(chain = true)
public class Users {
    @Id
    private Long id;
    private String username;
    private String password;
    //private List<Resume> resumeList;
}
