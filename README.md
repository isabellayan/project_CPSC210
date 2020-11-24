# Snake

Snake is a ***classic video game*** where the player controls a **snake** that increases its length by consuming 
**peas**. The game gets more challenging when more peas are ingested. Moreover, the 
*size* of the snake also becomes an obstacle when changing its direction. 

Snake is one of the most frequent game I played during primary school when I didn't have a smartphone. I was thinking 
about making this game for a long time, and I feel this is an excellent chance of doing so. 

## Who will use it?

- **Anyone** interested in cherishing the memory of childhood plays
- People who want to **kill some time**

## User stories

- As a user, I want to be able to start a game with the snake at **default state** (one body long at the center)
- As a user, I want to be able to consume peas at **random** positions
- As a user, I want to be able to view my **current score** on the screen
- As a user, I want to be able to **increase** the length of the snake when peas are consumed (\*add a segment to the 
snake's body\*)
- As a user, I want to be able to **save** the current state of the game
(snake and pea's length & position & current score)
- As a user, I want to be able to **load** the saved state from the file
- As a user, I want to be able to be aware of the **game is over** when the snake collides with *itself*, or the *wall*
- As a user, I want to be able to **pause** and **resume** the game when I want to
- As a user, I want to be able to hear a **sound played** when the snake eats the pea

## What I want to change

- When I first started to design the Body and Snake class in the model package, I thought each Body needs to have its
own direction, so it knows where to move next. However, I then figured out it's enough to let only the Snake knows 
the direction for all body segments. I want to *remove the fields used for the directions* in Body class, *add an Enum 
class for the directions*, and construct an **association from Snake class to the new Enum class**. 
	- The method changeDirection in SnakeGame will also be changed as one Snake has one direction
	- Edits will also need to be done in Body and Snake class, and many tests
