package az.digirella.currencyexchange.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Valute {

    @XmlAttribute(name = "Code")
    private String code;

    @XmlElement(name = "Nominal")
    private String nominal;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Value")
    private BigDecimal value;
}

