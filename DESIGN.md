##High-level design of this game
* This game consists of 9 classes. The MainGame.java class serves as the
main class, setting up the scenes, game objects, handling inputs, 
maintaining functionalities, etc ... The LevelControl.java class serves
as the game builder, handling the game scene, which is the MainGame, as
well as the transition (Transition.java). The Ball.java handles the 
activities and properties of the ball like moving and bouncing. The 
Paddle.java class maintains the collisions between the ball and paddle,
as well as receiving power ups. The Brick.java class serves as the brick
game object in this game, with different types in the constructor as
represented by BrickType.java class. The PowerUp.java class handles the 
different power up in the game, like collision with the paddle, as well
as updating power ups. 
* Overall, the MainGame.java class would call and spawn all other game
objects, as well as updating the scores and lives. The LevelControl.java
class would add levels from MainGame, and Transition.
##How to add new features
* To add in a new level, first we need to update a new text file outlining
the brick map. This is usually named as map_no.txt, and is stored in the
resource folder. A function in the MainGame.java class would read this 
text file and create the scene for us. We then call the MainGame, and 
a new Transition page in the LevelControl, so that we can have a new
level. 

* To add a new type of brick, first go to the BrickType class and add
another type of brick, as followed

```java
    public static final BrickType HIGH = new BrickType(3,30,HIGH_BRICK);
```
We need to update the lives, the scores, as well as the type of bricks
and update the **stringToBrickType** function. We then go to Brick.java
class to update the **brickDown** function to update how the ball would 
interact with the bricks. 

* To add a new power up, go to the PowerUp.java class to update another
type of power up, as followed:

```java
    public static final PowerUp MULTI_BALL = new PowerUp(MULTI_ID);
```
We would then go to the BrickType.java and Brick.java class to 
update another brick type. We then go the Paddle.java class to add 
another function on how the paddle would interact with the power up,
and then call that function in the PowerUp.java class.
##Design trade-offs
1. For PowerUp.java, I decided to create a new power up by updating 
many classes: BrickType, Brick, and Paddle. The pros for this is that
I can add more features by following the exacts steps as outlined above.
The cons is that when I have more intricate power ups, it would be more 
difficult to implement. One better design would be to make PowerUp.java
as a super class for other PowerUp types, that way the code is much 
more organized and for more intricate implementation, it's easier.

2. When designing the collisions between the ball and the paddle, I 
choose an easy design of the ball bouncing with exact opposite angle 
when hitting the paddle. The pros of this would be that the implementation
is easy enough; I only have to set the Y component of the ball's
velocity to be the opposite. The cons of this is that it adds to a very 
monotonic collisions, and it makes the game very simple and predictable.
