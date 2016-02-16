import java.util.Scanner;

public class RobotGame 
{
	private static int count = 1;

	public static void main(String[] args) 
	{
		System.out.println("Welcome to the robot race simulator!");

		// Create two robot objects
		Robot robot1 = initializeBot();
		Robot robot2 = initializeBot();
		
		// game loop
		while (!robot1.hasReached() || !robot2.hasReached()) {
			System.out.println("\n--Move!--");

			if (!robot1.hasReached())
				race(robot1, robot2);

			if (!robot2.hasReached())
				race(robot2, robot1);
		}

		System.out.println("\nGame over!");
		// display score
		System.out.printf("%s: %d \n%s: %d\n",
				robot1.getName(), robot1.getScore(),
				robot2.getName(), robot2.getScore());
	}
	
	private static Robot initializeBot()
	{
		if(count > 2) count = 1; // prevent more than 2 players

		Scanner scanName = new Scanner(System.in);
		Scanner scanDirection = new Scanner(System.in);

		String namePrompt = ((count == 1) ? "\nEnter the name of the first robot: " :
			"\nEnter the name of the second robot: ");
		String directionPrompt = ((count == 1) ? "Enter the direction of the first robot: " :
			"Enter the direction of the second robot: ");

		System.out.printf("%s", namePrompt);
		String name = scanName.nextLine();
		System.out.printf("%s \n(UP, RIGHT, or DIAGONAL): ", directionPrompt);
		String direction = scanDirection.nextLine();

		count++;

		return new Robot(name, direction);
	}
	
	public static void race(Robot robot, Robot otherRobot)
	{
		System.out.print(robot.getName());
		robot.move();
		robot.checkLocation(otherRobot);
		System.out.printf(" (%d, %d) %s\n", robot.getX(), robot.getY(),
				robot.getDirection().toString());
		checkPace(robot, otherRobot);
	}

	public static void checkPace(Robot robot, Robot otherRobot)
	{
		boolean isWinnerDeclared = (robot.hasReached() || otherRobot.hasReached());

		if (robot.isAhead(otherRobot) && !isWinnerDeclared) {
			System.out.printf("%s is ahead!\n", robot.getName());
		} else if (robot.isSameDistance(otherRobot) && !isWinnerDeclared) {
			System.out.println("Both robots are the same distance.");
		}
	}
}