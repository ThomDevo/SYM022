package com.sym022.sym022.beans;


import org.primefaces.PrimeFaces;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@ManagedBean
@SessionScoped
public class I18nBean implements Serializable{
    private Locale locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    private String language;



    // Getters and Setters

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Locale getLocale() { return locale; }



    private void loadLocal(){
        locale = new Locale(this.language);
    }

    public void loadLanguagePage(){
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        viewRoot.setLocale(locale);
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(locale);
    }


    public void changeLanguage(String language, String idForm){
        //apply and load new language.
        this.setLanguage(language);
        this.loadLocal();
        this.loadLanguagePage();

        //call js function to submit form after execution.
        PrimeFaces.current().executeScript("submitLanguageForm(\""+idForm+"\")");
    }
}
