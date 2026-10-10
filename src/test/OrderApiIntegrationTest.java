package com.example.ecommerce;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class OrderApiIntegrationTest {
 @Autowired MockMvc mvc; @Autowired ProductRepository products;
 @Test @WithMockUser(username="customer",roles={"CUSTOMER"})
 void customerPlacesOrderAndReadsOwnOrders() throws Exception {
  Product p=products.save(new Product("Test product","test",new BigDecimal("10.00"),5,"Test"));
  mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content("{\"productId\":"+p.getId()+",\"quantity\":2}"))
   .andExpect(status().isCreated()).andExpect(jsonPath("$.totalPrice").value(20.00));
  mvc.perform(get("/api/orders/my")).andExpect(status().isOk()).andExpect(jsonPath("$[0].customerUsername").value("customer"));
 }
 @Test @WithMockUser(roles={"CUSTOMER"}) void customerCannotSeeAllOrders() throws Exception {
  mvc.perform(get("/api/orders")).andExpect(status().isForbidden());
 }
}
