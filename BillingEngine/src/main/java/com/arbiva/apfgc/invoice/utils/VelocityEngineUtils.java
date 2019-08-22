package com.arbiva.apfgc.invoice.utils;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author srinivasa
 *
 */
@Component("velocityEngineUtils")
public class VelocityEngineUtils {
	
	private static final Logger LOGGER = Logger.getLogger(VelocityEngineUtils.class);
    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * 
     * @param parameters
     * @param templateURI
     * @return
     * @throws Exception
     */
    public String getTemplateData(Map<String, Object> parameters,
            String templateURI) throws Exception {
        Template template = velocityEngine.getTemplate(templateURI);
        VelocityContext context = getVelocityContext(parameters);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    
   /**
    * 
    * @param context
    * @param templateURI
    * @return
    * @throws Exception
    */
    public String getTemplateData(VelocityContext context, String templateURI) throws Exception {
        Template template = velocityEngine.getTemplate(templateURI);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        template = null;
        return writer.toString();
    }

    /**
     * 
     * @param parameters
     * @return
     */
    private VelocityContext getVelocityContext(Map<String, Object> parameters) {
        VelocityContext context = new VelocityContext();
        for (Entry<String, Object> entryMap : parameters.entrySet()) {
            context.put(entryMap.getKey(), entryMap.getValue());
        }
        return context;
    }
}
