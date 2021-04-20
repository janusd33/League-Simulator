import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Double.parseDouble;

public class Interface extends JFrame implements ActionListener {

    /**
     * Declatation of all GUI elements used in frame
     */
    Boolean skip;
    Tournament tournament;
    ArrayList<String> chosenClubs = new ArrayList<>();
    JLabel title;
    JLabel authors;
    JLabel info;
    JLabel note;
    JLabel headerHost;
    JLabel headerGuest;
    JLabel score;
    JLabel logoHost;
    JLabel logoGuest;
    JLabel time;
    JButton openSimulator;
    JButton exit;
    JButton chooseClubs;
    JButton nextMatch;
    JButton table;
    JButton statistics;
    JButton skipMatch;
    JButton hostbet;
    JButton drawbet;
    JButton guestbet;
    JSlider slider;
    JLabel bidinfo;
    JLabel moneyinfo;
    JTable leagueTable;
    String[] columns;
    String[][] rows;
    String[] stats_headers;
    String[][] stats_data;
    JScrollPane scrollPane;
    JTable stats;
    JScrollPane scrollPane2;
    JTable classifications;
    JScrollPane scrollPane3;
    String[][] classifications_data;
    String[] classifications_column;

    ArrayList<JLabel> minutes = new ArrayList<>() {
        {
            for (int i = 0; i < 5; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<JLabel> timetables = new ArrayList<>() {
        {
            for (int i = 0; i < 24; i++) {
                if (i < 12) {
                    add(new JLabel("", SwingConstants.RIGHT));
                } else {
                    add(new JLabel(""));
                }
            }
        }
    };


    ArrayList<JLabel> classificationstat = new ArrayList<>() {
        {
            for (int i = 0; i < 13; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<JLabel> tableLabels = new ArrayList<>() {
        {
            for (int i = 0; i < 13; i++) {
                add(new JLabel());
            }
        }
    };


    ArrayList<JRadioButton> RadioBox = new ArrayList<>() {
        {
            for (int i = 0; i < 8; i++) {
                add(new JRadioButton());
            }
        }
    };


    ArrayList<JCheckBox> checkBoxes = new ArrayList<>() {
        {
            for (int i = 0; i < 16; i++) {
                add(new JCheckBox());
            }
        }
    };

    ArrayList<JLabel> logos = new ArrayList<>() {
        {
            for (int i = 0; i < 16; i++) {
                add(new JLabel());
            }
        }
    };

    ArrayList<String> classification = new ArrayList<>() {
        {
            add("Top Goalscorers");
            add("Top Asistants");
            add("Canadian classification");
            add("The most Yellow Cards");
            add("The most Red Cards");
            add("The most fouling players");
            add("The most frequent fouled players");
            add("The most frequent shooting players");
            add("topGoalscorers");
            add("topAssistants");
            add("topCanadians");
            add("topYellows");
            add("topReds");
            add("topFouls");
            add("topFouled");
            add("topShoots");

        }
    };

    ArrayList<String> clubs = new ArrayList<>() {
        {
            add("AC Milan");
            add("Ajax");
            add("Bayern Munich");
            add("Benfica");
            add("Borussia Dortmund");
            add("FC Barcelona");
            add("Juventus");
            add("Lech Poznan");
            add("Legia Warsaw");
            add("Liverpool");
            add("Lokomotiv Moscow");
            add("Manchester City");
            add("Manchester United");
            add("PSG");
            add("Rangers");
            add("Real Madrid");
        }
    };

    /**
     * Constructor itself needs no argument. It specifies positions off all labels, buttons, etc.
     */
    public Interface() {
        /**
         * General settings
         */
        setSize(1000, 800);
        setTitle("League Simulator");
        setLayout(null);
        setResizable(false);

        /**
         * Labels
         */
        title = new JLabel("LEAGUE SIMULATOR");
        title.setFont(new Font("Verdana", Font.BOLD, 30));
        title.setBounds(330, 100, 500, 50);
        add(title);

        authors = new JLabel("Sebastian Deręgowski, Dawid Janus");
        authors.setFont(new Font("Verdana", Font.BOLD, 12));
        authors.setBounds(380, 700, 500, 50);
        add(authors);

        info = new JLabel("Choose 4 clubs to play in a league:");
        info.setFont(new Font("Verdana", Font.BOLD, 13));
        info.setBounds(150, 40, 300, 50);
        add(info);
        info.setVisible(false);

        note = new JLabel("You have to choose 4 clubs!");
        note.setFont(new Font("Verdana", Font.BOLD, 13));
        note.setBounds(150, 650, 200, 50);
        add(note);
        note.setVisible(false);

        headerHost = new JLabel("");
        headerHost.setFont(new Font("Verdana", Font.BOLD, 20));
        add(headerHost);
        headerHost.setVisible(false);

        headerGuest = new JLabel("");
        headerGuest.setFont(new Font("Verdana", Font.BOLD, 20));
        add(headerGuest);
        headerGuest.setVisible(false);

        logoHost = new JLabel();
        logoHost.setBounds(115, 25, 100, 100);
        add(logoHost);
        logoHost.setVisible(false);

        logoGuest = new JLabel();
        logoGuest.setBounds(515, 25, 100, 100);
        add(logoGuest);
        logoGuest.setVisible(false);

        score = new JLabel();
        score.setFont(new Font("Verdana", Font.BOLD, 30));
        add(score);
        score.setVisible(false);

        time = new JLabel();
        time.setFont(new Font("Verdana", Font.BOLD, 15));
        add(time);
        time.setVisible(false);

        int i = 0;
        for (JLabel minute : minutes) {
            minute.setText("");
            minute.setFont(new Font("Verdana", Font.BOLD, 11));
            minute.setBounds(75, 450 + i * 35, 700, 25);
            add(minute);
            minute.setVisible(false);
            i++;
        }

        i = 0;
        for (JLabel label : tableLabels) {
            label.setText("");
            label.setFont(new Font("Verdana", Font.BOLD, 11));
            label.setBounds(100, 100 + i * 25, 700, 25);
            add(label);
            label.setVisible(false);
            i++;
        }

        i = 0;
        for (JLabel statistic : classificationstat) {
            statistic.setText("");
            statistic.setFont(new Font("Verdana", Font.BOLD, 11));
            statistic.setBounds(100, 100 + i * 25, 700, 25);
            add(statistic);
            statistic.setVisible(false);
            i++;
        }

        /**
         * Buttons
         */
        openSimulator = new JButton("Open simulator");
        openSimulator.setBounds(400, 300, 200, 100);
        openSimulator.setFont(new Font("Verdana", Font.BOLD, 15));
        add(openSimulator);
        openSimulator.addActionListener(this);

        exit = new JButton("Exit");
        exit.setBounds(400, 500, 200, 100);
        exit.setFont(new Font("Verdana", Font.BOLD, 15));
        add(exit);
        exit.addActionListener(this);

        chooseClubs = new JButton("Choose Clubs");
        chooseClubs.setBounds(400, 625, 200, 100);
        chooseClubs.setFont(new Font("Verdana", Font.BOLD, 15));
        add(chooseClubs);
        chooseClubs.addActionListener(this);
        chooseClubs.setVisible(false);

        statistics = new JButton("Statistics");
        statistics.setFont(new Font("Verdana", Font.BOLD, 15));
        statistics.setBounds(700, 400, 200, 100);
        add(statistics);
        statistics.addActionListener(this);
        statistics.setVisible(false);

        hostbet = new JButton();
        hostbet.setFont(new Font("Verdana", Font.BOLD, 12));
        hostbet.setBounds(80, 300, 200, 50);
        hostbet.addActionListener(this);
        add(hostbet);
        hostbet.setVisible(false);

        guestbet = new JButton();
        guestbet.setFont(new Font("Verdana", Font.BOLD, 12));
        guestbet.setBounds(480, 300, 200, 50);
        guestbet.addActionListener(this);
        add(guestbet);
        guestbet.setVisible(false);

        drawbet = new JButton();
        drawbet.setFont(new Font("Verdana", Font.BOLD, 12));
        drawbet.setBounds(280, 300, 200, 50);
        drawbet.addActionListener(this);
        add(drawbet);
        drawbet.setVisible(false);

        slider = new JSlider();
        slider.setMaximum(1000);
        slider.setMinimum(0);
        slider.setBounds(480, 200, 200, 50);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {

                bidinfo.setText("Your bid for this match: " + slider.getValue() + "$");
            }
        });
        add(slider);
        slider.setVisible(false);

        bidinfo = new JLabel();
        bidinfo.setFont(new Font("Verdana", Font.BOLD, 12));
        bidinfo.setBounds(80, 200, 400, 50);
        add(bidinfo);
        bidinfo.setVisible(false);

        moneyinfo = new JLabel();
        moneyinfo.setFont(new Font("Verdana", Font.BOLD, 12));
        moneyinfo.setBounds(700,50,200,20);
        add(moneyinfo);
        moneyinfo.setVisible(false);


        i = 0;
        for (JRadioButton button : RadioBox) {
            button.setText(classification.get(i));
            button.setBounds(100, 500 + 25 * i, 300, 20);
            add(button);
            button.addActionListener(this);
            button.setVisible(false);
            i++;
        }

        skipMatch = new JButton("Skip match");
        skipMatch.setFont(new Font("Verdana", Font.BOLD, 15));
        skipMatch.setBounds(240, 650, 200, 100);
        add(skipMatch);
        skipMatch.addActionListener(this);
        skipMatch.setVisible(false);

        nextMatch = new JButton("Next Match");
        nextMatch.setFont(new Font("Verdana", Font.BOLD, 15));
        nextMatch.setBounds(700, 100, 200, 100);
        add(nextMatch);
        nextMatch.addActionListener(this);
        nextMatch.setVisible(false);

        table = new JButton("Table & Fixtures");
        table.setFont(new Font("Verdana", Font.BOLD, 15));
        table.setBounds(700, 250, 200, 100);
        add(table);
        table.addActionListener(this);
        table.setVisible(false);

        /**
         * Logos and club checkboxes
         */
        i = 0;
        for (JLabel logo : logos) {
            logo.setIcon(new ImageIcon((new ImageIcon("Logos/" + clubs.get(i) + ".png")).getImage().
                    getScaledInstance(75, 75, Image.SCALE_AREA_AVERAGING)));
            logo.setBounds(((i) % 4) * 200 + 150, (i / 4 + 1) * 125 - 40, 100, 100);
            add(logo);
            logo.setVisible(false);
            i++;
        }

        i = 0;
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setText(clubs.get(i));
            checkBox.setBounds(((i) % 4) * 200 + 150, (i / 4 + 1) * 125 + 60, 160, 20);
            add(checkBox);
            checkBox.addActionListener(this);
            checkBox.setVisible(false);
            i++;
        }

        i = 0;
        for (JLabel labels : timetables) {
            labels.setText("");
            if (i < 12) {
                labels.setBounds((((i) / 6) - 1) * 325 + 300, (i % 6) * 50 + 400, 200, 30);
            } else {
                labels.setBounds((((i - 12) / 6) - 1) * 325 + 500, ((i - 12) % 6) * 50 + 400, 200, 30);
            }


            labels.setFont(new Font("Verdana", Font.BOLD, 11));
            add(labels);
            labels.setVisible(false);
            i++;
        }

        /**
         * Tables used in simulator.
         */
        columns = new String[]{"POS.", "TEAM", "GP", "W", "D", "L", "GF", "GA", "GD", "Pts"};
        rows = new String[][]{{"", "", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", "", ""}};

        leagueTable = new JTable(rows, columns) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        leagueTable.setFont(new Font("Verdana", Font.BOLD, 11));
        leagueTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        leagueTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        leagueTable.setFillsViewportHeight(true);
        leagueTable.setRowHeight(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int q =2;i<10;i++){
            leagueTable.getColumnModel().getColumn(q).setCellRenderer(centerRenderer);
        }
        leagueTable.setVisible(false);

        scrollPane = new JScrollPane(leagueTable);
        scrollPane.setLocation(50, 100);
        scrollPane.setSize(600, 125);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setVisible(false);

        stats_headers = new String[]{"", "", ""};
        stats_data = new String[][]{{"", "POSSESION", ""}, {"", "SHOOTS", ""}, {"", "ON TARGET", ""},
                {"", "FOULS", ""}, {"", "YELLOW CARDS", ""}, {"", "RED CARDS", ""}};

        stats = new JTable(stats_data, stats_headers) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        stats.setTableHeader(null);
        stats.setFont(new Font("Verdana", Font.BOLD, 11));
        stats.getColumnModel().getColumn(0).setPreferredWidth(50);
        stats.getColumnModel().getColumn(1).setPreferredWidth(180);
        stats.getColumnModel().getColumn(2).setPreferredWidth(50);
        stats.setFillsViewportHeight(true);
        stats.setRowHeight(20);
        DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
        centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
        stats.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        stats.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        stats.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        scrollPane2 = new JScrollPane(stats);
        scrollPane2.setLocation(200, 250);
        scrollPane2.setSize(300, 122);
        getContentPane().add(scrollPane2, BorderLayout.CENTER);
        scrollPane2.setVisible(false);


        classifications_column = new String[]{"Rank","Player","Club", "Count"};
        classifications_data = new String[][]{{"-","-","-", "-"}, {"-","-","-", "-"}, {"-","-","-", "-"},
                {"-","-","-", "-"}, {"-","-","-", "-"}, {"-","-","-", "-"}, {"-","-","-", "-"}, {"-","-","-", "-"},
                {"-","-","-", "-"}, {"-","-","-", "-"}};


        classifications = new JTable(classifications_data, classifications_column) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        classifications.setFont(new Font("Verdana", Font.BOLD, 11));
        classifications.getColumnModel().getColumn(0).setPreferredWidth(35);
        classifications.getColumnModel().getColumn(1).setPreferredWidth(180);
        classifications.getColumnModel().getColumn(2).setPreferredWidth(150);
        classifications.getColumnModel().getColumn(3).setPreferredWidth(50);
        classifications.setFillsViewportHeight(true);
        classifications.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer3 = new DefaultTableCellRenderer();
        centerRenderer3.setHorizontalAlignment(JLabel.CENTER);
        classifications.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        classifications.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        classifications.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        classifications.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        scrollPane3 = new JScrollPane(classifications);
        scrollPane3.setLocation(100, 140);
        scrollPane3.setSize(415, 324);
        getContentPane().add(scrollPane3, BorderLayout.CENTER);
        scrollPane3.setVisible(false);



    }

    /**
     * Main method of GUI. Depending on button clicked, the dedicated action is performed.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == openSimulator) {

            /**
             * Updating visibility
             */
            title.setVisible(false);
            authors.setVisible(false);
            openSimulator.setVisible(false);
            exit.setVisible(false);
            scrollPane.setVisible(false);
            scrollPane2.setVisible(false);
            exit.setBounds(700, 550, 200, 100);
            chooseClubs.setVisible(true);
            info.setVisible(true);
            for (JLabel logo : logos) {
                logo.setVisible(true);
            }
            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setVisible(true);
            }

        } else if (source == chooseClubs) {
            /**
             * Updating visibility
             */
            if (chosenClubs.size() != 4) {
                note.setVisible(true);
                return;
            }
            note.setVisible(false);
            info.setVisible(false);
            chooseClubs.setVisible(false);
            for (JCheckBox checkBox : checkBoxes) {
                checkBox.setVisible(false);
            }
            for (JLabel logo : logos) {
                logo.setVisible(false);
            }
            nextMatch.setVisible(true);
            table.setVisible(true);
            statistics.setVisible(true);
            exit.setVisible(true);
            moneyinfo.setVisible(true);


            /**
             * Action performed
             */


            try {
                tournament = new Tournament(new Club(chosenClubs.get(0), this),
                        new Club(chosenClubs.get(1), this), new Club(chosenClubs.get(2), this), new Club(chosenClubs.get(3), this), this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            moneyinfo.setText("Money: " + Math.round(tournament.money * 100d) / 100d + "$");
            tournament.addSchedule();
            int i = 0;
            for (JLabel labels : timetables) {
                if (i < 12) {
                    labels.setText(tournament.schedule.get(i).team1 + "    " + "-" + " : ");
                } else {
                    labels.setText("-" + "    " + tournament.schedule.get(i - 12).team2);
                }
                i++;
            }


            /**
             * Updating number of chosen clubs in turn to prevent user from choosing incorrect number.
             */
        } else if (checkBoxes.contains(source)) {
            JCheckBox a = (JCheckBox) source;
            if (chosenClubs.size() == 4) {
                a.setSelected(false);
            }
            if (a.isSelected()) {
                chosenClubs.add(a.getText());
            } else {
                chosenClubs.remove(a.getText());
            }

        } else if (source == nextMatch) {
            /**
             * Updating visibility
             */
            skip = false;
            info.setVisible(false);
            for (JRadioButton button : RadioBox) {
                button.setVisible(false);
            }
            for (JLabel minute : minutes) {
                minute.setText("");
                minute.setVisible(true);
            }
            for (JLabel stat : classificationstat) {
                stat.setVisible(false);
            }
            for (JLabel x : tableLabels) {
                x.setVisible(false);
            }
            for (JLabel label : timetables) {
                label.setVisible(false);
            }
            scrollPane3.setVisible(false);
            headerHost.setVisible(true);
            headerGuest.setVisible(true);
            logoHost.setVisible(true);
            logoGuest.setVisible(true);
            nextMatch.setEnabled(false);
            score.setVisible(false);
            time.setVisible(false);
            statistics.setEnabled(false);
            table.setEnabled(false);
            scrollPane.setVisible(false);
            scrollPane2.setVisible(false);
            stats.setVisible(false);

            headerHost.setText(tournament.schedule.get(tournament.matchNumber).team1.toString());
            headerHost.setBounds((int) (300 - tournament.schedule.get(tournament.matchNumber).team1.toString().length() * 11.8) / 2, 50, 300, 200);
            headerGuest.setText(tournament.schedule.get(tournament.matchNumber).team2.toString());
            headerGuest.setBounds((int) (400 + (300 - tournament.schedule.get(tournament.matchNumber).team2.toString().length() * 11.8) / 2), 50, 300, 200);
            logoHost.setIcon(new ImageIcon((new ImageIcon("Logos/" + tournament.schedule.get(tournament.matchNumber).team1 + ".png")).getImage().
                    getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING)));
            logoGuest.setIcon(new ImageIcon((new ImageIcon("Logos/" + tournament.schedule.get(tournament.matchNumber).team2 + ".png")).getImage().
                    getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING)));

            hostbet.setVisible(true);
            tournament.schedule.get(tournament.matchNumber).bet();
            hostbet.setText(tournament.schedule.get(tournament.matchNumber).team1.toString() + ": " + tournament.schedule.get(tournament.matchNumber).host);
            guestbet.setVisible(true);
            guestbet.setText(tournament.schedule.get(tournament.matchNumber).team2.toString() + ": " + tournament.schedule.get(tournament.matchNumber).guest);
            drawbet.setVisible(true);
            drawbet.setText("X: " + tournament.schedule.get(tournament.matchNumber).draw);
            slider.setVisible(true);
            slider.setMaximum((int) tournament.money);
            bidinfo.setVisible(true);
            bidinfo.setText("Your bid for this match: " + slider.getValue() + "$");


        } else if (source == hostbet || source == drawbet || source == guestbet) {
            hostbet.setVisible(false);
            drawbet.setVisible(false);
            guestbet.setVisible(false);
            bidinfo.setVisible(false);
            slider.setVisible(false);
            score.setVisible(true);
            time.setVisible(true);
            scrollPane2.setVisible(true);
            stats.setVisible(true);
            skipMatch.setVisible(true);

            if(source == hostbet){
                tournament.schedule.get(tournament.matchNumber).bid(slider.getValue(),1);
            }else if(source == guestbet){
                tournament.schedule.get(tournament.matchNumber).bid(slider.getValue(),2);
            }else{
                tournament.schedule.get(tournament.matchNumber).bid(slider.getValue(),0);
            }

            try {
                tournament.run();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

        else if (source == table) {
            /**
             * Updating visibility
             */
            for (JLabel label : timetables) {
                label.setVisible(true);
            }

            for (JLabel minute : minutes) {
                minute.setVisible(false);
            }

            for (JRadioButton button : RadioBox) {
                button.setVisible(false);
            }
            for (JLabel x : classificationstat) {
                x.setVisible(false);
            }
            for (JLabel x : tableLabels) {
                x.setVisible(false);//true
            }

            scrollPane3.setVisible(false);
            table.setEnabled(false);
            statistics.setEnabled(true);
            headerHost.setVisible(false);
            headerGuest.setVisible(false);
            logoHost.setVisible(false);
            logoGuest.setVisible(false);
            score.setVisible(false);
            time.setVisible(false);
            skipMatch.setVisible(false);
            info.setVisible(false);
            scrollPane2.setVisible(false);
            leagueTable.setVisible(true);
            scrollPane.setVisible(true);

            /**
             * Action performed
             */
            tournament.table();

        } else if (source == statistics) {
            /**
             * Updating visibility
             */
            for (JLabel minute : minutes) {
                minute.setVisible(false);
            }

            for (JLabel x : tableLabels) {
                x.setVisible(false);
            }
            for (JLabel label : timetables) {
                label.setVisible(false);
            }
            table.setEnabled(true);
            info.setVisible(false);
            statistics.setEnabled(false);
            headerHost.setVisible(false);
            headerGuest.setVisible(false);
            logoHost.setVisible(false);
            logoGuest.setVisible(false);
            score.setVisible(false);
            time.setVisible(false);
            skipMatch.setVisible(false);
            scrollPane.setVisible(false);
            scrollPane2.setVisible(false);

            /**
             * Action performed
             */
            for (JRadioButton button : RadioBox) {
                button.setVisible(true);
                button.setSelected(false);
            }

            /**
             * Updating visibility and selection
             */
        } else if (RadioBox.contains(source)) {
            JRadioButton button = (JRadioButton) source;
            scrollPane3.setVisible(false);

            for (JRadioButton button2 : RadioBox) {
                if (button2 != button) {
                    button2.setSelected(false);

                }

            }
            for (int i = 0; i < 10; i++) {
                classifications_data[i] = new String[]{"-", "-", "-", "-"};
            }
            if (button.isSelected()) {
                scrollPane3.setVisible(true);

                ArrayList<Method> classificationList = new ArrayList<>(Arrays.asList(tournament.getClass().getDeclaredMethods()));
                for (Method method : classificationList) {
                    if (method.getName().equals(classification.get(RadioBox.indexOf(button) + 8))) {
                        try {
                            method.invoke(tournament);
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                return;

            }
            scrollPane3.setVisible(false);


        } else if (source == skipMatch) {
            skip = true;
            skipMatch.setVisible(false);
        } else if (source == exit) {
            dispose();
        }


    }
}
//