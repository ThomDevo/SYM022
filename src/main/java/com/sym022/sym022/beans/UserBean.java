package com.sym022.sym022.beans;

import com.sym022.sym022.entities.SubjectEntity;
import com.sym022.sym022.entities.UserEntity;
import com.sym022.sym022.services.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Named
@ManagedBean
@SessionScoped
public class UserBean extends FilterOfTable<UserEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private UserEntity user = new UserEntity();
    private UserService userService = new UserService();
    private List<UserEntity> allUsers;
    private List<UserEntity> allUsersEmptySites;
    private String messageErrorUserName = "hidden";
    private String messageErrorlastName = "hidden";
    private String messageErrorfirstName = "hidden";
    private String messageErrorUserNameTest = "hidden";
    private String messageErrorUserMail = "hidden";
    private int idSubject;
    private String buttonSuccess = "false";
    @Inject
    private EventBean eventBean;
    @Inject
    private VisitBean visiteBean;
    @Inject
    private FormBean formBean;
    @Inject
    private IcBean icBean;
    @Inject
    private DovBean dovBean;
    @Inject
    private SubjectBean subjectBean;

    /*--- Methods ---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormUser();
        return redirect;
    }


    /**
     * Method to reset the form to add or update a user
     */
    public void initFormUser(){
        this.user.setUsername("");
        this.user.setPassword("");
        this.user.setLastName("");
        this.user.setFirstName("");
        this.user.setMail("");
        this.user.setStatus(true);
        initErrorMessageUser();
    }

    /**
     * Method to reset all the errors messages in the form for add or update a user
     */
    public void initErrorMessageUser(){
        this.messageErrorUserName = "hidden";
        this.messageErrorlastName = "hidden";
        this.messageErrorfirstName = "hidden";
        this.messageErrorUserNameTest = "hidden";
        this.messageErrorUserMail = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the LastName in front end
     * @return messageErrorlastName hidden or not and button create/update deactivate or not
     */
    public String testLastName(){
        String redirect = "null";

        if(!Pattern.matches("^\\D{2,200}$", user.getLastName())){
            this.messageErrorlastName = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorlastName = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the FirstName in front end
     * @return messageErrorfirstName hidden or not and button create/update deactivate or not
     */
    public String testFirstName(){
        String redirect = "null";

        if(!Pattern.matches("^\\D{2,200}$", user.getFirstName())){
            this.messageErrorfirstName = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorfirstName = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the UsertName in front end
     * @return messageErrorUserNameTest hidden or not and button create/update deactivate or not
     */
    public String testUserName(){
        String redirect = "null";

        if(!Pattern.matches("^[A-Za-z ',\\-.-éèçàâêîûôù]{2,255}$", user.getUsername())){
            this.messageErrorUserNameTest = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorUserNameTest = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the UsertMail in front end
     * @return messageErrorUserMail hidden or not and button create/update deactivate or not
     */
    public String testMail(){
        String redirect = "null";

        if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", user.getMail())){
            this.messageErrorUserMail = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorUserMail = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to find all the users who have no sites in the select menu
     */
    public void initAllUserAllEmptySites(){
        EntityManager em = EMF.getEM();
        UserService userService = new UserService();
        try{
            this.allUsersEmptySites = userService.findUserAllEmptySites(em);
        }catch(Exception e){
            this.allUsersEmptySites = new ArrayList<>();
        }finally{
            em.close();
        }
    }


    /**
     * Method to filter the users on the lastname, the firstname, the mail and the role
     */
    public void researchFilterUser(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = userService.findUserByFilterAndOrderAsc(this.filter,em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            em.close();
        }
    }

    public void getFilterVisit(){
        EntityManager em = EMF.getEM();
        EventService eventService = new EventService();
        VisitService visitService = new VisitService();
        IcService icService = new IcService();
        DovService dovService = new DovService();
        this.idSubject = this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject();
        //ProcessUtils.debug(""+this.idUser);

        try{
            eventBean.setAllEvents(eventService.findEventAllExceptAeCm(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(), em));
            //ProcessUtils.debug("List récup et update"+ licensesService.findLicenseNotOwnByUser(this.licenseUserBean.getLicenseUser().getUsersByIdUser().getIdUser(), em).size());
        }catch(Exception e){
            ProcessUtils.debug("catch methodfindEventAllExceptAeCmr " + e);

        }

        try{
            icBean.setIcAll(icService.findIcEligibleNo(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(), em));
        }catch(Exception e){
            ProcessUtils.debug("catch methodfindEventAllExceptAeCmr " + e);
        }

        if(eventBean.getAllEvents().size() == 7 || dovService.findDovNDMois1(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em) && !eventService.findEventScreening(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em)){
            try{
                visiteBean.setAllVisit(visitService.findAeCM(em));
            }catch(Exception e){
                ProcessUtils.debug("catch methodFindAeCM " + e);
            }
        }else if(icBean.getIcAll().size() != 0){
            try{
                visiteBean.setAllVisit(visitService.findAeCM(em));
            }catch(Exception e){
                ProcessUtils.debug("catch methodFindAeCM " + e);
            }
        }else if(!icService.findIcEligibleYes(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em)){
            try{
                visiteBean.setAllVisit(visitService.findVisitExceptMois1(em));
                ProcessUtils.debug("je suis bien là1");
            }catch(Exception e){
                ProcessUtils.debug("catch methodFindVisitExceptMois1 " + e);
            }
        }else if(!eventService.isEventSubjectExist(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(), em)){
            try{
                visiteBean.setAllVisit(visitService.findVisitExceptMois1(em));
                ProcessUtils.debug("je suis bien là2");
            }catch(Exception e){
                ProcessUtils.debug("catch methodFindVisitExceptMois1 " + e);
            }
        }else if(eventService.findEventMois1(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em) && eventService.findEventScreening(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em) && !dovService.findDovNDMois1(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em)){
            try{
                visiteBean.setAllVisit(visitService.findVisitAll(em));
                ProcessUtils.debug("je suis bien là3");
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindVisitAll" + e);
            }
        }else if(eventService.findEventMois1(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em) && !eventService.findEventScreening(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em)){
            try{
                visiteBean.setAllVisit(visitService.findVisitExceptScreening(em));
                ProcessUtils.debug("je suis bien là4");
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindVisitScreening " + e);
            }
        }else if((!eventService.findEventMois1(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em) && eventService.findEventScreening(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em))
                || (dovService.findDovNDMois1(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em) && eventService.findEventScreening(this.eventBean.getEvent().getSubjectByIdSubject().getIdSubject(),em))){
            try{
                visiteBean.setAllVisit(visitService.findVisitExceptMois1(em));
                ProcessUtils.debug("je suis bien là5");
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindVisitExceptMois1 " + e);
            }
        }else {
            try {
                visiteBean.setAllVisit(visitService.findVisitAll(em));
                ProcessUtils.debug("je suis bien là6");
            } catch (Exception e) {
                ProcessUtils.debug("catch methodFindVisitExceptAECM " + e);
            }
        }
    }

    public void getFilterForm(){
        EntityManager em = EMF.getEM();
        EventService eventService = new EventService();
        FormService formService = new FormService();
        SubjectService subjectService = new SubjectService();
        DovService dovService = new DovService();
        //ProcessUtils.debug(""+this.idUser);


        if(this.eventBean.getEvent().getVisitByIdVisit().getVisitNum() == 80){
            try{
                formBean.setAllForm(formService.findFormAe(em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormAe " + e);
            }
        }else if(this.eventBean.getEvent().getVisitByIdVisit().getVisitNum() == 90){
            try{
                formBean.setAllForm(formService.findFormCm(em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormCm " + e);
            }
        }else if(this.eventBean.getEvent().getVisitByIdVisit().getVisitNum() == 10 && !eventService.isEventSubjectExist(this.idSubject, em)){
            try{
                formBean.setAllForm(formService.findFormDov(em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormDov " + e);
            }
        }else if(!eventService.isIcSubjectExist(this.idSubject, em)){
            try{
                formBean.setAllForm(formService.findFormIc(em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormic " + e);
            }
        }else if(this.eventBean.getEvent().getVisitByIdVisit().getVisitNum() == 10){
            try{
                formBean.setAllForm(formService.findFormScreeningND(this.idSubject,em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormScreeningND " + e);
            }
        }else if(this.eventBean.getEvent().getVisitByIdVisit().getVisitNum() == 20 && !eventService.isMois1SubjectExist(this.idSubject, em)){
            try{
                formBean.setAllForm(formService.findFormDov(em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormMoisNotStarted " + e);
            }
        }else if(this.eventBean.getEvent().getVisitByIdVisit().getVisitNum() == 20){
            try{
                formBean.setAllForm(formService.findFormMois1ND(this.idSubject,em));
            }catch(Exception e) {
                ProcessUtils.debug("catch methodFindFormMois1ND " + e);
            }
        }
    }

    /**
     * Method to add a new user in the DB
     * @return a user
     */
    public String submitFormAddUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        UserService userService = new UserService();

        try{
            user.setFirstName(user.getFirstName().substring(0,1).toUpperCase()+user.getFirstName().substring(1).toLowerCase());
            user.setLastName(user.getLastName().substring(0,1).toUpperCase()+user.getLastName().substring(1).toLowerCase());
            user.setPassword(ProcessUtils.cryptPassword(user.getPassword()));
            user.setStatus(true);

            transaction.begin();
            initErrorMessageUser();
            if(userService.isUserExist(user.getUsername(),em)){
                this.messageErrorUserName="";
                redirect = "null" ;
                return redirect;
            }
            userService.addUser(user,em);
            transaction.commit();
            confirmAddUser();
            initFormUser();

        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the addUser method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    /**
     * Method to update a user in the DB
     * @return a user
     */
    public String submitFormUpdateUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        UserService userService = new UserService();

        try{
            user.setFirstName(user.getFirstName().substring(0,1).toUpperCase()+user.getFirstName().substring(1).toLowerCase());
            user.setLastName(user.getLastName().substring(0,1).toUpperCase()+user.getLastName().substring(1).toLowerCase());

            transaction.begin();
            initErrorMessageUser();
            userService.updateUser(user,em);
            transaction.commit();
            confirmUpdateUser();
            initFormUser();

        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the updateUser method: "+ e);

        }finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
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

    /**
     * Method for getting a popup confirming that the user has been added
     */
    public void confirmAddUser(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addUser = bundle.getString("user");
        String add = bundle.getString("add");

        addMessage(addUser+" "+user.getLastName()+" "+user.getFirstName()+" "+add,"Confirmation");
    }

    /**
     * Method for getting a popup confirming that the user has been updated
     */
    public void confirmUpdateUser(){
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String updateUser = bundle.getString("user");
        String update = bundle.getString("update");

        addMessage(updateUser+" "+user.getLastName()+" "+user.getFirstName()+" "+update,"Confirmation");
    }

    /*--- Getters and Setters ---*/

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getMessageErrorUserName() {
        return messageErrorUserName;
    }

    public void setMessageErrorUserName(String messageErrorUserName) {
        this.messageErrorUserName = messageErrorUserName;
    }

    public List<UserEntity> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<UserEntity> allUsers) {
        this.allUsers = allUsers;
    }

    public List<UserEntity> getAllUsersEmptySites() {
        return allUsersEmptySites;
    }

    public void setAllUsersEmptySites(List<UserEntity> allUsersEmptySites) {
        this.allUsersEmptySites = allUsersEmptySites;
    }

    public String getMessageErrorlastName() {
        return messageErrorlastName;
    }

    public void setMessageErrorlastName(String messageErrorlastName) {
        this.messageErrorlastName = messageErrorlastName;
    }

    public String getMessageErrorfirstName() {
        return messageErrorfirstName;
    }

    public void setMessageErrorfirstName(String messageErrorfirstName) {
        this.messageErrorfirstName = messageErrorfirstName;
    }

    public String getMessageErrorUserNameTest() {
        return messageErrorUserNameTest;
    }

    public void setMessageErrorUserNameTest(String messageErrorUserNameTest) {
        this.messageErrorUserNameTest = messageErrorUserNameTest;
    }

    public String getMessageErrorUserMail() {
        return messageErrorUserMail;
    }

    public void setMessageErrorUserMail(String messageErrorUserMail) {
        this.messageErrorUserMail = messageErrorUserMail;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }

    public VisitBean getVisiteBean() {
        return visiteBean;
    }

    public void setVisiteBean(VisitBean visiteBean) {
        this.visiteBean = visiteBean;
    }

    public IcBean getIcBean() {
        return icBean;
    }

    public void setIcBean(IcBean icBean) {
        this.icBean = icBean;
    }
}
