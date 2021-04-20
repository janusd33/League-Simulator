import java.util.Random;

/**
 * Player class represents all the players that are in a simulator.
 */
public class Player {

    /**
     * Basic information about the player.
     */
    String name;
    String surname;
    Club club;
    Position position;
    int overall;

    /**
     * General statistic counters (updated after every match played).
     */
    int goals;
    int assists;
    int canadians = goals + assists;
    int yellows;
    int reds;
    int shoots;
    int shootsonTarget;
    int fouls;
    int fouled;

    /**
     * Current statistic counters (updated during every match, reset right after its end).
     */
    int currentYellows;
    int currentReds;


    /**
     * Probabilities of specific action.
     */
    Random r = new Random();
    final double yellow = 0.3;
    final double red = 0.05;
    final double shootOnTarget = 0.5;
    final double goal = 0.4;

    /**
     * Constructor involves:
     *
     * @param name     of a player
     * @param surname  of a player
     * @param position (see: enum Position)
     * @param overall  (integer between 0 and 100)
     */

    Interface frame;

    public Player(String name, String surname, Position position, int overall, Interface frame) throws Exception {
        this.name = name;
        this.surname = surname;
        this.position = position;
        if (overall < 0 || overall > 100) {
            throw new Exception("Overall must be an integer between 0 and 100.");
        } else {
            this.overall = overall;
        }
        this.frame=frame;
    }

    /**
     * Method shoot involves:
     *
     * @param goalkeeper of a opponent's team
     */
    public void shoot(Player assistant, Player goalkeeper, int i) {
        /**
         * Action does not take place if player has obtained a red card earlier.
         */
        if (currentReds == 1 || assistant.currentReds == 1) {
            return;
        }

        /**
         * A double between 0 and 1 is initialized to determine what type of shoot is going to happen.
         */
        double shootrate = r.nextDouble();
            shoots += 1;
            this.club.currentshoots+=1;
        if (shootrate < shootOnTarget * goal + (double) (overall - goalkeeper.overall) / 100) {
            goals += 1;
            assistant.assists += 1;
            shootsonTarget += 1;
            goalkeeper.goals += 1;
            printShoot(1,i,assistant,goalkeeper);
            this.club.currentGoals+=1;
            this.club.currentShootsonTarget+=1;
            return;
        }
        if (shootrate < shootOnTarget * overall / 100) {
            shootsonTarget += 1;
            goalkeeper.shootsonTarget += 1;
            this.club.currentShootsonTarget+=1;
            printShoot(2,i,assistant,goalkeeper);
            return;
        }
        printShoot(3,i,assistant,goalkeeper);

    }

    /**
     * Method foul involves:
     *
     * @param player of opponnent's team that is fouled.
     */
    public void foul(Player player, int i) {
        /**
         * Action does not take place if player has obtained a red card earlier.
         */
        if (currentReds == 1 || player.currentReds == 1) {
            return;
        }

        /**
         * A double between 0 and 1 is initialized to determine how serious the foul is.
         */
        double faulrate = r.nextDouble();
        fouls += 1;
        this.club.currentFouls+=1;
        player.fouled += 1;
        if (faulrate <= red) {
            currentReds += 1;
            this.club.currentReds+=1;

            printFoul(1,i,player);
            return;
        }
        if (faulrate <= yellow) {
            currentYellows += 1;
            this.club.currentYellows+=1;
            if (currentYellows == 2) {
                currentReds += 1;
                this.club.currentReds+=1;
                printFoul(2,i,player);
                return;
            }
            printFoul(3,i,player);
            return;
        }
        printFoul(4,i,player);
    }

    public int getAssists() {
        return assists;
    }

    public int getGoals() {
        return goals;
    }

    public int getCanadians(){
        return canadians;
    }

    public int getYellows() {
        return yellows;
    }

    public int getReds() {
        return reds;
    }

    public int getShoots() {
        return shoots;
    }

    public int getShootsonTarget() {
        return shootsonTarget;
    }

    public int getFouls() {
        return fouls;
    }

    public int getFouled() {
        return fouled;
    }

    /**
     * Method used for presenting actions during the game.
     * @param k
     * @param i
     * @param assistant
     * @param goalkeeper
     */

    public void printShoot(int k, int i, Player assistant, Player goalkeeper){
        String string;
        if(k==1){
            string =  " Great pass from " + assistant.surname + " (" + this.club.name + ") and "
                    + surname + " (" + this.club.name + ") scores a goal!";
        }
        else if(k==2){
            string = " " + surname + " (" + this.club.name + ") shoots, great save from " +
                    goalkeeper.surname + " (" + goalkeeper.club.name + ").";


        }
        else{
            string = " " + surname + " (" + this.club.name + ") shoots, but it's off target.";
        }

        if(i==1){
            frame.minutes.get(0).setText(i+"':"+string);}
        else if(i==2){
            frame.minutes.get(1).setText(i+"':"+string);}
        else if(i==3){
            frame.minutes.get(2).setText(i+"':"+string);}
        else if(i==4){
            frame.minutes.get(3).setText(i+"':"+string);}
        else if (i==5){
            frame.minutes.get(4).setText(i+"':"+string);

        }
        else{
            frame.minutes.get(0).setText(frame.minutes.get(1).getText());
            frame.minutes.get(1).setText(frame.minutes.get(2).getText());
            frame.minutes.get(2).setText(frame.minutes.get(3).getText());
            frame.minutes.get(3).setText(frame.minutes.get(4).getText());
            frame.minutes.get(4).setText(i+"':"+string);
        }
    }

    /**
     * Method used for presenting actions during the match.
     * @param k
     * @param i
     * @param player
     */
    public void printFoul(int k, int i, Player player){
        String string;
        if(k==1){
            string =  " " + surname + " (" + this.club.name + ") fouls " + player.surname
                    + " (" + player.club.name + ") and referee shows him a red card.";
        }
        else if(k==2){
            string = " " + surname + " (" + this.club.name + ") fouls " + player.surname
                    + " (" + player.club.name + ") and is booked for the second time - it's a red card then.";


        }
        else if(k==3){
            string = " " + surname + " (" + this.club.name + ") fouls " + player.surname
                    + " (" + player.club.name + ") and referee shows him a yellow card.";
        }
        else{
            string =" " + surname + " (" + this.club.name + ") fouls " + player.surname
                    + " (" + player.club.name + ").";
        }

        if(i==1){
            frame.minutes.get(0).setText(i+"':"+string);}
        else if(i==2){
            frame.minutes.get(1).setText(i+"':"+string);}
        else if(i==3){
            frame.minutes.get(2).setText(i+"':"+string);}
        else if(i==4){
            frame.minutes.get(3).setText(i+"':"+string);}
        else if (i==5){
            frame.minutes.get(4).setText(i+"':"+string);

        }
        else{
            frame.minutes.get(0).setText(frame.minutes.get(1).getText());
            frame.minutes.get(1).setText(frame.minutes.get(2).getText());
            frame.minutes.get(2).setText(frame.minutes.get(3).getText());
            frame.minutes.get(3).setText(frame.minutes.get(4).getText());
            frame.minutes.get(4).setText(i+"':"+string);
        }
    }
}
//