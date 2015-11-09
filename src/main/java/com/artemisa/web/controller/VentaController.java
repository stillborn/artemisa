package com.artemisa.web.controller;

import com.artemisa.domain.ProductoPrecio;
import com.artemisa.service.IBaseService;
import com.artemisa.service.ProductoPrecioServiceImpl;
import com.artemisa.service.VentaServiceImpl;
import com.artemisa.service.exceptions.EntityExistsException;
import com.artemisa.service.exceptions.InvalidValueException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.faces.application.FacesMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("session")
public class VentaController<Venta> extends BaseController implements Serializable
{
    private static final Log logger = LogFactory.getLog(VentaController.class);

    private static final long serialVersionUID = -5667552636749767933L;
    
    @Autowired
    private VentaServiceImpl service;
    
    @Autowired
    private ProductoPrecioServiceImpl productoPrecioService;
    
    private Venta selected;
    
    private Venta newVenta;
    
    private List<Venta> filter;

    public VentaController() 
    {
        newVenta = (Venta) new com.artemisa.domain.Venta();
    }

    public Venta getNewVenta() {
        return newVenta;
    }

    public void setNewVenta(Venta newVenta) {
        this.newVenta = newVenta;
    }

    public Venta getSelected() {
        return selected;
    }

    public void setSelected(Venta selected) {
        this.selected = selected;
    }

    public List<Venta> getFilter() {
        return filter;
    }

    public void setFilter(List<Venta> filter) {
        this.filter = filter;
    }
    
    public void create() throws InvalidValueException
    {
        if(this.getNewVenta() != null)
        {
            try
            {
                this.service.save((com.artemisa.domain.Venta)this.getNewVenta());
                
                addMessage("Se ha creado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
                this.clean();
            }
            catch(HibernateException | EntityExistsException | InvalidValueException ex )
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            }
        }
        else
        {
            addMessage("Debe seleccionar un item valido.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void update()
    {
        if(this.getSelected() != null)
        {
            try
            {
                this.service.update((com.artemisa.domain.Venta)this.getSelected());
                
                addMessage("Se ha actualizado correctamente!", FacesMessage.SEVERITY_INFO);
                addParameter("success", true);
                setDetail(false);
            }
            catch(HibernateException | EntityExistsException ex)
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            }
        }
        else
        {
            addMessage("Debe seleccionar un item válido.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void delete(com.artemisa.domain.Venta entity)
    {
        if(entity != null)
        {
            try
            {
                this.service.delete(entity);
                
                addMessage("Se ha eliminado correctamente!", FacesMessage.SEVERITY_INFO);
                setDetail(false);
            }
            catch(HibernateException ex)
            {
                addMessage(ex.getMessage(), FacesMessage.SEVERITY_FATAL);
                setDetail(true);
            }
        }
        else
        {
            addMessage("Debe seleccionar un item válido.", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void setVentaPrecio()
    {
        throw new UnsupportedOperationException();
    }
    
    public long getVentaTotal(Date fechaVenta)
    {
        return this.service.getTotalPriceByDate(fechaVenta);
    }
    
    public List<com.artemisa.domain.Venta> getList()
    {
        return this.service.list();
    }
    
    public LineChartModel getVentasByMonth()
    {
        LineChartModel model = new LineChartModel();
        
        Calendar currentDate = Calendar.getInstance();
        
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        String currentMonthString = new SimpleDateFormat("MMM").format(currentDate.getTime()).toUpperCase();
        
        List<Object> ventas = this.service.getVentasByMonth(currentMonth);
        
        ChartSeries ventasSerie = new ChartSeries();
        ventasSerie.setLabel("Ventas");
        
        ventas.stream().map((venta) -> (Object[])venta).forEach((ventaArray) -> {
            
            Date ventaDate = (Date)ventaArray[0];
            double ventaTotal = (double)ventaArray[1];
            
            currentDate.setTime(ventaDate);
            
            String sDay = String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH));
            
            ventasSerie.set(sDay,  ventaTotal);
        });
        
        model.addSeries(ventasSerie);
        
        model.setTitle("Ventas Diarias Mes " + currentMonthString + "-" + currentYear);
        model.setLegendPosition("e");
        model.setShowPointLabels(true);
        model.setExtender("skinChart");
        model.setAnimate(true);
        //model.getAxes().put(AxisType.X, new CategoryAxis("Dia"));
        //model.getAxes().put(AxisType.Y, new CategoryAxis("Venta"));
        
        Axis xAxis = model.getAxis(AxisType.X);
        xAxis.setTickInterval("1");
        xAxis.setMin(0);
        xAxis.setMax(31);
        
        return model;
    }
    
    public void clean()
    {
        this.newVenta = (Venta) new com.artemisa.domain.Venta();
    }
}
