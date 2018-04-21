

import java.net.URL;
import java.security.ProtectionDomain;
import java.util.Arrays;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public final class Launcher {
	private static boolean isDev;
	public static void main(String[] args) throws Exception {
			isDev=args.length>0&&"dev".equals(args[0]);
		    ProtectionDomain domain = Launcher.class.getProtectionDomain();
			URL location = domain.getCodeSource().getLocation();
			WebAppContext webapp = new WebAppContext();
			webapp.setContextPath("/");
			System.out.println(Arrays.toString(args));
			webapp.setWar(location.toExternalForm());
			if(isDev){
				webapp.setResourceBase("src/main/webapp/");
			}
			webapp.setParentLoaderPriority(true);
			// as enumerated from http://jira.codehaus.org/browse/JETTY-1256
			String[] configurations = new String[] { "org.eclipse.jetty.webapp.WebInfConfiguration",
					"org.eclipse.jetty.webapp.WebXmlConfiguration", "org.eclipse.jetty.webapp.MetaInfConfiguration",
					"org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration"
					// ,"org.eclipse.jetty.plus.webapp.Configuration"
					, "org.eclipse.jetty.annotations.AnnotationConfiguration",
					"org.eclipse.jetty.webapp.JettyWebXmlConfiguration"
					// ,"org.eclipse.jetty.annotations.ContainerInitializerConfiguration"
			};
			webapp.setAttribute("org.eclipse.jetty.webapp.configuration", configurations);
			webapp.setConfigurationClasses(configurations);

			int port = 8080;
			Server server = new Server(port);
			server.setHandler(webapp);
			server.start();
			System.out.println("app is started on "+port);
			server.join();
	   }
	}
