import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Club {
    /**
     * Main atributtes of club, used both in a single game and in a whole tournament.
     */
    ArrayList<Player> players = new ArrayList<>();
    int currentReds;
    int currentYellows;
    int currentGoals;
    int currentshoots;
    int currentShootsonTarget;
    int currentFouls;
    int matches;
    int wins;
    int draws;
    int loses;
    int goalsfor;
    int goalsagainst;
    int points;
    String name;

    Interface frame;

    /**
     * Constructor involves:
     *
     * @param name of a team;
     */
    public Club(String name, Interface frame) throws Exception {
        String file = "Clubs/"+ name + ".txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        for(int i=0;i<11;i++){
            String newPlayer = reader.readLine();
            String[] newPlayercut = newPlayer.split(";");
            players.add(new Player(newPlayercut[0],newPlayercut[1],Position.valueOf(newPlayercut[2]),
                    Integer.parseInt(newPlayercut[3]), frame));
        }
        for (Player x : this.players) {
            x.club = this;
        }
        this.name = name;
        this.frame=frame;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * count() method is used in an endgame() method of a Match class. It updates data about players' statistics.
     */
    public void count() {
        for (Player x : players) {
            x.reds += x.currentReds;
            x.yellows += x.currentYellows;
            x.canadians=x.goals+x.assists;
        }
    }

    /**
     * refresh() method zeros all the statistics of a recent played game in order to count statistics
     * for the following one.
     */
    public void refresh() {
        currentReds = 0;
        currentYellows = 0;
        currentGoals = 0;
        currentshoots = 0;
        currentShootsonTarget = 0;
        currentFouls = 0;
        for (Player x : players) {
            x.currentReds = 0;
            x.currentYellows = 0;


        }
    }

    /**
     * getPosition() is used in play() method of Match class.
     *
     * @param position of a player
     * @return list of players with a specific position.
     */
    public ArrayList<Player> getPosition(Position position) {
        ArrayList<Player> list = new ArrayList<>();
        for (Player player : players) {
            if (player.position == position) {
                list.add(player);
            }
        }
        return list;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalsfor() {
        return goalsfor;
    }

    public int getGoalsagainst() {
        return goalsagainst;
    }
}
//