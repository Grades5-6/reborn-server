package com.reborn.server.infra.license.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "item")
public class LicenseItem {
    @JacksonXmlProperty(localName = "jmcd")
    private String jmcd;

    @JacksonXmlProperty(localName = "jmfldnm")
    private String jmfldnm;

    @JacksonXmlProperty(localName = "mdobligfldnm")
    private String mdobligfldnm;

    @JacksonXmlProperty(localName = "obligfldnm")
    private String obligfldnm;

    @JacksonXmlProperty(localName = "qualgbnm")
    private String qualgbnm;

    @JacksonXmlProperty(localName = "seriesnm")
    private String seriesnm;
}
