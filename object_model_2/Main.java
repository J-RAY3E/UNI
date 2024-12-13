
import java.util.ArrayList;


import Enviroment.Controllers.InteractionsManager;
import Enviroment.Controllers.TimeActionManager;
import Enviroment.Controllers.UpdateRealizer;
import Enviroment.Data_Structures.Interactions;
import Enviroment.Data_Structures.SeqActions;
import Enviroment.Enums.Action;

import Enviroment.Enums.Distance;
import Enviroment.Objects.Coast;
import Enviroment.Objects.Person;
import Enviroment.Objects.Plane;
import Enviroment.Objects.Sea;
import Enviroment.Objects.Ship;
import Enviroment.Objects.Sight;
import Enviroment.Objects.Wave;
import Enviroment.Objects.Bird;
import Enviroment.Objects.Clock;


public class Main {

    public static final long LONG_TIME = 10000;
    public static final long SHORT_TIME = 1000;
    public static final long HALF_TIME = LONG_TIME/2;

    public static void main(String[] args){

        // CLOCK
        Clock clock = new Clock();
        InteractionsManager inteManager = new InteractionsManager();
        TimeActionManager timeInteManager = new TimeActionManager();

        // WAVE BEHAVIOR
        Wave wave = new Wave(false);
        wave.setRythmwave(2000);
        Coast coast = new Coast();
        Sea sea = new Sea();
        Interactions interactionCoasWave = new Interactions(wave,Action.HITTING, coast,wave.getRythmwave());
        Interactions interactionSeaWave = new Interactions(wave,Action.DISPERSING,sea,wave.getRythmwave());
        SeqActions cicleWaves = new SeqActions();
        cicleWaves.addInteraction(interactionCoasWave);
        cicleWaves.addInteraction(interactionSeaWave);
        cicleWaves.setCiclyc(true);
        inteManager.addSeqs(0, cicleWaves);
        // BIRD -SHIP - PLANE INVOCATION
        Bird bird = new Bird(false,Distance.FARAWAY);
        Ship ship = new Ship(false,Distance.CLOSE);
        Plane plane = new Plane(false,Distance.MEDIUM);
        Interactions interactionBirdSea = new Interactions(bird, Action.FLYING, sea, HALF_TIME);
        Interactions interactionShipSea = new Interactions(ship, Action.FLOATING, sea, HALF_TIME);
        Interactions interactionPlaneSea = new Interactions(plane, Action.FLYING, ship, HALF_TIME);
        SeqActions flyingthins = new SeqActions() ;
        flyingthins.addInteraction(interactionBirdSea);
        flyingthins.addInteraction(interactionShipSea);
        flyingthins.addInteraction(interactionPlaneSea);
        flyingthins.setCiclyc(false);
        inteManager.addSeqs(1, flyingthins);
        //SIGH INTERACTIONS
        Sight sight = new Sight(false);
        Interactions interactionBirdSight = new Interactions(sight, Action.OBSERVE, bird,  LONG_TIME);
        Interactions interactionShipSight = new Interactions(sight, Action.OBSERVE, ship, LONG_TIME);
        Interactions interactionPlaneSight = new Interactions(sight, Action.OBSERVE, plane, LONG_TIME);
        SeqActions sightInteractions = new SeqActions() ;
        sightInteractions.addInteraction(interactionBirdSight);
        sightInteractions.addInteraction(interactionShipSight);
        sightInteractions.addInteraction(interactionPlaneSight);
        sightInteractions.setCiclyc(false);
        inteManager.addSeqs(2, sightInteractions);
        // DEF PERSON
        Person person = new Person("Alik");
        person.setSight(sight);
        person.addAction(Action.DECIDESPIT,false);
        person.addAction(Action.SIT,false);
        person.addAction(Action.NOTICE,true);
        person.addAction(Action.CONCIDERE,true);
        person.addAction(Action.REALIZE,true);
        person.addAction(Action.CONCLUDE,true);

        //DECLARATION CURRENT ACTIONS AND  LOAD IN TIME MANAGER
        UpdateRealizer updateRealizer  = new UpdateRealizer();
        updateRealizer.addsEntity(person);
        // DEFIN UPDATE REALIZER
        ArrayList<Interactions> currentActions = inteManager.getCurrentInteractions();
        timeInteManager.addInteractions(currentActions, clock);

        while(true){

            currentActions = inteManager.getCurrentInteractions();
            timeInteManager.addInteractions(currentActions, clock);
            currentActions = timeInteManager.getFinishedClases(clock);
            inteManager.Update(currentActions);
            updateRealizer.Update();
            clock.Update();


        }

        
    }
    
}


