package Pente;

import java.util.Scanner;
/**
 * This class implements the Player interface, representing the human player.
 * @author Yihan
 *
 */
public class HumanPlayer implements Player{
	private Stone stone;
	private Scanner in;
	/**
	 * This is the constructor of the human player.
	 */
	public HumanPlayer(Scanner in) {
		this.in=in;
		stone=Stone.RED;
	}
	
	@Override
	public Coordinate getMove(Board b) {
		System.out.println("Please enter the x and y coordinates of the location that you wish to place your piece:");
		
		int row=in.nextInt();
		int column=in.nextInt();
		//in.close();
		
		if(b.getMoveNumber()==0 && (row!=9 || column!=9)) {
			throw new IllegalArgumentException();
		}
		
		if((b.getMoveNumber()==2) && (Math.abs(row-9)<=2 || Math.abs(column-9)<=2)) 
		{
			throw new IllegalArgumentException();
		}
		
		MyCoordinate ret=new MyCoordinate(row, column);
		if(b.isOutOfBounds(ret)) {
			throw new IllegalArgumentException();
		}
		else if(b.pieceAt(ret)!=Stone.EMPTY) {
			throw new IllegalArgumentException();
		}
		return ret;
	}

	@Override
	public Stone getStone() {
		return stone;	
	}
	
	
}
