package app.wllfengshu;

import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


public class Bootstrap {
	public static void main(String[] args) {
		ProtectionDomain domain = Bootstrap.class.getProtectionDomain();
		URL location = domain.getCodeSource().getLocation();
		Server server = new Server(9001);

		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setWar( location.toExternalForm() );
		webAppContext.setContextPath("/");
		webAppContext.setResourceBase("src/main/webapp/");
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);
		try {
			server.start();
			System.out.println("server is starting,port:9001");
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}