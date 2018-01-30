package Pente;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		MyBoard board=new MyBoard();
		//human player gets red stone. Goes first.
		Scanner in=new Scanner(System.in);
		HumanPlayer player=new HumanPlayer(in);
		yihanwPlayer computer=new yihanwPlayer();
		
		System.out.println("The initial board is:\n" +board);
		
		while(!board.gameOver()) {
			MyCoordinate playerCoord=(MyCoordinate)player.getMove(board);
			board.placeStone(player.getStone(), (Coordinate)playerCoord);
			System.out.println("After your move, the board is:\n"+ board);
			System.out.println("Your captures: "+board.getRedCaptures());
			if(board.gameOver()) {
				break;
			}
			
			MyCoordinate computerCoord=(MyCoordinate)computer.getMove(board);
			board.placeStone(computer.getStone(), (Coordinate)computerCoord);
			System.out.println("After the AI player's move, the board is:\n"+board);
			System.out.println("AI's captures: "+board.getYellowCaptures());
		}
		
		Stone win=board.getWinner();
		String winner="";
		if(win==Stone.RED) {
			winner="you";
		}
		else {
			winner="the AI player";
		}
		System.out.println("Game over! The winner is: "+winner+"!");
		
		in.close();
	}

}
