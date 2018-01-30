package Pente;

import java.util.ArrayList;

/**
 * This class implements the Board interface.
 * @author Yihan
 *
 */
public class MyBoard implements Board{
	private Stone[][] matrix;
	private int redCaptures;
	private int yellowCaptures;
	private Stone fivePieces;
	
	
	/**
	 * This is the constructor of the class.
	 * @param row
	 * @param column
	 */
	public MyBoard() {
		matrix=new Stone[19][19];
		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				matrix[i][j]=Stone.EMPTY;
			}
		}
		redCaptures=0;
		yellowCaptures=0;
		fivePieces=Stone.EMPTY;
	}
	
	@Override
	public void placeStone(Stone s, Coordinate c) {
		matrix[c.getRow()][c.getColumn()]=s;
		//check if there is any capture
		ArrayList<Integer> cap=hasCaptures((MyCoordinate)c, s);
		if(cap.size()>0) {
			if(s==Stone.RED) {
				redCaptures+=cap.size();
				removeCaped((MyCoordinate)c,cap,Stone.YELLOW, false);
			}
			else {
				yellowCaptures+=cap.size();
				removeCaped((MyCoordinate)c,cap,Stone.RED, false);
			}
		}
//		//check if itself is captured
//		ArrayList<Integer> caped=isCaptured((MyCoordinate)c, s);
//		if(caped.size()>0) {
//			if(s==Stone.RED) {
//				yellowCaptures+=caped.size();
//				removeCaped((MyCoordinate)c,caped,Stone.YELLOW, true);
//			}
//			else {
//				redCaptures+=caped.size();
//				removeCaped((MyCoordinate)c,caped,Stone.RED, true);
//			}
//		}
		if(numOfPieces((MyCoordinate)c,s,1,1)>=5||numOfPieces((MyCoordinate)c,s,0,1)>=5
				||numOfPieces((MyCoordinate)c,s,1,0)>=5
				||numOfPieces((MyCoordinate)c,s,-1,1)>=5) {
			fivePieces=s;
		}
	}
	
	@Override
	public Stone pieceAt(Coordinate c) {
		return matrix[c.getRow()][c.getColumn()];
	}
	
	@Override
	public boolean isOutOfBounds(Coordinate c) {
		//System.out.println("check out of bound: "+c);
		if(matrix.length<=c.getRow()||c.getRow()<0) {
			return true;
		}
		else if(matrix[c.getRow()].length<=c.getColumn()||c.getColumn()<0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean isEmpty(Coordinate c) {
		if(matrix[c.getRow()][c.getColumn()]==Stone.EMPTY) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int getMoveNumber() {
		int count=0;
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[i].length;j++) {
				if(matrix[i][j]!=Stone.EMPTY) {
					count++;
				}
			}
		}
		return count;
	}
	
	@Override
	public int getRedCaptures() {
		return redCaptures;
	}
	
	@Override
	public int getYellowCaptures() {
		return yellowCaptures;
	}
	
	
	/**
	 * This method counts the number of consecutive pieces given a coordinate and a direction. 
	 * Starts from the exact given coordinate. Checks itself.
	 * @param coord
	 * @param s
	 * @param x
	 * @param y
	 * @return
	 */
	public int numOfPieces(MyCoordinate coord, Stone s, int x, int y) {
		//System.out.println("check num of pieces: "+coord);
		int count=0;
		MyCoordinate tcoord=new MyCoordinate(coord.getRow(),coord.getColumn());
		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()+x, tcoord.getColumn()+y);
		}
		tcoord=new MyCoordinate(coord.getRow()-x, coord.getColumn()-y);
		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
			count++;
			tcoord=new MyCoordinate(tcoord.getRow()-x, tcoord.getColumn()-y);
		}
		return count;
		
	}
	
	/**
	 * This method counts the potential captures given a coordinate.
	 * @param c
	 * @return
	 */
	private ArrayList<Integer> hasCaptures(MyCoordinate c, Stone s) {
		ArrayList<Integer> ret=new ArrayList<>();
		Stone check=Stone.RED;
		if(s==Stone.RED) {
			check=Stone.YELLOW;
		}
		//check captures
		if(!isOutOfBounds(new MyCoordinate(c.getRow()+3, c.getColumn()+3))
				&&pieceAt(new MyCoordinate(c.getRow()+1, c.getColumn()+1))==check&&pieceAt(new MyCoordinate(c.getRow()+2, c.getColumn()+2))==check
				&&pieceAt(new MyCoordinate(c.getRow()+3, c.getColumn()+3))==s) {
			ret.add(0);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow()-3, c.getColumn()-3))
				&&pieceAt(new MyCoordinate(c.getRow()-1, c.getColumn()-1))==check&&pieceAt(new MyCoordinate(c.getRow()-2, c.getColumn()-2))==check
				&&pieceAt(new MyCoordinate(c.getRow()-3, c.getColumn()-3))==s) {
			ret.add(1);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow()+3, c.getColumn()))
				&&pieceAt(new MyCoordinate(c.getRow()+1, c.getColumn()))==check&&pieceAt(new MyCoordinate(c.getRow()+2, c.getColumn()))==check
				&&pieceAt(new MyCoordinate(c.getRow()+3, c.getColumn()))==s) {
			ret.add(2);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow()-3, c.getColumn()))
				&&pieceAt(new MyCoordinate(c.getRow()-1, c.getColumn()))==check&&pieceAt(new MyCoordinate(c.getRow()-2, c.getColumn()))==check
				&&pieceAt(new MyCoordinate(c.getRow()-3, c.getColumn()))==s) {
			ret.add(3);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow(), c.getColumn()+3))
				&&pieceAt(new MyCoordinate(c.getRow(), c.getColumn()+1))==check&&pieceAt(new MyCoordinate(c.getRow(), c.getColumn()+2))==check
				&&pieceAt(new MyCoordinate(c.getRow(), c.getColumn()+3))==s) {
			ret.add(4);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow(), c.getColumn()-3))
				&&pieceAt(new MyCoordinate(c.getRow(), c.getColumn()-1))==check&&pieceAt(new MyCoordinate(c.getRow(), c.getColumn()-2))==check
				&&pieceAt(new MyCoordinate(c.getRow(), c.getColumn()-3))==s) {
			ret.add(5);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow()+3, c.getColumn()-3))
				&&pieceAt(new MyCoordinate(c.getRow()+1, c.getColumn()-1))==check&&pieceAt(new MyCoordinate(c.getRow()+2, c.getColumn()-2))==check
				&&pieceAt(new MyCoordinate(c.getRow()+3, c.getColumn()-3))==s) {
			ret.add(6);
		}
		if(!isOutOfBounds(new MyCoordinate(c.getRow()-3, c.getColumn()+3))
				&&pieceAt(new MyCoordinate(c.getRow()-1, c.getColumn()+1))==check&&pieceAt(new MyCoordinate(c.getRow()-2, c.getColumn()+2))==check
				&&pieceAt(new MyCoordinate(c.getRow()-3, c.getColumn()+3))==s) {
			ret.add(7);
		}
		
		return ret;
		
	}
	
//	private ArrayList<Integer> isCaptured(MyCoordinate coord, Stone s){
//		int r=coord.getRow();
//		int c=coord.getColumn();
//		Stone oppo=s==Stone.RED?Stone.YELLOW:Stone.RED;
//
//		ArrayList<Integer> ret=new ArrayList<>();
//		if(!isOutOfBounds(new MyCoordinate(r+2,c+2)) && pieceAt(new MyCoordinate(r+1,c+1))==s
//				&&!isOutOfBounds(new MyCoordinate(r-1,c-1))
//				&&pieceAt(new MyCoordinate(r-1,c-1))==oppo && pieceAt(new MyCoordinate(r+2,c+2))==oppo) {
//			ret.add(0);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r-2,c-2)) && pieceAt(new MyCoordinate(r-1,c-1))==s
//				&&!isOutOfBounds(new MyCoordinate(r+1,c+1))
//				&&pieceAt(new MyCoordinate(r+1,c+1))==oppo && pieceAt(new MyCoordinate(r-2,c-2))==oppo) {
//			ret.add(1);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r+2,c)) && pieceAt(new MyCoordinate(r+1,c))==s
//				&&!isOutOfBounds(new MyCoordinate(r-1,c))
//				&&pieceAt(new MyCoordinate(r-1,c))==oppo && pieceAt(new MyCoordinate(r+2,c))==oppo) {
//			ret.add(2);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r-2,c)) && pieceAt(new MyCoordinate(r-1,c))==s
//				&&!isOutOfBounds(new MyCoordinate(r+1,c))
//				&&pieceAt(new MyCoordinate(r+1,c))==oppo && pieceAt(new MyCoordinate(r-2,c))==oppo) {
//			ret.add(3);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r,c+2)) && pieceAt(new MyCoordinate(r,c+1))==s
//				&&!isOutOfBounds(new MyCoordinate(r,c-1))
//				&&pieceAt(new MyCoordinate(r,c-1))==oppo && pieceAt(new MyCoordinate(r,c+2))==oppo) {
//			ret.add(4);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r,c-2)) && pieceAt(new MyCoordinate(r,c-1))==s
//				&&!isOutOfBounds(new MyCoordinate(r,c+1))
//				&&pieceAt(new MyCoordinate(r,c+1))==oppo && pieceAt(new MyCoordinate(r,c-2))==oppo) {
//			ret.add(5);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r+2,c-2)) && pieceAt(new MyCoordinate(r+1,c-1))==s
//				&&!isOutOfBounds(new MyCoordinate(r-1,c+11))
//				&&pieceAt(new MyCoordinate(r-1,c+1))==oppo && pieceAt(new MyCoordinate(r+2,c-2))==oppo) {
//			ret.add(6);
//		}
//		if(!isOutOfBounds(new MyCoordinate(r-2,c+2)) && pieceAt(new MyCoordinate(r-1,c+1))==s
//				&&!isOutOfBounds(new MyCoordinate(r+1,c-1))
//				&&pieceAt(new MyCoordinate(r+1,c-1))==oppo && pieceAt(new MyCoordinate(r-2,c+2))==oppo) {
//			ret.add(7);
//		}
//		return ret;
//	}
	
	
	/**
	 * This method removes the captured pieces.
	 * @param c
	 * @param dir
	 * @param s
	 */
	private void removeCaped(MyCoordinate c, ArrayList<Integer> dir, Stone s, boolean startSelf) {
		while(!dir.isEmpty()) {
			if(dir.get(0)==0) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow()+1,c.getColumn()+1);
				if(startSelf) {
					tcoord=new MyCoordinate(c.getRow(),c.getColumn());
				}
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()+1);
				}
				
			}
			else if(dir.get(0)==1) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow()-1,c.getColumn()-1);
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()-1);
				}
			}
			else if(dir.get(0)==2) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow()+1,c.getColumn());
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn());
				}
			}
			else if(dir.get(0)==3) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow()-1,c.getColumn());
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn());
				}
			}
			else if(dir.get(0)==4) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow(),c.getColumn()+1);
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()+1);
				}
			}
			else if(dir.get(0)==5) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow(),c.getColumn()-1);
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()-1);
				}
			}
			else if(dir.get(0)==6) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow()+1,c.getColumn()-1);
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()-1);
				}
			}
			else if(dir.get(0)==7) {
				MyCoordinate tcoord=new MyCoordinate(c.getRow()-1,c.getColumn()+1);
				while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==s) {
					placeStone(Stone.EMPTY,(Coordinate)tcoord);
					tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()+1);
				}
			}
			dir.remove(0);
		}
	}
	
//	/**
//	 * Count score of each coordinate.
//	 * Include itself. Assume already placed.
//	 * @param theCoordinate
//	 * @param b
//	 * @return
//	 */
//	public int getScore(MyCoordinate theCoordinate, Stone stone) {
//		//skip already taken spots
//		if(pieceAt(theCoordinate)!=Stone.EMPTY) {
//			return -1;
//		}
//		int score=0;
//		int count=1;
//		int deadEnd=0;
//		int countOp=0;
//		int deadOp=0;
//		Stone oppo=stone==Stone.RED?Stone.YELLOW:Stone.RED;
//		
//		MyCoordinate tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn()+1);
//		// get score on the 1st direction
//		//self consecutive pieces.
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()+1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		//opponent consecutive pieces.
//		tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn()+1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()+1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		
//		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn()-1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()-1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn()-1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()-1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		//add score
//		if(count>=5) {
//			score+=150;
//		}
//		if(count==4&&deadEnd==0) {
//			score+=90;
//		}
//		if(countOp>=3&&deadOp==0) {
//			score+=50;
//		}
//		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
//			score+=40;
//		}
//		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
//			score+=20;
//		}
//		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
//			score+=10;
//		}
//		
//		//get capture score. For only once.
//		int captures=(hasCaptures(theCoordinate, stone)).size();		
//		if(captures>0) {
//			score+=30*captures;
//		}
//
//		
//		//get score on the 2nd direction
//		count=1;
//		deadEnd=0;
//		countOp=0;
//		deadOp=0;
//		tcoord=new MyCoordinate(theCoordinate.getRow(),theCoordinate.getColumn()+1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()+1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow(),theCoordinate.getColumn()+1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()+1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		
//		tcoord=new MyCoordinate(theCoordinate.getRow(), theCoordinate.getColumn()-1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()-1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow(), theCoordinate.getColumn()-1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow(), tcoord.getColumn()-1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		
//		if(count>=5) {
//			score+=150;
//		}
//		if(count==4&&deadEnd==0) {
//			score+=90;
//		}
//		if(countOp>=3&&deadOp==0) {
//			score+=50;
//		}
//		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
//			score+=40;
//		}
//		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
//			score+=20;
//		}
//		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
//			score+=10;
//		}
//		
//		//get score on the 3rd direction
//		count=1;
//		deadEnd=0;
//		countOp=0;
//		deadOp=0;
//		tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn());
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn());
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow()+1,theCoordinate.getColumn());
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn());
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		
//		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn());
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn());
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow()-1, theCoordinate.getColumn());
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn());
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		
//		if(count>=5) {
//			score+=150;
//		}
//		if(count==4&&deadEnd==0) {
//			score+=90;
//		}
//		if(countOp>=3&&deadOp==0) {
//			score+=50;
//		}
//		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
//			score+=40;
//		}
//		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
//			score+=20;
//		}
//		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
//			score+=10;
//		}
//
//		//get score on the 4th direction
//		count=1;
//		deadEnd=0;
//		countOp=0;
//		deadOp=0;
//		tcoord=new MyCoordinate(theCoordinate.getRow()-1,theCoordinate.getColumn()+1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()+1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow()-1,theCoordinate.getColumn()+1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow()-1, tcoord.getColumn()+1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		
//		tcoord=new MyCoordinate(theCoordinate.getRow()+1, theCoordinate.getColumn()-1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==stone) {
//			count++;
//			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()-1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadEnd++;		
//		}
//		tcoord=new MyCoordinate(theCoordinate.getRow()+1, theCoordinate.getColumn()-1);
//		while(!isOutOfBounds((Coordinate)tcoord) && pieceAt((Coordinate)tcoord)==oppo) {
//			countOp++;
//			tcoord=new MyCoordinate(tcoord.getRow()+1, tcoord.getColumn()-1);
//		}
//		if(isOutOfBounds((Coordinate)tcoord)||pieceAt((Coordinate)tcoord)!=Stone.EMPTY) {
//			deadOp++;		
//		}
//		if(count>=5) {
//			score+=150;
//		}
//		if(count==4&&deadEnd==0) {
//			score+=90;
//		}
//		if(countOp>=3&&deadOp==0) {
//			score+=50;
//		}
//		if((count==4&&deadEnd==1)||(count==3&&deadEnd==0)){
//			score+=40;
//		}
//		if((count==3&&deadEnd==1)||(count==2&&deadEnd==0)||(countOp==2&&deadOp==0)) {
//			score+=20;
//		}
//		if((count==0&&deadEnd==0)||(countOp==1&&deadOp==0)) {
//			score+=10;
//		}
//				
//		return score;
//	}
	
	public String toString() {
		String ret="";
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[i].length;j++) {
				if(matrix[i][j]==Stone.RED) {
					ret+="|O|,";
				}
				else if(matrix[i][j]==Stone.YELLOW) {
					ret+="|X|,";
				}
				else {
					ret+="| |,";
				}
			}
			ret+="\n";
		}
		return ret;
	}
	
	@Override
	public boolean gameOver() {
		if(getRedCaptures()>=2||getYellowCaptures()>=2||fivePieces!=Stone.EMPTY||getMoveNumber()>=19*19) {
			return true;
		}
		return false;
	}
	
	@Override
	public Stone getWinner() {
		if(getYellowCaptures()>=2||fivePieces==Stone.YELLOW) {
			return Stone.YELLOW;
		}
		else if(getYellowCaptures()>=2||fivePieces==Stone.RED) {
			return Stone.RED;
		}
		else {
			return Stone.EMPTY;
		}
	}


}
