package rooms;

import rooms.objects.Door;


/**
 * The end room displays a win or loss message based on the player's status
 * and can lock all doors if the player loses.
 */
public class EndRoom extends OutsideRoom {

    // ---- Titles ----
    private final String winText;
    private final String lostText;

    // ---- Constructor ----

    /**
     * Create an end room at a given index in dungeon room
     *
     * @param index the index of this room within the dungeon
     */
    public EndRoom(int index) {
        super(index);
        winText = getConfig().GAME_END_WON;
        lostText =  getConfig().GAME_END_LOST;
        // Default will assume player is winning unless they lost
        setTitle(winText);
    }


    // ---- Manage status ----

    /**
     * Sets the end room status
     * If the player has lost, updates the title to the loss text and locks all doors
     * Else, displays the win text
     *
     * @param hasLost true if the player has lost, false if player won
     */
    public void setLostStatus(boolean hasLost) {
        if (hasLost) {
            setTitle(lostText);
            lockAllDoors();
        } else {
            setTitle(winText);
        }
    }


    // Lock all the doors in the room
    private void lockAllDoors() {
        for (Door door : getDoors()) {
            if (door == null) {break;}
            door.setUnlocked(false);
        }
    }
}
