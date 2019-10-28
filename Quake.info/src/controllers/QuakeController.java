package controllers;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import beans.Earthquake;
import services.QuakeService;
import util.DatabaseException;

@ManagedBean
@ViewScoped
public class QuakeController {
	QuakeService qs = new QuakeService();
	
	public String goHome() {
		try {
			List<Earthquake> quakes = qs.getQuakes();
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("quakes", quakes);
			return "Home.xhtml";
		} catch(DatabaseException e) {
			System.out.println("======================> Database connection failure!");
			return "Error.xhtml";
		}
	}
	
	public String viewChart() {
		try {
			List<Earthquake> lm = qs.getQuakes();
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("model", lm);
			return "Chart.xhtml";
		} catch(DatabaseException e) {
			System.out.println("======================> Database connection failure!");
			return "Error.xhtml";
		}
	}
}
