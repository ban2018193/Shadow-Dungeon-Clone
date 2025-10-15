package rooms;

import rooms.objects.Door;


/**
 * end room: child of outside room class
 * final room of dungeon
 * display win or lost base on player status
 */
public class EndRoom extends OutsideRoom {

    // ---- titles ----
    private final String winText;
    private final String lostText;

    // ---- constructor ----
    public EndRoom(int index) {
        super(index);
        winText = getConfig().GAME_END_WON;
        lostText =  getConfig().GAME_END_LOST;
        // default will assume player is winning unless they lost
        setTitle(winText);
    }

    // ---- manage status ----

    // set title to lost text, and lock all the doors if player lost
    public void setLostStatus(boolean hasLost) {
        if (hasLost) {
            setTitle(lostText);
            lockAllDoors();
        } else {
            setTitle(winText);
        }
    }

    // lock all the doors in the room
    private void lockAllDoors() {
        for (Door door : getDoors()) {
            if (door == null) {break;}
            door.setUnlocked(false);
        }
    }
}
