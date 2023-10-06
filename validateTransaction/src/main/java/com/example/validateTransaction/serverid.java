package com.example.validateTransaction;

public class serverid {
    private final int[] serverIds={10,20,30};

    public boolean isServer(int id){

        for(int i =0;i<serverIds.length;i++){
            if(serverIds[i]==id){
            return true;

        }

        }

        return false;


    }
}
