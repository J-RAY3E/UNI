package Enviroment.Entitys;

import Enviroment.Enums.Action;

public interface  ObservableEntity  extends UpdatableEntity{

    public void wasObservable(Action actionDone);
    public Boolean getWasObservable();
    public String getDistance();

}
