package br.com.tcs.treinamento.bean.welcome;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "welcomeBean")
@ViewScoped
public class WelcomeBean {

    private static final long serialVersionUID = 1L;

    private String message = "Bem-vindo ao treinamento JSF!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
