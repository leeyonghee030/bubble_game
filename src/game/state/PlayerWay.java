package game.state;


//[enum] 플레이어의 방향 상태 관리
/// enum : 상수의 범주호를 만들떄 사용한다
///  boolean  두개 (left, right) 로 사용할수있지만
/// 둘 다 true 가 되면 잘못된 상태가 생길수 있다.
/// enum은 정해진 값중 하나만 가질수 있어 더 안전함
///
///  왜 사용할까?
/// 나의 프로잭트나 논리 안에서 범위를 지정하고 싶을떄 안전하게 사용 가눙하다
///
/// 사용방법
///  PlayerWay P = PlayerWay.LEFT
///  PlayerWay P = PlayerWay.RIGHT
///
public enum PlayerWay {

    LEFT,RIGHT
}
