package com.sym022.sym022.beans;

import com.itextpdf.text.pdf.TextField;
import com.sun.org.apache.xpath.internal.operations.Div;
import com.sym022.sym022.entities.AeEntity;
import com.sym022.sym022.enums.*;
import com.sym022.sym022.mail.Mail;
import com.sym022.sym022.mail.MailSender;
import com.sym022.sym022.services.AeService;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.soap.Text;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class AeBean extends FilterOfTable<AeEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private AeEntity ae = new AeEntity();
    private AeService aeService = new AeService();
    private String messageErrorVisitDate = "hidden";
    private String messageErrorVisitNdFalse = "hidden";
    private String messageErrorAeendatBeforAestdat = "hidden";
    private String messageErrorAeendatPres = "hidden";
    private String messageErrorAeendatMis = "hidden";
    private String messageErrorAeotherspMis = "hidden";
    private String messageErrorAemedimspMis = "hidden";
    private String messageErrorVisitDateAeendat = "hidden";
    private String messageErrorAeterm = "hidden";
    private String messageErrorAeser = "hidden";
    private String messageErrorAeFatal = "hidden";
    private String messageErrormessageErrorAeendatMis = "hidden";
    private String buttonSuccess = "false";
    LocalDate now = LocalDate.now();
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;
    ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    /*---Method---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormAe();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateForm(){
        String redirect = "/VIEW/home";
        initFormAe();
        return redirect;
    }

    /**
     * Method to reset the form to add or update an AE
     */
    public void initFormAe(){
        Date now = new Date();
        this.ae.setAeterm("");
        this.ae.setAetermc("");
        this.ae.setAestdat(null);
        this.ae.setAeout(Aeout.UNKNOWN);
        this.ae.setAeendat(null);
        this.ae.setAetoxgd(Aetoxgd.NA);
        this.ae.setAesev(Aesev.NA);
        this.ae.setAerel(Aerel.NA);
        this.ae.setAeacn(Aeacn.NOT_APPLICABLE);
        this.ae.setAecm(false);
        this.ae.setAeproc(false);
        this.ae.setAeother(false);
        this.ae.setAeothersp("");
        this.ae.setAeser(false);
        this.ae.setAedeath(false);
        this.ae.setAelife(false);
        this.ae.setAehosp(false);
        this.ae.setAedisab(false);
        this.ae.setAecong(false);
        this.ae.setAemedim(false);
        this.ae.setAemedimsp("");
        initErrorMessageFormAe();
    }

    /**
     * Method to reset all Error Messages in the form to add or update an AE
     */
    public void initErrorMessageFormAe(){
        this.messageErrorAeterm = "hidden";
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitDateAeendat = "hidden";
        this.messageErrorAeendatBeforAestdat = "hidden";
        this.messageErrorAeotherspMis = "hidden";
        this.messageErrorAeser = "hidden";
        this.messageErrorAeFatal = "hidden";
        this.messageErrorAemedimspMis = "hidden";
        this.messageErrormessageErrorAeendatMis = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDateNull(){
        String redirect = "null";
        if(ae.getAestdat() == null){
            this.messageErrorVisitNdFalse = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVisitNdFalse = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the aeendat in front end
     * @return messageErrorAeendatMis hidden or not and button create/update deactivate or not
     */
    public String testDateaeendatNull(){
        String redirect = "null";
        if(ae.getAeendat() == null && (ae.getAeout() == Aeout.RECOVERED_RESOLVED || ae.getAeout() == Aeout.RECOVERED_RESOLVED_WITH_SEQUELAE || ae.getAeout() == Aeout.FATAL)){
            this.messageErrorAeendatMis = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeendatMis = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the aeout in front end
     * @return messageErrorAeFatal hidden or not and button create/update deactivate or not
     */
    public String testAeoutFatal(){
        String redirect = "null";
        if(ae.getAeout() == Aeout.FATAL && !ae.isAedeath() || ae.getAeout() != Aeout.FATAL && ae.isAedeath()){
            this.messageErrorAeFatal = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeFatal = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDate(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(ae.getAestdat() == null){
            testDateNull();
        }else{
            String aeDate = simpleDateFormat.format(ae.getAestdat());
            int resultAeDate = aeDate.compareTo(String.valueOf(now));
            if(resultAeDate > 0){
                this.messageErrorVisitDate = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDate = "hidden";
                this.buttonSuccess = "false";
            }
        }
        return redirect;
    }

    /**
     * Method to test the aeterm empty in front end
     * @return messageErrorAeterm hidden or not and button create/update deactivate or not
     */
    public String testAeterm(){
        String redirect = "null";
        if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
            this.messageErrorAeterm = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeterm = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDatetestDateAeout(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(ae.getAeendat() == null && (ae.getAeout() == Aeout.RECOVERED_RESOLVED || ae.getAeout() == Aeout.RECOVERED_RESOLVED_WITH_SEQUELAE || ae.getAeout() == Aeout.FATAL)){
            testDateaeendatNull();
        }else{
            String aeoutDate = simpleDateFormat.format(ae.getAeendat());
            int resultAeoutDate = aeoutDate.compareTo(String.valueOf(now));
            if(resultAeoutDate > 0){
                this.messageErrorVisitDateAeendat = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDateAeendat = "hidden";
                this.buttonSuccess = "false";
            }
        }

        return redirect;
    }

    /**
     * Method to test the AeendatBeforAestdat in front end
     * @return messageErrorAeendatBeforAestdat hidden or not and button create/update deactivate or not
     */
    public String testAeendatBeforAestdat(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        if(ae.getAestdat() == null){
            testDateNull();
        }else if(ae.getAeendat() == null && (ae.getAeout() == Aeout.RECOVERED_RESOLVED || ae.getAeout() == Aeout.RECOVERED_RESOLVED_WITH_SEQUELAE || ae.getAeout() == Aeout.FATAL)){
            testDateaeendatNull();
        }else{
            String aeendatDate = simpleDateFormat.format(ae.getAeendat());
            String aeestdatDate = simpleDateFormat.format(ae.getAestdat());
            int resultAendatDate = aeendatDate.compareTo(aeestdatDate);
            if(resultAendatDate < 0){
                this.messageErrorAeendatBeforAestdat = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorAeendatBeforAestdat = "hidden";
                this.buttonSuccess = "false";
            }
        }

        return redirect;
    }

    /**
     * Method to test the AemedimspMis empty in front end
     * @return messageErrorAemedimspMis hidden or not and button create/update deactivate or not
     */
    public String testAemedimspMis(){
        String redirect = "null";
        if(ae.isAemedim() && (ae.getAemedimsp() == null || Objects.equals(ae.getAemedimsp(), ""))){
            this.messageErrorAemedimspMis = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAemedimspMis = "hidden";
            this.buttonSuccess = "false";
        }

        return redirect;
    }

    /**
     * Method to test the AeotherspMis empty in front end
     * @return messageErrorAeotherspMis hidden or not and button create/update deactivate or not
     */
    public String testAeotherspMis(){
        String redirect = "null";
        if(ae.isAeother() && (ae.getAeothersp() == null || Objects.equals(ae.getAeothersp(), ""))){
            this.messageErrorAeotherspMis = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorAeotherspMis = "hidden";
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

    /**
     * Method to find an AE based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateAe";
        EntityManager em = EMF.getEM();
        try{
            ae = aeService.findAeByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to find an AE based on the IdEvent
     * @param idEvent
     */
    public String findEventQuery(int idEvent){
        String redirect = "/VIEW/consultQueryAe";
        EntityManager em = EMF.getEM();
        try{
            ae = aeService.findAeByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to add an AE in the DB
     * @return an AE
     */
    public String submitFormAddAe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        AeService aeService = new AeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String dateEventPattern = "ddMMyyyy-HHmmss";
        String isoDatePattern = "yyyy-MM-dd";
        String isoTimePattern = "HH:mm";
        Mail email = new Mail();
        Document doc = new Document();
        String filename;
        String source;

        if(ae.getAestdat() == null && ae.getAeendat() == null){
            initErrorMessageFormAe();
            this.messageErrorVisitNdFalse = "";
            redirect = "null";
        }else if(ae.getAestdat() != null && ae.getAeendat() == null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
            String aeStDate = simpleDateFormat.format(ae.getAestdat());
            int resultAeDateDate = aeStDate.compareTo(String.valueOf(now));
            if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
                initErrorMessageFormAe();
                this.messageErrorAeterm = "";
                redirect = "null";
                return redirect;
            }else if(resultAeDateDate > 0){
                initErrorMessageFormAe();
                this.messageErrorVisitDate = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAeother() && (Objects.equals(ae.getAeothersp(), "") || ae.getAeothersp() == null)){
                initErrorMessageFormAe();
                this.messageErrorAeotherspMis = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAemedim() && (Objects.equals(ae.getAemedimsp(), "") || ae.getAemedimsp() == null)){
                initErrorMessageFormAe();
                this.messageErrorAemedimspMis = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAeser() && !ae.isAedeath() && !ae.isAelife() && !ae.isAehosp() && !ae.isAedisab() && !ae.isAecong() && !ae.isAemedim()){
                initErrorMessageFormAe();
                this.messageErrorAeser = "";
                redirect = "null";
                return redirect;
            }else if(ae.getAeout() == Aeout.FATAL && !ae.isAedeath() || ae.getAeout() != Aeout.FATAL && ae.isAedeath()  ){
                initErrorMessageFormAe();
                this.messageErrorAeFatal = "";
                redirect = "null";
                return redirect;
            }else{
                try{
                    ae.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    ae.setAetermc("");

                    if(!ae.isAeother()){
                        this.ae.setAeothersp("");
                    }

                    if(!ae.isAemedim()){
                        this.ae.setAemedimsp("");
                    }

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    aeService.addAe(ae, em);
                    transaction.commit();
                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                if(ae.isAeser()){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat(isoDatePattern);
                    String dateAestdat = simpleDateFormatDate.format(ae.getAestdat());
                    //String dateAeendat = simpleDateFormatDate.format(ae.getAeendat());
                    LocalDateTime nowMail = LocalDateTime.now();
                    String newSAE = bundle.getString("newSAE");
                    String dear = bundle.getString("dear");
                    String subject = bundle.getString("subjectPage.subjectNum");
                    String aeterm = bundle.getString("aePage.aeterm");
                    String aestdat = bundle.getString("aePage.aestdat");
                    String aeout = bundle.getString("aePage.aeout");
                    String aeendat = bundle.getString("aePage.aeendat");
                    String aetoxgd = bundle.getString("aePage.aetoxgd");
                    String aesev = bundle.getString("aePage.aesev");
                    String aerel = bundle.getString("aePage.aerel");
                    String aedeath = bundle.getString("aePage.aedeath");
                    String aelife = bundle.getString("aePage.aelife");
                    String aehosp = bundle.getString("aePage.aehosp");
                    String aedisab = bundle.getString("aePage.aedisab");
                    String aecong = bundle.getString("aePage.aecong");
                    String aemedimsp = bundle.getString("aePage.aemedimsp");
                    String team = bundle.getString("team");
                    String reminder = bundle.getString("reminder");
                    filename = "SYM-022_SAE_"+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+ "_"+now +dtf.format(nowMail)+ ".pdf";
                    //source = "C:\\Users\\devog\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                    source = "C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                    try{
                        PdfWriter.getInstance(doc, new FileOutputStream(source));
                        doc.open();

                        Paragraph p = new Paragraph();

                        Image image = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\SAE_images.png");
                        image.scaleAbsolute(500, 150);
                        doc.add (image);
                        p.add("\n"+dear+"\n\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                                "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat+"\n"+ aeout+" : "+ae.getAeout().getAeout()+
                                /*"\n"+ aeendat+" : "+dateAeendat+*/"\n"+ aetoxgd+" : "+ae.getAetoxgd().getAetoxgd()+
                                "\n"+ aesev+" : "+ae.getAesev().getAesev()+"\n"+ aerel+" : "+ae.getAerel().getAerel()+
                                "\n"+ aedeath+" : "+ae.isAedeath()+"\n"+ aelife+" : "+ae.isAelife()+
                                "\n"+ aehosp+" : "+ae.isAehosp()+"\n"+ aedisab+" : "+ae.isAedisab()+
                                "\n"+ aecong+" : "+ae.isAecong()+"\n"+ aemedimsp+" : "+ae.getAemedimsp()
                        );
                        doc.add(p);
                        Font f1 = new Font();
                        f1.setColor(BaseColor.RED);
                        f1.setStyle(Font.BOLD);
                        doc.add(new Paragraph("\n"+ reminder, f1));
                        doc.add(new Paragraph("\n"+ team +"\n"));
                        Image image1 = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\logo_Sym022.png");
                        image.scaleAbsolute(65, 41);
                        doc.add (image1);
                        doc.close();

                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }

                    email.setFrom("SYM022_Safety@outlook.com");
                    email.setMsgBody(dear+"\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                            "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat);
                    email.setSubject("SYM-022: "+newSAE + "_" +ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum());
                    email.setNick("SYM022");
                    email.setReplyTo("SYM022_Safety@outlook.com");
                    //email.setListTo(listEMail);
                    email.getListTo().add("thomas.devogelaere@promsocatc.net");
                    email.getListTo().add("daphne.debetancourt@promsocatc.net");
                    email.setEncodeUTF8(true);
                    email.setFilename(filename);
                    email.setSource(source);
                    try {
                        MailSender.sendMail(email);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                String addAe = bundle.getString("ae");
                String add = bundle.getString("add");
                String forThe = bundle.getString("for");
                String addSubject = bundle.getString("subject");

                addMessage(addAe+" "+add+" "+forThe+" "+addSubject+" "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                initFormAe();
            }
        }else{
            if(ae.getAestdat() != null && ae.getAeendat() != null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
                String aeStDate = simpleDateFormat.format(ae.getAestdat());
                int resultAeDateDate = aeStDate.compareTo(String.valueOf(now));
                String aeendateDate = simpleDateFormat.format(ae.getAeendat());
                int resultAendateDate = aeendateDate.compareTo(aeStDate);
                String aeoutDate = simpleDateFormat.format(ae.getAeendat());
                int resultAeoutDate = aeoutDate.compareTo(String.valueOf(now));
                if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
                    initErrorMessageFormAe();
                    this.messageErrorAeterm = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAeDateDate > 0){
                    initErrorMessageFormAe();
                    this.messageErrorVisitDate = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAeoutDate > 0){
                    initErrorMessageFormAe();
                    this.messageErrorVisitDateAeendat = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAendateDate < 0){
                    initErrorMessageFormAe();
                    this.messageErrorAeendatBeforAestdat = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAeother() && (Objects.equals(ae.getAeothersp(), "") || ae.getAeothersp() == null)){
                    initErrorMessageFormAe();
                    this.messageErrorAeotherspMis = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAemedim() && (Objects.equals(ae.getAemedimsp(), "") || ae.getAemedimsp() == null)){
                    initErrorMessageFormAe();
                    this.messageErrorAemedimspMis = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAeser() && !ae.isAedeath() && !ae.isAelife() && !ae.isAehosp() && !ae.isAedisab() && !ae.isAecong() && !ae.isAemedim()){
                    initErrorMessageFormAe();
                    this.messageErrorAeser = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.getAeout() == Aeout.FATAL && !ae.isAedeath() || ae.getAeout() != Aeout.FATAL && ae.isAedeath()){
                    initErrorMessageFormAe();
                    this.messageErrorAeFatal = "";
                    redirect = "null";
                    return redirect;
                }else{
                    try{
                        ae.setEventByIdEvent(eventBean.getEvent());
                        auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                        auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                        auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                        eventBean.getEvent().setCompleted(true);
                        ae.setAetermc("");

                        if(!ae.isAeother()){
                            this.ae.setAeothersp("");
                        }

                        if(!ae.isAemedim()){
                            this.ae.setAemedimsp("");
                        }

                        transaction.begin();
                        eventService.updateEvent(eventBean.getEvent(),em);
                        auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                        aeService.addAe(ae, em);
                        transaction.commit();
                    }catch(Exception e){
                        ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

                    }finally {
                        if(transaction.isActive()){
                            transaction.rollback();
                        }
                        em.close();
                    }

                    if(ae.isAeser()){
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat(isoDatePattern);
                        String dateAestdat = simpleDateFormatDate.format(ae.getAestdat());
                        String dateAeendat = simpleDateFormatDate.format(ae.getAeendat());
                        LocalDateTime nowMail = LocalDateTime.now();
                        String newSAE = bundle.getString("newSAE");
                        String dear = bundle.getString("dear");
                        String subject = bundle.getString("subjectPage.subjectNum");
                        String aeterm = bundle.getString("aePage.aeterm");
                        String aestdat = bundle.getString("aePage.aestdat");
                        String aeout = bundle.getString("aePage.aeout");
                        String aeendat = bundle.getString("aePage.aeendat");
                        String aetoxgd = bundle.getString("aePage.aetoxgd");
                        String aesev = bundle.getString("aePage.aesev");
                        String aerel = bundle.getString("aePage.aerel");
                        String aedeath = bundle.getString("aePage.aedeath");
                        String aelife = bundle.getString("aePage.aelife");
                        String aehosp = bundle.getString("aePage.aehosp");
                        String aedisab = bundle.getString("aePage.aedisab");
                        String aecong = bundle.getString("aePage.aecong");
                        String aemedimsp = bundle.getString("aePage.aemedimsp");
                        String team = bundle.getString("team");
                        String reminder = bundle.getString("reminder");
                        filename = "SYM-022_SAE_"+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+ "_"+now +dtf.format(nowMail)+ ".pdf";
                        //source = "C:\\Users\\devog\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                        source = "C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                        try{
                            PdfWriter.getInstance(doc, new FileOutputStream(source));
                            doc.open();

                            Paragraph p = new Paragraph();

                            Image image = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\SAE_images.png");
                            image.scaleAbsolute(500, 150);
                            doc.add (image);
                            p.add("\n"+dear+"\n\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                                    "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat+"\n"+ aeout+" : "+ae.getAeout().getAeout()+
                                    "\n"+ aeendat+" : "+dateAeendat+"\n"+ aetoxgd+" : "+ae.getAetoxgd().getAetoxgd()+
                                    "\n"+ aesev+" : "+ae.getAesev().getAesev()+"\n"+ aerel+" : "+ae.getAerel().getAerel()+
                                    "\n"+ aedeath+" : "+ae.isAedeath()+"\n"+ aelife+" : "+ae.isAelife()+
                                    "\n"+ aehosp+" : "+ae.isAehosp()+"\n"+ aedisab+" : "+ae.isAedisab()+
                                    "\n"+ aecong+" : "+ae.isAecong()+"\n"+ aemedimsp+" : "+ae.getAemedimsp()
                            );
                            doc.add(p);
                            Font f1 = new Font();
                            f1.setColor(BaseColor.RED);
                            f1.setStyle(Font.BOLD);
                            doc.add(new Paragraph("\n"+ reminder, f1));
                            doc.add(new Paragraph("\n"+ team +"\n"));
                            Image image1 = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\logo_Sym022.png");
                            image.scaleAbsolute(65, 41);
                            doc.add (image1);
                            doc.close();

                        } catch (DocumentException | IOException e) {
                            e.printStackTrace();
                        }

                        email.setFrom("SYM022_Safety@outlook.com");
                        email.setMsgBody(dear+"\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                                "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat);
                        email.setSubject("SYM-022: "+newSAE + "_" +ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum());
                        email.setNick("SYM022");
                        email.setReplyTo("SYM022_Safety@outlook.com");
                        //email.setListTo(listEMail);
                        email.getListTo().add("thomas.devogelaere@promsocatc.net");
                        email.getListTo().add("daphne.debetancourt@promsocatc.net");
                        email.setEncodeUTF8(true);
                        email.setFilename(filename);
                        email.setSource(source);
                        try {
                            MailSender.sendMail(email);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    String addAe = bundle.getString("ae");
                    String add = bundle.getString("add");
                    String forThe = bundle.getString("for");
                    String addSubject = bundle.getString("subject");

                    addMessage(addAe+" "+add+" "+forThe+" "+addSubject+" "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                    initFormAe();
                }
            }
        }
        return redirect;
    }

    /**
     * Method to add an AE in the DB
     * @return an AE
     */
    public String submitFormUpdateAe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        AeService aeService = new AeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        Mail email = new Mail();
        Document doc = new Document();
        String filename;
        String source;

        if(ae.getAestdat() == null && ae.getAeendat() == null){
            initErrorMessageFormAe();
            this.messageErrorVisitNdFalse = "";
            redirect = "null";
        }else if(ae.getAestdat() != null && ae.getAeendat() == null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
            String aeStDate = simpleDateFormat.format(ae.getAestdat());
            int resultAeDateDate = aeStDate.compareTo(String.valueOf(now));
            if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
                initErrorMessageFormAe();
                this.messageErrorAeterm = "";
                redirect = "null";
                return redirect;
            }else if(resultAeDateDate > 0){
                initErrorMessageFormAe();
                this.messageErrorVisitDate = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAeother() && (Objects.equals(ae.getAeothersp(), "") || ae.getAeothersp() == null)){
                initErrorMessageFormAe();
                this.messageErrorAeotherspMis = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAemedim() && (Objects.equals(ae.getAemedimsp(), "") || ae.getAemedimsp() == null)){
                initErrorMessageFormAe();
                this.messageErrorAemedimspMis = "";
                redirect = "null";
                return redirect;
            }else if(ae.isAeser() && !ae.isAedeath() && !ae.isAelife() && !ae.isAehosp() && !ae.isAedisab() && !ae.isAecong() && !ae.isAemedim()){
                initErrorMessageFormAe();
                this.messageErrorAeser = "";
                redirect = "null";
                return redirect;
            }else if(ae.getAeout() == Aeout.FATAL && !ae.isAedeath() || ae.getAeout() != Aeout.FATAL && ae.isAedeath()  ){
                initErrorMessageFormAe();
                this.messageErrorAeFatal = "";
                redirect = "null";
                return redirect;
            }else{
                try{
                    ae.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    eventBean.getEvent().setMonitored(false);

                    if(Objects.equals(connectionBean.getUser().getRoleByIdRole().getRoleLabel(), "SITE")){
                        ae.setAetermc("");
                    }

                    if(!ae.isAeother()){
                        this.ae.setAeothersp("");
                    }

                    if(!ae.isAemedim()){
                        this.ae.setAemedimsp("");
                    }

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    aeService.updateAe(ae, em);
                    transaction.commit();
                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }

                if(ae.isAeser()){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat(isoDatePattern);
                    String dateAestdat = simpleDateFormatDate.format(ae.getAestdat());
                    //String dateAeendat = simpleDateFormatDate.format(ae.getAeendat());
                    LocalDateTime nowMail = LocalDateTime.now();
                    String updateSAE = bundle.getString("updateSAE");
                    String dear2 = bundle.getString("dear2");
                    String subject = bundle.getString("subjectPage.subjectNum");
                    String aeterm = bundle.getString("aePage.aeterm");
                    String aestdat = bundle.getString("aePage.aestdat");
                    String aeout = bundle.getString("aePage.aeout");
                    //String aeendat = bundle.getString("aePage.aeendat");
                    String aetoxgd = bundle.getString("aePage.aetoxgd");
                    String aesev = bundle.getString("aePage.aesev");
                    String aerel = bundle.getString("aePage.aerel");
                    String aedeath = bundle.getString("aePage.aedeath");
                    String aelife = bundle.getString("aePage.aelife");
                    String aehosp = bundle.getString("aePage.aehosp");
                    String aedisab = bundle.getString("aePage.aedisab");
                    String aecong = bundle.getString("aePage.aecong");
                    String aemedimsp = bundle.getString("aePage.aemedimsp");
                    String team = bundle.getString("team");
                    String reminder = bundle.getString("reminder");
                    filename = "SYM-022_SAE_"+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+ "_"+now +dtf.format(nowMail)+ ".pdf";
                    //source = "C:\\Users\\devog\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                    source = "C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                    try{
                        PdfWriter.getInstance(doc, new FileOutputStream(source));
                        doc.open();

                        Paragraph p = new Paragraph();

                        Image image = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\SAE_images.png");
                        image.scaleAbsolute(500, 150);
                        doc.add (image);
                        p.add("\n"+dear2+"\n\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                                "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat+"\n"+ aeout+" : "+ae.getAeout().getAeout()+
                                /*"\n"+ aeendat+" : "+dateAeendat+*/"\n"+ aetoxgd+" : "+ae.getAetoxgd().getAetoxgd()+
                                "\n"+ aesev+" : "+ae.getAesev().getAesev()+"\n"+ aerel+" : "+ae.getAerel().getAerel()+
                                "\n"+ aedeath+" : "+ae.isAedeath()+"\n"+ aelife+" : "+ae.isAelife()+
                                "\n"+ aehosp+" : "+ae.isAehosp()+"\n"+ aedisab+" : "+ae.isAedisab()+
                                "\n"+ aecong+" : "+ae.isAecong()+"\n"+ aemedimsp+" : "+ae.getAemedimsp()
                        );
                        doc.add(p);
                        Font f1 = new Font();
                        f1.setColor(BaseColor.RED);
                        f1.setStyle(Font.BOLD);
                        doc.add(new Paragraph("\n"+ reminder, f1));
                        doc.add(new Paragraph("\n"+ team +"\n"));
                        Image image1 = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\logo_Sym022.png");
                        image.scaleAbsolute(65, 41);
                        doc.add (image1);
                        doc.close();

                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }

                    email.setFrom("SYM022_Safety@outlook.com");
                    email.setMsgBody(dear2+"\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                            "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat);
                    email.setSubject("SYM-022: "+updateSAE + "_" +ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum());
                    email.setNick("SYM022");
                    email.setReplyTo("SYM022_Safety@outlook.com");
                    //email.setListTo(listEMail);
                    email.getListTo().add("thomas.devogelaere@promsocatc.net");
                    email.getListTo().add("daphne.debetancourt@promsocatc.net");
                    email.setEncodeUTF8(true);
                    email.setFilename(filename);
                    email.setSource(source);
                    try {
                        MailSender.sendMail(email);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                String addAe = bundle.getString("ae");
                String update = bundle.getString("update");
                String forThe = bundle.getString("for");
                String addSubject = bundle.getString("subject");

                addMessage(addAe+" "+update+" "+forThe+" "+addSubject+" "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                initFormAe();
            }
        }else{
            if(ae.getAestdat() != null && ae.getAeendat() != null){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
                String aeStDate = simpleDateFormat.format(ae.getAestdat());
                int resultAeDateDate = aeStDate.compareTo(String.valueOf(now));
                String aeendateDate = simpleDateFormat.format(ae.getAeendat());
                int resultAendateDate = aeendateDate.compareTo(aeStDate);
                String aeoutDate = simpleDateFormat.format(ae.getAeendat());
                int resultAeoutDate = aeoutDate.compareTo(String.valueOf(now));
                if(ae.getAeterm() == null || Objects.equals(ae.getAeterm(), "")){
                    initErrorMessageFormAe();
                    this.messageErrorAeterm = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAeDateDate > 0){
                    initErrorMessageFormAe();
                    this.messageErrorVisitDate = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAeoutDate > 0){
                    initErrorMessageFormAe();
                    this.messageErrorVisitDateAeendat = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAendateDate < 0){
                    initErrorMessageFormAe();
                    this.messageErrorAeendatBeforAestdat = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAeother() && (Objects.equals(ae.getAeothersp(), "") || ae.getAeothersp() == null)){
                    initErrorMessageFormAe();
                    this.messageErrorAeotherspMis = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAemedim() && (Objects.equals(ae.getAemedimsp(), "") || ae.getAemedimsp() == null)){
                    initErrorMessageFormAe();
                    this.messageErrorAemedimspMis = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.isAeser() && !ae.isAedeath() && !ae.isAelife() && !ae.isAehosp() && !ae.isAedisab() && !ae.isAecong() && !ae.isAemedim()){
                    initErrorMessageFormAe();
                    this.messageErrorAeser = "";
                    redirect = "null";
                    return redirect;
                }else if(ae.getAeout() == Aeout.FATAL && !ae.isAedeath() || ae.getAeout() != Aeout.FATAL && ae.isAedeath()  ){
                    initErrorMessageFormAe();
                    this.messageErrorAeFatal = "";
                    redirect = "null";
                    return redirect;
                }else{
                    try{
                        ae.setEventByIdEvent(eventBean.getEvent());
                        auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                        auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                        auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                        eventBean.getEvent().setCompleted(true);

                        if(Objects.equals(connectionBean.getUser().getRoleByIdRole().getRoleLabel(), "SITE")){
                            ae.setAetermc("");
                        }

                        if(!ae.isAeother()){
                            this.ae.setAeothersp("");
                        }

                        if(!ae.isAemedim()){
                            this.ae.setAemedimsp("");
                        }

                        transaction.begin();
                        eventService.updateEvent(eventBean.getEvent(),em);
                        auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                        aeService.updateAe(ae, em);
                        transaction.commit();
                    }catch(Exception e){
                        ProcessUtils.debug(" I'm in the catch of the addAe method: "+ e);

                    }finally {
                        if(transaction.isActive()){
                            transaction.rollback();
                        }
                        em.close();
                    }
                    if(ae.isAeser()){
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat(isoDatePattern);
                        String dateAestdat = simpleDateFormatDate.format(ae.getAestdat());
                        String dateAeendat = simpleDateFormatDate.format(ae.getAeendat());
                        LocalDateTime nowMail = LocalDateTime.now();
                        String updateSAE = bundle.getString("updateSAE");
                        String dear2 = bundle.getString("dear2");
                        String subject = bundle.getString("subjectPage.subjectNum");
                        String aeterm = bundle.getString("aePage.aeterm");
                        String aestdat = bundle.getString("aePage.aestdat");
                        String aeout = bundle.getString("aePage.aeout");
                        String aeendat = bundle.getString("aePage.aeendat");
                        String aetoxgd = bundle.getString("aePage.aetoxgd");
                        String aesev = bundle.getString("aePage.aesev");
                        String aerel = bundle.getString("aePage.aerel");
                        String aedeath = bundle.getString("aePage.aedeath");
                        String aelife = bundle.getString("aePage.aelife");
                        String aehosp = bundle.getString("aePage.aehosp");
                        String aedisab = bundle.getString("aePage.aedisab");
                        String aecong = bundle.getString("aePage.aecong");
                        String aemedimsp = bundle.getString("aePage.aemedimsp");
                        String team = bundle.getString("team");
                        String reminder = bundle.getString("reminder");
                        filename = "SYM-022_SAE_"+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+ "_"+now +dtf.format(nowMail)+ ".pdf";
                        //source = "C:\\Users\\devog\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                        source = "C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\" + filename;
                        try{
                            PdfWriter.getInstance(doc, new FileOutputStream(source));
                            doc.open();

                            Paragraph p = new Paragraph();

                            Image image = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\SAE_images.png");
                            image.scaleAbsolute(500, 150);
                            doc.add (image);
                            p.add("\n"+dear2+"\n\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                                    "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat+"\n"+ aeout+" : "+ae.getAeout().getAeout()+
                                    "\n"+ aeendat+" : "+dateAeendat+"\n"+ aetoxgd+" : "+ae.getAetoxgd().getAetoxgd()+
                                    "\n"+ aesev+" : "+ae.getAesev().getAesev()+"\n"+ aerel+" : "+ae.getAerel().getAerel()+
                                    "\n"+ aedeath+" : "+ae.isAedeath()+"\n"+ aelife+" : "+ae.isAelife()+
                                    "\n"+ aehosp+" : "+ae.isAehosp()+"\n"+ aedisab+" : "+ae.isAedisab()+
                                    "\n"+ aecong+" : "+ae.isAecong()+"\n"+ aemedimsp+" : "+ae.getAemedimsp()
                            );
                            doc.add(p);
                            Font f1 = new Font();
                            f1.setColor(BaseColor.RED);
                            f1.setStyle(Font.BOLD);
                            doc.add(new Paragraph("\n"+ reminder, f1));
                            doc.add(new Paragraph("\n"+ team +"\n"));
                            Image image1 = Image.getInstance("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\CSS\\PICTURES\\logo_Sym022.png");
                            image.scaleAbsolute(65, 41);
                            doc.add (image1);
                            doc.close();

                        } catch (DocumentException | IOException e) {
                            e.printStackTrace();
                        }

                        email.setFrom("SYM022_Safety@outlook.com");
                        email.setMsgBody(dear2+"\n"+subject+" : "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum()+
                                "\n"+ aeterm+" : "+ae.getAeterm()+"\n"+ aestdat+" : "+dateAestdat);
                        email.setSubject("SYM-022: "+updateSAE + "_" +ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum());
                        email.setNick("SYM022");
                        email.setReplyTo("SYM022_Safety@outlook.com");
                        //email.setListTo(listEMail);
                        email.getListTo().add("thomas.devogelaere@promsocatc.net");
                        email.getListTo().add("daphne.debetancourt@promsocatc.net");
                        email.setEncodeUTF8(true);
                        email.setFilename(filename);
                        email.setSource(source);
                        try {
                            MailSender.sendMail(email);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    String addAe = bundle.getString("ae");
                    String update = bundle.getString("update");
                    String forThe = bundle.getString("for");
                    String addSubject = bundle.getString("subject");

                    addMessage(addAe+" "+update+" "+forThe+" "+addSubject+" "+ae.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                    initFormAe();
                }
            }
        }
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public AeEntity getAe() {
        return ae;
    }

    public void setAe(AeEntity ae) {
        this.ae = ae;
    }

    public AeService getAeService() {
        return aeService;
    }

    public void setAeService(AeService aeService) {
        this.aeService = aeService;
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

    public String getMessageErrorVisitDate() {
        return messageErrorVisitDate;
    }

    public void setMessageErrorVisitDate(String messageErrorVisitDate) {
        this.messageErrorVisitDate = messageErrorVisitDate;
    }

    public String getMessageErrorVisitNdFalse() {
        return messageErrorVisitNdFalse;
    }

    public void setMessageErrorVisitNdFalse(String messageErrorVisitNdFalse) {
        this.messageErrorVisitNdFalse = messageErrorVisitNdFalse;
    }

    public String getMessageErrorAeendatBeforAestdat() {
        return messageErrorAeendatBeforAestdat;
    }

    public void setMessageErrorAeendatBeforAestdat(String messageErrorAeendatBeforAestdat) {
        this.messageErrorAeendatBeforAestdat = messageErrorAeendatBeforAestdat;
    }

    public String getMessageErrorAeendatPres() {
        return messageErrorAeendatPres;
    }

    public void setMessageErrorAeendatPres(String messageErrorAeendatPres) {
        this.messageErrorAeendatPres = messageErrorAeendatPres;
    }

    public String getMessageErrorAeendatMis() {
        return messageErrorAeendatMis;
    }

    public void setMessageErrorAeendatMis(String messageErrorAeendatMis) {
        this.messageErrorAeendatMis = messageErrorAeendatMis;
    }

    public String getMessageErrorAeotherspMis() {
        return messageErrorAeotherspMis;
    }

    public void setMessageErrorAeotherspMis(String messageErrorAeotherspMis) {
        this.messageErrorAeotherspMis = messageErrorAeotherspMis;
    }

    public String getMessageErrorAemedimspMis() {
        return messageErrorAemedimspMis;
    }

    public void setMessageErrorAemedimspMis(String messageErrorAemedimspMis) {
        this.messageErrorAemedimspMis = messageErrorAemedimspMis;
    }

    public String getMessageErrorVisitDateAeendat() {
        return messageErrorVisitDateAeendat;
    }

    public void setMessageErrorVisitDateAeendat(String messageErrorVisitDateAeendat) {
        this.messageErrorVisitDateAeendat = messageErrorVisitDateAeendat;
    }

    public String getMessageErrorAeterm() {
        return messageErrorAeterm;
    }

    public void setMessageErrorAeterm(String messageErrorAeterm) {
        this.messageErrorAeterm = messageErrorAeterm;
    }

    public String getMessageErrorAeser() {
        return messageErrorAeser;
    }

    public void setMessageErrorAeser(String messageErrorAeser) {
        this.messageErrorAeser = messageErrorAeser;
    }

    public String getMessageErrorAeFatal() {
        return messageErrorAeFatal;
    }

    public void setMessageErrorAeFatal(String messageErrorAeFatal) {
        this.messageErrorAeFatal = messageErrorAeFatal;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public String getMessageErrormessageErrorAeendatMis() {
        return messageErrormessageErrorAeendatMis;
    }

    public void setMessageErrormessageErrorAeendatMis(String messageErrormessageErrorAeendatMis) {
        this.messageErrormessageErrorAeendatMis = messageErrormessageErrorAeendatMis;
    }
}
