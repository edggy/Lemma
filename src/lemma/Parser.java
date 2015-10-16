package lemma;

import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
	private Settings set;
	private Proof proof;
	private Inferences inf;
	
	public Parser() {
		set = new Settings();
		proof = new Proof();
		inf = new Inferences();
	}
	
	public Parser(BufferedReader br) throws ParseException, IOException {
		//Read in Settings
		set = Settings.parse(br);
		//Read in Inferences
		inf = Inferences.parse(br, set);
		//Read in Proof
		proof = Proof.parse(br, set);
	}
	
	public static Parser parse(BufferedReader br) throws ParseException, IOException {
		return new Parser(br);
	}
	
	public Settings getSettings() {
		return set;
	}
	
	public Inferences getInferences() {
		return inf;
	}
	
	public Proof getProof() {
		return proof;
	}
}
