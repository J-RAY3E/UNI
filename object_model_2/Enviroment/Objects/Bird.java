package Enviroment.Objects;

import java.util.HashMap;
import java.util.List;



import Enviroment.Entitys.ActiveEntity;
import Enviroment.Entitys.ObservableEntity;
import Enviroment.Enums.Action;
import Enviroment.Enums.Distance;

public class Bird extends ActiveEntity implements ObservableEntity{
    private HashMap<Action,Integer> listActions;
    private Distance distance;
    private Boolean  wasObservated = false;
    
    public Bird (Boolean stateAction,Distance distance){
        super(stateAction);
        this.distance = distance;
    }

    public void wasObservable(Action actionDone){
        if(actionDone.equals(Action.OBSERVE)){
            this.wasObservated = true;
        }
    }


    public Boolean getWasObservable(){
        
        return this.wasObservated;
    }

    public void stateBack(){
        this.wasObservated = !this.wasObservated;
    }

    public String getDistance(){
        return this.distance.toString();
    };
    public void SetActions(List<Action> actions){
        for(Action action: actions){
            listActions.put(action, 0);
        }

    }
    public void Update(){
        //change state variables 
    };
}
