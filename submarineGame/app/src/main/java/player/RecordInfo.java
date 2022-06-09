package player;

import boards.Guess;

public class RecordInfo {
	
	protected String playerToString;
	protected int misses, points, hits;
	protected Guess guess;
	protected char hitOrMiss;
	
	
	public RecordInfo() {
		this("",0,0,0, new Guess(),' ');
	}
	
	public RecordInfo(String toString, int misses, int points, int hits, Guess guess, char hitOrMiss) {
		setToString(toString);
		setMisses(misses);
		setPoints(points);
		setHits(hits);
		setGuess(guess);
		setHitOrMiss(hitOrMiss);
	}


	public void setToString(String toString) {
		this.playerToString = toString;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setGuess(Guess guess) {
		this.guess = guess;
	}

	public void setHitOrMiss(char hitOrMiss) {
		this.hitOrMiss = hitOrMiss;
	}

	@Override
	public String toString() {
		return "RecordInfo [name=" + playerToString + ", misses=" + misses + ", points=" + points + ", hits=" + hits + ", guess="
				+ guess + ", hitOrMiss=" + hitOrMiss + "]";
	}

	
}
