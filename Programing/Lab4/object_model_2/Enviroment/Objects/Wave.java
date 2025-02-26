package Enviroment.Objects;


import Enviroment.Entitys.ActiveEntity;
import Enviroment.Entitys.ObservableEntity;
import Enviroment.Enums.Action;
import Enviroment.Enums.Direction;


public class Wave extends ActiveEntity implements ObservableEntity {
    Direction direction = Direction.ToSea;
    int rythmwave;
    private Boolean  wasObservated = true;
    public Wave(Boolean stateAction){
        super(stateAction);

    }

    public Boolean getWasObservable(){
        
        return this.wasObservated;
    }

    public void setRythmwave(int rw){
        this.rythmwave = rw;
    }
    public int getRythmwave(){
        return this.rythmwave;
    }

    @Override
    public String getDistance(){
        return null;
    };


    @Override
    public void wasObservable(Action actionDone){
        //NO NEEDED
    }
    
    public void Update(){
        //NO NEEDED
    };
}
