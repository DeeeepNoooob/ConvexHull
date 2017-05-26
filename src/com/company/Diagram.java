package com.company;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;


/**
 * Created by Yk on 2017/5/20.
 */
public class Diagram extends JFrame {
    ArrayList<Point> all = new ArrayList<Point>();
    ArrayList<Point> result = new ArrayList<Point>();
    private int rw = 200;
    private Graphics g;
    private Color rectColor = new Color(0xf5f5f5);

    public Diagram(ConvexHull ch) {
        all.addAll(ch.points);
        result.addAll(ch.result);
        setLocationRelativeTo(getOwner());
        setTitle("Convex Hull");
        Container p = getContentPane();
        setSize(500, 500);
        setVisible(true);
        p.setBackground(rectColor);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        g = this.getGraphics();
        paintComponent(g);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
//        g.drawRect(50,50,rw,rw);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.black);
        for (int i = 0; i < all.size(); i++) {
            int x = 2 * all.get(i).getX() + 200;
            int y = -2 * all.get(i).getY() + 200;
            g.drawLine(x, y, x, y);
        }
//        int x1 = 2*all.get(0).getX()+200;
//        int y1= -2*all.get(0).getY()+200;
//        int x2 = 2*all.get(all.size()-1).getX()+200;
//        int y2 = -2*all.get(all.size()-1).getY()+200;
//
////        g2.drawLine(2*all.get(0).getX()+200,-2*all.get(0).getX()+200,2*all.get(all.size()-1).getX()+200,-2*all.get(all.size()-1).getY()+200);
//        g.drawLine(x1,y1,x2,y2);
        for (int i = 0; i < result.size() - 1; i++) {
            int x1 = 2 * result.get(i).getX() + 200;
            int y1 = -2 * result.get(i).getY() + 200;
            int x2 = 2 * result.get(i + 1).getX() + 200;
            int y2 = -2 * result.get(i + 1).getY() + 200;
            g.drawLine(x1, y1, x2, y2);
        }
        int x1 = 2 * result.get(0).getX() + 200;
        int y1 = -2 * result.get(0).getY() + 200;
        int x2 = 2 * result.get(result.size() - 1).getX() + 200;
        int y2 = -2 * result.get(result.size() - 1).getY() + 200;
        g.drawLine(x1, y1, x2, y2);
    }

}
