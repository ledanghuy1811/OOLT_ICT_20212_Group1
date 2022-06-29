package context;

import algorithms.*;
import graph.*;
import java.util.Scanner;

public class Context {
	//constructor
	public Context() {
		
	}
	
	// method
	public void setUpAlgorithm(Algorithm algo) {
		algo.execute();
	}
	public void play(Algorithm algo) {
		algo.play();
	}
}
