import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Tournament class is used to organise and run all the matches within a special formay.
 */
public class Tournament {
    ArrayList<Club> teams = new ArrayList<>();
    ArrayList<Match> schedule = new ArrayList<>();
    int matchNumber = 0;
    Interface frame;

    double money = 1000;

    /**
     * Constructor is used to add 4 teams to a tournament:
     *
     * @param team1
     * @param team2
     * @param team3
     * @param team4
     */
    public Tournament(Club team1, Club team2, Club team3, Club team4, Interface frame) {
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        teams.add(team4);
        this.frame = frame;
    }

    /**
     * addSchedule() method creates a schedule of games between the teams. All the teams play twice against
     * each other - once as a host, and once as a guest team.
     */
    void addSchedule() {
        schedule.add(new Match(teams.get(0), teams.get(1), frame));
        schedule.add(new Match(teams.get(2), teams.get(3), frame));
        schedule.add(new Match(teams.get(3), teams.get(0), frame));
        schedule.add(new Match(teams.get(1), teams.get(2), frame));
        schedule.add(new Match(teams.get(0), teams.get(2), frame));
        schedule.add(new Match(teams.get(1), teams.get(3), frame));
        schedule.add(new Match(teams.get(2), teams.get(0), frame));
        schedule.add(new Match(teams.get(3), teams.get(1), frame));
        schedule.add(new Match(teams.get(1), teams.get(0), frame));
        schedule.add(new Match(teams.get(3), teams.get(2), frame));
        schedule.add(new Match(teams.get(0), teams.get(3), frame));
        schedule.add(new Match(teams.get(2), teams.get(1), frame));
        schedule.get(11).islast = true;
    }

    /**
     * run() method is used to play a current match in tournament.
     *
     * @throws InterruptedException
     */
    void run() throws InterruptedException {

        Thread thread = new Thread(schedule.get(matchNumber));
        thread.start();
        matchNumber++;


    }

    /**
     * table() method is used to print actual table situation to a viewer.
     */
    void table() {
        ArrayList<Club> table = teams;
        /**
         * Order of sorting:
         * 1. points
         * 2. goals difference
         * 3. goals for
         */
        table.sort(Comparator.comparingInt(Club::getPoints)
                .thenComparingInt(a -> (a.getGoalsfor() - a.getGoalsagainst()))
                .thenComparingInt(Club::getGoalsfor));
        Collections.reverse(table);
        for (int i = 0; i < 4; i++) {
            frame.rows[i] = new String[]{String.valueOf((i + 1)), table.get(i).name, String.valueOf(table.get(i).matches), String.valueOf(table.get(i).wins),
                    String.valueOf(table.get(i).draws), String.valueOf(table.get(i).loses), String.valueOf(table.get(i).goalsfor),
                    String.valueOf(table.get(i).goalsagainst), String.valueOf((table.get(i).goalsfor - table.get(i).goalsagainst)),
                    String.valueOf(table.get(i).points)};
        }
    }

    /**
     * Below there are methods used in displaying statistics.
     */
    void topGoalscorers() {
        ArrayList<Player> goalscorers = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.goals != 0 & player.position != Position.GOALKEEPER) {
                    goalscorers.add(player);
                }
            }
        }
        goalscorers.sort(Comparator.comparingInt(Player::getGoals));
        Collections.reverse(goalscorers);
        int counter = 0;
        for (int i = 0; i < Math.min(goalscorers.size(), 10); i++) {
            if (goalscorers.get(i).goals == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf(frame.classifications_data[i - 1][0]), goalscorers.get(i).surname,
                        goalscorers.get(i).club.toString(), String.valueOf(goalscorers.get(i).goals)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i+1), goalscorers.get(i).surname,
                        goalscorers.get(i).club.toString(), String.valueOf(goalscorers.get(i).goals)};
            }
            counter = goalscorers.get(i).goals;
        }
    }


    void topAssistants() {
        ArrayList<Player> assistants = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.assists != 0) {
                    assistants.add(player);
                }
            }
        }
        assistants.sort(Comparator.comparingInt(Player::getAssists));
        Collections.reverse(assistants);
        int counter = 0;
        for (int i = 0; i < Math.min(assistants.size(), 10); i++) {
            if (assistants.get(i).assists == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf(frame.classifications_data[i - 1][0]), assistants.get(i).surname,
                        assistants.get(i).club.toString(), String.valueOf(assistants.get(i).assists)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i+1), assistants.get(i).surname,
                        assistants.get(i).club.toString(), String.valueOf(assistants.get(i).assists)};
            }
            counter = assistants.get(i).assists;
        }
    }

    void topCanadians() {
        ArrayList<Player> canadians = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.canadians != 0 & player.position != Position.GOALKEEPER) {
                    canadians.add(player);
                }
            }
        }
        canadians.sort(Comparator.comparingInt(Player::getCanadians));
        Collections.reverse(canadians);
        int counter = 0;
        for (int i = 0; i < Math.min(canadians.size(), 10); i++) {
            if (canadians.get(i).canadians == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf((frame.classifications_data[i - 1][0])), canadians.get(i).surname,
                        canadians.get(i).club.toString(), String.valueOf(canadians.get(i).canadians)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i + 1), canadians.get(i).surname,
                        canadians.get(i).club.toString(), String.valueOf(canadians.get(i).canadians)};
            }
            counter = canadians.get(i).canadians;
        }
    }

    void topYellows() {
        ArrayList<Player> yellows = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.yellows != 0) {
                    yellows.add(player);
                }
            }
        }
        yellows.sort(Comparator.comparingInt(Player::getYellows));
        Collections.reverse(yellows);

        int counter = 0;
        for (int i = 0; i < Math.min(yellows.size(), 10); i++) {
            if (yellows.get(i).yellows == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf((frame.classifications_data[i - 1][0])), yellows.get(i).surname,
                        yellows.get(i).club.toString(), String.valueOf(yellows.get(i).yellows)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i + 1), yellows.get(i).surname,
                        yellows.get(i).club.toString(), String.valueOf(yellows.get(i).yellows)};
            }
            counter = yellows.get(i).yellows;
        }
    }

    void topReds() {
        ArrayList<Player> reds = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.reds != 0) {
                    reds.add(player);
                }
            }
        }
        reds.sort(Comparator.comparingInt(Player::getReds));
        Collections.reverse(reds);
        int counter = 0;
        for (int i = 0; i < Math.min(reds.size(), 10); i++) {
            if (reds.get(i).reds == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf((frame.classifications_data[i - 1][0])), reds.get(i).surname,
                        reds.get(i).club.toString(), String.valueOf(reds.get(i).reds)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i + 1), reds.get(i).surname,
                        reds.get(i).club.toString(), String.valueOf(reds.get(i).reds)};
            }
            counter = reds.get(i).reds;
        }
    }

    void topFouls() {
        ArrayList<Player> fouls = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.fouls != 0) {
                    fouls.add(player);
                }
            }
        }
        fouls.sort(Comparator.comparingInt(Player::getFouls));
        Collections.reverse(fouls);
        int counter = 0;
        for (int i = 0; i < Math.min(fouls.size(), 10); i++) {
            if (fouls.get(i).fouls == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf((frame.classifications_data[i - 1][0])), fouls.get(i).surname,
                        fouls.get(i).club.toString(), String.valueOf(fouls.get(i).fouls)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i + 1), fouls.get(i).surname,
                        fouls.get(i).club.toString(), String.valueOf(fouls.get(i).fouls)};
            }
            counter = fouls.get(i).fouls;
        }
    }

    void topFouled() {
        ArrayList<Player> fouled = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.fouled != 0) {
                    fouled.add(player);
                }
            }
        }
        fouled.sort(Comparator.comparingInt(Player::getFouled));
        Collections.reverse(fouled);
        int counter = 0;
        for (int i = 0; i < Math.min(fouled.size(), 10); i++) {
            if (fouled.get(i).fouled == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf((frame.classifications_data[i - 1][0])), fouled.get(i).surname,
                        fouled.get(i).club.toString(), String.valueOf(fouled.get(i).fouled)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i + 1), fouled.get(i).surname,
                        fouled.get(i).club.toString(), String.valueOf(fouled.get(i).fouled)};
            }
            counter = fouled.get(i).fouled;
        }
    }

    void topShoots() {
        ArrayList<Player> shoots = new ArrayList<>();
        for (Club team : teams) {
            for (Player player : team.players) {
                if (player.shoots != 0) {
                    shoots.add(player);
                }
            }
        }
        shoots.sort(Comparator.comparingInt(Player::getShoots));
        Collections.reverse(shoots);
        int counter = 0;
        for (int i = 0; i < Math.min(shoots.size(), 10); i++) {
            if (shoots.get(i).shoots == counter) {
                frame.classifications_data[i] = new String[]{String.valueOf((frame.classifications_data[i - 1][0])), shoots.get(i).surname,
                        shoots.get(i).club.toString(), String.valueOf(shoots.get(i).shoots)};
            } else {
                frame.classifications_data[i] = new String[]{String.valueOf(i + 1), shoots.get(i).surname,
                        shoots.get(i).club.toString(), String.valueOf(shoots.get(i).shoots)};
            }
            counter = shoots.get(i).shoots;
        }
    }
}
//