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

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import beans.Earthquake;
import data.QuakeDAO;

@ManagedBean
public class ChartView {
	private LineChartModel lineModel;
	QuakeDAO dao;
	
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
        
        yAxis.setLabel("Depth"); //Set Y axis to depth, will be changed to time later
        xAxis.setMin(0); //Min magnitude
        xAxis.setMax(10); //Max magnitude
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
        
        //Multiple series could be added here to show different days
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Earthquakes"); 
 
        //Setting the data for the series to earthquakes in past 24 hours
        for(Earthquake quake : quakes) {      	
        	series.set(quake.getMagnitude(), quake.getDepth());
        }
        
        model.addSeries(series); //Attach series to the model
 
        return model;
    }
}
