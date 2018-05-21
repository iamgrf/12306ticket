package com.ticket.ui;

import com.ticket.service.TicketCore;
import com.ticket.util.Constant;
import com.ticket.util.Http;
import com.ticket.util.Point;
import com.ticket.util.Route;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class LoginCodeView extends JFrame {

    private TicketCore ticketCore;

    public static final int R = 15;
    public static final int W = 30;

    public LoginCodeView(TicketCore ticketCore, String title, String codeURL) {
        this.ticketCore = ticketCore;
        Constant.codePoints.clear();
        byte[] img = Http.getForFile(Route.HOST + codeURL + new Random().nextDouble());
        initUI(title, img);
    }

    private void initUI(String title, byte[] img) {
        this.setSize(300, 252);
        JPanel jpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(img);
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
            }
        };
        jpanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                Graphics g = jpanel.getGraphics();
                int x = e.getX() - R;
                int y = e.getY() - R;
                for (int i = 0; i < Constant.codePoints.size(); i++) {
                    boolean x_c = ((x >= Constant.codePoints.get(i).getX() - R) && (x <= Constant.codePoints.get(i).getX() + R));
                    boolean y_c = ((y >= Constant.codePoints.get(i).getY() - R) && (y <= Constant.codePoints.get(i).getY() + R));
                    if (x_c && y_c){
                        g.clearRect(Constant.codePoints.get(i).getX(), Constant.codePoints.get(i).getY(), W, W);
                        Constant.codePoints.remove(i);
                        return;
                    }
                }
                Constant.codePoints.add(new Point(x, y));
                g.setColor(Color.RED);
                g.fillOval(x, y, W, W);
                g.drawOval(x, y, W, W);
            }
        });
        JButton jButton = new JButton();
        jButton.setBounds(0,185,300,35);
        jButton.setText("登录");
        jButton.addActionListener(e -> {
            this.dispose();
            try {
                ticketCore.loginCall();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        this.add(jButton);

        this.add(jpanel);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
