package Enviroment.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Enviroment.Data_Structures.Interactions;
import Enviroment.Objects.Clock;

public class TimeActionManager {
    HashMap<Interactions, Long> DiffTimeInteractions = new HashMap<>();
    public void addInteraction(Interactions interaction,Clock clock){
        if(!this.DiffTimeInteractions.containsKey(interaction)){
            this.DiffTimeInteractions.put(interaction,clock.getTime());
        }
    }

    public void addInteractions(ArrayList<Interactions> interactions,Clock clock){
        for(Interactions interaction: interactions){
            if(!this.DiffTimeInteractions.containsKey(interaction)){
                this.DiffTimeInteractions.put(interaction,clock.getTime());
            }
        }
    }

    public ArrayList<Interactions> getFinishedClases(Clock clock){
        Iterator<Map.Entry<Interactions, Long>> iterator = this.DiffTimeInteractions.entrySet().iterator();
        ArrayList<Interactions>  RegisterTimes = new ArrayList<Interactions>();
        while (iterator.hasNext()) {
            Map.Entry<Interactions, Long> entry = iterator.next();
            long diff = clock.getTime() - entry.getValue();
            if(diff >= entry.getKey().time()){
                RegisterTimes.add(entry.getKey());
            }
        };
        this.Delete(RegisterTimes);
        return   RegisterTimes;
    };


    private void Delete(ArrayList<Interactions>  ListDelete){
        for(Interactions interaction: ListDelete){
            this.DiffTimeInteractions.remove(interaction);
        }
    };

}
