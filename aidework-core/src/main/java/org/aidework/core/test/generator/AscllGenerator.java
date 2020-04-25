package org.aidework.core.test.generator;

public class AscllGenerator {

    public static char ascllChar(){
        return (char) (33+Math.random()*(127-33+1));
    }

    public static char randomAlphabet(){
        return (char) ((65+Math.random()*(90-65+1))+(int)(Math.random()+0.5)*32);
    }

    public static char randomNormalChar(){
        if(Math.random()>0.5)
            return (char) ((65+Math.random()*(90-65+1))+(int)(Math.random()+0.5)*32);

        return (char)(48+Math.random()*(57-48+1));
    }
}
