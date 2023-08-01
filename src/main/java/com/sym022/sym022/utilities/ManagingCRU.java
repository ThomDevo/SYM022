package com.sym022.sym022.utilities;

public class ManagingCRU <T>{
    //char for type of page generate (c,r,u,d).
    protected char modeSelected = 'r';
    public boolean isModeSelected(char modeAsk){ return (this.modeSelected == modeAsk); }
    public boolean isNotModeSelected(char modeAsk){
        return !isModeSelected(modeAsk);
    }



    //object product load from other page.
    protected T elementCrudSelected;
    public T getElementCrudSelected(){
        return elementCrudSelected;
    }
    public void setElementCrudSelected(T elementCrudSelected){ this.elementCrudSelected = elementCrudSelected; }
    public boolean elementCrudIsErrorLoad(){
        return (this.elementCrudSelected == null);
    }
    public boolean elementCrudIsSuccessLoad(){
        return !(this.elementCrudIsErrorLoad());
    }



    private FilterOfTable tableFilter;
    public void setTableFilter(FilterOfTable tableFilter){
        this.tableFilter=tableFilter;
    }
    public void resetFilterOfTableFilter(){
        if(tableFilter==null)
            return;
        tableFilter.resetFilter();
    }



    private boolean errorSubmitDB;
    public boolean getErrorSubmitDB(){ return this.errorSubmitDB; }
    public void setErrorSubmitDB(boolean errorSubmitDB){ this.errorSubmitDB = errorSubmitDB; }

}
