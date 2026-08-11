package My_test.ch04;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
public class Bubble extends JLabel {

    @Setter
    private int x;
    @Setter
    private int y;
    private Player player;
    private ImageIcon bubbleIcon;

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
        setLocation(x,y);
        setIcon(bubbleIcon);
        setSize(50,50);
    }




}
