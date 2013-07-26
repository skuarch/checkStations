package model.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Stations bean.
 * @author skuarch
 */
@Entity
@Table(name="stations")
public class Stations implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="station_id")        
    private long id;
    
    @Column(name="station_name")
    private String name;
    
    @Column(name="station_url")
    private String url;
    
    @Column(name="station_genre")
    private int genre;
    
    @Column(name="station_frecuncie")
    private String freciencie;
    
    @Column(name="station_description")
    private String description;
    
    @Column(name="station_clicks")
    private Integer clicks;
    
    @Column(name="station_active")
    private Integer active;
    
    @Column(name="station_contry")
    private Integer contry;
    
    @Column(name="station_language")
    private String languaje;
    
    @Column(name="station_type")
    private Integer type;
    
    @Column(name="station_website")
    private String website;
    
    @Column(name="station_position")
    private Integer position;
    
    //==========================================================================
    public Stations(){
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getContry() {
        return contry;
    }

    public void setContry(int contry) {
        this.contry = contry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFreciencie() {
        return freciencie;
    }

    public void setFreciencie(String freciencie) {
        this.freciencie = freciencie;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getLanguaje() {
        return languaje;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    
} // end class