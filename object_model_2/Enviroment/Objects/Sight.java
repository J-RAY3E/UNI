package Enviroment.Objects;

import Enviroment.Entitys.ActiveEntity;


public class Sight extends ActiveEntity{


    public Sight(Boolean stateAction){
        super(stateAction);

    }
    public Boolean  getstate() {
        return this.stateAction;
    }

    public void setStateAction(Boolean state){
        this.stateAction = state;
    }

    public void Update(){
    }



}
