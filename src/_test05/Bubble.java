package _test05;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
public class Bubble extends JLabel implements Moveable {

    private int x;
    private int y;
    private Player player;
    private ImageIcon bubbleIcon;

    //버블 이동 상태 플래그
    private static final int HORIZONTAL_DISTANCE = 400; //버플 수평 이동 거리
    private static final int BUBBLE_SPEED_MS = 1; //버블 이동 간격
    private static final int SCREEN_TOP = 0; //화면 상단 경계

    private boolean leftMoving;
    private boolean rightMoving;
    private boolean upMoving;

    @Setter
    private boolean bubbleLeftEnd;
    @Setter
    private boolean bubbleRightEnd;


    public Bubble(Player player) {
        this.player = player;
        initData();
        setInitLayout();
    }

    private void initData() {
        bubbleIcon = new ImageIcon("images/bubble.png");


    }

    private void setInitLayout() {
        x = player.getX();
        y = player.getY();
        setLocation(x, y);
        setIcon(bubbleIcon);
        setSize(50, 50);
    }


    @Override
    public void left() {
        leftMoving = true;
        new Thread(() -> {
            BackgroundPlayerService service = new BackgroundPlayerService(this.player);
            for (int i = 0; i < HORIZONTAL_DISTANCE; i++) {
                bubbleLeftEnd = service.isRed(x,y);
                if (bubbleLeftEnd){
                    break;
                }
                x--;
                setLocation(x, y);
                try {
                    Thread.sleep(BUBBLE_SPEED_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            leftMoving = false;
            upMoving = true;
            new Thread(()->{
                    while (y >= SCREEN_TOP) {
                        y--;
                        setLocation(x,y);
                        try {
                            Thread.sleep(BUBBLE_SPEED_MS);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                    }

                }
                upMoving = false;
            }).start();
        }).start();

    }

    @Override
    public void right() {

        rightMoving = true;

        new Thread(() -> {
            BackgroundPlayerService service = new BackgroundPlayerService(this.player);
            for (int i = 0; i < HORIZONTAL_DISTANCE; i++) {
                bubbleRightEnd = service.isRed(x+50,y);
                if (bubbleRightEnd){
                    break;
                }
                x++;
                setLocation(x, y);
                try {
                    Thread.sleep(BUBBLE_SPEED_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            rightMoving = false;
            upMoving = true;
            new Thread(()->{
                if (upMoving) {
                    while (y >= SCREEN_TOP) {
                        y--;
                        setLocation(x,y);
                        try {
                            Thread.sleep(BUBBLE_SPEED_MS);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                upMoving = false;
            }).start();
        }).start();

    }
}
