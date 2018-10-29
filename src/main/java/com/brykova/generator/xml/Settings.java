package com.brykova.generator.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings {

    @XmlElement
    private Page page;

    public Page getPage() {
        return this.page;
    }

    @XmlElementWrapper
    @XmlElement(name = "column")
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }
}
