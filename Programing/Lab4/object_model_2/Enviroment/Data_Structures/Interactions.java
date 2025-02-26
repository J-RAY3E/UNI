package Enviroment.Data_Structures;

import Enviroment.Entitys.ActiveEntity;
import Enviroment.Entitys.ObservableEntity;
import Enviroment.Enums.Action;

public record Interactions(ActiveEntity activeEntity,Action action , Object pasiveEntity, long time) {
    

    public final String toString() {
        try{
            String distance = " ";
            if(pasiveEntity instanceof ObservableEntity){
                distance = ((ObservableEntity) pasiveEntity).getDistance();
            }
            return activeEntity.getClass().getSimpleName() + " " + action + " " + pasiveEntity.getClass().getSimpleName()+" "+ distance;
        }
        catch(ClassCastException e){
            return e.getMessage();
        }
        catch (Exception e) {
            return "Error: Unable to process interaction.";
        }
    }
}