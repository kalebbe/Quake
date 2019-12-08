/**
 * This class handles setting up the line graph for the view. It is placed
 * with the controllers because it interacts directly with the view.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package controllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import beans.Earthquake;
import data.QuakeDAO;
import util.LoggingInterceptor;

@Named
@ViewScoped
@Interceptors(LoggingInterceptor.class)
public class ChartView implements Serializable{
	
	private static final long serialVersionUID = -1746002099203519688L;

	private LineChartModel lineModel;
	
	/**
	 * The dao was injected directly bc the method required were not within the interface.
	 */
	@EJB
	private QuakeDAO dao;
	
	/**
	 * This method calls the create line method on init
	 */
	@PostConstruct
    public void init() {
        createLineModels();
	}
	
	/**
	 * Getter for the line model.
	 * @return LineChartModel
	 */
	public LineChartModel getLineModel() {
        return lineModel;
    }
	
	/**
	 * This method sets the x and y axis properties for the line model
	 */
	private void createLineModels() {
        lineModel = initLinearModel();
        lineModel.setLegendPosition("e");
        
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        Axis xAxis = lineModel.getAxis(AxisType.X);
        
        /**
         * Sets the Y Axis to depth, will be updated later with graph overhaul
         */
        yAxis.setLabel("Depth");
        
        /**
         * Sets the minimum magnitude on the graph
         */
        xAxis.setMin(0);
        
        /**
         * Sets the maximum magnitude on the graph
         */
        xAxis.setMax(10);
        xAxis.setLabel("Magnitude");
    }
	
	/**
	 * Initiates the linear model
	 * @return LineChartModel
	 */
	private LineChartModel initLinearModel() {
		dao = new QuakeDAO();
		List<Earthquake> quakes = dao.findLast24();
		
        LineChartModel model = new LineChartModel();
        
        /**
         * Multiple series could be added here if we chose to show different days
         */
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Earthquakes"); 
 
        /**
         * Setting the data for the series to earthquakes in past 24 hours
         */
        for(Earthquake quake : quakes) {      	
        	series.set(quake.getMagnitude(), quake.getDepth());
        }
        
        /**
         * Attach series to the model
         */
        model.addSeries(series);
 
        return model;
    }
}
