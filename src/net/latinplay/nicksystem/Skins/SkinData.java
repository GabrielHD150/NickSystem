package net.latinplay.nicksystem.Skins;

import java.io.Serializable;

public class SkinData implements Serializable {

    private String name;
    private String value;
    private String signature;

    public SkinData(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "SkinData{name=" + name + ",value" + value + ",signature" + signature + "}";
    }

    public String getSignature() {
        return this.signature;
    }
    
    public void setSignature(String signa) {
        this.signature = signa;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
