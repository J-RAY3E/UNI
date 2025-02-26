package Enviroment.Objects;

import java.util.ArrayList;
import java.util.Objects;

import Enviroment.Data_Structures.ActionPerson;
import Enviroment.Entitys.UpdatableEntity;
import Enviroment.Enums.Action;
            
public class Person implements UpdatableEntity {

    private String name;
    private ArrayList<ActionPerson> listActions= new ArrayList<>();
    private Sight sight;
    public Person(String name){
        this.name = name;
    }

    public void setName(String name ){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public void setSight(Sight sight){
        this.sight = sight; 
    }

    public void addAction(Action action, boolean isDependent) {
        this.listActions.add(new ActionPerson(action, isDependent));
    }

    public void Update(){
        if (!listActions.isEmpty()) {
            ActionPerson currentAction = listActions.get(0);
            if(this.sight.getStateAction() && currentAction.ActionDependent() ){
                System.out.println(this.name + currentAction.action());
                this.sight.setStateAction(false);
                listActions.remove(0);
            }
            else if(!currentAction.ActionDependent()){
                System.out.println(this.name + currentAction.action());
                listActions.remove(0);
            }
        }
    }

    @Override
    public boolean equals(Object person2) {
        if (this == person2) return true; // Mismo objeto
        if (person2 == null || getClass() != person2.getClass()) return false;
        return this.getName()  == ((Person) person2).getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return this.name;
    }

}