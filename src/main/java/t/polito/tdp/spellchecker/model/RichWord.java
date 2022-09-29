package t.polito.tdp.spellchecker.model;

public class RichWord {

	private String word;
	private boolean correct;
	
	public RichWord(String word, boolean correct) {
		super();
		this.word = word;
		this.correct = correct;
	}

	public String getWord() {
		return word;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RichWord other = (RichWord) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
}
