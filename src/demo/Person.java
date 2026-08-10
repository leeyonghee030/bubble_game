package demo;

import lombok.*;

//@Setter
//@Getter
//@ToString
//단축버전
@Data   // 무지성으로 만들면 안됨
@NoArgsConstructor // 빈생성자
@AllArgsConstructor // 모든 멤벼변수 값 받는 생성자
public class Person {

    private String name;
    private  Integer age;

}
