package ua.it_contract.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class DataObject {

    @Id
    private String id;

    private String hash;
    private String description;
    private String format;
    private String url;
    private String title;
    private String documentOf;
    private String documentType;
    private String relatedItem;
    private String language;
    private Date datePublished;
    private Date dateModified;
}
