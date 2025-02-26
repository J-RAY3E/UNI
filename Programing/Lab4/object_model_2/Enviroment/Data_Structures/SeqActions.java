package Enviroment.Data_Structures;

import java.util.ArrayList;


public class SeqActions {
    public  Boolean isCiclyc;
    public ArrayList<Interactions> actions = new ArrayList<>();
    public SeqActions(){}

    public void addInteraction(Interactions interaction){
        this.actions.add(interaction);
    };
    public void setCiclyc(Boolean isCiclyc){
        this.isCiclyc = isCiclyc;
    }
    public Interactions getCurrentAction(){
        
        return this.actions.get(0);
    }
    

    public void seq(){
        System.out.println(this.actions);
    }

    public int getSize(){
        return this.actions.size();
    }

    public void nexInteraction(){
        
        if (this.isCiclyc){
            Interactions CurrentInteraction = this.actions.get(0) ;
            System.out.println(CurrentInteraction.toString());
            this.actions.remove(0);
            this.actions.add(CurrentInteraction);

        }
        else{
            if (this.actions.size() >= 0){
                Interactions CurrentInteraction = this.actions.get(0) ;
                System.out.println(CurrentInteraction.toString());
                this.actions.remove(0);
            }
        }
    }

}
