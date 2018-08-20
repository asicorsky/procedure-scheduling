package com.procedure.scheduling.web.configuration;

import com.procedure.scheduling.common.exceptions.ProcedureSchedulingException;
import com.procedure.scheduling.common.utils.StringUtils;
import com.procedure.scheduling.web.Navigation;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableWebMvc
public class WebApplicationConfiguration implements WebMvcConfigurer {

	private static final String STATIC = "static";
	private static final String CLASSPATH_PREFIX = "classpath:";

	private static String[] getResources() {

		try {
			File root = new ClassPathResource(STATIC).getFile();
			var files = FileUtils.listFiles(root, null, true);
			return files.stream().map(File::getParentFile).distinct().filter(File::isDirectory).map(file -> {

				String path = file.getAbsolutePath();
				String dirPath = path.substring(path.indexOf(STATIC)).replace("\\", StringUtils.RIGHT_SLASH);

				return CLASSPATH_PREFIX + StringUtils.RIGHT_SLASH + dirPath + StringUtils.RIGHT_SLASH;
			}).toArray(String[]::new);

		} catch (IOException e) {

			throw new ProcedureSchedulingException(e);
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(Navigation.STATIC_RESOURCES).addResourceLocations(getResources());
	}

}
