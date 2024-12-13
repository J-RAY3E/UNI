package Enviroment.Entitys;

import java.util.Objects;

import Enviroment.Exceptions.NullStateEntityException;

public abstract class ActiveEntity implements UpdatableEntity {


    protected Boolean stateAction;
    public ActiveEntity(){
    }
    public ActiveEntity(Boolean stateAction){
        if (stateAction == null) {
            throw new NullStateEntityException("State action cannot be null.");
        }
        this.stateAction = stateAction;

    }
    public Boolean  getstate() {
        return this.stateAction;
    }

    public void setStateAction(Boolean stateAction){
        if (stateAction == null) {
            throw new NullStateEntityException("State action cannot be null.");
        }
        this.stateAction = stateAction;
    }

    public Boolean getStateAction(){
        return this.stateAction;
    }

    @Override
    public boolean equals(Object activeEntity2) {
        if (this == activeEntity2) return true; // Mismo objeto
        if (activeEntity2 == null || getClass() != activeEntity2.getClass()) return false;
        return this.getClass().getSimpleName()  == ((ActiveEntity) activeEntity2).getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.stateAction, this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
