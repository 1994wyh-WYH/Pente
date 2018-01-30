package Pente;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements the Player class, representing an auto computer player.
 * @author Yihan
 *
 */
public class yihanwPlayer implements Player{
	private Stone stone;
	public yihanwPlayer() {
			stone=Stone.YELLOW;
	}
	
	@Override
	public Coordinate getMove(Board b) {	
		MyCoordinate move=new MyCoordinate(9,9);
		int max=0;
		
		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				if(!b.isEmpty(new MyCoordinate(i,j))){
					continue;
				}
				int score=getScore(b, new MyCoordinate(i,j), stone);
				if(score>max) {
					max=score;
					move=new MyCoordinate(i,j);
				}
			}
		}
		Random rand=new Random();
		int z=1;
		while(b.isOutOfBounds(move) || !b.isEmpty(move)){
			int x=rand.nextInt(19);
			int y=rand.nextInt(19);
			move=new MyCoordinate(move.getRow()+x*z,move.getColumn()+y*z);
			z=-1;
		}
		return move;	
	}
	
	@Override
	public Stone getStone() {
		return stone;	
	}
	
	private ArrayList<Integer> hasCaptures(Board b, MyCoordinate c, Stone s) {
		ArrayList<Integer> ret=new ArrayList<>();
		Stone check=Stone.RED;
		if(s==Stone.RED) {
			check=Stone.YELLOW;
		}
		//check captures
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow()+3, c.getColumn()+3))
				&&b.pieceAt(new MyCoordinate(c.getRow()+1, c.getColumn()+1))==check&&b.pieceAt(new MyCoordinate(c.getRow()+2, c.getColumn()+2))==check
				&&b.pieceAt(new MyCoordinate(c.getRow()+3, c.getColumn()+3))==s) {
			ret.add(0);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow()-3, c.getColumn()-3))
				&&b.pieceAt(new MyCoordinate(c.getRow()-1, c.getColumn()-1))==check&&b.pieceAt(new MyCoordinate(c.getRow()-2, c.getColumn()-2))==check
				&&b.pieceAt(new MyCoordinate(c.getRow()-3, c.getColumn()-3))==s) {
			ret.add(1);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow()+3, c.getColumn()))
				&&b.pieceAt(new MyCoordinate(c.getRow()+1, c.getColumn()))==check&&b.pieceAt(new MyCoordinate(c.getRow()+2, c.getColumn()))==check
				&&b.pieceAt(new MyCoordinate(c.getRow()+3, c.getColumn()))==s) {
			ret.add(2);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow()-3, c.getColumn()))
				&&b.pieceAt(new MyCoordinate(c.getRow()-1, c.getColumn()))==check&&b.pieceAt(new MyCoordinate(c.getRow()-2, c.getColumn()))==check
				&&b.pieceAt(new MyCoordinate(c.getRow()-3, c.getColumn()))==s) {
			ret.add(3);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow(), c.getColumn()+3))
				&&b.pieceAt(new MyCoordinate(c.getRow(), c.getColumn()+1))==check&&b.pieceAt(new MyCoordinate(c.getRow(), c.getColumn()+2))==check
				&&b.pieceAt(new MyCoordinate(c.getRow(), c.getColumn()+3))==s) {
			ret.add(4);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow(), c.getColumn()-3))
				&&b.pieceAt(new MyCoordinate(c.getRow(), c.getColumn()-1))==check&&b.pieceAt(new MyCoordinate(c.getRow(), c.getColumn()-2))==check
				&&b.pieceAt(new MyCoordinate(c.getRow(), c.getColumn()-3))==s) {
			ret.add(5);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow()+3, c.getColumn()-3))
				&&b.pieceAt(new MyCoordinate(c.getRow()+1, c.getColumn()-1))==check&&b.pieceAt(new MyCoordinate(c.getRow()+2, c.getColumn()-2))==check
				&&b.pieceAt(new MyCoordinate(c.getRow()+3, c.getColumn()-3))==s) {
			ret.add(6);
		}
		if(!b.isOutOfBounds(new MyCoordinate(c.getRow()-3, c.getColumn()+3))
				&&b.pieceAt(new MyCoordinate(c.getRow()-1, c.getColumn()+1))==check&&b.pieceAt(new MyCoordinate(c.getRow()-2, c.getColumn()+2))==check
				&&b.pieceAt(new MyCoordinate(c.getRow()-3, c.getColumn()+3))==s) {
			ret.add(7);
		}
		
		return ret;
		
	}
	
	
	
	public int getScore(Board b, MyCoordinate theCoordinate, Stone stone) {
		//skip already taken spots
		if(b.pieceAt(theCoordinate)!=Stone.EMPTY) {
			return -1;
		}
		int score=0;
		int count=1;
		int deadEnd=0;
		int countOp=0;
		int deadOp=0;
		Stone oppo=stone==Stone.RED?Stone.YELLOW:Stone.RED;
		
		MyCoordinate tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn()+1);
		// get score on the 1st direction
		//self consecutive pieces.
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()+1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		//opponent consecutive pieces.
		tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn()+1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()+1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		
		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn()-1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()-1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn()-1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()-1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		//add score
		if(count>=5) {
			score+=150;
		}
		if(count==4&&deadEnd==0) {
			score+=90;
		}
		if((countOp>=3&&deadOp==0)||(countOp>=4&&deadOp==1)) {
			score+=80;
		}
		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
			score+=40;
		}
		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)||(countOp==3&&deadOp==1)) {
			score+=20;
		}
		if(countOp==2&&deadOp==0) {
			score+=10;
		}
		
		//get capture score. For only once.
		int captures=(hasCaptures(b, theCoordinate, stone)).size();		
		if(captures>0) {
			score+=30*captures;
		}

		
		//get score on the 2nd direction
		count=1;
		deadEnd=0;
		countOp=0;
		deadOp=0;
		tcoord=new MyCoordinate(theCoordinate.getRow(),theCoordinate.getColumn()+1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()+1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow(),theCoordinate.getColumn()+1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()+1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		
		tcoord=new MyCoordinate(theCoordinate.getRow(), theCoordinate.getColumn()-1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()-1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow(), theCoordinate.getColumn()-1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()-1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		
		if(count>=5) {
			score+=150;
		}
		if(count==4&&deadEnd==0) {
			score+=90;
		}
		if((countOp>=3&&deadOp==0)||(countOp>=4&&deadOp==1)) {
			score+=80;
		}
		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
			score+=40;
		}
		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
			score+=20;
		}
		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
			score+=10;
		}
		
		//get score on the 3rd direction
		count=1;
		deadEnd=0;
		countOp=0;
		deadOp=0;
		tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn());
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn());
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn());
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn());
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		
		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn());
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn());
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn());
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn());
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		
		if(count>=5) {
			score+=150;
		}
		if(count==4&&deadEnd==0) {
			score+=90;
		}
		if((countOp>=3&&deadOp==0)||(countOp>=4&&deadOp==1)) {
			score+=80;
		}
		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
			score+=40;
		}
		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
			score+=20;
		}
		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
			score+=10;
		}

		//get score on the 4th direction
		count=1;
		deadEnd=0;
		countOp=0;
		deadOp=0;
		tcoord=new MyCoordinate(theCoordinate.getRow()-1,theCoordinate.getColumn()+1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()+1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow()-1,theCoordinate.getColumn()+1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()+1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		
		tcoord=new MyCoordinate(theCoordinate.getRow()+1, theCoordinate.getColumn()-1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==stone) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()-1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadEnd++;		
		}
		tcoord=new MyCoordinate(theCoordinate.getRow()+1, theCoordinate.getColumn()-1);
		while(!b.isOutOfBounds((Coordinate)tcoord) && b.pieceAt((Coordinate)tcoord)==oppo) {
			countOp++;
			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()-1);
		}
		if(b.isOutOfBounds((Coordinate)tcoord)||b.pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
			deadOp++;		
		}
		if(count>=5) {
			score+=150;
		}
		if(count==4&&deadEnd==0) {
			score+=90;
		}
		if((countOp>=3&&deadOp==0)||(countOp>=4&&deadOp==1)) {
			score+=80;
		}
		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
			score+=40;
		}
		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
			score+=20;
		}
		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
			score+=10;
		}
				
		return score;
	}
	
}
