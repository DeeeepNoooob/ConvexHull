package com.company;

import java.util.*;

import java.util.Random;

public class ConvexHull {
    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<Point> up = new ArrayList<Point>();
    ArrayList<Point> down = new ArrayList<Point>();
    ArrayList<Point> resultu = new ArrayList<Point>();
    ArrayList<Point> resultd = new ArrayList<Point>();
    ArrayList<Point> result = new ArrayList<Point>();

    public void points(int t) {
        Random in = new Random();

        for (int i = 0; i < t; i++) {
            int a = -in.nextInt(100) + 50;
            int b = -in.nextInt(100) + 50;
            points.add(new Point(a, b, i));
        }
        Collections.sort(points, new SortByX());
        Point p1 = points.get(0);
        Point p2 = points.get(points.size() - 1);
        for (int i = 1; i < points.size() - 1; i++) {
            int d = divide(p1, p2, points.get(i));
            if (d > 0)
                this.up.add(points.get(i));
            else
                this.down.add(points.get(i));
        }
    }


    public int divide(Point p1, Point p2, Point p3) {
        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();
        int x3 = p3.getX(), y3 = p3.getY();
        int n = (y1 - y2) * x3 + (x2 - x1) * y3 + x1 * y2 - x2 * y1;
        return n;
    }
    
    public Point findu(Point p1, Point p2, ArrayList<Point> p) {
        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();
        if (p.size() > 0) {
            Point max = p.get(0);
            int smax = 0;
            int x3, y3;
            int s;
            for (int i = 0; i < p.size(); i++) {
                x3 = p.get(i).getX();
                y3 = p.get(i).getY();
                s = (x1 * y2 - x2 * y1) + (x2 * y3 - x3 * y2) + (x3 * y1 - x1 * y3);
                if (s > smax) {
                    smax = s;
                    max = p.get(i);
                }
            }
            return max;
        } else
            return null;
    }

    public Point findd(Point p1, Point p2, ArrayList<Point> p) {
        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();
        if (p.size() > 0) {
            Point max = p.get(0);
            int smax = 0;
            int d;
            int x3, y3;
            int k = (y2 - y1) / (x2 - x1);
            int b = y1 - k * x1;
            int s;
            for (int i = 0; i < p.size(); i++) {
                x3 = p.get(i).getX();
                y3 = p.get(i).getY();
                s = (x1 * y3 - x3 * y1) + (x3 * y2 - x2 * y3) + (x2 * y1 - x1 * y2);
                if (s > smax) {
                    smax = s;
                    max = p.get(i);
                }
            }
            return max;
        } else
            return null;
    }

    public void chu(Point p1, Point p2, ArrayList<Point> p) {
        Point pmax = findu(p1, p2, p);
        if (pmax != null) {
            resultu.add(pmax);
            Point plmax, prmax;
            ArrayList<Point> left = new ArrayList<Point>();
            ArrayList<Point> right = new ArrayList<Point>();
            for (int i = 0; i < p.size(); i++) {
                if (p.get(i).getX() < pmax.getX() && divide(p1, pmax, p.get(i)) > 0)
                    left.add(p.get(i));
                if (p.get(i).getX() > pmax.getX() && divide(pmax, p2, p.get(i)) > 0)
                    right.add(p.get(i));
            }
            if (left.size() > 0) {
                plmax = findu(p1, pmax, left);
                if (plmax != null)
                    this.resultu.add(plmax);
                chu(p1, pmax, left);
            }
            if (right.size() > 0) {
                prmax = findu(pmax, p2, right);
                if (prmax != null)
                    this.resultu.add(prmax);
                chu(pmax, p2, right);
            }
        }

    }

    public void chd(Point p1, Point p2, ArrayList<Point> p) {
        Point pmax = findd(p1, p2, p);
        if (pmax != null) {
            resultd.add(pmax);
            Point plmax, prmax;
            ArrayList<Point> left = new ArrayList<Point>();
            ArrayList<Point> right = new ArrayList<Point>();
            for (int i = 0; i < p.size(); i++) {
                if (p.get(i).getX() < pmax.getX() && divide(p1, pmax, p.get(i)) < 0)
                    left.add(p.get(i));
                if (p.get(i).getX() > pmax.getX() && divide(pmax, p2, p.get(i)) < 0)
                    right.add(p.get(i));
            }
            if (left.size() > 0) {
                plmax = findd(p1, pmax, left);
                if (plmax != null)
                    this.resultd.add(plmax);
                chd(p1, pmax, left);
            }
            if (right.size() > 0) {
                prmax = findd(pmax, p2, right);
                if (prmax != null)
                    this.resultd.add(prmax);
                chd(pmax, p2, right);
            }
        }
    }

    public void convexhull(int n) {
        points(n);
        chd(points.get(0), points.get(points.size() - 1), down);
        chu(points.get(0), points.get(points.size() - 1), up);
        Collections.sort(resultu, new SortByX());
        Collections.sort(resultd, new SortByX());
        result.add(points.get(0));
        for (int i = 0; i < resultu.size(); i++) {
            result.add(resultu.get(i));
        }
        result.add(points.get(points.size() - 1));

        for (int i = resultd.size() - 1; i >= 0; i--) {
            result.add(resultd.get(i));
        }

    }

    public static void main(String[] args) {
        ConvexHull convexHull = new ConvexHull();

        convexHull.convexhull(10);
        for (int i = 0; i < convexHull.result.size(); i++) {
            int x = convexHull.result.get(i).getX();
            int y = convexHull.result.get(i).getY();
            System.out.println("坐标：" + x + "，" + y);
        }
//        convexHull.chu(convexHull.points.get(0), convexHull.points.get(convexHull.points.size() - 1), convexHull.up);
//        convexHull.chd(convexHull.points.get(0), convexHull.points.get(convexHull.points.size() - 1), convexHull.down);
//        for (int i = 0; i < convexHull.up.size(); i++)
//            System.out.println(convexHull.up.get(i).getX() + " " + convexHull.up.get(i).getY());
        // write your code here
        new Diagram(convexHull);
    }
}

