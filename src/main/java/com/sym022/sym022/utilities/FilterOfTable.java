package com.sym022.sym022.utilities;

import org.primefaces.PrimeFaces;
import javax.validation.constraints.Pattern;
import java.util.List;

public class FilterOfTable <TEntity>{
    //entity filtered from db.
    protected List<TEntity> filterOfTable;
    public List<TEntity> getEntityFiltered(){
        return this.filterOfTable;
    }
    public void setEntityFiltered(List<TEntity> filterOfTable){
        this.filterOfTable = filterOfTable;
    }



    protected String order = "id";

    protected boolean orderAsc = false;

    public void editOrderTable(String order){

        if(this.order.equals(order)){
            orderAsc = !orderAsc;
        }else{
            orderAsc = true;
            this.order = order;
        }

    }

    public String getOrderIcon(String order){
        if(!this.order.equals(order))
            return "pi pi-circle-off";
        return ((orderAsc)? "pi pi-chevron-circle-down": "pi pi-chevron-circle-up");
    }




    @Pattern(regexp = "^[a-zA-Z0-9 çéâêîôûàèìòùëïü.]{0,60}$")
    protected String filter = "";

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter(){
        return this.filter;
    }



    private int idRedirection;
    private char modeRedirection = 'r';
    private boolean newRedirect = false;

    public int getIdRedirection(){
        return this.idRedirection;
    }
    public char getModeRedirection() { return this.modeRedirection; }
    public boolean getNewRedirect() {
        boolean newRedirect = this.newRedirect;
        this.newRedirect = false;
        return newRedirect;
    }

    //redirect page with sending id of row click.
    public String redirectPageWithId(String url, int id){
        this.idRedirection = id;
        this.newRedirect = true;
        return url;
    }


    //redirect page with sending id of row click AND mode (c,r,u,d).
    public String redirectPageWithId(String url, int id, char mode){
        this.modeRedirection = mode;
        return this.redirectPageWithId(url, id);
    }



    //call JS for apply class research word in a table list.
    public void applyResearchWordClass(){
        PrimeFaces.current().executeScript("applyClassWordResearch(\""+this.filter+"\")");
    }


    //reset filter (call before an insert db, for apply last insert on the top of list).
    public void resetFilter(){
        this.order = "id";
        this.orderAsc = false;
        this.filter = "";
    }


}
