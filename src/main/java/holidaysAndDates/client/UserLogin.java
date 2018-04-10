package holidaysAndDates.client;


import holidaysAndDates.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name = "userLogin", eager = true)
@RequestScoped
@Component
public class UserLogin {

    private String username;
    private String password;

    @Autowired
    private ServiceImpl service;


    public void login() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (service.validate(username,password)) {
            context.getExternalContext().getSessionMap().put("user", username);
            try {
                context.getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Send an error message on Login Failure
            context.addMessage(null, new FacesMessage("Authentication Failed. Check username or password."));
        }
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ServiceImpl getService() {
        return service;
    }

    public void setService(ServiceImpl service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
