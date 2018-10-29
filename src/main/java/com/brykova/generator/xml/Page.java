package com.brykova.generator.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Page {

    public Integer getWidth() {
        return width;
    }

    @XmlElement
    private Integer width;

    public Integer getHeight() {
        return height;
    }

    @XmlElement
    private Integer height;
}
