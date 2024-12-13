package Enviroment.Controllers;

import java.util.ArrayList;

import Enviroment.Entitys.UpdatableEntity;

public class UpdateRealizer {
    ArrayList<UpdatableEntity> updateEntityesList =  new  ArrayList<>();

    public void addsEntity(UpdatableEntity updateEntity){
        this.updateEntityesList.add(updateEntity);
    }

    public void Update(){

            for(UpdatableEntity updatableEntity: this.updateEntityesList){
                updatableEntity.Update();
            }
    }
}
