package entities.player;

public class PlayerCharacter {
    private Player player;
    private boolean hasChose = false;

    public PlayerCharacter() {
        this.player = new Player();
    }

    public void changeCharacter(Player player){
        this.player = player;
        this.hasChose = true;

    }

    public boolean hasChose() {
        return hasChose;
    }

    public Player getPlayer() {
        return player;
    }
}
