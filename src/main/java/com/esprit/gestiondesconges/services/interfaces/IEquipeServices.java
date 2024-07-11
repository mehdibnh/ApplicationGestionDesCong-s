package com.esprit.gestiondesconges.services.interfaces;

import com.esprit.gestiondesconges.entities.Conge;
import com.esprit.gestiondesconges.entities.Equipe;

import java.util.List;
public interface IEquipeServices {

    Equipe ajouterEquipe(Equipe equipe);
    Equipe supprimerEquipe(Long iidEquipe);
    Equipe recupererEquipe (Long idEquipe);
    Equipe modifierEquipe (Long idEquipe,Equipe equipe);
    List<Equipe> recupererListeEquipe();
    public String assignTeamLeader(Long equipeId, Long employeeId);
    public String addMembersToEquipe(Long equipeId, List<Long> employeeIds);
    public void deleteEquipe(Long equipeId);

    Equipe updateEquipe(Long idEquipe, Equipe equipeDetails);
    void sendTeamLeaderNotification(Long equipeId, Long employeeId);
}
