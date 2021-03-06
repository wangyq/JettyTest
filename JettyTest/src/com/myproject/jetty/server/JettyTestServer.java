package com.myproject.jetty.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.xml.sax.SAXException;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author wang
 *
 */
public class JettyTestServer extends Server {
	private String xmlConfigPath;

	private String contextPath;

	private String warPath;

	private String resourceBase = "./WebRoot";

	private String webXmlPath = "./WebRoot/WEB-INF/web.xml";

	public JettyTestServer(String xmlConfigPath, String contextPath, String resourceBase, String webXmlPath) {
		this(xmlConfigPath, contextPath, resourceBase, webXmlPath, null);
	}

	public JettyTestServer(String xmlConfigPath, String contextPath) {
		this(xmlConfigPath, contextPath, null, null, null);
	}

	public JettyTestServer(String xmlConfigPath, String contextPath, String warPath) {
		this(xmlConfigPath, contextPath, null, null, warPath);
	}

	public JettyTestServer(String xmlConfigPath, String contextPath, String resourceBase, String webXmlPath, String warPath) {
		super();
		if (StringUtils.isNotBlank(xmlConfigPath)) {
			this.xmlConfigPath = xmlConfigPath;
			readXmlConfig();
		}

		if (StringUtils.isNotBlank(warPath)) {
			this.warPath = warPath;
			if (StringUtils.isNotBlank(contextPath)) {
				this.contextPath = contextPath;
				applyHandle(true);
			}
		} else {
			if (StringUtils.isNotBlank(resourceBase))
				this.resourceBase = resourceBase;
			if (StringUtils.isNotBlank(webXmlPath))
				this.webXmlPath = webXmlPath;
			if (StringUtils.isNotBlank(contextPath)) {
				this.contextPath = contextPath;
				applyHandle(false);
			}
		}

	}

	private void readXmlConfig() {
		try {
			XmlConfiguration configuration = new XmlConfiguration(new FileInputStream(this.xmlConfigPath));
			configuration.configure(this);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyHandle(Boolean warDeployFlag) {

		ContextHandlerCollection handler = new ContextHandlerCollection();

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(contextPath);
		webapp.setDefaultsDescriptor("./jetty/etc/webdefault.xml");

		if (!warDeployFlag) {
			webapp.setResourceBase(resourceBase);
			webapp.setDescriptor(webXmlPath);
		} else {
			webapp.setWar(warPath);
		}

		handler.addHandler(webapp);

		super.setHandler(handler);
	}

	public void startServer() {
		try {
			super.start();
			System.out.println("Current total number of threads: " + super.getThreadPool().getThreads() + " , Current number of  idle threads: " + super.getThreadPool().getIdleThreads());
			System.out.println("Jetty Server started successfully!");
			super.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getXmlConfigPath() {
		return xmlConfigPath;
	}

	public void setXmlConfigPath(String xmlConfigPath) {
		this.xmlConfigPath = xmlConfigPath;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getResourceBase() {
		return resourceBase;
	}

	public void setResourceBase(String resourceBase) {
		this.resourceBase = resourceBase;
	}

	public String getWebXmlPath() {
		return webXmlPath;
	}

	public void setWebXmlPath(String webXmlPath) {
		this.webXmlPath = webXmlPath;
	}

	public String getWarPath() {
		return warPath;
	}

	public void setWarPath(String warPath) {
		this.warPath = warPath;
	}
}
