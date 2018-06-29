package hubble.backend.providers.configurations.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Primary
@PropertySource("classpath:config/jira.properties")
public class JiraProviderEnvironmentImpl implements JiraProviderEnvironment{
	
	@Value("${jira.host}")
	private String host;
	@Value("${jira.port}")
	private String port;
	@Value("${jira.user}")
	private String user;
	@Value("${jira.password}")
	private String password;
	
	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public String getPort() {
		return this.port;
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
}
