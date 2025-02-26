package Enviroment.Controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Enviroment.Data_Structures.Interactions;
import Enviroment.Data_Structures.SeqActions;
import Enviroment.Entitys.ObservableEntity;
import Enviroment.Enums.Action;

public class InteractionsManager {
    HashMap<Integer,SeqActions> actions = new HashMap<>();
    HashMap<Action,Interactions> registerInteractions = new HashMap<>();

    public void addSeqs(int id,SeqActions seqActions){
        this.actions.put( id, seqActions);
    }
    public void Update(ArrayList<Interactions> interactions){
        for(Interactions currentinteraction: interactions){
            Iterator<Map.Entry<Integer, SeqActions>> iterator = actions.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Integer, SeqActions> interaction = iterator.next();
                if(interaction.getValue().getSize() == 0){
                    iterator.remove();
                }

                else if(interaction.getValue().getCurrentAction().equals(currentinteraction)  ){
                        if (currentinteraction.action() == Action.OBSERVE && 
                        currentinteraction.pasiveEntity() instanceof ObservableEntity) {
                            ((ObservableEntity) currentinteraction.pasiveEntity()).wasObservable(currentinteraction.action());
                            currentinteraction.activeEntity().setStateAction(true);;
                        }
                        interaction.getValue().nexInteraction();
                }
            }
        }
    };

    public void printSeq(int id){
        this.actions.get(id).seq();
    }

    public ArrayList<Interactions> getCurrentInteractions(){
        ArrayList<Interactions> currentInteractions = new ArrayList<>();
        for (Map.Entry<Integer, SeqActions> entry : actions.entrySet()) {
            SeqActions seq = entry.getValue();
            if (seq.getSize() > 0) {
                Interactions currentInteraction = seq.getCurrentAction();

                if (currentInteraction.action() != Action.OBSERVE && 
                    currentInteraction.activeEntity() instanceof ObservableEntity) {
                    if(((ObservableEntity) currentInteraction.activeEntity()).getWasObservable()){
                        currentInteractions.add(currentInteraction);
                    }
                }
                else{
                    currentInteractions.add(currentInteraction);
                }
            
            }
        }
        return currentInteractions;
    };


}
