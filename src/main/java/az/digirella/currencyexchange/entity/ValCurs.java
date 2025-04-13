package az.digirella.currencyexchange.entity;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValCurs {

    @XmlAttribute(name = "Date")
    private String date;

    @XmlElement(name = "ValType")
    private List<ValType> valTypes;
}
