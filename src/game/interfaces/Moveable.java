package game.interfaces;

public interface Moveable {

    void left();

    void right();

    //Adapter 클래스가 너무 많이 생겨서 default 문법을 인터페이스에서
    //사용할 수 있도록 만들어 줬다
    //즉 default 키워드를 사용하묜 인터페이스안에 일반 메서드도 구현할수 있다

    default void up() {};

    default void down() {};

}
