package com.example.demo.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "Todo")
@NoArgsConstructor
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id; // 이 오브젝트의 아이디
    private String userId; // 이 오브젝트를 생성한 유저의 아이디
    private String title;
    private boolean done; // true - todo를 완료한 경우(checked)

    @Builder
    public TodoEntity(String userId, String title, boolean done) {
        this.userId = userId;
        this.title = title;
        this.done = done;
    }


}
