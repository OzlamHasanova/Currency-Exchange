package az.digirella.currencyexchange.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValType {

    @XmlAttribute(name = "Type")
    private String type;

    @XmlElement(name = "Valute")
    private List<Valute> valutes;
}

