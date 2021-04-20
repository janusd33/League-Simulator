import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Class match represents every game that is played in a simulator.
 */
public class Match implements Runnable {
    Interface frame;
    Boolean islast = false;
    int team1Goals;
    int team2Goals;

    /**
     * Clubs involved in a game.
     */
    Club team1;
    Club team2;

    /**
     * probability of events and types of players involved in them.
     */
    final double changePossesion = 0.5;
    final double action = 0.5;
    final double foul = 0.35;
    final double defenderfoul = 0.6;
    final double forwardfoul = 0.1;
    final double defendershoot = 0.1;
    final double forwardshoot = 0.6;
    final double defenderassist = 0.1;
    final double midfielderassist = 0.6;
    Position position;
    Position assistantPosition;
    Random r = new Random();

    DecimalFormat df = new DecimalFormat("#.##");
    double host = 1.67;
    double draw = 1.33;
    double guest = 1.67;
    double bid;
    int result;
    double overall1;
    double overall2;

    /**
     * Parameters connected with ball possesion during the game.
     */
    boolean possesionTeam1 = true;
    double possesion1counter = 0;
    double possesion2counter = 0;

    /**
     * Constructor involves:
     *
     * @param team1 (host team)
     * @param team2 (away team)
     */
    public Match(Club team1, Club team2, Interface frame) {
        this.team1 = team1;
        this.team2 = team2;
        this.frame = frame;
    }


    /**
     * play() is the main method for this class.
     *
     * @throws InterruptedException
     */
    @Override
    public void run() {
        /**
         * It's based on a loop that runs 90 times - one for every minute.
         */

        frame.score.setBounds(300, 50, 100, 200);
        frame.time.setBounds(335, 50, 100, 50);




        try {
            for (int i = 1; i < 91; i++) {
                frame.score.setText(team1.currentGoals + "  :  " + team2.currentGoals);
                if (i > 1 & !frame.skip) {
                    statistics(i - 1);
                    Thread.sleep(500);
                }

                /**
                 * Ball possesion update
                 */
                if (possesionTeam1) {
                    possesion1counter += 1;
                } else {
                    possesion2counter += 1;
                }

                /**
                 * When the possesion is changed in this minute, nothing else may happen.
                 */
                if (r.nextDouble() < changePossesion) {
                    possesionTeam1 = !possesionTeam1;
                    printMinutes(i);
                    frame.time.setText(i + "'");
                    continue;
                }

                /**
                 * If statement determines whether any action is going to happen or not.
                 */
                if (r.nextDouble() < action) {


                    /**
                     * Another if statement determines whether the action is a shoot or a foul.
                     */
                    if (r.nextDouble() < foul) {

                        /**
                         * A double positionrate determines position of a player.
                         */
                        double positionrate = r.nextDouble();
                        if (positionrate >= (1 - defenderfoul)) {
                            position = Position.DEFENDER;
                        } else if (positionrate < forwardfoul) {
                            position = Position.FORWARD;
                        } else {
                            position = Position.MIDFIELDER;
                        }

                        /**
                         * (see: @getPosition method in Club class and @foul method in Player class)
                         */
                        if (possesionTeam1) {
                            team2.getPosition(position).get(r.nextInt((team2.getPosition(position)).size()))
                                    .foul(team1.players.get(r.nextInt(11)), i);
                        } else {
                            team1.getPosition(position).get(r.nextInt((team1.getPosition(position)).size()))
                                    .foul(team2.players.get(r.nextInt(11)), i);
                        }
                    } else {

                        /**
                         * Doubles positionrate and assistantpositionrate determine position of a player.
                         */
                        double positionrate = r.nextDouble();
                        double assistantpositionrate = r.nextDouble();
                        if (positionrate > (1 - forwardshoot)) {
                            position = Position.FORWARD;
                        } else if (positionrate < defendershoot) {
                            position = Position.DEFENDER;
                        } else {
                            position = Position.MIDFIELDER;
                        }
                        if (assistantpositionrate >= (1 - midfielderassist)) {
                            assistantPosition = Position.MIDFIELDER;
                        } else if (assistantpositionrate < defenderassist) {
                            assistantPosition = Position.DEFENDER;
                        } else {
                            assistantPosition = Position.FORWARD;
                        }

                        /**
                         * (see: @getPosition method in Club class and @shoot method in Player class)
                         */
                        if (possesionTeam1) {
                            if (assistantPosition != position) {
                                team1.getPosition(position).get(r.nextInt((team1.getPosition(position)).size()))
                                        .shoot(team1.getPosition(assistantPosition)
                                                        .get(r.nextInt((team1.getPosition(assistantPosition)).size())),
                                                team2.getPosition(Position.GOALKEEPER).get(0), i);
                            } else {

                                /**
                                 * The same player cannot both have an assist and score.
                                 */
                                int notthesame1 = r.nextInt((team1.getPosition(position)).size());
                                int notthesame2 = r.nextInt((team1.getPosition(assistantPosition)).size());
                                while (notthesame1 == notthesame2) {
                                    notthesame2 = r.nextInt((team1.getPosition(assistantPosition)).size());
                                }
                                team1.getPosition(position).get(notthesame1)
                                        .shoot(team1.getPosition(assistantPosition)
                                                        .get(notthesame2),
                                                team2.getPosition(Position.GOALKEEPER).get(0), i);
                            }
                            possesionTeam1 = false;
                        } else {
                            if (assistantPosition != position) {
                                team2.getPosition(position).get(r.nextInt((team2.getPosition(position)).size()))
                                        .shoot(team2.getPosition(assistantPosition)
                                                        .get(r.nextInt((team2.getPosition(assistantPosition)).size())),
                                                team1.getPosition(Position.GOALKEEPER).get(0), i);
                            } else {
                                int notthesame1 = r.nextInt((team2.getPosition(position)).size());
                                int notthesame2 = r.nextInt((team2.getPosition(assistantPosition)).size());
                                while (notthesame1 == notthesame2) {
                                    notthesame2 = r.nextInt((team2.getPosition(assistantPosition)).size());
                                }
                                team2.getPosition(position).get(notthesame1)
                                        .shoot(team2.getPosition(assistantPosition)
                                                        .get(notthesame2),
                                                team1.getPosition(Position.GOALKEEPER).get(0), i);
                            }
                            possesionTeam1 = true;
                        }
                    }
                    frame.time.setText(i + "'");
                    continue;
                }

                /**
                 * Otherwise nothing happens.
                 */
                frame.time.setText(i + "'");
                printMinutes(i);
            }
            statistics(90);
            frame.skipMatch.setVisible(false);
            frame.score.setText(team1.currentGoals + "  :  " + team2.currentGoals);
            endgame();
            for(int k=0; k<12; k++){
            if(this==frame.tournament.schedule.get(k)){
                frame.timetables.get(k).setText((team1 + "    " + team1Goals+ " : "));
                frame.timetables.get(k+12).setText((team2Goals + "    " + team2));


            }
            }

            /**
             * If this is the last game, the proper information is displayed and "Next match" button is disabled.
             */
            if (islast) {
                Thread.sleep(4000);
                for (JLabel x : frame.minutes) {
                    x.setVisible(false);
                }
                for (JLabel x : frame.timetables) {
                    x.setVisible(false);
                }

                for (JLabel x : frame.classificationstat) {
                    x.setVisible(false);
                }
                frame.scrollPane.setVisible(false);
                frame.nextMatch.setEnabled(false);
                frame.skipMatch.setVisible(false);
                frame.headerHost.setVisible(false);
                frame.headerGuest.setVisible(false);
                frame.logoHost.setVisible(false);
                frame.logoGuest.setVisible(false);
                frame.score.setVisible(false);
                frame.time.setVisible(false);
                frame.info.setVisible(true);
                frame.info.setText("Tournament has ended! You have " + Math.round(frame.tournament.money * 100d) / 100d +"$.");
                frame.info.setBounds(80, 300, 600, 100);
                frame.info.setFont(new Font("Verdana", Font.BOLD, 20));
                frame.scrollPane2.setVisible(false);
            } else {
                frame.nextMatch.setEnabled(true);
            }
            frame.statistics.setEnabled(true);
            frame.table.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void bet(){
        for(int i=0;i<11;i++){
            overall1 = host + team1.players.get(i).overall;
            overall2 = guest + team2.players.get(i).overall;
        }
        overall1 = overall1/11;
        overall2 = overall2/11;
        host = host - (overall1-overall2)*0.3;
        guest = guest - (overall2-overall1)*0.3;
        draw = draw + abs(overall1-overall2)*0.2;

        host = Math.round(host * 100d) / 100d;
        guest = Math.round(guest * 100d) / 100d;
        draw = Math.round(draw * 100d) / 100d;

    }

    void bid(double input, int result){
        this.result = result;
        frame.tournament.money = frame.tournament.money - input;
        if(result==1) {
                bid = input * host;
            }else if(result==2){
                bid = input * guest;
            }else{
                bid = input * draw;
            };

    }

    /**
     * Method endgame() is used to update data about teams' results when it's finished.
     */
    void endgame() {
        team1.count();
        team2.count();

        if(result==1 && team1.currentGoals>team2.currentGoals){
            frame.tournament.money = frame.tournament.money + bid;
        }else if(result ==0 && team1.currentGoals==team2.currentGoals){
            frame.tournament.money = frame.tournament.money + bid;
        }else if(result == 2 && team1.currentGoals<team2.currentGoals){
            frame.tournament.money = frame.tournament.money + bid;
        }
        frame.moneyinfo.setText("Money: " + Math.round(frame.tournament.money * 100d) / 100d + "$");


        team1.matches += 1;
        team2.matches += 1;

        team1.goalsfor += team1.currentGoals;
        team1.goalsagainst += team2.currentGoals;
        team2.goalsfor += team2.currentGoals;
        team2.goalsagainst += team1.currentGoals;
        team1Goals=team1.currentGoals;
        team2Goals=team2.currentGoals;

        if (team1.currentGoals > team2.currentGoals) {
            team1.wins += 1;
            team1.points += 3;
            team2.loses += 1;
        } else if (team2.currentGoals > team1.currentGoals) {
            team2.wins += 1;
            team2.points += 3;
            team1.loses += 1;
        } else {
            team1.draws += 1;
            team1.points += 1;
            team2.draws += 1;
            team2.points += 1;
        }
        team1.refresh();
        team2.refresh();

    }

    /**
     * Method statistics updates a stats table during a game.
     */
    void statistics(int i) {
        double possesion1 = Math.round((possesion1counter / i) * 100);
        double possesion2 = Math.round((possesion2counter / i) * 100);

        frame.stats.setValueAt(String.valueOf(possesion1), 0, 0);
        frame.stats.setValueAt(String.valueOf(team1.currentshoots), 1, 0);
        frame.stats.setValueAt(String.valueOf(team1.currentShootsonTarget), 2, 0);
        frame.stats.setValueAt(String.valueOf(team1.currentFouls), 3, 0);
        frame.stats.setValueAt(String.valueOf(team1.currentYellows), 4, 0);
        frame.stats.setValueAt(String.valueOf(team1.currentReds), 5, 0);
        frame.stats.setValueAt(String.valueOf(possesion2), 0, 2);
        frame.stats.setValueAt(String.valueOf(team2.currentshoots), 1, 2);
        frame.stats.setValueAt(String.valueOf(team2.currentShootsonTarget), 2, 2);
        frame.stats.setValueAt(String.valueOf(team2.currentFouls), 3, 2);
        frame.stats.setValueAt(String.valueOf(team2.currentYellows), 4, 2);
        frame.stats.setValueAt(String.valueOf(team2.currentReds), 5, 2);
    }

    /**
     * Method printMinutes() is used in displaying minutes during the game, when no action took place.
     * It requires a following parameter:
     * @param i -th minute
     */
    void printMinutes(int i) {
        if (i == 1) {
            frame.minutes.get(0).setText(i + "':");
        } else if (i == 2) {
            frame.minutes.get(1).setText(i + "':");
        } else if (i == 3) {
            frame.minutes.get(2).setText(i + "':");
        } else if (i == 4) {
            frame.minutes.get(3).setText(i + "':");
        } else if (i == 5) {
            frame.minutes.get(4).setText(i + "':");

        } else {
            frame.minutes.get(0).setText(frame.minutes.get(1).getText());
            frame.minutes.get(1).setText(frame.minutes.get(2).getText());
            frame.minutes.get(2).setText(frame.minutes.get(3).getText());
            frame.minutes.get(3).setText(frame.minutes.get(4).getText());
            frame.minutes.get(4).setText(i + "':");
        }
    }
}
//