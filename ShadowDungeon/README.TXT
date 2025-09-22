Design Assumptions and Implementation Decisions

Package Structure

src/
├── app/           # Main application entry point
├── config/        # Configuration management
├── dungeon/       # Room management and game state
├── entities/      # All game objects (players, enemies, items)
├── rooms/         # Room implementations and door mechanics
└── utils/         # Utility functions

Clear separation of functionality. Easier to maintain and navigate.

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

4. Key Collection System

Assumption: Future game expansions may require collecting multiple keys to progress.

Implementation Decision:
- Added `keysRequired` to track number of keys left require to clear the room

Justification:
- Specification doesn't limit key collection to exactly one key per room
- Easier to implement future features without major refactoring

5. Configuration Management

Assumption: Game settings should be externalized and easily modifiable without code changes.

Implementation Decision:
- Created `GameConfig` class to centralize property access
- added methods in IOUtils to handle all file parsing and type conversion

Justification:
- Makes the game easily configurable for different difficulty levels
- Supports localization through message properties
- Reduces repeated gameProperties.get in classes

6. Player movements

Assumption: When player is blocked by obstacles and multiple keys are pressed simultaneously,
player should attempt alternative movement rather than stopping completely

 Implementation Decision:
 - Try to move in another direction where other keys were pressed (gliding behavior)

 Justification:
 - Provides better user experience by maintaining fluid movement
