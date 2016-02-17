import java.awt.*;

public class Robot
{
    private String name;
    private int points;
    private boolean isInvalidMove;
    private Point location = new Point();

    enum Direction {
        UP, RIGHT, DIAGONAL
    }

    private Direction direction;

    // constructor.
    public Robot(String name, String direction) throws InvalidDirectionException
    {
        if (direction.equalsIgnoreCase(Direction.UP.toString())
                || direction.equalsIgnoreCase(Direction.RIGHT.toString())
                || direction.equalsIgnoreCase(Direction.DIAGONAL.toString())) {
            this.name = name;
            this.location.setLocation(1, 1);
            this.points = 0;
            //TODO: handle possible exceptions from next line
            this.direction = Direction.valueOf(direction.toUpperCase());
        } else {
            throw new InvalidDirectionException("Invalid initial direction.");
        }
    }

    // This method moves the robot ahead according to
    // its X and or Y values. It also checks the robot's
    // path for any obstructions, and whether it's out of bounds.
    public void move()
    {
        if (hasReached())   // robot makes no moves if it has reached.
            return;

        int move = 1 + (int) (Math.random() * 3); // Generate a random number from 1 to 3

        if (direction.equals(Direction.UP)) {
            if ((getY() + move) > 8) {
                System.out.print(" Position Y out of bounds.");
                isInvalidMove = true;
                return;
            } else {
                location.setLocation(getX(), getY()+move);
            }
        } else if (direction.equals(Direction.RIGHT)) {
            if ((getX() + move) > 8) {
                System.out.print(" Position X out of bounds.");
                isInvalidMove = true;
                return;
            } else {
                location.setLocation(getX()+move, getY());
            }
        } else if (direction.equals(Direction.DIAGONAL)) {
            if ((getX() + move) > 8 || (getY() + move) > 8) {
                System.out.print(" Position XY out of bounds.");
                isInvalidMove = true;
                return;
            } else {
                location.setLocation(getX()+move, getY()+move);
            }
        }

        if (hasReached())
            System.out.print(" has reached!");

        isInvalidMove = false;
    }

    /* Checks if a point is occupied by another robot on the grid */
    public void checkLocation(Robot otherRobot)
    {
        changeDirection();
        if (isInvalidMove)
            return;

        int points = getX() + getY();

        if(this.location.equals(otherRobot.getLocation()) &&
                (!this.hasReached() && !otherRobot.hasReached())) {
            this.points -= points;
            System.out.printf(" Space occupied! %d points deducted.", points);
        } else {
            this.points += points;
        }
    }

    /* This method randomizes the robot's direction */
    private void changeDirection()
    {
        int directionNumber = 1 + (int) (Math.random() * 3);

        switch (directionNumber) {
            case 1 : direction = Direction.UP; break;
            case 2 : direction = Direction.RIGHT; break;
            case 3 : direction = Direction.DIAGONAL; break;
        }
    }

    /* Returns true if the robot is ahead of its competitor */
    public boolean isAhead(Robot otherRobot)
    {
        return (this.distance() < otherRobot.distance());
    }

    /* Returns true if the robot is the same distance
       from the goal as its competitor. */
    public boolean isSameDistance(Robot otherRobot)
    {
        return (this.distance() == otherRobot.distance());
    }

    /* Returns true if the robot has reached the goal */
    public boolean hasReached()
    {
        return (distance() == 0);
    }

    private int distance()
    {
        return (int) getLocation().distance(8, 8);
    }

    /*** Getters for the name, X Y points, location,
     direction and score of the robot. ***/

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return (int) this.location.getX();
    }

    public int getY()
    {
        return (int) this.location.getY();
    }

    public Point getLocation()
    {
        return this.location;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public int getScore()
    {
        return points;
    }
}
