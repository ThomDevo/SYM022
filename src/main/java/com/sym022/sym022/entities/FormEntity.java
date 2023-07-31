package com.sym022.sym022.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "form", schema = "sym022", catalog = "")
public class FormEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_form", nullable = false)
    private int idForm;
    @Basic
    @Column(name = "form_num", nullable = false)
    private int formNum;
    @Basic
    @Column(name = "form_label", nullable = false)
    private Object formLabel;
    @OneToMany(mappedBy = "formByIdForm")
    private Collection<EventEntity> eventsByIdForm;

    public int getIdForm() {
        return idForm;
    }

    public void setIdForm(int idForm) {
        this.idForm = idForm;
    }

    public int getFormNum() {
        return formNum;
    }

    public void setFormNum(int formNum) {
        this.formNum = formNum;
    }

    public Object getFormLabel() {
        return formLabel;
    }

    public void setFormLabel(Object formLabel) {
        this.formLabel = formLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormEntity that = (FormEntity) o;
        return idForm == that.idForm && formNum == that.formNum && Objects.equals(formLabel, that.formLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idForm, formNum, formLabel);
    }

    public Collection<EventEntity> getEventsByIdForm() {
        return eventsByIdForm;
    }

    public void setEventsByIdForm(Collection<EventEntity> eventsByIdForm) {
        this.eventsByIdForm = eventsByIdForm;
    }
}
