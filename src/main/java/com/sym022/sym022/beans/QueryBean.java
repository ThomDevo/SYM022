package com.sym022.sym022.beans;

import com.sym022.sym022.entities.QueryEntity;
import com.sym022.sym022.enums.*;
import com.sym022.sym022.services.QueryService;
import com.sym022.sym022.utilities.FilterOfTable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Named
@ManagedBean
@SessionScoped
public class QueryBean extends FilterOfTable<QueryEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private QueryEntity query = new QueryEntity();
    private QueryService queryService = new QueryService();
    private String messageErrorInputQueryTextNd = "hidden";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;


    /*---Method---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormQuery();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to reset the form to add or update a QUERY
     */
    public void initFormQuery(){
        this.query.setQueryText("");
        this.query.setQueryAnswer("");
        this.query.setQueryClosed(false);
        this.query.setQueryStatus(QueryStatus.OPENED);
        initErrorMessageFormQuery();
    }

    /**
     * Method to reset all Error Messages in the form to add or update  a QUERY
     */
    public void initErrorMessageFormQuery(){
        this.messageErrorInputQueryTextNd = "hidden";
    }

    /*--- Getters and Setters ---*/

    public QueryEntity getQuery() {
        return query;
    }

    public void setQuery(QueryEntity query) {
        this.query = query;
    }

    public QueryService getQueryService() {
        return queryService;
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    public AuditTrailBean getAuditTrailBean() {
        return auditTrailBean;
    }

    public void setAuditTrailBean(AuditTrailBean auditTrailBean) {
        this.auditTrailBean = auditTrailBean;
    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public String getMessageErrorInputQueryTextNd() {
        return messageErrorInputQueryTextNd;
    }

    public void setMessageErrorInputQueryTextNd(String messageErrorInputQueryTextNd) {
        this.messageErrorInputQueryTextNd = messageErrorInputQueryTextNd;
    }
}
