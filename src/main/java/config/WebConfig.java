package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Ruta a la carpeta donde se guardan los archivos
		Path uploadDir = Paths.get("uploads");
		String uploadPath = uploadDir.toFile().getAbsolutePath();

		// Mapea la URL /uploads/** a la carpeta física uploads/
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath + "/");
	}
}