package com.alkastor.crowd.gui;

import com.alkastor.crowd.model.Direct;
import com.alkastor.crowd.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JMenuBar mainMenu = new JMenuBar();
    private JMenu menu = new JMenu("menu");
    private Action showSettings = new SettingsAction(MainFrame.this);
    private JMenuItem settings = new JMenuItem(showSettings);

    public MainFrame() {
        setSize(300, 300);
        setLocationByPlatform(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainMenu.add(menu);
        menu.add(settings);
        model = new Model();
        model.nx = 100;
        model.ny = 100;
        model.N = 1500;
        model.initialize();
        comp = new Compp(model);
        timer = new Timer(50, new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                for (int i = 0; i < model.N; i++) {
                    model.simulate();
                }
                Direct.sum_v_direct(model.cells, model.balls, model.nx, model.ny);
                comp.update(comp.getGraphics());
            }
        });
        add(comp);
        setJMenuBar(mainMenu);
    }

    public Model model;
    public Timer timer;
    public Compp comp;
}
