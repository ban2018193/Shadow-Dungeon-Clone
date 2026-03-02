This is a clone of the game Shadow Dungeon, where Bagel (Java-based graphics library) and Java is used 

How to run:
cd ShadowDungeon --> run src/app/ShadowDungeon

Design Assumptions and Implementation Decisions

-------------------------------------------------------------------------

Design Assumptions

1. Room Hierarchy Design

Assumption: EndRoom and PrepRoom share similar functionality as both serve as "safe spaces" and reset points.

Implementation Decision:
- Created `OutsideRoom` as a parent class for both `PrepRoom` and `EndRoom`
- Both inherit from `Room` → `OutsideRoom` → `PrepRoom`/`EndRoom`

Justification:
- EndRoom acts as a game reset point, functionally similar to the starting PrepRoom
- Both rooms are "outside the dungeon" and represent safe spaces without hazards
- Future expansions may require similar reset/preparation functionality
- Shared interface allows consistent behavior for game state transitions


2. Entity Hierarchy Design

Assumption: All interactive game objects share common properties (position, image).

Implementation Decision:
- `Entity` class as the parent for all game objects
- Child classes: `Player`, `KeyBulletKin`, `River`, `All`, `TreasureBox`
- Entity stores position and image; children implement specific collision detection and behavior

Justification:
- Promotes code reuse
- Easier to add new entity types in future expansions


3. Room Safety Assumptions

Assumption: Rooms outside the main dungeon (PrepRoom, EndRoom) are completely safe spaces.

Implementation Decision:
- OutsideRoom subclasses have no obstacles, hazards, or enemies
- Only BattleRoom contains dangerous elements (has more complex player move validations)

Justification:
- Provides players with guaranteed safe spaces for preparation and completion
- Clear distinction between challenge areas and safe zones


4. Configuration Management

Assumption: Game settings should be externalized and easily modifiable without code changes.

Implementation Decision:
- Created `GameConfig` class to centralize property access
- added methods in IOUtils to handle all file parsing and type conversion

Justification:
- Makes the game easily configurable for different difficulty levels
- Supports localization through message properties
- Reduces repeated gameProperties.get in classes


5. Player movements

Assumption: When player is blocked by obstacles and multiple keys are pressed simultaneously,
player should attempt alternative movement rather than stopping completely

 Implementation Decision:
 - Try to move in another direction where other keys were pressed (gliding behavior)

 Justification:
 - Provides better user experience by maintaining fluid movement

Design explanation

1. Game core:
Dungeon is the core mechanism that manages the states of the room and start of the
game. Store would pause activity in Dungeon when it’s being called.

2. Creating bullet kins and ashen bullet kins:
Since both of them share the same logic but different states, I merged them both
together. We will determine which it will be by initialising the class’s instance differently
by initBulletKin() initAshenBulletKin().

3. Rendering:
The room will take care of all of the renderings. It will contain all of the entities inside and
call their render() method by looping through the collidables.

4. Interaction logic:
Check if collided with any entity (by loop), if yes, call triggerCollisionEvent(). This method is about making
change to player. And if it's being attacked by projectiles, it will call attackByProjectile, which usually
is about making change to itself.
