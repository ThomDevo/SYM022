package com.sym022.sym022.entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "form", schema = "sym022", catalog = "")
public class FormEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_form", nullable = false)
    private int idForm;

    @Basic
    @Range(min=1,max= 99)
    @NotNull
    @Column(name = "form_num", nullable = false)
    private int formNum;

    @Basic
    @NotNull
    @Column(name = "form_label", nullable = false)
    private Object formLabel;

    @OneToMany(mappedBy = "formByIdForm")
    private List<EventEntity> eventsByIdForm;

    /*--- Getters and Setters ---*/

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

    public List<EventEntity> getEventsByIdForm() {
        return eventsByIdForm;
    }

    public void setEventsByIdForm(List<EventEntity> eventsByIdForm) {
        this.eventsByIdForm = eventsByIdForm;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormEntity that = (FormEntity) o;
        return idForm == that.idForm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idForm);
    }
}
