package Enviroment.Objects;

import java.util.Objects;

import Enviroment.Entitys.GroundEntity;

public class Coast implements GroundEntity{
    
    @Override
    public boolean equals(Object Coast) {
        if (this == Coast) return true; // Mismo objeto
        if (Coast == null || getClass() != Coast.getClass()) return false;
        return this.getClass().getSimpleName()  == ((GroundEntity) Coast).getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getClass().getSimpleName(), this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
