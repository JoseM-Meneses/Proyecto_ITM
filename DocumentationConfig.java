@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("API para el sistema de una tienda de tenis")
                                .version("1.0")
                                .description("Esta es una API para el desarrollo de nuestro sistema.")
                                .contact(new Contact().name("Grupo 6am").email("Grupo-6am@programacionSoftware.com"))
                );
    }
}
