package br.com.udemycourse.demo.integrationtests.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement
public class WrapperPersonVO implements Serializable {

    @JsonProperty("_embedded")
    private PersonEmbeddedVO embeddedVO;

    public PersonEmbeddedVO getEmbeddedVO() {
        return embeddedVO;
    }

    public void setEmbeddedVO(PersonEmbeddedVO embeddedVO) {
        this.embeddedVO = embeddedVO;
    }
}
