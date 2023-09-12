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
import java.util.List;
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
    private String messageErrorInputQueryAnswerNd = "hidden";
    private String buttonSuccess = "false";
    private List<QueryEntity> allQueries;
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;
    @Inject
    private DovBean dovBean;
    @Inject
    private AeBean aeBean;
    @Inject
    private CmBean cmBean;
    @Inject
    private DmBean dmBean;
    @Inject
    private IcBean icBean;
    @Inject
    private TeBean teBean;
    @Inject
    private VsBean vsBean;


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
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormDov(){
        String redirect = "/VIEW/home";
        initFormQuery();
        dovBean.initFormDov();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormCm(){
        String redirect = "/VIEW/home";
        initFormQuery();
        cmBean.initFormCm();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormAe(){
        String redirect = "/VIEW/home";
        initFormQuery();
        aeBean.initFormAe();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormDm(){
        String redirect = "/VIEW/home";
        initFormQuery();
        dmBean.initFormDm();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormIc(){
        String redirect = "/VIEW/home";
        initFormQuery();
        icBean.initFormIc();;
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormTe(){
        String redirect = "/VIEW/home";
        initFormQuery();
        teBean.initFormTe();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateFormVs(){
        String redirect = "/VIEW/home";
        initFormQuery();
        vsBean.initFromVs();
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
        this.messageErrorInputQueryAnswerNd = "hidden";
        this.buttonSuccess = "false";
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
     * Method to research all queries permitted
     */
    public void researchFilterAllQueries(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = queryService.findConsultQuery(connectionBean.getUser().getIdUser(), this.filter, em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to research all queries to answer permitted
     */
    public void researchFilterAllQueriesToAnswer(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = queryService.findOpenedDM(connectionBean.getUser().getIdUser(), this.filter, em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to research all queries answered permitted
     */
    public void researchFilterAllQueriesAnswered(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = queryService.findAnsweredDM(connectionBean.getUser().getIdUser(), this.filter, em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to research all queries to answer permitted
     */
    public void researchFilterAllQueriesToAnswerPerRole(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = queryService.findOpenedRole(connectionBean.getUser().getIdUser(), connectionBean.getUser().getRoleByIdRole().getIdRole(), this.filter, em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to research all queries answer permitted
     */
    public void researchFilterAllQueriesAnsweredPerRole(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = queryService.findAnsweredRole(connectionBean.getUser().getIdUser(), connectionBean.getUser().getRoleByIdRole().getIdRole(), this.filter, em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to add a query in the DB
     * @return a query
     */
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

    /**
     * Method to update a query in the DB
     * @return a query
     */
    public String submitFormUpdateQuery(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        QueryService queryService = new QueryService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        if(query.getQueryAnswer() == null || Objects.equals(query.getQueryAnswer(), "")){
            initErrorMessageFormQuery();
            this.messageErrorInputQueryAnswerNd = "";
            redirect = null;
            return redirect;
        }else{
            try{
                query.setEventByIdEvent(eventBean.getEvent());
                query.setQueryStatus(QueryStatus.ANSWERED);
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
                queryService.updateQuery(query, em);
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
            String update = bundle.getString("update");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addQuery+" "+update+" "+forThe+" "+query.getEventByIdEvent().getFormByIdForm().getFormLabel()+" "+forThe+" "+addSubject+" "+query.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            eventBean.initEvent();
            initFormQuery();
        }

        return redirect;
    }

    /**
     * Method to update a query in the DB
     * @return a query
     */
    public String submitFormCloseQuery(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        QueryService queryService = new QueryService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        int numberOfEvents;

            try{
                query.setEventByIdEvent(eventBean.getEvent());
                query.setQueryStatus(QueryStatus.CLOSED);
                query.setQueryDatetime(new Date());
                query.setQueryClosed(true);
                query.setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                this.allQueries=queryService.findByEvent(query.getEventByIdEvent().getIdEvent(),em);
                numberOfEvents = this.allQueries.size();

                if(numberOfEvents == 1){
                    eventBean.getEvent().setQueried(false);
                }

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                queryService.updateQuery(query, em);
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
            String addClosed = bundle.getString("closed");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addQuery+" "+addClosed+" "+forThe+" "+query.getEventByIdEvent().getFormByIdForm().getFormLabel()+" "+forThe+" "+addSubject+" "+query.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            eventBean.initEvent();
            initFormQuery();

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

    public List<QueryEntity> getAllQueries() {
        return allQueries;
    }

    public void setAllQueries(List<QueryEntity> allQueries) {
        this.allQueries = allQueries;
    }

    public String getMessageErrorInputQueryAnswerNd() {
        return messageErrorInputQueryAnswerNd;
    }

    public void setMessageErrorInputQueryAnswerNd(String messageErrorInputQueryAnswerNd) {
        this.messageErrorInputQueryAnswerNd = messageErrorInputQueryAnswerNd;
    }
}
