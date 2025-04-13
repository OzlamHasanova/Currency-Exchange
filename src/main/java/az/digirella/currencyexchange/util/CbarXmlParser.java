package az.digirella.currencyexchange.util;

import az.digirella.currencyexchange.entity.ValCurs;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class CbarXmlParser {

    public ValCurs parseXml(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (ValCurs) unmarshaller.unmarshal(inputStream);
    }
}

