package com.fitness.sm.smartmuscle.helpers;

public class Muscles {
    public enum MuscleGroup
    {
        ARMS,
        LEGS,
        CHEST,
        BACK,
        ABS
    }

    public static MuscleGroup getMuscleGroup(int i){
        if (i == 0){
            return MuscleGroup.ARMS;
        } else if (i == 1){
            return MuscleGroup.LEGS;
        }else if (i == 2){
            return MuscleGroup.CHEST;
        }else if (i == 3) {
            return MuscleGroup.BACK;
        }else if (i == 4){
            return MuscleGroup.ABS;
        }else{
            return null;
        }
    }

    public static int toInt(MuscleGroup mg){
        if (mg==MuscleGroup.ARMS){
            return 0;
        } else if (mg==MuscleGroup.LEGS){
            return 1;
        } else if (mg==MuscleGroup.CHEST){
            return 2;
        } else if (mg==MuscleGroup.BACK){
            return 3;
        } else if (mg == MuscleGroup.ABS){
            return 4;
        } else{
            return -1;
        }
    }

    public static String toString(MuscleGroup mg){
        switch (mg){
            case ARMS:
                return "Arms";
            case LEGS:
                return "Legs";
            case BACK:
                return "Back";
            case CHEST:
                return "Chest";
            case ABS:
                return "Abs";
        }
        return null;
    }
}
