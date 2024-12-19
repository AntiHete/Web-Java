import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ProductServiceSecurityTest {

    @Autowired
    private ProductService productService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdmin_thenCreateProduct() {
        var dto = new ProductCreationDto("Galactic Toy", "A cosmic toy", 99.99);
        var product = productService.createProduct(dto);
        assertThat(product).isNotNull();
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenUser_thenAccessDenied() {
        var dto = new ProductCreationDto("Galactic Toy", "A cosmic toy", 99.99);
        assertThatThrownBy(() -> productService.createProduct(dto))
            .isInstanceOf(AccessDeniedException.class);
    }
}
