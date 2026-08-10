package My_test.ch02;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
public class Player extends JLabel implements Moveable {

    private int x;
    private int y;

    private ImageIcon playerR;
    private ImageIcon playerL;

    //플레이어 속도 상태
    private final int SPEED = 4;
    private final int JUMP_SPEED = 2;

    //플레이어의 움직임 상태
    @Setter
    private boolean left;
    @Setter  // 개인 setter / 필드에 적으면
    private boolean right;
    private boolean up;
    private boolean down;

    public Player() {
        initData();
        setInitLayout();
    }

    private void initData() {
        x = 55;
        y = 535;
        //명시적으로 할당
        left = false;
        right = false;
        up = false;
        down = false;

        playerR = new ImageIcon("images/playerR.png");
        playerL = new ImageIcon("images/playerL.png");


    }

    private void setInitLayout() {
        setSize(50, 50);
        setLocation(x, y);
        setIcon(playerR);
        right = true;

    }


    //Alt+ ins 메소드 구현
    @Override
    public void left() {
        left = true;
        setIcon(playerL);

        new Thread(() -> {
            while (left) {
                x -= SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


    }

    @Override
    public void right() {
        right = true;
        setIcon(playerR);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (right) {
                    x += SPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

    @Override
    public void up() {
        new Thread(() -> {
            up = true;
            for (int i = 0; i < 50; i++) {
                y -= JUMP_SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int j = 0; j < 50; j++) {
                y += JUMP_SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            up = false;
        }).start();
    }

    @Override
    public void down() {

    }
}
