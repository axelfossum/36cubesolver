import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {
	
	private List<String> colors = colors();
	private String [][]colorCombinations = allCombinations(colors);
	private Piece [][] solveAttempt = new Piece[6][6];
	
	private int [][] startBoard = {{ 5, 3, 2, 1, 4, 6} ,
								  { 4, 1, 5, 2, 5, 3} ,
								  { 6, 5, 3, 4, 1, 2} ,
								  { 1, 2, 6, 3, 6, 4} ,
								  { 2, 4, 1, 6, 3, 5} ,
								  { 3, 6, 4, 5, 2, 1} ,
	};
	
	public boolean solve() {
		solveFill(solveAttempt);
		return solver(solveAttempt, 0);
	}
	
	private boolean solver(Piece[][] p, int row) {
		for(int i = 0; i<colorCombinations.length; i++) {
			addColorRow(p, colorCombinations[i], row);
			if(check(p,row)) {
				if(row ==5) {
					if(p[1][2].getColor().equals("Y")&&p[3][2].getColor().equals("O")) {
					return true;
					}
				}
				else if (solver(p,row+1)) {
					return true;
				}
			}
		}
		removeColorRow(p,row);
		return false;
	}
	
	private boolean check(Piece[][] p, int row) {
		boolean works = true;
		int col = 0;
		while(works&&col<p.length) {
			for(int i = 0; i<row; i++) {
				if(p[i][col].getColor().equals(p[row][col].getColor())) {
					works = false;
					break;
				}
			}
			col++;
		}
		Set<Piece> containsSimilarPiece = new HashSet<Piece>();
		for(int i = 0; i<row+1; i++) {
			for(int j=0; j<p[i].length; j++ ) {
				containsSimilarPiece.add(p[i][j]);
			}
		}
		
	 boolean duplicateCheck = containsSimilarPiece.size() == (row+1)*p.length;
		return works && duplicateCheck;
	}
	
	private void addColorRow(Piece[][] p, String[] colorRow, int row) {
		for(int i = 0; i<p.length; i++) {
			p[row][i].setColor(colorRow[i]);
		}
	}
	
	private void removeColorRow(Piece[][] p,int row) {
		for(int i = 0; i<p.length; i++) {
			p[row][i].setColor(null);
		}
	}
	
	private void solveFill(Piece[][] p) {
		for(int i = 0; i<p.length; i++) {
			for(int j = 0; j<p[i].length; j++) {
				p[i][j] = new Piece(startBoard[i][j], null);
			}
		}
	}
	
	private List<String> colors() {
		List<String> colorList = new ArrayList<String>(6);
		colorList.add("R");
		colorList.add("B");
		colorList.add("G");
		colorList.add("Y");
		colorList.add("P");
		colorList.add("O");
		return colorList;
	}
	
	public String[][] allCombinations(List<String> s){
		Set<List<String>> uniqueComb = new HashSet<>();
		String[][] allCombinations = new String[720][6];
		while(uniqueComb.size()!=720) {
			Collections.shuffle(s);
			ArrayList<String> d = (ArrayList<String>) ((ArrayList<String>) s).clone();
			uniqueComb.add(d);
		}
		int i = 0;
		for(List<String> l: uniqueComb) {
			for(int j = 0; j<l.size(); j++) {
				allCombinations[i][j] = l.get(j);
			}
			i++;
		}
		return allCombinations;
	}
	
	private class Piece {
		private int value;
		private String color;
		
		public Piece(int value, String color){
			this.value = value;
			this.color = color;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getColor() {
			return color;
		}
		
		public void setColor(String s) {
			color = s;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Piece) {
			return this.value == ((Piece) obj).getValue() && this.color.equals(((Piece) obj).getColor());
			}
			return false;
		}
		
		@Override
		public String toString() {
			return color + value;
		}
		
		@Override
		public int hashCode() {
			return color.hashCode();
		}	
	}

	public static void main(String[] args) {
		Solver s = new Solver();
		s.solve();
		for(Piece[] xs: s.solveAttempt) {
			System.out.println(Arrays.toString(xs));
		}
	}
}
