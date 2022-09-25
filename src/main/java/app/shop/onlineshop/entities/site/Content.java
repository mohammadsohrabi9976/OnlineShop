package app.shop.onlineshop.entities.site;
import javax.persistence.*;

@Entity
public class Content {
    @Id
    @GeneratedValue
    private long id;
    private String key;
    @Column(length = 4000)
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
