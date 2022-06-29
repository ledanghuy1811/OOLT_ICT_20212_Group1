package main;

import java.util.Scanner;

import algorithms.Algorithm;
import context.*;
import input.*;

public class Main {
	public static int showMenu() {
		Scanner keyBoard = new Scanner(System.in);
		
		System.out.println("----------------------------------------------------");
		System.out.println("---------------OPTION OF ALGORITHMS-----------------");
		System.out.println("1. BFS");
		System.out.println("2. Dijkstra");
		System.out.println("3. Bellman-Ford");
		System.out.println("0. Exit");
		System.out.println("----------------------------------------------------");
		System.out.println("Your choice: ");
		int choice = keyBoard.nextInt();
		return choice;
	}
	
	public static Algorithm inputAlgorithms(InputAlgorithm algo) {
		return algo.inputAlgorithm();
	}
	
	public static void main(String[] args) {
		int choice;
		Algorithm aAlgo;
		Context context = new Context();
		do {
			choice = showMenu();
			switch(choice) {
			case 1:
				aAlgo = inputAlgorithms(new InputBFS());
				context.setUpAlgorithm(aAlgo);
				context.play(aAlgo);
				break;
			case 2:
				aAlgo = inputAlgorithms(new InputDijkstra());
				context.setUpAlgorithm(aAlgo);
				context.play(aAlgo);
				break;
			case 3:
				aAlgo = inputAlgorithms(new InputBellmanFord());
				context.setUpAlgorithm(aAlgo);
				context.play(aAlgo);
				break;
			case 0:
				System.out.println("Thanks for using!");
				break;
			default:
				System.out.println("Wrong option!");
				break;
			}
		}while(choice != 0);
	}
}
