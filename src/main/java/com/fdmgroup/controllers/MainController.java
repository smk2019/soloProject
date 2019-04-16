package com.fdmgroup.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Player;
import com.fdmgroup.entities.Team;

public class MainController {

	public static void main(String[] args) {

		//Entity Factory Setup
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soloprojectwk4");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//Team object and creating row
		Team team1 = new Team();
		team1.setTeamId(1);
		team1.setTeamName("Arsenal");
		
		Team team2 = new Team();
		team2.setTeamId(2);
		team2.setTeamName("Liverpool");
		
		//Player objects and rows
		Player player1 = new Player();
		player1.setPlayerId(1);
		player1.setPlayerName("Wil");
		player1.setTeam(team1);
		
		Player player2 = new Player();
		player2.setPlayerId(2);
		player2.setPlayerName("Dom");
		player2.setTeam(team2);
		
		//updating the table 
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		//delete the rows to empty the table
		TypedQuery<Player> queryPlayers = entityManager.createQuery("Delete from Player", Player.class);
		queryPlayers.executeUpdate();
		TypedQuery<Team> queryTeams = entityManager.createQuery("Delete from Team", Team.class);
		queryTeams.executeUpdate();
		
		
		//update rows to tables
		entityManager.persist(team1);
		entityManager.persist(team2);
		entityManager.persist(player1);
		entityManager.persist(player2);
		
		//select all teams into a list and display teams
		TypedQuery<Team> queryAllTeams = entityManager.createQuery("SELECT t FROM Team t ORDER BY t.teamId ASC", Team.class);
		List<Team> listTeams = queryAllTeams.getResultList();
		for(Team eachTeam: listTeams){
			System.out.println(eachTeam.getTeamId()+", "+eachTeam.getTeamName());
		}
		
		//select all players into a list and display players
		TypedQuery<Player> queryAllPlayers = entityManager.createQuery("SELECT p FROM Player p ORDER BY p.playerId ASC", Player.class);
		List<Player> listPlayers = queryAllPlayers.getResultList();
		for(Player eachPlayer: listPlayers){
			System.out.println(eachPlayer.getPlayerId()+", "+eachPlayer.getPlayerName()+", "+eachPlayer.getTeam().getTeamName());
		}
		
		
		entityTransaction.commit();
		entityManager.close();
		

	}

}
