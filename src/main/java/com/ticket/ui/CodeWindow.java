package com.ticket.ui;

import javax.swing.*;

public class CodeWindow extends JFrame {

    public CodeWindow(String title, byte[] img) {
        initUI(title, img);
    }

    private void initUI(String title, byte[] img) {
        this.setSize(300, 230);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(new ImageIcon(img));
        this.add(jLabel);
        this.setVisible(true);
    }

}
