package com.sym022.sym022.beans;

import com.sym022.sym022.entities.QueryEntity;
import com.sym022.sym022.enums.*;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.DmService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.services.QueryService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class QueryBean extends FilterOfTable<QueryEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private QueryEntity query = new QueryEntity();
    private QueryService queryService = new QueryService();
    private String messageErrorInputQueryTextNd = "hidden";
    private String buttonSuccess = "false";
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
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the QueryText empty in front end
     * @return messageErrorInputQueryTextNd hidden or not and button create/update deactivate or not
     */
    public String testInputQueryTextNd(){
        String redirect = "null";
        if(query.getQueryText() == null || Objects.equals(query.getQueryText(), "")){
            this.messageErrorInputQueryTextNd = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorInputQueryTextNd = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to have I18n messages in Back-end
     * @param summary
     * @param detail
     */
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String submitFormAddQuery(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        QueryService queryService = new QueryService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        if(query.getQueryText() == null || Objects.equals(query.getQueryText(), "")){
            initErrorMessageFormQuery();
            this.messageErrorInputQueryTextNd = "";
            redirect = null;
            return redirect;
        }else{
            try{
                query.setEventByIdEvent(eventBean.getEvent());
                query.setQueryStatus(QueryStatus.OPENED);
                query.setQueryAnswer("");
                query.setQueryDatetime(new Date());
                query.setQueryClosed(false);
                query.setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setQueried(true);

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                queryService.addQuery(query, em);
                transaction.commit();
            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addQuery = bundle.getString("query");
            String add = bundle.getString("add");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addQuery+" "+add+" "+forThe+" "+query.getEventByIdEvent().getFormByIdForm().getFormLabel()+" "+forThe+" "+addSubject+" "+query.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            eventBean.initEvent();
            initFormQuery();
        }

        return redirect;
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

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }
}
