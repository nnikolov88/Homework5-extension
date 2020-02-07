package fmi.informatics.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.SortOrder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import fmi.informatics.comparators.AgeComparator;
import fmi.informatics.comparators.EgnComparator;
import fmi.informatics.comparators.HeightComparator;
import fmi.informatics.comparators.NameComparator;
import fmi.informatics.comparators.PersonComparator;
import fmi.informatics.comparators.WeightComparator;
import fmi.informatics.extending.Person;
import fmi.informatics.extending.Professor;
import fmi.informatics.extending.Student;

// създаваме клас PersonDataGUI
public class PersonDataGUI {
	
	public static Person[] people;
	JTable table;
	PersonDataModel personDataModel;

	public static void main(String[] args) {
		getPeople();
		PersonDataGUI gui = new PersonDataGUI();
		gui.createAndShowGUI();
	}
	
	public static void getPeople() {
		people = new Person[8];
		
		for (int i = 0; i < 4; i++) {
			Person student = Student.StudentGenerator.make();
			people[i] = student;
		}
		
		for (int i = 4; i < 8; i++) {
			Person professor = Professor.ProfessorGenerator.make();
			people[i] = professor;
		}
	}
	
	public void createAndShowGUI() {
		JFrame frame = new JFrame("Таблица с данни за хора");
		frame.setSize(500, 500);
		
		JLabel label = new JLabel("Списък с потребители", JLabel.CENTER);
		
		personDataModel = new PersonDataModel(people);
		table = new JTable(personDataModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Добавяме бутон за сортиране по години с Comparable interface
		JButton buttonSortAge = new JButton("Сортирай по години");

		// Добавяме бутон за сортиране
		JButton buttonSort = new JButton("Сортирай");
		
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(label, BorderLayout.NORTH);
		container.add(scrollPane, BorderLayout.CENTER);
		
		container.add(buttonSortAge, BorderLayout.BEFORE_FIRST_LINE);
		container.add(buttonSort, BorderLayout.SOUTH);
		
		// Добавяме listener към бутона за сортиране по години
		buttonSortAge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Arrays.sort(people);
				table.repaint();
			}
		});
		
		// Добавяме диалог
		final JDialog sortDialog = new CustomDialog(getSortText(), this);
		
		// Добавяме listener към бутона за сортиране
		buttonSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortDialog.pack();
				sortDialog.setVisible(true);
			}
		});
		
		frame.setVisible(true);
	}
	
	public void sortTable(int intValue, JTable table, Person[] people) {
		PersonComparator comparator = null;
		
		switch (intValue) {
			case 1: 
				comparator = new NameComparator(); 
				comparator.setSortOrder(SortOrder.ASCENDING); //сортираме Имената по възходящ ред
				break;
			case 2: 
				comparator = new EgnComparator();
				comparator.setSortOrder(SortOrder.ASCENDING); //сортираме ЕГН по възходящ ред
				break;
			case 3:
				comparator = new HeightComparator();
				comparator.setSortOrder(SortOrder.DESCENDING); //сортираме Височината по низходящ ред
				break;
			case 4: 
				comparator = new WeightComparator();
				comparator.setSortOrder(SortOrder.DESCENDING);//сортираме Теглото по низходящ ред
				break;
			case 5:
				comparator = new AgeComparator();
				comparator.setSortOrder(SortOrder.ASCENDING);//сортираме Годините по възходящ ред
				break;
		}

		if (comparator == null) { // Ако стойността е null - сортирай по подразбиране
			Arrays.sort(people); // Сортировка по подразбиране по години
		} else {
			Arrays.sort(people, comparator);
		}
		
		table.repaint();	
	}
	
	private static String getSortText() {
		return "Моля, въведете цифрата на колоната, по която да се сортират данните: \n" +
				" 1 - Име (възходящ ред)\n" +
				" 2 - ЕГН (възходящ ред) \n" +
				" 3 - Височина(низходящ ред) \n" +
				" 4 - Тегло(низходящ ред) \n" +
				" 5 - Години (възходящ ред)\n"; 
	}
}
