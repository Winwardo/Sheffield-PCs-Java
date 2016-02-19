package api;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.config.ConfigFactory;

import databaseaccess.DatabaseLoginInfo;

@Path("/meta")
@Api(value = "/meta", description = "General info about the system, user")
public class ApiMeta {
	static {
		ConfigFactory.config().setBasePath("http://www.shefunipcs.info/api/");
	}

	@GET
	@Path("/certs/{password}")
	@ApiOperation(value = "/certs/{password}", notes = "Trust all SSL certs.")
	@Produces(MediaType.APPLICATION_JSON)
	public String trustCerts(@PathParam("password") String password) {
		// Hm. This is dubious at the least.
		if (DatabaseLoginInfo.apiPassword.equals(password)) {
			// http://www.rgagnon.com/javadetails/java-fix-certificate-problem-in-HTTPS.html
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
					// Deliberately empty
				}

				@Override
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
					// Deliberately empty
				}

			} };

			SSLContext sc;
			try {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
				return new Gson().toJson("Allows untrusted SSL certs.");
			} catch (NoSuchAlgorithmException | KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new Gson().toJson("Failed to allow untrusted SSL certs.");
	}

	@GET
	@Path("/info")
	@ApiOperation(value = "/info", notes = "Returns info about Sheffield PCs.")
	@Produces(MediaType.APPLICATION_JSON)
	public String info() {
		return "{version: \"1.1.0\"}";
	}

	@GET
	@Path("/docs/web")
	@ApiOperation(value = "/docs/web", notes = "Sets the Swagger basepath to http://www.shefunipcs.info/api")
	@Produces(MediaType.APPLICATION_JSON)
	public String swaggerWeb() {
		ConfigFactory.config().setBasePath("http://www.shefunipcs.info/api");
		final String result = ConfigFactory.config().getBasePath();

		return new Gson().toJson(result);
	}
}
