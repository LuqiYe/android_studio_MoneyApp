package exam2.moneyapp.dto;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import exam2.moneyapp.R;
public class AccDto extends RealmObject {

    private String date;
    @PrimaryKey
    private String transId;
    private String cat;
    private String amount;
    private String type;

    public AccDto() {
    }

    public AccDto(String date, String transId, String cat, String amount, String type) {
        this.date = date;
        this.transId = transId;
        this.cat = cat;
        this.amount = amount;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
