package Pente;

public class MyCoordinate implements Coordinate{
	private int row;
	private int column;
	
	public MyCoordinate(int row, int column) {
		this.row=row;
		this.column=column;
	}
	
	@Override
	public int getRow() {
		return row;
	}
	
	@Override
	public int getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		String ret=Integer.toString(row);
		ret+=" ";
		ret+=Integer.toString(column);
		return ret;
	}
}
