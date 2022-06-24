package main;

import java.util.Scanner;

import algorithms.Algorithms;
import context.*;

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
	
	public static void main(String[] args) {
		int choice;
		Algorithms aAlgo;
		do {
			choice = showMenu();
			switch(choice) {
			case 1:
				aAlgo = Context.setUpAlgorithms(new SetBFS());
				aAlgo.execute();
				aAlgo.play();
				break;
			case 2:
				aAlgo = Context.setUpAlgorithms(new SetDijkstra());
				aAlgo.execute();
				aAlgo.play();
				break;
			case 3:
				aAlgo = Context.setUpAlgorithms(new SetBellmanFord());
				aAlgo.execute();
				aAlgo.play();
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