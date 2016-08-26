package com.alkastor.crowd.impl;

import com.alkastor.crowd.CreateModel;
import com.alkastor.crowd.model.Ball;
import com.alkastor.crowd.model.Cell;

import java.awt.*;
import java.util.Random;

public class CreateModelImpl implements CreateModel {

    private Model model;
    private Ball[] balls;
    private Random rm = new Random();

    public CreateModelImpl(Model model) {
        this.model = model;
//        Direct.clear_v_direct(model.cells, model.nx, model.ny);
    }

    public Ball[] GetBalls(Cell[][] cells) {
        model.n++;
        balls = new Ball[model.n + 1];
        balls[0] = new Ball();
        balls[0].t = 0;
        balls[0].id = 0;
        balls[0].round = (int) 1e10;
        balls[0].on_stack = false;
        balls[0].event = 11;
        double x = 0.5;
        double y = 0.5;
        double a, c, fi, fj;
        ///////
        for (int i = 1; i < model.n; i++) {
            balls[i] = new Ball();
            balls[i].id = i;
            balls[i].x = x;
            balls[i].y = y;
            balls[i].i = (int) x;
            balls[i].j = (int) y;
            balls[i].massa = 1;
            balls[i].type = 2;
            balls[i].r1 = 0.45;
            balls[i].next = 0;
            balls[i].t_loc = 0;
            balls[i].t = i;
            balls[i].event = 0;
            balls[i].on_stack = true;
            a = rm.nextDouble();
            c = rm.nextDouble();
            fi = a * 2 * Math.PI;
            fj = c * 2 * Math.PI;
            balls[i].vx = Math.sin(fi) * Math.cos(fj) * (1 / Math.sqrt(balls[i].massa));
            balls[i].vy = Math.cos(fi) * Math.cos(fj) * (1 / Math.sqrt(balls[i].massa));
            balls[i].numNeighbor = 0;
            balls[i].color = Color.orange;
        }
        return balls;
    }

    private boolean check(int i, int j, Cell[][] cells) {
        return cells[i][j].numBalls > 0;
    }

    public Ball[] GetMaze(Cell[][] cells) {
        balls = GetBalls(cells);
        double x = 0.5;
        double y = 30;
        int kk = 0;
        boolean b = false;
        for (int i = model.n - 20; i < model.n; i++) {
            balls[i] = new Ball();
            balls[i].id = i;
            balls[i].x = x;
            balls[i].y = y;
            balls[i].i = (int) x;
            balls[i].j = (int) y;
            balls[i].massa = 10e20;
            balls[i].type = 2;
            balls[i].r1 = 0.7;
            balls[i].next = 0;
            balls[i].t_loc = 0;
            balls[i].t = 10e20;
            balls[i].event = 0;
            balls[i].vx = 0;
            balls[i].vy = 0;
            balls[i].numNeighbor = 0;
            balls[i].color = Color.green;
            balls[i].fixed = true;
            cells[balls[i].i][balls[i].j].addBall(i);
            x++;
            kk++;
            if ((int) x >= model.nx) {
                x = 0.5;
            }
            if (kk == 80) {
                y += 7;
                kk = 0;
                b = !b;
                if (b)
                    x = model.nx - 80;
            }
            if ((int) y >= model.ny) {
                y = 1;
            }
        }
        return balls;
    }
}
