package _Test01;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame extends JFrame {

    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() {
        initData();
        setInitLayout();
        addEventListener();
    }

    private void initData() {
        setTitle("버블버블");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundMap = new JLabel(new ImageIcon("images/backgroundMap.png"));
        setContentPane(backgroundMap); //루트 패넣에 JLabel 설정
        player = new Player();


    }


    private void setInitLayout() {
        setLocale(null);
        setResizable(false);
        setLocationRelativeTo(null); //JFrame 화면 가운데 배치

        add(player);
        setVisible(true);
    }

    private void addEventListener() {
        //프레임에 키보드 리스너 등록
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keCode" + e.getKeyCode());

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT :
                    player.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                    player.right();
                        break;
                    case KeyEvent.VK_UP:
                    player.up();
                        break;
                    case KeyEvent.VK_DOWN:
                    player.down();
                        break;

                }


            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }


    //테스트코드
    public static void main(String[] args) {
        new BubbleFrame();
    }


}
